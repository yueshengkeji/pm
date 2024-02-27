package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;

public class ProPutDetailCount extends BaseEntity {
    /**
     * 采购/应收金额合计
     */
    private Double  proMoney;
    /**
     * 入库/实收金额合计
     */
    private Double putMoney;

    public Double getProMoney() {
        return proMoney;
    }

    public void setProMoney(Double proMoney) {
        this.proMoney = proMoney;
    }

    public Double getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(Double putMoney) {
        this.putMoney = putMoney;
    }
}
