package com.yuesheng.pm.entity;

import java.util.List;

/**
 * 兼容线上旧版本，员工信息新增加属性
 */
public class StaffPlate extends Staff {
    private List<ProPlate> plate;


    public List<ProPlate> getPlate() {
        return plate;
    }

    public void setPlate(List<ProPlate> plate) {
        this.plate = plate;
    }
}
