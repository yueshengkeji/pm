package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-17 【我的审批附表】 sdpo007.
 *
 * @author XiaoSong
 * @date 2016/08/17
 */
public class MyApproveAttached extends BaseEntity {
    /**
     * 审批表id 02 (sdpo006 po00601)
     */
    private String approveId;
    /**
     * 发送人id 03
     */
    private String sendPersong;
    /**
     * 审批人id 04
     */
    private String approvePerson;
    /**
     * 接收时间  05
     */
    private String acceptDate;
    /**
     * 默认空字符串    06
     */
    private String po00706 = "";
    /**
     * 默认空字符串    07
     */
    private String po00707 = "";
    /**
     * 默认0=未审核，1=同意
     */
    private int po00708 = 0;
    /**
     * 默认空字符串
     */
    private String po00709 = "";
    /**
     * 默认0
     */
    private int po00710 = 0;
    /**
     * 默认01
     */
    private int po00711 = 0;
    /**
     * 默认1，数据库中有默认值，插入时无需
     */
    private int po00712 = 1;

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson;
    }

    public String getSendPersong() {
        return sendPersong;
    }

    public void setSendPersong(String sendPersong) {
        this.sendPersong = sendPersong;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getPo00706() {
        return po00706;
    }

    public void setPo00706(String po00706) {
        this.po00706 = po00706;
    }

    public String getPo00707() {
        return po00707;
    }

    public void setPo00707(String po00707) {
        this.po00707 = po00707;
    }

    public int getPo00708() {
        return po00708;
    }

    public void setPo00708(int po00708) {
        this.po00708 = po00708;
    }

    public String getPo00709() {
        return po00709;
    }

    public void setPo00709(String po00709) {
        this.po00709 = po00709;
    }

    public int getPo00710() {
        return po00710;
    }

    public void setPo00710(int po00710) {
        this.po00710 = po00710;
    }

    public int getPo00711() {
        return po00711;
    }

    public void setPo00711(int po00711) {
        this.po00711 = po00711;
    }

    public int getPo00712() {
        return po00712;
    }

    public void setPo00712(int po00712) {
        this.po00712 = po00712;
    }
}
