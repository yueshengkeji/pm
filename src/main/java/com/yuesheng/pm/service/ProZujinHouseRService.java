package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProZujinHouseR;

import java.util.List;

/**
 * (ProZujinHouseR)表服务接口
 *
 * @author xiaoSong
 * @since 2021-07-12 09:29:58
 */
public interface ProZujinHouseRService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujinHouseR queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujinHouseR> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proZujinHouseR 实例对象
     * @return 实例对象
     */
    ProZujinHouseR insert(ProZujinHouseR proZujinHouseR);

    /**
     * 修改数据
     *
     * @param proZujinHouseR 实例对象
     * @return 实例对象
     */
    ProZujinHouseR update(ProZujinHouseR proZujinHouseR);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<ProZujinHouseR> queryAll(ProZujinHouseR query);

    int deleteByZujinId(Integer zujinId);

    /**
     * 更新状态
     * @param houseR
     * @return
     */
    int updateType(ProZujinHouseR houseR);
}