package com.yuesheng.pm.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购合同支付登记   purchase_pay_registration
 * @author ssk
 * @since 2022-1-12
 */
public class PurchasePay extends BaseEntity{

    /**
     * 支付编号
     */
    private String payID;
    /**
     * 支付金额
     */
    private BigDecimal payMoney;
    /**
     * 支付百分比
     */
    private String percent;
    /**
     * 备注
     */
    private String remark;
    /**
     * 支付日期
     */
    private Date payDate;
    /**
     * 预支付日期
     */
    private Date pDate;
    /**
     * 登记人
     */
    private String registrant;
    /**
     * 采购合同标记
     */
    private Integer mark;
    /**
     * 预支付标记
     */
    private String pMark;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 合同编号
     */
    private String agreementID;

    public String getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getpMark() {
        return pMark;
    }

    public void setpMark(String pMark) {
        this.pMark = pMark;
    }
}
