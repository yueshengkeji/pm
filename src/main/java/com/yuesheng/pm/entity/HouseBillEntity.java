package com.yuesheng.pm.entity;

import java.util.List;

public class HouseBillEntity extends BaseEntity {
    /**
     * 合同编号
     */
    private String series;
    /**
     * 单位名称
     */
    private String companyName;
    /**
     * 单元号
     */
    private String mark;
    /**
     * 合同签订日期
     */
    private String contractDate;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 合同总价
     */
    private Double money;
    /**
     * 已收款金额
     */
    private Double inMoney;
    /**
     * 合同登记日期
     */
    private String date;
    /**
     * 登记人
     */
    private Staff staff;
    /**
     * 合同到期日期
     */
    private String endDate;
    /**
     * 登记明细集合
     */
    private List<HouseBillDetail> houseBillDetailList;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getInMoney() {
        return inMoney;
    }

    public void setInMoney(Double inMoney) {
        this.inMoney = inMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<HouseBillDetail> getHouseBillDetailList() {
        return houseBillDetailList;
    }

    public void setHouseBillDetailList(List<HouseBillDetail> houseBillDetailList) {
        this.houseBillDetailList = houseBillDetailList;
    }
}
