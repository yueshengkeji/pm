package com.yuesheng.pm.entity;

/**
 * 工作日记附件关系表(ProWorkLogFile)实体类
 *
 * @author xiaoSong
 * @since 2021-07-03 10:28:16
 */
public class ProWorkLogFile extends BaseEntity {
    private static final long serialVersionUID = -50054268487430936L;
    /**
     * 工作日记id
     */
    private Long logId;
    /**
     * 附件id
     */
    private String fileId;


    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}