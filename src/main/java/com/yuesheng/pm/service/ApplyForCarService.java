package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ApplyForCar;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.StatisticByDriver;
import com.yuesheng.pm.entity.StatisticByProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ApplyForCarService {
    /**
     * 获取用车单
     * @param params {driverId:司机id,projectName:项目名称,searchText:检索串,staffId:申请人id,begin:开始时间，end:截止时间，order:排序方式}
     * @return
     */
    List<ApplyForCar> selectByParam(Integer pageNum,Integer pageSize, Map<String, Object> params);

    /**
     * 获取用车单总数
     * @param params {driverId:司机id,projectName:项目名称,searchText:检索串,staffId:申请人id,begin:开始时间，end:截止时间，order:排序方式}
     * @return
     */
    Integer getCountByParam(Map<String, Object> params);

    /**
     * 用车单不分页
     * @param params
     * @return
     */
    List<ApplyForCar> selectByParamNoPage(Map<String, Object> params);

    /**
     * 通过id获取用车数据
     * @param id
     * @return
     */
    ApplyForCar selectById(Integer id);

    /**
     * 新增用车申请
     * @param applyForCar
     * @return
     */
    int insert(ApplyForCar applyForCar);

    /**
     * 更新用车信息
     * @param applyForCar
     * @return
     */
    int update(ApplyForCar applyForCar);

    /**
     * 删除用车信息
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 通过标记ID查询
     * @param markId
     * @return
     */
    ApplyForCar selectByMarkId(String markId);

    /**
     * @return
     */
    List<ApplyForCar> selectAll();

    /**
     * 按项目分组查询
     * @return
     */
    List<StatisticByProject> selectByGroup(Integer pageNum,Integer pageSize);

    /**
     * 按项目分组查询计数
     * @return
     */
    Integer selectByGroupCounts();

    /**
     * 查询指定时间内
     * @return
     */
    List<StatisticByDriver> selectByDate(Integer pageNum,Integer pageSize,@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 按司机指定时间内分组查询计数
     * @return
     */
    Integer selectByDateCounts(@Param("startDate")String startDate,@Param("endDate")String endDate);

    /**
     * 修改审核状态为已审核
     * @param id
     * @return
     */
    boolean setCheckState(Integer id);

    /**
     * 根据主键设置司机
     * @param driverStaff
     * @return
     */
    boolean setDriver(Staff driverStaff, @Param("id")Integer id);

    /**
     * 获取司机待出勤列表
     * @return
     */
    List<ApplyForCar> getDriverTask(Integer pageNum,Integer pageSize,String driverId);

    /**
     * 获取司机待出勤计数
     * @param driverId
     * @return
     */
    Integer getDriverTaskCounts(String driverId);

    /**
     * 获取司机已完成任务
     * @param driverId
     * @return
     */
    List<ApplyForCar> getDriverTaskDone(Integer pageNum,Integer pageSize,String driverId);

    /**
     * 已完成任务计数
     * @param driverId
     * @return
     */
    Integer getDriverTaskDoneCounts(String driverId);

    /**
     * 获取未完成任务
     * @return
     */
    List<ApplyForCar> getTaskToDo();
}
