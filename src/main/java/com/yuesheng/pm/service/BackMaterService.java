package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.BackMater;
import com.yuesheng.pm.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/22 退料单服务 接口.
 */
public interface BackMaterService {
    /**
     * 添加退料单
     * @param back
     * @return 添加成功后的退料单对象
     */
    BackMater addBackMater(BackMater back);

    /**
     * 更新退料单信息
     *
     * @param back
     * @return 更新影响的行数
     */
    int updateBackMater(BackMater back) throws Exception;

    /**
     * 删除退料单
     * @param id 主键id
     * @return
     */
    int deleteBack(String id);

    /**
     * 审核退料单
     * @param id
     * @param date
     * @param staffCoding
     * @param state 审核状态：2=同意，3=不同意
     */
    void approveBack(String id,String date,String staffCoding,String state);

    /**
     * 获取退料单集合
     * @param params {order:排序，index:分页对象，str:检索的字符串。start:开始时间，end:结束时间}
     * @return
     */
    List<BackMater> getBackMater(Map<String, Object> params);

    /**
     * 生成退料单号
     * @return
     */
    String getBackNumber();

    /**
     * 根据参数限制获取退料单总数
     * @param params {str:检索的字符串。start:开始时间，end:结束时间}
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
     * 获取退料金额合计
     *
     * @param projectId 项目id
     * @return
     */
    Double getBackMoneyByProject(String projectId);

    /**
     * 添加退料单，自动审核
     *
     * @param backMater 退料单
     * @param staff     添加人员
     * @return
     */
    BackMater addBackMater(BackMater backMater, Staff staff);

    /**
     * 修改退料单
     *
     * @param backMater 退料单
     * @param staff     修改人员
     * @return
     */
    BackMater updateBackMater(BackMater backMater, Staff staff) throws Exception;

}
