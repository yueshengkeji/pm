package com.yuesheng.pm.entity;


/**
 * (StaffCard)实体类
 *
 * @author xiaosong
 * @since 2024-01-09 13:35:52
 */
public class StaffCard extends BaseEntity {
    private static final long serialVersionUID = -64079718726591932L;

    
    private String name;
    
    private String phone;
    
    private String tel;
    
    private String tel2;
    
    private String tel3;
    
    private String address;
    
    private String phone2;
    
    private String log;
    
    private String email;
    
    private String fax;
    
    private String company;
    
    private String emailNumber;

    private Staff staff;

    private String duty;

    private String remark;

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmailNumber() {
        return emailNumber;
    }

    public void setEmailNumber(String emailNumber) {
        this.emailNumber = emailNumber;
    }

}

