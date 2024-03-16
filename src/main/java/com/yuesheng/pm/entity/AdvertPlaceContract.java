package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @ClassName advertPlaceContract 广告位合同
 * @Description
 * @Author ssk
 * @Date 2024/3/14 0014 8:28
 */
@JsonIgnoreProperties(value = "handler")
public class AdvertPlaceContract {

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
     * 广告位数量 块
     */
    private Integer number;
    /**
     * 场地编号
     */
    private String placeNum;
    /**
     * 具体位置
     */
    private String placeAddress;
    /**
     * 广告类别
     */
    private String advertType;
    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 截止日期
     */
    private String endDate;
    /**
     * 租金单价 元/块
     */
    private Double price;
    /**
     * 合计租金
     */
    private Double money;
    /**
     * 合计租金 大写
     */
    private String capitalizationMoney;
    /**
     * 设计费
     */
    private Double designPrice;
    /**
     * 电费单价 元/块/月
     */
    private Double perElectricPrice;
    /**
     * 合计电费
     */
    private Double electricMoney;
    /**
     * 合计电费 大写
     */
    private String capitalizationElectricMoney;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 登记人
     */
    private Staff recordStaff;
    /**
     * 更新人
     */
    private Staff updateStaff;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 合同类型：0=租赁合同，2=多经类合同，1=物业合同，9=作废，3=广告租赁合同
     */
    private int type;
    /**
     * 附件
     */
    private String files;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getElectricMoney() {
        return electricMoney;
    }

    public void setElectricMoney(Double electricMoney) {
        this.electricMoney = electricMoney;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Staff getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Staff updateStaff) {
        this.updateStaff = updateStaff;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Staff getRecordStaff() {
        return recordStaff;
    }

    public void setRecordStaff(Staff recordStaff) {
        this.recordStaff = recordStaff;
    }

    public String getId() {
        return id;
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

    public String getPartB() {
        return partB;
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getAdvertType() {
        return advertType;
    }

    public void setAdvertType(String advertType) {
        this.advertType = advertType;
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

    public String getCapitalizationMoney() {
        return capitalizationMoney;
    }

    public void setCapitalizationMoney(String capitalizationMoney) {
        this.capitalizationMoney = capitalizationMoney;
    }

    public Double getDesignPrice() {
        return designPrice;
    }

    public void setDesignPrice(Double designPrice) {
        this.designPrice = designPrice;
    }

    public Double getPerElectricPrice() {
        return perElectricPrice;
    }

    public void setPerElectricPrice(Double perElectricPrice) {
        this.perElectricPrice = perElectricPrice;
    }

    public String getCapitalizationElectricMoney() {
        return capitalizationElectricMoney;
    }

    public void setCapitalizationElectricMoney(String capitalizationElectricMoney) {
        this.capitalizationElectricMoney = capitalizationElectricMoney;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
