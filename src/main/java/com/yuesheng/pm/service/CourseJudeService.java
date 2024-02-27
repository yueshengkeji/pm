package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.CourseJudge;

import java.util.List;

/**
 * Created by 96339 on 2017/3/16 步骤判断服务.
 */
public interface CourseJudeService {
    /**
     * 添加流程发起的过程判断对象
     * @param judge
     */
    void addCourseJudgeForMsg(CourseJudge judge);

    /**
     * 获取审批消息实例中的步骤判断列表
     *
     * @param courseId 步骤实例id
     * @return
     */
    List<CourseJudge> getByCourse(String courseId);

    /**
     * 添加步骤判断条件
     *
     * @param judge
     * @return
     */
    int insert(CourseJudge judge);

    /**
     * 更新步骤判断条件
     *
     * @param judge
     * @return
     */
    int update(CourseJudge judge);

    /**
     * 获取步骤判断条件列表
     *
     * @param courseId 过程id
     * @return
     */
    List<CourseJudge> getJudgeForMsg(String courseId);

    /**
     * 删除过程条件
     *
     * @param courseId 过程id
     * @return
     */
    int deleteByCourse(String courseId);

    /**
     * 删除过程条件
     *
     * @param id 主键
     * @return
     */
    int deleteById(String id);
}
