package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.Leave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/28 请假单服务.
 *
 */
public interface LeaveService {
    /**
     * 获取请假单对象
     *
     * @param id 请假单id
     * @return
     */
    Leave getLeaveById(String id);

    /**
     * 获取请假单集合
     *
     * @param staffId 职员id
     * @return
     */
    List<Leave> getLeaveByStaff(String staffId);

    /**
     * 获取职员请假天数
     *
     * @param staffId   职员id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    Double getLeaveSumByStaff(String staffId, String startDate, String endDate);

    /**
     * 添加请假单
     *
     * @param leave 请假单对象
     */
    void insert(Leave leave);

    /**
     * 审核 | 反审核 请假单
     *
     * @param frameId 主键
     * @param status  2=审核，3=反审核
     * @param staffId 审核人
     * @param date    审核时间
     */
    void approve(String frameId, int status, String staffId, String date);

    /**
     * 删除请假单
     *
     * @param id 请假单id
     */
    void delete(String id);

    /**
     * 获取请假单集合
     * @param params {searchText:搜索文本,sort：排序方式}
     * @return
     */
    List<Leave> getLeaveByParam(Map<String, Object> params);

    /**
     * 根据条件获取总页数
     * @param params 参考getLeaveByParam()
     * @return
     */
    int getCountByParam(Map<String, Object> params);

    /**
     * 获取已审批请假单集合
     * @param param {searchText:搜索文本,sort：排序方式}
     * @return
     */
    List<Leave> getApproveLeaveByParam(HashMap<String, Object> param);

    /**
     * 表单审批完成后触发
     * @param message
     */
    void checkOa(FlowMessage message);
}
