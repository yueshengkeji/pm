package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.EquipmentToRepairFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName EquipmentToRepairFileMapper
 * @Description
 * @Author ssk
 * @Date 2022/11/1 0001 13:00
 */
@Mapper
public interface EquipmentToRepairFileMapper {
    int insert(EquipmentToRepairFile equipmentToRepairFile);

    int delete(String id);

    /**
     * 获取附件列表
     * @param equipmentToRepairId
     * @return
     */
    List<EquipmentToRepairFile> getFiles(String equipmentToRepairId);
}
