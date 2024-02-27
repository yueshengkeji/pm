package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.FlowHistory;
import com.yuesheng.pm.mapper.FlowHistoryMapper;
import com.yuesheng.pm.service.FlowHistoryService;
import com.yuesheng.pm.service.FlowService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016-08-16.
 * @author XiaoSong
 * @date 2016/08/16
 * 流程记录服务实现类
 */
@Service("flowHistoryService")
public class FlowHistoryServiceImpl implements FlowHistoryService {
    @Autowired
    private FlowHistoryMapper flowHistoryMapper;

    @Autowired
    @Lazy
    private FlowService flowService;
    @Override
    public Integer addHistory(FlowHistory flowHistory) {
        flowService.updateLastUse(flowHistory.getSourceFlowId(), DateUtil.getDatetime());
        return flowHistoryMapper.addHistory(flowHistory);
    }

    @Override
    public FlowHistory getFlowByName(String name) {
        PageHelper.startPage(1,1);
        return flowHistoryMapper.getFlowByName(name);
    }

}
