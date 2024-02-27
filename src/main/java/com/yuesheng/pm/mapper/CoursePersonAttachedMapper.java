package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.CoursePerson;
import com.yuesheng.pm.entity.CoursePersonAttached;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-17 审批过程人员记录表mapper.
 * @author XiaoSong
 * @date 2016/08/17
 */
@Mapper
public interface CoursePersonAttachedMapper {
    /**
     * 添加审批过程人员
     * @param attached 审批过程人员记录
     */
    void addPersonAttached(CoursePersonAttached attached);
    /**
     * 添加审批过程人员记录
     * @param attached 审批过程人员记录
     */
    void addHistory(CoursePersonAttached attached);

    /**
     * 删除审批人记录
     * @param flowHistoryId 流程记录id
     * @return
     */
    int deleteByFlowHistoryId(@Param("flowHistoryId") String flowHistoryId);
    /**
     * 获取审批人记录
     * @param flowHistoryId 流程记录id
     * @return
     */
    List<CoursePersonAttached> getByFlowHistoryId(@Param("flowHistoryId") String flowHistoryId);
    /**
     * 根据过程id+流程id获取当前流程过程中审批人员集合
     * @param currentId 过程id
     * @param histroryId 审批人员集合
     * @return 审批流程过程
     */
    List<CoursePersonAttached> getPersonByCourseFlowId(@Param("currentId") String currentId,
                                                       @Param("histroryId") String histroryId);

    /**
     * 获取审批人员类型集合
     * @param histroryId 流程记录id
     * @param courseId 当前步骤id
     * @param type 处理类型{0：审批，1：知会}
     * @return 步骤中处理人员集合
     */
    List<CoursePerson> getPersonsByType(@Param("histroryId") String histroryId, @Param("currentId") String courseId, @Param("type") int type);

    /**
     * 删除审批人类型
     * @param flowHistoryId
     * @return
     */
    int deleteHistory(@Param("flowHistoryId") String flowHistoryId);

}
