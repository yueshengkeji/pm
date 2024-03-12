package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.io.Serializable;
import java.util.List;

/**
 * (ProZujin)实体类
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Schema(name="租赁合同")
public class ProZujin implements Serializable {
    private static final long serialVersionUID = -45260487124975093L;

    private Integer id;

    @Schema(name="合同编号")
    @Excel(name = "合同编号")
    private String series;
    @Schema(name="单位名称")
    @Excel(name = "单位名称")
    private String company;
    @Schema(name="品牌")
    @Excel(name = "品牌")
    private String brand;
    @Schema(name="付款方式，0=年付，1=季度，2=月付")
    private Integer payType;
    @Schema(name="租赁方式，0=固定铺位,1=机动,2=临时收费")
    private Integer zlType;
    @Schema(name="租赁面积")
    private Double acreage;
    @Schema(name="租赁联系人")
    private String zlPerson;
    @Schema(name="租赁联系人手机号")
    private String zlPersonTel;
    @Schema(name="商户业态id")
    private Integer companyTypeId;
    @Schema(name="业态对象")
    private ProZujinYt yt;
    @Schema(name="招商经办人")
    private String staffName;
    @Schema(name="有无递增")
    private Boolean isDz;
    @Schema(name="有无孰商")
    private Boolean isSh;
    @Schema(name="递增金额")
    private Double dzNumber = 0.0;
    @Schema(name="合同应收金额（合同总价）")
    private Double ysMoney = 0.0;
    @Schema(name="招商实际已收合计")
    private Double sjMoney = 0.0;
    @Schema(name="开票金额合集")
    private Double kpMoney = 0.0;
    @Schema(name="财务实际收款合集")
    private Double cwMoney = 0.0;
    @Schema(name="备注")
    private String remark;
    @Schema(name="登记人id")
    private String staffId;
    @Schema(name="登记时间")
    private String dateTime;
    @Schema(name="最后更新人id")
    private String lastStaffId;
    @Schema(name="最后更新人姓名")
    private String lastStaffName;
    @Schema(name="最后更新时间")
    private String lastDateTime;
    @Schema(name="商铺集合")
    private List<ProZujinHouse> houses;
    @Schema(name="年初欠款")
    private ProDetailOwe moneyOwe;
    @Schema(name="年初欠票")
    private ProDetailOwe billOwe;
    @Schema(name="当年租金")
    private Double yearRental = 0d;
    @Schema(name="合同截止时间")
    private String endDatetime;
    @Schema(name="合同开始时间")
    private String startDatetime;
    @Schema(name="保证金")
    private Double bzjMoney;
    @Schema(name="保证金类型")
    private String bzjType;
    @Schema(name="已退保证金")
    private Double returnBzjMoney;
    @Schema(name="合同标记")
    private Double flag;
    @Schema(name="合同类型：0=租赁合同，2=多经类合同，1=物业合同，9=作废")
    private Byte type = 0;
    @Schema(name="合同附件路径")
    private String files;
    @Schema(name = "计划进场日期")
    private String planDate;
    @Schema(name = "计划开业日期")
    private String openDate;
    @Schema(name = "装修天数")
    private String dayNum;
    @Schema(name = "费用条款")
    private List<Term> termList;
    @Schema(name = "品牌单位")
    private Company brandCompany;
    @Schema(name = "保证金列表")
    private List<ProBzj> bzjList;
    @Schema(name = "收款单位")
    private Company receivedCompany;
    @Schema(name = "推广费")
    private List<ProZujinPromotion> tgfList;

    public List<ProZujinPromotion> getTgfList() {
        return tgfList;
    }

    public void setTgfList(List<ProZujinPromotion> tgfList) {
        this.tgfList = tgfList;
    }
    public Company getReceivedCompany() {
        return receivedCompany;
    }

    public void setReceivedCompany(Company receivedCompany) {
        this.receivedCompany = receivedCompany;
    }

    public List<ProBzj> getBzjList() {
        return bzjList;
    }

    public void setBzjList(List<ProBzj> bzjList) {
        this.bzjList = bzjList;
    }

    public Company getBrandCompany() {
        return brandCompany;
    }

    public void setBrandCompany(Company brandCompany) {
        this.brandCompany = brandCompany;
    }

    public List<Term> getTermList() {
        return termList;
    }

    public void setTermList(List<Term> termList) {
        this.termList = termList;
    }
    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getLastStaffName() {
        return lastStaffName;
    }

    public void setLastStaffName(String lastStaffName) {
        this.lastStaffName = lastStaffName;
    }

    /**
     * 会计科目列表
     */
    private List<ProDetailMoney> kjType;

    private ProDetailMoney kj;

    private String housesString;

    private Boolean ysMoneyEarly;

    public Boolean getYsMoneyEarly() {
        return ysMoneyEarly;
    }

    public void setYsMoneyEarly(Boolean ysMoneyEarly) {
        this.ysMoneyEarly = ysMoneyEarly;
    }

    public ProDetailMoney getKj() {
        return kj;
    }

    public void setKj(ProDetailMoney kj) {
        this.kj = kj;
    }

    public String getHousesString() {
        return housesString;
    }

    public void setHousesString(String housesString) {
        this.housesString = housesString;
    }

    public List<ProDetailMoney> getKjType() {
        return kjType;
    }

    public void setKjType(List<ProDetailMoney> kjType) {
        this.kjType = kjType;
    }

    public Double getFlag() {
        return flag;
    }

    public void setFlag(Double flag) {
        this.flag = flag;
    }

    public String getBzjType() {
        return bzjType;
    }

    public void setBzjType(String bzjType) {
        this.bzjType = bzjType;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Double getYearRental() {
        return yearRental;
    }

    public void setYearRental(Double yearRental) {
        this.yearRental = yearRental;
    }

    public ProDetailOwe getMoneyOwe() {
        return moneyOwe;
    }

    public void setMoneyOwe(ProDetailOwe moneyOwe) {
        this.moneyOwe = moneyOwe;
    }

    public ProDetailOwe getBillOwe() {
        return billOwe;
    }

    public void setBillOwe(ProDetailOwe billOwe) {
        this.billOwe = billOwe;
    }

    public Double getDzNumber() {
        return dzNumber;
    }

    public void setDzNumber(Double dzNumber) {
        this.dzNumber = dzNumber;
    }

    public List<ProZujinHouse> getHouses() {
        return houses;
    }

    public void setHouses(List<ProZujinHouse> houses) {
        this.houses = houses;
    }

    public ProZujinYt getYt() {
        return yt;
    }

    public void setYt(ProZujinYt yt) {
        this.yt = yt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getZlType() {
        return zlType;
    }

    public void setZlType(Integer zlType) {
        this.zlType = zlType;
    }

    public Double getAcreage() {
        return acreage;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public String getZlPerson() {
        return zlPerson;
    }

    public void setZlPerson(String zlPerson) {
        this.zlPerson = zlPerson;
    }

    public String getZlPersonTel() {
        return zlPersonTel;
    }

    public void setZlPersonTel(String zlPersonTel) {
        this.zlPersonTel = zlPersonTel;
    }

    public Integer getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(Integer companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Boolean getDz() {
        return isDz;
    }

    public void setDz(Boolean dz) {
        isDz = dz;
    }

    public Boolean getSh() {
        return isSh;
    }

    public void setSh(Boolean sh) {
        isSh = sh;
    }

    public Double getYsMoney() {
        return ysMoney;
    }

    public void setYsMoney(Double ysMoney) {
        this.ysMoney = ysMoney;
    }

    public Double getSjMoney() {
        return sjMoney;
    }

    public void setSjMoney(Double sjMoney) {
        this.sjMoney = sjMoney;
    }

    public Double getKpMoney() {
        return kpMoney;
    }

    public void setKpMoney(Double kpMoney) {
        this.kpMoney = kpMoney;
    }

    public Double getCwMoney() {
        return cwMoney;
    }

    public void setCwMoney(Double cwMoney) {
        this.cwMoney = cwMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLastStaffId() {
        return lastStaffId;
    }

    public void setLastStaffId(String lastStaffId) {
        this.lastStaffId = lastStaffId;
    }

    public String getLastDateTime() {
        return lastDateTime;
    }

    public void setLastDateTime(String lastDateTime) {
        this.lastDateTime = lastDateTime;
    }

    public void setBzjMoney(Double bzjMoney) {
        this.bzjMoney = bzjMoney;
    }

    public Double getBzjMoney() {
        return bzjMoney;
    }

    public void setReturnBzjMoney(Double returnBzjMoney) {
        this.returnBzjMoney = returnBzjMoney;
    }

    public Double getReturnBzjMoney() {
        return returnBzjMoney;
    }
}