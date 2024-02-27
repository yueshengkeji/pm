package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDisposeDetail;

import java.util.List;

/**
 * 处置单明细(ProDisposeDetail)表服务接口
 *
 * @author makejava
 * @since 2020-06-28 10:39:51
 */
public interface ProDisposeDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProDisposeDetail queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProDisposeDetail> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proDisposeDetail 实例对象
     * @return 实例对象
     */
    ProDisposeDetail insert(ProDisposeDetail proDisposeDetail);

    /**
     * 修改数据
     *
     * @param proDisposeDetail 实例对象
     * @return 实例对象
     */
    ProDisposeDetail update(ProDisposeDetail proDisposeDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 通过指定属性条件获取数据
     *
     * @param disposeDetail
     * @return
     */
    List<ProDisposeDetail> queryAll(ProDisposeDetail disposeDetail);

}