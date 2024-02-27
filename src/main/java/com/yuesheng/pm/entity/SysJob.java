package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuesheng.pm.annotation.Excel;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.job.CronUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务调度表 sys_job
 *
 * @author ruoyi
 */
@Schema(name="定时任务")
public class SysJob extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @Excel(name = "任务序号")
    @Schema(name="任务id")
    private Long jobId;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    @Schema(name="任务名称")
    private String jobName;

    /**
     * 任务组名
     */
    @Excel(name = "任务组名")
    @Schema(name="任务组名")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @Excel(name = "调用目标字符串")
    @Schema(name="调用字符串")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @Excel(name = "执行表达式 ")
    @Schema(name="执行表达式")
    private String cronExpression;

    /**
     * cron计划策略
     */
    @Excel(name = "计划策略 ")
    @Schema(name="计划策略")
    private String misfirePolicy = Constant.MISFIRE_DEFAULT;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    @Schema(name="并发执行")
    @Excel(name = "并发执行", coverFormat = "0=允许,1=禁止")
    private String concurrent;

    /**
     * 任务状态（0正常 1暂停）
     */
    @Excel(name = "任务状态", coverFormat = "0=正常,1=暂停")
    @Schema(name="任务状态")
    private String status;

    @Schema(name="备注")
    private String remark;

    @Schema(name="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(name="创建人员")
    private String createBy;

    @Schema(name="更新人员")
    private String updateBy;

    @Schema(name="更新时间")
    private String updateTime;

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }


    public String getInvokeTarget() {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }


    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Date getNextValidTime() {
        if (StringUtils.isNotEmpty(cronExpression)) {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }

    public String getMisfirePolicy() {
        return misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy) {
        this.misfirePolicy = misfirePolicy;
    }

    public String getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("jobId", getJobId())
                .append("jobName", getJobName())
                .append("jobGroup", getJobGroup())
                .append("cronExpression", getCronExpression())
                .append("nextValidTime", getNextValidTime())
                .append("misfirePolicy", getMisfirePolicy())
                .append("concurrent", getConcurrent())
                .append("status", getStatus())
                .toString();
    }
}