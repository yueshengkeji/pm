package com.yuesheng.pm.entity;


import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * (ProZujinHouse)实体类
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Schema(name = "商铺信息")
public class ProZujinHouse implements Serializable {
    private static final long serialVersionUID = 503812873018144081L;

    private Integer id;

    @Schema(name="楼层")
    private String floor;
    @Schema(name="商铺号")
    private String pwNumber;
    @Schema(name="业态id")
    private Integer yetaiId;
    @Schema(name="品类")
    private String type;

    @Schema(name="面积")
    private String acreage;

    @Schema(name="是否租赁标记")
    private String flag;

    @Schema(name="业态对象")
    private ProZujinYt yt;
    @Schema(name="备注")
    private String remark;
    @Schema(name="租赁品牌")
    private String brandName;
    @Schema(name="租赁人")
    private String person;
    @Schema(name="商铺每月单价")
    private Double money;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

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