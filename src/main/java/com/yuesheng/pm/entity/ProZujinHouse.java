package com.yuesheng.pm.entity;

import java.io.Serializable;

/**
 * (ProZujinHouse)实体类
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
public class ProZujinHouse implements Serializable {
    private static final long serialVersionUID = 503812873018144081L;

    private Integer id;

    private String floor;

    private String pwNumber;

    private Integer yetaiId;

    private String type;

    private String acreage;

    private String flag;

    private ProZujinYt yt;

    private String remark;

    private String brandName;

    private String person;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProZujinYt getYt() {
        return yt;
    }

    public void setYt(ProZujinYt yt) {
        this.yt = yt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getPwNumber() {
        return pwNumber;
    }

    public void setPwNumber(String pwNumber) {
        this.pwNumber = pwNumber;
    }

    public Integer getYetaiId() {
        return yetaiId;
    }

    public void setYetaiId(Integer yetaiId) {
        this.yetaiId = yetaiId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}