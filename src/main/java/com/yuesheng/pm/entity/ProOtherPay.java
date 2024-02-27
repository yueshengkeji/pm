package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuesheng.pm.annotation.Excel;

import java.util.Date;

/**
 * (ProOtherPay)实体类
 *
 * @author xiaoSong
 * @since 2022-10-19 10:56:02
 */
public class ProOtherPay extends BaseEntity {
    private static final long serialVersionUID = -41885698715802238L;

    private String id;

    private Company company;

    private Project project;

    @Excel(name = "金额")
    private Double payMoney;

    @Excel(name = "付款类型")
    private String payTypeTag;

    private Staff staff;

    private Boolean state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datetime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "付款时间")
    private Date payDatetime;

    private String[] fileId;

    @Excel(name = "标题")
    private String title;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "开户行")
    private String openAccount;

    @Excel(name = "银行账号")
    private String blankNumber;

    private Integer index;

    private String JQ;

    private String ZH;

    private String SP;

    private String YJ;

    public String getYJ() {
        return YJ;
    }

    public void setYJ(String YJ) {
        this.YJ = YJ;
    }

    public String getJQ() {
        return JQ;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setJQ(String JQ) {
        this.JQ = JQ;
    }

    public String getZH() {
        return ZH;
    }

    public void setZH(String ZH) {
        this.ZH = ZH;
    }

    public String getSP() {
        return SP;
    }

    public void setSP(String SP) {
        this.SP = SP;
    }

    public Date getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(Date payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(String openAccount) {
        this.openAccount = openAccount;
    }

    public String getBlankNumber() {
        return blankNumber;
    }

    public void setBlankNumber(String blankNumber) {
        this.blankNumber = blankNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String[] getFileId() {
        return fileId;
    }

    public void setFileId(String[] fileId) {
        this.fileId = fileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayTypeTag() {
        return payTypeTag;
    }

    public void setPayTypeTag(String payTypeTag) {
        this.payTypeTag = payTypeTag;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
