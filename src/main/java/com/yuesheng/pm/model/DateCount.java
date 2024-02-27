package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;

/**
 * 日期统计
 */
public class DateCount extends BaseEntity {
    /**
     * count date
     */
    private String date;
    /**
     * count number
     */
    private Integer count;

    private Double doubleCount;
    private Double doubleMoney;

    public Double getDoubleCount() {
        return doubleCount;
    }

    public void setDoubleCount(Double doubleCount) {
        this.doubleCount = doubleCount;
    }

    public Double getDoubleMoney() {
        return doubleMoney;
    }

    public void setDoubleMoney(Double doubleMoney) {
        this.doubleMoney = doubleMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
