package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-20 角色对象 sdpj005.
 *
 * @author XiaoSong
 * @date 2016/08/20
 */
@Schema(name="角色")
public class Role extends BaseEntity {
    /**
     * 角色编码
     */
    @Schema(name = "角色编码")
    private String coding;
    /**
     * 角色名称
     */
    @Schema(name = "角色名称", required = true)
    private String name;
    /**
     * 角色备注
     */
    @Schema(name = "备注")
    private String remark;
    /**
     * 角色职员集合
     */
    @Schema(name = "职员集合")
    private List<Staff> staffList;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public Role() {
    }

    public Role(String coding, String name) {
        this.coding = coding;
        this.name = name;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
