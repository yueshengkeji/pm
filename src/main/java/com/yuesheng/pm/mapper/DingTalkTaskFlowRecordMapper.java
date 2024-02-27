package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.DingTalkTaskFlowRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName DingTalkTaskFlowRecordMapper 钉钉待办任务与系统流程审批记录
 * @Description
 * @Author ssk
 * @Date 2022/10/22 0022 15:44
 */
@Mapper
public interface DingTalkTaskFlowRecordMapper {

    /**
     * 新增
     * @param dingTalkTaskFlowRecord
     * @return
     */
    int insert(DingTalkTaskFlowRecord dingTalkTaskFlowRecord);

    /**
     * 更新待办任务完成状态
     * @param flowApproveId
     * @param staffId 任务执行人id
     * @return
     */
    int update(@Param("flowApproveId")String flowApproveId,@Param("staffId")String staffId,@Param("isDone")Integer isDone);

    /**
     * 查询未完成的待办任务
     * @param flowApproveId
     * @param staffId 任务执行人id
     * @return
     */
    DingTalkTaskFlowRecord selectTask(@Param("flowApproveId")String flowApproveId,@Param("staffId") String staffId);

}
