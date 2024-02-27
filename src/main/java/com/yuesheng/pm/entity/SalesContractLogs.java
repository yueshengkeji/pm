package com.yuesheng.pm.entity;

import java.util.Date;

/**
 * @ClassName SalesContractLogs
 * @Description
 * @Author ssk
 * @Date 2022/6/24 0024 10:12
 */
public class SalesContractLogs extends BaseEntity {
    /**
     * 合同编号
     */
    private String agreementID;
    /**
     * 类型 开票=0/收款=1
     */
    private Integer type;
    /**
     * 修改人
     */
    private Staff modifyStaff;
    /**
     * 修改类型 新增=0/删除=1/修改=2
     */
    private Integer modifyType;
    /**
     * 修改信息
     */
    private String modifyMSG;

    /**
     * 创建时间
     */
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Staff getModifyStaff() {
        return modifyStaff;
    }

    public void setModifyStaff(Staff modifyStaff) {
        this.modifyStaff = modifyStaff;
    }

    public Integer getModifyType() {
        return modifyType;
    }

    public void setModifyType(Integer modifyType) {
        this.modifyType = modifyType;
    }

    public String getModifyMSG() {
        return modifyMSG;
    }

    public void setModifyMSG(String modifyMSG) {
        this.modifyMSG = modifyMSG;
    }
}
