package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;

public interface FrameStateCheckService {
    /**
     * oa状态审批完成时触发
     * @param message
     */
    public void oaSuccessChange(FlowMessage message);
}
