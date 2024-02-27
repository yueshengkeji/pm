package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ExpenseSubModel;
import com.yuesheng.pm.entity.ExpenseSubject;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExpenseSubjectService
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:12
 */
public interface ExpenseSubjectService {
    int insert(ExpenseSubject expenseSubject);

    int delete(String id);

    List<ExpenseSubject> getByMark(String mark);

    int deleteByMark(String mark);

    List<ExpenseSubModel> list(Map<String,Object> params);

    /**
     * 更新报销科目信息
     * @param item
     * @return
     */
    int update(ExpenseSubject item);
}
