package com.yuesheng.pm.model;

import com.yuesheng.pm.annotation.Excel;
import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.Company;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Project;
import io.swagger.v3.oas.annotations.media.Schema;

public class ProMaterReport extends BaseEntity {
    @Schema(name="材料")
    private Material material;
    @Schema(name="供应商")
    private Company company;
    @Schema(name="项目")
    private Project project;
    @Schema(name="申请单材料id")
    private String applyMaterialId;
    @Schema(name="采购数量")
    @Excel(name = "采购数量")
    private Double sum;
    @Excel(name = "已入库数量")
    private Double inSum;
    @Excel(name = "采购单价")
    private Double priceTax;
    @Excel(name = "采购金额")
    private Double moneyTax;
    private String inDate;
    private String dhDate;
    private String proId;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getApplyMaterialId() {
        return applyMaterialId;
    }

    public void setApplyMaterialId(String applyMaterialId) {
        this.applyMaterialId = applyMaterialId;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getInSum() {
        return inSum;
    }

    public void setInSum(Double inSum) {
        this.inSum = inSum;
    }

    public Double getPriceTax() {
        return priceTax;
    }

    public void setPriceTax(Double priceTax) {
        this.priceTax = priceTax;
    }

    public Double getMoneyTax() {
        return moneyTax;
    }

    public void setMoneyTax(Double moneyTax) {
        this.moneyTax = moneyTax;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
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
}
