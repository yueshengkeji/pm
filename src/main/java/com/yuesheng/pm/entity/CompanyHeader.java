package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/5/26 第三方单位对账单.
 * @author XiaoSong
 * @date 2017/05/26
 */
public class CompanyHeader extends BaseEntity {
    /**
     * 单位id
     */
    private String companyId;
    /**
     * 单位对象
     */
    private Company company;
    /**
     * 采购金额
     */
    private Double proMoney;
    /**
     * 入库金额
     */
    private Double putMoney;
    /**
     * 付款金额
     */
    private Double payMoney;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 系统登记时间
     */
    private String date;
    /**
     * 单据填写时间（用户指定）
     */
    private String datetime;
    /**
     * 最后修改时间
     */
    private String lastDate;

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

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

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
