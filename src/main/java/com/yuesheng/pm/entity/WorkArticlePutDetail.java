package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

@Schema(name="办公用品入库单明细")
public class WorkArticlePutDetail extends BaseEntity {

    @Schema(name="入库主单据")
    private WorkArticlePut articlePut;

    @Schema(name="排序序号")
    private int series;

    @Schema(name="备注")
    private String remark;

    @Schema(name="入库数量")
    private Double sum;

    @Schema(name="入库单价")
    private Double price;

    @Schema(name="入库金额")
    private Double money;

    @Schema(name="入库之前的库存")
    private Double beforeSum;

    @Schema(name="材料对象")
    private Material material;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public WorkArticlePut getArticlePut() {
        return articlePut;
    }

    public void setArticlePut(WorkArticlePut articlePut) {
        this.articlePut = articlePut;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
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

    public Double getBeforeSum() {
        return beforeSum;
    }

    public void setBeforeSum(Double beforeSum) {
        this.beforeSum = beforeSum;
    }
}
