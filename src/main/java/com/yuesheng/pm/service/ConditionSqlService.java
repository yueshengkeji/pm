package com.yuesheng.pm.service;


import com.yuesheng.pm.entity.Condition;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019-09-18 条件表达式sql服务接口.
 */
public interface ConditionSqlService {
    /**
     * 查询条件表达式
     *
     * @param courseId 过程id
     * @return
     */
    List<Condition> queryCondition(String courseId);

    /**
     * 获取条件表达式
     *
     * @param tableName 表名称
     * @return 表达式列与列说明Map
     */
    Map<String, String> queryConditionColumn(String tableName);

}
