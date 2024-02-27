package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Expense;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExpenseMapper
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:08
 */
@Mapper
public interface ExpenseMapper {
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

    /**
     * 更新状态
     * @param expense
     * @return
     */
    int updateState(Expense expense);
}
