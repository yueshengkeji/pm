package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Expense;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExpenseService
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:03
 */
public interface ExpenseService {
    int insert(Expense expense);

    int delete(String id);

    List<Expense> list(Map<String,Object> params);

    Expense selectById(String id);

    /**
     * 更新报销单
     * @param expense
     * @return
     */
    int update(Expense expense);
}
