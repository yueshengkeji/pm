package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-11 合同类型.
 *
 * @author XiaoSong
 * @date 2016/08/11
 */
@Schema(name="合同类型")
public class ContractType extends BaseEntity {
    /**
     * 类型名称  02
     */
    @Schema(name="类型名称")
    private String name;
    /**
     * 类型编号
     */
    @Schema(name = "类型编号",hidden = true)
    private int serialNumber;
    /**
     * 父节点id
     */
    @Schema(name="父节点id")
    private String parentId;
    /**
     * 根节点+当前节点id集合
     */
    @Schema(name="根节点id")
    private String rootId;
    /**
     * 合同条款信息 12
     */
    @Schema(name = "合同条款",hidden = true)
    private String msg;
    /**
     * 合同类型对应的流程 13
     */
    @Schema(name = "合同类型对应的流程",hidden = true)
    private Flow flow;

    @Schema(name="序号")
    private String orderNumber;

    @Schema(name="是否具有子类")
    private Boolean ifHaveChildren;

    @Schema(name="根节点")
    private String tempRoot;

    public String getTempRoot() {
        return tempRoot;
    }

    public void setTempRoot(String tempRoot) {
        this.tempRoot = tempRoot;
    }

    public Boolean getIfHaveChildren() {
        return ifHaveChildren;
    }

    public void setIfHaveChildren(Boolean ifHaveChildren) {
        this.ifHaveChildren = ifHaveChildren;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
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

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}
