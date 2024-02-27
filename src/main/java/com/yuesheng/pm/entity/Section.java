package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-06 部门实体对象.
 *
 * @author XiaoSong
 * @date 2016/08/06
 */
@Schema(name="部门类")
public class Section extends BaseEntity {
    private static final long serialVersionUID = -4018766111730061456L;
    /**
     * 部门名称  02
     */
    @Schema(name = "部门名称", required = true)
    @Excel(name = "部门名称",width = 3092)
    private String name;
    /**
     * 上级部门id    03
     */
    @Schema(name = "上级部门id", required = true)
    private String parentid;
    /**
     * 部门主管id    05
     */
    @Schema(name = "部门经理id", required = true)
    private String managerid;
    @Schema(name = "部门经理姓名")
    private String managerName;
    @Schema(name="部门内人员字符串‘;’分割")
    private String userNames;
    /**
     * 联系电话      06
     */
    @Schema(name = "部门联系电话", required = false)
    private String tel;
    /**
     * 办公地点      07
     */
    @Schema(name = "办公地点")
    private String address;
    /**
     * 部门备注
     */
    @Schema(name = "备注信息")
    private String remark;
    /**
     * 部门编码      09
     */
    @Schema(name = "部门编码")
    private String coding;
    /**
     * 父元素id+当前部门id      04
     */
    @Schema(name = "部门所有上级id集合字符串，规则为最高级别部门编码在最前，以此类推")
    private String rootId;
    @Schema(name = "上级部门对象")
    private Section parent;

    @Schema(name="协管领导")
    private String assistManager = "";
    @Schema(name="协管领导名称")
    private String assistManagerName = "";

    @Schema(name="下级部门列表")
    private List<Section> children;

    public String getAssistManagerName() {
        return assistManagerName;
    }

    public void setAssistManagerName(String assistManagerName) {
        this.assistManagerName = assistManagerName;
    }

    public String getAssistManager() {
        return assistManager;
    }

    public void setAssistManager(String assistManager) {
        this.assistManager = assistManager;
    }

    public List<Section> getChildren() {
        return children;
    }

    public void setChildren(List<Section> children) {
        this.children = children;
    }

    public Section getParent() {
        return parent;
    }

    public void setParent(Section parent) {
        this.parent = parent;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
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

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
