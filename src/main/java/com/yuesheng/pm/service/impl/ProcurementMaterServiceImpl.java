package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProMaterialMapper;
import com.yuesheng.pm.model.CompanyModel;
import com.yuesheng.pm.model.ProMaterReport;
import com.yuesheng.pm.model.ProjectMaterial;
import com.yuesheng.pm.service.ApplyMaterialService;
import com.yuesheng.pm.service.ApplyService;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.ProcurementMaterService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016-08-12.
 * 采购订单材料服务
 */
@Service("procurementMaterService")
@DependsOn("databaseVersionService")
public class ProcurementMaterServiceImpl implements ProcurementMaterService {
    @Autowired
    private ProMaterialMapper proMaterialMapper;  //订单mapepr
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private FlowNotifyService notifyService;


    @Override
    public Integer addMater(List<ProMaterial> applyMaterialList, String proId) {
        return proMaterialMapper.addMaterial(applyMaterialList, proId);
    }

    @Override
    public List<ProMaterial> getProMatersByProId(String id, String discard) {
        return proMaterialMapper.getMaterials(id, discard == null ? "" : discard);
    }

    @Override
    public List<ProMaterial> getNotMatersByProId(String id) {
        return proMaterialMapper.getNotMatersByProId(id);
    }

    @Override
    public void updatePutSum(List<ProMaterial> materials) {
        proMaterialMapper.updatePutSum(materials);
    }

    @Override
    public Map<String, BigDecimal> getCount(String id) {
        return proMaterialMapper.getCount(id);
    }

    @Override
    public ProMaterial getMatersById(String proMaterId) {
        return proMaterialMapper.getMatersById(proMaterId);
    }

    @Override
    public List<ProMaterial> getHistoryPrice(String name, String model, int amount, String start, String end) {
        Map<String, String> params = new HashMap(16);
        params.put("name", name);
        params.put("model", model);
        params.put("amount", amount + "");
        params.put("start", start);
        params.put("end", end);
        return proMaterialMapper.getHistoryPriceV2(params);
    }

    @Override
    public List<ProPutForDetail> getProMoneyByCompany(Map<String, Object> params) {
        return proMaterialMapper.getProMoneyBuCompany(params);
    }

    @Override
    public List<ProMaterial> getProMatersByCompany(HashMap params) {
        return proMaterialMapper.getProMaterByCompany(params);
    }

    @Override
    public void deleteByProId(String id) {
        proMaterialMapper.deleteByProId(id);
    }

    @Override
    public void addMater(ProMaterial material) {
        proMaterialMapper.addMater(material);
    }

    @Override
    public int deletemater(String id) {
        return proMaterialMapper.deleteMaterial(id);
    }

    @Override
    public void updateMater(ProMaterial material) {
        proMaterialMapper.updateMater(material);
    }

    @Override
    public Material getMaterByMaterId(String id) {
        PageHelper.startPage(1,1);
        return proMaterialMapper.getMaterByMaterId(id);
    }

    @Override
    public List<ProjectMaterial> getMaterialBYProMax(Integer size, String start, String end) {
        PageHelper.startPage(1,size);
        return proMaterialMapper.getMaterialByProMax(start, end);
    }

    @Override
    public Map<String, Object> getMaterialProInfo(String name, String model, String start, String end) {
        return proMaterialMapper.getMaterialProInfo(name, model, start, end);
    }

    @Override
    public Double getProMoneyByDate(String companyId, String start, String end) {
        return proMaterialMapper.getProMoneyByDate(companyId, start, end);
    }

    @Override
    public List<CompanyModel> getProMoneyByCompanyMax(String start, String end, Integer size) {
        return proMaterialMapper.getProMoneyByCompanyMax(start, end, size);
    }

    @Override
    public List<ProMaterial> getHistoryPriceV2(String name, String model, String brand, Integer size, String start, String end) {
        Map<String, String> params = new HashMap(16);
        params.put("name", name);
        params.put("model", model);
        params.put("brand", brand);
        params.put("start", start);
        params.put("end", end);
        params.put("size", size+"");
        return proMaterialMapper.getHistoryPriceV2(params);
    }

    @Override
    public List<ProMaterial> getProMaterByApply(String applyMaterialId) {
        return proMaterialMapper.getProMaterByApply(applyMaterialId);
    }

    @Override
    public void addDiscardMater(List<ProMaterial> material, String id) {
        proMaterialMapper.addDiscardMater(material, id);
    }

    @Override
    public List<ProMaterial> getProMatersByProId(String proId) {
        return proMaterialMapper.getMaterialsByProId(proId);
    }

    @Override
    public int addMaterial2(ProMaterial proMaterial, String id) {
        return proMaterialMapper.addMaterial2(proMaterial, id);
    }

    @Override
    public Double getProMoneyByProId(String proId) {
        List<ProMaterial> pmList = getProMatersByProId(proId);
        Double money = 0.0;
        for (int i = 0; i < pmList.size(); i++) {
            ProMaterial pm = pmList.get(i);
            if (pm.getMoneyTax() != null) {
                money += pm.getMoneyTax();
            }
        }
        return money;
    }

    @Override
    public List<ProMaterReport> getMaterialByParam(Map<String, Object> param) {
        return proMaterialMapper.getMaterialByParam(param);
    }

    @Override
    public Map<String,Double> getMaterialByQueryCount(Map<String, Object> param) {
        return proMaterialMapper.getMaterialByQueryCount(param);
    }

    @Override
    public List<ProMaterial> getNoDhList(HashMap<String, String> query) {
        return proMaterialMapper.getNoDhList(query);
    }

    @Override
    public void initDateParse(Integer pageNum){
        PageHelper.startPage(pageNum,500);
        com.github.pagehelper.Page<ProMaterial> p = (com.github.pagehelper.Page) queryAllProMater();
        p.forEach(item->{
            if(!Objects.isNull(item) && !Objects.isNull(item.getDhDate())){
                String[] dhDate = item.getDhDate().split("-");
                if(!Objects.isNull(dhDate) && dhDate.length > 2){
                    String month = convert(dhDate[1]);
                    String day = convert(dhDate[2]);
                    item.setDhDate(dhDate[0]+"-"+month+"-"+day);
                    proMaterialMapper.updateDhDate(item);
                }
            }

        });
        Integer page = p.getPages();
        if(page > pageNum){
            initDateParse(++pageNum);
        }
    }
    private String convert(String str){
        if(str.length() == 1){
            return "0"+str;
        }else{
            return str;
        }
    }

    private List<ProMaterial> queryAllProMater() {
        return proMaterialMapper.queryAllProMater();
    }

    @Override
    public boolean noDhNotify() {
        String date = DateUtil.format(DateUtil.rollDay(new Date(), 3), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        HashMap<String, String> query = new HashMap<>();
        query.put("dhEndDate", date);
        query.put("dhStartDate", DateUtil.format(new Date(),DateUtil.PATTERN_CLASSICAL_SIMPLE));
        List<ProMaterial> materials = getNoDhList(query);
        ArrayList<Staff> notifyStaff = new ArrayList<>();
        HashMap<String, Object> sendMsgMap = new HashMap<>();
        HashMap<String, Staff> cache = new HashMap<>();
        sendMsgMap.put("title", "材料未到货通知");
        StringBuffer projectId = new StringBuffer();
        materials.forEach(item -> {
            ApplyMaterial am = applyMaterialService.getMaterById(item.getMajor());
            projectId.append(item.getProjectId());
            Staff staff = null;
            if (!Objects.isNull(am)) {
                Apply apply = applyService.getApplyById(am.getApplyid());
                if (!Objects.isNull(apply)) {
                    //通知项目经理
                    staff = apply.getStaff();
                    if (!cache.containsKey(staff.getId())) {
                        notifyStaff.add(staff);
                        cache.put(staff.getId(), staff);
                    }
                }
            }
            //通知采购员
            staff = item.getP().getStaff();
            if(!cache.containsKey(staff.getId())){
                notifyStaff.add(staff);
                cache.put(staff.getId(),staff);
            }
            if (Objects.isNull(staff)) {
                sendMsgMap.put("content", item.getMaterial().getName() + "等材料即将逾期未到货，请联系采购员沟通");
            } else {
                sendMsgMap.put("content", item.getMaterial().getName() + "等材料即将逾期未到货，请联系采购员沟通！，当前材料采购员：" + staff.getName());
            }
            sendMsgMap.put("url", "/managent/systemNotify/procurement/noDhList/" + projectId);
            notifyService.msgNotify(notifyStaff, sendMsgMap);
            projectId.delete(0, projectId.length());
        });
        return false;
    }

    @Override
    public Double getProMoneyByProject(String projectId) {
        return proMaterialMapper.getProMoneyByProject(projectId);
    }

    @Override
    public List<ProMaterial> getDestroyList(String proId) {
        return proMaterialMapper.getDestroyList(proId);
    }

    @Override
    public int updatePutSum(ProMaterial item) {
        return proMaterialMapper.updatePutSumV2(item);
    }

    @Override
    public Double getProSumByApplyMaterId(String major) {
        return proMaterialMapper.getProSumByApplyMaterId(major);
    }

    @Override
    public int updatePrice(ProMaterial pm) {
        return proMaterialMapper.updatePrice(pm);
    }

    @Override
    public List<ProjectMaterial> getProjectMaterial(String projectId) {
        return proMaterialMapper.getProjectMaterial(projectId);
    }

    @Override
    public int updateBackSum(ProMaterial material) {
        return proMaterialMapper.updateBackSum(material);
    }
}
