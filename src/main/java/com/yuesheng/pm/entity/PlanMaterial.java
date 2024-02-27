package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2016/12/19 项目任务计划单附表 材料表 ： sdpm070.
 *
 * @author XiaoSong
 * @date 2016/12/19
 */
@Schema(name="计划单材料")
public class PlanMaterial extends BaseEntity {
    /**
     * 计划单id     01
     */
    @Schema(name="计划单id")
    private String planId;
    /**
     * 项目id      02
     */
    @Schema(name="项目id")
    private String projectId;
    /**
     * 任务id,没有任务时为：1     03
     */
    @Schema(name="任务id")
    private String taskId = "1";
    /**
     * 材料对象      04
     */
    @Schema(name="材料对象")
    private Material material;
    /**
     * 计划数量             05
     */
    @Schema(name="计划数量")
    @Excel(name = "计划数量")
    private Double planSum;
    /**
     * 已申请数量         sdpm035.pm03505
     */
    @Schema(name="已申请数量")
    private double applySum;
    /**
     * 金额（含税）     06
     */
    @Schema(name="计划含税金额")
    @Excel(name = "金额合计")
    private Double taxMoney;
    /**
     * 07,08,09列忽略，不知作用,09列默认为1，第二次添加为0，未找到规律
     * 进场日期          10
     */
    @Schema(name="计划进场日期")
    private String inDate;
    /**
     * 计划日期      11
     */
    @Schema(name="单据创建日期")
    private String planDate;
    /**
     * 12,13列默认为0，不知作用
     * 计划单价  14
     */
    @Schema(name="含税计划单价")
    @Excel(name = "计划单价")
    private Double planPrice;
    /**
     * 15列：单位id忽略，材料对象中已有
     * 16列：默认为1.00，不知作用，可能是系数，忽略
     * 审核标识，添加时默认为0,      17
     */
    @Schema(name="审核标记")
    private int pm07017;
    /**
     * 审核人编号，无用，直接根据计划单主表判断       18
     */
    @Schema(name="审核人编号")
    private String approveCode;
    /**
     * 审核日期，无用，直接根据计划单主表判断         19
     */
    @Schema(name="审核日期")
    private String approveDate;
    /**
     * 税率    20
     */
    @Schema(name="税率")
    private Double tax = 0.0;
    /**
     * 不含税单价 21
     */
    @Schema(name="不含税单价")
    private Double price;
    /**
     * 不含税总价 22
     */
    @Schema(name="不含税总价")
    private Double money;
    /**
     * 配置参数
     */
    @Schema(name="额外配置参数")
    private String cnfStr;
    /**
     * 序号
     */
    @Schema(name="序号")
    private int serial;
    /**
     * 单位对象
     */
    @Schema(name="额外单位")
    private Unit unit;
    /**
     * 计划单对象
     */
    @Schema(name="计划单主对象")
    private Plan plan;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        if ("1".equals(taskId)) {
            return "";
        }
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public Double getPlanSum() {
        return planSum;
    }

    public void setPlanSum(Double planSum) {
        this.planSum = planSum;
    }

    public Double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public Double getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }

    public int getPm07017() {
        return pm07017;
    }

    public void setPm07017(int pm07017) {
        this.pm07017 = pm07017;
    }

    public String getApproveCode() {
        return approveCode;
    }

    public void setApproveCode(String approveCode) {
        this.approveCode = approveCode;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
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

    public String getCnfStr() {
        return cnfStr;
    }

    public void setCnfStr(String cnfStr) {
        this.cnfStr = cnfStr;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public double getApplySum() {
        return applySum;
    }

    public void setApplySum(double applySum) {
        this.applySum = applySum;
    }
}
