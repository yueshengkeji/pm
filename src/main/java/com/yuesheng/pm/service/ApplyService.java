package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Apply;
import com.yuesheng.pm.entity.FlowMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-06-20 项目材料申请单接口.
 */
public interface ApplyService {
    /**
     * 获取采购申请单
     * @return
     */
    List<Apply> getApplyList();

    /**
     * 根据指定日期获取采购申请单
     * @param bounds 分页对象
     * @param map   开始日期、结束日期；
     * @return
     */
    List<Apply> getApplysByDate(Integer bounds, Map map);

    /**
     * 更新采购申请单状态
     * @param s 申请单id
     * @param stateAllApply 状态代码
     */
    void updateState(String s, int stateAllApply);

    /**
     * 根据申请单id获取申请单对象
     * @param applyId 申请单id
     * @return 申请单对象
     */
    Apply getApplyById(String applyId);

    /**
     * 获取申请单集合
     * @param params
     * @return
     */
    List<Apply> getApplyList(Map<String, Object> params);

    /**
     * 根据参数获取总数
     * @param params
     * @return
     */
    Integer getApplyListCount(Map<String, Object> params);

    /**
     * 检查设置申请单状态，并自动更新已采购数量
     * @param s
     */
    void setStatus(String s);
    /**
     * 检查申请单已采购状态
     * @param id
     * @param isNoPro
     */
    void checkStatus(String id, boolean isNoPro);

    /**
     * 添加材料申请单
     * @param apply
     */
    void addApply(Apply apply);

    /**
     * 删除申请单
     * @param id
     */
    void delete(String id);

    /**
     * 审核|反审核申请单
     * @param id
     * @param state 2=审核，1=反审核
     * @param coding
     * @param date
     */
    void approve(String id, int state, String coding, String date);

    /**
     * 验证申请单编号是否存在
     *
     * @param seriesNumber 申请单编号
     * @return
     */
    String verifySeries(String seriesNumber);

    /**
     * 更新申请单审批通过时间（包含时分秒）
     *
     * @param id          申请单id
     * @param approveDate 时间字符串
     */
    void updateApproveTime(String id, String approveDate);

    /**
     * 通过项目id分组，查询所有申请单
     *
     * @param projectId 项目id
     * @return
     */
    List<Apply> getApplyGroupByProject(String projectId);

    /**
     * 更新申请单提醒时间
     *
     * @param id          申请单id
     * @param notifyDate  提醒日期
     * @param staffCoding 提醒员工编码
     * @return
     */
    int updateNotify(String id, String notifyDate, String staffCoding);

    /**
     * 触发当日通知的单据
     *
     * @return
     */
    boolean notifyStaff();

    /**
     * 申请单发货通知
     *
     * @param id          申请单id
     * @param expressCode 快递单号
     * @return
     */
    int notifyExpress(String id, String expressCode);

    /**
     * 获取申请单列表
     *
     * @param params
     * @return
     */
    List<Apply> getApplyListV2(HashMap<String, Object> params);

    /**
     * 通过申请单编号查询申请单
     * @param seriesNumber
     * @return
     */
    Apply getBySeries(String seriesNumber);

    void checkOa(FlowMessage flowMsg);
}
