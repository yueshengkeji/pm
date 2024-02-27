package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.FlowApprove;
import com.yuesheng.pm.entity.FlowCourse;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created by 96339 on 2016/12/10.
 * 自由审批包装类
 *
 * @author XiaoSong
 * @date 2016/12/10
 */
@Schema(name="自由审批包装类")
public class SelectApprove {
    @Schema(name="流程审批消息")
    private FlowApprove approve;
    @Schema(name="流程审批过程")
    private FlowCourse flowCourse;

    public FlowApprove getApprove() {
        return approve;
    }

    public void setApprove(FlowApprove approve) {
        this.approve = approve;
    }

    public FlowCourse getFlowCourse() {
        return flowCourse;
    }

    public void setFlowCourse(FlowCourse flowCourse) {
        this.flowCourse = flowCourse;
    }
}
