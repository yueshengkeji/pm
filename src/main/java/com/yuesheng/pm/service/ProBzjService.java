package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProBzj;

import java.util.List;

/**
 * (ProBzj)表服务接口
 *
 * @author xiaoSong
 * @since 2021-08-31 09:32:20
 */
public interface ProBzjService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProBzj queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProBzj> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proBzj 实例对象
     * @return 实例对象
     */
    ProBzj insert(ProBzj proBzj);

    /**
     * 修改数据
     *
     * @param proBzj 实例对象
     * @return 实例对象
     */
    ProBzj update(ProBzj proBzj);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<ProBzj> queryAll(ProBzj bzj);
}