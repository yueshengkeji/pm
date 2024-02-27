package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-06 材料单位.
 *
 * @author XiaoSong
 * @date 2016/08/06
 */
@Schema(name="材料单位")
public class Unit extends BaseEntity {
    /**
     * 单位名称
     */
    @Schema(name="单位名称")
    @Excel(name = "单位", width = 2048)
    private String name;
    /**
     * 单位备注
     */
    @Schema(name="备注信息")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
