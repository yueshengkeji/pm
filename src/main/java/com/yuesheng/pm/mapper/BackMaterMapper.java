package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.BackMater;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/20 退料单mapper sdpm024.
 * @author XiaoSong
 * @date 2017/02/20
 */
@Mapper
public interface BackMaterMapper {
    /**
     * 添加退料单
     * @param back
     */
    void addBackMater(BackMater back);

    /**
     * 更新退料单信息
     * @param back
     * @return 影响的行数
     */
    int updateBackMater(BackMater back);

    /**
     * 删除退料单
     * @param id 主键id
     * @return
     */
    int deleteBack(String id);

    /**
     * 获取退料单集合
     *
     * @param params 查询参数
     * @return 退料单集合
     */
    List<BackMater> getBackMater(Map<String, Object> params);

    /**
     * 生成退料单序号
     * @param date 日期
     * @return
     */
    String getBackNumber(String date);

    /**
     * 根据条件参数获取条目总数
     * @param params
     * @return
     */
    Integer getBackMaterCount(Map<String, Object> params);
    /**
     * 获取材料退料集合
     * @param params {start:开始时间，end:截止时间，projects:项目集合}
     * @return
     */
    List<BackMater> getMatersByParam(Map<String, String> params);

    /**
     * 获取退料单对象
     *
     * @param id 退料单主表id
     * @return
     */
    BackMater getBackById(String id);

    /**
     * 更新审批信息
     * @param bm
     * @return
     */
    int updateApprove(BackMater bm);
}
