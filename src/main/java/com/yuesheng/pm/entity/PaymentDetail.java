package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2016/12/27 合同付款明细表（付款子表）    s.
 *
 * @author XiaoSong
 * @date 2016/12/27
 */
@Schema(name="合同付款单明细")
public class PaymentDetail extends BaseEntity {
    /**
     * 申请时间        03
     */
    @Schema(name = "申请付款时间", required = true)
    private String applyDate;
    /**
     * 批准金额        04
     */
    @Schema(name = "批准金额", required = false)
    private Double ratify = 0.0;
    /**
     * 付款备注        05
     */
    @Schema(name = "付款备注", required = false)
    private String remark = "";
    /**
     * 制单时间        08
     */
    @Schema(name = "制单时间", required = false)
    private String date;
    /**
     * 最后更新时间      09
     */
    @Schema(name = "最后更新时间", required = false)
    private String lastUpdate;
    /**
     * 付款单编号       15
     */
    @Schema(name = "付款单编号", required = false)
    private String series;
    /**
     * 申请付款金额      16
     */
    @Schema(name = "申请付款金额", required = true)
    private Double applyMoney;
    /**
     * 审核标志(主表中已有，目前无用)       17
     */
    @Schema(name = "审核标志", required = false)
    private byte approveStatus;
    /**
     * 审核时间(主表中已有，目前无用)        19
     */
    @Schema(name = "审核时间", required = false)
    private String approveDate;
    /**
     * 该合同付款次数     34
     */
    @Schema(name = "合同付款次数", required = false)
    private int payCount;
    /**
     * 汇率      36
     */
    @Schema(name = "汇率，默认1", required = false)
    private float rate;
    /**
     * 付款单主表       40
     */
    @Schema(name = "申请付款时间", required = false, hidden = true)
    private Payment payment;
    /**
     * 付款性质        32
     */
    @Schema(name = "付款性质", required = true)
    private PaymentType paymentType;
    /**
     * 制单人对象（id获取）       26
     */
    @Schema(name = "制单人", required = false)
    private Staff staff;
    /**
     * 制单人id 26
     */
    @Schema(name = "制单人id", required = false, hidden = true)
    private String staffId;
    /**
     * 审核人对象（编号获取） 18
     */
    @Schema(name = "审核人", required = false)
    private Staff approveStaff;
    /**
     * 最后修改人对象
     */
    @Schema(name = "最后修改人（批准付款人）", required = false)
    private Staff lastUpdateStaff;
    /**
     * 付款单位（目前无用，主表中已有）        13
     */
    @Schema(name = "付款单位", required = false)
    private Company company;
    /**
     * 合同对象 01
     */
    @Schema(name = "合同对象", required = true)
    private Contract contract;
    /**
     * 合同id 01
     */
    @Schema(name = "合同id", required = false, hidden = true)
    private String contractId;
    /**
     * 项目id        33
     */
    @Schema(hidden = true)
    private String projectId;
    /**
     * 已付款金额    25
     */
    @Schema(name = "已付款金额", required = false)
    private Double yMoney;

    @Schema(name = "审批意见")
    private String approveContent;

    public String getApproveContent() {
        return approveContent;
    }

    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Double getyMoney() {
        return yMoney;
    }

    public void setyMoney(Double yMoney) {
        this.yMoney = yMoney;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Double getRatify() {
        return ratify;
    }

    public void setRatify(Double ratify) {
        this.ratify = ratify;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(Double applyMoney) {
        this.applyMoney = applyMoney;
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

    public int getPayCount() {
        return payCount;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
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

    public Staff getLastUpdateStaff() {
        return lastUpdateStaff;
    }

    public void setLastUpdateStaff(Staff lastUpdateStaff) {
        this.lastUpdateStaff = lastUpdateStaff;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
