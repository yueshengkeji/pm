package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.mapper.PutStorageMapper;
import com.yuesheng.pm.mapper.PutStorageMaterialMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author XiaoSong
 * @date 2016-08-23 入库单服务实现类
 */
@Service("putStorageService")
public class PutStorageServiceImpl implements PutStorageService {
    @Autowired
    private PutStorageMapper putStorageMapper;
    @Autowired
    private PutStorageMaterialMapper putStorageMaterialMapper;
    @Autowired
    private PutStorageMaterialService putStorageMaterialService;
    @Autowired
    private ProcurementMaterService procurementMaterService;
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    @Lazy
    private ProPutSignService proPutSignService;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    private ProDetailService proDetailService;
    @Autowired
    private ProPutDetailService proPutDetailService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ProPutSignService signService;
    @Autowired
    private OutMaterService outMaterService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private FixedAssetsService fixedAssetsService;
    @Autowired
    private MaterialService materialService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addStorage(PutStorage storage) {
        if (storage.getProject() == null) {
            Project p = new Project();
            p.setId("");
            storage.setProject(p);
        }
        if (storage.getCompany() == null) {
            Company c = new Company();
            c.setId("");
            storage.setCompany(c);
        }
//        添加入库单到数据库
        Integer i = putStorageMapper.addStorage(storage);
        if (i != null && i > 0) {
            //            添加入库单材料到数据库
            List<StorageMaterial> materials = storage.getMaterialList();
            ArrayList<StorageMaterial> insertList = new ArrayList<>();
            if (materials.size() > 20) {
                for (int j = 0; j < materials.size(); j++) {
                    materials.get(j).setPm02719(j);
                    insertList.add(materials.get(j));
                    if (insertList.size() == 20) {
                        i += putStorageMaterialMapper.addMaterials(insertList);
                        insertList.clear();
                    }
                }
                if (!insertList.isEmpty()) {
                    i += putStorageMaterialMapper.addMaterials(insertList);
                }
            } else {
                for (int x = 0; x < materials.size(); x++) {
                    materials.get(x).setPm02719(x);
                }
                i += putStorageMaterialMapper.addMaterials(storage.getMaterialList());
            }

        }
        return i;
    }

    @Override
    public List<PutStorage> getStorage(String params) {
        return putStorageMapper.getStorage(params);
    }

    @Override
    public int updateApprove(Map<String, Object> params) {
        return putStorageMapper.updateApprove(params);
    }

    @Override
    public PutStorage getStorageById(String id) {
        PutStorage ps = putStorageMapper.getStorageById(id);
        if (!Objects.isNull(ps)) {
            PageHelper.startPage(1,1);
            ps.setProject(putStorageMapper.getProjectIdByMater(id));
        }
        return ps;
    }

    @Override
    public Integer getCount(Map<String, Object> params) {
        return putStorageMapper.getCount(params);
    }

    @Override
    public List<PutStorage> getPutStorages(Map<String, Object> params) {
        PageHelper.startPage(1, Constant.PAGESIZE);
        return putStorageMapper.getPutStorages( params);
    }

    @Override
    public Integer updatePutMixMoney(PutStorage storage) {
        return putStorageMapper.updatePutMixMoney(storage);
    }

    @Override
    public Integer updatePutTax(PutStorage storage) {
        return putStorageMapper.updatePutTax(storage);
    }

    @Override
    public Integer updatePutRemark(PutStorage storage) {
        return putStorageMapper.updatePutRemark(storage);
    }

    @Override
    public Integer updatePutDate(PutStorage storage) {
        return putStorageMapper.updatePutDate(storage);
    }

    @Override
    public Integer updatePutSerial(PutStorage storage) {
        return putStorageMapper.updatePutSerial(storage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deletePut(String id) {
        if (putStorageMapper.deletePut(id) > 0) {
            return putStorageMaterialService.deleteMaterByPutId(id);
        }
        return -1;
    }

    @Override
    public int updatePutMessage(PutStorage storage) {
        return putStorageMapper.updatePutMessage(storage);
    }

    @Override
    public List<PutStorage> getPutAll() {
        return putStorageMapper.getPutAll();
    }

    @Override
    public List<PutStorage> seekPutStorage(String str) {
        return putStorageMapper.seekPutStorage(str);
    }

    @Override
    public String isOut(String id) {
        PageHelper.startPage(1,1);
        return putStorageMapper.isOut(id);
    }

    @Override
    public String getNowPutSerial(String date) {
        PageHelper.startPage(1,1);
        return putStorageMapper.getNowPutSerial(date);
    }

    @Override
    public List<PutStorage> getStorageByProject(String proId) {
        return putStorageMapper.getStorageByProject(proId);
    }

    @Override
    public List<PutStorage> getPutStorageList( Map<String, Object> params) {
        return putStorageMapper.getPutStorageList( params);
    }

    @Override
    public void approve(FlowMessage msg) {
        approve(getStorageById(msg.getFrameId()), 2, msg.getLastApproveUser().getId(), DateUtil.getDate());
    }

    @Override
    public void approve(PutStorage storage, int tempState, String coding, String date) {

        HashMap<String, Object> params = new HashMap(16);
        params.put("id", storage.getId());
        params.put("state", tempState);
        params.put("coding", coding);
        params.put("date", date);

        List<StorageMaterial> sms = storage.getMaterialList();
        if (sms == null) {
            sms = putStorageMaterialService.getMaterAllByPutId(storage.getId());
        }
        HashMap<String, Procurement> proMap = new HashMap<>();
        if (tempState == 1) {
            //审核入库单，更新库存
            sms.forEach(item -> {
                //更新库存信息,先查询该材料所在仓库库存总数，再加上本次入库总数，则为新的库存总数
                storageService.updatePutMaterial(item, storage.getStorage().getId(), true);
                //更新采购订单已入库信息
                setProMaterial(proMap, item, true);
                //保存材料操作记录
                /*Fio fio = new Fio();
                fio.setDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
                fio.setMaterId(item.getMaterial().getId());
                fio.setPutMaterId(item.getId());
                fio.setPutSum(item.getPutSum());
                fio.setPutPrice(item.getPrice());
                fio.setType((byte) 30);
                fio.setIo05(0.0);
                fio.setIo06(0.0);
                fio.setPutMoney(item.getMoney());
                putStorageMaterialService.addFifoi(fio);*/
            });
        } else {
            //反审核，减少库存，更新采购单入库状态
            sms.forEach(item -> {
                storageService.updatePutMaterial(item, storage.getStorage().getId(), false);
                //设置采购订单行已入库数和入库事件
                setProMaterial(proMap, item, false);
                //删除材料操作记录
//                putStorageMaterialService.deleteFifois(item.getId());
            });
        }
        //更新采购单入库数和入库时间
        proMap.forEach((key, val) -> {
            updateProState(val.getMaterial(), val, 1, 0);
        });
    }

    @Override
    public void setProMaterial(HashMap<String, Procurement> proMap, StorageMaterial item, boolean approve) {
        ProMaterial pm = procurementMaterService.getMatersById(item.getProMaterId());
        if (Objects.isNull(pm)) {
            return;
        }
        Procurement p = proMap.get(pm.getProId());
        List<ProMaterial> pmList = null;
        if (Objects.isNull(p)) {
            p = new Procurement();
            pmList = new ArrayList<>();
        } else {
            pmList = p.getMaterial();
        }
        p.setId(pm.getProId());
        //查询该材料入库总数
        Double inSum = putStorageMaterialService.getPutSumByProId(item.getProMaterId());
        if (Objects.isNull(inSum)) {
            inSum = 0.0;
        }
        pm.setInSum(inSum);
        if (approve) {
            pm.setInDate(DateUtil.getDate());
        } else {
            pm.setInDate("");
        }
        pmList.add(pm);
        p.setMaterial(pmList);
        proMap.put(pm.getProId(), p);
    }

    @Override
    public int getPutCount(Map<String, Object> params) {
        return putStorageMapper.getPutCount(params);
    }

    @Override
    public int getPutStoragesListFastCount(Map<String, Object> params) {
        return putStorageMapper.getPutStoragesListFastCount(params);
    }

    @Override
    public int addPutStorageSelect(PutStorage putStorage, Staff staff) {

        //取出入库材料集合
        List<StorageMaterial> materials = putStorage.getMaterialList();
        if (materials == null || materials.size() <= 0) {
            return -3;
        }
        Procurement pro = procurementService.getProcurementById(putStorage.getProId());
        if (pro == null) {        //采购订单不存在
            return -1;
        } /*else if (pro.getPutState() == 4) {
            return -4;
        }*/
        Staff psStaff = pro.getStaff();
        if (!staff.getId().equals(psStaff.getId())) {
            return -2;
        }
        if (putStorage.getStorage() == null || putStorage.getStorage().getId() == null || "".equals(putStorage.getStorage().getId().trim())) {
            return -5;
        }
        String date = DateUtil.getProcurementDate();
        putStorage.setCreateDate(date);  //设置表单创建时间
        if (putStorage.getPutDate() == null || "".equalsIgnoreCase(putStorage.getPutDate())) {
            putStorage.setPutDate(date);        //设置入库时间
        }
        if ((putStorage.getTax() == null) && pro.getTax() != null) {
            putStorage.setTax(pro.getTax());
        }
        putStorage.setPutPerson(staff.getCoding());     //设置入库人员
        putStorage.setPm02608(putStorage.getCreateDate());      //设置跟创建时间一样的列 08
        putStorage.setPm02610(staff.getCoding());       //设置制单人员编号

        if (putStorage.getCompany() == null) {
            putStorage.setCompany(pro.getCompany());
        }
        if (StringUtils.isBlank(putStorage.getId())) {
            putStorage.setId(UUID.randomUUID().toString());     //生成入库单id
        }


        //通过订单材料主键id获取材料，并添加到集合中，以便后续对采购订单的更新
        List<ProMaterial> materialList = new ArrayList();

//        入库单材料引用
        StorageMaterial putMaterial;

        //初始化订单对象，以便后续的状态更新
        Procurement procurement = procurementService.getProcurementById(putStorage.getProId());
        List<ProMaterial> proMaterials = new ArrayList<>();
        double putSums = 0.0;
        for (int i = 0; i < materials.size(); i++) {
            putMaterial = materials.get(i);
            if (putMaterial == null) {
                continue;
            }
            ProMaterial material = procurementMaterService.getMatersById(putMaterial.getProMaterId());     //获取订单材料对象
            putMaterial.setId(UUID.randomUUID().toString());        //生成该入库材料的id
            putMaterial.setStorageId(putStorage.getId());           //设置入库单id
            if (material == null) {

            } else {
                proMaterials.add(material);
                material.setInDate(DateFormat.getDate());       //设置订单材料的入库时间
                materialList.add(material);     //添加订单材料到集合中
                if (material.getInSum() != null) {
                    material.setInSum(putMaterial.getPutSum() + material.getInSum());       //设置已入库数量,便于后面更新
                } else {
                    material.setInSum(putMaterial.getPutSum());       //设置已入库数量,便于后面更新
                }

                //记录此次入库总数
                putSums += putMaterial.getPutSum();
            }
        }

        //        添加入库单到数据库
        Integer state = addStorage(putStorage);
        if (materialList.size() <= 0) {
            return -3;
        }
        if (StringUtils.isNotBlank(putStorage.getSaleMoney()) && Double.valueOf(putStorage.getSaleMoney()) > 0) {
            procurement.setSaleMoney(putStorage.getSaleMoney());
        }
        //添加对账单数据
        procurementService.genProDetailByPut(procurement, materials, true, putStorage.getId());
//        更新采购订单状态
        return updateProState(materialList, procurement, state, putSums);
    }

    @Override
    public int updateProState(List<ProMaterial> materialList, Procurement procurement, Integer state, double putSum) {
        if (state > 0) {
            byte putState = Constant.STATE_4;
            HashMap<String,String> materMap = new HashMap<>();
            for (ProMaterial pm : materialList) {
                materMap.put(pm.getId(),"1");
                if (pm.getInSum() < pm.getSum()) {
                    //部分入库
                    putState = Constant.STATE_3;
                    break;
                }
            }

            if(putState == Constant.STATE_4){
                //查询该订单的所有材料，遍历查询是否全部入库
                List<ProMaterial> pmList = procurementMaterService.getProMatersByProId(procurement.getId());
                for (ProMaterial pm : pmList) {
                    if(!materMap.containsKey(pm.getId())){
                        //未比较过的材料，并且有入库数小于采购数的，设置未部分入库，否则设置订单未完全入库
                        if(pm.getInSum() < pm.getSum()){
                            putState = Constant.STATE_3;
                            break;
                        }
                    }
                }
            }

            Map<String, BigDecimal> sums = procurementMaterService.getCount(procurement.getId());     //获取该采购订单材料总数和已入库总数
            BigDecimal putSums = sums.get(Constant.PUTSUM);
            if (putSums.equals(0.0)) {
                procurementService.updatePutState((byte) 0, procurement.getId(), materialList);
            } else {
                procurementService.updatePutState(putState, procurement.getId(), materialList);
            }
            return putState;

//            putSums = putSums.add(new BigDecimal(putSum));        //入库总数 = 原来入库数量 + 此次入库数量;
//            putSums = putSums.setScale(6, BigDecimal.ROUND_HALF_UP);
//            如果入库总数量 >= 采购数量，更新采购订单状态为全部入库
//            if (putSums.compareTo(sums.get(Constant.YSUM)) == 0 || putSums.compareTo(sums.get(Constant.YSUM)) == 1) {
//                procurementService.updatePutState(Constant.STATE_4, procurement.getId(), materialList);    //更新订单状态为完全入库,并更新材料的入库数量和入库时间
//                return Constant.STATE_4;
//            } else if (sums.get(Constant.PUTSUM).compareTo(new BigDecimal(0)) != -1) { //如果入库数量大于0
//                procurementService.updatePutState(Constant.STATE_3, procurement.getId(), materialList);    //更新订单状态为部分入库，并更新材料的入库数量和入库时间
//                return Constant.STATE_3;
//            }
        }
        return -1;
    }

    @Override
    public PutStorage getPutStorageByDetailId(String detailId) {
        return putStorageMapper.getPutStorageByDetailId(detailId);
    }

    @Override
    public List<PutStorage> getPutStorageListFast(Map<String, Object> params) {
        return putStorageMapper.getPutStorageListFast(params);
    }


    @Override
    public PutStorage getPutBySerial(String putSerial) {
        PageHelper.startPage(1,1);
        PutStorage ps = putStorageMapper.getPutStorageBySerial(putSerial);
        if (ps != null) {
            ps.setMaterialList(putStorageMaterialService.getMaterAllByPutId(ps.getId()));
        }
        return ps;
    }

    @Override
    public int putStorageByPro(PutStorage putStorage, Procurement pro, Staff staff) {

        if (pro == null) {        //采购订单不存在
            return -1;
        }
        if (pro.getPutState() == 4) {
            return -4;
        }
        Staff psStaff = pro.getStaff();
        if (!staff.getId().equals(psStaff.getId())) {       //不能入库其他人的订单
            return -2;
        }
        if (putStorage.getStorage() == null || putStorage.getStorage().getId() == null || "".equals(putStorage.getStorage().getId().trim())) {
            return -5;
        }
        putStorage.setCompany(pro.getCompany());
        putStorage.setTax(pro.getTax());        //设置税率
        String date = DateUtil.getProcurementDate();
        putStorage.setPutDate(date);        //设置入库时间
        putStorage.setCreateDate(putStorage.getPutDate());  //设置表单创建时间
        putStorage.setPutPerson(staff.getCoding());     //设置入库人员
        putStorage.setPm02608(putStorage.getCreateDate());      //设置跟创建时间一样的列 08
        putStorage.setPm02610(staff.getCoding());       //设置制单人员编号
        //通过订单id获取材料集合，并计算入库的材料和数量
        List<ProMaterial> materialList = procurementMaterService.getNotMatersByProId(putStorage.getProId());

        //初始化订单对象，以便后续的状态更新
        Procurement procurement = procurementService.getProcurementById(putStorage.getProId());

        //遍历采购订单中材料集合，并计算出入库数量
        ProMaterial material;    //订单材料对象
        StorageMaterial storageMaterial;     //入库单材料对象
        List<StorageMaterial> materials = new ArrayList<>();     //初始化入库单材料集合
        putStorage.setId(UUID.randomUUID().toString());         //生成入库单id
        double putSums = 0.0;
        for (int i = 0; i < materialList.size(); i++) {
            material = materialList.get(i);
            double tempSum = material.getSum() - material.getInSum();   //入库数量=采购数量-已入库数量

            if (tempSum > 0) {    //如果该材料采购数量减去入库入量还大于0,则再次入库该材料
                double moneyTax = tempSum * material.getPriceTax();       //计算出含税金额
                storageMaterial = new StorageMaterial();
                storageMaterial.setId(UUID.randomUUID().toString());        //生成该入库材料的id

                storageMaterial.setPutSum(tempSum);                      //设置入库数量
                storageMaterial.setTaxPrice(material.getPriceTax());        //设置含税单价
                storageMaterial.setMoneyTax(moneyTax);        //设置含税金额

                if (procurement.getTax() == 0.0) {
                    storageMaterial.setPrice(0.0);          //设置不含税单价
                    storageMaterial.setMoney(0.0);          //设置不含税金额
                    storageMaterial.setTaxMoney(0.0);        //税额=含税金额-不含税金额
                } else {
//                    不含税价格= 售价 / (1 + 增值税率),
                    Double money = moneyTax / (1.0 + pro.getTax());
                    storageMaterial.setPrice(money / tempSum);          //设置不含税单价
                    storageMaterial.setMoney(money);          //设置不含税金额
                    storageMaterial.setTaxMoney((moneyTax - money));        //税额=含税金额-不含税金额
                }

                storageMaterial.setMaterial(material.getMaterial());        //设置材料对象
                storageMaterial.getMaterial().setUnit(material.getUnit());  //设置材料单位
                storageMaterial.setProMaterId(material.getId());            //设置采购订单材料主键id
                storageMaterial.setProjectId(material.getProjectId());      //设置项目id

                storageMaterial.setStorageId(putStorage.getId());           //设置入库单id
                materials.add(storageMaterial);     //添加入库材料对象到集合中
//                material.setInSum((tempSum + material.getInSum()));     //系数
                material.setInDate(DateFormat.getDate());       //设置订单材料的入库时间
                if (material.getInSum() != null) {
                    material.setInSum(storageMaterial.getPutSum() + material.getInSum());       //设置已入库数量,便于后面更新
                } else {
                    material.setInSum(storageMaterial.getPutSum());       //设置已入库数量,便于后面更新
                }

                putSums += tempSum;     //记录此次入库总数
            }
        }
        putStorage.setMaterialList(materials);      //设置该入库单材料集合
        if (materialList.size() <= 0) {     //没有可入库的材料
            return -3;
        }
        //生成采购对账单数据
        procurementService.genProDetailByPut(procurement, materials, true, putStorage.getId());
//        添加入库单到数据库
        Integer state = addStorage(putStorage);
//        更新采购订单状态
        return updateProState(materialList, procurement, state, putSums);
    }

    @Override
    public int addPutSign(PutStorage putStorage, Staff staff) {
        List<StorageMaterial> materials = putStorage.getMaterialList();
        if (materials == null || materials.size() <= 0) {
            return -3;
        }
        Procurement pro = procurementService.getProcurementById(putStorage.getProId());
        if (pro == null) {
            return -1;
        } else if (pro.getPutState() == 4) {
            return -4;
        }
        Staff psStaff = pro.getStaff();
        if (!staff.getId().equals(psStaff.getId())) {
            return -2;
        }
        if (putStorage.getStorage() == null || putStorage.getStorage().getId() == null || "".equals(putStorage.getStorage().getId().trim())) {
            return -5;
        }
        checkPro(putStorage);
        ProPutSign sign = new ProPutSign();
        sign.setId(UUID.randomUUID().toString());
        sign.setPutDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        sign.setPutobj(JSON.toJSONString(putStorage));
        sign.setSignImg("");
        sign.setStaffId(staff.getId());
        sign.setSignStaffId(putStorage.getProjectLeader().getId());
        sign.setType(0);
        sign.setProId(putStorage.getProId());
        sign.setPastDate(putStorage.getPm02629());
        proPutSignService.insert(sign);
        List<Staff> staffSign = new ArrayList<>();
        staffSign.add(putStorage.getProjectLeader());
        Map<String, Object> param = new HashMap<>();
        param.put("title", "材料入库签单");
        param.put("mTitle", "等待" + putStorage.getProjectLeader().getName() + "签字确认");
        param.put("content", "发送人：" + staff.getName());
        param.put("url", WebParam.VUETIFY_BASE + "/procurement/signPut/" + sign.getId());
        flowNotifyService.msgNotify(staffSign, param);
        return 0;
    }

    private boolean checkPro(PutStorage putStorage) {
        ProPutSign pps = new ProPutSign();
        pps.setProId(putStorage.getProId());
        pps = signService.queryByParam(pps);
        if (Objects.isNull(pps)) {
            return false;
        } else if (pps.getType() == 0) {
            //相同采购单，存在未签名，删除前一张单据
            signService.deleteById(pps.getId());
        }
        return true;
    }

    @Override
    public Double getPutMoneyByProject(String projectId) {
        return putStorageMaterialService.getPutMoneyByProject(projectId);
    }

    @Override
    public List<PutStorage> getNoDetailList(String startDate, String endDate) {
        return putStorageMapper.getNoDetailList(startDate, endDate);
    }

    @Override
    public boolean genPutDetail(PutStorage storage) {
        Procurement procurement = storage.getProcurement();
        if (procurement != null && procurement.getId() != null) {

            ProDetail pd = proDetailService.getDetailByCompany(storage.getCompany().getId(), storage.getPutDate().substring(0, 4), proDetailService.getCompanyTypeByPro(procurement));
            if (!Objects.isNull(pd)) {
                List<ProPutForDetail> detail = proPutDetailService.getProDetailByPutId(storage.getId());
                if (detail == null || detail.isEmpty()) {
                    //添加新的对账单数据
                    detail = proPutDetailService.getProDetailBYProId(procurement.getId(), pd.getId());
                    if (detail != null) {
                        if (Objects.isNull(storage.getProject())) {
                            PageHelper.startPage(1,1);
                            storage.setProject(putStorageMapper.getProjectIdByMater(storage.getId()));
                        }
                        for (int i = 0; i < detail.size(); i++) {
                            String id = storage.getProject().getId();
                            Project p = detail.get(i).getProject();
                            ProPutForDetail d = detail.get(i);
                            if (d.getPutMoney() == null || d.getPutMoney() <= 0) {
                                if (d.getPutMoney() == null) {
                                    d.setPutMoney(0.0);
                                }
                                if (p != null && p.getId().equals(id)) {
                                    d.setPutDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                                    Double putMoney = getPutMoney(storage);
                                    d.setPutMoney(putMoney);
                                    proPutDetailService.updatePutMoney(d);
                                }
                                break;
                            }
                        }
                    }
                } else {
                    ProPutForDetail d = detail.get(0);
                    d.setPutDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                    Double putMoney = getPutMoney(storage);
                    d.setPutMoney(putMoney);
                    proPutDetailService.updatePutMoney(d);
                }
            }

        }
        return true;
    }

    @Override
    public boolean backPutDetail(PutStorage storage) {
        try {
            List<ProPutForDetail> detail = proPutDetailService.getProDetailByPutId(storage.getId());
            if (detail != null && detail.isEmpty()) {
                Procurement procurement = procurementService.getProcurementById(storage.getProId());
                if (procurement != null) {
                    ProDetail pd = proDetailService.getDetailByCompany(storage.getCompany().getId(), storage.getPutDate().substring(0, 4), proDetailService.getCompanyTypeByPro(procurement));
                    detail = proPutDetailService.getProDetailBYProId(procurement.getId(), pd.getId());
                }
            }
            if (detail != null) {
                for (int i = 0; i < detail.size(); i++) {
                    ProPutForDetail d = detail.get(i);
                    if (d.getPutMoney() == null) {
                        d.setPutMoney(0.0);
                    }
                    d.setPutDate("");
                    Double putMoney = getPutMoney(storage);
                    d.setPutMoney(d.getPutMoney() - putMoney);
                    proPutDetailService.updatePutMoney(d);
                    break;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    private String newSeries() {
        String series = getNowPutSerial(DateUtil.getDate());
        return NumberFormat.createSeries(series);
    }

    @Override
    public PutStorage approve(PutStorage storage, Staff s) throws Exception {
        int tempState = 0;  //默认反审核
        if (storage.getApproveType() == 0) {

            PutStorage ps = getStorageById(storage.getId());
            if (ps.getApproveType() == 1) {
                throw new Exception("该单据已审核入库，请勿重复操作");
            }

            //是否固定资产入库
            boolean isFixed = false;
            SystemConfig config = systemConfigService.queryByCoding(Constant.FIXED_PROJECT_CODING);
            if (!Objects.isNull(config) && StringUtils.equals(config.getValue(), storage.getProject().getId())) {
                isFixed = true;
            }

            //更新税率、备注、运杂费、入库单号（如果存在）、入库数量、单价、金额
            if (StringUtils.isBlank(storage.getPutSerial())) {
                storage.setPutSerial(newSeries());
            }
            storage.setApproveDate(DateUtil.getDate());
            storage.setApproveType(1);
            storage.setApproveStaff(s.getCoding());
            updateSimple(storage);
            updateMater(storage);

            tempState = 1;
            List<StorageMaterial> sms = new ArrayList<>();
            try {
                sms = putStorageMaterialService.getMaterAllByPutId(ps.getId());
                //更新仓库材料排序时间
                for (StorageMaterial putSm : sms) {
                    storageService.updateOrInsert(storage.getId(), putSm.getMaterial().getId());

                    //更新固定资产
                    if (isFixed) {
                        updateFixedAssets(putSm);
                    }

                }
                approve(storage, tempState, s.getCoding(), DateFormat.getDate());
            } catch (Exception e) {
                e.printStackTrace();
                MDC.put("url", "审核入库单");
                MDC.put("ip", "");
                MDC.put("userName", "");
                MDC.put("userId", "");
                MDC.put("params", "入库单审核失败:" + e.getMessage() + ";入库单id" + storage.getId());
                Logger.getLogger("mylog").error("入库单审核失败:" + e.getMessage());
            }
            //是否项目经理签字确认
            ProPutSign proPutSign = new ProPutSign();
            proPutSign.setPutId(storage.getId());
            proPutSign = signService.queryByParam(proPutSign);
            if (proPutSign != null && sms.size() > 0) {
                storage.setMaterialList(sms);
                //直接生成出库单
                storage.setProject(sms.get(0).getProject());
                genOutMater(storage, proPutSign, s);
            }
//            添加对账单明细
            genPutDetail(storage);
            // if (!WebParam.SYSTEM_IS_DEBUG) {
            weiChartNotify(storage.getProcurement(), storage.getStaff(), "仓库已确认入库", s);
            // }
        } else {      //反审核
            String isOut = isOut(storage.getId());        //查看该入库单是否有出库记录
            if (isOut != null) {
                storage.setApproveType(Constant.STATE_2);       //该订单已出库
                throw new Exception("该单据已出库");
            }
            storage = getStorageById(storage.getId());
            if (storage == null) {
                throw new Exception("入库单不存在");
            }
            storage.setApproveType(0);
            Map<String, Object> param = new HashMap(16);
            param.put("id", storage.getId());
            param.put("state", 0);
            param.put("person", s.getCoding());
            param.put("date", DateFormat.getDate());
            //更新入库单状态，用于后面的反审核入库单调用
            try {
                updateApprove(param);
                approve(storage, 0, s.getCoding(), DateUtil.getDate());
                //回滚对账单
                backPutDetail(storage);
            } catch (Exception e) {
                //库存不足异常时，再恢复入库单状态
                param.put("state", 0);
                param.put("person", s.getCoding());
                updateApprove(param);
                storage.setApproveType(Constant.STATE_3);       //该订单库存不足
                LogManager.getLogger("mylog").error("反审核入库单异常" + e.getLocalizedMessage());
            }
        }
        storage = getStorageById(storage.getId());
        return storage;
    }

    private void updateFixedAssets(StorageMaterial putSm) {
        ProMaterial pm = procurementMaterService.getMatersById(putSm.getProMaterId());
        if (!Objects.isNull(pm)) {
            FixedAssets fa = fixedAssetsService.queryById(pm.getApplyMaterialId());
            if (!Objects.isNull(fa)) {
                fa.setMoney(putSm.getMoneyTax());
                fixedAssetsService.update(fa);
            }
        }
    }

    private int updateSimple(PutStorage storage) {
        return putStorageMapper.updateSimpleInfo(storage);
    }

    /**
     * 入库单审核入库时调用
     *
     * @param storage
     */
    private void updateMater(PutStorage storage) {
        List<StorageMaterial> storageMaters = storage.getMaterialList();
        if (!Objects.isNull(storageMaters)) {
            storageMaters.forEach(item -> {
                putStorageMaterialService.updateMaterMoneyAll(item);
                Material m = item.getMaterial();
                m.setLastPutDate(DateUtil.getDate());
                materialService.updateLastPutDate(m);
            });
        }
    }

    @Override
    public int weiChartNotify(Procurement procurement, Staff staff, String msg, Staff sendStaff) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("title", "入库单状态变更");
        param.put("mTitle", "仓库:" + sendStaff.getName() + " 发送");
        param.put("content", procurement.getPmNumber() + "：" + msg);
        param.put("url", "/managent/getPage?pageName=managerIndex");
        ArrayList<Staff> staffs = new ArrayList<>();
        staffs.add(staff);
        flowNotifyService.msgNotify(staffs, param);
        return 1;
    }

    @Override
    public int delete(String putId, Staff staff) {
        //删除之前先改变入库单状态
        PutStorage put = getStorageById(putId);
        put.setMaterialList(putStorageMaterialService.getMaterAllByPutId(putId));

        Procurement procurement = procurementService.getProcurementById(put.getProId());
        if (procurement != null) {
            weiChartNotify(procurement, put.getStaff(), "入库单被删除", staff);
        }
        Map<String, Object> param = new HashMap(16);
        param.put("id", putId);
        param.put("state", 1);
        param.put("person", "system_auto");
        param.put("date", DateFormat.getDate());
        updateApprove(param);
        //删除对账单数据
        proPutDetailService.deleteByPutId(putId);
        //删除入库数据
        int row = deletePut(putId);
        //删除时无需更新库存，因为反审核时已经把库存退回，未反审核的入库单禁止删除，只需更新采购状态即可
        if (StringUtils.isBlank(put.getApproveDate()) && put.getApproveType() == 0) {
//            更新采购订单状态
            List<StorageMaterial> sms = put.getMaterialList();
            HashMap<String, Procurement> proMap = new HashMap<>();
            sms.forEach(item -> {
                setProMaterial(proMap, item, false);
            });

            proMap.forEach((key, pro) -> {
                updateProState(pro.getMaterial(), pro, 1, 0);
            });
        }
        return row;
    }

    @Override
    public Integer addOtherStorage(PutStorage putStorage, Staff staff) {
        //取出入库材料集合
        List<StorageMaterial> materials = putStorage.getMaterialList();
        if (materials == null || materials.size() <= 0) {
            return -1;
        }
        putStorage.setCreateDate(DateUtil.getProcurementDate());  //设置表单创建时间
        if (putStorage.getPutDate() == null || "".equalsIgnoreCase(putStorage.getPutDate())) {
            putStorage.setPutDate(putStorage.getCreateDate());        //设置入库时间
        }
        putStorage.setPutPerson(staff.getCoding());     //设置入库人员
        putStorage.setPm02608(putStorage.getCreateDate());      //设置跟创建时间一样的列 08
        putStorage.setPm02610(staff.getCoding());       //设置制单人员编号
        putStorage.setId(UUID.randomUUID().toString());     //生成入库单id

//        入库单材料引用
        StorageMaterial putMaterial;

        //初始化订单对象，以便后续的状态更新
        Procurement procurement = new Procurement();

        //设置采购单id
        procurement.setId(putStorage.getProId());
        for (int i = 0; i < materials.size(); i++) {
            putMaterial = materials.get(i);
            if (putMaterial == null) {
                continue;
            }
            putMaterial.setId(UUID.randomUUID().toString());        //生成该入库材料的id
            putMaterial.setStorageId(putStorage.getId());           //设置入库单id
        }
        //        添加入库单到数据库
        return addStorage(putStorage);
    }

    @Override
    public Integer getPutStorageListCount(HashMap<String, Object> params) {
        return putStorageMapper.getPutStorageListCount(params);
    }

    @Override
    public List<PutStorage> getPutStorageByProId(String proId) {
        return putStorageMapper.getPutStorageByProId(proId);
    }

    private void genOutMater(PutStorage storage, ProPutSign putSign, Staff genStaff) {
        MaterOut materOut = new MaterOut();
        materOut.setProject(storage.getProject());
        Project project = projectService.getProjectByid(storage.getProject().getId());
        if (Objects.isNull(project.getoConstruction()) || (project.getoConstruction() != null && "6D09D98A-D462-4707-86B4-D31E98C2156D".equals(project.getoConstruction().getId()))) {
            Company c = new Company();
            c.setId("BB69596C-7C41-4619-AF4A-517A43679C21");
            materOut.setCompany(c);
        } else {
            materOut.setCompany(project.getoConstruction());
        }

        Staff staff = staffService.getStaffById(putSign.getSignStaffId());
        staff.setId(putSign.getSignStaffId());
        materOut.setOutPerson(staff);
        materOut.setSection(staff.getSection());
        materOut.setRemark(storage.getRemark());
        materOut.setStorage(storage.getStorage());
        materOut.setPutId(storage.getId());
        materOut.setOutPersonId(putSign.getSignStaffId());
        List<StorageMaterial> storageMater = storage.getMaterialList();
        MaterOutChild outChild = null;
        LinkedList<MaterOutChild> outChildList = new LinkedList<>();
        for (int i = 0; i < storageMater.size(); i++) {
            outChild = new MaterOutChild();
            StorageMaterial material = storageMater.get(i);
            outChild.setSum(material.getPutSum());
            outChild.setMaterial(material.getMaterial());
            outChild.setTaxPrice(material.getPrice());
            outChild.setTaxMoney(material.getMoney());
            outChild.setPutMaterId(material.getId());
            outChild.setTax(storage.getTax());

            outChildList.add(outChild);
        }
        materOut.setMaterOuts(outChildList);
        materOut.setOutDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        outMaterService.addOutMater(materOut, genStaff, 1);
    }

    private Double getPutMoney(PutStorage storage) {
        Double putMoney = putStorageMaterialService.getPutMoneyByStorageId(storage.getId());
        if (putMoney == null) {
            putMoney = 0.0;
        }
        if (StringUtils.isNotBlank(storage.getSaleMoney()) && Double.valueOf(storage.getSaleMoney()) > 0) {
            //使用入库优惠金额
            return Double.valueOf(storage.getSaleMoney());
        }
        return putMoney;
    }
}
