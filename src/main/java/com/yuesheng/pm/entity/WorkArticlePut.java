package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

@Schema(name="办公用品入库单")
public class WorkArticlePut extends BaseEntity {

    @Schema(name="入库标题")
    private String name;

    @Schema(name="入库日期")
    private String date;

    @Schema(name="备注")
    private String remark;

    @Schema(name="入库员工")
    private Staff staff;

    @Schema(name="审核人员")
    private Staff approve;

    @Schema(name="审核状态")
    private Integer approveState;

    @Schema(name="审核日期")
    private String approveDate;

    @Schema(name="入库材料集合")
    private List<WorkArticlePutDetail> detailList;

    public List<WorkArticlePutDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<WorkArticlePutDetail> detailList) {
        this.detailList = detailList;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getApprove() {
        return approve;
    }

    public void setApprove(Staff approve) {
        this.approve = approve;
    }

    public Integer getApproveState() {
        return approveState;
    }

    public void setApproveState(Integer approveState) {
        this.approveState = approveState;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }
}
