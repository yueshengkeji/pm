package com.yuesheng.pm.entity;

import java.util.Date;

/**
 * @ClassName SalesContractManager
 * @Description 指定销售合同项目经理
 * @Author ssk
 * @Date 2022/6/22 0022 8:25
 */
public class SalesContractManager extends BaseEntity{
    /**
     * 合同编号
     */
    private String agreementID;
    /**
     * 项目经理名字
     */
    private String managerName;
    /**
     * 项目经理id
     */
    private String managerID;
    /**
     * 部门名字
     */
    private String sectionName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 指定人ID
     */
    private String appointManID;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppointManID() {
        return appointManID;
    }

    public void setAppointManID(String appointManID) {
        this.appointManID = appointManID;
    }

    public String getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }
}
