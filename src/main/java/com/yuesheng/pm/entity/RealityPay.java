package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/1/7 实际支付单明细表.
 *
 * @author XiaoSong
 * @date 2017/01/07
 */
public class RealityPay extends BaseEntity {

    /**
     * 票据号
     */
    private String billsSeries = "";
    /**
     * 实际付款类型      10
     */
    private byte type = 3;
    /**
     * 实际付款单主表id:实际付款单主对象是Payment
     */
    private String realityId;
    /**
     * 合同付款明细
     */
    private PaymentDetail detail;

    public String getRealityId() {
        return realityId;
    }

    public void setRealityId(String realityId) {
        this.realityId = realityId;
    }

    public String getBillsSeries() {
        return billsSeries;
    }

    public void setBillsSeries(String billsSeries) {
        this.billsSeries = billsSeries;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public PaymentDetail getDetail() {
        return detail;
    }

    public void setDetail(PaymentDetail detail) {
        this.detail = detail;
    }
}
