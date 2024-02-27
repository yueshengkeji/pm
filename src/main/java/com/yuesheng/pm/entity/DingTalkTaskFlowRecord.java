package com.yuesheng.pm.entity;

/**
 * @ClassName DingTalkTaskFlowRecord 钉钉待办任务与系统审批流程记录
 * @Description
 * @Author ssk
 * @Date 2022/10/22 0022 13:40
 */
public class DingTalkTaskFlowRecord extends BaseEntity{

    /**
     * 审批流程消息id
     */
    private String flowApproveId;

    /**
     * 审批人id
     */
    private String staffId;

    /**
     * 待办任务id
     */
    private String taskId;

    /**
     * 待办任务是否完成 0=未完成，1=已完成
     */
    private Integer isDone;

    /**
     * 待办任务创建者id
     */
    private String creatorId;

    public String getFlowApproveId() {
        return flowApproveId;
    }

    public void setFlowApproveId(String flowApproveId) {
        this.flowApproveId = flowApproveId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getIsDone() {
        return isDone;
    }

    public void setIsDone(Integer isDone) {
        this.isDone = isDone;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
