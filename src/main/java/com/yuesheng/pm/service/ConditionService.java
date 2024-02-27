package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Condition;

import java.util.List;

/**
 * Created by Administrator on 2019-08-26.
 */
public interface ConditionService {
    /**
     * 获取判定条件
     *
     * @param courseId 流程过程id
     * @return
     */
    List<Condition> getByCourseId(String courseId);

    /**
     * 添加条件
     *
     * @param condition
     * @return
     */
    int insert(Condition condition);

    /**
     * 修改条件
     *
     * @param condition
     * @return
     */
    int update(Condition condition);
}
