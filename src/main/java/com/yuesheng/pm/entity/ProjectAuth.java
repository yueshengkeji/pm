package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="项目权限")
public class ProjectAuth extends BaseEntity{
    private Project project;
    private Staff staff;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
