package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.SysJobLog;
import com.yuesheng.pm.mapper.SysJobLogMapper;
import com.yuesheng.pm.service.ISysJobLogService;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author ruoyi
 */
@Service("sysJobLogService")
public class SysJobLogServiceImpl implements ISysJobLogService {
    @Autowired
    private SysJobLogMapper jobLogMapper;

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog) {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId) {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog) {
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(String ids) {
        return jobLogMapper.deleteJobLogByIds(StrUtils.toStrArray(ids));
    }

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }

    @Override
    public int deleteByDate(String startDate, String endDate) {
        return this.jobLogMapper.deleteByDate(startDate, endDate);
    }

    @Override
    public int deleteByDaysAgo(Integer days) {
        Date startDate = DateUtil.rollDay(new Date(), -days);
        String ed = DateUtil.format(startDate);
        return this.deleteByDate("2022-01-01 00:00:00", ed);
    }
}
