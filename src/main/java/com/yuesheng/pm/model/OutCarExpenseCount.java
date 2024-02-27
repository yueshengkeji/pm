package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;

public class OutCarExpenseCount extends BaseEntity {
    private Double inputKm;
    private Double systemKm;
    private String projectId;

    public Double getInputKm() {
        return inputKm;
    }

    public void setInputKm(Double inputKm) {
        this.inputKm = inputKm;
    }

    public Double getSystemKm() {
        return systemKm;
    }

    public void setSystemKm(Double systemKm) {
        this.systemKm = systemKm;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
