package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * @ClassName DiningDay
 * @Description
 * @Author ssk
 * @Date 2022/7/7 0007 9:02
 */
public class DiningDayStatistics extends BaseEntity {

    @Excel(name = "日期", width = 3000)
    private String ymd;
    @Excel(name = "中餐|次数", width = 3000)
    private Integer countDay;
    @Excel(name = "中餐|金额", width = 3000)
    private Double totalMoney;

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public Integer getCountDay() {
        return countDay;
    }

    public void setCountDay(Integer countDay) {
        this.countDay = countDay;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
}
