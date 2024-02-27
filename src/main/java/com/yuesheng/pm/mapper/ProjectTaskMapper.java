package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProjectTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectTaskMapper {
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
