package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gui_lin
 * @Description 证书对象
 * 2021/12/24
 */
@JsonIgnoreProperties(value = {"handler"})
public class Certificate implements Serializable {
    private static final long serialVersionUid = 616445466042845656L;
    /**
     * id
     */
    private Long id;

    /**
     * 序号
     */
    private String serialNumber;

    /**
     * 证书名称
     */
    private String name;

    /**
     * 证书文件
     */
    private String fileUrl;

    /**
     * 领证时间
     */
    private Date gainTime;

    /**
     * 到期时间
     */
    private Date expirationTime;

    /**
     * 上传人员id
     */
    private Long uploadUserId;

    /**
     * 上传人员姓名
     */
    private String uploadUserName;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 更新人员id
     */
    private Long updateUserId;

    /**
     * 更新人员姓名
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否授权
     */
    private int state;

    /**
     * 证书分类目录id
     */
    private Integer catalogueId;

    /**
     * 角色职员集合
     */
    private List<Staff> staffList;

    /**
     * 是否已推送过证书
     */
    private Integer pushMsg;

    /**
     * 证书持有者
     */
    private Staff holder;

    /**
     * 补贴
     */
    private BigDecimal subsidy;

    public Staff getHolder() {
        return holder;
    }

    public void setHolder(Staff holder) {
        this.holder = holder;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public Integer getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(Integer pushMsg) {
        this.pushMsg = pushMsg;
    }

    public Integer getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(Integer catalogueId) {
        this.catalogueId = catalogueId;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getGainTime() {
        return gainTime;
    }

    public void setGainTime(Date gainTime) {
        this.gainTime = gainTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Long getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(Long uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
