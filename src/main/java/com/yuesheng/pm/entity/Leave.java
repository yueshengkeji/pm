package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * Created by Administrator on 2016-12-28 请假单.
 *
 * @author XiaoSong
 * @date 2016/12/28
 */
public class Leave extends BaseEntity {
    /**
     * 请假单号              02
     */
    @Excel(index = true, name = "序号", width = 1500)
    private String series;
    /**
     * 单据日期（可更改）    05
     */
    private String billsDate;
    /**
     * 请假开始时间          07
     */
    @Excel(name = "开始时间")
    private String startDate;
    /**
     * 请假结束时间          08
     */
    @Excel(name = "截止时间")
    private String endDate;
    /**
     * 请假备注              09
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 制单时间（不可改）    11
     */
    private String date;
    /**
     * 审核时间              14
     */
    private String approveDate;
    /**
     * 1.请假类型     06
     * （0:病假
     * 1:事假
     * 2:婚假
     * 3:年休假
     * 4:丧假
     * 5:产假
     * 6:其他）
     */
    @Excel(name = "类型", coverFormat = "0=病假,1=事假,2=婚假,3=年休假,4=丧假,5=产假,6=其他", width = 1500)
    private int type;
    /**
     * 审核标志（0：否，1：是）12
     */
    private byte approveStatus;
    /**
     * 请假天数 15
     */
    @Excel(name = "请假天数", width = 2500)
    private Double leaveNumber;
    /**
     * 当月请假总天数
     */
    private Double leaveSum;
    /**
     * 请假人 03
     */
    @Excel(name = "请假人", width = 2000)
    private Staff staff;

    /**
     * 2.审批人 13
     */
    private Staff approveStaff;
    /**
     * 请假小时数
     */
    @Excel(name = "请假小时", width = 2500)
    private Double leaveHouse;

    public Double getLeaveHouse() {
        return leaveHouse;
    }

    public void setLeaveHouse(Double leaveHouse) {
        this.leaveHouse = leaveHouse;
    }

    public Double getLeaveNumber() {
        return leaveNumber;
    }

    public void setLeaveNumber(Double leaveNumber) {
        this.leaveNumber = leaveNumber;
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

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getBillsDate() {
        return billsDate;
    }

    public void setBillsDate(String billsDate) {
        this.billsDate = billsDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(byte approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Double getLeaveSum() {
        return leaveSum;
    }

    public void setLeaveSum(Double leaveSum) {
        this.leaveSum = leaveSum;
    }
}
