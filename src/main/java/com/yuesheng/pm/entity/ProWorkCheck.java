package com.yuesheng.pm.entity;

/**
 * 员工考勤表(ProWorkCheck)实体类
 *
 * @author makejava
 * @since 2020-05-06 10:49:17
 */
public class ProWorkCheck extends BaseEntity {
    private static final long serialVersionUID = -30367030241327927L;
    /**
     * 主键
     */
    private String id;
    /**
     * 职员id
     */
    private String staffId;
    /**
     * 职员名称
     */
    private String staffName;
    /**
     * 打卡日期
     */
    private String date;
    /**
     * 打卡时间
     */
    private String time;
    /**
     * 打卡类型，1=企业微信正常打卡，2=微信外勤打卡，8=钉钉正常打卡，9=钉钉外勤打卡,10=补卡未审核，11=补卡已审核
     */
    private byte type;
    /**
     * 打卡地点
     */
    private String address;
    /**
     * 打卡备注
     */
    private String note;
    /**
     * 打开附件base64
     */
    private String attache;
    /**
     * 设备mac地址
     */
    private String mac;
    /**
     * 汉王考勤机设备id
     */
    private Integer deviceId;
    /**
     * 用户在设备中的名称
     */
    private String deviceName;
    /**
     *
     */
    private String column10;
    /**
     * 打卡地点纬度
     */
    private Long lat;
    /**
     * 打卡地点经度
     */
    private Long lng;

    /**
     * 打卡头像id
     *
     * @return
     */
    private String signBgAvatar;
    /**
     * 请假对象
     */
    private Leave leave;
    /**
     * 加班对象
     */
    private Overtime overtime;

    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public void setOvertime(Overtime overtime) {
        this.overtime = overtime;
    }

    public String getSignBgAvatar() {
        return signBgAvatar;
    }

    public void setSignBgAvatar(String signBgAvatar) {
        this.signBgAvatar = signBgAvatar;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAttache() {
        return attache;
    }

    public void setAttache(String attache) {
        this.attache = attache;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getColumn10() {
        return column10;
    }

    public void setColumn10(String column10) {
        this.column10 = column10;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }
}
