package com.yuesheng.pm.entity;

import java.io.Serializable;

/**
 * (ProZujinHouseR)实体类
 *
 * @author xiaoSong
 * @since 2021-07-12 09:29:58
 */
public class ProZujinHouseR implements Serializable {
    private static final long serialVersionUID = 135498468564942043L;

    private Integer id;

    private Integer zjId;

    private Integer houseId;

    private Byte type;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZjId() {
        return zjId;
    }

    public void setZjId(Integer zjId) {
        this.zjId = zjId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

}