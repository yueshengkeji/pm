package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @ClassName ProZujinEnd 租赁终止合同
 * @Description
 * @Author ssk
 * @Date 2024/3/15 0015 16:09
 */
@JsonIgnoreProperties(value = "handler")
public class ProZujinEnd {

    private String id;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 乙方
     */
    private String partB;
    /**
     * 终止合同
     */
    private ProZujin proZujin;
    /**
     * 楼层
     */
    private String floor;
    /**
     * 铺位号
     */
    private String pwNumber;
    /**
     * 合同编号
     */
    private String contractNum;
    /**
     * 品牌名
     */
    private String brandName;
    /**
     * 终止原因
     */
    private String endReason;
    /**
     * 终止日期
     */
    private String endDate;
    /**
     * 归还房屋日期
     */
    private String returnHouseDate;
    /**
     * 租金物业费水电费其他费用支付日期
     */
    private String otherPayDate;
    /**
     * 违约金
     */
    private Double bzj;
    /**
     * 保证金归还天数
     */
    private int returnDays;
    /**
     * 逾期天数
     */
    private int overdueDay;
    /**
     * 逾期违约金
     */
    private Double overdueDayMoney;
    /**
     * 质保金
     */
    private Double qualityBzj;
    /**
     * 质保时长
     */
    private int qualityTime;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 登记人
     */
    private Staff recordStaff;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 更新人
     */
    private Staff updateStaff;
    /**
     * 合同类型：0=租赁合同，2=多经类合同，1=物业合同，9=作废, 3=广告位租赁合同，4=终止合同
     */
    private int type;
    /**
     * 附件
     */
    private String files;

    public int getQualityTime() {
        return qualityTime;
    }

    public void setQualityTime(int qualityTime) {
        this.qualityTime = qualityTime;
    }

    public ProZujin getProZujin() {
        return proZujin;
    }

    public void setProZujin(ProZujin proZujin) {
        this.proZujin = proZujin;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public String getPartB() {
        return partB;
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getPwNumber() {
        return pwNumber;
    }

    public void setPwNumber(String pwNumber) {
        this.pwNumber = pwNumber;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReturnHouseDate() {
        return returnHouseDate;
    }

    public void setReturnHouseDate(String returnHouseDate) {
        this.returnHouseDate = returnHouseDate;
    }

    public String getOtherPayDate() {
        return otherPayDate;
    }

    public void setOtherPayDate(String otherPayDate) {
        this.otherPayDate = otherPayDate;
    }

    public Double getBzj() {
        return bzj;
    }

    public void setBzj(Double bzj) {
        this.bzj = bzj;
    }

    public int getReturnDays() {
        return returnDays;
    }

    public void setReturnDays(int returnDays) {
        this.returnDays = returnDays;
    }

    public int getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(int overdueDay) {
        this.overdueDay = overdueDay;
    }

    public Double getOverdueDayMoney() {
        return overdueDayMoney;
    }

    public void setOverdueDayMoney(Double overdueDayMoney) {
        this.overdueDayMoney = overdueDayMoney;
    }

    public Double getQualityBzj() {
        return qualityBzj;
    }

    public void setQualityBzj(Double qualityBzj) {
        this.qualityBzj = qualityBzj;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Staff getRecordStaff() {
        return recordStaff;
    }

    public void setRecordStaff(Staff recordStaff) {
        this.recordStaff = recordStaff;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Staff getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Staff updateStaff) {
        this.updateStaff = updateStaff;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
