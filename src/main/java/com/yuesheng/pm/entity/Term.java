package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * (Term)实体类
 *
 * @author xiaosong
 * @since 2024-02-29 15:33:29
 */
public class Term extends BaseEntity {
    private static final long serialVersionUID = -82122010419896569L;

    /**
     * 合同id
     */
    private String concatId;
    /**
     * 费用名称
     */
    private String name;
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 计费方式
     */
    private String type;
    /**
     * 计费单位
     */
    private String unit;
    /**
     * 支付周期
     */
    private String payCycle;
    /**
     * 是否月度账单
     */
    private Boolean monthBill;
    /**
     * 付款方式
     */
    private String payType;
    /**
     * 付款日
     */
    private String payDay;
    /**
     * 首次计费开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date firstStartDate;
    /**
     * 首次计费结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date firstEndDate;
    /**
     * 首次计费金额
     */
    private Double firstMoney;
    /**
     * 计价方式
     */
    private String priceType;
    /**
     * 计费金额
     */
    private Double money;

    /**
     * 单价
     */
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getConcatId() {
        return concatId;
    }

    public void setConcatId(String concatId) {
        this.concatId = concatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
    }

    public Boolean getMonthBill() {
        return monthBill;
    }

    public void setMonthBill(Boolean monthBill) {
        this.monthBill = monthBill;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayDay() {
        return payDay;
    }

    public void setPayDay(String payDay) {
        this.payDay = payDay;
    }

    public Date getFirstStartDate() {
        return firstStartDate;
    }

    public void setFirstStartDate(Date firstStartDate) {
        this.firstStartDate = firstStartDate;
    }

    public Date getFirstEndDate() {
        return firstEndDate;
    }

    public void setFirstEndDate(Date firstEndDate) {
        this.firstEndDate = firstEndDate;
    }

    public Double getFirstMoney() {
        return firstMoney;
    }

    public void setFirstMoney(Double firstMoney) {
        this.firstMoney = firstMoney;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}

