package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 宋正根 on 2016/8/30 出库单材料对象 & 办公用品材料对象 sdpm201.
 * @author XiaoSong
 * @date 2016/08/30
 */
@Schema(name="出库材料")
public class MaterOutChild extends BaseEntity {
    /**
     * 出库单主表id
     */
    @Schema(name="出库主键")
    private String materOutId;
    /**
     * 出库单主对象
     */
    @Schema(name="出库主对象")
    private MaterOut materOut;
    /**
     * 出库单号
     */
    @Schema(name="出库单号")
    private String outNumber;
    /**
     * 材料对象 03
     */
    @Schema(name="材料")
    private Material material;
    /**
     * 出库数量  04
     */
    @Schema(name="出库数量")
    @Excel(name = "数量")
    private Double sum;
    /**
     * 出库单价
     */
    @Schema(name="出库单价")
    private Double taxPrice;
    /**
     * 出库金额
     */
    @Schema(name="出库金额")
    private Double taxMoney;
    /**
     * 成本单价
     */
    @Schema(name="成本单价")
    private Double planPrice = 0.0;
    /**
     * 入库单材料id
     */
    @Schema(name="入库单材料主键")
    private String putMaterId = "";
    /**
     * 配置参数
     */
    @Schema(name="备注参数")
    private String pm02109 = "";
    /**
     * 预留
     */
    private String pm02110 = "";
    /**
     * 预留，添加时无需，默认为0
     */
    private byte pm02111 = 0;
    /**
     * 预留，添加时无需
     */
    private String pm02112 = "";
    /**
     * 出库时间
     */
    @Schema(name="出库时间")
    @Excel(name = "出库时间")
    private String outDate;
    /**
     * 税率        21
     */
    @Schema(name="税率")
    private Double tax = 0.0;
    /**
     * 出库所属仓库id
     */
    @Schema(name="所在仓库主键")
    private String storageId;
    /**
     * 行备注
     */
    @Schema(name="备注")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public MaterOut getMaterOut() {
        return materOut;
    }

    public void setMaterOut(MaterOut materOut) {
        this.materOut = materOut;
    }

    public String getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(String outNumber) {
        this.outNumber = outNumber;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getMaterOutId() {
        return materOutId;
    }

    public void setMaterOutId(String materOutId) {
        this.materOutId = materOutId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    public Double getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }

    public String getPutMaterId() {
        return putMaterId;
    }

    public void setPutMaterId(String putMaterId) {
        this.putMaterId = putMaterId;
    }

    public String getPm02109() {
        return pm02109;
    }

    public void setPm02109(String pm02109) {
        this.pm02109 = pm02109;
    }

    public String getPm02110() {
        return pm02110;
    }

    public void setPm02110(String pm02110) {
        this.pm02110 = pm02110;
    }

    public byte getPm02111() {
        return pm02111;
    }

    public void setPm02111(byte pm02111) {
        this.pm02111 = pm02111;
    }

    public String getPm02112() {
        return pm02112;
    }

    public void setPm02112(String pm02112) {
        this.pm02112 = pm02112;
    }
}
