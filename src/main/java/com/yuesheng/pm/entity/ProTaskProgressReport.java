package com.yuesheng.pm.entity;

/**
 * @ClassName ProTaskProgressReport 项目任务进度汇报实体类
 * @Description
 * @Author ssk
 * @Date 2022/9/14 0014 8:40
 */
public class ProTaskProgressReport extends BaseEntity{
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 工作内容
     */
    private String content;
    /**
     * 施工节点
     */
    private String constructionNode;
    /**
     * 施工人数
     */
    private Integer constructorsNumber;
    /**
     * 明日计划
     */
    private String planForTomorrow;
    /**
     * 上传图片路径
     */
    private String picUrl;
    /**
     * 目前进度百分比
     */
    private String progressNow;
    /**
     * 汇报人
     */
    private Staff staff;
    /**
     * 汇报时间
     */
    private String createTime;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConstructionNode() {
        return constructionNode;
    }

    public void setConstructionNode(String constructionNode) {
        this.constructionNode = constructionNode;
    }

    public Integer getConstructorsNumber() {
        return constructorsNumber;
    }

    public void setConstructorsNumber(Integer constructorsNumber) {
        this.constructorsNumber = constructorsNumber;
    }

    public String getPlanForTomorrow() {
        return planForTomorrow;
    }

    public void setPlanForTomorrow(String planForTomorrow) {
        this.planForTomorrow = planForTomorrow;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getProgressNow() {
        return progressNow;
    }

    public void setProgressNow(String progressNow) {
        this.progressNow = progressNow;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
