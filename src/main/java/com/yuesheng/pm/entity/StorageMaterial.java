package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-23 入库单材料集合 sdpm027.
 *
 * @author XiaoSong
 * @date 2016/08/23
 */
@Schema(name="入库单明细对象")
public class StorageMaterial extends BaseEntity {
    /**
     * 入库单id 01
     */
    @Schema(name="入库单id")
    private String storageId;
    /**
     * 材料对象  03 & 04
     */
    @Schema(name="材料对象")
    private Material material;
    /**
     * 系数   05
     *
     */
    @Schema(name="入库系数（默认1.0）")
    private Double inSum = 1.0;
    /**
     * 入库数量  06
     */
    @Schema(name="入库数量")
    @Excel(name = "入库数量", width = 3096)
    private Double putSum;
    /**
     * 单价（不含税）   07
     */
    @Schema(name="不含税单价")
    private Double price;
    /**
     * 金额（不含税）   08
     */
    @Schema(name="不含税金额")
    private Double money;
    /**
     * 含税单价      09
     */
    @Schema(name="含税单价")
    @Excel(name = "含税单价", width = 3096)
    private Double taxPrice;
    /**
     * 含税金额      10
     */
    @Schema(name="含税金额")
    @Excel(name = "含税金额", width = 3096)
    private Double moneyTax;
    /**
     * 税额        11
     */
    @Schema(name="税额")
    private Double taxMoney;
    /**
     * 采购订单材料表主键id:sdpm014-->pm01402,   12
     */
    @Schema(name="采购订单明细id")
    private String proMaterId;
    /**
     * 备注  13
     */
    @Schema(name="备注")
    private String pm02713 = "";
    /**
     * 不知作用，默认 14 添加时无需
     */
    private Double pm02714 = 0.0;
    /**
     * 不知作用，默认 15 添加时无需
     */
    private Double pm02715 = 0.0;
    /**
     * 不知作用，默认 16 添加时无需
     */
    private Double pm02716 = 0.0;
    /**
     * 不知作用，默认 17 添加时无需
     */
    private Double pm02717 = 0.0;
    /**
     * 不知作用，     18
     */
    @Excel(name = "材料编码",noBreak = true,width = 4096)
    private String pm02718 = "";
    /**
     * 材料排序，默认0
     */
    @Excel(name = "序号",index = true,width = 2048)
    private int pm02719 = 0;
    /**
     * 不知作用，默认"" 20
     */
    private String pm02720 = "";
    /**
     * 不知作用，默认"" 21  添加时无需
     */
    private String pm02721 = "";
    /**
     * 不知作用，默认0.0 22  添加时无需
     */
    private Double pm02722 = 0.0;
    /**
     * 项目id  23
     */
    @Schema(name="项目id")
    private String projectId = "";
    /**
     * 项目对象
     */
    @Schema(name="项目对象")
    private Project project;
    /**
     * 入库时间
     */
    @Schema(name="入库时间")
    @Excel(name = "入库日期", width = 3096)
    private String putDate;
    /**
     * 税率
     */
    @Schema(name="税率")
    @Excel(name = "税率", width = 2048)
    private Double tax;

    /**
     * 入库单号
     */
    @Schema(name="入库单号")
    @Excel(name = "入库单号", width = 3096)
    private String putNumber;

    @Schema(name="入库单对象")
    @Excel(name = "单位名称")
    private Company company;

    private PutStorage putStorage;

    public PutStorage getPutStorage() {
        return putStorage;
    }

    public void setPutStorage(PutStorage putStorage) {
        this.putStorage = putStorage;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPutNumber() {
        return putNumber;
    }

    public void setPutNumber(String putNumber) {
        this.putNumber = putNumber;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getInSum() {
        return inSum;
    }

    public void setInSum(Double inSum) {
        this.inSum = inSum;
    }

    public Double getPutSum() {
        return putSum;
    }

    public void setPutSum(Double putSum) {
        this.putSum = putSum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Double getMoneyTax() {
        return moneyTax;
    }

    public void setMoneyTax(Double moneyTax) {
        this.moneyTax = moneyTax;
    }

    public Double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getProMaterId() {
        return proMaterId;
    }

    public void setProMaterId(String proMaterId) {
        this.proMaterId = proMaterId;
    }

    public String getPm02713() {
        return pm02713;
    }

    public void setPm02713(String pm02713) {
        this.pm02713 = pm02713;
    }

    public Double getPm02714() {
        return pm02714;
    }

    public void setPm02714(Double pm02714) {
        this.pm02714 = pm02714;
    }

    public Double getPm02715() {
        return pm02715;
    }

    public void setPm02715(Double pm02715) {
        this.pm02715 = pm02715;
    }

    public Double getPm02716() {
        return pm02716;
    }

    public void setPm02716(Double pm02716) {
        this.pm02716 = pm02716;
    }

    public Double getPm02717() {
        return pm02717;
    }

    public void setPm02717(Double pm02717) {
        this.pm02717 = pm02717;
    }

    public String getPm02718() {
        return pm02718;
    }

    public void setPm02718(String pm02718) {
        this.pm02718 = pm02718;
    }

    public int getPm02719() {
        return pm02719;
    }

    public void setPm02719(int pm02719) {
        this.pm02719 = pm02719;
    }

    public String getPm02720() {
        return pm02720;
    }

    public void setPm02720(String pm02720) {
        this.pm02720 = pm02720;
    }

    public String getPm02721() {
        return pm02721;
    }

    public void setPm02721(String pm02721) {
        this.pm02721 = pm02721;
    }

    public Double getPm02722() {
        return pm02722;
    }

    public void setPm02722(Double pm02722) {
        this.pm02722 = pm02722;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
