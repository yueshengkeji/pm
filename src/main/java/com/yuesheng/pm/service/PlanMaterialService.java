package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Plan;
import com.yuesheng.pm.entity.PlanMaterial;
import com.yuesheng.pm.model.DateCount;
import com.yuesheng.pm.model.ProjectMaterial;

import java.util.List;

/**
 * Created by 96339 on 2016/12/19 计划单服务接口.
 */
public interface PlanMaterialService {
    /**
     * 获取计划单材料集合
     *
     * @param planId 计划单id
     * @return
     */
    List<PlanMaterial> getMaterialsByPlan(String planId);

    /**
     * 获取计划单材料对象
     *
     * @param id 计划单子表主键 pm07024
     * @return
     */
    PlanMaterial getMaterialById(String id);

    /**
     * 获取材料集合
     *
     * @param proId 项目id
     * @return
     */
    List<PlanMaterial> getMaterByPro(String proId);

    List<PlanMaterial> getMaterByParam(PlanMaterial pm);

    /**
     * 获取未申请过的材料计划单集合
     *
     * @param projectId 项目id
     * @return
     */
    List<PlanMaterial> getMaterForApply(String projectId);

    /**
     * 添加计划单材料对象
     *
     * @param material 计划单材料对象
     */
    void insert(PlanMaterial material);

    /**
     * 更新材料信息
     *
     * @param material
     * @return
     */
    int update(PlanMaterial material);

    /**
     * 删除单个材料
     *
     * @param id 计划单材料表主键
     * @return
     */
    int delete(String id);

    /**
     * 删除计划单中所有材料
     *
     * @param planId 计划单id
     * @return
     */
    int deleteAll(String planId);

    /**
     * 获取审批通过后的计划单材料行
     *
     * @param id
     * @return
     */
    PlanMaterial getMaterialByOk(String id);

    /**
     * 判断材料是否被计划单引用
     *
     * @param id 材料id
     * @return
     */
    Material getMaterByMaterId(String id);

    /**
     * 获取材料使用信息
     *
     * @param id     材料id
     * @return
     */
    List<PlanMaterial> getMaterialByMaterId(String id);

    int insertOk(PlanMaterial item);

    /**
     * 获取申请单金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getPlanMoneyByProject(String projectId);

    /**
     * 根据项目id,查询所有计划单材料
     *
     * @param projectId  项目id
     * @param searchText 搜索字符
     * @param applyType  0=可申请采购，1=申请采购完成
     * @return
     */
    List<PlanMaterial> getMaterForApplyV2(String projectId, String searchText, Integer applyType);

    /**
     * 更新审批状态
     *
     * @param plan 主单据信息
     * @return
     */
    int approve(Plan plan);

    /**
     * 获取计划单审批过的材料
     *
     * @param key {项目id+任务id+材料id+额外配置参数}
     * @return
     */
    PlanMaterial getApproveMaterByKey(String key);

    /**
     * 查询计划单审批过的材料统计信息
     * @param key {项目id+任务id+材料id+额外配置参数}
     * @return
     */
    DateCount getMaterCount(String key);

    /**
     * 删除已审核的材料信息
     * @param key {项目id+任务id+材料id+额外配置参数}
     * @return
     */
    int deleteOk(String key);

    /**
     * 更新已审批材料信息
     * @param pm
     * @return
     */
    int updateOk(PlanMaterial pm);

    /**
     * 查询项目计划材料集合
     * @param projectId
     * @return
     */
    List<ProjectMaterial> getProjectMaterial(String projectId);
}
