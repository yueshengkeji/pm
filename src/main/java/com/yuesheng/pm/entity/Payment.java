package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by 96339 on 2016/12/27 合同付款单 sdpd064  和  sdpb053(合同实际付款单主表).
 * @author XiaoSong
 * @date 2016/12/27
 */
@Schema(name="合同付款单")
public class Payment extends BaseEntity {
    /**
     * 实际付款id
     */
    @Schema(hidden = true)
    private String realityId;
    /**
     * 编号      02
     */
    @Schema(name = "付款单编号", required = true)
    @Excel(name = "付款单编号")
    private String series;
    /**
     * 制单时间      06
     */
    @Schema(name = "制单时间")
    private String date;
    /**
     * 申请付款日期  07
     */
    @Schema(name = "申请付款日期", required = true)
    @Excel(name = "申请时间")
    private String payDate;
    /**
     * 审核标志      08（0：否，1：是）
     */
    @Schema(name = "审核标志", required = false)
    @Excel(name = "审核状态",coverFormat = "0=未审核,1=已审核")
    private byte approveStatus;
    /**
     * 审核日期  10
     */
    @Schema(name = "审核日期", required = false)
    @Excel(name = "审核时间")
    private String approveDate;
    /**
     * 备注  11
     */
    @Schema(name = "备注", required = false)
    @Excel(name = "备注")
    private String remark;
    /**
     * 汇率  15
     */
    @Schema(name = "汇率，默认为1", required = false)
    private float rate;
    /**
     * 付款单位      03
     */
    @Schema(name = "付款单位", required = true)
    @Excel(name = "付款单位")
    private Company company;
    /**
     * 制单人         04
     */
    @Schema(name = "制单人", required = false)
    @Excel(name = "申请人")
    private Staff staff;
    /**
     * 审核人         09
     */
    @Schema(name = "审核人", required = false)
    private Staff approveStaff;
    /**
     * 付款性质        13
     */
    @Schema(name = "付款性质", required = true)
    @Excel(name = "付款性质")
    private PaymentType paymentType;
    /**
     * 付款明细  付款合同集合
     */
    @Schema(name = "付款明细列表", required = true)
    private List<PaymentDetail> details;
    /**
     * 付款金额sum
     */
    @Schema(name = "付款金额合计", required = true)
    @Excel(name = "付款金额")
    private Double moneys;

    @Schema(name = "当前审核步骤名称")
    private String courseName;

    @Schema(name = "打印时间")
    private String printDate;

    @Schema(name = "默认付款账户")
    private String payBankNumber;
    @Schema(name = "默认付款开户行")
    private String payOpenAccount;

    public String getPayBankNumber() {
        return payBankNumber;
    }

    public void setPayBankNumber(String payBankNumber) {
        this.payBankNumber = payBankNumber;
    }

    public String getPayOpenAccount() {
        return payOpenAccount;
    }

    public void setPayOpenAccount(String payOpenAccount) {
        this.payOpenAccount = payOpenAccount;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getMoneys() {
        return moneys;
    }

    public void setMoneys(Double moneys) {
        this.moneys = moneys;
    }

    public String getRealityId() {
        return realityId;
    }

    public void setRealityId(String realityId) {
        this.realityId = realityId;
    }

    public List<PaymentDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PaymentDetail> details) {
        this.details = details;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public byte getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(byte approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getApproveStaff() {
        return approveStaff;
    }

    public void setApproveStaff(Staff approveStaff) {
        this.approveStaff = approveStaff;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
