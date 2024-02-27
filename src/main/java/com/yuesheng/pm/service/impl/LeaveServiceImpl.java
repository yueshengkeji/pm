package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.Leave;
import com.yuesheng.pm.mapper.LeaveMapper;
import com.yuesheng.pm.service.LeaveService;
import com.yuesheng.pm.util.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 96339 on 2016/12/28.
 */
@Service("leaveService")
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveMapper leaveMapper;
    @Override
    public Leave getLeaveById(String id) {
        return leaveMapper.getLeaveById(id);
    }

    @Override
    public List<Leave> getLeaveByStaff(String staffId) {
        return leaveMapper.getLeaveByStaff(staffId);
    }

    @Override
    public Double getLeaveSumByStaff(String staffId, String startDate, String endDate) {
        Map<String, String> params = new HashMap<>(16);
        params.put("staffId",staffId);
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        return leaveMapper.getLeaveSumByStaff(params);
    }

    @Override
    public void insert(Leave leave) {
        if (Objects.isNull(leave.getLeaveNumber())) {
            leave.setLeaveNumber(0.0);
        }
        leave.setId(UUID.randomUUID().toString());
        leave.setDate(DateFormat.getDate());
        leave.setBillsDate(leave.getDate());
        leaveMapper.insert(leave);
    }

    @Override
    public void approve(String frameId, int status, String staffId, String date) {
        leaveMapper.approve(frameId,status,staffId,date);
    }

    @Override
    public void delete(String id) {
        leaveMapper.delete(id);
    }

    @Override
    public List<Leave> getLeaveByParam(Map<String, Object> params) {
        return leaveMapper.getLeaveByParam(params);
    }

    @Override
    public int getCountByParam(Map<String, Object> params) {
        return leaveMapper.getCountByParam(params);
    }

    @Override
    public List<Leave> getApproveLeaveByParam(HashMap<String, Object> param) {
        return leaveMapper.getApproveLeaveByParam(param);
    }

    @Override
    public void checkOa(FlowMessage message){
        approve(message.getFrameId(),1,message.getLastApproveUser().getId(),DateFormat.getDate());
    }
}
