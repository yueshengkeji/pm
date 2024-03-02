package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

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
     * 截止时间
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
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date startDate;
    /**
     * 缴费截止日期
     */
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date endDate;

    private Boolean payState;

    public Boolean getPayState() {
        return payState;
    }

    public void setPayState(Boolean payState) {
        this.payState = payState;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

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