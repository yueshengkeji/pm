package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.CourseInvoke;
import com.yuesheng.pm.entity.FlowCourse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-16 流程过程mapper.
 * @author XiaoSong
 * @date 2016/08/16
 */
@Mapper
public interface FlowCourseMapper {
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
     * 添加审批过程记录到数据库sdpo020b表中
     * @param flowCourse
     */
    void addFlowCourseBt(FlowCourse flowCourse);

    /**
     * 添加审批过程实例化记录
     * @param flowCourse 审批过程
     */
    void addFlowCourseBInstance(FlowCourse flowCourse);

    /**
     * 获取过程对象
     * @param id 过程id
     * @return 过程对象
     */
    FlowCourse getFlowCourseById(@Param("id") String id);

    /**
     * 根据当前步骤获取知会集合
     * @param courseId 步骤id
     * @param historyId 流程记录id
     * @return 知会步骤
     */
    FlowCourse getNotifyCourseBByMsgId(@Param("courseId") String courseId,@Param("historyId") String historyId);

    /**
     * 获取当前步骤的下一步审批步骤集合
     * @param id 当前步骤主键
     * @return
     */
    List<FlowCourse> getNextCourses(String id);

    /**
     * 获取父过程id集合
     * @param id 父过程id
     * @return
     */
    String getParentId(String id);

    /**
     * 更新审批步骤
     * @param course
     */
    void update(FlowCourse course);

    /**
     * 添加审批步骤
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
     * 更新判断条件sql
     *
     * @param course
     * @return
     */
    int updateJudgeSql(FlowCourse course);

    /**
     * 获取已发起的流程步骤
     *
     * @param courseId 步骤id
     * @return
     */
    FlowCourse getCourseBByCourseId(@Param("id") String courseId);

    /**
     * 获取当前流程步骤
     *
     * @param courseId  步骤id
     * @param historyId 流程记录id
     * @return
     */
    FlowCourse getThanFlowCourseByCourseId(@Param("courseId") String courseId, @Param("historyId") String historyId);

    /**
     * 获取流程步骤
     *
     * @param id     主键
     * @param flowId 流程主键
     * @return
     */
    FlowCourse getById(@Param("id") String id, @Param("flowId") String flowId);

    int updateThanFlowCourseBByCourseId(FlowCourse mfc);

    FlowCourse getInstanceById(@Param("id") String id,@Param("flowId") String flowId);

    /**
     * 执行过程条件sql
     * 此方法如果返回map不为空，则代表条件通过，返回空map则代表条件不通过，
     * （具体数据无法明确，应为每个表单对应的sql和列不同，只能以map形式返回）
     * @param execSql
     * @return
     */
    Map<String, Object> execJudgeSql(@Param("execSql") String execSql);

    List<FlowCourse> getInstanceByFlowId(@Param("flowId") String flowHistoryId);

    /**
     * 添加实例记录
     * @param item
     * @return
     */
    int addFLowCourseBHistory(FlowCourse item);

    /**
     * 添加流程过程实例记录
     * @param item
     * @return
     */
    int addFlowCourseBInstanceHistory(FlowCourse item);

    /**
     * 删除流程过程审批记录数据
     * @param historyId 流程记录id
     * @return
     */
    int deleteBByFlowId(@Param("flowId") String historyId);
    /**
     * 删除流程过程审批实例记录数据
     * @param historyId 流程记录id
     * @return
     */
    int deleteInstanceByFlowId(@Param("flowId") String historyId);

    /**
     * 查询审批实例表过程
     * @param courseId 过程id
     * @return
     */
    FlowCourse getFlowCourseBById(@Param("courseId") String courseId);

    /**
     * 查询过程实例记录
     * @param courseId
     * @return
     */
    FlowCourse getFlowCourseBByInstance(String courseId);

    /**
     * 删除实例记录
     * @param flowHistoryId
     * @return
     */
    int deleteCourseBInstanceHistory(@Param("flowId") String flowHistoryId);

    /**
     * 获取代理信息
     * @param id
     * @return
     */
    CourseInvoke getInvoke(String id);

    /**
     * 更新代理信息
     * @param flowCourse
     * @return
     */
    int updateInvoke(FlowCourse flowCourse);

    /**
     * 删除代理信息
     * @param id
     * @return
     */
    int deleteInvoke(String id);

    /**
     * 添加代理信息
     * @param course
     * @return
     */
    int insertInvoke(FlowCourse course);

    /**
     * 查询流程中指定过程序号后的所有过程
     * @param courseNo 过程序号
     * @param flowId 流程id
     * @return
     */
    List<FlowCourse> getNextCoursesAll(@Param("courseNo") String courseNo,@Param("flowId") String flowId);
}
