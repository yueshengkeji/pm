package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/2/10 采购付款明细 主对象：ProDetailPay.
 *
 * @author XiaoSong
 * @date 2017/02/10
 */
public class ProDetailPay extends BaseEntity {
    /**
     * 主对象id
     */
    private String mainId;
    /**
     * 实收付款金额
     */
    private Double payMoney = 0.0;
    /**
     * 实收付款时间
     */
    private String payDate;
    /**
     * 计划单据金额
     */
    private Double billMoney = 0.0;
    /**
     * 计划单据时间
     */
    private String billDate;
    /**
     * 单据时间
     */
    private String date;
    /**
     * 添加人员
     */
    private Staff staff;
    /**
     * 最后更新时间
     */
    private String lastDate;
    /**
     * 最后修改人员
     */
    private String lastStaff;
    /**
     * 合同明细id
     */
    private String detailId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Double getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(Double billMoney) {
        this.billMoney = billMoney;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastStaff() {
        return lastStaff;
    }

    public void setLastStaff(String lastStaff) {
        this.lastStaff = lastStaff;
    }
}
