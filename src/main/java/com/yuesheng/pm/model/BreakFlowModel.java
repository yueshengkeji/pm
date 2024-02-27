package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.FlowApprove;
import com.yuesheng.pm.entity.FlowCourse;

/**
 * 流程中断model
 */
public class BreakFlowModel {
    /**
     * 中断节点
     */
    private FlowApprove approve;
    /**
     * 中断位置
     */
    private FlowCourse link;

    public FlowApprove getApprove() {
        return approve;
    }

    public void setApprove(FlowApprove approve) {
        this.approve = approve;
    }

    public FlowCourse getLink() {
        return link;
    }

    public void setLink(FlowCourse link) {
        this.link = link;
    }
}
