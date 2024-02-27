package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.MyApproveMain;

/**
 * Created by Administrator on 2016-08-18 【我的审批】超级主表.
 *
 */
public interface MyApproveMainService {
    /**
     * 添加【我的审批】超级主表对象到数据库
     * @param main 【我的审批】超级主表
     */
    void addApproveMain(MyApproveMain main);

    /**
     * 通过sdpo003主键获取第一条记录
     * @param msgId sdpo003主键
     * @return 【我的审批超级对象】
     */
    MyApproveMain getMainByMsgId(String msgId);

    /**
     * 根据日期删除数据
     * @param startDatetime 开始日期
     * @param endDatetime 截止日期
     * @return
     */
    int deleteByDate(String startDatetime,String endDatetime);
}
