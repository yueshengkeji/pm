package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProPlate;

import java.util.List;

/**
 * (ProPlate)表服务接口
 *
 * @author xiaoSong
 * @since 2021-08-06 15:44:35
 */
public interface ProPlateService {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    ProPlate queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProPlate> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proPlate 实例对象
     * @return 实例对象
     */
    ProPlate insert(ProPlate proPlate);

    /**
     * 修改数据
     *
     * @param proPlate 实例对象
     * @return 实例对象
     */
    ProPlate update(ProPlate proPlate);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    List<ProPlate> queryAll(ProPlate plate);

    Integer deleteByStaffId(String staffId);

    int deleteByPlate(String plate);
}