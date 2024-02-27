package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * (ProApplyDine)就餐实体类
 *
 * @author xiaoSong
 * @since 2022-08-15 16:23:56
 */
@Schema(name="就餐申请")
public class ProApplyDine extends BaseEntity {

    @Schema(name="就餐申请人")
    private Staff staff;
    @Schema(name="就餐生擒事由")
    private String note;
    @Schema(name="就餐时间")
    private String date;
    @Schema(name="申请时间")
    private String datetime;
    @Schema(name="单据状态")
    private Integer state;
    @Schema(name="就餐人数")
    private Integer personNum;
    @Schema(name="就餐标准")
    private String standard;
    @Schema(name="申请人所在部门")
    private Section section;
    @Schema(name="项目关联")
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}