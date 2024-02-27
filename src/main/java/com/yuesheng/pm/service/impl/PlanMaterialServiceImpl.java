package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Plan;
import com.yuesheng.pm.entity.PlanMaterial;
import com.yuesheng.pm.mapper.PlanMaterialMapper;
import com.yuesheng.pm.model.DateCount;
import com.yuesheng.pm.model.ProjectMaterial;
import com.yuesheng.pm.service.PlanMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 96339 on 2016/12/19.
 */
@Service("planMaterialService")
public class PlanMaterialServiceImpl implements PlanMaterialService {
    @Autowired
    private PlanMaterialMapper planMaterialMapper;

    @Override
    public List<PlanMaterial> getMaterialsByPlan(String planId) {
        return planMaterialMapper.getMaterialsByPlan(planId);
    }

    @Override
    public PlanMaterial getMaterialById(String id) {
        return planMaterialMapper.getMaterialById(id);
    }

    @Override
    public List<PlanMaterial> getMaterByPro(String proId) {
        return planMaterialMapper.getMaterByProId(proId);
    }

    @Override
    public List<PlanMaterial> getMaterByParam(PlanMaterial pm) {
        return planMaterialMapper.getMaterByParam(pm);
    }

    @Override
    public List<PlanMaterial> getMaterForApply(String projectId) {
        return planMaterialMapper.getMaterForApply(projectId);
    }

    @Override
    public void insert(PlanMaterial material) {
        planMaterialMapper.insert(material);
    }

    @Override
    public int update(PlanMaterial material) {
        return planMaterialMapper.update(material);
    }

    @Override
    public int delete(String id) {
        return planMaterialMapper.delete(id);
    }

    @Override
    public int deleteAll(String planId) {
        return planMaterialMapper.deleteAll(planId);
    }

    @Override
    public PlanMaterial getMaterialByOk(String id) {
        return planMaterialMapper.getMaterialByOk(id);
    }

    @Override
    public Material getMaterByMaterId(String id) {
        PageHelper.startPage(1, 1);
        return planMaterialMapper.getMaterByMaterId(id);
    }

    @Override
    public List<PlanMaterial> getMaterialByMaterId(String id) {
        return planMaterialMapper.getMaterialByMaterId(id);
    }

    @Override
    public int insertOk(PlanMaterial item) {
        return planMaterialMapper.insertOk(item);
    }

    @Override
    public Double getPlanMoneyByProject(String projectId) {
        return planMaterialMapper.getPlanMoneyByProject(projectId);
    }

    @Override
    public List<PlanMaterial> getMaterForApplyV2(String projectId, String searchText, Integer applyType) {
        return planMaterialMapper.getMaterForApplyV2(projectId, searchText, applyType);
    }

    @Override
    public int approve(Plan plan) {
        return planMaterialMapper.approve(plan);
    }

    @Override
    public PlanMaterial getApproveMaterByKey(String key) {
        return planMaterialMapper.getApproveMaterByKey(key);
    }

    @Override
    public DateCount getMaterCount(String key) {
        return planMaterialMapper.getMaterCount(key);
    }

    @Override
    public int deleteOk(String key) {
        return planMaterialMapper.deleteOk(key);
    }

    @Override
    public int updateOk(PlanMaterial pm) {
        return planMaterialMapper.updateOk(pm);
    }

    @Override
    public List<ProjectMaterial> getProjectMaterial(String projectId) {
        return planMaterialMapper.getProjectMaterial(projectId);
    }
}
