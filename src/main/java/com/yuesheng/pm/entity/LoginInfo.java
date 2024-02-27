package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-04 职员登录对象.
 *
 * @author XiaoSong
 * @date 2016/08/04
 */
public class LoginInfo extends BaseEntity {
    /**
     * 职员编号
     */
    private String staffNumber;
    /**
     * 登录用户名
     */
    private String userNamel;
    /**
     * 用户类型 (1.OA用户，2.PM2用户)
     */
    private String type;
    /**
     * 登录密码
     */
    private String passwd;
    /**
     * 职员对象
     */
    private Staff staff;

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getUserNamel() {
        return userNamel;
    }

    public void setUserNamel(String userNamel) {
        this.userNamel = userNamel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
