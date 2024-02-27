package com.yuesheng.pm.entity;

/**
 * 采购合同附件   purchase_contract_registration_files
 * @author ssk
 * @since 2022-1-11
 */
public class PurchaseFiles {
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
     * 绑定采购合同的标记
     */
    private Integer mark;

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

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
