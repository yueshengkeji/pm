package com.yuesheng.pm.entity;

/**
 * 投标盖章申请信息表(ProBid)实体类
 *
 * @author xiaoSong
 * @since 2022-05-16 14:41:21
 */
public class ProBid extends BaseEntity {
    private static final long serialVersionUID = -41891395057532745L;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 建设单位
     */
    private Company company;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 招标代理单位
     */
    private String biddingDlCo;
    /**
     * 项目来源
     */
    private String projectSource;
    /**
     * 收费情况
     */
    private String feeNote;
    /**
     * 负责人
     */
    private Staff busPerson;
    /**
     * 是否自营项目
     */
    private Byte self;
    /**
     * 合作方
     */
    private String cooperate;
    /**
     * 配合方名称
     */
    private String coordinateName;
    /**
     * 投标金额
     */
    private Double bidMoney;
    /**
     * 项目地点
     */
    private String address;
    /**
     * 投标时间
     */
    private String date;
    /**
     * 纸质标或电子标
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 申请人姓名
     */
    private Staff staff;
    /**
     * 申请时间
     */
    private String datetime;
    /**
     * 审核状态
     */
    private int state = 0;
    /**
     * 保证金回款时间
     */
    private String bzjDate;
    /**
     * 保证金回款金额
     */
    private Double bzjMoney;
    /**
     * 标前成本
     */
    private Double beforeMoney;
    /**
     * 计划入场时间
     */
    private String inDate;
    /**
     * 保证金回款状态：0=未回款，1=已回款
     */
    private Integer bzjState;
    /**
     * 当前过程名称
     */
    private String courseName;
    /**
     * 成本
     */
    private String cost;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getBzjState() {
        return bzjState;
    }

    public void setBzjState(Integer bzjState) {
        this.bzjState = bzjState;
    }

    public Double getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(Double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getBzjDate() {
        return bzjDate;
    }

    public void setBzjDate(String bzjDate) {
        this.bzjDate = bzjDate;
    }

    public Double getBzjMoney() {
        return bzjMoney;
    }

    public void setBzjMoney(Double bzjMoney) {
        this.bzjMoney = bzjMoney;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBiddingDlCo() {
        return biddingDlCo;
    }

    public void setBiddingDlCo(String biddingDlCo) {
        this.biddingDlCo = biddingDlCo;
    }

    public String getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    public String getFeeNote() {
        return feeNote;
    }

    public void setFeeNote(String feeNote) {
        this.feeNote = feeNote;
    }

    public Byte getSelf() {
        return self;
    }

    public void setSelf(Byte self) {
        this.self = self;
    }

    public String getCooperate() {
        return cooperate;
    }

    public void setCooperate(String cooperate) {
        this.cooperate = cooperate;
    }

    public String getCoordinateName() {
        return coordinateName;
    }

    public void setCoordinateName(String coordinateName) {
        this.coordinateName = coordinateName;
    }

    public Double getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(Double bidMoney) {
        this.bidMoney = bidMoney;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Staff getBusPerson() {
        return busPerson;
    }

    public void setBusPerson(Staff busPerson) {
        this.busPerson = busPerson;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}