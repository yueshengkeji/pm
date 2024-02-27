package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.CollectionNotify;

import java.util.List;

/**
 * (CollectionNotify)表服务接口
 *
 * @author xiaoSong
 * @since 2022-03-25 09:45:18
 */
public interface CollectionNotifyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CollectionNotify queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CollectionNotify> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param collectionNotify 实例对象
     * @return 实例对象
     */
    CollectionNotify insert(CollectionNotify collectionNotify);

    /**
     * 修改数据
     *
     * @param collectionNotify 实例对象
     * @return 实例对象
     */
    CollectionNotify update(CollectionNotify collectionNotify);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询最新通知记录
     *
     * @param query 指定收款单据id
     * @return
     */
    CollectionNotify selectByCollect(CollectionNotify query);
}