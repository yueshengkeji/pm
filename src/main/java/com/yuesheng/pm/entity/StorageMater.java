package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * Created by Administrator on 2016-08-26 仓库中材料表 sdpm005.
 *
 * @author XiaoSong
 * @date 2016/08/26
 */
public class StorageMater extends BaseEntity {
    /**
     * 仓库id      01
     */
    private String storageId;
    /**
     * 材料对象  02
     */
    private Material material;
    /**
     * 数量        03
     */
    private Double sum = 0.0;
    /**
     * 平均单价      04
     */
    @Excel(name = "价格", width = 2098)
    private Double price = 0.0;
    /**
     * 总价        05
     */
    @Excel(name = "金额", width = 2098)
    private Double money = 0.0;
    /**
     * 位置信息
     */
    private String pm00506 = "";
    /**
     * 税率
     */
    private Double tax;
    /**
     * 入库时间
     */
    private String putDate;

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
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

    public String getPm00506() {
        return pm00506;
    }

    public void setPm00506(String pm00506) {
        this.pm00506 = pm00506;
    }
}
