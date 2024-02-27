package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.io.Serializable;

/**
 * 系统配置主表(SystemConfig)实体类
 *
 * @author xiaoSong
 * @since 2022-07-07 14:40:20
 */
@JsonIgnoreProperties(value = {"handler"})
@Schema(name="系统配置key")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = -98508963940655188L;

    @Schema(name="主键")
    private Integer id;

    @NotNull
    @Schema(name="参数名称")
    private String name;

    @Schema(name="备注")
    private String remark;

    @NotNull
    @Schema(name="参数编码")
    private String coding;

    @Schema(name="参数具体的值")
    private String value;

    @Schema(name="上级配置coding")
    private Integer parent;

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

}