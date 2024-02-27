package com.yuesheng.pm.entity;


/**
 * 客流统计(FlowCount)实体类
 *
 * @author xiaosong
 * @since 2023-08-22 08:47:48
 */
public class FlowCount extends BaseEntity {
    private static final long serialVersionUID = 906135395394087500L;
    /**
     * 车流量
     */
    private Integer carCount;
    /**
     * 人流量
     */
    private Integer personCount;
    /**
     * 统计日期
     */
    private String countDate;
    /**
     * 上传人员id
     */
    private String staffId;
    /**
     * 上传日期
     */
    private String date;
    /**
     * 天气
     */
    private String weather;
    /**
     * 星期
     */
    private String week;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

