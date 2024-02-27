package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.exception.StorageException;
import com.yuesheng.pm.mapper.OutMaterMapper;
import com.yuesheng.pm.model.MaterUseHistory;
import com.yuesheng.pm.model.OutMaterData;
import com.yuesheng.pm.model.ProjectMaterial;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.NumberFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 宋正根 on 2016/8/30.
 */
@Service("outMaterService")
public class OutMaterServiceImpl implements OutMaterService {
    @Autowired
    private OutMaterMapper outMaterMapper;
    @Autowired
    private OutMaterChildService outMaterChildService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private BackMaterChildService backService;

    @Override
    public MaterOut getNowOutMater(String staffName, String data) {
        PageHelper.startPage(1,1);
        return outMaterMapper.getNowOutMater(staffName, data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOutMater(MaterOut materOut) {
        if (StringUtils.isBlank(materOut.getOutNumber())) {
            materOut.setOutNumber(newOutNumber());
        }
        //添加出库单主对象到数据库  sdpm020
        outMaterMapper.addOutMater(materOut);
        //添加出库单材料到数据库   sdpm021
        insertOutMater(materOut);
        return 1;
    }

    private String newOutNumber() {
        PageHelper.startPage(1,1);
        String outNumber = outMaterMapper.getMaxOutNumber(DateUtil.getDate());
        outNumber = NumberFormat.createSeries(outNumber);
        return outNumber;
    }

    private void insertOutMater(MaterOut materOut) {
        List<MaterOutChild> materOutChildren = materOut.getMaterOuts();
        if (materOutChildren != null) {
            MaterOutChild child = null;
            for (int i = 0; i < materOutChildren.size(); i++) {
                child = materOutChildren.get(i);
                if (StringUtils.isNotBlank(child.getId())) {
                    MaterOutChild oldChild = outMaterChildService.getOutMaterById(child.getId());
                    if (!Objects.isNull(oldChild)) {
                        //更新出库材料信息，并继续下一次循环
                        outMaterChildService.updateMaterChild(child);
                        continue;
                    }
                }
                //添加新材料
                child.setId(UUID.randomUUID().toString());      //生成材料主键id
                child.setMaterOutId(materOut.getId());                      //设置出库单主表id
                child.setPm02110(i + "");     //设置排序序号
                outMaterChildService.addOutMater(child);            //添加出库单到材料库 sdpm021表中
            }
        }
    }

    @Override
    public List<MaterOut> getOutMaterList(Map<String, Object> params) {
        return outMaterMapper.getOutMaterList(params);
    }

    @Override
    public List<Count> getCountForStaff() {
        PageHelper.startPage(1,1);
        return outMaterMapper.getCountForStaff();
    }

    @Override
    public void updateState(MaterOut materOut) {
        outMaterMapper.updateState(materOut);
    }

    @Override
    public MaterOut getOutMaterById(String outId) {
        return outMaterMapper.getOutMaterById(outId);
    }

    @Override
    public List<MaterOut> getOutMaterByProjectId(String projectId, String searchStr) {
        return outMaterMapper.getOutMaterByProjectId(projectId, searchStr);
    }

    @Override
    public List<MaterOut> getOutMaterByMaterId(String materId, String... start) {
        Map<Object, Object> params = new HashMap<>(16);
        params.put("materId", materId);
        params.put("param", start);
        return outMaterMapper.getOutMaterByMaterId(params);
    }

    @Override
    public Integer updateMaterOut(MaterOut materOut) {
        return outMaterMapper.updateMaterOut(materOut);
    }

    @Override
    @Transactional
    public void deleteOut(String outId) {
        MaterOut mo = getOutMaterById(outId);
        if (!Objects.isNull(mo) && mo.getState() == 0) {
            List<MaterOutChild> children = outMaterChildService.getOutMatersByOutId(outId);
            children.forEach(item -> {
                //查询是否产生退库
                List<BackMaterChild> bmc = backService.getBackMaterByOutMater(item.getId());
                if (bmc.isEmpty()) {
                    outMaterChildService.deleteOutMater(item.getId());
                } else {
                    throw new StorageException(item.getMaterial().getName() + "已经产生退库，无法删除", mo.getStorage().getId());
                }
            });
            outMaterMapper.deleteOut(outId);
        }
    }

    @Override
    @Transactional
    public synchronized void approveOutMater(MaterOut out) throws StorageException {
        List<MaterOutChild> moc = out.getMaterOuts();
        if (Objects.isNull(moc)) {
            moc = outMaterChildService.getOutMatersByOutId(out.getId());
            out.setMaterOuts(moc);
        }
        if (out.getState() == 0) {
            //查询是否产生退库,产生退库的出库单，禁止反审核
            for (MaterOutChild om : moc) {
                List<BackMaterChild> bmc = backService.getBackMaterByOutMater(om.getId());
                if (!bmc.isEmpty()) {
                    throw new StorageException(om.getMaterial().getName() + "材料已经产生退库，无法反审核", out.getStorage().getId());
                }
            }
        }
        for (MaterOutChild item : moc) {
            if (out.getState() == 1) {
                //审核,减少库存信息
                storageService.updateOutMaterial(item, out.getStorage().getId(), true);
                //记录出库记录
//                OutMaterChildHistory omch = new OutMaterChildHistory();
//                String idStr = outMaterChildService.getMaxHistory();
//                Double id = 1.0;
//                if(StringUtils.isNotBlank(idStr)){
//                    id = (Double.valueOf(id) + 1);
//                }
//                omch.setId(id);
//                omch.setMaterId(item.getMaterial().getId());
//                omch.setDateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
//                omch.setOutMaterChildId(item.getId());
//                omch.setOutSum(item.getSum());
//                omch.setPrice(item.getTaxPrice());
//                omch.setType((byte)20);
//                omch.setMoney(item.getTaxMoney());

//                outMaterChildService.addOutMaterHistory(omch);
            } else {
                //反审核
                storageService.updateOutMaterial(item, out.getStorage().getId(), false);
            }
        }
//        outMaterMapper.approveOut(out);
    }

    @Override
    public int getOutSum(Map<String, Object> params) {
        return outMaterMapper.getOutSum(params);
    }

    @Override
    public int getOutSumByParam(Map<String, Object> params) {

        return outMaterMapper.getOutSumByParam(params);
    }

    @Override
    public List<ProjectMaterial> getOutMaxByProject(String year, String month, Integer size, Integer number) {
        List<ProjectMaterial> psList = new ArrayList<>();
        if (year == null) {
            return psList;
        }
        if (month != null) {
            year += month;
        }
        PageHelper.startPage(number,size,false);
        List<Map<String, Object>> pidNumberMap = outMaterMapper.getOutMaxByProject(year);
        Project p = null;
        ProjectMaterial pm;
        for (Map<String, Object> pn : pidNumberMap) {
            pm = new ProjectMaterial();
            try {
                p = projectService.getProjectByid(pn.get("projectId").toString());
            } catch (Exception e) {
                p = new Project();
                p.setId("");
                p.setName("-");
            }
            pm.setMoney(Constant.bigToDouble(pn.get("outMoney")));
            pm.setBackMoney(Constant.bigToDouble(pn.get("backMoney")));
            pm.setProject(p);
            psList.add(pm);
        }
        return psList;
    }

    @Override
    public List<ProjectMaterial> getOutMaxNumberByProject(String start, String end, Integer number, Integer size) {
        List<Map<String, Object>> pidList = projectService.getMaterialTopTen(start, end, number, size);
        List<ProjectMaterial> pmList = new ArrayList<>();
        Project p;
        ProjectMaterial pm;
        for (Map<String, Object> so : pidList) {
            pm = new ProjectMaterial();
            try {
                p = projectService.getProjectByid(so.get("projectId").toString());
            } catch (Exception e) {
                p = new Project();
                p.setId("");
                p.setName("-");
            }
            pm.setOutSum(Constant.bigToDouble(so.get("outNumber")));
            pm.setBackSum(Constant.bigToDouble(so.get("backNumber")));
            pm.setProject(p);
            pmList.add(pm);
        }
        return pmList;
    }

    @Override
    public List<OutMaterData> getOutMaterialMoney(Map<String, Object> param) {
        return outMaterMapper.getOutMaterialMoney(param);
    }

    @Override
    public List<MaterUseHistory> getOutMaterHistory(Map<String, Object> param) {
        return outMaterChildService.getOutMaterHistory(param);
    }

    @Override
    public Double getOutMaterialMoney(String projectId) {
        return outMaterChildService.getOutMaterMoney(projectId);
    }

    @Override
    @Transactional
    public MaterOut addOutMater(MaterOut materOut, Staff staff, int isApprove) {
        //        设置制单人员
        materOut.setStaff(staff);
//        设置制单时间
        materOut.setDate(DateFormat.getDate());
//        生成出库单id号
        materOut.setId(UUID.randomUUID().toString());
        /*
         * 判断出库单号是否自动生成
         */
        if (materOut.getOutNumber() == null || "".equals(materOut.getOutNumber())) {
            MaterOut outTemp = getNowOutMater(materOut.getStaff().getCoding(), DateFormat.getDate());
            if (outTemp == null) {
                materOut.setOutNumber(genNumber(""));
            } else {
                materOut.setOutNumber(genNumber(outTemp.getOutNumber()));
            }
        }
        //            添加入库单和入库单材料到数据库
        addOutMater(materOut);
        //审核出库单
        if (isApprove == 1) {
            approve(materOut, materOut.getStaff());
        }
        return materOut;
    }

    @Override
    public int approve(MaterOut out, Staff staff) {
        if (out == null) {
            return -1;
        }
        int tempState = out.getState();
        if (out.getOutPerson() == null) {
            out.setOutPerson(staff);
        }

        if (tempState == 0) {        //未审核,审核状态
            //更新出库单信息
            updateMaterOut(out);
            //添加出库单材料到数据库   sdpm021
            insertOutMater(out);
            out.setApproveStaff(staff);
            out.setState(1);
            out.setApproveDate(DateFormat.getDate());
        } else {      //已审核，反审核
            out.setApproveDate("");
            out.setState(0);
            out.setApproveStaff(staff);
        }
        approveOutMater(out);
        updateState(out);
        return 1;
    }

    @Override
    public MaterOut getOutMaterByNumber(String outNumber) {
        PageHelper.startPage(1,1);
        return outMaterMapper.getOutMaterByNumber(outNumber);
    }

    public static String genNumber(String outSerial) {
        int serialLenth = 3;
        int serialCountNumber = 1;
        StringBuffer serialCount = new StringBuffer("");
        String temp = outSerial + "";
        try {
            temp = temp.substring(temp.length() - serialLenth);
            serialCountNumber = Integer.parseInt(temp);
            serialCountNumber++;
            for (int i = 0; i < (serialLenth - ((serialCountNumber + "").length())); i++) {
                serialCount.append("0");
            }
            serialCount.append(serialCountNumber);
            temp = DateFormat.getDateForNumber() + serialCount.toString();
            serialCount.delete(0, serialCount.length());
        } catch (Exception e) {
            //订单号生成失败
            for (int i = 0; i < (serialLenth - ((serialCountNumber + "").length())); i++) {
                serialCount.append("0");
            }
            serialCount.append(serialCountNumber);
            temp = DateFormat.getDateForNumber() + serialCount.toString();
            serialCount.delete(0, serialCount.length());
        }
        return temp;
    }

}
