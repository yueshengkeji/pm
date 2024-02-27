package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.EquipmentToRepair;

/**
 * @ClassName EquipmentToRepairModel
 * @Description
 * @Author ssk
 * @Date 2023/1/31 0031 11:27
 */
public class EquipmentToRepairModel {
    private EquipmentToRepair equipmentToRepair;
    private int index;

    public EquipmentToRepair getEquipmentToRepair() {
        return equipmentToRepair;
    }

    public void setEquipmentToRepair(EquipmentToRepair equipmentToRepair) {
        this.equipmentToRepair = equipmentToRepair;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
