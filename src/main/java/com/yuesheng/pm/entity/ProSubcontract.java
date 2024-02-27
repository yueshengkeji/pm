package com.yuesheng.pm.entity;

/**
 * @ClassName ProSubcontract
 * @Description
 * @Author ssk
 * @Date 2022/9/22 0022 8:49
 */
public class ProSubcontract extends BaseEntity {
    /**
     * 合同名称
     */
    private String name;
    /**
     * 项目
     */
    private Project project;
    /**
     * 合同金额
     */
    private Double money;
    /**
     * 乙方
     */
    private Company partyB;
    /**
     * 甲方
     */
    private Company partyA;
    /**
     * 登记人
     */
    private Staff staff;
    /**
     * 合同状态：0=未审核，1=已审核，2=作废
     */
    private int state;
    /**
     * 备注
     */
    private String remark;
    /**
     * 登记时间
     */
    private String dateTime;
    /**
     * 签订日期
     */
    private String signDate;
    /**
     * 合同类型：0=分包合同,1=工程物业合同,2=企划类合同
     */
    private Byte type = 0;
    /**
     * 合同开始日期
     */
    private String startDate;
    /**
     * 合同截止日期
     */
    private String endDate;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Company getPartyB() {
        return partyB;
    }

    public void setPartyB(Company partyB) {
        this.partyB = partyB;
    }

    public Company getPartyA() {
        return partyA;
    }

    public void setPartyA(Company partyA) {
        this.partyA = partyA;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
}
