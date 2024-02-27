package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProjectTask;

import java.util.List;

/**
 * 项目任务服务接口.
 *
 * @author xiaoSong
 * @date 2020-08-05
 */
public interface ProjectTaskService {
    /**
     * 添加任务对象
     *
     * @param task
     */
    void insert(ProjectTask task);

    /**
     * 更新任务对象
     *
     * @param task
     */
    void update(ProjectTask task);

    /**
     * 删除任务对象
     *
     * @param id
     */
    void delete(String id);

    /**
     * 通过id获取任务对象
     *
     * @param id
     * @return
     */
    ProjectTask queryById(String id);

    /**
     * 通过项目id查询任务集合
     *
     * @param projectId
     * @return
     */
    List<ProjectTask> queryByProject(String projectId);
}
