package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-02 材料采购申请单 sdpm034.
 *
 * @author XiaoSong
 * @date 2016/08/02
 */
@Schema(name="材料申请单主体")
public class Apply extends BaseEntity {

    @Schema(name="项目id（支持排序）")
    private String projectid;

    @Schema(name = "项目对象", required = true)
    private Project project;

    @Schema(name = "申请单编号（支持排序）", required = true)
    private String serialNumber;

    @Schema(name="审核标志，{0：未审核，1：审核通过}")
    private byte audit;

    @Schema(name="审核人编号")
    private String approveCoding;

    @Schema(name="单据添加时间（支持排序）")
    private String prepareDate;

    @Schema(name="员工编号")
    private String staffCoding;

    @Schema(name="制单人")
    private Staff staff;

    @Schema(name="采购状态  {0：未采购，1：部分采购，2全部采购}")
    private int state;

    @Schema(name="备注信息")
    private String remark;

    @Schema(name="申请部门")
    private Section section;

    @Schema(name="申请单位id")
    private String unitid;

    @Schema(name="是否抵税")
    private int isTax;

    @Schema(hidden = true)
    private String pm03413;

    @Schema(name = "申请日期", required = true)
    private String date;

    @Schema(name="审核日期")
    private String approveDate;

    @Schema(hidden = true)
    private String pm03416;

    @Schema(name = "货运地址id", hidden = true)
    private String address;

    @Schema(hidden = true)
    private byte pm03418 = 0;

    @Schema(hidden = true)
    private String pm03419;

    @Schema(hidden = true)
    private int pm03420;

    @Schema(name = "调整原因,添加时可为null")
    private String noMark;

    @Schema(name="申请材料列表")
    private List<ApplyMaterial> applyMaterialList;

    @Schema(name="申请单位")
    private Company applyUnit;

    @Schema(name="货运地址对象")
    private City city;

    @Schema(name="提醒采购日期")
    private String notifyDate;

    @Schema(name="设置提醒人编码")
    private String notifyStaffCoding;

    public String getNotifyStaffCoding() {
        return notifyStaffCoding;
    }

    public void setNotifyStaffCoding(String notifyStaffCoding) {
        this.notifyStaffCoding = notifyStaffCoding;
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Company getApplyUnit() {
        return applyUnit;
    }

    public void setApplyUnit(Company applyUnit) {
        this.applyUnit = applyUnit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public List<ApplyMaterial> getApplyMaterialList() {
        return applyMaterialList;
    }

    public void setApplyMaterialList(List<ApplyMaterial> applyMaterialList) {
        this.applyMaterialList = applyMaterialList;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public byte getAudit() {
        return audit;
    }

    public void setAudit(byte audit) {
        this.audit = audit;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getStaffCoding() {
        return staffCoding;
    }

    public void setStaffCoding(String staffCoding) {
        this.staffCoding = staffCoding;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public int getIsTax() {
        return isTax;
    }

    public void setIsTax(int isTax) {
        this.isTax = isTax;
    }

    public String getPrepareDate() {
        return prepareDate;
    }

    public void setPrepareDate(String prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApproveCoding() {
        return approveCoding;
    }

    public void setApproveCoding(String approveCoding) {
        this.approveCoding = approveCoding;
    }

    public String getPm03413() {
        return pm03413;
    }

    public void setPm03413(String pm03413) {
        this.pm03413 = pm03413;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getPm03416() {
        return pm03416;
    }

    public void setPm03416(String pm03416) {
        this.pm03416 = pm03416;
    }

    public byte getPm03418() {
        return pm03418;
    }

    public void setPm03418(byte pm03418) {
        this.pm03418 = pm03418;
    }

    public String getPm03419() {
        return pm03419;
    }

    public void setPm03419(String pm03419) {
        this.pm03419 = pm03419;
    }

    public int getPm03420() {
        return pm03420;
    }

    public void setPm03420(int pm03420) {
        this.pm03420 = pm03420;
    }

    public String getNoMark() {
        return noMark;
    }

    public void setNoMark(String noMark) {
        this.noMark = noMark;
    }
}
