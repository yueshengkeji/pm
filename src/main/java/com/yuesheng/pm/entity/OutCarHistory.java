package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * (OutCarHistory)实体类
 *
 * @author xiaosong
 * @since 2023-03-29 14:54:22
 */
@Schema(name="车辆外出登记记录")
public class OutCarHistory extends BaseEntity {
    private static final long serialVersionUID = -81056417299888233L;

    private Staff staff;

    private Section section;

    private String startLatitude;

    private String startLongitude;

    @Excel(name = "出发时间")
    private String startTime;
    @Excel(name = "出发地点")
    private String startAddrName;

    private String startImg;

    private String endLatitude;

    private String endLongitude;

    @Excel(name = "到达时间")
    private String endTime;

    @Excel(name = "到达地点")
    private String endAddrName;

    private String endImg;

    private String datetime;

    @Excel(name = "系统计算里程")
    private Double systemKm;

    @Excel(name = "实际里程")
    private Double inputKm;

    @Excel(name = "停车费",coverFormat = "0=无,1=有")
    private Integer isParkingCost;

    private String parkingCostImg;

    @Excel(name = "事由")
    private String remark;

    private Project project;

    @Excel(name = "过路费")
    private Double toll;
    @Excel(name = "智能识别开始里程")
    private Double startSmartKm;
    @Excel(name = "智能识别截止里程")
    private Double endSmartKm;
    @Excel(name = "车辆类型",coverFormat = "1=新能源,2=油电混动")
    private Integer carType;
    @Excel(name = "是否报销标记")
    private Boolean expenseFlag;

    public Boolean getExpenseFlag() {
        return expenseFlag;
    }

    public void setExpenseFlag(Boolean expenseFlag) {
        this.expenseFlag = expenseFlag;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Double getStartSmartKm() {
        return startSmartKm;
    }

    public void setStartSmartKm(Double startSmartKm) {
        this.startSmartKm = startSmartKm;
    }

    public Double getEndSmartKm() {
        return endSmartKm;
    }

    public void setEndSmartKm(Double endSmartKm) {
        this.endSmartKm = endSmartKm;
    }

    public Double getToll() {
        return toll;
    }

    public void setToll(Double toll) {
        this.toll = toll;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartAddrName() {
        return startAddrName;
    }

    public void setStartAddrName(String startAddrName) {
        this.startAddrName = startAddrName;
    }

    public String getStartImg() {
        return startImg;
    }

    public void setStartImg(String startImg) {
        this.startImg = startImg;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndAddrName() {
        return endAddrName;
    }

    public void setEndAddrName(String endAddrName) {
        this.endAddrName = endAddrName;
    }

    public String getEndImg() {
        return endImg;
    }

    public void setEndImg(String endImg) {
        this.endImg = endImg;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getSystemKm() {
        return systemKm;
    }

    public void setSystemKm(Double systemKm) {
        this.systemKm = systemKm;
    }

    public Double getInputKm() {
        return inputKm;
    }

    public void setInputKm(Double inputKm) {
        this.inputKm = inputKm;
    }

    public Integer getIsParkingCost() {
        return isParkingCost;
    }

    public void setIsParkingCost(Integer isParkingCost) {
        this.isParkingCost = isParkingCost;
    }

    public Integer isParkingCost(){
        return isParkingCost;
    }

    public String getParkingCostImg() {
        return parkingCostImg;
    }

    public void setParkingCostImg(String parkingCostImg) {
        this.parkingCostImg = parkingCostImg;
    }

}

