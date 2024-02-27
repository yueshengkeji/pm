package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProApplyDine;

import java.util.List;

/**
 * (ProApplyDine)就餐申请表服务接口
 *
 * @author xiaoSong
 * @since 2022-08-15 16:23:56
 */
public interface ProApplyDineService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProApplyDine queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProApplyDine> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proApplyDine 实例对象
     * @return 实例对象
     */
    ProApplyDine insert(ProApplyDine proApplyDine);

    /**
     * 修改数据
     *
     * @param proApplyDine 实例对象
     * @return 实例对象
     */
    ProApplyDine update(ProApplyDine proApplyDine);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询所有就餐申请
     *
     * @param q
     * @return
     */
    List<ProApplyDine> queryAll(ProApplyDine q);

    /**
     * 转换办文数据为就餐数据
     */
    void parseArticleToDine();

    /**
     * 单据审批通过后置
     *
     * @param id
     * @return
     */
    int approve(String id);
}