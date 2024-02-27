package com.yuesheng.pm.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同收款登记       contract_invoicing_registration
 * @author ssk
 * @since 2021-12-17
 */
public class Collection {

    /**
     * ID
     */
    private Integer ID;

    /**
     * 收款编号
     */
    private String collectID;

    /**
     * 收款金额
     */
    private BigDecimal collectMoney;

    /**
     * 备注
     */
    private String remark;

    /**
     * 百分比
     */
    private String percent;

    /**
     * 合同编号
     */
    private String agreementID;

    /**
     * 收款日期
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date createTime2;

    /**
     * 登记人
     */
    private String registrant;

    /**
     * 登记人id
     */
    private String registrantId;

    /**
     * 预收款日期
     */
    private Date pDate;

    /**
     * 登记状态
     */
    private String status;

    /**
     * 合同金额
     */
    private BigDecimal agreementMoney;

    /**
     * 预收款标记
     */
    private String pMark;

    /**
     * 绑定销售合同标记
     */
    private String mark;

    /**
     * 合同名称
     */
    private String agreementName;

    /**
     * bill标记
     */
    private String billMark;

    /**
     * 原收款金额（作用：收款金额修改日志）
     */
    private BigDecimal collectMoneyPast;

    /**
     * 收款状态 未回款=0，已回款=1
     */
    private Integer statusNumber;

    /**
     * 收款类型 现金=0，商业承兑=1，保理=2，固定资产=3
     */
    private Integer collectType;

    /**
     * 商业承兑到期时间
     */
    private Date tradeAcceptanceDate;

    /**
     * 商业承兑的贴息
     */
    private BigDecimal tradeAcceptanceInterest;

    /**
     * 保理时间
     */
    private Integer factoringTime;

    /**
     * 保理到期时间
     */
    private Date factoringDate;

    /**
     * 固定资产
     */
    private String fixedAssets;

    /**
     * 固定资产处理状况 未处理=0，已处理=1
     */
    private Integer fixedAssetsStatus;

    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Date getFactoringDate() {
        return factoringDate;
    }

    public void setFactoringDate(Date factoringDate) {
        this.factoringDate = factoringDate;
    }

    public Integer getStatusNumber() {
        return statusNumber;
    }

    public void setStatusNumber(Integer statusNumber) {
        this.statusNumber = statusNumber;
    }

    public Integer getCollectType() {
        return collectType;
    }

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    public Date getTradeAcceptanceDate() {
        return tradeAcceptanceDate;
    }

    public void setTradeAcceptanceDate(Date tradeAcceptanceDate) {
        this.tradeAcceptanceDate = tradeAcceptanceDate;
    }

    public BigDecimal getTradeAcceptanceInterest() {
        return tradeAcceptanceInterest;
    }

    public void setTradeAcceptanceInterest(BigDecimal tradeAcceptanceInterest) {
        this.tradeAcceptanceInterest = tradeAcceptanceInterest;
    }

    public Integer getFactoringTime() {
        return factoringTime;
    }

    public void setFactoringTime(Integer factoringTime) {
        this.factoringTime = factoringTime;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public Integer getFixedAssetsStatus() {
        return fixedAssetsStatus;
    }

    public void setFixedAssetsStatus(Integer fixedAssetsStatus) {
        this.fixedAssetsStatus = fixedAssetsStatus;
    }

    public BigDecimal getCollectMoneyPast() {
        return collectMoneyPast;
    }

    public void setCollectMoneyPast(BigDecimal collectMoneyPast) {
        this.collectMoneyPast = collectMoneyPast;
    }

    public String getBillMark() {
        return billMark;
    }

    public void setBillMark(String billMark) {
        this.billMark = billMark;
    }

    public Date getCreateTime2() {
        return createTime2;
    }

    public void setCreateTime2(Date createTime2) {
        this.createTime2 = createTime2;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getpMark() {
        return pMark;
    }

    public void setpMark(String pMark) {
        this.pMark = pMark;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public BigDecimal getAgreementMoney() {
        return agreementMoney;
    }

    public void setAgreementMoney(BigDecimal agreementMoney) {
        this.agreementMoney = agreementMoney;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public String getRegistrantId() {
        return registrantId;
    }

    public void setRegistrantId(String registrantId) {
        this.registrantId = registrantId;
    }
}
