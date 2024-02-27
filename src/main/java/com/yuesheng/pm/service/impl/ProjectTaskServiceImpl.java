package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProjectTask;
import com.yuesheng.pm.mapper.ProjectTaskMapper;
import com.yuesheng.pm.service.ProjectTaskService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {
    @Autowired
    private ProjectTaskMapper projectTaskMapper;

    @Override
    public void insert(ProjectTask task) {
        task.setId(UUID.randomUUID().toString());
        task.setDatetime(DateUtil.getDatetime());
        projectTaskMapper.insert(task);
    }

    @Override
    public void update(ProjectTask task) {
        projectTaskMapper.update(task);
    }

    @Override
    public void delete(String id) {
        projectTaskMapper.delete(id);
    }

    @Override
    public ProjectTask queryById(String id) {
        return projectTaskMapper.queryById(id);
    }

    @Override
    public List<ProjectTask> queryByProject(String projectId) {
        return projectTaskMapper.queryByProject(projectId);
    }
}
