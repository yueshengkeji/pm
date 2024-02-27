package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProjectAuthor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProjectAuthor)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-11-28 09:00:56
 */
@Mapper
public interface ProjectAuthorMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param projectId 主键
     * @return 实例对象
     */
    ProjectAuthor queryById(@Param("projectId") String projectId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProjectAuthor> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param projectAuthor 实例对象
     * @return 对象列表
     */
    List<ProjectAuthor> queryAll(ProjectAuthor projectAuthor);

    /**
     * 新增数据
     *
     * @param projectAuthor 实例对象
     * @return 影响行数
     */
    int insert(ProjectAuthor projectAuthor);

    /**
     * 修改数据
     *
     * @param projectAuthor 实例对象
     * @return 影响行数
     */
    int update(ProjectAuthor projectAuthor);

    /**
     * 通过主键删除数据
     *
     * @param projectId 主键
     * @return 影响行数
     */
    int deleteById(String projectId);

}