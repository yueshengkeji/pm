package com.yuesheng.pm.model;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="角色菜单关系模型")
public class RoleMenuModel {
    @Schema(name="角色编码")
    private String coding;
    @Schema(name="菜单id")
    private String menuId;

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
