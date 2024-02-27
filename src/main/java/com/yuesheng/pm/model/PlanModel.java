package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Plan;
import com.yuesheng.pm.entity.ProjectTask;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.List;

/**
 * 申请单数据model.
 */
@Schema(name="申请单数据model")
public class PlanModel {
    /**
     * 申请单对象
     */
    private Plan plan;
    /**
     * 任务分组
     */
    private List<ProjectTask> projectTask;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<ProjectTask> getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(List<ProjectTask> projectTask) {
        this.projectTask = projectTask;
    }
}
