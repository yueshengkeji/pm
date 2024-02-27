package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-08 采购合同对象           sdpd004.
 * @author XiaoSong
 * @date 2016/08/08
 */
@Schema(name="采购合同")
public class Contract extends BaseEntity {
    /**
     * 合同名称  02
     */
    @Schema(name="合同名称")
    @Excel(name = "合同名称", width = 9000)
    private String name = "";
    /**
     * 甲方对象       04
     */
    @Schema(name="合同甲方")
    @Excel(name = "甲方")
    private Company partyA;
    /**
     * 乙方对象      05
     */
    @Schema(name="合同乙方")
    @Excel(name = "乙方")
    private Company partyB;
    /**
     * 合同金额      09
     */
    @Schema(name="合同金额")
    @Excel(name = "合同金额", width = 4096)
    private Double price;
    /**
     * 币种        17
     */
    @Schema(name="合同币种")
    private String currency;
    /**
     * 合同拟定人     21
     */
    @Schema(name="合同登记人")
    @Excel(name = "登记人")
    private Staff staff;
    /**
     * 合同编号  52
     */
    @Schema(name="合同编号")
    @Excel(name = "合同编号")
    private String serialNumber;
    /**
     * 运营单位  68
     */
    @Schema(name = "运营单位",hidden = true)
    private Company oCompany;
    /**
     * 合同类型  03
     */
    @Schema(name="合同类型")
    private ContractType type;
    /**
     * 地址      69
     */
    @Schema(name="合同地址,默认南通")
    private City city;
    /**
     * 合同添加时间    19
     */
    @Schema(name="合同添加时间")
    @Excel(name = "登记时间")
    private String date;
    /**
     * 合同状态：1=执行中，2=失效  16
     */
    @Schema(name="合同状态：1=执行中，2=失效")
    private byte state;
    /**
     * 失效原因 lose cause 24
     */
    @Schema(name="合同失效原因")
    private String lose = "";
    /**
     * 合同备注 13
     */
    @Schema(name="备注")
    @Excel(name = "备注")
    private String remark = "无内容";
    /**
     * 付款备注 11
     */
    @Schema(name="付款备注")
    private String fk_remark = "无内容";
    /**
     * 暂不知作用，8位字符串
     */
    @Schema(hidden = true)
    private String pd00434 = "";
    /**
     * 暂不知作用，添加时和34一样，不过长度是255
     */
    @Schema(hidden = true)
    private String pd00435 = "";
    /**
     * 已付款金额 48
     */
    @Schema(name="已付款金额")
    @Excel(name = "已付款金额")
    private Double yetPay;
    /**
     * 开票类型      71
     */
    @Schema(name="开票类型")
    private TaxType taxType;
    /**
     * 流程对象
     */
    @Schema(hidden = true)
    private Flow flow;
    /**
     * 合同项目集合
     */
    @Schema(name="合同项目")
    private List<Project> projects;
    /**
     * 合同订单集合
     */
    @Schema(name="合同采购订单")
    private List<Procurement> procurements;
    /**
     * 合同附件集合
     */
    @Schema(name="合同附件")
    private List<Attach> attaches;
    /**
     * 申请中的金额合计
     */
    @Schema(name="申请中的金额合计")
    @Excel(name = "申请中付款合计")
    private Double applyMoney;

    @Schema(name="欠款金额")
    @Excel(name = "欠款金额")
    private Double debt;

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(Double applyMoney) {
        this.applyMoney = applyMoney;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public String getFk_remark() {
        return fk_remark;
    }

    public void setFk_remark(String fk_remark) {
        this.fk_remark = fk_remark;
    }

    public String getPd00434() {
        return pd00434;
    }

    public void setPd00434(String pd00434) {
        this.pd00434 = pd00434;
    }

    public String getPd00435() {
        return pd00435;
    }

    public void setPd00435(String pd00435) {
        this.pd00435 = pd00435;
    }

    public Double getYetPay() {
        return yetPay;
    }

    public void setYetPay(Double yetPay) {
        this.yetPay = yetPay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Attach> getAttaches() {
        return attaches;
    }

    public void setAttaches(List<Attach> attaches) {
        this.attaches = attaches;
    }

    public List<Procurement> getProcurements() {
        return procurements;
    }

    public void setProcurements(List<Procurement> procurements) {
        this.procurements = procurements;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getLose() {
        return lose;
    }

    public void setLose(String lose) {
        this.lose = lose;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getPartyA() {
        return partyA;
    }

    public void setPartyA(Company partyA) {
        this.partyA = partyA;
    }

    public Company getPartyB() {
        return partyB;
    }

    public void setPartyB(Company partyB) {
        this.partyB = partyB;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Company getoCompany() {
        return oCompany;
    }

    public void setoCompany(Company oCompany) {
        this.oCompany = oCompany;
    }
}
