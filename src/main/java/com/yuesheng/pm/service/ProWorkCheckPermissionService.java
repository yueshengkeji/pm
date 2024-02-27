package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProWorkCheckPermission;

import java.util.List;

/**
 * (ProWorkCheckPermission)表服务接口
 *
 * @author xiaoSong
 * @since 2023-03-06 08:51:46
 */
public interface ProWorkCheckPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    List<ProWorkCheckPermission> queryById(String staffId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkCheckPermission> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proWorkCheckPermission 实例对象
     * @return 实例对象
     */
    ProWorkCheckPermission insert(ProWorkCheckPermission proWorkCheckPermission);

    /**
     * 修改数据
     *
     * @param proWorkCheckPermission 实例对象
     * @return 实例对象
     */
    ProWorkCheckPermission update(ProWorkCheckPermission proWorkCheckPermission);

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 是否成功
     */
    boolean deleteById(String staffId);

}