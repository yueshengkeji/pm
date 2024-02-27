package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProWorkCheck;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工考勤表(ProWorkCheck)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-06 10:49:17
 */
@Mapper
public interface ProWorkCheckMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProWorkCheck queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkCheck> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proWorkCheck 实例对象
     * @return 对象列表
     */
    List<ProWorkCheck> queryAll(ProWorkCheck proWorkCheck);

    /**
     * 新增数据
     *
     * @param proWorkCheck 实例对象
     * @return 影响行数
     */
    int insert(ProWorkCheck proWorkCheck);

    /**
     * 修改数据
     *
     * @param proWorkCheck 实例对象
     * @return 影响行数
     */
    int update(ProWorkCheck proWorkCheck);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

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
     */
    List<ProWorkCheck> queryAllByDate(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("isShow") Integer isShow, @Param("type") Integer type);

    /**
     * 获取指定职员指定日期的考勤数据结婚
     *
     * @param params { staffId 职员id date 日期 }
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
    List<ProWorkCheck> queryAllBySection(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("isShow") Integer isShow, @Param("sectionName") String sectionName, @Param("dataType") String dataType);

    /**
     * 获取指定日期考勤总人数
     *
     * @param date 指定日期
     * @return
     */
    Integer queryCount(@Param("date") String date, @Param("type") Integer type);

    /**
     * 获取指定员工的考勤数据
     *
     * @param startDate   开始日期
     * @param endDate     截止日期
     * @param isShow      是否获取隐藏的数据
     * @param sectionName 部门名称
     * @param dataType    考勤类型
     * @param staffIds    员工id：'员工id1','员工id2'
     * @return
     */
    List<ProWorkCheck> queryAllByStaffIds(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("isShow") Integer isShow, @Param("sectionName") String sectionName, @Param("dataType") String dataType, @Param("staffIds") String staffIds);

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
     * 查询员工制定日期和时间，是否考勤，忽略秒数
     * @param param {date:"考勤日期",time:"考勤时间",staffId:"考勤人员"}
     * @return
     */
    ProWorkCheck queryByDatetime(HashMap param);

    /**
     * 更新考勤数据类型
     * @param check
     * @return
     */
    int updateType(ProWorkCheck check);
}