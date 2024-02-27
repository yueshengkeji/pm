package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * 项目耗材记录表(ProMaterialHistory)实体类
 *
 * @author xiaoSong
 * @since 2022-06-18 15:02:14
 */
@Schema(name="项目耗材记录")
public class ProMaterialHistory extends BaseEntity {
    private static final long serialVersionUID = -79608612853100198L;

    @Schema(name="项目id")
    private String projectId;

    @Schema(name="材料id")
    private String materialId;

    @Schema(name="计划数量")
    private Double planSum = 0.0;

    @Schema(name="计划单价")
    private Double planPrice = 0.0;

    @Schema(name="申请数量")
    private Double applySum = 0.0;

    @Schema(name="申请单价")
    private Double applyPrice = 0.0;

    @Schema(name="采购数量")
    private Double proSum = 0.0;

    @Schema(name="采购单价")
    private Double proPrice = 0.0;

    @Schema(name="入库数量")
    private Double putSum = 0.0;

    @Schema(name="入库单价")
    private Double putPrice = 0.0;

    @Schema(name="出库数量")
    private Double outSum = 0.0;

    @Schema(name="出库单价")
    private Double outPrice = 0.0;

    @Schema(name="退库数量")
    private Double backSum = 0.0;

    @Schema(name="退库单价")
    private Double backPrice = 0.0;

    @Schema(name="材料")
    private Material material;

    @Schema(name="数据类型:原材料/报销")
    private String type;

    @Schema(name="采购金额合计")
    private Double proMoney;

    public Double getProMoney() {
        return proMoney;
    }

    public void setProMoney(Double proMoney) {
        this.proMoney = proMoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Double getPlanSum() {
        return planSum;
    }

    public void setPlanSum(Double planSum) {
        this.planSum = planSum;
    }

    public Double getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }

    public Double getApplySum() {
        return applySum;
    }

    public void setApplySum(Double applySum) {
        this.applySum = applySum;
    }

    public Double getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(Double applyPrice) {
        this.applyPrice = applyPrice;
    }

    public Double getProSum() {
        return proSum;
    }

    public void setProSum(Double proSum) {
        this.proSum = proSum;
    }

    public Double getProPrice() {
        return proPrice;
    }

    public void setProPrice(Double proPrice) {
        this.proPrice = proPrice;
    }

    public Double getPutSum() {
        return putSum;
    }

    public void setPutSum(Double putSum) {
        this.putSum = putSum;
    }

    public Double getPutPrice() {
        return putPrice;
    }

    public void setPutPrice(Double putPrice) {
        this.putPrice = putPrice;
    }

    public Double getOutSum() {
        return outSum;
    }

    public void setOutSum(Double outSum) {
        this.outSum = outSum;
    }

    public Double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

    public Double getBackSum() {
        return backSum;
    }

    public void setBackSum(Double backSum) {
        this.backSum = backSum;
    }

    public Double getBackPrice() {
        return backPrice;
    }

    public void setBackPrice(Double backPrice) {
        this.backPrice = backPrice;
    }

}