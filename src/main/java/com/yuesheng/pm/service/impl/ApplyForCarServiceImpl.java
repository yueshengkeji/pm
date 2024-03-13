package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ApplyForCar;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.StatisticByDriver;
import com.yuesheng.pm.entity.StatisticByProject;
import com.yuesheng.pm.mapper.ApplyForCarMapper;
import com.yuesheng.pm.service.ApplyForCarService;
import com.yuesheng.pm.service.FlowNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplyForCarServiceImpl implements ApplyForCarService {
    @Autowired
    private ApplyForCarMapper applyForCarMapper;
    @Autowired
    private FlowNotifyService flowNotifyService;

    @Override
    public List<ApplyForCar> selectByParam(Integer pageNum,Integer pageSize, Map<String, Object> params) {
        PageHelper.startPage(pageNum,pageSize,false);
        return applyForCarMapper.selectByParam(params);
    }

    @Override
    public Integer getCountByParam(Map<String, Object> params) {
        return applyForCarMapper.getCountByParam(params);
    }

    @Override
    public List<ApplyForCar> selectByParamNoPage(Map<String, Object> params) {
        return applyForCarMapper.selectByParamNoPage(params);
    }

    @Override
    public ApplyForCar selectById(Integer id) {
        return applyForCarMapper.selectById(id);
    }

    @Override
    public int insert(ApplyForCar applyForCar) {
        return applyForCarMapper.insert(applyForCar);
    }

    @Override
    public int update(ApplyForCar applyForCar) {
        return applyForCarMapper.update(applyForCar);
    }

    @Override
    public int delete(String id) {
        return applyForCarMapper.delete(id);
    }

    @Override
    public ApplyForCar selectByMarkId(String markId) {
        return applyForCarMapper.selectByMarkId(markId);
    }

    @Override
    public List<ApplyForCar> selectAll() {
        return applyForCarMapper.selectAll();
    }

    @Override
    public List<StatisticByProject> selectByGroup(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return applyForCarMapper.selectByGroup();
    }

    @Override
    public Integer selectByGroupCounts() {
        return applyForCarMapper.selectByGroupCounts();
    }

    @Override
    public List<StatisticByDriver> selectByDate(Integer pageNum,Integer pageSize,String startDate, String endDate) {
        PageHelper.startPage(pageNum,pageSize);
        return applyForCarMapper.selectByDate(startDate,endDate);
    }

    @Override
    public Integer selectByDateCounts(String startDate, String endDate) {
        return applyForCarMapper.selectByDateCounts(startDate,endDate);
    }

    @Override
    public boolean setCheckState(String id) {
        applyForCarMapper.setCheckState(Integer.valueOf(id));
        return true;
    }

    @Override
    public boolean setDriver(Staff driverStaff, Integer id) {
        applyForCarMapper.setDriver(driverStaff.getName(),driverStaff.getId(),id);
        List<Staff> list = new ArrayList<>();
        list.add(driverStaff);
        Map<String,Object> msg = new HashMap<>();
        msg.put("title","通知");
        msg.put("content","您有新的任务");
        msg.put("url","/managent/systemNotify/applyForCar/driverTaskDetail/" + id);
        msg.put("mTitle",200);
        flowNotifyService.msgNotify(list,msg);
        return true;
    }

    @Override
    public List<ApplyForCar> getDriverTask(Integer pageNum,Integer pageSize,String driverId) {
        PageHelper.startPage(pageNum,pageSize,false);
        return applyForCarMapper.getDriverTask(driverId);
    }

    @Override
    public Integer getDriverTaskCounts(String driverId) {
        return applyForCarMapper.getDriverTaskCounts(driverId);
    }

    @Override
    public List<ApplyForCar> getDriverTaskDone(Integer pageNum,Integer pageSize, String driverId) {
        PageHelper.startPage(pageNum,pageSize);
        return applyForCarMapper.getDriverTaskDone(driverId);
    }

    @Override
    public Integer getDriverTaskDoneCounts(String driverId) {
        return applyForCarMapper.getDriverTaskDoneCounts(driverId);
    }

    @Override
    public List<ApplyForCar> getTaskToDo() {
        return applyForCarMapper.getTaskToDo();
    }
}
