package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.FLowConsentModel;
import com.yuesheng.pm.model.FLowMessageQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-16 审批流程消息服务.
 */
public interface FlowMessageService {
    /**
     * 添加审批消息
     *
     * @param flowMessage 审批消息对象
     * @return 影响的行数
     */
    Integer addFlowMessage(FlowMessage flowMessage);

    /**
     * 添加【我的发起】消息到主记录表中
     *
     * @param flowMessage
     */
    void addFlowMessageHistory(FlowMessage flowMessage);

    /**
     * 通过发起id获取消息对象
     * @param id id
     * @return 流程发起消息对象
     */
    FlowMessage getMessageById(String id);

    /**
     * 开始流程流转
     *
     * @param flow        流程对象
     * @param flowMessage 流程信息
     * @param staff       职员对象
     * @param history     流程使用记录主表
     */
    FLowConsentModel startFlow(Flow flow, FlowMessage flowMessage, Staff staff, FlowHistory history, FlowCourse pubPerson);

    CoursePerson saveCoursePerson(FlowMessage flowMessage,
                                  FlowCourse flowCourse,
                                  CoursePerson coursePerson);

    /**
     * 更新消息主表状态{1：审批中，2：已完成，3：中断，4：取消}
     * @param flowMessageId 消息主键
     * @param status 状态
     */
    void updateMsgStatus(String flowMessageId, int status);

    /**
     * 更新发起时间
     * @param data
     * @param id
     * @return
     */
    Integer updateMsgDate(String data, String id);

    /**
     * 获取审批消息集合
     * @param params 用户id
     */
    List<FlowMessage> getMessageByStaff(Map<String,Object> params);

    /**
     * 获取审批消息总数
     * @param params
     * @return
     */
    int getMessageByCount(Map<String, Object> params);

    /**
     * 删除流程消息
     * @param mainId 主键表单id
     */
    void deleteMessage(String mainId);


    void insertCourseRelation(FlowHistory history, List<FlowCourseBRelation> temp);

    List<Staff> getStaffByType(CoursePerson person, Staff staff);

    String getSql(String frameCoding);

    /**
     * 获取我的发起 审批对象
     *
     * @param id frame窗口主键id
     * @return
     */
    FlowMessage getMessageByFrameId(String id);

    /**
     * 删除流程消息实例
     *
     * @param id
     */
    void deleteMessageById(String id);

    /**
     * 更新审批标签
     *
     * @param id     审批主消息id
     * @param fqFlag 标签数据
     */
    void updateFlag(String id, String fqFlag);

    /**
     * 开启流程
     *
     * @param message   流程消息主体对象
     * @param pubPerson 自由选人
     * @param flow      流程对象
     * @param attribute 发起人
     * @return
     */
    Map<String, Object> startFlow(FlowMessage message, FlowCourse pubPerson, Flow flow, Staff attribute);

    /**
     * 流程消息通知
     *
     * @param id
     */
    void flowNotify(String id,FlowMessage message);

    /**
     * 获取审批消息集合
     *
     * @param message 查询参数
     * @return
     */
    List<FlowMessage> getMsgList(FlowMessage message);

    /**
     * 获取流程当前等待处理的位置过程名称
     *
     * @param frameId 表单id
     * @return
     */
    String getFlowMsgStateStr(String frameId);

    /**
     * 查询流程是否出现异常
     *
     * @param msg 流程实例
     * @return
     */
    FlowMessage isError(FlowMessage msg);

    /**
     * 通过审批日期筛选流程实例
     *
     * @param message
     * @param pageBounds
     * @return
     */
    List<FlowMessage> getMsgListByApproveDate(FLowMessageQuery message);

    /**
     * 检查流程状态
     *
     * @param flowMsg 审批消息实例
     * @param approve 最后审批步骤
     * @return
     */
    int checkStatus(FlowMessage flowMsg, FlowApprove approve);

    void checkErrorFlow();

    /**
     * 查询流程实例sql
     * @param id
     * @return
     */
    String getSqlById(String id);

    /**
     * 获取已结束的流程
     * @param startDatetime 开始日期
     * @param endDatetime 截止日期
     * @return
     */
    List<FlowMessage> getOverMessage(String startDatetime, String endDatetime);

    /**
     * 删除流程
     * @param frameId 表单主键
     * @param delHandler 是否触发表单删除事件
     */
    void deleteMessage(String frameId, boolean delHandler);

    void syncDate();

    /**
     * 查询流程实例
     * @param historyId 流程记录id
     * @return
     */
    FlowMessage getByFlowHistory(String historyId);
}
