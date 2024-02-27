package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-02 申请单中材料对象 sdpm035.
 *
 * @author XiaoSong
 * @date 2016/08/02
 */
@Schema(name="申请单材料")
public class ApplyMaterial extends Material {
    /**
     * 采购申请单id      01
     */
    @Schema(name="申请单主体id")
    private String applyid;
    /**
     * 序号                  02
     */
    @Schema(name="序号")
    @Excel(width = 4096, name = "序号")
    private int serialNumber;
    /**
     * 需求日期      04
     */
    @Schema(name="需求日期")
    private String applyDate = "";
    /**
     * 申请数量                         05
     */
    @Schema(name="申请数量")
    @Excel(name = "数量", width = 2048)
    private Double sum;
    /**
     * 任务id,1=没有任务              07
     */
    @Schema(name="任务信息")
    private String taskId = "1";
    /**
     * 已采购数量                  08
     */
    @Schema(name="已采购数量")
    private Double ySum;
    /**
     * 材料备注                   09
     */
//    private String remark = "";
    /**
     * 主键id                     12
     */
    @Schema(name="管关联主键")
    private String major;
    /**
     * 配置参数               13
     */
    @Schema(name="额外配置参数")
    private String cnfParam = "";
    /**
     * 未采购数量                   18
     */
    @Schema(name="未采购数量")
    private Double notSum;
    /**
     * 计划单明细行主键,直接从材料库选择的为""             20
     */
    @Schema(name="计划单明细行主键")
    private String planRowId = "";
    /**
     * 计划数量
     */
    @Schema(name="计划数量")
    private Double planSum;
    /**
     * 申请单对象
     */
    @Schema(name="申请单主体")
    private Apply apply;
    /**
     * 材料申请人名称，查看项目材料使用情况时使用
     */
    @Schema(name="申请人名称")
    private String staffName;
    /**
     * 已经申请总数
     */
    private Double applySum;

    public Double getPlanSum() {
        return planSum;
    }

    public void setPlanSum(Double planSum) {
        this.planSum = planSum;
    }

    public String getPlanRowId() {
        return planRowId;
    }

    public void setPlanRowId(String planRowId) {
        this.planRowId = planRowId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Double getySum() {
        return ySum;
    }

    public void setySum(Double ySum) {
        this.ySum = ySum;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Double getNotSum() {
        return notSum;
    }

    public void setNotSum(Double notSum) {
        this.notSum = notSum;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getCnfParam() {
        if (cnfParam == null) {
            return "";
        } else {
            return cnfParam;
        }
    }

    public void setCnfParam(String cnfParam) {
        this.cnfParam = cnfParam;
    }

    public String getTaskId() {
        if ("1".equals(taskId)) {
            return "";
        }
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setApplySum(Double applySum) {
        this.applySum = applySum;
    }

    public Double getApplySum() {
        return applySum;
    }
}
