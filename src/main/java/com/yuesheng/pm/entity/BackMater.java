package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by 96339 on 2017/2/8 退料单 sdpm024.
 *
 * @author XiaoSong
 * @date 2017/02/08
 */
@Schema(name="退料单")
public class BackMater extends BaseEntity {

    @Schema(name="退料时间")
    private String backDate;

    @Schema(name="退料单号")
    @Excel(name = "退库单号", width = 3096)
    private String backNumber;

    @Schema(name="部门")
    private Section section;

    @Schema(name="备注")
    private String remark;

    @Schema(name="制单时间")
    @Excel(name = "退库日期", width = 3096)
    private String date;

    @Schema(name="退料项目")
    @Excel(name = "项目名称")
    private Project project;

    @Schema(name="'退料仓库")
    private Storage storage;

    @Schema(name="制单人")
    private Staff staff;

    @Schema(name="出库单对象")
    private MaterOut out;

    @Schema(name="审核时间")
    private String approveDate;

    @Schema(name="审核人编号")
    private String approveStaff;

    @Schema(name="审核状态")
    private byte approveState;

    @Schema(name="退料单位")
    private Company company;

    @Schema(name="退料集合对象")
    private List<BackMaterChild> maters;

    @Schema(name="退库人员")
    @Excel(name = "退库人员", width = 3096)
    private Staff backStaff;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Staff getBackStaff() {
        return backStaff;
    }

    public void setBackStaff(Staff backStaff) {
        this.backStaff = backStaff;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<BackMaterChild> getMaters() {
        return maters;
    }

    public void setMaters(List<BackMaterChild> maters) {
        this.maters = maters;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getBackNumber() {
        return backNumber;
    }

    public void setBackNumber(String backNumber) {
        this.backNumber = backNumber;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public MaterOut getOut() {
        return out;
    }

    public void setOut(MaterOut out) {
        this.out = out;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveStaff() {
        return approveStaff;
    }

    public void setApproveStaff(String approveStaff) {
        this.approveStaff = approveStaff;
    }

    public byte getApproveState() {
        return approveState;
    }

    public void setApproveState(byte approveState) {
        this.approveState = approveState;
    }
}
