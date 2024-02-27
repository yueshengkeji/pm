package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.FlowApprove;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-16 审批流程步骤mapper.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
@Mapper
public interface FlowApproveMapper {
    /**
     * 添加单个审批步骤对象
     *
     * @param approve 审批步骤对象
     */
    void addApprove(FlowApprove approve);

    int addApproveRecord(FlowApprove approve);

    /**
     * 更新消息状态为已读
     */
    int updateReadState(FlowApprove flowApprove);

    /**
     * 更新消息状态为已读
     */
    int updateReadStateRecord(FlowApprove flowApprove);

    /**
     * 获取流程的审批集合
     *
     * @param messageId 流程id sdpo003主键
     * @return 审批消息集合
     */
    List<FlowApprove> getFlowApproveByMessageId(@Param("messageId") String messageId);

    /**
     * 更新消息状态集合
     *
     * @param params {state:状态吗，ids:消息id集合}
     */
    void updateStates(@Param("approves") FlowApprove[] params);

    /**
     * 根据【我的审批】id获取审批过程消息对象
     *
     * @param attachId 审批附表id
     * @return 审批消息对象
     */
    FlowApprove getFlowApproveByAttached(String attachId);

    /**
     * 通过id获取审批步骤状态
     *
     * @param id 审批步骤状态 主键 sdpo004 po00401
     * @return
     */
    FlowApprove getFlowApproveById(String id);

    /**
     * 更新审批流程中步骤的其他人员状态
     *
     * @param flowApprove
     */
    void updateOtherStatus(FlowApprove flowApprove);

    /**
     * 更新审批流程中步骤的其他人员状态
     *
     * @param flowApprove
     */
    void updateOtherStatusRecord(FlowApprove flowApprove);

    /**
     * 获取【我的审批】步骤对象
     *
     * @param flowMessageId 【我的发起】sdpo003主键
     * @param courseId      过程id
     * @param status        审批状态
     * @return
     */
    List<FlowApprove> getApproveByMsgAndHistory(@Param("flowMessageId") String flowMessageId, @Param("courseId") String courseId, @Param("status") String status);

    /**
     * 判断流程步骤是否审批过
     *
     * @param flowApprove 审批流程步骤对象
     * @return
     */
    FlowApprove isApprove(FlowApprove flowApprove);

    /**
     * 查询指定过程审批记录列表
     *
     * @param historyId 流程记录id
     * @param courseId  过程id
     * @param msgId     消息id
     * @return
     */
    List<FlowApprove> getApproveList(@Param("historyId") String historyId, @Param("courseId") String courseId, @Param("msgId") String msgId);

    /**
     * 更新审批时间
     *
     * @param date
     * @param id
     * @return
     */
    Integer updateApproveDate(@Param("date") String date, @Param("id") String id);

    /**
     * 更新审批时间
     *
     * @param date
     * @param id
     * @return
     */
    Integer updateApproveDateRecord(@Param("date") String date, @Param("id") String id);

    /**
     * 获取超时未审批的消息集合
     *
     * @param params {datetime:指定当前比较时间，{hour:当前时间之前多少毫秒}}
     * @return
     */
    List<FlowApprove> getMessageByTimeout(Map<String, String> params);

    /**
     * 添加推送记录到数据库
     *
     * @param id    我的审批主键
     * @param state 状态:0=已发，1=未发送
     */
    void insertEmailHistory(@Param("id") String id, @Param("state") String state);

    /**
     * 获取我的审批状态
     *
     * @param id 我的审批主键
     * @return
     */
    String getApproveState(String id);

    /**
     * 更新审批标签
     *
     * @param id     消息id
     * @param fqFlag 标签数据
     */
    void updateFlag(@Param("id") String id, @Param("fqFlag") String fqFlag);

    /**
     * 更新审批标签
     *
     * @param id     消息id
     * @param fqFlag 标签数据
     */
    void updateFlagRecord(@Param("id") String id, @Param("fqFlag") String fqFlag);

    /**
     * 查询存在标签的审批过程对象集合
     *
     * @param messageId 主流程消息id
     * @param flag      标签数据
     * @return
     */
    List<FlowApprove> getApproveByFlag(@Param("messageId") String messageId, @Param("flag") String flag);

    /**
     * 获取最后一个审批过程对象
     *
     * @param flowMessageId 主流程消息id
     * @return
     */
    FlowApprove getLastApprove(@Param("flowMessageId") String flowMessageId);

    /**
     * 获取审批消息推送记录状态
     *
     * @param id
     * @return
     */
    String getEmailHistory(@Param("id") String id);

    /**
     * 获取所有审批步骤消息 ，可忽略userId
     *
     * @param pmap
     * @return
     */
    List<FlowApprove> getMessagesAll2(Map<String, Object> pmap);

    List<FlowApprove> getMessagesV2(Map<String, Object> pmap);

    /**
     * 获取到期未处理的审批数据
     *
     * @return
     */
    List<FlowApprove> getPastApprove();

    /**
     * 更新审批消息状态（包括记录表）
     *
     * @param flowApprove
     * @return
     */
    Integer updateApproveState(FlowApprove flowApprove);

    /**
     * 通过过程id和发起流程实例id查询查询审批消息列表
     *
     * @param courseId  过程id
     * @param messageId 流程消息实例
     * @return
     */
    List<FlowApprove> getByCourseId(@Param("courseId") String courseId, @Param("messageId") String messageId);

    /**
     * 查询异常流程
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    List<FlowApprove> queryErrorFlow(@Param("startDatetime") String startDatetime,
                                     @Param("endDatetime") String endDatetime);

    /**
     * 查询审批记录
     *
     * @param params
     * @return
     */
    List<FlowApprove> getMessageHistory(HashMap<String, String> params);

    /**
     * 更新审批状态记录
     * @param flowApprove
     * @return
     */
    int updateApproveStateRecord(FlowApprove flowApprove);

    /**
     * 查询无效数据
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    List<FlowApprove> queryExpireData(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 删除审批消息数据
     *
     * @param id
     * @return
     */
    int deleteById(@Param("id") String id);

    /**
     * 批量删除审批消息数据
     *
     * @param id
     * @return
     */
    int deleteInId(@Param("id") String id);

    /**
     * 查询记录表
     *
     * @param id
     * @return
     */
    FlowApprove queryRecordById(String id);

    /**
     * 查询未插入记录表的审批数据
     *
     * @param startTime 开始时间
     * @param endTime   截止时间
     * @return
     */
    List<FlowApprove> queryNoRecord(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 获取分期支付标记数据
     *
     * @return
     */
    List<FlowApprove> queryFqFlagList(@Param("startTime") String startTime,
                                      @Param("endTime") String endTime);

    /**
     * 获取审批记录列表
     * * @param flowMessageId 流程消息实例id
     *
     * @return
     */
    List<FlowApprove> getFlowApproveRecordByMessageId(String flowMessageId);

    /**
     * 查询消息总数
     * @param param
     * @return
     */
    Integer notifyMyCount(HashMap<String, Object> param);

    /**
     * 标记用户所有知会未读消息为已读
     * @param staffId
     * @return
     */
    int updateStateAll(@Param("staffId") String staffId);
    /**
     * 标记用户所有知会未读消息为已读
     * @param staffId
     * @return
     */
    int updateStateAllRecord(@Param("staffId") String staffId);

    /**\
     * 获取审批消息实例
     * @param courseId 过程id
     * @param flowMessageId 流程实例id
     * @return
     */
    List<FlowApprove> getFlowApproveByCourse(@Param("courseId") String courseId,@Param("flowMessageId") String flowMessageId);

    /**
     * 删除通知记录
     * @param id 审批消息实例
     * @return
     */
    int deleteNotifyHistory(@Param("id") String id);

    /**
     * 更新最后审批标记
     * @param id
     * @param lastCourseState
     * @return
     */
    int updateLastCourse(@Param("id") String id, @Param("lastCourseState") int lastCourseState);

    /**
     * 获取审批表记录数据
     * @param params
     * @return
     */
    List<FlowApprove> getMessageHistoryNow(HashMap<String, String> params);

    /**
     * 查询审批表缺失的异常记录信息
     * @param startDatetime 开始日期
     * @param endDatetime 截止日期
     * @return
     */
    List<FlowApprove> queryErrorRecord(@Param("startDatetime") String startDatetime,
                                       @Param("endDatetime") String endDatetime);

    /**
     * 删除通知记录
     * @param approveIds
     * @return
     */
    int deleteNotifyHistorys(@Param("approveIds") String approveIds);

    /**
     * 删除审批数据
     * @param msgId 流程实例id
     * @return
     */
    int deleteByMsgId(@Param("msgId") String msgId);
    /**
     * 删除审批记录数据
     * @param msgId 流程实例id
     * @return
     */
    int deleteRecordByMsgId(@Param("msgId") String msgId);

    /**
     * 删除记录
     * @param id 主键
     * @return
     */
    int deleteRecordById(String id);

    /**
     * 查询审批消息
     * @param flowMessageId 流程实例id
     * @param courseId 过程id
     * @param staffId 接受人id
     * @return
     */
    FlowApprove getFlowApproveByStaff(@Param("flowMessageId") String flowMessageId,
                                      @Param("courseId") String courseId,
                                      @Param("staffId") String staffId);
}
