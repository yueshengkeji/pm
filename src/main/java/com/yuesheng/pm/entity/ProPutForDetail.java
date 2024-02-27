package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * Created by 96339 on 2017/2/10 采购和入库明细对象，主对象为ProDetail.
 *
 * @author XiaoSong
 * @date 2017/02/10
 */
public class ProPutForDetail extends BaseEntity {
    /**
     * 主键
     */
    private String id;
    /**
     * 主对象id
     */
    private String mainId;
    /**
     * 项目名称，可以当做项目别名
     */
    private String projectName;
    /**
     * 项目
     */
    private Project project;
    /**
     * 采购日期
     */
    @Excel(name = "应收日期")
    private String proDate;
    /**
     * 采购金额
     */
    @Excel(name = "应收金额")
    private Double proMoney;
    /**
     * 入库日期
     */
    @Excel(name = "实收日期")
    private String putDate;
    /**
     * 入库金额
     */
    @Excel(name = "实收金额")
    private Double putMoney;
    /**
     * 添加人员
     */
    @Excel(name = "登记人员")
    private Staff staff;
    /**
     * 最后修改人员
     */
    @Excel(name = "修改人员")
    private Staff lastStaff;
    /**
     * 数据时间
     */
    private String date;
    /**
     * 最后修改时间
     */
    @Excel(name = "修改日期")
    private String lastDate;
    /**
     * 已付款金额总和 数据来源于：pro_detail_dp sum(pay_money)
     */
    private Double payMoneys = 0.0;
    /**
     * 已到票金额总和 数据来源于：pro_detail_dp sum(dp_money)
     */
    private Double dpMoneys = 0.0;
    /**
     * 采购订单id
     */
    private String proId;
    /**
     * 备注信息
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 入库单id
     */
    private String putId;
    /**
     * 合同信息
     */
    private ProZujin zujin;

    public ProZujin getZujin() {
        return zujin;
    }

    public void setZujin(ProZujin zujin) {
        this.zujin = zujin;
    }

    public Double getPayMoneys() {
        return payMoneys;
    }

    public void setPayMoneys(Double payMoneys) {
        this.payMoneys = payMoneys;
    }

    public Double getDpMoneys() {
        return dpMoneys;
    }

    public void setDpMoneys(Double dpMoneys) {
        this.dpMoneys = dpMoneys;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public Double getProMoney() {
        return proMoney;
    }

    public void setProMoney(Double proMoney) {
        this.proMoney = proMoney;
    }

    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public Double getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(Double putMoney) {
        this.putMoney = putMoney;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getLastStaff() {
        return lastStaff;
    }

    public void setLastStaff(Staff lastStaff) {
        this.lastStaff = lastStaff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPutId(String putId) {
        this.putId = putId;
    }

    public String getPutId() {
        return putId;
    }
}
