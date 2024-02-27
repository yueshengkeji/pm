package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.EquipmentToRepairFile;
import com.yuesheng.pm.mapper.EquipmentToRepairFileMapper;
import com.yuesheng.pm.service.EquipmentToRepairFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName EquipmentToRepairFileServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/11/1 0001 13:03
 */
@Service
public class EquipmentToRepairFileServiceImpl implements EquipmentToRepairFileService {
    @Autowired
    private EquipmentToRepairFileMapper equipmentToRepairFileMapper;
    @Override
    public int insert(EquipmentToRepairFile equipmentToRepairFile) {
        equipmentToRepairFile.setId(UUID.randomUUID().toString());
        return equipmentToRepairFileMapper.insert(equipmentToRepairFile);
    }

    @Override
    public int delete(String id) {
        return equipmentToRepairFileMapper.delete(id);
    }

    @Override
    public List<EquipmentToRepairFile> getFiles(String equipmentToRepairId) {
        return equipmentToRepairFileMapper.getFiles(equipmentToRepairId);
    }
}
