package com.yuesheng.pm.entity;

/**
 * @ClassName ExpenseFile
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 9:48
 */
public class ExpenseFile extends BaseEntity{

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 路径
     */
    private String fileUrl;
    /**
     * 报销表标记
     */
    private String mark;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
