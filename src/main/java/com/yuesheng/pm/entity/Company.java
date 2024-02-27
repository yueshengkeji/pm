package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-04 企业（第三方单位）对象      sdpf003.
 * @author XiaoSong
 * @date 2016/08/01
 */
@Schema(name="单位对象")
public class Company extends BaseEntity {
    /**
     * 单位名称
     */
    @Schema(name="名称")
    @Excel(name = "单位名称",noBreak = true)
    private String name;
    /**
     * 单位目录
     */
    @Schema(name="所在目录")
    private String folder;
    /**
     * 是否供应商（0:否，1：是）
     */
    @Schema(name="是否供应商：0=否，1=是")
    private byte isSupply;
    /**
     * 是否客户（0:否，1：是）
     */
    @Schema(name="是否客户：0=否，1=是")
    private byte isClient;
    /**
     * 登记时间
     */
    @Schema(name="登记时间")
    private String logDate;
    /**
     * 单位编号
     */
    @Schema(name="单位编号")
    private String unitNumber;
    /**
     * 税务号
     */
    @Schema(name="税务号")
    private String taxNumber;
    /**
     * 银行账号
     */
    @Schema(name="银行账号")
    private String bankNumber;
    /**
     * 开户行
     */
    @Schema(name="开户行")
    private String openAccount;
    /**
     * 联系人
     */
    @Schema(name="联系人")
    private String relationP;
    /**
     * 座机/联系方式
     */
    @Schema(name="座机号码")
    private String telephoneP;
    /**
     * 财务邮箱
     */
    @Schema(name="财务邮箱")
    private String emailP;
    /**
     * 财务手机号
     */
    @Schema(name="财务手机号")
    private String tel;
    /**
     * 单位地址
     */
    @Schema(name="单位地址")
    private String address;
    /**
     * 单位目录对象
     */
    @Schema(name="单位所属目录对象")
    private Folder oFolder;

    /**
     * 单位添加人员
     */
    @Schema(name="单位添加人对象")
    private Staff staff;

    /**
     * 管辖区
     */
    @Schema(name="管辖区")
    private String jurisdiction;

    /**
     * 街道
     */
    @Schema(name="街道")
    private String street;

    /**
     * 联系电话
     */
    @Schema(name="联系电话")
    private String phone;

    @Schema(name="银行账号")
    private String bankNumber2 = "";
    /**
     * 开户行
     */
    @Schema(name="开户行")
    private String openAccount2 = "";
    @Schema(name="银行行号")
    private String lineNum;

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getBankNumber2() {
        return bankNumber2;
    }

    public void setBankNumber2(String bankNumber2) {
        this.bankNumber2 = bankNumber2;
    }

    public String getOpenAccount2() {
        return openAccount2;
    }

    public void setOpenAccount2(String openAccount2) {
        this.openAccount2 = openAccount2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Folder getoFolder() {
        return oFolder;
    }

    public void setoFolder(Folder oFolder) {
        this.oFolder = oFolder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public byte getIsSupply() {
        return isSupply;
    }

    public void setIsSupply(byte isSupply) {
        this.isSupply = isSupply;
    }

    public byte getIsClient() {
        return isClient;
    }

    public void setIsClient(byte isClient) {
        this.isClient = isClient;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(String openAccount) {
        this.openAccount = openAccount;
    }

    public String getRelationP() {
        return relationP;
    }

    public void setRelationP(String relationP) {
        this.relationP = relationP;
    }

    public String getTelephoneP() {
        return telephoneP;
    }

    public void setTelephoneP(String telephoneP) {
        this.telephoneP = telephoneP;
    }

    public String getEmailP() {
        return emailP;
    }

    public void setEmailP(String emailP) {
        this.emailP = emailP;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
