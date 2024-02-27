package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * Created by 96339 on 2017/2/20 退料单详情 sdpm025.
 *
 * @author XiaoSong
 * @date 2017/02/20
 */
public class BackMaterChild extends BaseEntity {
    /**
     * 主键    02
     */
    private String id;
    /**
     * 主表主键  01
     */
    private String backId;
    /**
     * 退料数量  04
     */
    @Excel(name = "退库数量", width = 2048)
    private Double sum;
    /**
     * 退料后，入库的单价 05
     */
    @Excel(name = "单价", width = 2048)
    private Double price;
    /**
     * 入库金额  06
     */
    @Excel(name = "金额", width = 2048)
    private Double money;
    /**
     * 出库单id 07
     */
    private String outId;
    /**
     * 退料行备注 08 ......09未知列，10配置参数
     */
    private String remark;
    /**
     * 退料单主单据对象
     */
    @Excel(name = "退库单")
    private BackMater bm;
    /**
     * 材料对象      03
     */
    private Material material;

    public BackMater getBm() {
        return bm;
    }

    public void setBm(BackMater bm) {
        this.bm = bm;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
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

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
