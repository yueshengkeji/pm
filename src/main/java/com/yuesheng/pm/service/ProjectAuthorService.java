package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProjectAuthor;

import java.util.List;

/**
 * (ProjectAuthor)表服务接口
 *
 * @author xiaoSong
 * @since 2022-11-28 09:00:56
 */
public interface ProjectAuthorService {

    /**
     * 通过ID查询单条数据
     *
     * @param projectId 主键
     * @return 实例对象
     */
    ProjectAuthor queryById(String projectId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProjectAuthor> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param projectAuthor 实例对象
     * @return 实例对象
     */
    ProjectAuthor insert(ProjectAuthor projectAuthor);

    /**
     * 修改数据
     *
     * @param projectAuthor 实例对象
     * @return 实例对象
     */
    ProjectAuthor update(ProjectAuthor projectAuthor);

    /**
     * 通过主键删除数据
     *
     * @param projectId 主键
     * @return 是否成功
     */
    boolean deleteById(String projectId);

}