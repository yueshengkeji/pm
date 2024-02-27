package com.yuesheng.pm.entity;

/**
 * @ClassName EquipmentToRepairFile
 * @Description
 * @Author ssk
 * @Date 2022/11/1 0001 11:44
 */
public class EquipmentToRepairFile extends BaseEntity{
    private String equipmentToRepairId;
    private String attachId;
    private String fileUrl;
    private String name;

    public String getEquipmentToRepairId() {
        return equipmentToRepairId;
    }

    public void setEquipmentToRepairId(String equipmentToRepairId) {
        this.equipmentToRepairId = equipmentToRepairId;
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
