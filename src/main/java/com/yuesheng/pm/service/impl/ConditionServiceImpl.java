package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Condition;
import com.yuesheng.pm.mapper.ConditionMapper;
import com.yuesheng.pm.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019-08-26.
 */
@Service
public class ConditionServiceImpl implements ConditionService {
    @Autowired
    private ConditionMapper conditionMapper;

    @Override
    public List<Condition> getByCourseId(String courseId) {
        return conditionMapper.getByCourseId(courseId);
    }

    @Override
    public int insert(Condition condition) {
        return conditionMapper.insert(condition);
    }

    @Override
    public int update(Condition condition) {
        return conditionMapper.update(condition);
    }
}
