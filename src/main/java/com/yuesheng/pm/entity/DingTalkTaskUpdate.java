package com.yuesheng.pm.entity;

import java.util.ArrayList;

/**
 * @ClassName DingTalkTaskRsp 钉钉待办更新实体类
 * @Description
 * @Author ssk
 * @Date 2022/10/21 0021 11:02
 */
public class DingTalkTaskUpdate {
    /**
     * 待办id 必传
     */
    private String id;

    /**
     * 待办创建者unionId; 必传
     */
    private String unionId;

    /**
     * 待办的标题
     */
    private String subject;

    /**
     * 待办描述
     */
    private String description;

    /**
     * 截止时间
     */
    private Long dueTime;

    /**
     * 完成状态。
     *
     * true：已完成
     *
     * false：未完成
     */
    private Boolean done;

    /**
     * 执行者的unionId，最大数量1000。
     */
    private ArrayList<String> executorIds;

    /**
     * 参与者的unionId，最大数量1000。
     */
    private ArrayList<String> participantIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
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
}
