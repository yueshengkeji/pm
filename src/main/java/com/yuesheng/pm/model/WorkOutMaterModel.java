package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.MaterOutChild;
import com.yuesheng.pm.entity.Staff;

/**
 * 办公材料领用model
 */
public class WorkOutMaterModel {
    /**
     * 材料信息
     */
    private MaterOutChild materOut;
    /**
     * 领用人
     */
    private Staff staff;

    public MaterOutChild getMaterOut() {
        return materOut;
    }

    public void setMaterOut(MaterOutChild materOut) {
        this.materOut = materOut;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
