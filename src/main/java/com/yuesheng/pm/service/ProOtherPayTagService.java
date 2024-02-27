package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProOtherPayTag;

import java.util.List;

/**
 * (ProOtherPayTag)表服务接口
 *
 * @author xiaoSong
 * @since 2022-10-19 10:56:34
 */
public interface ProOtherPayTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProOtherPayTag queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProOtherPayTag> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proOtherPayTag 实例对象
     * @return 实例对象
     */
    ProOtherPayTag insert(ProOtherPayTag proOtherPayTag);

    /**
     * 修改数据
     *
     * @param proOtherPayTag 实例对象
     * @return 实例对象
     */
    ProOtherPayTag update(ProOtherPayTag proOtherPayTag);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}