package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-12 采购订单材料对象 sdpm014.
 *
 * @author XiaoSong
 * @date 2016/08/12
 */
@Schema(name="采购订单材料明细")
public class ProMaterial extends ApplyMaterial {
    /**
     * 订单id           01     服务端赋值
     */
    @Schema(name="采购订单主体id")
    private String proId;
    /**
     * 单价（不含税）    07     客户端 no
     */
    @Excel(name = "不含税单价")
    @Schema(name="不含税单价")
    private Double price;
    /**
     * 金额（不含税）    08     客户端 no
     */
    @Excel(name = "不含税金额")
    @Schema(name="不含税金额")
    private Double money;
    /**
     * 单价（含税）      14    客户端  ok
     */
    @Schema(name="含税单价")
    @Excel(name = "单价", width = 4096)
    private Double priceTax;
    /**
     * 金额（含税）   15    客户端  ok
     */
    @Schema(name="含税金额")
    @Excel(name = "金额", width = 3096)
    private Double moneyTax;
    /**
     * 税额          16    客户端  no
     */
    @Schema(name="税额")
    @Excel(name = "税额", width = 2048)
    private Double taxMoney;
    /**
     * 入库数量      11  客户端
     */
    @Excel(name = "已入库数量", width = 2048)
    @Schema(name="已入库数量")
    private Double inSum = 0.00;
    /**
     * 入库时间      12  客户端
     */
    @Schema(name="最后入库时间")
    private String inDate = "";
    /**
     * 项目id        22    客户端 ok
     */
    @Schema(name="项目id")
    private String projectId;
    /**
     * 到货日期      10      客户端 ok
     */
    @Schema(name="到货日期")
    @Excel(name = "到货日期")
    private String dhDate;
    /**
     * 暂时不知作用
     */
    private Double pm01405 = 1.000000;
    /**
     * 材料对象
     */
    @Schema(name="材料对象")
    private Material material;
    /**
     * 成本单价（申请单价）
     */
    @Schema(name="成本单价（申请单价）")
    @Excel(name = "成本单价", width = 3096)
    private Double applyPrice = 0.0;

    @Schema(name="申请单行id")
    private String planRowId;

    /**
     * 采购材料所属项目
     */
    @Schema(name="所属项目")
    @Excel(name = "所属项目")
    private Project project;
    /**
     * 采购订单对象
     */
    @Schema(name="订单主体")
    private Procurement p;
    @Schema(name="申请单明细行id")
    private String applyMaterialId;
    @Schema(name="计划数量")
    private Double planSum;
    @Schema(name = "退料数量")
    private Double backSum;

    public Double getBackSum() {
        return backSum;
    }

    public void setBackSum(Double backSum) {
        this.backSum = backSum;
    }

    @Override
    public Double getPlanSum() {
        return planSum;
    }

    @Override
    public void setPlanSum(Double planSum) {
        this.planSum = planSum;
    }

    @Override
    public String getPlanRowId() {
        return planRowId;
    }

    @Override
    public void setPlanRowId(String planRowId) {
        this.planRowId = planRowId;
    }

    public Double getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(Double applyPrice) {
        this.applyPrice = applyPrice;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getPm01405() {
        return pm01405;
    }

    public void setPm01405(Double pm01405) {
        this.pm01405 = pm01405;
    }


    public Double getMoneyTax() {
        return moneyTax;
    }

    public void setMoneyTax(Double moneyTax) {
        this.moneyTax = moneyTax;
    }

    public Double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getDhDate() {
        return dhDate;
    }

    public void setDhDate(String dhDate) {
        this.dhDate = dhDate;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
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

    public Double getInSum() {
        return inSum;
    }

    public void setInSum(Double inSum) {
        this.inSum = inSum;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Procurement getP() {
        return p;
    }

    public void setP(Procurement p) {
        this.p = p;
    }

    public String getApplyMaterialId() {
        return applyMaterialId;
    }

    public void setApplyMaterialId(String applyMaterialId) {
        this.applyMaterialId = applyMaterialId;
    }

    @Override
    public String toString() {
        return "ProMaterial{" +
                "proId='" + proId + '\'' +
                ", price=" + price +
                ", money=" + money +
                ", priceTax=" + priceTax +
                ", moneyTax=" + moneyTax +
                ", taxMoney=" + taxMoney +
                ", inSum=" + inSum +
                ", projectId='" + projectId + '\'' +
                ", dhDate='" + dhDate + '\'' +
                '}';
    }
}
