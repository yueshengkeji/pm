package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * 员工钱包(ProStaffBalance)实体类
 *
 * @author xiaoSong
 * @since 2022-05-20 11:16:49
 */
public class ProStaffAccount extends BaseEntity {
    private static final long serialVersionUID = -54826080746636537L;
    
    private String id;

    /**
    * 余额
    */
    @Excel(name = "余额",width = 4000)
    private Double balance;
    /**
    * 最后变化时间
    */
    private String lastDatetime;
    /**
     * 员工信息
     */
    @Excel(name = "员工",width = 4000)
    private Staff staff;

    @Excel(name = "部门",width = 4500)
    private Section section;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLastDatetime() {
        return lastDatetime;
    }

    public void setLastDatetime(String lastDatetime) {
        this.lastDatetime = lastDatetime;
    }

}