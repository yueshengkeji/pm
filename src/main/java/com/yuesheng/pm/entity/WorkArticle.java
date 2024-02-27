package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by 96339 on 2016/12/29 办公用品主对象 sdpo204.
 *
 * @author XiaoSong
 * @date 2016/12/29
 */
@Schema(name="办公用品")
public class WorkArticle extends BaseEntity {
    /**
     * 领用编号      02
     */
    @Schema(name="单据编号")
    private String series;
    /**
     * 领用时间      03
     */
    @Schema(name="领用时间")
    private String outDate;
    @Schema(name="领用部门")
    private Section Section;
    /**
     * 备注         06
     */
    @Schema(name="备注")
    private String remark;
    /**
     * 制单时间      08
     */
    @Schema(name="制单时间")
    private String date;
    /**
     * 审核时间      11
     */
    @Schema(name="审核时间")
    private String approveDate;
    /**
     * 审核状态        09
     */
    @Schema(name="审核状态")
    private byte approveStatus;
    /**
     * 领用人       05
     */
    @Schema(name="领用人")
    private Staff staff;
    /**
     * 审核人       10
     */
    @Schema(name="审核人")
    private Staff approveStaff;
    /**
     * 办公用品领用明细
     */
    @Schema(name="办公用品明细")
    private List<MaterOutChild> materOutList;

    @Schema(name="类型：0=领用，1=退还")
    private Integer type = 0;

    public com.yuesheng.pm.entity.Section getSection() {
        return Section;
    }

    public void setSection(com.yuesheng.pm.entity.Section section) {
        Section = section;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<MaterOutChild> getMaterOutList() {
        return materOutList;
    }

    public void setMaterOutList(List<MaterOutChild> materOutList) {
        this.materOutList = materOutList;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
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

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public byte getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(byte approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getApproveStaff() {
        return approveStaff;
    }

    public void setApproveStaff(Staff approveStaff) {
        this.approveStaff = approveStaff;
    }
}
