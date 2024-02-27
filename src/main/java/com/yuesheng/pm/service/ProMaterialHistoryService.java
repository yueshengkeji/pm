package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProMaterialHistory;

import java.util.List;

/**
 * 项目耗材记录表(ProMaterialHistory)表服务接口
 *
 * @author xiaoSong
 * @since 2022-06-18 15:02:14
 */
public interface ProMaterialHistoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProMaterialHistory queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProMaterialHistory> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proMaterialHistory 实例对象
     * @return 实例对象
     */
    ProMaterialHistory insert(ProMaterialHistory proMaterialHistory);

    /**
     * 修改数据
     *
     * @param proMaterialHistory 实例对象
     * @return 实例对象
     */
    ProMaterialHistory update(ProMaterialHistory proMaterialHistory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询所有数据
     *
     * @param query
     * @return
     */
    List<ProMaterialHistory> queryAll(ProMaterialHistory query);
}