package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowCourse;
import com.yuesheng.pm.entity.FlowMessage;

import java.util.List;

/**
 * Created by Administrator on 2016-08-16 流程过程服务接口.
 *
 */
public interface FlowCourseService {
    /**
     * 根据流程id获取第一个审批过程对象
     * @param flowId 流程对象id
     * @return 流程过程
     */
    FlowCourse getFlowCourseFirst(String flowId);

    /**
     * 根据流程id获取该流程步骤集合
     * @param flowId 流程id
     * @return 过程集合
     */
    List<FlowCourse> getFlowCourseByFlow(String flowId);

    /**
     * 添加审批过程步骤到sdpo020b表中
     */
    void addFlowCourseB(FlowCourse flowCourse);

    void addFlowCourseBInstance(FlowCourse flowCourse);

    /**
     * 获取过程对象
     * @param currentId 过程id
     * @return 过程对象
     */
    FlowCourse getFlowCourseById(String currentId);

    /**
     * 获取当前审批流程的下一个步骤
     *
     * @param currentId  当前步骤id
     * @param histroryId 流程记录id
     * @param msg 流程消息实例
     * @return 审批流程步骤
     */
    FlowCourse getNextFlowCourseBByCourseId(String currentId, String histroryId, FlowMessage msg);

    /**
     * 获取当前步骤知会集合
     * @param courseId 步骤id
     * @param flowMessageId 流程记录id
     * @return 知会步骤集合
     */
    FlowCourse getNotifyCourseBByMsgId(String courseId, String flowMessageId);

    /**
     * 获取当前过程的下一个过程集合
     * @param id
     * @return
     */
    List<FlowCourse> getNextCourses(String id);

    /**
     * 获取该过程的父过程id
     * @param id
     * @return
     */
    String getParentId(String id);

    /**
     * 更新审批步骤对象
     * @param course
     */
    void update(FlowCourse course);

    /**
     * 添加过程
     * @param course
     */
    void insert(FlowCourse course);

    /**
     * 删除过程
     *
     * @param courseId
     */
    void delete(String courseId);

    /**
     * 添加过程条件判断
     *
     * @param course
     * @return
     */
    int insertJudge(FlowCourse course);

    /**
     * 更新过程条件判断
     *
     * @param course
     * @return
     */
    int updateJudge(FlowCourse course);

    /**
     * 获取已发起的流程步骤
     *
     * @param courseId 步骤id
     * @return
     */
    FlowCourse getNextFlowCourseBByCourseId(String courseId);

    /**
     * 获取当前流程步骤
     *
     * @param courseId   步骤id
     * @param histroryId 流程记录id
     * @return
     */
    FlowCourse getThanFlowCourseBByCourseId(String courseId, String histroryId);

    /**
     * 更新审批实例步骤
     * @param mfc
     * @return
     */
    int updateThanFlowCourseBByCourseId(FlowCourse mfc);

    /**
     * 查询过程是否存在
     * @param id 过程ID
     * @param flowId 流程id
     * @return
     */
    FlowCourse getById(String id, String flowId);

    /**
     * 获取过程实例
     * @param id 主键
     * @param flowId 流程记录主键
     * @return
     */
    FlowCourse getInstanceById(String id, String flowId);

    /**
     * 流程记录主键
     * @param flowMessageId 流程实例id
     * @param flowHistoryId 流程记录id
     * @return
     */
    List<FlowCourse> getInstanceByFlowId(String flowMessageId, String flowHistoryId);

    /**
     * 清除已完成的流程过程记录，被删除的记录数据会记录到sdpo002b_history中
     * @param startDatetime
     * @param endDatetime
     */
    void clearCourseB(String startDatetime, String endDatetime);

    int deleteInstanceByFlowId(String histroryId);

    /**
     * 查询审批实例表过程
     * @param courseId 过程id
     * @return
     */
    FlowCourse getFlowCourseBById(String courseId);

    /**
     * 查询
     * @param courseId
     * @return
     */
    FlowCourse getFlowCourseBByInstance(String courseId);

    /**
     * 删除过程实例记录
     * @param histroryId
     * @return
     */
    int deleteCourseBInstanceHistory(String histroryId);
}
