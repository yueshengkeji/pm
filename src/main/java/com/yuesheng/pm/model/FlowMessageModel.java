package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Flow;
import com.yuesheng.pm.entity.FlowCourse;
import com.yuesheng.pm.entity.FlowMessage;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created by 96339 on 2017/3/6.
 * 流程发起包装类
 *
 * @author XiaoSong
 * @date 2017/03/06
 */
@Schema(name="发起流程实例包装类")
public class FlowMessageModel {
    /**
     * 【我的发起】对象
     */
    @Schema(name="流程实例消息")
    private FlowMessage message;
    /**
     * 流程对象
     */
    @Schema(name="流程主体")
    private Flow flow;
    /**
     * 步骤对象
     */
    @Schema(name="自由选人时指定的步骤")
    private FlowCourse flowCourse;

    public FlowMessage getMessage() {
        return message;
    }

    public void setMessage(FlowMessage message) {
        this.message = message;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public FlowCourse getFlowCourse() {
        return flowCourse;
    }

    public void setFlowCourse(FlowCourse flowCourse) {
        this.flowCourse = flowCourse;
    }
}
