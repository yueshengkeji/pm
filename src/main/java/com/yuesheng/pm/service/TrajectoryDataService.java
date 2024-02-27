package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.TrajectoryData;

import java.util.List;
import java.util.Map;

/**
 * @author xiaoSong
 * @date 2020/3/10
 * 行程轨迹服务接口
 */
public interface TrajectoryDataService {
    /**
     * 添加行程轨迹
     *
     * @param trajectoryData
     */
    void insert(TrajectoryData trajectoryData);

    /**
     * 更新行程轨迹审核状态
     *
     * @param trajectoryData
     */
    int updateState(TrajectoryData trajectoryData);

    /**
     * 删除行程轨迹
     *
     * @param id 主键id
     * @return
     */
    int delete(String id);

    /**
     * 查询行程轨迹数据
     *
     * @param param {startDate:行程开始时间，endDate:行程截止时间,str:检索字符串}
     * @return
     */
    List<TrajectoryData> query(Map<String, Object> param);

    /**
     * 查询行程轨迹数据
     *
     * @param id 行程轨迹主键
     * @return
     */
    TrajectoryData queryById(String id);

    /**
     * 查询数据总数
     *
     * @param param 参见query()方法
     * @return
     */
    Integer queryCount(Map<String, Object> param);

    void approve(String id);
}
