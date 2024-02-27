package com.yuesheng.pm.entity;

/**
 * @ClassName ExpenseSubject
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 9:53
 */
public class ExpenseSubject extends BaseEntity{

    /**
     * 报销科目
     */
    private Course course;
    /**
     * 报销费用
     */
    private Double money;
    /**
     * 报销人
     */
    private Staff staff;
    /**
     * 备注
     */
    private String remark;
    /**
     * 报销表标记
     */
    private String mark;
    /**
     * 项目
     */
    private String project;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
