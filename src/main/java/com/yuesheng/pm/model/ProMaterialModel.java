package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Material;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="采购订单明细行数据模型")
public class ProMaterialModel {
    @Schema(name="主键")
    private String id;
    @Schema(name="到货日期")
    private String dhDate;
    @Schema(name="申请单明细行id")
    private String major;
    @Schema(name="材料对象")
    private Material material;
    @Schema(name="采购金额（不含税）")
    private Double money;
    @Schema(name="采购金额（含税）")
    private Double moneyTax;
    @Schema(name="采购单价（不含税）")
    private Double price;
    @Schema(name="采购单价（含税）")
    private Double priceTax;
    @Schema(name="项目id")
    private String projectId;
    @Schema(name="备注信息")
    private String remark;
    @Schema(name="采购数量")
    private Double sum;
    @Schema(name="税额")
    private Double taxMoney;
    @Schema(name="如果前端修改了材料，则设置为true")
    private boolean update = false;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDhDate() {
        return dhDate;
    }

    public void setDhDate(String dhDate) {
        this.dhDate = dhDate;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMoneyTax() {
        return moneyTax;
    }

    public void setMoneyTax(Double moneyTax) {
        this.moneyTax = moneyTax;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceTax() {
        return priceTax;
    }

    public void setPriceTax(Double priceTax) {
        this.priceTax = priceTax;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }
}
