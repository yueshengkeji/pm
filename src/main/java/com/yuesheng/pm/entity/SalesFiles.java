package com.yuesheng.pm.entity;

/**
 * 销售合同附件   sales_contract_registrant_files
 * @author ssk
 * @since 2022-1-7
 */
public class SalesFiles {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 附件名
     */
    private String fileName;

    /**
     * 附件路径
     */
    private String fileUrl;

    /**
     * 绑定销售合同的标记
     */
    private String mark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
