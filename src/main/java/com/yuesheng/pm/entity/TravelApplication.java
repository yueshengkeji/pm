package com.yuesheng.pm.entity;

import java.util.Date;

/**
 *出差申请msg
 * @author ssk
 * @since 2022-3-2
 */
public class TravelApplication {

    /**
     * 主键
     */
    private String id;

    /**
     * 出差人
     */
    private String traveller;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门
     */
    private String department;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 合计时长
     */
    private String totalTime;

    /**
     * 出差类型 0 = 市内公务 1 = 外地出差
     */
    private Integer travelType;

    private String travelTypeString;

    /**
     * 交通工具
     */
    private String transportation;

    /**
     * 出差地点
     */
    private String place;

    /**
     * 出差事由
     */
    private String remark;

    /**
     * 申请状态 0 = 未通过 1 = 已通过
     */
    private Integer status;

    private String statusString;

    /**
     * 出差员工id
     */
    private String travellerId;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 序号
     */
    private Integer index;

    /**
     * 其他交通工具
     */
    private String otherTransport;

    /**
     * 交通费
     */
    private Double travelFee;
    /**
     * 住宿费
     */
    private Double stayFee;
    /**
     * 其他费用
     */
    private Double otherFee;
    /**
     * 费用合计
     */
    private Double totalFee;

    public Double getTravelFee() {
        return travelFee;
    }

    public void setTravelFee(Double travelFee) {
        this.travelFee = travelFee;
    }

    public Double getStayFee() {
        return stayFee;
    }

    public void setStayFee(Double stayFee) {
        this.stayFee = stayFee;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getOtherTransport() {
        return otherTransport;
    }

    public void setOtherTransport(String otherTransport) {
        this.otherTransport = otherTransport;
    }

    public String getTravelTypeString() {
        return travelTypeString;
    }

    public void setTravelTypeString(String travelTypeString) {
        this.travelTypeString = travelTypeString;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraveller() {
        return traveller;
    }

    public void setTraveller(String traveller) {
        this.traveller = traveller;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getTravelType() {
        return travelType;
    }

    public void setTravelType(Integer travelType) {
        this.travelType = travelType;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
