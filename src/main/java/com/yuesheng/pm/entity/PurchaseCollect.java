package com.yuesheng.pm.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购合同收票登记        purchase_collect_registration
 * @author ssk
 * @since 2022-1-12
 */
public class PurchaseCollect extends BaseEntity{
    /**
     * 收票编号
     */
    private String collectID;
    /**
     * 收票金额
     */
    private BigDecimal collectMoney;
    /**
     * 收票百分比
     */
    private String percent;
    /**
     * 备注
     */
    private String remark;
    /**
     * 收票日期
     */
    private Date collectDate;
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
     * 账期
     */
    private Integer paymentDays;

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

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getCollectID() {
        return collectID;
    }

    public void setCollectID(String collectID) {
        this.collectID = collectID;
    }

    public BigDecimal getCollectMoney() {
        return collectMoney;
    }

    public void setCollectMoney(BigDecimal collectMoney) {
        this.collectMoney = collectMoney;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
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
