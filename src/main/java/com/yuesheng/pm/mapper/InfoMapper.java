package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Info;
import com.yuesheng.pm.entity.InfoState;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/11/24.
 * @author XiaoSong
 * @date 2017/11/24
 */
@Mapper
public interface InfoMapper {
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
     * @param infoId 消息id
     * @return 已读总数
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
     * 获取未读消息集合
     *
     * @param staffId 职员id
     * @return
     */
    List<InfoState> queryInfoStates(@Param("staffId") String staffId);

    /**
     * 查询消息总数
     *
     * @param result 查询入参
     * @return
     */
    int queryListCount(Map<String, Object> result);

    /**
     * 查询通知公告消息
     *
     * @param info 查询条件
     * @return
     */
    List<Info> queryListByInfo(Info info);

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

    /**
     * 查询消息状态
     *
     * @param mainId  消息id
     * @param staffId 职员id
     * @return
     */
    InfoState queryInfoState(@Param("mainId") String mainId, @Param("staffId") String staffId);
}
