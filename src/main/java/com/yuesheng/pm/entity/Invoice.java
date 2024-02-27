package com.yuesheng.pm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同开票登记     contract_invoicing_registration
 * @author ssk
 * @since 2021-12-17
 */
public class Invoice extends BaseEntity{
    /**
     * ID
     */
    private String ID;

    /**
     * 开票编号
     */
    private String invoiceID;

    /**
     * 开票金额
     */
    private BigDecimal invoiceMoney;

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
     * 开票日期
     */
    private Date createTime;

    /**
     * 登记人
     */
    private String registrant;

    /**
     * 登记人id
     */
    private String registrantId;

    /**
     * 账期
     */
    private Integer paymentDays;

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
    private Integer mark;

    /**
     * 合同名称
     */
    private String agreementName;

    /**
     * 单位名称
     */
    private String company;

    /**
     * 序号
     */
    private Integer index;

    /**
     * 预回款时间
     */
    private Date returnDate;

    /**
     * 开票状态
     */
    private String status;

    /**
     * 税率
     */
    private String tax;

    /**
     * bill标记
     */
    private String billMark;

    /**
     * 项目基地
     */
    private String projectBase;

    /**
     * 单位地址
     */
    private String companyAddress;

    /**
     * 开票状态(int)未审核=0，已审核=1
     */
    private Integer statusNumber;

    /**
     * 开票文件（13%税率）
     */
    private List<InvoiceFile> invoiceFiles;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 跨区涉税经办人姓名
     */
    private String operatorName;

    /**
     * 联系方式
     */
    private String operatorPhone;

    /**
     * 身份证
     */
    private String operatorIDNumber;

    /**
     * 甲方需求
     */
    private String partyANeeds;

    /**
     * 开票内容
     */
    private String invoiceContent;

    /**
     * 当前已收款金额
     */
    private String collectedMoney;

    /**
     * 开票单位
     */
    private String invoiceCompany;

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    public String getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(String collectedMoney) {
        this.collectedMoney = collectedMoney;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getOperatorIDNumber() {
        return operatorIDNumber;
    }

    public void setOperatorIDNumber(String operatorIDNumber) {
        this.operatorIDNumber = operatorIDNumber;
    }

    public String getPartyANeeds() {
        return partyANeeds;
    }

    public void setPartyANeeds(String partyANeeds) {
        this.partyANeeds = partyANeeds;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public List<InvoiceFile> getInvoiceFiles() {
        return invoiceFiles;
    }

    public void setInvoiceFiles(List<InvoiceFile> invoiceFiles) {
        this.invoiceFiles = invoiceFiles;
    }

    public Integer getStatusNumber() {
        return statusNumber;
    }

    public void setStatusNumber(Integer statusNumber) {
        this.statusNumber = statusNumber;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getProjectBase() {
        return projectBase;
    }

    public void setProjectBase(String projectBase) {
        this.projectBase = projectBase;
    }

    public String getBillMark() {
        return billMark;
    }

    public void setBillMark(String billMark) {
        this.billMark = billMark;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getpMark() {
        return pMark;
    }

    public void setpMark(String pMark) {
        this.pMark = pMark;
    }

    public BigDecimal getAgreementMoney() {
        return agreementMoney;
    }

    public void setAgreementMoney(BigDecimal agreementMoney) {
        this.agreementMoney = agreementMoney;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public BigDecimal getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(BigDecimal invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
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

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceID='" + invoiceID + '\'' +
                ", invoiceMoney=" + invoiceMoney +
                ", remark='" + remark + '\'' +
                ", percent='" + percent + '\'' +
                ", agreementID='" + agreementID + '\'' +
                ", createTime=" + createTime +
                ", registrant='" + registrant + '\'' +
                '}';
    }
}
