package com.yuesheng.pm.entity;


import java.util.List;

/**
 * (ProBack)实体类
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
public class ProBack extends BaseEntity {
    private static final long serialVersionUID = 686611283682418578L;
    
    private String id;
    /**
     * 采购订单id
     */
    private String proId;
    /**
     * 退货原因
     */
    private String remark;
    /**
     * 退货时间
     */
    private String backDatetime;
    /**
     * 单据时间
     */
    private String datetime;
    /**
     * 退货人员
     */
    private Staff staff;
    /**
     * 退货审批状态：0=未审核，1=已审核
     */
    private Integer state;
    /**
     * 退货总金额
     */
    private Double money;
    /**
     * 退货标题(编号)
     */
    private String title;
    /**
     * 退库单位
     */
    private Company Company;
    /**
     * 税率
     */
    private Double tax;
    /**
     * 退货材料列表
     */
    private List<ProBackMaster> masterList;
    /**
     * 是否退回到申请单未采购状态
     */
    private Boolean toApply;

    public Boolean getToApply() {
        return toApply;
    }

    public void setToApply(Boolean toApply) {
        this.toApply = toApply;
    }

    public List<ProBackMaster> getMasterList() {
        return masterList;
    }

    public void setMasterList(List<ProBackMaster> masterList) {
        this.masterList = masterList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBackDatetime() {
        return backDatetime;
    }

    public void setBackDatetime(String backDatetime) {
        this.backDatetime = backDatetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        Company = company;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

}

