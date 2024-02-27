package com.yuesheng.pm.util.job;

import com.yuesheng.pm.entity.SysJob;
import com.yuesheng.pm.entity.SysJobLog;
import com.yuesheng.pm.service.ISysJobLogService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author ruoyi
 */
public abstract class AbstractQuartzJob implements Job {
    private static final Logger log = LogManager.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJob sysJob = new SysJob();
        BeanUtils.copyProperties(sysJob, context);
        try {
            before(context, sysJob);
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, SysJob sysJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context        工作执行上下文对象
     */
    protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setStartTime(startTime);
        sysJobLog.setEndTime(new Date());
        long runMs = sysJobLog.getEndTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            sysJobLog.setStatus(Constant.FAIL);
            String errorMsg = StringUtils.substring(e.getMessage(), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus(Constant.SUCCESS);
        }

        // 写入数据库当中
        sysJobLog.setCreateTime(DateUtil.getNowDate());
        SpringUtils.getBean(ISysJobLogService.class).addJobLog(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context = {SysJobLog@5667} "com.yuesheng.pm.entity.SysJobLog@51560ba9[\n  jobLogId=<null>\n  jobName=到期自动处理流程\n  jobGroup=flowManager\n  jobMessage=到期自动处理流程 总共耗时：0毫秒\n  status=0\n  exceptionInfo=<null>\n  startTime=Mon Aug 21 06:05:00 CST 2023\n  endTime=Mon Aug 21 06:05:00 CST 2023\n]"... Viewcontext 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
