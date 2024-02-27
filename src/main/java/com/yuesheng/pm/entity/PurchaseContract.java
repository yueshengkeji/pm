package com.yuesheng.pm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购合同登记   purchase_contract_registration
 * @author ssk
 * @since 2022-1-10
 */
public class PurchaseContract extends BaseEntity {
    /**
     * 项目基地
     */
    private String projectBase;
    /**
     * 合同编号
     */
    private String agreementID;
    /**
     * 合同名称
     */
    private String agreementName;
    /**
     * 合同金额
     */
    private BigDecimal agreementMoney;
    /**
     * 签订日期
     */
    private Date signDate;
    /**
     * 账期
     */
    private Integer paymentDays;
    /**
     * 单位名称
     */
    private String companyName;
    /**
     * 单位地址
     */
    private String companyAddress;
    /**
     * 开户银行
     */
    private String bankName;
    /**
     * 银行账户
     */
    private String bankAccount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 税率
     */
    private String tak;
    /**
     * 已收票金额
     */
    private BigDecimal collectedMoney;
    /**
     * 已支出金额
     */
    private BigDecimal paidMoney;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 登记人
     */
    private String registrant;
    /**
     * 附件路径集合
     */
    private List<PurchaseFiles> files;

    public List<PurchaseFiles> getFiles() {
        return files;
    }

    public void setFiles(List<PurchaseFiles> files) {
        this.files = files;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public BigDecimal getAgreementMoney() {
        return agreementMoney;
    }

    public void setAgreementMoney(BigDecimal agreementMoney) {
        this.agreementMoney = agreementMoney;
    }

    public BigDecimal getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(BigDecimal collectedMoney) {
        this.collectedMoney = collectedMoney;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProjectBase() {
        return projectBase;
    }

    public void setProjectBase(String projectBase) {
        this.projectBase = projectBase;
    }

    public String getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }


    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTak() {
        return tak;
    }

    public void setTak(String tak) {
        this.tak = tak;
    }
}
