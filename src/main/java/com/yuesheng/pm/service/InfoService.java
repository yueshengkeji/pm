package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Info;
import com.yuesheng.pm.entity.InfoState;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/11/24 系统消息持久服务.
 */
public interface InfoService {
    /**
     * 获取消息集合
     *
     * @param params     {str:检索串}
     * @return
     */
    List<Info> queryList(Map<String, Object> params);

    /**
     * 获取消息对象
     *
     * @param id 消息id
     * @return
     */
    Info queryById(String id);

    /**
     * 删除消息
     *
     * @param id 消息id
     */
    void deleteById(String id);

    /**
     * 添加消息对象
     *
     * @param info
     */
    void insert(Info info);

    /**
     * 添加消息是否阅读状态
     *
     * @param infoState
     */
    void insertState(InfoState infoState);

    /**
     * 查询该消息已读总数
     *
     * @param infoId
     */
    int queryCount(@Param("infoId") String infoId);

    /**
     * 更新消息是否阅读状态
     *
     * @param infoState
     * @return
     */
    int updateState(InfoState infoState);

    /**
     * 获取已经阅读过的用户
     *
     * @param infoId 消息id
     * @return
     */
    List<Staff> queryReadStaff(@Param("infoId") String infoId);

    /**
     * 获取未读消息集合(只获取五天前的公告数据)
     *
     * @param staffId
     * @return
     */
    List<InfoState> queryInfoStates(String staffId);

    int queryListCount(Map<String, Object> result);

    /**
     * 查询通知、公告消息
     *
     * @param info 插叙条件
     * @return
     */
    List<Info> queryList(Info info);

    /**
     * 更新通知公告消息
     *
     * @param info 公告消息
     * @return
     */
    int update(Info info);

    /**
     * 通过消息id和用户更新通知信息状态
     *
     * @param info
     * @return
     */
    int updateStateByInfo(InfoState info);
}
