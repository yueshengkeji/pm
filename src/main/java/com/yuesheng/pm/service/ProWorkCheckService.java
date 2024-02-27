package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProWorkCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工考勤表(ProWorkCheck)表服务接口
 *
 * @author makejava
 * @since 2020-05-06 10:49:17
 */
public interface ProWorkCheckService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProWorkCheck queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkCheck> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proWorkCheck 实例对象
     * @return 实例对象
     */
    ProWorkCheck insert(ProWorkCheck proWorkCheck);

    /**
     * 修改数据
     *
     * @param proWorkCheck 实例对象
     * @return 实例对象
     */
    ProWorkCheck update(ProWorkCheck proWorkCheck);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 根据条件查询考勤数据（第一条）
     *
     * @param param {date:"考勤日期",time:"考勤时间",staffId:"考勤人员"}
     * @return
     */
    ProWorkCheck queryByParam(Map<String, Object> param);

    /**
     * 获取指定时间内所有的考勤数据
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @param isShow
     * @param type 打卡类型
     */
    List<ProWorkCheck> queryAllByDate(String startDate, String endDate, Integer isShow, Integer type);

    /**
     * 获取指定职员指定日期的考勤数据结婚
     *
     * @param params {staffId 职员id date 日期}
     * @return
     */
    List<ProWorkCheck> queryByStaff(Map<String, Object> params);

    /**
     * 获取部门下考勤数据
     *
     * @param startDate   开始日期
     * @param endDate     截止期
     * @param isShow      是否显示数据
     * @param sectionName 部门名称
     * @param dataType    考勤数据类型
     * @return
     */
    List<ProWorkCheck> queryAllBySection(String startDate, String endDate, Integer isShow, String sectionName, String dataType);

    /**
     * 同步汉王/商汤考勤数据集合到数据库
     *
     * @param proWorkChecks
     */
    void syncWorkCheckForHanWang(List<ProWorkCheck> proWorkChecks);

    /**
     * 获取指定日期考勤总人数
     * @param date 指定日期
     * @return
     */
    Integer queryCount(String date, Integer type);

    /**
     * 查询指定日期考勤总人数
     *
     * @param date 指定日期
     * @return
     */
    Integer getCountByDate(String date);

    /**
     * 通过员工id集合，获取考勤数据
     *
     * @param startDate 开始时间
     * @param endDate   截止时间
     * @param staffIds  例：“'员工id1','员工id2','...'"
     * @return
     */
    List<ProWorkCheck> queryAllByStaffIds(String startDate, String endDate, String staffIds);

    /**
     * 更新考勤请假数据
     *
     * @param update
     * @return
     */
    int updateLeave(ProWorkCheck update);

    /**
     * 更新考情加班数据
     *
     * @param update
     * @return
     */
    int updateOvertime(ProWorkCheck update);

    /**
     * 同步请假、加班数据到考勤表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    int syncLeaveAndOvertime(String startDate, String endDate);

    /**
     * 查询员工制定日期和时间，是否考勤，忽略秒数
     * @param param {date:"考勤日期",time:"考勤时间",staffId:"考勤人员"}
     * @return
     */
    ProWorkCheck queryByDatetime(HashMap param);

    boolean exists(ProWorkCheck workCheck, String staffId);

    /**
     * 更新考勤数据类型
     * @param check
     * @return
     */
    int updateType(ProWorkCheck check);
}