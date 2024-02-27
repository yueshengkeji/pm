package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * (ProFixedApply)实体类
 *
 * @author xiaoSong
 * @since 2022-11-29 09:41:20
 */
public class FixedApply extends BaseEntity {
    private static final long serialVersionUID = -52348037888179700L;

    private String id;

    private String title;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date datetime;

    private Byte state;

    private String reason;

    private Staff staff;

    private Section section;

    private List<FixedAssets> assetsList;

    public List<FixedAssets> getAssetsList() {
        return assetsList;
    }

    public void setAssetsList(List<FixedAssets> assetsList) {
        this.assetsList = assetsList;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}