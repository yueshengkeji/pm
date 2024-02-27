package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.SystemLog;
import com.yuesheng.pm.model.DateCount;

import java.util.Date;
import java.util.List;

/**
 * 系统日志(SystemLog)表服务接口
 *
 * @author xiaoSong
 * @since 2020-11-04 16:36:58
 */
public interface SystemLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SystemLog queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SystemLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param systemLog 实例对象
     * @return 实例对象
     */
    SystemLog insert(SystemLog systemLog);

    /**
     * 修改数据
     *
     * @param systemLog 实例对象
     * @return 实例对象
     */
    SystemLog update(SystemLog systemLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取数据总数
     *
     * @param systemLog
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
    int deleteByDate(Date start, Date end);

    /**
     * 查询系统日志
     *
     * @param systemLog
     * @return
     */
    List<SystemLog> queryByParam(SystemLog systemLog);
    /**
     * 根据日期统计日志总数
     * @return
     */
    List<DateCount> queryByDateGroup();

    /**
     * 查询指定日期日志总数
     *
     * @param date 日期 yyyy-MM-dd
     * @return
     */
    DateCount queryCountByDate(String date);

    /**
     * 清除日志
     *
     * @param dayNumber 指定多少天之前的日志，默认-32
     * @return
     */
    int clearLog(Integer dayNumber);

    /**
     * 检查系统请求异常日志，记录请求次数过多的数据
     */
    void checkErrorLog();

}