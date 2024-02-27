package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ApplyMaterial;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.mapper.ApplyMaterialMapper;
import com.yuesheng.pm.model.ProjectMaterial;
import com.yuesheng.pm.service.ApplyMaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-06.
 */
@Service("applyMaterialService")
public class ApplyMaterialServiceImpl implements ApplyMaterialService {
    @Autowired
    ApplyMaterialMapper applyMaterialMapper;

    @Override
    public List<ApplyMaterial> getApplyMaterials(String applyid) {
        return applyMaterialMapper.getApplyMaterials(applyid);
    }

    @Override
    public int updateMaterProSum(String primentId, Double sum) {
        if(primentId != null){
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("major",primentId);
            params.put("sum",sum);
            return applyMaterialMapper.updateMaterial(params);
        }
        return -1;
    }

    @Override
    public Map<String, BigDecimal> getMaterSums(String s) {
        return applyMaterialMapper.getMaterSums(s);
    }

    @Override
    public Integer updatePutSum(Map<String, Object> params) {
        return applyMaterialMapper.updateMaterial(params);
    }

    @Override
    public List<ApplyMaterial> getMaterAllByApply(String applyId) {
        return applyMaterialMapper.getMaterAllByApply(applyId);
    }

    @Override
    public List<ApplyMaterial> getMaterByProid(String materId, String projectId) {
        return applyMaterialMapper.getMaterByProid(materId,projectId);
    }

    @Override
    public int updateRemark(String id, String remark) {
        return applyMaterialMapper.updateRemark(id,remark);
    }

    @Override
    public ApplyMaterial getMaterById(String major) {
        return applyMaterialMapper.getMaterById(major);
    }

    @Override
    public void addMater(ApplyMaterial material) {
        if(StringUtils.isBlank(material.getRemark())){
            material.setRemark("");
        }
        applyMaterialMapper.addMater(material);
    }

    @Override
    public void updateMater(ApplyMaterial am) {
        applyMaterialMapper.updateMater(am);
    }

    @Override
    public Material getMaterialByMaterId(String id) {
        return applyMaterialMapper.getMaterialByMaterId(id);
    }

    @Override
    public List<ApplyMaterial> listByPlanRowId(String planRowId) {
        return applyMaterialMapper.listByPlanRowId(planRowId);
    }

    @Override
    public List<ApplyMaterial> getByApplyId(String applyId) {
        return applyMaterialMapper.getByApplyId(applyId);
    }

    @Override
    public int updateProSum(ApplyMaterial am){
        return applyMaterialMapper.updateProSum(am);
    }

    @Override
    public int deleteByApply(String applyId) {
        return applyMaterialMapper.deleteByApply(applyId);
    }

    @Override
    public List<ProjectMaterial> getProjectMaterial(String projectId) {
        return applyMaterialMapper.getProjectMaterial(projectId);
    }
}
