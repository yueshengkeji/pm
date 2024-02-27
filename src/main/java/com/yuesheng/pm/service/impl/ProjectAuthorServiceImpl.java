package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProjectAuthor;
import com.yuesheng.pm.mapper.ProjectAuthorMapper;
import com.yuesheng.pm.service.ProjectAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * (ProjectAuthor)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-11-28 09:00:56
 */
@Service("projectAuthorService")
public class ProjectAuthorServiceImpl implements ProjectAuthorService {
    @Autowired
    private ProjectAuthorMapper projectAuthorMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param projectId 主键
     * @return 实例对象
     */
    @Override
    public ProjectAuthor queryById(String projectId) {
        return this.projectAuthorMapper.queryById(projectId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProjectAuthor> queryAllByLimit(int offset, int limit) {
        return this.projectAuthorMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param projectAuthor 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectAuthor insert(ProjectAuthor projectAuthor) {
        ProjectAuthor old = queryById(projectAuthor.getProjectId());
        if (Objects.isNull(old)) {
            this.projectAuthorMapper.insert(projectAuthor);
        } else {
            update(projectAuthor);
        }
        return projectAuthor;
    }

    /**
     * 修改数据
     *
     * @param projectAuthor 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectAuthor update(ProjectAuthor projectAuthor) {
        this.projectAuthorMapper.update(projectAuthor);
        return this.queryById(projectAuthor.getProjectId());
    }

    /**
     * 通过主键删除数据
     *
     * @param projectId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String projectId) {
        return this.projectAuthorMapper.deleteById(projectId) > 0;
    }
}