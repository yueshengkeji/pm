package com.yuesheng.pm.entity;

import java.util.List;

/**
 * Created by 96339 on 2016/12/26 费用报销主对象.
 *
 * @author XiaoSong
 * @date 2016/12/26
 */
public class Declare extends BaseEntity {
    /**
     * 进度日期        04
     */
    private String planDate;
    /**
     * 费用累计        06
     */
    private Double money;
    /**
     * 审核标志0:否，1:是        07
     */
    private byte status;
    /**
     * 备注              08
     */
    private String remark;
    /**
     * 制单时间         09和10
     */
    private String date;
    /**
     * 制单人对象       根据15列人员id获取
     */
    private Staff staff;
    /**
     * 单据编号        18
     */
    private String code;
    /**
     * 审核人对象       19人员编号
     */
    private Staff approveStaff;
    /**
     * 审核时间        20
     */
    private String approveDate;
    /**
     * 报销项目  02
     */
    private Project project;
    /**
     * 报销的费用科目集合
     */
    private List<DeclareDetail> details;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<DeclareDetail> getDetails() {
        return details;
    }

    public void setDetails(List<DeclareDetail> details) {
        this.details = details;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Staff getApproveStaff() {
        return approveStaff;
    }

    public void setApproveStaff(Staff approveStaff) {
        this.approveStaff = approveStaff;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }
}
