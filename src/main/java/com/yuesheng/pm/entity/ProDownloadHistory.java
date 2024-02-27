package com.yuesheng.pm.entity;

public class ProDownloadHistory extends BaseEntity {
    /**
     * 采购订单
     */
    private Procurement procurement;
    /**
     * 下载时间
     */
    private String dateTime;
    /**
     * 下载人
     */
    private Staff staff;
    /**
     * 是否下载，0=未下载，1=已下载
     */
    private byte state;
    /**
     * 图凭证原图
     */
    private String img;

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}
