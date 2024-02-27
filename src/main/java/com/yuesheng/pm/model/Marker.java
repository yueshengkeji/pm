package com.yuesheng.pm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Marker 百度地图点位对象 对应工程项目点位
 * @Description
 * @Author ssk
 * @Date 2024/2/22 0022 10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marker {
    private String id;
    private String projectBase;
    private double lat;
    private double lng;
    private String agreementName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectBase() {
        return projectBase;
    }

    public void setProjectBase(String projectBase) {
        this.projectBase = projectBase;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }
}
