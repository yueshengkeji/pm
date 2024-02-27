package com.yuesheng.pm.entity;


import com.yuesheng.pm.annotation.Excel;

import java.util.List;

/**
 * (OutCarExpense)实体类
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:02
 */
public class OutCarExpense extends BaseEntity {
    private static final long serialVersionUID = 448055282518220040L;
    
    private String id;
    
    private String staffId;
    
    private String startDate;
    
    private String endDate;
    
    private Double systemKm;
    
    private Double inputKm;
    
    private String remark;
    
    private String files;
    @Excel(name = "报销日期")
    private String datetime;
    
    private Double money;
    private Staff staff;

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private List<OutCarExpenseDetail> detail;

    private List<OutCarHistory> detailHistory;

    public List<OutCarHistory> getDetailHistory() {
        return detailHistory;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void setDetailHistory(List<OutCarHistory> detailHistory) {
        this.detailHistory = detailHistory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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

    public Double getSystemKm() {
        return systemKm;
    }

    public void setSystemKm(Double systemKm) {
        this.systemKm = systemKm;
    }

    public Double getInputKm() {
        return inputKm;
    }

    public void setInputKm(Double inputKm) {
        this.inputKm = inputKm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<OutCarExpenseDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<OutCarExpenseDetail> detail) {
        this.detail = detail;
    }
}

