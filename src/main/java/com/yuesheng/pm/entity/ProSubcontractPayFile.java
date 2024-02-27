package com.yuesheng.pm.entity;

/**
 * @ClassName ProSubcontractPayFile
 * @Description
 * @Author ssk
 * @Date 2022/9/21 0021 14:47
 */
public class ProSubcontractPayFile {
    private String contractPayId;
    private String attachId;
    private String fileUrl;
    private String name;

    public String getContractPayId() {
        return contractPayId;
    }

    public void setContractPayId(String contractPayId) {
        this.contractPayId = contractPayId;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
