package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowApprove;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * 流程变化通知接口
 */
public interface FlowNotifyService {
    /**
     * 流程发生变化时触发
     *
     * @param state       流程状态：1=审批中，2=审批已完成
     * @param notifyStaff 被通知人员集合
     * @param flowApprove 当前审批流程主题对象
     */
    void flowChange(int state, List<Staff> notifyStaff, FlowApprove flowApprove);

    /**
     * 流程驳回时触发
     *
     * @param staff       驳回人员
     * @param notifyStaff 被通知人员集合
     * @param flowApprove 当前审批流程主体对象
     */
    void reject(Staff staff, List<Staff> notifyStaff, FlowApprove flowApprove);

    /**
     * 流程中断时触发
     *
     * @param staff       中断人员
     * @param notifyStaff 被通知人员集合
     * @param flowApprove 当前审批流程主体对象
     */
    void offFlow(Staff staff, List<Staff> notifyStaff, FlowApprove flowApprove);

    /**
     * 知会消息时触发
     *
     * @param staff       知会人员
     * @param notifyStaff 被知会人员集合
     * @param flowApprove 当前审批流程主体对象
     */
    void notifyMsg(Staff staff, List<Staff> notifyStaff, FlowApprove flowApprove);

    /**
     * @param notifyStaff 发送人员集合
     * @param msg         {title:消息标题,content:消息内容,url:点击跳转地址,mTitle:副标题}
     */
    void msgNotify(List<Staff> notifyStaff, Map<String, Object> msg);

    /**
     * 根据职员对象的ID获取微信userID或微信OpenId
     * @param staff
     * @return
     */
    String getWxUserIdOrWxOpenId(Staff staff);

    void msgNotify(Staff manager, Map<String, Object> param);
}
