package com.yuesheng.pm.entity;


import com.yuesheng.pm.annotation.Excel;

import java.util.List;

/**
 * 绩效考核表(Performance)实体类
 *
 * @author xiaosong
 * @since 2023-10-17 09:02:39
 */
public class Performance extends BaseEntity {
    private static final long serialVersionUID = 958085343908157171L;
    /**
     * 主键
     */
    @Excel(index = true,name = "序号",width = 3092)
    private String id;
    /**
     * 工作内容id
     */
    private String logId;
    /**
     * 完成标记
     */
    private Integer flag;

    private Double weight;
    /**
     * 综合评分
     */
    private Double score;
    /**
     * sign_img
     */
    @Excel(name = "备注")
    private String remark;

    private String signStaffId;
    @Excel(name = "被考核人签字")
    private String signImg;
    /**
     * 提包人id
     */
    private String staffId;
    /**
     * 审核人id
     */
    private String approveId;

    /**
     * 提交时间
     */
    private String datetime;
    /**
     * 考核年-月份
     */
    private String month;
    private Staff staff;
    private String note;
    @Excel(name = "考核分数",width = 3092)
    private Double scoreSum;
    private Double logScore;
    private Section section;
    private List<ProWorkLog> workLogs;
    private Staff sign;
    private int tempScoreFlag;//临时判断是否为打分审批标识，未置入数据库

    public int getTempScoreFlag() {
        return tempScoreFlag;
    }

    public void setTempScoreFlag(int tempScoreFlag) {
        this.tempScoreFlag = tempScoreFlag;
    }

    public Staff getSign() {
        return sign;
    }

    public void setSign(Staff sign) {
        this.sign = sign;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Double getLogScore() {
        return logScore;
    }

    public void setLogScore(Double logScore) {
        this.logScore = logScore;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(Double scoreSum) {
        this.scoreSum = scoreSum;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<ProWorkLog> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<ProWorkLog> workLogs) {
        this.workLogs = workLogs;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSignStaffId() {
        return signStaffId;
    }

    public void setSignStaffId(String signStaffId) {
        this.signStaffId = signStaffId;
    }

    public String getSignImg() {
        return signImg;
    }

    public void setSignImg(String signImg) {
        this.signImg = signImg;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

}

