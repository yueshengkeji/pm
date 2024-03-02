package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuesheng.pm.annotation.Excel;

import java.util.Date;

/**
 * (ConcatBill)实体类
 *
 * @author xiaosong
 * @since 2024-02-29 16:16:45
 */
public class ConcatBill extends BaseEntity {
    private static final long serialVersionUID = 794933765229845787L;

    private String id;

    private String concatId;
    /**
     * 费用名称
     */
    @Excel(name = "费用名称")
    private String name;
    /**
     * 账单开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "账单开始日期")
    private Date startDate;
    /**
     * 账单截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "账单结束日期")
    private Date endDate;
    /**
     * 计费方式
     */
    @Excel(name = "计费方式", coverFormat = "regular=固定金额")
    private String type;
    /**
     * 计费单位
     */
    @Excel(name = "计费单位", coverFormat = "month=月付,one=一次性付费")
    private String unit;
    /**
     * 支付周期
     */
    @Excel(name = "支付周期", coverFormat = "month=月付,towMonth=两月付,quarter=季付,one=一次性付清")
    private String payCycle;
    /**
     * 是否月度账单
     */
    @Excel(name = "是否月度账单", coverFormat = "0=否,1=是")
    private Integer monthBill;
    /**
     * 支付类型
     */
    @Excel(name = "支付类型", coverFormat = "day=固定日期")
    private String payType;
    /**
     * 支付截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付截止日期")
    private Date payEndDate;
    /**
     * 账单状态
     */
    @Excel(name = "状态", coverFormat = "wait=未支付,pay=部分支付,1=已支付")
    private String state;
    /**
     * 欠费天数
     */
    @Excel(name = "欠费天数")
    private Integer arrearageDay;
    /**
     * 铺位号
     */
    @Excel(name = "铺位号")
    private String room;
    /**
     * 楼层
     */
    @Excel(name = "楼层")
    private String floor;
    /**
     * 品牌
     */
    @Excel(name = "品牌")
    private String brand;
    /**
     * 应收金额
     */
    @Excel(name = "账单金额")
    private Double money;
    /**
     * 实际金额
     */
    @Excel(name = "调整金额")
    private Double sjMoney;
    /**
     * 已收金额
     */
    @Excel(name = "已收金额")
    private Double payMoney;
    /**
     * 已退金额
     */
    @Excel(name = "已退金额")
    private Double backMoney;
    /**
     * 欠费金额
     */
    @Excel(name = "欠费金额")
    private Double arrearage;
    /**
     * 合同类型
     */
    @Excel(name = "合同类型", coverFormat = "0=租赁合同,1=物业管理,2=多经类合同")
    private String concatType;
    /**
     * 状态：0=正常，1=已审核，2=作废
     */
    @Excel(name = "状态", coverFormat = "0=正常,1=已审核,2=作废")
    private Integer approveState;
    /**
     * 开票状态：0=为开票，1=已开票
     */
    @Excel(name = "开票状态", coverFormat = "0=未正常,1=已开票")
    private Integer invoiceState;
    /**
     * 账单生成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "账单生成日期")
    private Date datetime;
    /**
     * 单据添加人
     */
    private String staffId;
    /**
     * 单据来源id
     */
    private String sourceId;
    /**
     * 合同
     */
    private ProZujin concat;

    public ProZujin getConcat() {
        return concat;
    }

    public void setConcat(ProZujin concat) {
        this.concat = concat;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConcatId() {
        return concatId;
    }

    public void setConcatId(String concatId) {
        this.concatId = concatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
    }

    public Integer getMonthBill() {
        return monthBill;
    }

    public void setMonthBill(Integer monthBill) {
        this.monthBill = monthBill;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getPayEndDate() {
        return payEndDate;
    }

    public void setPayEndDate(Date payEndDate) {
        this.payEndDate = payEndDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getArrearageDay() {
        return arrearageDay;
    }

    public void setArrearageDay(Integer arrearageDay) {
        this.arrearageDay = arrearageDay;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getSjMoney() {
        return sjMoney;
    }

    public void setSjMoney(Double sjMoney) {
        this.sjMoney = sjMoney;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Double getArrearage() {
        return arrearage;
    }

    public void setArrearage(Double arrearage) {
        this.arrearage = arrearage;
    }

    public String getConcatType() {
        return concatType;
    }

    public void setConcatType(String concatType) {
        this.concatType = concatType;
    }

    public Integer getApproveState() {
        return approveState;
    }

    public void setApproveState(Integer approveState) {
        this.approveState = approveState;
    }

    public Integer getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(Integer invoiceState) {
        this.invoiceState = invoiceState;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Double getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Double backMoney) {
        this.backMoney = backMoney;
    }
}

