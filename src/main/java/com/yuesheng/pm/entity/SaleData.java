package com.yuesheng.pm.entity;


/**
 * (SaleData)实体类
 *
 * @author xiaosong
 * @since 2023-06-26 15:31:14
 */
public class SaleData extends BaseEntity {
    private static final long serialVersionUID = -81919404323582019L;
    /**
     * 主键
     */
    private String id;
    /**
     * 登记日期
     */
    private String date;
    /**
     * 销售日期
     */
    private String saleDate;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 业态
     */
    private String yetai;
    /**
     * 销售金额
     */
    private Double money;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 联系人姓名
     */
    private String person;
    /**
     * 登记人
     */
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYetai() {
        return yetai;
    }

    public void setYetai(String yetai) {
        this.yetai = yetai;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }


}

