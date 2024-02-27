package com.yuesheng.pm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售合同登记    sales_contract_registration
 * @author ssk
 * @since 2021-12-17
 */
public class SalesContract extends BaseEntity{

    private String contractId;
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
     * 单位名称
     */
    private String companyName;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 单位地址
     */
    private String companyAddress;

    /**
     * 联系人
     */
    private String contactMan;

    /**
     * 单位联系电话
     */
    private String phone;

    /**
     * 管辖区
     */
    private String jurisdiction;

    /**
     * 街道
     */
    private String street;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登记人
     */
    private String registrant;

    /**
     * 登记人id
     */
    private String registrantId;

    /**
     * 登记日期
     */
    private Date createTime;

    /**
     * 已开票金额
     */
    private BigDecimal invoicedMoney;

    /**
     * 已收款金额
     */
    private BigDecimal collectedMoney;

    /**
     * 账期
     */
    private Integer paymentDays;

    /**
     * 项目基地
     */
    private String projectBase;

    /**
     * 质保到期时间
     */
    private Date pDate;

    /**
     * 实际完工时间
     */
    private Date actualDate;

    /**
     * 质保金
     */
    private BigDecimal retentionMoney;

    /**
     * 质保金百分比
     */
    private String retentionPercent;

    /**
     * 税率
     */
    private String tax;

    /**
     * 税率切分
     */
    private List<SalesContractTax> taxList;

    /**
     * 附件路径集合
     */
    private List<SalesFiles> files;
    /**
     * 收款过期通知周期：1=每日推送一次，2=每周推送一次，3=每月推送一次，4=每三个月推送一次，5=半年推送一次，6=一年推送一次
     */
    private Integer notifyType;

    /**
     * 修改人Id
     */
    private String modifierId;

    /**
     * 待收款金额
     */
    private BigDecimal toCollectMoney;

    /**
     * 纳税人识别号
     */
    private String taxID;

    /**
     * 履约保证金
     */
    private BigDecimal performanceBond;

    /**
     * 履约保证金比例
     */
    private String performanceBondPercent;

    /**
     * 决算金额
     */
    private BigDecimal finalAccounts;

    /**
     * 审核状态 0=未审核 1=已审核
     */
    private int state;

    /**
     * 开票单位
     */
    private String invoiceCompany;

    /**
     * 地区
     */
    private Region city;

    private Double lng;

    private Double lat;

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Region getCity() {
        return city;
    }

    public void setCity(Region city) {
        this.city = city;
    }

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    public BigDecimal getPerformanceBond() {
        return performanceBond;
    }

    public void setPerformanceBond(BigDecimal performanceBond) {
        this.performanceBond = performanceBond;
    }

    public String getPerformanceBondPercent() {
        return performanceBondPercent;
    }

    public void setPerformanceBondPercent(String performanceBondPercent) {
        this.performanceBondPercent = performanceBondPercent;
    }

    public BigDecimal getFinalAccounts() {
        return finalAccounts;
    }

    public void setFinalAccounts(BigDecimal finalAccounts) {
        this.finalAccounts = finalAccounts;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public List<SalesContractTax> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<SalesContractTax> taxList) {
        this.taxList = taxList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public BigDecimal getToCollectMoney() {
        return toCollectMoney;
    }

    public void setToCollectMoney(BigDecimal toCollectMoney) {
        this.toCollectMoney = toCollectMoney;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Integer getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    public List<SalesFiles> getFiles() {
        return files;
    }

    public void setFiles(List<SalesFiles> files) {
        this.files = files;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public BigDecimal getRetentionMoney() {
        return retentionMoney;
    }

    public void setRetentionMoney(BigDecimal retentionMoney) {
        this.retentionMoney = retentionMoney;
    }

    public String getRetentionPercent() {
        return retentionPercent;
    }

    public void setRetentionPercent(String retentionPercent) {
        this.retentionPercent = retentionPercent;
    }

    public String getProjectBase() {
        return projectBase;
    }

    public void setProjectBase(String projectBase) {
        this.projectBase = projectBase;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
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

    public BigDecimal getAgreementMoney() {
        return agreementMoney;
    }

    public void setAgreementMoney(BigDecimal agreementMoney) {
        this.agreementMoney = agreementMoney;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegistrantId() {
        return registrantId;
    }

    public void setRegistrantId(String registrantId) {
        this.registrantId = registrantId;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getInvoicedMoney() {
        return invoicedMoney;
    }

    public void setInvoicedMoney(BigDecimal invoicedMoney) {
        this.invoicedMoney = invoicedMoney;
    }

    public BigDecimal getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(BigDecimal collectedMoney) {
        this.collectedMoney = collectedMoney;
    }

    @Override
    public String toString() {
        return "SalesContract{" +
                "agreementID='" + agreementID + '\'' +
                ", agreementName='" + agreementName + '\'' +
                ", agreementMoney=" + agreementMoney +
                ", signDate=" + signDate +
                ", companyName='" + companyName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", remark='" + remark + '\'' +
                ", registrant='" + registrant + '\'' +
                ", createTime=" + createTime +
                ", invoicedMoney=" + invoicedMoney +
                ", collectedMoney=" + collectedMoney +
                '}';
    }
}
