package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.StorageMaterial;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name="采购订单入库model")
public class PutStorageModel extends BaseEntity {
    @Schema(name="采购订单id")
    private String proId;
    @Schema(name="仓库id")
    private String storageId;
    @Schema(name="入库单材料集合")
    private List<StorageMaterial> materialList;
    @Schema(name="签字截止日期（过期日期）")
    private String pastDate;
    @Schema(name="签字人员id")
    private String projectLeaderId;
    @Schema(name="签字人员名称")
    private String projectLeaderName;
    @Schema(name="入库单备注信息")
    private String remark;
    @Schema(name="入库单税率")
    private Double tax;
    @Schema(name="优惠金额")
    private String saleMoney;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public List<StorageMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<StorageMaterial> materialList) {
        this.materialList = materialList;
    }

    public String getPastDate() {
        return pastDate;
    }

    public void setPastDate(String pastDate) {
        this.pastDate = pastDate;
    }

    public String getProjectLeaderId() {
        return projectLeaderId;
    }

    public void setProjectLeaderId(String projectLeaderId) {
        this.projectLeaderId = projectLeaderId;
    }

    public String getProjectLeaderName() {
        return projectLeaderName;
    }

    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(String saleMoney) {
        this.saleMoney = saleMoney;
    }
}
