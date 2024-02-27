package com.yuesheng.pm.entity;

/**
 * @program: kailismart.com
 * @description: Staff的附加信息，包含微信userID和OpenId
 * @author: zcj
 * @create: 2019-06-11 14:16
 **/
public class StaffAdditionInfo {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户主键
     */
    private String staffId;

    /**
     * 对应的系统ID
     */
    private String systemId;

    /**
     * 对应的系统名称
     */
    private String systemName;

    /**
     * 微信userID（企业成员）
     */
    private String wxUserId;

    /**
     * 微信openId(非企业成员)
     */
    private String wxOpenId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }
}
