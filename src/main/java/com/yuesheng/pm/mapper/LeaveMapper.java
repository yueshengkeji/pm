package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Leave;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/28 请假单mapepr.
 * @author XiaoSong
 * @date 2016/12/28
 */
@Mapper
public interface LeaveMapper {
    /**
     * 获取请假单对象
     *
     * @param id 请假单id
     * @return
     */
    Leave getLeaveById(@Param("id") String id);

    /**
     * 获取请假单集合
     *
     * @param staffId 职员id
     * @return
     */
    List<Leave> getLeaveByStaff(@Param("staffId") String staffId);

    /**
     * 获取职员请假天总数
     *
     * @param params {staffId：职员id,startDate:开始时间；endDate:截止时间}
     * @return
     */
    Double getLeaveSumByStaff(Map<String, String> params);

    /**
     * 添加请假单
     *
     * @param leave
     */
    void insert(Leave leave);

    /**
     * 审核请假单
     *
     * @param frameId 请假单id
     * @param status  审核状态
     * @param staffId 审核职员id
     * @param date    审核时间
     */
    void approve(@Param("id") String frameId, @Param("status") int status, @Param("staffId") String staffId, @Param("date") String date);

    /**
     * 删除请假单
     * @param id 请假单id
     */
    void delete(String id);

    /**
     * 获取请假单集合
     *
     * @param params {searchText:搜索文本,sort：排序方式,Start:开始时间，end:结束时间}
     * @return
     */
    List<Leave> getLeaveByParam(Map<String, Object> params);

    /**
     * 根据条件获取总页数
     *
     * @param params 参考GetLeaveByParam()
     * @return
     */
    int getCountByParam(Map<String, Object> params);

    /**
     * 获取已审批请假单集合
     *
     * @param param {searchText:搜索文本,sort：排序方式,Start:开始时间，end:结束时间}
     * @return
     */
    List<Leave> getApproveLeaveByParam(HashMap<String, Object> param);
}
