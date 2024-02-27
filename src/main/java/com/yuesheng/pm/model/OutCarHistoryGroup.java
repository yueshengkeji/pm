package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.entity.Project;

import java.util.List;

public class OutCarHistoryGroup extends BaseEntity {
    private Project project;
    private List<OutCarHistory> outCarHistoryList;
    private Double km;
    private Double systemKm;
    private String startTime;
    private String endTime;
    private Double toll;

    public Double getSystemKm() {
        return systemKm;
    }

    public void setSystemKm(Double systemKm) {
        this.systemKm = systemKm;
    }

    public Double getToll() {
        return toll;
    }

    public void setToll(Double toll) {
        this.toll = toll;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<OutCarHistory> getOutCarHistoryList() {
        return outCarHistoryList;
    }

    public void setOutCarHistoryList(List<OutCarHistory> outCarHistoryList) {
        this.outCarHistoryList = outCarHistoryList;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
