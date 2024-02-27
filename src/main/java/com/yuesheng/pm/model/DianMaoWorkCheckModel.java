package com.yuesheng.pm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 点卯考勤数据模型
 */
public class DianMaoWorkCheckModel implements Serializable {
    private String name;
    private String eid;
    private List<Map<String, String>> days;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getDays() {
        return days;
    }

    public void setDays(List<Map<String, String>> days) {
        this.days = days;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }
}
