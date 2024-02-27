package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-08 采购订单对象.
 *
 * @author XiaoSong
 * @date 2016/08/08
 */
@Schema(name="采购订单主体")
public class Procurement extends BaseEntity {
    /**
     * 采购职员  20
     */
    @Schema(name="采购员")
    private Staff staff;
    /**
     * 采购日期  02
     */
    @Excel(name = "采购日期")
    @Schema(name="采购日期")
    private String pmDate;
    /**
     * 订单编号  01
     */
    @Excel(name = "订单编号", width = 6000)
    @Schema(name="订单编号")
    private String pmNumber;
    /**
     * 税率      16
     */
    @Excel(name = "税率", width = 2048)
    @Schema(name="税率")
    private Double tax;
    /**
     * 供应单位对象    04
     */
    @Schema(name="供应商单位")
    private Company company;
    /**
     * 制单时间  08
     */
    @Schema(name="制单日期")
    private String voucherDate;
    /**
     * 发起时间  09
     */
    @Schema(name="发起审批时间")
    private String fqDate;
    /**
     * 制单人员  10
     */
    @Schema(name="制单人姓名")
    private String voucherName;
    /**
     * 制单人员编码 10
     */
    @Schema(name="制单人编码")
    private String voucherCoding;
    /**
     * 货运地址  06
     */
    @Schema(name="货运地址")
    private City city;
    /**
     * 审核人员 13
     */
    @Schema(name="审核人姓名")
    private String auditName;
    /**
     * 审核人员 13
     */
    @Schema(name="审核人编码")
    private String auditCoding;
    /**
     * 审核人员 17
     */
    @Schema(name="审核时间")
    private String approveDate;
    /**
     * 审核状态 14   {-1：未发起，0：未审核，1已审核}
     */
    @Excel(name = "是否审核", coverFormat = "0=未审核,1=已审核")
    @Schema(name="审核状态，0=未审核，1=已审核")
    private byte state;
    /**
     * 合同对象
     */
    @Schema(name="合同对象")
    private Contract contract;
    /**
     * 采购订单材料
     */
    @Schema(name="订单材料列表")
    private List<ProMaterial> material;
    /**
     * 备注        07
     */
    @Schema(name="订单备注")
    private String remark;
    /**
     * 采购单位：1=指定单位（配合参数配置使用），空字符串=默认单位
     */
    @Schema(name="采购单位:1=指定单位,空字符串=默认单位")
    private String pm01326 = "";
    /**
     * 是否作废，1=作废，''=正常
     */
    private String pm01312 = "";
    /**
     * 入库状态{0：未入库，3：部分入库，4：完全入库}
     */
    @Schema(name="入库状态：0：未入库，3：部分入库，4：完全入库")
    private byte putState;
    /**
     * 流程对象
     */
    private Flow flow;

    /**
     * 是否有合同的订单 = 18,0=没有合同，1=有合同未登记，2=有合同已登记
     */
    @Schema(name="是否有合同，0=未付清，1=有合同未登记，2=已付清未开票，3=已付清开票，4=没有合同")
    private int isContract;
    /**
     * 是否有票的订单 = 24，0=否，1=有票未登记，2=有票已登记
     */
    @Schema(name="是否有票：0=没有票，1=有票未登记，2=有票已登记")
    private String isInvoice;
    /**
     * 优惠总额 22
     */
    @Schema(name="订单优惠金额")
    private String saleMoney;
    /**
     * 到货时间
     */
    private String dhDate;
    /**
     * 快递单号
     */
    private String expressCode;

    @Schema(name="收据图片base64")
    private String voucherImg;

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getAuditCoding() {
        return auditCoding;
    }

    public void setAuditCoding(String auditCoding) {
        this.auditCoding = auditCoding;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getDhDate() {
        return dhDate;
    }

    public void setDhDate(String dhDate) {
        this.dhDate = dhDate;
    }


    public String getVoucherImg() {
        return voucherImg;
    }

    public void setVoucherImg(String voucherImg) {
        this.voucherImg = voucherImg;
    }

    public int getIsContract() {
        return isContract;
    }

    public void setIsContract(int isContract) {
        this.isContract = isContract;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public byte getPutState() {
        return putState;
    }

    public void setPutState(byte putState) {
        this.putState = putState;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public String getPm01312() {
        return pm01312;
    }

    public void setPm01312(String pm01312) {
        this.pm01312 = pm01312;
    }

    public String getPm01326() {
        return pm01326;
    }

    public void setPm01326(String pm01326) {
        this.pm01326 = pm01326;
    }

    public String getVoucherCoding() {
        return voucherCoding;
    }

    public void setVoucherCoding(String voucherCoding) {
        this.voucherCoding = voucherCoding;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFqDate() {
        return fqDate;
    }

    public void setFqDate(String fqDate) {
        this.fqDate = fqDate;
    }

    public List<ProMaterial> getMaterial() {
        return material;
    }

    public void setMaterial(List<ProMaterial> material) {
        this.material = material;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getPmDate() {
        return pmDate;
    }

    public void setPmDate(String pmDate) {
        this.pmDate = pmDate;
    }

    public String getPmNumber() {
        return pmNumber;
    }

    public void setPmNumber(String pmNumber) {
        this.pmNumber = pmNumber;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(String saleMoney) {
        this.saleMoney = saleMoney;
    }
}
