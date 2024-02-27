package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProWorkCheckShow;

import java.util.List;

/**
 * 可见的考勤人员名单(ProWorkCheckShow)表服务接口
 *
 * @author makejava
 * @since 2020-05-11 15:54:14
 */
public interface ProWorkCheckShowService {

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    ProWorkCheckShow queryById(String staffId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkCheckShow> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proWorkCheckShow 实例对象
     * @return 实例对象
     */
    ProWorkCheckShow insert(ProWorkCheckShow proWorkCheckShow);

    /**
     * 修改数据
     *
     * @param proWorkCheckShow 实例对象
     * @return 实例对象
     */
    ProWorkCheckShow update(ProWorkCheckShow proWorkCheckShow);

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 是否成功
     */
    boolean deleteById(String staffId);

}