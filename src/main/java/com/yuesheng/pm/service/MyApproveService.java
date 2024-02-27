package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.MyApprove;
import com.yuesheng.pm.entity.MyApproveAttached;

/**
 * Created by Administrator on 2016-08-17.
 * 【我的审批】服务接口
 */
public interface MyApproveService {
    /**
     * 添加【我的审批】对象
     *
     * @param myApprove         【我的审批】对象
     * @param myApproveAttached 【我的审批】附表
     */
    void addMyApprove(MyApprove myApprove, MyApproveAttached myApproveAttached);

    /**
     * 添加第一步骤人【我的审批】对象
     *
     * @param myApprove 我的审批对象
     */
    void addMyApproveFirst(MyApprove myApprove);

    /**
     * 通过id获取【我的审批消息】对象
     * @param approveId 【我的审批】消息对象
     * @return 审批消息对象
     */
    MyApprove getMessageById(String approveId);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteById(String id);

    int deleteByDate(String startDatetime, String endDatetime);
}
