package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.ArrayList;

/**
 * Created by 96339 on 2017/4/28 菜单 pro_menu.
 *
 * @author XiaoSOng
 * @date 2017/04/28
 */
@Schema(name="菜单类")
public class Menu extends BaseEntity {
    @Schema(name = "菜单名称", required = true)
    private String name;
    @Schema(name = "菜单url地址", required = true)
    private String url;
    @Schema(name="上级菜单对象")
    private Menu parent;
    @Schema(name="上级菜单id")
    private String parentId;
    @Schema(name="菜单排序序号")
    private int sort;
    @Schema(name="备注")
    private String remark;
    @Schema(name="菜单图标，可以是前端class名称，亦可是图标url路径，由调用者灵活运用")
    private String ico;
    @Schema(name = "菜单类型，0=系统自有视图菜单，1/2=老系统菜单,3=vue前端菜单", required = true)
    private int type;
    @Schema(name="菜单编码,后期oa流程绑定时时使用")
    private String coding;
    @Schema(name="子菜单集合")
    private ArrayList<Menu> children;
    @Schema(name = "是否外链")
    private boolean isOuter;
    @Schema(name = "外部菜单地址")
    private String outerPath;
    @Schema(name="是否隐藏")
    private Boolean hide;
    @Schema(name="实体类名称")
    private String beanName;
    @Schema(name="流程sql语句")
    private String flowSql;

    public String getFlowSql() {
        return flowSql;
    }

    public void setFlowSql(String flowSql) {
        this.flowSql = flowSql;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getOuterPath() {
        return outerPath;
    }

    public void setOuterPath(String outerPath) {
        this.outerPath = outerPath;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public ArrayList<Menu> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Menu> children) {
        this.children = children;
    }

    public boolean isOuter() {
        return isOuter;
    }

    public void setOuter(boolean outer) {
        isOuter = outer;
    }
}
