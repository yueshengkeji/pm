package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Performance;

import java.util.List;

/**
 * 绩效考核表(Performance)表服务接口
 *
 * @author xiaosong
 * @since 2023-10-17 09:02:39
 */
public interface PerformanceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Performance queryById(String id);

    Performance getPerByScoreId(String id);

    /**
     * 分页查询
     *
     * @param performance 筛选条件
     * @return 查询结果
     */
    List<Performance> queryByPage(Performance performance);

    /**
     * 新增数据
     *
     * @param performance 实例对象
     * @return 实例对象
     */
    Performance insert(Performance performance);

    /**
     * 修改数据
     *
     * @param performance 实例对象
     * @return 实例对象
     */
    Performance update(Performance performance);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 删除流程时触发
     * @param id
     */
    void deleteFlowHandler(String id);

    /**
     * 更新考勤信息不可编辑
     * @param id
     */
    void updateEditHandler(String id);

    /**
     * 流程驳回触发
     * @param id
     */
    void breakFlowHandler(String id);

}
