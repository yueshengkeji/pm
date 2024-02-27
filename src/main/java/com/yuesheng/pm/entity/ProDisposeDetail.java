package com.yuesheng.pm.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * 处置单明细(ProDisposeDetail)实体类
 *
 * @author makejava
 * @since 2020-06-28 10:39:51
 */
@Schema(name="处置单明细类")
public class ProDisposeDetail extends BaseEntity {
    private static final long serialVersionUID = -24073217105081053L;
    /**
     * 主键
     */
    @Schema(name="主键")
    private String id;
    /**
     * 处置资产名称
     */
    @Schema(name = "处置材料名称", required = true)
    private String name;
    /**
     * 处置数量
     */
    @Schema(name = "处置数量", required = true)
    private Double sum;
    /**
     * 处置总价
     */
    @Schema(name = "预估金额合计", required = true)
    private Double money;
    /**
     * 备注信息
     */
    @Schema(name = "备注信息")
    private String remark;
    /**
     *
     * 主单据id
     */
    @Schema(name = "处置申请单主单据id", required = true)
    private String disposeId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDisposeId() {
        return disposeId;
    }

    public void setDisposeId(String disposeId) {
        this.disposeId = disposeId;
    }

}