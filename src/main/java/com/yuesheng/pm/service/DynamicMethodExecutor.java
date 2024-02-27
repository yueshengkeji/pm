package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;

public interface DynamicMethodExecutor {
    void executeDynamicMethod(String beanName, String methodName, Object... methodArgs);

    /**
     * 执行service函数
     * @param service service.methodName
     * @param flowMsg
     */
    void executeDynamicMethod(String service, FlowMessage flowMsg);
}
