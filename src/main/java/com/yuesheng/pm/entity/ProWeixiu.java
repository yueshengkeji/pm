package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

import java.util.Objects;

/**
 * (ProWeixiu)报修登记单实体类
 *
 * @author xiaoSong
 * @since 2021-08-14 14:15:38
 */
public class ProWeixiu extends BaseEntity {
    private static final long serialVersionUID = -63345930518765434L;

    private String id;

    private String projectId;

    @Excel(name = "项目名称")
    private String projectName;

    @Excel(name = "报修内容")
    private String title;

    @Excel(name = "维修结果")
    private String returnContent;

    @Excel(name = "登记时间")
    private String datetime;

    private Integer isService;

    private String staffId;

    private Project project;

    @Excel(name = "登记人")
    private Staff staff;

    private String files;

    @Excel(name = "联系人")
    private String name;

    @Excel(name = "电话")
    private String tel;

    @Excel(name = "反馈人")
    private Staff returnStaff;

    @Excel(name = "反馈人员")
    private String returnStaffName;

    @Excel(name = "反馈时间")
    private String returnTime;

    @Excel(name = "反馈附件")
    private String returnFile;

    public String getReturnFile() {
        return returnFile;
    }

    public void setReturnFile(String returnFile) {
        this.returnFile = returnFile;
    }

    public Staff getReturnStaff() {
        return returnStaff;
    }

    public void setReturnStaff(Staff returnStaff) {
        this.returnStaff = returnStaff;
    }

    public String getReturnStaffName() {
        if (!Objects.isNull(this.returnStaff)) {
            return this.returnStaff.getName();
        } else {
            return returnStaffName;
        }
    }

    public void setReturnStaffName(String returnStaffName) {
        this.returnStaffName = returnStaffName;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(String returnContent) {
        this.returnContent = returnContent;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getIsService() {
        return isService;
    }

    public void setIsService(Integer isService) {
        this.isService = isService;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

}