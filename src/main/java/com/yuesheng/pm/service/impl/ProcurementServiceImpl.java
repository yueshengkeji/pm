package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProcurementMapper;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.CompanyModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author XiaoSong
 * @date 2016-08-08
 * 采购订单服务对象实现类
 */
@Service("procurementService")
@DependsOn("databaseVersionService")
public class ProcurementServiceImpl implements ProcurementService, FrameStateCheckService {
    @Autowired
    private ProcurementMapper procurementMapper;
    @Autowired
    private ProcurementMaterService procurementMaterService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private ProDetailService proDetailService;
    @Autowired
    private ProPutDetailService proPutDetailService;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    private ProDownloadHistoryService proDownloadHistoryService;
    @Autowired
    private CityService cityService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private ProReportService reportService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SystemConfigService configService;
    @Autowired
    private PutStorageMaterialService storageMaterialService;
    @Autowired
    private AttachService attachService;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addProcurement(Procurement procurement, String[] applyy, Map<String, JSONObject> updateMater) {
        boolean isUpdate = false;
        procurement.setVoucherCoding(procurement.getStaff().getCoding());
        procurement.setVoucherName(procurement.getStaff().getName());

        verify(procurement);
        String date = DateUtil.getProcurementDate();
        //设置采购日期
        procurement.setPmDate(date);
        //设置发起时间
        procurement.setFqDate(date);
        //设置制单时间
        procurement.setVoucherDate(date + " "+DateUtil.getTime());
        //设置收货地址
        setCity(procurement);
        //设置合同
        setContract(procurement);
        procurement.setState((byte) 2);
        //设置税率
        procurement.setTax(procurement.getTax() == null ? 0 : procurement.getTax());
        long time = System.currentTimeMillis();
//        添加采购订单
        procurementMapper.addProcurement(procurement);
        //设置到货时间
        if (StringUtils.isNotBlank(procurement.getDhDate())) {
            procurementMapper.addProDhDate(procurement);
        }
        System.out.println("添加主单据耗时：" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
//        添加采购订单材料
        JSONObject jo = null;
        for (ProMaterial proMaterial : procurement.getMaterial()) {
            if (updateMater != null) {
                jo = updateMater.get(proMaterial.getMajor());
            }
            if (jo != null) {
                /*
                订单中对申请单材料有修改
                修改订单中材料id
                 */
                proMaterial.getMaterial().setId((String) jo.get("id"));
                isUpdate = true;
            }

            if (proMaterial.getMaterial() == null) {
                Material material = materialService.getMaterialByid(proMaterial.getId());
                proMaterial.setMaterial(material);
                proMaterial.setUnit(material.getUnit());
            }
//            生成材料id
            proMaterial.setId(UUID.randomUUID().toString());
            if (Objects.isNull(proMaterial.getUnit())) {
                proMaterial.setUnit(proMaterial.getMaterial().getUnit());
            }
        }
        if (procurement.getMaterial().size() > 30) {
            //数据过多，分批插入数据库
            List<ProMaterial> materials = procurement.getMaterial();
            for (int i = 0; i < materials.size(); i++) {
                procurementMaterService.addMaterial2(materials.get(i), procurement.getId());
            }
        } else {
//        添加订单材料集合到数据库
            procurementMaterService.addMater(procurement.getMaterial(), procurement.getId());
        }
        System.out.println("添加材料耗时：" + (System.currentTimeMillis() - time));
        executorService.submit(() -> {
            //        获取申请单材料总数，并改变申请单状态
            List<ProMaterial> proMaterials = procurement.getMaterial();
            if (applyy != null && applyy.length > 0) {
                HashMap<String, Boolean> cache = new HashMap<>();
                for (int i = 0; i < applyy.length; i++) {
                    String applyId = applyy[i];
                    if (StringUtils.isNotBlank(applyId) && !cache.containsKey(applyId)) {
                        applyService.setStatus(applyId);
                        cache.put(applyId, true);
                    }
                }
            } else if (proMaterials != null) {
                HashMap<String, Boolean> cache = new HashMap<>();
                for (ProMaterial pm : proMaterials) {
                    ApplyMaterial am = applyMaterialService.getMaterById(pm.getMajor());
                    if (am != null && !cache.containsKey(am.getApplyid())) {
                        applyService.setStatus(am.getApplyid());
                        cache.put(am.getApplyid(), true);
                    }
                }
            }
            //添加报表
            reportService.genByProcurement(procurement);
        });
        System.out.println("其他线程差值：" + (System.currentTimeMillis() - time));
        return isUpdate;
    }

    private void setContract(Procurement procurement) {
        Contract contract = procurement.getContract();
        if (contract == null) {
            contract = new Contract();
            contract.setId("");
            procurement.setContract(contract);
        }
    }

    private void setCity(Procurement procurement) {
        City city = procurement.getCity();
        if (city == null) {
            city = new City();
            city.setId("");
            procurement.setCity(city);
        } else if ((StringUtils.isBlank(city.getId()) || StringUtils.equals("-1", city.getId())) && StringUtils.isNotBlank(city.getName())) {
            city = cityService.insert(city);
            procurement.setCity(city);
        }
    }

    private void verify(Procurement procurement) {
        if (procurement.getRemark() == null) {
            procurement.setRemark("");
        }
        if (StringUtils.isBlank(procurement.getId())) {
            //生成采购订单id号
            procurement.setId(UUID.randomUUID().toString());
        }
        if (Objects.isNull(procurement.getCity())) {
            procurement.setCity(new City());
        }
        if (StringUtils.isBlank(procurement.getCity().getId())) {
            procurement.getCity().setId("-1");
        }
        if (StringUtils.isBlank(procurement.getSaleMoney())) {
            procurement.setSaleMoney("0");
        }
    }

    @Override
    public Procurement getProcurementById(String pId) {
        return procurementMapper.getProcurementById(pId);
    }

    @Override
    public List<Procurement> getProcurements(Integer pageNum,Integer pageSize, Map params) {
        PageHelper.startPage(pageNum,pageSize);
        return procurementMapper.getProcurements( params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProcurement(Procurement procurement) {
        flowMessageService.deleteMessage(procurement.getId());
        //修改后重新发起
        return procurementMapper.updateProcurement(procurement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProcurement(String id) {
        boolean isDelete = true;
        Procurement pro = getProcurementById(id);
        if (pro == null) {
            return 1;
        }
        if (pro.getPutState() == 0 && (pro.getState() == 0 || pro.getState() == 2)) {

            if (isDelete) {
                if (pro.getMaterial() == null) {
                    pro.setMaterial(procurementMaterService.getProMatersByProId(pro.getId()));
                }
//                先删除订单材料
                procurementMaterService.deleteByProId(pro.getId());
//                删除订单主单据
                procurementMapper.deleteProcurement(pro.getId());
                procurementMapper.deleteDiscard(pro.getId());
                // 删除订单报表
                reportService.deleteByProId(pro.getId());
                //删除财务对账单
                proPutDetailService.deleteByProId(pro.getId());
//                删除附件
                attachService.deleteAttachByModuleId(pro.getId(),"sdpm013");
            }

            //提交删除审批消息任务
            executorService.submit(() -> {
                //                恢复申请单状态
                String[] applyList = new String[pro.getMaterial().size()];
                int i = 0;
                for (ProMaterial material : pro.getMaterial()) {
                    ApplyMaterial apply = applyMaterialService.getMaterById(material.getMajor());
                    if (!ArrayUtil.isExists(applyList, apply.getApplyid())) {
//                        通过申请单材料主键获取申请单主单据id
                        applyService.setStatus(apply.getApplyid());
                        applyList[i] = apply.getApplyid();
                        i++;
                    }
                }
                flowMessageService.deleteMessage(id);
            });
            return 1;
        }
        return -1;
    }

    @Override
    public void approve(String id, int i, String coding, String date) {
        procurementMapper.approve(id, i, coding, date);
    }

    @Override
    public Integer getCount(Map<String, Object> params) {
        return procurementMapper.getCount(params);
    }

    @Override
    public void updatePutState(byte b, String proId, List<ProMaterial> materials) {
        //        更新订单材料入库数量集合
        procurementMaterService.updatePutSum(materials);
//        更新订单状态
        procurementMapper.updatePutState(b, proId);
    }

    @Override
    public void updatePutState(byte b, String proId) {
        procurementMapper.updatePutState(b, proId);
    }

    @Override
    public List<Procurement> getProcurementAll() {
        return procurementMapper.getProcurementAll();
    }

    @Override
    public List<Procurement> getProcurementByContract(String contract) {
        List<Procurement> procurements = procurementMapper.getProcurementByContract(contract);
        for (Procurement temp : procurements) {
            temp.setMaterial(procurementMaterService.getProMatersByProId(temp.getId()));
        }
        return procurements;
    }

    @Override
    public int updateProContract(Procurement procurement) {
        if (procurement.getContract() == null) {
            return -1;
        }
        return procurementMapper.updateProContract(procurement);
    }

    @Override
    public int getProCountByParam(Map<String, Object> rp) {
        return procurementMapper.getProCountByParam(rp);

    }

    @Override
    public Double getProMoneyByCompany(String companyId) {
        return procurementMapper.getProMoneyByCompany(companyId);
    }

    @Override
    public int deleteContract(String contractId, String staffCoding) {
        return procurementMapper.deleteContract(contractId, staffCoding);
    }

    @Override
    public int updateTax(Procurement procurement) {
        Procurement temp = getProcurementById(procurement.getId());
        if (temp.getTax().compareTo(procurement.getTax()) != 0) {
            //更新材料行明细税率
            temp.setMaterial(procurementMaterService.getNotMatersByProId(temp.getId()));
            if (temp.getMaterial() != null) {
                double money = 0.0;
                double tax = procurement.getTax();
                for (ProMaterial pm : temp.getMaterial()) {
                    //不含税金额 = 含税总金额 / (1 + 税率/100)
                    if (pm.getMoneyTax() != null && pm.getMoneyTax() > 0) {
                        if (pm.getDhDate() == null) {
                            pm.setDhDate("");
                        }
                        if (tax == 0.0) {
                            //不含税金额
                            pm.setMoney(0.0);
                            //不含税单价
                            pm.setPrice(0.0);
                            //设置税额
                            pm.setTaxMoney(0.0);
                        } else {
                            money = pm.getMoneyTax() / (1 + (tax / 100));
                            //不含税金额
                            pm.setMoney(money);
                            //不含税单价
                            pm.setPrice(money / pm.getSum());
                            //设置税额
                            pm.setTaxMoney(pm.getMoneyTax() - money);
                        }

                        procurementMaterService.updateMater(pm);
                    }
                }
            }
            //更新税率
            return procurementMapper.updateTax(procurement);
        } else {
            return -1;
        }

    }

    @Override
    public Double getProMoneyByDate(String companyId, String start, String end) {
        return procurementMaterService.getProMoneyByDate(companyId, start, end);
    }

    @Override
    public List<CompanyModel> getProMoneyByCompany(String start, String end, Integer size) {
        return procurementMaterService.getProMoneyByCompanyMax(start, end, size);
    }

    @Override
    public Integer getProNumberByDate(String id, String start, String end) {
        return procurementMapper.getProNumberByDate(id, start, end);
    }

    @Override
    public Procurement approveOk(String proId) {
        try {
            Procurement p = getProcurementById(proId);
            if (p != null) {
                genDownloadHistory(p);
                notify(p, "审核通过");
                genProDetail(p, procurementMaterService.getProMatersByProId(p.getId()), false, null);
            }
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateVoucherDate(Procurement p) {
        procurementMapper.updateVoucherDate(p);
    }

    @Override
    public void genProDetail(Procurement p, List<ProMaterial> materials, boolean isUpdate, String putId) {
        Company company = p.getCompany();
        Project project2 = null;
        Map<String, Double> pm = new HashMap<>();
        Map<String, Project> projectMap = new HashMap<>();
        for (int x = 0; x < materials.size(); x++) {
            if ((project2 = materials.get(x).getProject()) != null) {
                Double money = pm.get(project2.getId() + "&" + project2.getName());
                if (money != null) {
                    money += materials.get(x).getMoneyTax();
                } else {
                    money = materials.get(x).getMoneyTax();
                    projectMap.put(project2.getId(), project2);
                }
                pm.put(project2.getId() + "&" + project2.getName(), money);
            }
        }
        Iterator<String> projectIterator = pm.keySet().iterator();
        String date = DateUtil.getProcurementDate();
        while (projectIterator.hasNext()) {
            ProPutForDetail putDetail = new ProPutForDetail();
            String pid = projectIterator.next();
            String projectType = projectMap.get(pid.split("&")[0]).getFolderId();
            if (p.getPm01326().equals("1")) {
                projectType = "2";
            } else {
                projectType = proDetailService.getCompanyType(projectType);
            }

            ProDetail detail = proDetailService.getDetailByCompany(company.getId(), date.substring(0, 4), projectType);
            if (Objects.isNull(detail)) {
                continue;
            }
            putDetail.setMainId(detail.getId());
            putDetail.setProject(projectMap.get(pid.split("&")[0]));
            // putDetail.setProDate(DateFormat.getDate());
            putDetail.setProjectName(putDetail.getProject().getName());
            putDetail.setProId(p.getId());
            putDetail.setPutId(putId);
            List<ProPutForDetail> putForDetails = proPutDetailService.getProDetailBYProId(p.getId(), detail.getId());
            if (isUpdate) {
                boolean update = false;
                for (ProPutForDetail putForDetail : putForDetails) {
                    if (putForDetail.getProject().getId().equals(putDetail.getProject().getId())) {
                        Double money = putForDetail.getProMoney();
                        if (money == null || money <= 0) {
                            //第一次入库添加，更新数据
                            putDetail = putForDetail;
                            putDetail.setProDate(DateFormat.getDate());
                            putDetail.setPutId(putId);
                            setProMoney(p, pm, putDetail, pid);
                            proPutDetailService.updateProMoney(putDetail);
                            update = true;
                            break;
                        }
                    }
                }
                if (!update) {
                    putDetail.setProDate(DateFormat.getDate());
                    //没有数据，添加
                    setProMoney(p, pm, putDetail, pid);
                    proPutDetailService.addDetail(putDetail, p.getStaff());
                }
            } else {
                //直接添加，不设置采购金额
                proPutDetailService.addDetail(putDetail, p.getStaff());
            }
        }
    }

    @Override
    public void genProDetailByPut(Procurement p, List<StorageMaterial> materials, boolean isUpdate, String putId) {
        Company company = p.getCompany();
        Project project2 = null;
        Map<String, Double> pm = new HashMap<>();
        Map<String, Project> projectMap = new HashMap<>();
        HashMap<String, Project> temp = new HashMap<>(16);
        for (int x = 0; x < materials.size(); x++) {
            StorageMaterial sm = materials.get(x);
            if (sm != null) {
                project2 = sm.getProject();
                if (project2 == null) {
                    if ((project2 = temp.get(sm.getProjectId())) == null) {
                        project2 = projectService.getProjectByid(sm.getProjectId());
                        temp.put(sm.getProjectId(), project2);
                    }
                    sm.setProject(project2);
                }
                if (project2 != null) {
                    Double money = pm.get(project2.getId() + "&" + project2.getName());
                    if (money != null) {
                        money += sm.getMoneyTax();
                    } else {
                        money = sm.getMoneyTax();
                        projectMap.put(project2.getId(), project2);
                    }
                    pm.put(project2.getId() + "&" + project2.getName(), money);
                }
            }
        }
        Iterator<String> projectIterator = pm.keySet().iterator();
        String date = DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        while (projectIterator.hasNext()) {
            ProPutForDetail putDetail = new ProPutForDetail();
            String pid = projectIterator.next();
            String projectType = projectMap.get(pid.split("&")[0]).getFolderId();
            if (p.getPm01326().equals("1")) {
                projectType = "2";
            } else {
                projectType = proDetailService.getCompanyType(projectType);
            }

            ProDetail detail = proDetailService.getDetailByCompany(company.getId(), date.substring(0, 4), projectType);
            if (detail == null) {
                continue;
            }
            putDetail.setMainId(detail.getId());
            putDetail.setProject(projectMap.get(pid.split("&")[0]));
            // putDetail.setProDate(DateFormat.getDate());
            putDetail.setProjectName(putDetail.getProject().getName());
            putDetail.setProId(p.getId());
            putDetail.setPutId(putId);
            List<ProPutForDetail> putForDetails = proPutDetailService.getProDetailBYProId(p.getId(), detail.getId());
            if (isUpdate) {
                boolean update = false;
                if (putForDetails.size() == 1) {
                    if (putForDetails.get(0).getProject().getId().equals(putDetail.getProject().getId())) {
                        Double money = putForDetails.get(0).getProMoney();
                        if (putForDetails.size() == 1 && money == null || money <= 0) {
                            //第一次入库添加，更新数据
                            putDetail = putForDetails.get(0);
                            putDetail.setProDate(DateFormat.getDate());
                            putDetail.setPutId(putId);
                            setProMoney(p, pm, putDetail, pid);
                            proPutDetailService.updateProMoney(putDetail);
                            update = true;
                        }
                    }
                }
                if (!update) {
                    putDetail.setProDate(DateFormat.getDate());
                    //没有数据，添加
                    setProMoney(p, pm, putDetail, pid);
                    proPutDetailService.addDetail(putDetail, p.getStaff());
                }
            } else {
                //直接添加，不设置采购金额
                proPutDetailService.addDetail(putDetail, p.getStaff());
            }
        }
    }

    private void setProMoney(Procurement p, Map<String, Double> pm, ProPutForDetail putDetail, String pid) {
        Double oldMoney = putDetail.getProMoney();
        if (oldMoney == null) {
            oldMoney = 0.0;
        }
        if (StringUtils.isNotBlank(p.getSaleMoney())) {
            Double saleMoney = Double.valueOf(p.getSaleMoney());

            //优惠金额不为0，并且小于此次入库金额，方才设置优惠金额为对账单数据
            if (saleMoney > 0 && pm.size() == 1 && saleMoney < pm.get(pid)) {
                putDetail.setProMoney(saleMoney);
                //有优惠金额，添加负数平账
                // ProPutForDetail putForDetail = new ProPutForDetail();
                // putForDetail.setProId(pid);
            } else {
                putDetail.setProMoney(pm.get(pid));
            }
        } else {
            putDetail.setProMoney(pm.get(pid));
        }
        putDetail.setProMoney(oldMoney + putDetail.getProMoney());
    }

    @Override
    public int discard(String proId, String discardState) {
        return procurementMapper.discard(proId, discardState);
    }

    @Override
    public int insertDiscard(Procurement p) {
        procurementMaterService.addDiscardMater(p.getMaterial(), p.getId());
        if (p.getContract() == null) {
            Contract contract = new Contract();
            contract.setId("");
            p.setContract(contract);
        }
        if (p.getCity() == null) {
            City city = new City();
            city.setId("");
            p.setCity(city);
        }
        if (p.getStaff() == null) {
            Staff staff = new Staff();
            staff.setId("");
            p.setStaff(staff);
        }
        return procurementMapper.insertDiscard(p);
    }

    @Override
    public Procurement getProcurementSimpleById(String id) {
        return procurementMapper.getProcurementSimpleById(id);
    }

    @Override
    public List<Procurement> getProcurementByParam(Map<String, Object> rp) {
        return procurementMapper.getProByParam(rp);
    }

    @Override
    public int updateProComapny(Procurement procurement) {
        return procurementMapper.updateProCompany(procurement);
    }

    @Override
    public Double getProMoneyByProject(String projectId) {
        return procurementMaterService.getProMoneyByProject(projectId);
    }

    @Override
    public String updateProContract(Procurement procurement, Staff staff) {
        //设置修改人员名称
        procurement.setVoucherCoding(staff.getName());

        //修改的税率和以前的不相等，才修改
        updateTax(procurement);
        updateProComapny(procurement);

        if (procurement.getId() == null || "".equals(procurement.getId())) {
            return "-1";
        } else if (updateProContract(procurement) != -1) {
            //更新合同
            Contract contract = contractService.getContractById(procurement.getContract().getId());
            if (contract != null) {
                return companyService.getCompanyById(procurement.getCompany().getId()).getName() + "," + contract.getName();
            }
            return companyService.getCompanyById(procurement.getCompany().getId()).getName() + ",";
        }
        return "-1";
    }

    @Override
    public int expressCode(Procurement procurement) {
        if (StringUtils.isBlank(procurement.getExpressCode()) || StringUtils.isBlank(procurement.getId())) {
            return -1;
        } else {
            Procurement p = getProcurementById(procurement.getId());
            if (!Objects.isNull(p)) {
                int row = procurementMapper.expressCode(procurement);
                List<Staff> staffList = new ArrayList<>();
                staffList.add(p.getStaff());
                Map<String, Object> param = new HashMap<>();
                param.put("title", "采购订单已发货");
                param.put("mTitle", p.getPmNumber() + "采购订单已发货");
                param.put("content", "快递单号：" + p.getExpressCode());
                param.put("url", "/managent/systemNotify/procurement/list/list");
                flowNotifyService.msgNotify(staffList, param);
                try {
                    List<ProMaterial> proMaterials = procurementMaterService.getProMatersByProId(p.getId());
                    ArrayList<String> applyList = new ArrayList<>();
                    proMaterials.forEach(item -> {
                        if (!Objects.isNull(item.getMajor())) {
                            ApplyMaterial am = applyMaterialService.getMaterById(item.getMajor());
                            if (!Objects.isNull(am) && !applyList.contains(am.getApplyid())) {
                                applyList.add(am.getApplyid());
                            }
                        }
                    });
                    applyList.forEach(a -> {
                        applyService.notifyExpress(a, procurement.getExpressCode());
                    });
                } catch (Exception e) {
                    LogManager.getLogger("mylog").error("发货通知异常：" + e.getMessage());
                }
                return row;
            }
            return 0;
        }

    }

    @Override
    public Integer getPutState(String id) {
        return procurementMapper.getPutState(id);
    }

    @Override
    public String getApproveVoucherImage(Procurement procurement) {
        // HtmlToImageUtil.convertToImage(null,1024,768);
        return null;
    }

    @Override
    public int rebackState(String id) {
        Procurement p = getProcurementById(id);
        boolean isReBack = false;
        if (!Objects.isNull(p) && p.getPutState() == 4) {
            //完全入库的采购订单
            List<ProMaterial> pmList = procurementMaterService.getProMatersByProId(id);
            for (int i = 0; i < pmList.size(); i++) {
                ProMaterial item = pmList.get(i);
                List<StorageMaterial> sm = storageMaterialService.getByProMaterId(item.getId());
                double putSum = getPutSum(item, sm);
                if ((item.getInSum() - putSum) > 0.0) {
                    //该材料还有未入库的数，回退采购订单状态
                    isReBack = true;
                    //更新采购订单已入库数量
                    item.setInSum(putSum);
                    procurementMaterService.updatePutSum(item);
                }
            }
            if (isReBack) {
                updatePutState((byte) 0, id);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void checkErrorPutState(Integer day) {
        HashMap<String, Object> param = new HashMap<>();
        String start = DateUtil.format(DateUtil.rollDay(new Date(), day), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        String end = DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        param.put("start", start);
        param.put("end", end);
        param.put("state", 1);
        param.put("putState", "0,3");
        List<Procurement> procurements = getProcurementByParam(param);
        procurements.forEach(item -> {
            if (!Objects.isNull(item)) {
                List<ProMaterial> materials = procurementMaterService.getNotMatersByProId(item.getId());
                if (materials.isEmpty()
                        && item.getState() == 1
                        && (item.getPutState() == 0 || item.getPutState() == 3)) {
                    updatePutState((byte) 4, item.getId());
                    LoggerFactory.getLogger("mylog").error("采购订单状态异常，自动修复完成：" + item.getPmNumber() + "," + item.getId());
                }
            }
        });
    }

    private double getPutSum(ProMaterial item, List<StorageMaterial> sm) {
        double sum = 0.0;
        for (int i = 0; i < sm.size(); i++) {
            sum += sm.get(i).getPutSum();
        }
        return sum;
    }

    private void getAutoWidth(String value, Cell cell) {
        if (StringUtils.isBlank(value)) {
            cell.setWidth(3096);
        } else {
            cell.setWidth(value.length() * 1024);
        }
    }

    private String getConfigStr(String value) {
        return configService.getValueByParentCoding(value, Constant.PRO_COMPANY_TYPE);
    }

    @Override
    public int updatePro(Procurement pro, Staff staff, Map<String, JSONObject> updateMater) {
        String isUpdate = "0";
        pro.setVoucherCoding(staff.getCoding());
        if (pro.getRemark() == null) {
            pro.setRemark("");
        }
        if (StringUtils.isBlank(pro.getDhDate())) {
            pro.setDhDate("");
        }
        setCity(pro);
        setContract(pro);
        String date = DateFormat.getDate();
        //设置采购日期
        pro.setPmDate(date);
        //设置发起时间
        pro.setFqDate(date);
        JSONObject jo;
        Map<String, Boolean> cache = new HashMap<>();
        //更新订单材料信息
        for (ProMaterial material : pro.getMaterial()) {
            if (material != null) {
                if (!Objects.isNull(updateMater)) {
                    jo = updateMater.get(material.getMajor());
                    if (jo != null) {
                        //设置新的材料
                        material.getMaterial().setId((String) jo.get("id"));
                        isUpdate = "1";
                    }
                }
                if (Objects.isNull(material.getUnit())) {
                    material.setUnit(material.getMaterial().getUnit());
                }
                //数据库中不存在，添加新的
                if ("".equals(material.getId()) || material.getId() == null) {
                    material.setId(UUID.randomUUID().toString());
                    material.setProId(pro.getId());
                    procurementMaterService.addMater(material);
                } else {
                    //更新
                    procurementMaterService.updateMater(material);
                }
                //更新申请单状态
                executorService.submit(() -> {
                    ApplyMaterial am = applyMaterialService.getMaterById(material.getMajor());
                    if (am != null && cache.get(am.getApplyid()) == null) {
                        cache.put(am.getApplyid(), true);
                        applyService.setStatus(am.getApplyid());
                    }
                });
            }
        }
        //更新订单信息
        pro.setState((byte) 2);
        updateProcurement(pro);
        executorService.submit(() -> {
            //删除旧报表
            reportService.deleteByProId(pro.getId());
            //生成新报表
            reportService.genByProcurement(pro);
        });
        return Integer.valueOf(isUpdate);
    }


    @Override
    public ProDownloadHistory genDownloadHistory(Procurement p) {
        ProDownloadHistory history = new ProDownloadHistory();
        history.setProcurement(p);
        history.setStaff(p.getStaff());
        history.setState((byte) 0);
        try {
            if (Objects.isNull(p.getMaterial())) {
                p.setMaterial(procurementMaterService.getProMatersByProId(p.getId()));
            }
            history.setImg(new String(Base64.getEncoder().encode(HtmlToImageUtil.convertProcurementToImage(p, 1836, 720))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        proDownloadHistoryService.insert(history);
        return history;
    }

    private void notify(Procurement p, String msg) {
        //微信通知发起人
        List<Staff> staffList = new ArrayList<>();
        staffList.add(p.getStaff());
        Map<String, Object> param = new HashMap<>();
        param.put("title", "采购订单审核通过");
        param.put("mTitle", p.getPmNumber() + "：" + msg);
        param.put("content", "采购凭证已经生成！");
        param.put("url", "/managent/getPage?pageName=managerIndex");
        flowNotifyService.msgNotify(staffList, param);
    }

    @Override
    public List<Procurement> getProByMaterial(String materialId, String projectId) {
        return procurementMapper.getProByMaterial(materialId, projectId);
    }

    @Override
    public List<Procurement> getProcurementByDate(String startDate, String endDate) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return new ArrayList<>();
        }

        int day = DateUtil.getOffsetDays(DateUtil.parse(startDate, DateUtil.PATTERN_CLASSICAL_SIMPLE),
                DateUtil.parse(endDate, DateUtil.PATTERN_CLASSICAL_SIMPLE));
        if (day > 366) {
            return new ArrayList();
        }
        return procurementMapper.getProcurementByDate(startDate, endDate);
    }

    @Override
    public void oaSuccessChange(FlowMessage message) {
        Procurement p = this.approveOk(message.getFrameId());
        if (!Objects.isNull(p)) {
            //更新审批状态，采购时间
            p.setVoucherDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL));
            p.setState((byte) 1);
            p.setAuditCoding(message.getLastApproveUser().getCoding());
            p.setApproveDate(DateUtil.getDate());
            updateVoucherDate(p);

            //更新材料最后采购单价
            List<ProMaterial> pmList = p.getMaterial();
            if (!Objects.isNull(pmList)) {
                pmList.forEach(item -> {
                    Material m = item.getMaterial();
                    m.setLastPrice(item.getPriceTax());
                    materialService.updateLastPro(m);
                });
            }
        }
    }

    @Override
    public int updateState(Procurement p) {
        return procurementMapper.updateState(p);
    }

    @Override
    public String getProSaleMoney(String proId) {
        return procurementMapper.getProSaleMoney(proId);
    }
}
