package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.Objects;

/**
 * Created by Administrator on 2016-08-02 材料对象.
 *
 * @author XiaoSong
 * @date 2016/08/02
 */
@Schema(name="材料对象")
public class Material extends BaseEntity {
    /**
     * 材料目录id    03
     */
    @Schema(name = "材料目录", hidden = true)
    @Excel(index = true, name = "序号",width = 2048)
    private String folder;
    /**
     * 材料名称       02
     */
    @Excel(name = "材料名称",noBreak = true)
    @Schema(name = "材料名称", required = true)
    private String name;
    /**
     * 品牌        21
     */
    @Excel(name = "品牌", width = 2048)
    @Schema(name = "品牌", required = true)
    private String brand;
    /**
     * 产地       22
     */
    @Excel(name = "产地")
    @Schema(name="产地")
    private String producingArea = "";
    /**
     * 材料属性
     */
    @Schema(name="属性")
    @Excel(name = "属性")
    private String property = "";
    /**
     * 型号
     */
    @Schema(name = "型号", required = true)
    @Excel(name = "型号",noBreak = true)
    private String model;
    /**
     * 计划单价
     */
    @Schema(name="材料计划单价")
    @Excel(name = "材料计划单价")
    private Double planPrice = 0.0;
    /**
     * 成本单价
     */
    @Schema(name="成本单价")
    @Excel(name = "成本单价",width = 3096)
    private Double costPrice;
    /**
     * 询价单价
     */
    @Schema(name="询价单价")
    @Excel(name = "询价单价")
    private Double quotePrice;
    /**
     * 备注
     */
    @Excel(name = "备注")
    @Schema(name="备注信息")
    private String remark = "";
    /**
     * 材料单位对象  {}
     */
    @Schema(name = "材料单位", required = true)
    private Unit unit;
    /**
     * 材料单位名称
     */
    @Schema(name = "单位名称", hidden = true)
    @Excel(name = "单位")
    private String unitName;
    /**
     * 材料编码
     */
    @Schema(name="材料编码")
    @Excel(name = "编码")
    private String coding;
    /**
     * 当前库存数量
     */
    @Schema(name="库存数量")
    @Excel(name = "库存数量",width = 3096)
    private Double storageSum;
    @Schema(name="所有仓库库存总数")
    private Double allStorageSum;
    /**
     * 材料添加人员编码
     */
    @Schema(name="添加职员编码")
    private String staffCoding;
    /**
     *
     */
    @Schema(name="添加日期")
    private String date;
    /**
     * 材料所属文件夹
     */
    @Schema(name="材料目录对象")
    private Folder folderObj;
    /**
     * 平均单价      通过入库单价计算（含税）
     */
    @Schema(name="平均单价")
    private Double avgPrice;

    @Schema(name="出库总数")
    private Double outSum;

    @Schema(name="入库总数")
    private Double putSum;

    @Schema(name="最后入库的税率")
    private Double tax;

    @Excel(name = "最后入库时间",width = 4000)
    @Schema(name="最后入库时间")
    private String lastPutDate;

    @Excel(name = "最后采购单价")
    private Double lastPrice;
    @Excel(name = "材料入库金额合计")
    private Double putMoney;

    public Double getAllStorageSum() {
        return allStorageSum;
    }

    public void setAllStorageSum(Double allStorageSum) {
        this.allStorageSum = allStorageSum;
    }

    public Double getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(Double putMoney) {
        this.putMoney = putMoney;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getLastPutDate() {
        return lastPutDate;
    }

    public void setLastPutDate(String lastPutDate) {
        this.lastPutDate = lastPutDate;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getPutSum() {
        return putSum;
    }

    public void setPutSum(Double putSum) {
        this.putSum = putSum;
    }

    public Double getOutSum() {
        return outSum;
    }

    public void setOutSum(Double outSum) {
        this.outSum = outSum;
    }

    public Double getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(Double quotePrice) {
        this.quotePrice = quotePrice;
    }

    public Folder getFolderObj() {
        return folderObj;
    }

    public void setFolderObj(Folder folderObj) {
        this.folderObj = folderObj;
    }

    public String getStaffCoding() {
        return staffCoding;
    }

    public void setStaffCoding(String staffCoding) {
        this.staffCoding = staffCoding;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getStorageSum() {
        if (Objects.isNull(this.storageSum)) {
            return 0.0;
        }
        return storageSum;
    }

    public void setStorageSum(Double storageSum) {
        this.storageSum = storageSum;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProducingArea() {
        return producingArea;
    }

    public void setProducingArea(String producingArea) {
        this.producingArea = producingArea;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }
}
