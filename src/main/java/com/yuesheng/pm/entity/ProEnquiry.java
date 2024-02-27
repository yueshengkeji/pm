package com.yuesheng.pm.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019-07-01 询价主体 pro_enquiry.
 *
 * @author XiaoSong
 * @date 2019/07/01
 */
public class ProEnquiry extends BaseEntity {
    /**
     * 申请单id
     */
    private String applyId;
    /**
     * 申请单对象
     */
    private Apply apply;
    /**
     * 询价单名称
     */
    private String name;
    /**
     * 询价日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    /**
     * 是否关闭询价
     */
    private boolean isClose;
    /**
     * 职员id
     */
    private String staffId;
    /**
     * 询价职员
     */
    private Staff staff;
    private List<ProEnquiryMater> proEnquiryMaterList;
    private Integer quoteCount;

    public List<ProEnquiryMater> getProEnquiryMaterList() {
        return proEnquiryMaterList;
    }

    public void setProEnquiryMaterList(List<ProEnquiryMater> proEnquiryMaterList) {
        this.proEnquiryMaterList = proEnquiryMaterList;
    }

    public Staff getStaff() {
        return staff;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public void setQuoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
    }

    public Integer getQuoteCount() {
        return quoteCount;
    }
}
