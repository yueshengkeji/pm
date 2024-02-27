package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 采购订单报表记录(ProReport)实体类
 *
 * @author xiaoSong
 * @since 2021-06-24 10:07:21
 */
@JsonIgnoreProperties(value = {"handler"})
public class ProReport implements Serializable {
    private static final long serialVersionUID = -28402398891509371L;

    private Long id;
    /**
     * 采购订单id
     */
    private String proId;
    /**
     * 采购订单明细id
     */
    private String proMaterialId;
    /**
     * 材料id
     */
    private String materialId;

    private String voucherDate;
    /**
     * 供应单位id
     */
    private String companyId;
    /**
     * 采购数量
     */
    private Double sum;
    /**
     * 申请单价
     */
    private Double applyPrice;

    private Double proPrice;

    private Double proMoney;
    /**
     * 备注
     */
    private String remark;

    private String projectPersonName;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 到货备注信息
     */
    private String dhRemark;

    private String acceptPersonName;
    /**
     * 是否签订合同备注
     */
    private String contractRemark;
    /**
     * 付款日期记录
     */
    private String payDate;

    private String companyName;
    private String staffId;
    private String staffName;
    private Material material;
    private String series;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProMaterialId() {
        return proMaterialId;
    }

    public void setProMaterialId(String proMaterialId) {
        this.proMaterialId = proMaterialId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(Double applyPrice) {
        this.applyPrice = applyPrice;
    }

    public Double getProPrice() {
        return proPrice;
    }

    public void setProPrice(Double proPrice) {
        this.proPrice = proPrice;
    }

    public Double getProMoney() {
        return proMoney;
    }

    public void setProMoney(Double proMoney) {
        this.proMoney = proMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectPersonName() {
        return projectPersonName;
    }

    public void setProjectPersonName(String projectPersonName) {
        this.projectPersonName = projectPersonName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDhRemark() {
        return dhRemark;
    }

    public void setDhRemark(String dhRemark) {
        this.dhRemark = dhRemark;
    }

    public String getAcceptPersonName() {
        return acceptPersonName;
    }

    public void setAcceptPersonName(String acceptPersonName) {
        this.acceptPersonName = acceptPersonName;
    }

    public String getContractRemark() {
        return contractRemark;
    }

    public void setContractRemark(String contractRemark) {
        this.contractRemark = contractRemark;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}