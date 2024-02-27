package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-20 职务对象 sdpo014.
 *
 * @author XiaoSong
 * @date 2016/08/20
 */
@Schema(name="职务类")
public class Duty extends BaseEntity {
    @Schema(name = "职务姓名", required = true)
    private String name;
    @Schema(name = "上级职务", required = true)
    private String parentId;
    @Schema(name = "上级所有职务集合id串,新增时后台自动生成")
    private String rootId;
    @Schema(name = "职务编码")
    private String coding;
    @Schema(name="子职务")
    private List<Duty> children;
    @Schema(name="该职务人员总数")
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Duty> getChildren() {
        return children;
    }

    public void setChildren(List<Duty> children) {
        this.children = children;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }
}
