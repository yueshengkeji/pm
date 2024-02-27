package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;

import java.util.List;

/**
 * Created by Administrator on 2016-08-17 【我的审批附表】服务接口.
 */
public interface MyApproveAttachedService {
    /**
     * 添加【我的审批附表】对象到数据库
     *
     * @param myApproveAttached 【我的审批】附表对象
     */
    void addApproveAttached(MyApproveAttached myApproveAttached);

    /**
     * 【我的审批】之同意
     */
    int consentAttached(FlowApprove flowApprove);

    /**
     * 审批通过后的知会处理
     *
     * @return 自动知会人员集合返回0，自由选人返回人员集合
     */
    List<Staff> notifyMsg(FlowCourse course, FlowApprove approve,
                          FlowMessage flowMsg, MyApprove myApprove, MyApproveMain approveMain);

    /**
     * 处理步骤中人员集合
     *
     * @param flowCourse  【流程过程】sdpo020
     * @param flowApprove 【审批流程消息】sdpo004
     * @param person      【审批步骤人员】sdpo002
     * @param approve     【我的审批】主表对象 sdpo006
     * @param approveMain 【我的审批Main】 sdpo100
     * @param flowMsg     【我的发起】对象 sdpo003
     * @param sendUser    发送用户
     * @param staffList   职员集合
     * @return 返回添加成功的审批人消息列表
     */
    List<FlowApprove> staffDispose(FlowCourse flowCourse, FlowApprove flowApprove, FlowMessage flowMsg, CoursePerson person,
                                   MyApprove approve, MyApproveMain approveMain, String sendUser, List<Staff> staffList);

    /**
     * 通过id获取审批附表对象
     *
     * @param po00418Id 附表id
     * @return 我的审批附表对象
     */
    MyApproveAttached getAttchedById(String po00418Id);

    /**
     * 【我的审批】之撤回
     *
     * @param approve      【我的审批对象】
     * @param nextCourseId 下一个过程id
     * @param flowMsg      【我的发起对象】
     * @return
     */
    void recall(FlowMessage flowMsg, String nextCourseId, FlowApprove approve);

    /**
     * 更新审批步骤所有数据
     *
     * @param attached
     */
    void updateAttached(MyApproveAttached attached);

    /**
     * 更新审批步骤状态
     *
     * @param ma
     * @return
     */
    int updateState(MyApproveAttached ma);

    /**
     * 删除数据，主表一起删除
     * @param id
     * @return
     */
    int deleteById(String id);
}
