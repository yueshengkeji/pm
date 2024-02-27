package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * @ClassName PersonalDiningStatistics
 * @Description
 * @Author ssk
 * @Date 2022/7/7 0007 9:27
 */
public class PersonalDiningStatistics extends BaseEntity {

    @Excel(name = "员工姓名", width = 3000)
    private String staffName;
    private String staffId;
    @Excel(name = "中餐|次数", width = 3000)
    private Integer personalCounts;
    @Excel(name = "中餐|金额", width = 3000)
    private Double totalMoney;
    @Excel(name = "部门", width = 4000)
    private Staff staff;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getPersonalCounts() {
        return personalCounts;
    }

    public void setPersonalCounts(Integer personalCounts) {
        this.personalCounts = personalCounts;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
