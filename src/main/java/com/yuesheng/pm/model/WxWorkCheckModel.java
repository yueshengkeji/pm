package com.yuesheng.pm.model;

public class WxWorkCheckModel {
    private String userid;
    private Long checkin_time;
    private String location_title;
    private String location_detail;
    private String notes;
    private String[] mediaids;
    private String exception_type;
    private String checkin_type;
    private Long lat;
    private Long lng;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(Long checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getLocation_title() {
        return location_title;
    }

    public void setLocation_title(String location_title) {
        this.location_title = location_title;
    }

    public String getLocation_detail() {
        return location_detail;
    }

    public void setLocation_detail(String location_detail) {
        this.location_detail = location_detail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String[] getMediaids() {
        return mediaids;
    }

    public void setMediaids(String[] mediaids) {
        this.mediaids = mediaids;
    }

    public String getException_type() {
        return exception_type;
    }

    public void setException_type(String exception_type) {
        this.exception_type = exception_type;
    }

    public String getCheckin_type() {
        return checkin_type;
    }

    public void setCheckin_type(String checkin_type) {
        this.checkin_type = checkin_type;
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
