package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @ClassName PlaceUseContract
 * @Description
 * @Author ssk
 * @Date 2024/3/11 0011 8:26
 */
@JsonIgnoreProperties(value = "handler")
public class PlaceUseContract {
    private static final long serialVersionUid = 616445466042812656L;
    private String id;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 乙方
     */
    private Company partB;
    /**
     * 场地编号
     */
    private String placeNum;
    /**
     * 场地面积
     */
    private Double placeArea;
    /**
     * 场地用途
     */
    private String placeUseFor;
    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 截止日期
     */
    private String endDate;
    /**
     * 场地费用标准(含税)
     */
    private Double price;
    /**
     * 场地费费总额(含税)
     */
    private Double money;
    /**
     * 场地费税率
     */
    private Double taxRate;
    /**
     * 交费结算期 按月
     */
    private int payCycle;
    /**
     * 水费单价
     */
    private Double waterPrice;
    /**
     * 缴电类型 0=甲方电表通知 1=甲方电表预付 2=月付 3=一次性付
     */
    private int electricType;
    /**
     * 电费标准
     */
    private String electricPrice;
    /**
     * 保证金
     */
    private Double bond;

    /**
     * 登记人
     */
    private Staff recordStaff;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 合同类型：0=租赁合同，2=多经类合同，1=物业合同，9=作废
     */
    private int type;

    /**
     * 文件
     */
    private String files;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company getPartB() {
        return partB;
    }

    public void setPartB(Company partB) {
        this.partB = partB;
    }

    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    public Double getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(Double placeArea) {
        this.placeArea = placeArea;
    }

    public String getPlaceUseFor() {
        return placeUseFor;
    }

    public void setPlaceUseFor(String placeUseFor) {
        this.placeUseFor = placeUseFor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public int getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(int payCycle) {
        this.payCycle = payCycle;
    }

    public Double getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(Double waterPrice) {
        this.waterPrice = waterPrice;
    }

    public int getElectricType() {
        return electricType;
    }

    public void setElectricType(int electricType) {
        this.electricType = electricType;
    }

    public String getElectricPrice() {
        return electricPrice;
    }

    public void setElectricPrice(String electricPrice) {
        this.electricPrice = electricPrice;
    }

    public Double getBond() {
        return bond;
    }

    public void setBond(Double bond) {
        this.bond = bond;
    }
}
