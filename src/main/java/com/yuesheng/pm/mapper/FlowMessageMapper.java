package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.model.FLowMessageQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-16 审批消息【我的发起】主表mapper.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
@Mapper
public interface FlowMessageMapper {
    /**
     * 添加审批消息
     *
     * @param flowMessage 审批消息对象
     * @return 影响的行数
     */
    Integer addFlowMessage(FlowMessage flowMessage);

    /**
     * 修改流程消息状态
     *
     * @param id    流程消息id
     * @param state 状态码 {4：取消，1：审批中,2:完成，3：中断}
     * @return
     */
    Integer updateMessage(@Param("id") String id, @Param("status") int state,@Param("date")String date);

    /**
     * 添加【我的发起】消息到主记录表中
     *
     * @param flowMessage 我的发起对象
     */
    void addFlowMessageHistory(FlowMessage flowMessage);

    /**
     * 获取发起消息对象
     *
     * @param id 消息id po00301
     * @return 消息对象
     */
    FlowMessage getMessageById(String id);

    /**
     * 更新发起消息时间
     *
     * @param date
     * @param id
     * @return
     */
    Integer updateMsgDate(@Param("date") String date, @Param("id") String id);

    /**
     * 获取我的发起集合
     *
     * @param params 查询入参
     * @return
     */
    List<FlowMessage> getMessageByStaff(Map<String, Object> params);

    /**
     * 获取我的发起总条目
     *
     * @param params {}
     * @return
     */
    int getMessageByCount(Map<String, Object> params);

    /**
     * 获取sql语句，为了兼容pm2软件，利用窗口编号进行反查sql语句，抛弃om2后该sql可以放弃
     *
     * @param frameCoding 窗口编号
     * @return
     */
    String getSql(String frameCoding);

    /**
     * 删除我的发起流程
     *
     * @param id 主对象id
     */
    void deleteMessage(String id);

    /**
     * 获取 【我的发起】对象
     *
     * @param id frameId 窗口单据主键id
     * @return
     */
    FlowMessage getMsgByFrameId(String id);

    /**
     * 更新审批标签
     *
     * @param id     主流程消息id
     * @param fqFlag 标签数据
     */
    void updateFlag(@Param("id") String id, @Param("flag") String fqFlag);

    /**
     * 查询发起审批消息列表
     *
     * @param message
     * @return
     */
    List<FlowMessage> getMsgList(FlowMessage message);

    /**
     * 通过审批日期筛选流程实例
     *
     * @param message
     * @return
     */
    List<FlowMessage> getMsgListByApproveDate(FLowMessageQuery message);

    /**
     * 获取流程实例sql
     * @param id
     * @return
     */
    String getSqlById(@Param("id") String id);

    /**
     * 获取已结束的流程
     * @param startDatetime 开始日期
     * @param endDatetime 截止日期
     * @return
     */
    List<FlowMessage> getOverMessage(@Param("startDatetime") String startDatetime,
                                     @Param("endDatetime") String endDatetime);

    /**
     * 删除记录表
     * @param id
     * @return
     */
    int deleteHistory(@Param("id") String id);

    /**
     * 获取审批状态为成功，但是没有审批完成时间的数据
     * @return
     */
    List<FlowMessage> getNoDate();

    /**
     * 更新流程完成时间
     * @param item
     * @return
     */
    int updateFinishDate(FlowMessage item);

    /**
     * 查询流程实例
     * @param historyId 流程记录id
     * @return
     */
    FlowMessage getByFlowHistory(@Param("historyId") String historyId);

    /**
     * 获取流程消息实例
     * @param frameId 表单id
     * @param frameCoding 表单编码
     * @return
     */
    FlowMessage getMessage(@Param("frameId") String frameId,
                           @Param("frameCoding") String frameCoding);
}
