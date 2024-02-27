package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-17 【我的审批】主表对象   sdpo006.
 *
 * @author XiaoSong
 * @date 2016/08/17
 */
public class MyApprove extends BaseEntity {
    /**
     * 接收时间  02
     */
    private String acceptDate;
    /**
     * 发送人id 03
     */
    private String sendPerson;
    /**
     * 流程主题  04
     */
    private String name;
    /**
     * 原单据标题 05
     */
    private String title;
    /**
     * 数据库默认0
     */
    private int po00606;
    /**
     * 数据库默认0
     */
    private int po00607;
    /**
     * 窗口编号  11
     */
    private String frameCoding;
    /**
     * 窗口单据主id   12
     */
    private String frameId;
    /**
     * 主题发起用户
     */
    private Staff initiate;
    /**
     * 发送用户
     */
    private Staff sendUser;
    /**
     * 当前流程状态    来源：sdpo003表---po00308列
     */
    private int state;
    /**
     * 审批状态对象
     */
    private FlowApprove approve;
    /**
     * 流程发起时间
     */
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Staff getInitiate() {
        return initiate;
    }

    public void setInitiate(Staff initiate) {
        this.initiate = initiate;
    }

    public Staff getSendUser() {
        return sendUser;
    }

    public void setSendUser(Staff sendUser) {
        this.sendUser = sendUser;
    }

    public FlowApprove getApprove() {
        return approve;
    }

    public void setApprove(FlowApprove approve) {
        this.approve = approve;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPo00606() {
        return po00606;
    }

    public void setPo00606(int po00606) {
        this.po00606 = po00606;
    }

    public int getPo00607() {
        return po00607;
    }

    public void setPo00607(int po00607) {
        this.po00607 = po00607;
    }

    public String getFrameCoding() {
        return frameCoding;
    }

    public void setFrameCoding(String frameCoding) {
        this.frameCoding = frameCoding;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }
}
