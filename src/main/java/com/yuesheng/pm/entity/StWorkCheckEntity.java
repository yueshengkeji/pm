package com.yuesheng.pm.entity;

/**
 * 商汤科技-考勤机数据实体
 */
public class StWorkCheckEntity extends BaseEntity {
    /**
     * 考勤日期
     */
    private String signDate;
    /**
     * 考勤设备名称
     */
    private String deviceName;
    /**
     * 考勤模式 1 = 入口
     */
    private int entryMode;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 考勤日期+时间（秒）
     */
    private long signTime;
    /**
     * 识别头像id ，获取地址：https://link.bi.sensetime.com/v1/image/1/ + avatar
     */
    private String avatar;
    /**
     * 识别大图，获取地址：https://link.bi.sensetime.com/v1/image/2/ + signBgAvatar
     */
    private String signBgAvatar;

    public String getSignBgAvatar() {
        return signBgAvatar;
    }

    public void setSignBgAvatar(String signBgAvatar) {
        this.signBgAvatar = signBgAvatar;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getEntryMode() {
        return entryMode;
    }

    public void setEntryMode(int entryMode) {
        this.entryMode = entryMode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
