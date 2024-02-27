package com.yuesheng.pm.entity;

/**
 * (ProBzj)实体类
 *
 * @author xiaoSong
 * @since 2021-08-31 09:32:20
 */
public class ProBzj extends BaseEntity {
    private static final long serialVersionUID = -13467556378394294L;

    /**
     * 保证金额
     */
    private Double money;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 登记时间
     */
    private String datetime;
    /**
     * 对账单id
     */
    private String proDetailId;
    /**
     * 保证金类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProDetailId() {
        return proDetailId;
    }

    public void setProDetailId(String proDetailId) {
        this.proDetailId = proDetailId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}