package com.yuesheng.pm.entity;

import java.util.Date;

/**
 * @author gui_lin
 * @Description 证书下载记录
 * 2022/1/15
 */
public class DownloadRecord {
    /**
     * id
     */
    private Long id;

    /**
     * 证书名称
     */
    private String name;

    /**
     * 下载人员id
     */
    private Long recordUserId;

    /**
     * 下载人员姓名
     */
    private String recordUserName;

    /**
     * 下载时间
     */
    private Date recordTime;

    /**
     * 用途说明
     */
    private String recordText;

    /**
     * 证书id
     */
    private Long certificateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Long recordUserId) {
        this.recordUserId = recordUserId;
    }

    public String getRecordUserName() {
        return recordUserName;
    }

    public void setRecordUserName(String recordUserName) {
        this.recordUserName = recordUserName;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordText() {
        return recordText;
    }

    public void setRecordText(String recordText) {
        this.recordText = recordText;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }
}
