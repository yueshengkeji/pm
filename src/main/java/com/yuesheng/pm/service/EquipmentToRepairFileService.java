package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.EquipmentToRepairFile;

import java.util.List;

/**
 * @ClassName EquipmentToRepairFileService
 * @Description
 * @Author ssk
 * @Date 2022/11/1 0001 13:03
 */
public interface EquipmentToRepairFileService {
    int insert(EquipmentToRepairFile equipmentToRepairFile);

    int delete(String id);

    /**
     * 获取附件列表
     * @param equipmentToRepairId
     * @return
     */
    List<EquipmentToRepairFile> getFiles(String equipmentToRepairId);
}
