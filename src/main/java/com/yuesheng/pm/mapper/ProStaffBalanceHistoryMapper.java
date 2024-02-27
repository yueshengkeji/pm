package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.DiningDayStatistics;
import com.yuesheng.pm.entity.PersonalDiningStatistics;
import com.yuesheng.pm.entity.ProStaffBalanceHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProStaffBalanceHistory)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-05-20 14:28:22
 */
@Mapper
public interface ProStaffBalanceHistoryMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProStaffBalanceHistory queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProStaffBalanceHistory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param q 实例对象
     * @return 对象列表
     */
    List<ProStaffBalanceHistory> queryAll(@Param("q") ProStaffBalanceHistory q,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 新增数据
     *
     * @param proStaffBalanceHistory 实例对象
     * @return 影响行数
     */
    int insert(ProStaffBalanceHistory proStaffBalanceHistory);

    /**
     * 修改数据
     *
     * @param proStaffBalanceHistory 实例对象
     * @return 影响行数
     */
    int update(ProStaffBalanceHistory proStaffBalanceHistory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 查询指定员工、指定日期是否有消费记录
     *
     * @param staffId 员工id
     * @param date    日志日期
     * @return
     */
    ProStaffBalanceHistory queryByStaff(@Param("staffId") String staffId, @Param("date") String date,@Param("type") Integer type);

    /**
     * 通过实力对象查询每日消费汇总
     * @param q
     * @param startDate
     * @param endDate
     * @return
     */
    List<DiningDayStatistics> selectDiningDayStatistics(@Param("q") ProStaffBalanceHistory q,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 通过实力对象查询个人消费汇总
     * @param q
     * @param startDate
     * @param endDate
     * @return
     */
    List<PersonalDiningStatistics> selectPersonalStatistics(@Param("q") ProStaffBalanceHistory q,@Param("startDate") String startDate,@Param("endDate") String endDate);

    /**
     * 指定日期内最新数据
     * @param startDate
     * @param endDate
     * @return
     */
    List<ProStaffBalanceHistory> selectStaffLastHistory(@Param("startDate") String startDate,@Param("endDate") String endDate);
}