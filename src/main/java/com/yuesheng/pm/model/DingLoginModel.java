package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Staff;

/**
 * @ClassName DingLoginModel
 * @Description
 * @Author ssk
 * @Date 2022/8/8 0008 9:11
 */
public class DingLoginModel {
    private Staff staff;
    private String token;
    private String url;

    public DingLoginModel(String url) {
        this.url = url;
    }

    public DingLoginModel(Staff staff, String token) {
        this.staff = staff;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
