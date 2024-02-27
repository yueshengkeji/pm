package com.yuesheng.pm.entity;

public class HouseBillDetail extends BaseEntity {
    /**
     * 经营资产管理主单据id
     */
    private String houseBillId;
    /**
     * 类型：1=收款，2=开票，3=付款，4=收票
     */
    private byte type;
    /**
     * 金额
     */
    private Double money;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 会计序号
     */
    private String series;
    /**
     * 到期时间
     */
    private String endDate;
    /**
     * 是否审核
     */
    private byte isApprove;
    /**
     * 登记人
     */
    private Staff staff;
    /**
     * 审核人
     */
    private Staff approveStaff;
    /**
     * 登记日期
     */
    private String date;
    /**
     * 收款日期
     */
    private String inDate;
    /**
     * 审核日期
     */
    private String approveDate;

    public String getHouseBillId() {
        return houseBillId;
    }

    public void setHouseBillId(String houseBillId) {
        this.houseBillId = houseBillId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public byte getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(byte isApprove) {
        this.isApprove = isApprove;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }
}
