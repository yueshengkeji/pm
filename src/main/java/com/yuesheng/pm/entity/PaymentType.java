package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2016/12/27 付款性质（付款类型表）      sdpd053.
 *
 * @author XiaoSong
 * @date 2016/12/27
 */
@Schema(name="付款性质")
public class PaymentType extends BaseEntity {
    /**
     * 性质名称
     */
    @Schema(name="性质名称")
    @Excel(name = "付款性质")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
