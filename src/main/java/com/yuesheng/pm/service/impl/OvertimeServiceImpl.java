package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.Overtime;
import com.yuesheng.pm.mapper.OvertimeMapper;
import com.yuesheng.pm.service.FlowApproveService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.OvertimeService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 96339 on 2017/5/10.
 */
@Service("overtimeService")
public class OvertimeServiceImpl implements OvertimeService {
    @Autowired
    private OvertimeMapper overtimeMapper;
    @Autowired
    private FlowMessageService messageService;
    @Autowired
    private FlowApproveService approveService;

    @Override
    public void insert(Overtime overtime) {
        overtimeMapper.insert(overtime);
    }

    @Override
    public int delete(String id) {
        return overtimeMapper.delete(id);
    }

    @Override
    public Overtime queryById(String id) {
        return overtimeMapper.queryById(id);
    }

    @Override
    public List<Overtime> getByParam(Map<String, Object> params) {
        return overtimeMapper.getByParam(params);
    }

    @Override
    public int getCountByParam(Map<String, Object> params) {
        return overtimeMapper.getCountByParam(params);
    }

    @Override
    public int approve(String id) {
        Overtime o = queryById(id);
        if (!Objects.isNull(o)) {
            o.setApprove(1);
            o.setApproveDate(DateUtil.getDatetime());
            approve(o);
            return 1;
        }
        return -1;
    }

    @Override
    public int approve(Overtime item) {
        return overtimeMapper.approve(item);
    }

    @Override
    public int updateState(String startDate, String endDate) {
        Map<String,Object> param = new HashMap<>();
        param.put("begin",startDate);
        param.put("end",endDate);
        List<Overtime> overtimeList = getByParam(param);
        overtimeList.forEach(item->{
            FlowMessage fm = messageService.getMessageByFrameId(item.getId());
            if(!Objects.isNull(fm) && fm.getState() == 2){

                if(StringUtils.isBlank(fm.getDate())){
                    approveService.queryLastApprove(fm);
                }

                item.setApprove(1);
                item.setApproveDate(fm.getDate());
                approve(item);
            }
        });
        return 0;
    }
}
