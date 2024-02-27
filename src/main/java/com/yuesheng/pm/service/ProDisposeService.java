package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDispose;
import com.yuesheng.pm.model.RequestPageModel;

import java.util.List;

/**
 * 处置单主表(ProDispose)表服务接口
 *
 * @author makejava
 * @since 2020-06-28 10:28:57
 */
public interface ProDisposeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProDispose queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProDispose> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proDispose 实例对象
     * @return 实例对象
     */
    ProDispose insert(ProDispose proDispose);

    /**
     * 修改数据
     *
     * @param proDispose 实例对象
     * @return 实例对象
     */
    ProDispose update(ProDispose proDispose);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询处置单集合
     *
     * @param requestPageModel
     * @return
     */
    List<ProDispose> queryAllByParam(RequestPageModel requestPageModel);
}