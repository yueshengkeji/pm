package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-26 入库单审核通过后的记录表，sdfifoi.
 * @author XiaoSong
 * @date 2016/08/26
 */
public class Fio extends BaseEntity{
    /**
     * 主键id   01
     */
    private Double id01;
    /**
     * 材料编码|id   02
     */
    private String materId;
    /**
     * 添加时间 03
     */
    private String date;
    /**
     * 入库数量 04
     */
    private Double putSum;
    /**
     * 暂时不知 05
     */
    private Double io05 = 0.0;
    /**
     * 出库数量 06
     */
    private Double io06 = 0.0;
    /**
     * 入库单价 (含税)   07
     */
    private Double putPrice;
    /**
     * 入库金额 (含税) 08
     */
    private Double putMoney;
    /**
     * 入库单材料表id 09
     */
    private String putMaterId;
    /**
     * 入库来源类型,30=采购订单入库，60=库存盘点入库
     */
    private byte type = 30;

    public Double getIo06() {
        return io06;
    }

    public void setIo06(Double io06) {
        this.io06 = io06;
    }

    public Double getIo05() {
        return io05;
    }

    public void setIo05(Double io05) {
        this.io05 = io05;
    }

    public Double getPutSum() {
        return putSum;
    }

    public void setPutSum(Double putSum) {
        this.putSum = putSum;
    }

    public Double getPutPrice() {
        return putPrice;
    }

    public void setPutPrice(Double putPrice) {
        this.putPrice = putPrice;
    }

    public Double getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(Double putMoney) {
        this.putMoney = putMoney;
    }

    public String getPutMaterId() {
        return putMaterId;
    }

    public void setPutMaterId(String putMaterId) {
        this.putMaterId = putMaterId;
    }

    public Double getId01() {
        return id01;
    }

    public void setId01(Double id01) {
        this.id01 = id01;
    }

    public String getMaterId() {
        return materId;
    }

    public void setMaterId(String materId) {
        this.materId = materId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
