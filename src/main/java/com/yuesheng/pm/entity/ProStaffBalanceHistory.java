package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import com.sun.istack.NotNull;

import java.io.Serializable;

/**
 * (ProStaffBalanceHistory)实体类
 *
 * @author xiaoSong
 * @since 2022-05-20 14:28:22
 */
public class ProStaffBalanceHistory implements Serializable {
    private static final long serialVersionUID = -83379491064292053L;

    private Integer id;
    /**
     * 职员对象
     */
    @NotNull
    @Excel(name = "员工姓名", width = 3000)
    private Staff staff;
    /**
     * 本次变化金额
     */
    @NotNull
    @Excel(name = "金额", width = 1500)
    private Double money;
    /**
     * 0=增加金额，1=减少金额
     */
    @Excel(name = "类型", width = 1500, coverFormat = "0=充值,1=消费,2=退款")
    private Byte type;
    /**
     * 变化时间
     */
    @Excel(name = "时间", width = 4500)
    private String datetime;
    /**
     * 变化之前的余额
     */
    private Double afterBalance;
    /**
     * 备注信息
     */
    @Excel(name="备注",width = 3000)
    private String remark;
    /**
     * 操作职员
     */
    private Staff operate;

    /**
     * 余额
     */
    @Excel(name = "卡上余额",width = 3000)
    private Double balance;

    /**
     * 部门
     */
    @Excel(name = "部门",width = 4500)
    private Section section;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(Double afterBalance) {
        this.afterBalance = afterBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Staff getOperate() {
        return operate;
    }

    public void setOperate(Staff operate) {
        this.operate = operate;
    }
}