package com.yuesheng.pm.entity;


/**
 * Created by 96339 on 2016/12/26 报销对象附表，报销金额详情对象.
 * @author XiaoSong
 * @date 2016/12/26
 */
public class DeclareDetail extends BaseEntity {
    /**
    报销单主对象  02
     */
    private Declare declare;
    /**
    报销金额    05
     */
    private Double money;
    /**
    费用摘要    08
     */
    private String remark;
    /**
    科目对象    04
     */
    private Course subject;
    /**
    报销人     07
     */
    private Staff staff;
    private String declareId;


    public Declare getDeclare() {
        return declare;
    }

    public void setDeclare(Declare declare) {
        this.declare = declare;
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

    public Course getSubject() {
        return subject;
    }

    public void setSubject(Course subject) {
        this.subject = subject;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void setDeclareId(String declareId) {
        this.declareId = declareId;
    }

    public String getDeclareId() {
        return declareId;
    }
}
