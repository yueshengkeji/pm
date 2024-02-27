package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/2/16 账面欠款明细   pro_detail_money.
 *
 * @author XiaoSong
 * @date 2017/02/16
 */
public class ProDetailMoney extends BaseEntity {
    /**
     * 主表id
     */
    private String mainId;
    /**
     * 会计科目
     */
    private String series;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 添加时间
     */
    private String date;
    /**
     * 账面欠款金额
     */
    private Double money = 0.0;

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
