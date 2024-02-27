package com.yuesheng.pm.entity;

import java.util.ArrayList;

/**
 * @ClassName DingTalkTaskToDo 钉钉待办任务
 * @Description
 * @Author ssk
 * @Date 2022/10/17 0017 14:12
 */
public class DingTalkTaskToDo {
    /**
     * 业务系统侧的唯一标识ID，即业务ID。
     * 当ISV接入钉钉待办后，传递ISV应用业务系统侧的唯一标识任务ID。当待办创建成功后，需要更换新的sourceId，保持一个待办任务对应一个sourceId。
     * 创建钉钉官方待办时，该字段无需传入。
     */
    private String sourceId;

    /**
     * 待办标题
     * 最大长度1024。
     */
    private String subject;

    /**
     * 创建者的unionId
     */
    private String creatorId;

    /**
     * 待办描述
     */
    private String description;

    /**
     * 截止时间 Unix时间戳，单位毫秒。
     */
    private Long dueTime;

    /**
     * 执行者的unionId，最大数量1000。
     */
    private ArrayList<String> executorIds;

    /**
     * 参与者的unionId，最大数量1000。
     */
    private ArrayList<String> participantIds;

    /**
     * APP端详情页url跳转地址。与pcUrl同时填可在待办列表显示
     */
    private String appUrl;

    /**
     * PC端详情页url跳转地址。与appUrl同时填可在待办列表显示
     */
    private String pcUrl;

    /**
     * 生成的待办是否仅展示在执行者的待办列表中。
     */
    private Boolean isOnlyShowExecutor;

    /**
     * 优先级
     * 10：较低
     * 20：普通
     * 30：紧急
     * 40：非常紧急
     */
    private Integer priority;

    /**
     * DING通知配置，目前仅支持取值为1，表示应用内DING。
     */
    private String dingNotify;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDueTime() {
        return dueTime;
    }

    public void setDueTime(Long dueTime) {
        this.dueTime = dueTime;
    }

    public ArrayList<String> getExecutorIds() {
        return executorIds;
    }

    public void setExecutorIds(ArrayList<String> executorIds) {
        this.executorIds = executorIds;
    }

    public ArrayList<String> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(ArrayList<String> participantIds) {
        this.participantIds = participantIds;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public Boolean getOnlyShowExecutor() {
        return isOnlyShowExecutor;
    }

    public void setOnlyShowExecutor(Boolean onlyShowExecutor) {
        isOnlyShowExecutor = onlyShowExecutor;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDingNotify() {
        return dingNotify;
    }

    public void setDingNotify(String dingNotify) {
        this.dingNotify = dingNotify;
    }
}
