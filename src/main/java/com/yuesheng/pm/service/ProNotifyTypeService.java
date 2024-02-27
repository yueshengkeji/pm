package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProNotifyType;

import java.util.List;

/**
 * (ProNotifyType)表服务接口
 *
 * @author xiaoSong
 * @since 2022-10-11 15:11:31
 */
public interface ProNotifyTypeService {

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    ProNotifyType queryById(String staffId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProNotifyType> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proNotifyType 实例对象
     * @return 实例对象
     */
    ProNotifyType insert(ProNotifyType proNotifyType);

    /**
     * 修改数据
     *
     * @param proNotifyType 实例对象
     * @return 实例对象
     */
    ProNotifyType update(ProNotifyType proNotifyType);

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 是否成功
     */
    boolean deleteById(String staffId);

}