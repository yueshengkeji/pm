package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProZujinYt;

import java.util.List;

/**
 * (ProZujinYt)表服务接口
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
public interface ProZujinYtService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujinYt queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujinYt> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proZujinYt 实例对象
     * @return 实例对象
     */
    ProZujinYt insert(ProZujinYt proZujinYt);

    /**
     * 修改数据
     *
     * @param proZujinYt 实例对象
     * @return 实例对象
     */
    ProZujinYt update(ProZujinYt proZujinYt);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询数据列表
     *
     * @param yt 筛选数据属性
     * @return
     */
    List<ProZujinYt> queryAll(ProZujinYt yt);
}