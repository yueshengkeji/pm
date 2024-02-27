package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.DiningDayStatistics;
import com.yuesheng.pm.entity.PersonalDiningStatistics;
import com.yuesheng.pm.entity.ProStaffBalanceHistory;

import java.util.List;

/**
 * (ProStaffBalanceHistory)表服务接口
 *
 * @author xiaoSong
 * @since 2022-05-20 14:28:22
 */
public interface ProStaffBalanceHistoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProStaffBalanceHistory queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProStaffBalanceHistory> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proStaffBalanceHistory 实例对象
     * @return 实例对象
     */
    int insert(ProStaffBalanceHistory proStaffBalanceHistory);

    /**
     * 修改数据
     *
     * @param proStaffBalanceHistory 实例对象
     * @return 实例对象
     */
    ProStaffBalanceHistory update(ProStaffBalanceHistory proStaffBalanceHistory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询指定员工、指定日期是否有消费记录
     * @param staffId 员工id
     * @param date 日志日期
     * @return
     */
    ProStaffBalanceHistory queryByStaff(String staffId, String date,Integer type);

    /**
     * 查询余额流水
     * @param query
     * @return
     */
    List<ProStaffBalanceHistory> queryAll(ProStaffBalanceHistory query,String startDate,String endDate);

    /**
     * 通过实力对象查询每日消费汇总
     * @param q
     * @param startDate
     * @param endDate
     * @return
     */
    List<DiningDayStatistics> selectDiningDayStatistics(ProStaffBalanceHistory q, String startDate, String endDate);

    /**
     * 通过实力对象查询个人消费汇总
     * @param q
     * @param startDate
     * @param endDate
     * @return
     */
    List<PersonalDiningStatistics> selectPersonalStatistics(ProStaffBalanceHistory q, String startDate, String endDate);

    /**
     * 指定日期内最新数据
     * @param startDate
     * @param endDate
     * @return
     */
    List<ProStaffBalanceHistory> selectStaffLastHistory(String startDate,String endDate);
}