package com.yuesheng.pm.entity;

/**
 * (ProWeixiuPerson)实体类
 *
 * @author xiaoSong
 * @since 2023-02-08 16:06:30
 */
public class ProWeixiuPerson extends BaseEntity {

    private static final long serialVersionUID = -91086794171493247L;

    private String id;

    private String staffId;

    private String staffName;

    private String projectId;

    private String projectName;

    private String date;

    private String yjDate;

    private String expireDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYjDate() {
        return yjDate;
    }

    public void setYjDate(String yjDate) {
        this.yjDate = yjDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

}