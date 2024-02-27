package com.yuesheng.pm.entity;

/**
 * @ClassName EquipmentToRepair 设备送修
 * @Description
 * @Author ssk
 * @Date 2022/10/27 0027 16:40
 */
public class EquipmentToRepair extends BaseEntity {
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 损坏原因
     */
    private String cause;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 设备名
     */
    private String equipmentName;
    /**
     * 序列号
     */
    private String serialNumber;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 备注
     */
    private String remark;
    /**
     * 申报人
     */
    private Staff staffApplicant;
    /**
     * 采购员
     */
    private Staff staffPurchaser;
    /**
     * 快递单号
     */
    private String trackingNumber;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 状态 0=未发货 1=已发货 2=已收货 3=已报废
     */
    private Integer state;
    /**
     * 申请时间
     */
    private String createTime;

    /**
     * 序号
     */
    private Integer index;

    /**
     * 结果通知标签
     */
    private Integer notifyFlag;

    /**
     * 通知时间
     */
    private String notifyTime;

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public Integer getNotifyFlag() {
        return notifyFlag;
    }

    public void setNotifyFlag(Integer notifyFlag) {
        this.notifyFlag = notifyFlag;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Staff getStaffApplicant() {
        return staffApplicant;
    }

    public void setStaffApplicant(Staff staffApplicant) {
        this.staffApplicant = staffApplicant;
    }

    public Staff getStaffPurchaser() {
        return staffPurchaser;
    }

    public void setStaffPurchaser(Staff staffPurchaser) {
        this.staffPurchaser = staffPurchaser;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
