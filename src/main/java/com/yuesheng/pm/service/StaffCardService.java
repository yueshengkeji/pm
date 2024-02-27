package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.StaffCard;

import java.util.List;

/**
 * (StaffCard)表服务接口
 *
 * @author xiaosong
 * @since 2024-01-09 13:35:52
 */
public interface StaffCardService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffCard queryById(String id);

    /**
     * 分页查询
     *
     * @param staffCard 筛选条件
     * @return 查询结果
     */
    List<StaffCard> queryByPage(StaffCard staffCard);

    /**
     * 新增数据
     *
     * @param staffCard 实例对象
     * @return 实例对象
     */
    StaffCard insert(StaffCard staffCard);

    /**
     * 修改数据
     *
     * @param staffCard 实例对象
     * @return 实例对象
     */
    StaffCard update(StaffCard staffCard);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询名片信息
     * @param staffId 员工id
     * @return
     */
    StaffCard queryByStaff(String staffId);

    List<StaffCard> queryAllByStaff(String staffId);
}
