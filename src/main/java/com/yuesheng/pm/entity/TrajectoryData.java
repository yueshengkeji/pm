package com.yuesheng.pm.entity;

/**
 * @author xiaoSong
 * @date 2020/3/10
 * 人员行程轨迹实体类
 */
public class TrajectoryData extends BaseEntity {
    /**
     * 出发时间
     */
    private String departureDate;
    /**
     * 目的地
     */
    private String arriveAddr;
    /**
     * 途径地点
     */
    private String tjAddr;
    /**
     * 交通工具
     */
    private String jtTool;
    /**
     * 接触人数
     */
    private Integer number;
    /**
     * 回通时间
     */
    private String backDate;
    /**
     * 职员名称
     */
    private String userName;

    /**
     * 是否审核
     */
    private int state;
    /**
     * 职员
     */
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArriveAddr() {
        return arriveAddr;
    }

    public void setArriveAddr(String arriveAddr) {
        this.arriveAddr = arriveAddr;
    }

    public String getTjAddr() {
        return tjAddr;
    }

    public void setTjAddr(String tjAddr) {
        this.tjAddr = tjAddr;
    }

    public String getJtTool() {
        return jtTool;
    }

    public void setJtTool(String jtTool) {
        this.jtTool = jtTool;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }
}
