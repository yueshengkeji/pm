package com.yuesheng.pm.entity;

/**
 * (ProNotifyType)实体类
 *
 * @author xiaoSong
 * @since 2022-10-11 15:11:30
 */
public class ProNotifyType extends BaseEntity {

    private String staffId;
    /**
     * 0=推送老系统url
     * 1=推送vue系统url
     */
    private Boolean type;
    /**
     * 0=关闭微信推送
     * 1=启用微信推送
     */
    private Boolean wx;
    /**
     * 0=关闭钉钉推送
     * 1=启用钉钉推送
     */
    private Boolean ding;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getWx() {
        return wx;
    }

    public void setWx(Boolean wx) {
        this.wx = wx;
    }

    public Boolean getDing() {
        return ding;
    }

    public void setDing(Boolean ding) {
        this.ding = ding;
    }
}