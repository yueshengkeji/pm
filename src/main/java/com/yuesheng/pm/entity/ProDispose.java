package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * 处置单主表(ProDispose)实体类
 *
 * @author makejava
 * @since 2020-06-28 10:28:55
 */
@Schema(name="处置申请单类，附件表名称：sdpd013")
public class ProDispose extends BaseEntity {
    private static final long serialVersionUID = 378062281083637378L;
    /**
     * 主键
     */
    @Schema(name="主键")
    private String id;
    /**
     * 标题
     */
    @Schema(name = "标题", required = true)
    private String title;
    /**
     * 单据创建人
     */
    @Schema(name = "表单制作人", required = false)
    private String staffId;
    /**
     * 执行人id
     */
    @Schema(name = "执行员工id", required = true)
    private String execStaffId;
    /**
     * 执行人姓名
     */
    @Schema(name = "执行员工姓名", required = true)
    private String execStaffName;
    /**
     * 执行原因
     */
    @Schema(name = "执行原因")
    private String reasons;
    /**
     * 处置时间
     */
    @Schema(name = "处置时间")
    private String disposeDate;
    /**
     * 单据添加时间
     */
    @Schema(name = "单据添加时间")
    private String date;

    @Schema(name = "单据状态:0=未审核，2=已审核，3-待复核")
    private Integer state;
    @Schema(name = "处置单明细集合", required = true)
    private List<ProDisposeDetail> disposeDetailList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getExecStaffId() {
        return execStaffId;
    }

    public void setExecStaffId(String execStaffId) {
        this.execStaffId = execStaffId;
    }

    public String getExecStaffName() {
        return execStaffName;
    }

    public void setExecStaffName(String execStaffName) {
        this.execStaffName = execStaffName;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getDisposeDate() {
        return disposeDate;
    }

    public void setDisposeDate(String disposeDate) {
        this.disposeDate = disposeDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<ProDisposeDetail> getDisposeDetailList() {
        return disposeDetailList;
    }

    public void setDisposeDetailList(List<ProDisposeDetail> disposeDetailList) {
        this.disposeDetailList = disposeDetailList;
    }
}