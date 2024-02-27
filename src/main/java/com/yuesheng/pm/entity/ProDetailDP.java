package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2019-05-13 采购部使用-付款与到票情况登记表.
 *
 * @author XiaoSong
 * @date 2019/05/13
 */
@Schema(name="到票信息")
public class ProDetailDP extends BaseEntity {
    /**
     * 采购付款明细id
     */
    @Schema(name="采购付款明细id")
    private String proDetailId;
    /**
     * 付款日期
     */
    @Schema(name="付款日期")
    private String payDate;
    /**
     * 到票日期
     */
    @Schema(name="到票日期")
    private String dpDate;
    /**
     * 付款金额，到票金额
     */
    @Schema(name="付款金额")
    private Double payMoney;
    @Schema(name="到票金额")
    private Double dpMoney;
    /**
     * 备注信息
     */
    @Schema(name="备注信息")
    private String remark;
    /**
     * 项目
     */
    @Schema(name="项目")
    private Project project;
    /**
     * 单位
     */
    @Schema(name="单位")
    private Company company;
    /**
     * 入库单编号
     */
    @Schema(name="入库单号")
    private String putNumber;
    /**
     * 审核时间
     */
    @Schema(name="审核日期")
    private String approveDate;
    /**
     * 登记人
     */
    @Schema(name="登记人")
    private Staff staff;
    /**
     * 审核人
     */
    @Schema(name="审核人")
    private Staff approveStaff;
    /**
     * 审核状态：0：未审核，1=已审核，2=作废
     */
    @Schema(name="审核状态：0=未审核，1=已审核，2=作废")
    private byte state;

    public String getProDetailId() {
        return proDetailId;
    }

    public void setProDetailId(String proDetailId) {
        this.proDetailId = proDetailId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getDpDate() {
        return dpDate;
    }

    public void setDpDate(String dpDate) {
        this.dpDate = dpDate;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getDpMoney() {
        return dpMoney;
    }

    public void setDpMoney(Double dpMoney) {
        this.dpMoney = dpMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPutNumber() {
        return putNumber;
    }

    public void setPutNumber(String putNumber) {
        this.putNumber = putNumber;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
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

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}
