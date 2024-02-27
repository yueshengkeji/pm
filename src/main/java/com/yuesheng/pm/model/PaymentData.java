package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;

/**
 * Created by Administrator on 2019-07-18.
 * @author XiaoSong
 * @date 2019/07/18
 */
public class PaymentData extends BaseEntity {
    /**
     * 付款中的金额
     */
    private Double money;
    /**
     * 单位名称
     */
    private String cname;
    /**
     * 单位id
     */
    private String cid;
    /**
     * 已付款金额
     */
    private Double ytMoney;

    public Double getMoney() {
        return money == null ? 0 : money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setYtMoney(Double ytMoney) {
        this.ytMoney = ytMoney;
    }

    public Double getYtMoney() {
        return ytMoney;
    }
}
