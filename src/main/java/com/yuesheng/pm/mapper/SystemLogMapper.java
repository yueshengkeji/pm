package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SystemLog;
import com.yuesheng.pm.model.DateCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 系统日志(SystemLog)表数据库访问层
 *
 * @author xiaoSong
 * @since 2020-11-04 16:36:57
 */
@Mapper
public interface SystemLogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SystemLog queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SystemLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param systemLog 实例对象
     * @return 对象列表
     */
    List<SystemLog> queryAll(SystemLog systemLog);

    /**
     * 新增数据
     *
     * @param systemLog 实例对象
     * @return 影响行数
     */
    int insert(SystemLog systemLog);

    /**
     * 修改数据
     *
     * @param systemLog 实例对象
     * @return 影响行数
     */
    int update(SystemLog systemLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 查询系统日志
     *
     * @param systemLog
     * @return
     */
    List<SystemLog> queryByParam(SystemLog systemLog);

    /**
     * 获取日志数据总数
     *
     * @param systemLog 筛选条件
     * @return
     */
    Integer queryCountByParam(SystemLog systemLog);

    /**
     * 删除日志
     *
     * @param start 开始日期
     * @param end   截止日期
     * @return
     */
    int deleteByDate(@Param("start") Date start, @Param("end") Date end);

    /**
     * 查询指定日期日志总数
     * @param date 日期 yyyy-MM-dd
     * @return
     */
    DateCount queryCountByDate(@Param("date") String date);
    /**
     * 根据日期统计日志总数
     * @return
     */
    List<DateCount> queryCountByGroup();

}