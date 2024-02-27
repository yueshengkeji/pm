package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;

public class DateGroupModel extends BaseEntity {
    private String date;
    private Double money;
    private String title;
    private Double money2;

    public Double getMoney2() {
        return money2;
    }

    public void setMoney2(Double money2) {
        this.money2 = money2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
