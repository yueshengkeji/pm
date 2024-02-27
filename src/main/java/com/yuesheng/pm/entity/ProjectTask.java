package com.yuesheng.pm.entity;

import java.util.List;

/**
 * 项目任务实体.
 *
 * @author xiaoSong
 * @date 2020-08-05
 */
public class ProjectTask extends BaseEntity {
    private String name;
    private Project project;
    private Double money;
    private String datetime;
    private Staff staff;
    private String taskDatetime;
    private Integer state;

    public String getTaskDatetime() {
        return taskDatetime;
    }

    public void setTaskDatetime(String taskDatetime) {
        this.taskDatetime = taskDatetime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private List<PlanMaterial> materialList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

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

    public List<PlanMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<PlanMaterial> materialList) {
        this.materialList = materialList;
    }
}
