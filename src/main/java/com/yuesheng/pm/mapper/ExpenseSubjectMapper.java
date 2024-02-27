package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ExpenseSubModel;
import com.yuesheng.pm.entity.ExpenseSubject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExpenseSubjectMapper
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:16
 */
@Mapper
public interface ExpenseSubjectMapper {
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
