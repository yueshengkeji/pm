package com.yuesheng.pm.entity;

import java.util.Date;

public class ApplyForCar {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用车时间
     */
    private String useTime;

    /**
     * 用车时间（详细）
     */
    private String useTimeDetailed;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 事由
     */
    private String remark;

    /**
     * 备注
     */
    private String remark2;

    /**
     * 目的地
     */
    private String direction;

    /**
     * 司机
     */
    private String driver;

    /**
     * 司机id
     */
    private String driverId;

    /**
     * 审核状态
     */
    private String checkState;

    /**
     * 出行状态
     */
    private String runState;

    /**
     * 申请人id
     */
    private String applicantId;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 出发时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 出发里程
     */
    private Integer mileageStart;

    /**
     * 到达里程
     */
    private Integer mileageEnd;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 标记id
     */
    private String markId;

    /**
     * 序号
     */
    private int index;

    /**
     * 预计返回时间
     */
    private String returnTime;

    public String getUseTimeDetailed() {
        return useTimeDetailed;
    }

    public void setUseTimeDetailed(String useTimeDetailed) {
        this.useTimeDetailed = useTimeDetailed;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
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

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getMileageStart() {
        return mileageStart;
    }

    public void setMileageStart(Integer mileageStart) {
        this.mileageStart = mileageStart;
    }

    public Integer getMileageEnd() {
        return mileageEnd;
    }

    public void setMileageEnd(Integer mileageEnd) {
        this.mileageEnd = mileageEnd;
    }
}
