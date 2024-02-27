package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-16 流程使用记录对象.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
public class FlowHistory extends BaseEntity {
    /**
     * 流程名称          02
     */
    private String name;
    /**
     * 流程说明              03
     */
    private String remark;
    /**
     * 流程目录编号    04
     */
    private String folderCoding;
    /**
     * 窗体代码        07
     */
    private String frameCoding;
    /**
     * 流程来源
     */
    private String sourceFlowId;

    /**
     * 默认的构造函数
     */
    public FlowHistory() {

    }

    public FlowHistory(String name, String remark, String folderCoding, String frameCoding) {
        this.name = name;
        this.remark = remark;
        this.folderCoding = folderCoding;
        this.frameCoding = frameCoding;
    }

    public String getSourceFlowId() {
        return sourceFlowId;
    }

    public void setSourceFlowId(String sourceFlowId) {
        this.sourceFlowId = sourceFlowId;
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

    public String getFolderCoding() {
        return folderCoding;
    }

    public void setFolderCoding(String folderCoding) {
        this.folderCoding = folderCoding;
    }

    public String getFrameCoding() {
        return frameCoding;
    }

    public void setFrameCoding(String frameCoding) {
        this.frameCoding = frameCoding;
    }
}
