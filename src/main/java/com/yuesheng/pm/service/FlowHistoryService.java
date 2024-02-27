package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowHistory;

/**
 * Created by Administrator on 2016-08-16 流程记录服务接口.
 *
 */
public interface FlowHistoryService {
    /**
     * 添加流程使用记录
     * @param flowHistory 记录对象
     * @return 影响的行数
     */
    Integer addHistory(FlowHistory flowHistory);

    /**
     * 根据流程名称，查询第一个流程记录
     * @param name 流程名称
     * @return
     */
    FlowHistory getFlowByName(String name);
}
