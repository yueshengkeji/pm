package com.yuesheng.pm.entity;

/**
 * @ClassName CompanyExtra
 * @Description 公司额外信息 外表 company_to_sdpf003
 * @Author ssk
 * @Date 2022/6/20 0020 9:41
 */
public class CompanyExtra extends BaseEntity {

    /**
     * 管辖区
     */
    private String jurisdiction;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 街道
     */
    private String street;

    /**
     * 证件号
     */
    private String idNumber;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}

