package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-17 每次审批流程的过程表 sdpo002b.
 * @author XiaoSong
 * @date 2016/08/17
 */
public class CoursePersonAttached extends CoursePerson {
    /**
     * 流程过程审批人员对象主键id    b01
     */
    private String coursePersonId;
    /**
     * 流程记录表id   b02
     */
    private String flowHistoryId;
    /**
     * 作用未知      b06
     */
    private String b06 = "";
    /**
     * 默认为0，作用未知 b07
     */
    private int b07 = 0;
    /**
     * 作用未知，默认为0，添加时无需   po00209=b10
     */
    private byte b10 = 0;
    /**
     * 窗口id号 b11
     */
    private String frameId;
    /**
     * 作用未知，默认为0，添加时无需   b12
     */
    private String b12 = "0";
    /**
     * 作用未知，默认空字符串   b10
     */
    private byte b13 = 1;

    public String getCoursePersonId() {
        return coursePersonId;
    }

    public void setCoursePersonId(String coursePersonId) {
        this.coursePersonId = coursePersonId;
    }

    public String getFlowHistoryId() {
        return flowHistoryId;
    }

    public void setFlowHistoryId(String flowHistoryId) {
        this.flowHistoryId = flowHistoryId;
    }

    public String getB06() {
        return b06;
    }

    public void setB06(String b06) {
        this.b06 = b06;
    }

    public int getB07() {
        return b07;
    }

    public void setB07(int b07) {
        this.b07 = b07;
    }

    public byte getB10() {
        return b10;
    }

    public void setB10(byte b10) {
        this.b10 = b10;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public String getB12() {
        return b12;
    }

    public void setB12(String b12) {
        this.b12 = b12;
    }

    public byte getB13() {
        return b13;
    }

    public void setB13(byte b13) {
        this.b13 = b13;
    }
}
