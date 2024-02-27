package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.CoursePerson;
import com.yuesheng.pm.entity.CoursePersonAttached;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016-08-17 审批过程人员记录服务.
 */
public interface CoursePersonAttachedService {
    /**
     * 添加审批过程记录人员到数据库
     * @param attached 审批过程关系记录
     */
    void addAttached(CoursePersonAttached attached);
    /**
     * 根据过程id+流程id获取当前流程过程中审批人员集合
     * @param currentId 过程id
     * @param histroryId 审批人员集合
     * @return 审批流程过程
     */
    List<CoursePersonAttached> getPersonByCourseAFlowId(String currentId, String histroryId);
    /**
     * 获取审批人员类型集合
     * @param historyId 流程记录id
     * @param courseId 当前步骤id
     * @param type 处理类型{0：审批，1：知会}
     * @return 步骤中处理人员集合
     */
    List<CoursePerson> getPersonsByType(String historyId, String courseId, int type);

    /**
     * 添加记录
     * @param attached
     * @return
     */
    CoursePersonAttached addHistory(CoursePersonAttached attached);

    /**
     * 删除审批人记录
     * @param flowHistoryId 流程记录id
     * @return
     */
    int deleteByFlowHistoryId(@Param("flowHistoryId") String flowHistoryId);

    /**
     * 清空已完成的流程数据，清空的数据会被转移到history记录表中
     * @param startDatetime 开始日期
     * @param endDatetime 截止日期
     */
    void clearByDatetime(String startDatetime,String endDatetime);

    /**
     * 删除记录
     * @param histroryId 流程记录id
     * @return
     */
    int deleteHistoryByFlowId(String histroryId);

    int deleteByFlowHistoryId(String histroryId, boolean insertHistory);
}
