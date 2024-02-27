package com.yuesheng.pm.model;


import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(name="职员和职务绑定模型")
public class StaffDutyBandModel implements Serializable {
    @Schema(name = "职员id", required = true)
    private String staffId;
    @Schema(required = true, name = "职务id")
    private String dutyId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDutyId() {
        return dutyId;
    }

    public void setDutyId(String dutyId) {
        this.dutyId = dutyId;
    }
}
