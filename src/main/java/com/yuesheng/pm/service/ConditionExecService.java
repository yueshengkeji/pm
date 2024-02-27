package com.yuesheng.pm.service;

/**
 * Created by Administrator on 2019-09-18 条件表达式执行器.
 */
public interface ConditionExecService {
    /**
     * 字符串比较
     *
     * @param value1 比较值1
     * @param value2 比较值2
     * @return
     */
    int compareString(String value1, String value2);

    /**
     * 数字比较
     *
     * @param value1 比较值1
     * @param value2 比较值2
     * @return
     */
    int compareNumber(Double value1, Double value2);
}
