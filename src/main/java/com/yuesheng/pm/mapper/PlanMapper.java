package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Plan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/19 材料计划单mapper.
 * @author XiaoSong
 * @date 2016/12/19
 */
@Mapper
public interface PlanMapper {
    /**
     * 获取计划单对象
     *
     * @param id 主键id
     * @return 计划单对象
     */
    Plan getPlanById(@Param("id") String id);

    /**
     * 获取项目所有计划单
     *
     * @param projectId 项目id
     * @return
     */
    List<Plan> getPlansByProject(@Param("projectId") String projectId);

    /**
     * 检索材料计划单
     *
     * @param name 检索字符串
     * @return
     */
    List<Plan> searchPlan(@Param("name") String name);

    /**
     * 获取材料计划单集合
     *
     * @param params type 1=全部单据，0=自己的单据 str 检索字符串（可选）
     *               pageIndex 页码  pageNumber 数据数量 sortName  排序名称
     *               sortOrder 排序类型 ASC(升序) | DESC（降序）
     * @return
     */
    List<Plan> getPlans(Map<String, Object> params);

    /**
     * 获取计划单总条数
     * @param params 参考getPlans()方法参数
     * @return
     */
    int getPlansCount(Map<String, Object> params);
    /**
     * 添加计划单主表
     * @param plan 计划单对象
     */
    void insert(Plan plan);

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
     * 更新计划单审批信息
     * @param plan
     * @return
     */
    int approve(Plan plan);
}
