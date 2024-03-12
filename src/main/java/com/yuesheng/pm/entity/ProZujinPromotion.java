package com.yuesheng.pm.entity;

/**
 * @ClassName ProZujinPromotion 广告推广
 * @Description
 * @Author ssk
 * @Date 2024/3/8 0008 9:51
 */
public class ProZujinPromotion extends BaseEntity {

    /**
     * 合同id
     */
    private String contractId;
    /**
     * 名称
     */
    private String name;
    /**
     * 推广次数
     */
    private int times;
    /**
     * 推广单价/次
     */
    private Double price;
    /**
     * 推广费用
     */
    private Double money;
    /**
     * 抵用券
     */
    private Double voucher;
    /**
     * 违约金
     */
    private Double liquidatedDamages;
    /**
     * 头条单价/次
     */
    private Double firstBar;
    /**
     * 次条单价/次
     */
    private Double secondBar;
    /**
     * 其他单价/次
     */
    private Double otherBar;

    /**
     * 创建时间
     */
    private String createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
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

    public Double getVoucher() {
        return voucher;
    }

    public void setVoucher(Double voucher) {
        this.voucher = voucher;
    }

    public Double getLiquidatedDamages() {
        return liquidatedDamages;
    }

    public void setLiquidatedDamages(Double liquidatedDamages) {
        this.liquidatedDamages = liquidatedDamages;
    }

    public Double getFirstBar() {
        return firstBar;
    }

    public void setFirstBar(Double firstBar) {
        this.firstBar = firstBar;
    }

    public Double getSecondBar() {
        return secondBar;
    }

    public void setSecondBar(Double secondBar) {
        this.secondBar = secondBar;
    }

    public Double getOtherBar() {
        return otherBar;
    }

    public void setOtherBar(Double otherBar) {
        this.otherBar = otherBar;
    }
}
