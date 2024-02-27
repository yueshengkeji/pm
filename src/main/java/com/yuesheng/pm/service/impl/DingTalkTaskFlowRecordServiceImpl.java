package com.yuesheng.pm.service.impl;

import com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskResponseBody;
import com.yuesheng.pm.entity.DingTalkTaskFlowRecord;
import com.yuesheng.pm.entity.DingTalkTaskUpdate;
import com.yuesheng.pm.mapper.DingTalkTaskFlowRecordMapper;
import com.yuesheng.pm.service.DingTalkApiService;
import com.yuesheng.pm.service.DingTalkTaskFlowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @ClassName DingTalkTaskFlowRecordServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/10/24 0024 8:33
 */
@Service
public class DingTalkTaskFlowRecordServiceImpl implements DingTalkTaskFlowRecordService {

    @Autowired
    private DingTalkTaskFlowRecordMapper dingTalkTaskFlowRecordMapper;

    @Autowired
    private DingTalkApiService dingTalkApiService;

    @Override
    public int insert(DingTalkTaskFlowRecord dingTalkTaskFlowRecord) {
        dingTalkTaskFlowRecord.setId(UUID.randomUUID().toString());
        return dingTalkTaskFlowRecordMapper.insert(dingTalkTaskFlowRecord);
    }

    @Override
    public int update(String flowApproveId,String staffId,Integer isDone) {
        int update = 0;
        DingTalkTaskFlowRecord dingTalkTaskFlowRecord = selectTask(flowApproveId, staffId);
        DingTalkTaskUpdate dingTalkTaskUpdate = new DingTalkTaskUpdate();
        dingTalkTaskUpdate.setId(dingTalkTaskFlowRecord.getTaskId());
        dingTalkTaskUpdate.setUnionId(dingTalkTaskFlowRecord.getCreatorId());
        dingTalkTaskUpdate.setDone(isDone == 1 ? true:false);
        UpdateTodoTaskResponseBody updateTodoTaskResponseBody = dingTalkApiService.updateTask(dingTalkTaskUpdate);
        if (!Objects.isNull(updateTodoTaskResponseBody)){
            update = dingTalkTaskFlowRecordMapper.update(flowApproveId, staffId, isDone);
        }
        return update;
    }

    @Override
    public DingTalkTaskFlowRecord selectTask(String flowApproveId, String staffId) {
        return dingTalkTaskFlowRecordMapper.selectTask(flowApproveId,staffId);
    }
}
