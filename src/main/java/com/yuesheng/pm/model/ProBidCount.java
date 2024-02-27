package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;

public class ProBidCount extends BaseEntity {
    /**
     * 投标总数
     */
    private Integer count;
    /**
     * 中标总数
     */
    private Integer biddingCount;
     /**
     * 中标总价
     */
    private Double biddingMoney;
    /**
     * 保证金金额
     */
    private Double bzjMoney;

    private Integer state;
    private Integer bzjState;
    private String startDate;
    private String endDate;
    private String bzjStartDate;
    private String bzjEndDate;

    public void setBiddingMoney(Double biddingMoney) {
        this.biddingMoney = biddingMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getBzjState() {
        return bzjState;
    }

    public void setBzjState(Integer bzjState) {
        this.bzjState = bzjState;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBzjStartDate() {
        return bzjStartDate;
    }

    public void setBzjStartDate(String bzjStartDate) {
        this.bzjStartDate = bzjStartDate;
    }

    public String getBzjEndDate() {
        return bzjEndDate;
    }

    public void setBzjEndDate(String bzjEndDate) {
        this.bzjEndDate = bzjEndDate;
    }

    public Double getBzjMoney() {
        return bzjMoney;
    }

    public void setBzjMoney(Double bzjMoney) {
        this.bzjMoney = bzjMoney;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBiddingCount() {
        return biddingCount;
    }

    public void setBiddingCount(Integer biddingCount) {
        this.biddingCount = biddingCount;
    }
}
