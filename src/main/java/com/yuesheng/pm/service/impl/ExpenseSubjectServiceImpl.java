package com.yuesheng.pm.service.impl;


import com.yuesheng.pm.entity.ExpenseSubModel;
import com.yuesheng.pm.entity.ExpenseSubject;
import com.yuesheng.pm.mapper.ExpenseSubjectMapper;
import com.yuesheng.pm.service.ExpenseSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName ExpenseSubjectServiceImpl
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:30
 */
@Service
public class ExpenseSubjectServiceImpl implements ExpenseSubjectService {

    @Autowired
    private ExpenseSubjectMapper expenseSubjectMapper;


    @Override
    public int insert(ExpenseSubject expenseSubject) {
        expenseSubject.setId(UUID.randomUUID().toString());
        return expenseSubjectMapper.insert(expenseSubject);
    }

    @Override
    public int delete(String id) {
        return expenseSubjectMapper.delete(id);
    }

    @Override
    public List<ExpenseSubject> getByMark(String mark) {
        return expenseSubjectMapper.getByMark(mark);
    }

    @Override
    public int deleteByMark(String mark) {
        return expenseSubjectMapper.deleteByMark(mark);
    }

    @Override
    public List<ExpenseSubModel> list(Map<String, Object> params) {
        return expenseSubjectMapper.list(params);
    }

    @Override
    public int update(ExpenseSubject item) {
        return expenseSubjectMapper.update(item);
    }
}
