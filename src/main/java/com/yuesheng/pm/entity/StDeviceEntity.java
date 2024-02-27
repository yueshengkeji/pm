package com.yuesheng.pm.entity;

public class StDeviceEntity extends BaseEntity {
    /**
     * 设备序号
     */
    private String sn;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 说明
     */
    private String direction;
    /**
     * 地址
     */
    private String location;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 状态
     */
    private int status;
    /**
     * 公司id
     */
    private int companyId;
    /**
     * 创建日期
     */
    private String createAt;
    /**
     * 更新日期
     */
    private String updateAt;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
