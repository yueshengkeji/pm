package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.Procurement;
import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.entity.Unit;

import java.util.List;

/**
 * Created by 96339 on 2016/12/19.
 * 项目用料modal 用于查询项目的 {计划数量，申请数量，采购数量，入库数量，领用数量}
 *
 * @author XiaoSong
 * @date 2016/12/19
 */
public class ProjectMaterial extends BaseEntity {
    /**
     * 材料名称
     */
    private String name;
    /**
     * 材料型号
     */
    private String model;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 产地
     */
    private String producingArea;
    /**
     * 材料单位对象
     */
    private Unit unit;
    /**
     * 当前类型
     */
    private int type;
    /**
     * 计划数量
     */
    private Double planSum = 0.0;
    /**
     * 申请数量
     */
    private Double applySum = 0.0;
    /**
     * 采购数量
     */
    private Double proSum = 0.0;
    /**
     * 入库数量
     */
    private Double putSum = 0.0;
    /**
     * 出库数量
     */
    private Double outSum = 0.0;
    /**
     * 金额，以出库金额为准
     */
    private Double money = 0.0;
    /**
     * 退料金额
     */
    private Double backMoney = 0.0;
    /**
     * 项目对象
     */
    private Project project;
    /**
     * 退料数量
     */
    private Double backSum = 0.0;
    /**
     * 报销金额
     */
    private Double dcMoney = 0.0;
    /**
     * 供应单位集合
     */
    private String companyNames;
    /**
     * 采购订单列表
     */
    private List<Procurement> procurementList;
    /**
     * 采购金额
     */
    private Double proMoney = 0.0;
    /**
     * 入库金额
     */
    private Double putMoney = 0.0;
    /**
     * 计划成本金额
     */
    private Double planMoney = 0.0;

    public Double getProMoney() {
        return proMoney;
    }

    public void setProMoney(Double proMoney) {
        this.proMoney = proMoney;
    }

    public String getCompanyNames() {
        return companyNames;
    }

    public void setCompanyNames(String companyNames) {
        this.companyNames = companyNames;
    }

    public List<Procurement> getProcurementList() {
        return procurementList;
    }

    public void setProcurementList(List<Procurement> procurementList) {
        this.procurementList = procurementList;
    }

    public Double getDcMoney() {
        return dcMoney;
    }

    public void setDcMoney(Double dcMoney) {
        this.dcMoney = dcMoney;
    }

    public Double getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Double backMoney) {
        this.backMoney = backMoney;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getPlanSum() {
        return planSum;
    }

    public void setPlanSum(Double planSum) {
        this.planSum = planSum;
    }

    public Double getApplySum() {
        return applySum;
    }

    public void setApplySum(Double applySum) {
        this.applySum = applySum;
    }

    public Double getProSum() {
        return proSum;
    }

    public void setProSum(Double proSum) {
        this.proSum = proSum;
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

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setBackSum(Double backSum) {
        this.backSum = backSum;
    }

    public Double getBackSum() {
        return backSum;
    }

    public Double getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(Double putMoney) {
        this.putMoney = putMoney;
    }

    public Double getPlanMoney() {
        return planMoney;
    }

    public void setPlanMoney(Double planMoney) {
        this.planMoney = planMoney;
    }
}
