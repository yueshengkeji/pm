package com.yuesheng.pm.entity;

/**
 * (ProPlate)实体类
 *
 * @author xiaoSong
 * @since 2021-08-06 15:44:35
 */
public class ProPlate extends BaseEntity {
    private static final long serialVersionUID = -65423885114999533L;

    private String id;

    private String staffId;

    private String plate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

}