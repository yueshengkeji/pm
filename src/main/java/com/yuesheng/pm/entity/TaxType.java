package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2016/11/21 开票类型表 sdpj020.
 *
 * @author XiaoSong
 * @date 2016/11/21
 */
@Schema(name="税率类型字典表")
public class TaxType extends BaseEntity {
    /**
     * 名称
     */
    @Schema(name="税率名称")
    private String name;
    /**
     * 税率
     */
    @Schema(name="税率点")
    private Double tax;
    /**
     * 备注
     */
    @Schema(name="备注信息")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
