package com.yuesheng.pm.entity;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Expense
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 9:08
 */
public class Expense extends BaseEntity{

    /**
     * 项目名
     */
    private String project;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 报销人
     */
    private Staff staff;
    /**
     * 总费用
     */
    private Double totalMoney;
    /**
     * 申请时间
     */
    private Date applyDate;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 备注
     */
    private String remark;

    /**
     * 审批状态
     */
    private int status;

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private List<ExpenseFile> expenseFiles;

    private List<ExpenseSubject> expenseSubjects;

    public List<ExpenseFile> getExpenseFiles() {
        return expenseFiles;
    }

    public void setExpenseFiles(List<ExpenseFile> expenseFiles) {
        this.expenseFiles = expenseFiles;
    }

    public List<ExpenseSubject> getExpenseSubjects() {
        return expenseSubjects;
    }

    public void setExpenseSubjects(List<ExpenseSubject> expenseSubjects) {
        this.expenseSubjects = expenseSubjects;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
