package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.Plan;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/19 材料计划单服务接口.
 *
 */
public interface PlanService {
    /**
     * 获取计划单对象
     * @param id 主键id
     * @return 计划单对象
     */
    Plan getPlanById(String id);

    /**
     * 获取项目所有计划单
     * @param projectId 项目id
     * @return
     */
    List<Plan> getPlansByProject(String projectId);

    /**
     * 检索材料计划单
     * @param name 检索字符串
     * @return
     */
    List<Plan> searchPlan(String name);

    /**
     * 获取材料计划单集合
     * @param params type 1=全部单据，0=自己的单据 str 检索字符串（可选）
     *               pageIndex 页码  pageNumber 数据数量 sortName  排序名称
     *               sortOrder 排序类型 ASC(升序) | DESC（降序）
     * @return
     */
    List<Plan> getPlans(Map<String, Object> params, Integer pageNum,Integer pageSize);

    /**
     * 根据条件获取数据条数
     * @param params 参考getPlans()参数
     * @return
     */
    int getPlansCount(Map<String, Object> params);

    /**
     * 添加计划单主表
     * @param plan 计划单对象
     */
    void insert(final Plan plan);

    /**
     * 删除计划单
     * @param id 计划单id
     * @return
     */
    void delete(String id);

    /**
     * 更新计划单信息
     * @param plan 计划单
     * @return
     */
    int update(Plan plan);

    /**
     * 删除计划单
     * @param projectId 项目id
     * @return
     */
    int deleteByProject(String projectId);

    /**
     * 删除计划单和计划单明细
     *
     * @param id    主键
     * @param staff 当前操作人
     * @return
     */
    Map<String, String> delete(String id, Staff staff);

    /**
     * 获取申请单金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getPlanMoneyByProject(String projectId);

    int approve(FlowMessage msg);

    /**
     * 审核计划单
     * @param id 主键
     * @param staff 审核人员
     * @return
     */
    int approve(String id,Staff staff);
}
