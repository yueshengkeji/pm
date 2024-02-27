package com.yuesheng.pm.entity;

import java.util.Date;

/**
 * @ClassName DingTalkLinkNoticeImage 钉钉链接消息图片
 * @Description
 * @Author ssk
 * @Date 2022/9/9 0009 15:03
 */
public class DingTalkLinkNoticeImage extends BaseEntity{
    /**
     * 图片名称
     */
    private String name;
    /**
     * 使用状态
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 存放路径
     */
    private String picUrl;
    /**
     * 钉钉媒文体id
     */
    private String mediaId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
