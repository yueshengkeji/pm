package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-23 入库单主对象 sdpm026.
 *
 * @author XiaoSong
 * @date 2016/08/23
 */
@Schema(name="入库单实体")
public class PutStorage extends BaseEntity {
    /**
     * 入库日期  02
     */
    @Schema(name="入库日期")
    private String putDate;
    /**
     * 入库单编号 03
     */
    @Schema(name="入库单编号")
    private String putSerial = "";
    /**
     * 供应单位   04
     */
    @Schema(name="供应单位")
    private Company company;
    /**
     * 仓库对象 05
     */
    @Schema(name="入库单所属仓库")
    private Storage storage;
    /**
     * 仓库备注  06
     */
    @Schema(name="入库单备注")
    private String remark = "";
    /**
     * 制单时间  07
     */
    @Schema(name="制单时间")
    private String createDate;
    /**
     * 与制单时间一样   08
     */
    @Schema(hidden = true)
    private String pm02608;
    /**
     * 入库人员编号    09
     */
    @Schema(name="入库人编号")
    private String putPerson;
    /**
     * 与入库人员编号相同 10
     */
    @Schema(hidden = true)
    private String pm02610;
    /**
     * 不知作用 11
     */
    @Schema(hidden = true)
    private String pm02611 = "";
    /**
     * 采购订单id    12
     */
    @Schema(name="采购订单id")
    private String proId = "";
    /**
     * 采购订单名称
     */
    @Schema(name="采购订单名称")
    private String proName;
    /**
     * 税率        13
     */
    @Schema(name="税率")
    private Double tax = 0.0;
    /**
     * 审核日期  14
     */
    @Schema(name="审核日期")
    private String approveDate;
    /**
     * 是否生成发票  15
     */
    @Schema(name="是否生成发票")
    private String pm02615 = "";
    /**
     * 审核人员
     */
    @Schema(name="审核人")
    private String approveStaff = "";
    /**
     * 审核状态 16
     */
    @Schema(name="审核状态")
    private int approveType = 0;
    /**
     * 付款发票
     */
    @Schema(name="是否审核：0=未审核，1=已审核")
    private String payInvoice = "";
    /**
     * 应付发票号    18
     */
    @Schema(name="发票号")
    private Double mixMoney = 0.0;
    /**
     * 运杂费       20
     */
    @Schema(name="运杂费")
    private byte pm02620 = 0;
    /**
     * 预留字段  21
     */
    @Schema(hidden = true)
    private String pm02621 = "";
    /**
     * 预留字段  22
     */
    @Schema(hidden = true)
    private String pm02622 = "";
    /**
     * 入库单类型：1：采购订单入库，2.其他入库 23
     */
    @Schema(name="入库单类型：1=采购订单入库，2=其他入库")
    private byte putType = 1;
    /**
     * 预留字段  24
     */
    @Schema(hidden = true)
    private byte pm02624 = 0;
    /**
     * 预留字段  25
     */
    @Schema(hidden = true)
    private String pm02625 = "";
    /**
     * 预留字段  26
     */
    @Schema(hidden = true)
    private String pm02626 = "";
    /**
     * 预留字段  27
     */
    @Schema(hidden = true)
    private String pm02627 = "";
    /**
     * 预留字段  28
     */
    @Schema(hidden = true)
    private String pm02628 = "";
    /**
     * 预留字段  29
     */
    @Schema(hidden = true)
    private String pm02629 = "";
    /**
     * 项目对象  （需要去入库材料表中反向查询）
     */
    @Schema(name="项目对象")
    private Project project;
    /**
     * 订单对象
     */
    @Schema(name="采购订单对象")
    private Procurement procurement;
    /**
     * 入库人员
     */
    @Schema(name="入库人员对象")
    private Staff staff;
    /**
     * 入库材料集合
     */
    @Schema(name="入库单材料集合")
    private List<StorageMaterial> materialList;
    /**
     * 项目经理确认人
     */
    @Schema(name="项目经理确认人")
    private Staff projectLeader;
    /**
     * 入库总金额
     */
    @Schema(name="入库总金额")
    private Double money;

    @Schema(name="优惠金额")
    private String saleMoney = "";
    private Staff approve;

    public String getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(String saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<StorageMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<StorageMaterial> materialList) {
        this.materialList = materialList;
    }

    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public String getPutSerial() {
        return putSerial;
    }

    public void setPutSerial(String putSerial) {
        this.putSerial = putSerial;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPm02608() {
        return pm02608;
    }

    public void setPm02608(String pm02608) {
        this.pm02608 = pm02608;
    }

    public String getPutPerson() {
        return putPerson;
    }

    public void setPutPerson(String putPerson) {
        this.putPerson = putPerson;
    }

    public String getPm02610() {
        return pm02610;
    }

    public void setPm02610(String pm02610) {
        this.pm02610 = pm02610;
    }

    public String getPm02611() {
        return pm02611;
    }

    public void setPm02611(String pm02611) {
        this.pm02611 = pm02611;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getPm02615() {
        return pm02615;
    }

    public void setPm02615(String pm02615) {
        this.pm02615 = pm02615;
    }

    public String getApproveStaff() {
        return approveStaff;
    }

    public void setApproveStaff(String approveStaff) {
        this.approveStaff = approveStaff;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }

    public String getPayInvoice() {
        return payInvoice;
    }

    public void setPayInvoice(String payInvoice) {
        this.payInvoice = payInvoice;
    }

    public Double getMixMoney() {
        return mixMoney;
    }

    public void setMixMoney(Double mixMoney) {
        this.mixMoney = mixMoney;
    }

    public byte getPm02620() {
        return pm02620;
    }

    public void setPm02620(byte pm02620) {
        this.pm02620 = pm02620;
    }

    public String getPm02621() {
        return pm02621;
    }

    public void setPm02621(String pm02621) {
        this.pm02621 = pm02621;
    }

    public String getPm02622() {
        return pm02622;
    }

    public void setPm02622(String pm02622) {
        this.pm02622 = pm02622;
    }

    public byte getPutType() {
        return putType;
    }

    public void setPutType(byte putType) {
        this.putType = putType;
    }

    public byte getPm02624() {
        return pm02624;
    }

    public void setPm02624(byte pm02624) {
        this.pm02624 = pm02624;
    }

    public String getPm02625() {
        return pm02625;
    }

    public void setPm02625(String pm02625) {
        this.pm02625 = pm02625;
    }

    public String getPm02626() {
        return pm02626;
    }

    public void setPm02626(String pm02626) {
        this.pm02626 = pm02626;
    }

    public String getPm02627() {
        return pm02627;
    }

    public void setPm02627(String pm02627) {
        this.pm02627 = pm02627;
    }

    public String getPm02628() {
        return pm02628;
    }

    public void setPm02628(String pm02628) {
        this.pm02628 = pm02628;
    }

    public String getPm02629() {
        return pm02629;
    }

    public void setPm02629(String pm02629) {
        this.pm02629 = pm02629;
    }

    public Staff getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Staff projectLeader) {
        this.projectLeader = projectLeader;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setApprove(Staff approve) {
        this.approve = approve;
    }

    public Staff getApprove() {
        return approve;
    }
}
