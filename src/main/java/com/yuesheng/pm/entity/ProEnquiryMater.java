package com.yuesheng.pm.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2019-07-01 询价材料明细.
 *
 * @author XiaoSong
 * @date 2019/07/01
 */
public class ProEnquiryMater extends BaseEntity {
    /**
     * 询价单主单据id
     */
    private String enquiryId;
    /**
     * 材料id
     */
    private String materId;
    /**
     * 询价日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    /**
     * 最后报价日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastDate;
    /**
     * 申请单id
     */
    private String applyId;
    /**
     * 申请单材料id
     */
    private String applyMaterialId;
    /**
     * 询价材料数量
     */
    private Double num;
    /**
     * 期待单价
     */
    private Double price;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 询价材料对象
     */
    private Material material;

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getMaterId() {
        return materId;
    }

    public void setMaterId(String materId) {
        this.materId = materId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyMaterialId() {
        return applyMaterialId;
    }

    public void setApplyMaterialId(String applyMaterialId) {
        this.applyMaterialId = applyMaterialId;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
