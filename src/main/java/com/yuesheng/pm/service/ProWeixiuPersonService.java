package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProWeixiuPerson;

import java.util.List;

/**
 * (ProWeixiuPerson)表服务接口
 *
 * @author xiaoSong
 * @since 2023-02-08 16:06:30
 */
public interface ProWeixiuPersonService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProWeixiuPerson queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWeixiuPerson> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proWeixiuPerson 实例对象
     * @return 实例对象
     */
    ProWeixiuPerson insert(ProWeixiuPerson proWeixiuPerson);

    /**
     * 修改数据
     *
     * @param proWeixiuPerson 实例对象
     * @return 实例对象
     */
    ProWeixiuPerson update(ProWeixiuPerson proWeixiuPerson);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 通过项目名称查询维修负责人信息
     *
     * @param projectName
     * @return
     */
    ProWeixiuPerson getByProjectName(String projectName);

    /**
     * 根据条件，查询维修负责人信息
     *
     * @param pwp
     * @return
     */
    List<ProWeixiuPerson> queryAll(ProWeixiuPerson pwp);
}