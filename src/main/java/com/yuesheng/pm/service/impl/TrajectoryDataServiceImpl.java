package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.TrajectoryData;
import com.yuesheng.pm.mapper.TrajectoryDataMapper;
import com.yuesheng.pm.service.TrajectoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("trajectoryDataService")
public class TrajectoryDataServiceImpl implements TrajectoryDataService {
    @Autowired
    private TrajectoryDataMapper trajectoryDataMapper;

    @Override
    public void insert(TrajectoryData trajectoryData) {
        trajectoryDataMapper.insert(trajectoryData);
    }

    @Override
    public int updateState(TrajectoryData trajectoryData) {
        return trajectoryDataMapper.updateState(trajectoryData);
    }

    @Override
    public int delete(String id) {
        return trajectoryDataMapper.delete(id);
    }

    @Override
    public List<TrajectoryData> query(Map<String, Object> param) {
        return trajectoryDataMapper.query(param);
    }

    @Override
    public TrajectoryData queryById(String id) {
        return trajectoryDataMapper.queryById(id);
    }

    @Override
    public Integer queryCount(Map<String, Object> param) {
        return trajectoryDataMapper.queryCount(param);
    }

    @Override
    public void approve(String id){
        TrajectoryData data = queryById(id);
        data.setState(1);
        updateState(data);
    }
}
