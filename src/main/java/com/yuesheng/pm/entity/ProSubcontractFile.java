package com.yuesheng.pm.entity;

/**
 * @ClassName ProSubcontractFile
 * @Description
 * @Author ssk
 * @Date 2022/9/22 0022 10:12
 */
public class ProSubcontractFile {
    private String contractId;
    private String attachId;
    private String fileUrl;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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
}
