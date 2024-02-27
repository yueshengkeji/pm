package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.DingTalkTaskFlowRecord;

/**
 * @ClassName DingTalkTaskFlowRecordService
 * @Description
 * @Author ssk
 * @Date 2022/10/24 0024 8:32
 */
public interface DingTalkTaskFlowRecordService {
    /**
     * 新增
     * @param dingTalkTaskFlowRecord
     * @return
     */
    int insert(DingTalkTaskFlowRecord dingTalkTaskFlowRecord);

    /**
     * 更新待办完成状态
     * @param flowApproveId
     * @param isDone
     * @return
     */
    int update(String flowApproveId,String staffId,Integer isDone);

    /**
     * 查询未完成的待办任务
     * @param flowApproveId
     * @param staffId
     * @return
     */
    DingTalkTaskFlowRecord selectTask(String flowApproveId, String staffId);
}
