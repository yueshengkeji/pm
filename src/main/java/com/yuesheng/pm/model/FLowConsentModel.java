package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.FlowCourse;
import com.yuesheng.pm.entity.FlowMessage;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="流程审批返回值对象")
public class FLowConsentModel {
    @Schema(name="操作状态，1=通过成功，2=需要指定下一步审批人（自由选人）,3=未指定下一步审批人，审批失败,4=该步骤已经审批,5=发起人与审批人为同一人，下一步也是自由选人审批，提示审批人自行到审批界面审批")
    private int state;
    @Schema(name="提示消息")
    private String msg;
    @Schema(name="下一步骤对象")
    private FlowCourse flowCourse;
    @Schema(name="审批完成后返回的主消息实例")
    private FlowMessage flowMessage;
    @Schema(name="当前流程是否全部审批通过")
    private boolean flowSuccess;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FlowCourse getFlowCourse() {
        return flowCourse;
    }

    public void setFlowCourse(FlowCourse flowCourse) {
        this.flowCourse = flowCourse;
    }

    public FlowMessage getFlowMessage() {
        return flowMessage;
    }

    public void setFlowMessage(FlowMessage flowMessage) {
        this.flowMessage = flowMessage;
    }

    public void setFlowSuccess(boolean flowSuccess) {
        this.flowSuccess = flowSuccess;
    }

    public boolean getFlowSuccess() {
        return flowSuccess;
    }
}

