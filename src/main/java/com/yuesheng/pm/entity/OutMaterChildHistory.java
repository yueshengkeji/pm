package com.yuesheng.pm.entity;

/**
 * Created by 宋正根 on 2016/9/1 出库单材料表关系表 sdfifoo.
 *
 * @author XiaoSong
 * @date 2016/09/01
 */
public class OutMaterChildHistory {
    /**
     * 自增主键  01
     */
    private Double id;
    /**
     * 材料id  02
     */
    private String materId;
    /**
     * 添加时间  03
     */
    private String dateTime;
    /**
     * 出库数量  04
     */
    private Double outSum;
    /**
     * 预留字段  05
     */
    private Double RQty05 = 0.0;
    /**
     * 单价        06
     */
    private Double price;
    /**
     * 总价        07
     */
    private Double money;
    /**
     * 出库单材料表主键  08
     */
    private String outMaterChildId;
    /**
     * 类型    09
     */
    private byte type = 20;
    /**
     * sdfifoi主键id
     */
    private Double fifoiId;


    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getMaterId() {
        return materId;
    }

    public void setMaterId(String materId) {
        this.materId = materId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Double getOutSum() {
        return outSum;
    }

    public void setOutSum(Double outSum) {
        this.outSum = outSum;
    }

    public Double getRQty05() {
        return RQty05;
    }

    public void setRQty05(Double RQty05) {
        this.RQty05 = RQty05;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getOutMaterChildId() {
        return outMaterChildId;
    }

    public void setOutMaterChildId(String outMaterChildId) {
        this.outMaterChildId = outMaterChildId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Double getFifoiId() {
        return fifoiId;
    }

    public void setFifoiId(Double fifoiId) {
        this.fifoiId = fifoiId;
    }
}
