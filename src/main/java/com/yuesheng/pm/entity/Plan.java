package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by 96339 on 2016/12/19 项目任务材料计划单主表  sdpm071.
 *
 * @author XiaoSong
 * @date 2016/12/19
 */
@Schema(name="材料计划单对象")
public class Plan extends BaseEntity {
    /**
     * 项目对象 pm07102
     */
    @Schema(name="计划单所属项目")
    private Project project;
    /**
     * 任务id，没有任务时为1     pm07103
     */
    @Schema(name="计划任务，默认为1：无任务")
    private String task;
    /**
     * 单据代码  pm07104
     */
    @Schema(name="单据代码")
    private String billsCode;
    /**
     * 单据名称      pm07105
     */
    @Schema(name="单据名称")
    private String name;
    /**
     * 不可见的制单日期  pm07106
     */
    @Schema(name="制单日期")
    private String date;
    /**
     * 审核人    pm07107
     */
    @Schema(name="审核人")
    private Staff approve;
    /**
     * 审核日期 pm07108
     */
    @Schema(name="审核日期")
    private String approveDate;
    /**
     * 审核标示：0未审核，1已审核
     */
    @Schema(name="审核标志：0=未审核，1=已审核，-1=未发起审批流程")
    private byte appMark;
    /**
     * 制单人对象
     */
    @Schema(name="制单人")
    private Staff staff;
    /**
     * 计划日期，单据日期，可见，可修改
     */
    @Schema(name="计划日期")
    private String planDate;
    /**
     * 备注
     */
    @Schema(name="单据备注")
    private String remark;
    /**
     * 计划单材料集合
     */
    @Schema(name="计划单材料数组")
    private List<PlanMaterial> materialList;

    public List<PlanMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<PlanMaterial> materialList) {
        this.materialList = materialList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getBillsCode() {
        return billsCode;
    }

    public void setBillsCode(String billsCode) {
        this.billsCode = billsCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Staff getApprove() {
        return approve;
    }

    public void setApprove(Staff approve) {
        this.approve = approve;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public byte getAppMark() {
        return appMark;
    }

    public void setAppMark(byte appMark) {
        this.appMark = appMark;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
