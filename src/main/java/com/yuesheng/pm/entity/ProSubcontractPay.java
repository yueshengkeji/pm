package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * @ClassName ProSubcontractPay 项目分包付款
 * @Description
 * @Author ssk
 * @Date 2022/9/16 0016 8:58
 */
public class ProSubcontractPay extends BaseEntity{
    /**
     * 合同名称
     */
    @Excel(name = "合同名称")
    private String contractName;
    /**
     * 合同id
     */
    private String contractId;
    /**
     * 付款金额
     */
    @Excel(name = "付款金额")
    private Double payMoney;
    /**
     * 合同总价
     */
    @Excel(name = "合同总价")
    private Double contractMoney;
    /**
     * 项目id
     */
    @Excel(name = "项目")
    private Project project;
    /**
     * 付款事由
     */
    @Excel(name = "事由")
    private String payInfo;
    /**
     * 付款单位名称
     */
    @Excel(name = "付款单位")
    private String companyName;
    /**
     * 付款单位id
     */
    private String companyId;
    /**
     * 付款单位银行
     */
    @Excel(name = "开户行")
    private String companyBlank;
    /**
     * 付款单位银行账号
     */
    @Excel(name = "银行帐号")
    private String companyNumber;
    /**
     * 初审价
     */
    @Excel(name = "初审价")
    private Double earlyMoney;
    /**
     * 财务结算价
     */
    @Excel(name = "财务结算价")
    private Double accountMoney;
    /**
     * 累计付款金额
     */
    private Double sumPayMoney;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 申请付款时间
     */
    @Excel(name = "申请时间")
    private String dateTime;
    /**
     * 合同状态：0=未审核，1=已审核，2=作废
     */
    private int state;
    /**
     * 文件存储路径
     */
    private String filesUrl;

    /**
     * 记录人
     */
    @Excel(name = "申请人")
    private Staff staff;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(Double contractMoney) {
        this.contractMoney = contractMoney;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyBlank() {
        return companyBlank;
    }

    public void setCompanyBlank(String companyBlank) {
        this.companyBlank = companyBlank;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Double getEarlyMoney() {
        return earlyMoney;
    }

    public void setEarlyMoney(Double earlyMoney) {
        this.earlyMoney = earlyMoney;
    }

    public Double getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Double accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Double getSumPayMoney() {
        return sumPayMoney;
    }

    public void setSumPayMoney(Double sumPayMoney) {
        this.sumPayMoney = sumPayMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
