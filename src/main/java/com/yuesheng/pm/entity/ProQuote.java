package com.yuesheng.pm.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Administrator on 2019-07-01.
 *
 * @author XiaoSong
 * @date 2019/07/01
 */
public class ProQuote extends BaseEntity {
    /**
     * *询价主体id
     */
    private String enquiryId = "";
    /**
     * 询价单材料明细主键
     */
    private String enquiryMaterId = "";
    /**
     * 单价
     */
    private Double price;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 报价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date datetime;
    /**
     * 联系方式
     */
    private String tel;
    /**
     * 报价人
     */
    private String name;
    /**
     * 单位名称
     */
    private String company;
    /**
     * 总价
     */
    private Double money = 0.0;
    /**
     * 材料对象
     */
    private Material material;

    private ProEnquiryMater enquiryMater;

    private ApplyMaterial applyMaterial;
    private Apply apply;

    public ApplyMaterial getApplyMaterial() {
        return applyMaterial;
    }

    public void setApplyMaterial(ApplyMaterial applyMaterial) {
        this.applyMaterial = applyMaterial;
    }

    public ProEnquiryMater getEnquiryMater() {
        return enquiryMater;
    }

    public void setEnquiryMater(ProEnquiryMater enquiryMater) {
        this.enquiryMater = enquiryMater;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getEnquiryMaterId() {
        return enquiryMaterId;
    }

    public void setEnquiryMaterId(String enquiryMaterId) {
        this.enquiryMaterId = enquiryMaterId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    public Apply getApply() {
        return apply;
    }
}
