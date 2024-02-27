package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.FLowConsentModel;
import com.yuesheng.pm.model.SelectApprove;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-16 发起流程步骤服务.
 */
public interface FlowApproveService {
    /**
     * 添加单个审批步骤对象
     *
     * @param approve 审批步骤对象
     */
    void addApprove(FlowApprove approve);

    /**
     * 更新消息状态
     *
     * @param state 状态码
     * @param id    消息id
     */
    int updateState(int state, String id);

    /**
     * 获取流程的审批集合
     *
     * @param messageId 流程id sdpo003主键
     * @return 审批消息集合
     */
    List<FlowApprove> getFlowApproveByMessageId(String messageId);

    /**
     * 更新消息状态集合
     *
     * @param flowApproves 消息对象集合
     */
    void updateStates(FlowApprove[] flowApproves);

    /**
     * 根据【我的审批】id获取审批过程消息对象
     *
     * @param id 审批附表id
     * @return 审批消息对象
     */
    FlowApprove getFlowApproveByAttached(String id);

    /**
     * 更新【我的审批步骤】状态
     *
     * @param flowApprove 【我的审批】步骤服务
     */
    void updateFlowApproveStatus(FlowApprove flowApprove);

    /**
     * 通过id获取审批步骤状态
     *
     * @param id 审批步骤状态 主键 sdpo004 po00401
     * @return
     */
    FlowApprove getFlowApproveById(String id);

    /**
     * 更新审批消息的其他人员状态
     * （id属性字符串为in方式，更新多个，格式:'id1','id2'）
     *
     * @param flowApprove 【我的审批】步骤对象
     */
    void updateOtherStatus(FlowApprove flowApprove);

    /**
     * 获取【我的审批】步骤对象
     *
     * @param flowMessageId 【我的发起】sdpo003 主键
     * @param courseId      步骤id
     * @param status        状态码
     * @return
     */
    List<FlowApprove> getApproveByMsgAndHistory(String flowMessageId, String courseId, String status);

    /**
     * 判断该步骤是否审批过
     *
     * @param flowApprove 流程过程对象
     * @return
     */
    FlowApprove isApprove(FlowApprove flowApprove);

    /**
     * 同意审批
     *
     * @param approve [我的审批对象]
     * @return
     */
    FLowConsentModel consentApprove(FlowApprove approve);

    /**
     * 通过步骤id 和 消息实例 查询审批过程列表
     *
     * @param courseId  步骤id
     * @param messageId 流程发起实例
     * @return
     */
    List<FlowApprove> getByCourseId(String courseId, String messageId);

    /**
     * 自由选人的审批通过
     *
     * @param approve      审批过程人员对象
     * @param coursePerson 流程过程
     * @return
     */
    Map<String, Object> consentByPerson(FlowApprove approve, CoursePerson coursePerson, FlowCourse flowCourse);

    /**
     * 中断流程流转
     *
     * @param approve 审批步骤对象
     */
    int breakFlow(FlowApprove approve);

    List<FlowApprove> isRecall(FlowApprove approve, FlowMessage msg);

    /**
     * 判断是否可以撤回操作
     *
     * @param approve 待撤回的审批消息
     * @param msg   流程实例
     * @return
     */
    Integer recall(FlowApprove approve, FlowMessage msg);

    /**
     * 更新审批时间
     *
     * @param data
     * @param id
     */
    Integer updateApproveDate(String data, String id);

    /**
     * 更新审批消息状态
     *
     * @param flowApprove
     * @return
     */
    Integer updateApproveState(FlowApprove flowApprove);

    /**
     * 获取审批（知会）消息
     *
     * @param userId    用户id
     * @param start     开始时间
     * @param end       截止时间
     * @param params    当前消息状态：{0：未读，1：已读，3：同意，5:知会未读，6：知会已读，7：驳回,8:忽略}
     * @param msgStates 审批消息主对象状态 {1:审批中（处理中），4：取消,3:已读，2：流程已完成}
     * @param type      用户id类型： 1=指定发送人，0=指定接收人
     * @return
     */
    List<FlowApprove> getMessages(String userId, String start, String end, List<String> params, List<String> msgStates, Integer type, String searchText, Integer pageNum,Integer pageSize, String fqFlag);

    List<FlowApprove> getMessages(String userId,
                                  String start,
                                  String end,
                                  List<String> states,
                                  List<String> msgStates,
                                  Integer type,
                                  String searchText,
                                  Integer page,
                                  Integer itemsPerPage,
                                  String fqFlag, String order);

    /**
     * 获取超时未审批的消息集合
     *
     * @param params {datetime:指定时间，hour:小时数}
     */
    List<FlowApprove> getMessageByTimeout(Map<String, String> params);

    /**
     * 添加推送记录到数据库
     *
     * @param id    我的审批主键
     * @param state 状态:0=已发，1=未发送
     */
    void insertEmailHistory(String id, String state);

    /**
     * 获取我的审批状态
     *
     * @param id 我的审批主键
     * @return
     */
    String getApproveState(String id);

    /**
     * 获取审批消息集合
     *
     * @param userId     用户id,null=加载所有用户数据
     * @param start      开始时间
     * @param end        截止时间
     * @param params     当前消息状态：{0：未读，1：已读，3：同意，5:知会未读，6：知会已读，7：驳回,8:忽略}
     * @param msgStates  审批消息主对象状态 {1:审批中（处理中），4：取消,3:已读，2：流程已完成}
     * @param type       用户id类型： 1=指定发送人，0=指定接收人
     * @param searchText 过滤字符串
     * @param flowFilter 流程id过滤
     * @return
     */
    List<FlowApprove> getMessagesAll(String userId, String start, String end, List<String> params, List<String> msgStates, Integer type, String searchText, String flowFilter, Integer pageNum,Integer pageSize);

    /**
     * 为审批消息更新标签
     *
     * @param id     消息id
     * @param fqFlag 标签数据
     */
    void updateFlag(String id, String fqFlag);

    /**
     * 根据主流程消息id判断过程中是否存在标签
     *
     * @param messageId 主流程消息id
     * @param flag      标签数据
     * @return 存在返回true，不存在返回false
     */
    boolean isFlag(String messageId, String flag);

    /**
     * 获取最后一个审批完成对象
     *
     * @param flowMessageId 流程主消息id
     * @return
     */
    FlowApprove getLastApprove(String flowMessageId);

    Map<String, Object> appendApprove(FlowApprove approve);

    void inFromUser(String[] staffList, String flowApproveId);

    /**
     * 自由选人审批
     *
     * @param selectApprove
     * @param attribute
     * @return {consent='no-person'(未指定审批人),consent='ok'(操作成功)}
     */
    FLowConsentModel publicApprove(SelectApprove selectApprove, Staff attribute);

    /**
     * 获取审批消息推送记录状态
     *
     * @param id
     * @return
     */
    String getEmailHistory(String id);

    List<FlowApprove> getMessagesAll2(String userId,
                                      String start,
                                      String end,
                                      Integer[] params,
                                      Integer[] msgStates,
                                      Integer type,
                                      String searchText,
                                      String flowFilter,
                                      Integer pageNum,
                                      Integer pageSize);

    /**
     * 通过流程审批撤回
     *
     * @param approve
     * @param recallContent 是否清空处理时间和处理内容
     * @return
     */
    int recall(FlowApprove approve, Boolean recallContent);

    /**
     * 获取到期未处理的审批数据
     *
     * @return
     */
    List<FlowApprove> getPastApprove();

    /**
     * 获取异常流程
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    List<FlowApprove> queryErrorFlow(String startDatetime, String endDatetime);

    /**
     * 获取审批记录表数据
     * @param params
     * @return
     */
    List<FlowApprove> getMessagesHistory(HashMap<String, String> params);

    void clearNotify(String endDate);

    /**
     * 清除审批完成的过期数据
     * @param endDate 截止时间
     */
    void clearExpireData(String endDate);

    void checkNoRecord(String startTime, String endTime);

    /**
     * 检查未插入记录表的审批数据，并自动添加到记录表
     * @param endTime
     */
    void checkNoRecord(String endTime);

    void checkPrevDayNoRecord();

    /**
     * 获取审批记录列表
     * @param flowMessageId 流程消息实例id
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
    int updateStateAll(String staffId);

    /**
     * 获取审批消息实例
     * @param  courseId 过程id
     * @param flowMessageId 流程实例id
     * @return
     */
    List<FlowApprove> getFlowApproveByCourse(String courseId, String flowMessageId);

    /**
     * 删除通知记录
     *
     * @param id 审批消息实例
     */
    void deleteNotifyHistory(String id);

    /**
     * 设置审批过程是否到最后一步
     * @param fa
     * @return
     */
    FlowApprove setLastCourse(FlowApprove fa);

    /**
     * 获取审批表记录数据
     * @param params
     * @return
     */
    List<FlowApprove> getMessagesHistoryNow(HashMap<String, String> params);

    /**
     * 查询并修复异常记录数据
     * @param startDatetime 开始日期
     * @param endDatetime 截止日期
     */
    void queryErrorRecord(String startDatetime,String endDatetime);

    /**
     * 删除流程消息
     * @param msgId 流程实例id
     * @return
     */
    int deleteByMsgId(String msgId);

    /**
     * 查询审批消息
     * @param flowMessageId 流程实例id
     * @param courseId 过程id
     * @param staffId 接受人id
     * @return
     */
    FlowApprove getFlowApproveByStaff(String flowMessageId, String courseId, String staffId);

    /**
     * 查询流程实例最后点击同意的审批人
     * @param fm
     * @return
     */
    FlowApprove queryLastApprove(FlowMessage fm);

    /**
     * 蒋流程驳回到指定节点
     * @param fa
     * @param link
     * @return
     */
    int breakFLow(FlowApprove fa, FlowCourse link);
}
