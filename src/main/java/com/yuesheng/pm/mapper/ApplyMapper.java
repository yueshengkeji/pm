package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Apply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-02 采购申请单对象mapper.
 * @author XiaoSong
 * @date 2016/08/02
 */
@Mapper
public interface ApplyMapper {
    /**
     * 获取采购生清单集合
     * @return
     */
    List<Apply> getApplyListByParams();
    /**
     * 根据指定日期获取采购申请单
     *
     * @param map    开始日期、结束日期；
     * @return
     */
    List<Apply> getApplysByDate(@Param("map") Map map);
    /**
     * 更新申请单状态
     *
     * @param params state 0：未采购，1：部分采购，2：全部采购 * appId 申请单id
     * @return 影响的行数
     */
    int updateState(Map<String,Object> params);

    /**
     * 根据申请单id获取申请单对象
     * @param applyId 申请单id
     * @return 申请单对象
     */
    Apply getApplyById(String applyId);

    /**
     * 获取申请单集合
     * @param params 查询条件参数
     * @return
     */
    List<Apply> getApplyListByParams(Map<String, Object> params);

    /**
     * 根据条件获取条目总数
     * @param params 查询条件参数
     * @return
     */
    Integer getApplyCount(Map<String, Object> params);

    /**
     * 添加申请单对象
     * @param apply 申请单对象
     * @return 数据主键
     */
    String addApply(Apply apply);

    /**
     * 删除申请单
     * @param id 主键
     */
    void delete(String id);

    /**
     * 审核|反审核单据
     * @param param 审核状态，审核时间，审核人员
     */
    void approve(Map<String, Object> param);

    /**
     * 验证申请单编号是否存在
     *
     * @param seriesNumber 申请单编号
     * @return
     */
    String verifySeries(@Param("seriesNumber") String seriesNumber);

    /**
     * 更新申请单审核时间9（包含时分秒）
     *
     * @param id          申请单id
     * @param approveDate 时间（包含时分秒）
     */
    void updateApproveTime(@Param("id") String id, @Param("approveDate") String approveDate);

    /**
     * 通过项目id分组，查询所有申请单
     *
     * @param projectId 项目id
     * @return
     */
    List<Apply> getApplyByProject(@Param("projectId") String projectId);

    /**
     * 更新提醒时间
     *
     * @param id         申请单id
     * @param notifyDate 提醒时间
     * @return
     */
    int updateNotify(@Param("id") String id, @Param("notifyDate") String notifyDate, @Param("staffCoding") String staffCoding);

    /**
     * 查询指定时间待提醒的单据
     */
    List<Apply> queryByNotify(@Param("notifyDate") String notifyDate);

    /**
     * 查询申请单列表
     *
     * @param params
     * @return
     */
    List<Apply> getApplyListByParamsV2(HashMap<String, Object> params);

    /**
     * 通过申请单编号查询
     * @param seriesNumber
     * @return
     */
    Apply getBySeries(String seriesNumber);
}
