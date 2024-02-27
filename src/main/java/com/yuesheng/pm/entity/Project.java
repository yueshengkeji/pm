package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import com.yuesheng.pm.model.ProjectMaterial;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-04 项目类 sdpa001.
 *
 * @author XiaoSong
 * @date 2016/08/01
 */
@Schema(name="项目对象")
public class Project extends BaseEntity {
    /**
     * 项目名称  pa00102
     */
    @Schema(name="项目名称")
    @Excel(name = "项目名称", width = 6096, noBreak = true)
    private String name;
    /**
     * 负责人   pa00112
     */
    @Schema(name="负责人")
    private String managerName = "";
    /**
     * 业主单位id    pa00105
     */
    @Schema(name="业主单位id")
    private String owner;
    /**
     * 建设单位  pa00158
     */
    @Schema(name="建设单位id")
    private String construction;
    /**
     * 单据添加日期
     */
    @Schema(name="单据添加时间")
    private String date;
    /**
     * 项目立项日期       project approve
     */
    @Schema(name="立项时间")
    private String paDate;
    /**
     * 项目目录主键
     */
    @Schema(name="所属目录id")
    private String folderId;
    /**
     * 暂用随机号代替，目前未发现作用
     */
    @Schema(name="项目编号")
    private String code;
    /**
     * 立项人员
     */
    @Schema(name="立项人")
    private Staff staff;
    /**
     * 项目编号
     */
    @Schema(name="项目编号")
    private String series;
    /**
     * 是否审核：0=否，1=是
     */
    @Schema(name="是否审核：0=否，1=是")
    private int state;
    /**
     * 审核时间
     */
    @Schema(name="审核时间")
    private String approveDate;
    /**
     * 备注,说明
     */
    @Schema(name="备注")
    private String remark;
    @Schema(name="说明")
    private String explain;
    /**
     * 项目所在目录
     */
    @Schema(name="所在目录对象")
    private Folder folder;
    /**
     * 项目地点
     */
    @Schema(name="项目地点")
    private String add;
    /**
     * 项目地区
     */
    @Schema(name="所在地区")
    private Region city;
    /**
     * 业主单位
     */
    @Schema(name="业主单位对象")
    private Company oOwner;
    /**
     * 建设单位
     */
    @Schema(name="建设单位对象")
    private Company oConstruction;
    /**
     * 项目造价      21
     */
    @Schema(name="项目造价")
    private Double money = 0.0;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Region getCity() {
        return city;
    }

    public void setCity(Region city) {
        this.city = city;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }


    public Company getoConstruction() {
        return oConstruction;
    }

    public void setoConstruction(Company oConstruction) {
        this.oConstruction = oConstruction;
    }

    public Company getoOwner() {
        return oOwner;
    }

    public void setoOwner(Company oOwner) {
        this.oOwner = oOwner;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    private List<ProjectMaterial> projectMaterials;

    public List<ProjectMaterial> getProjectMaterials() {
        return projectMaterials;
    }

    public void setProjectMaterials(List<ProjectMaterial> projectMaterials) {
        this.projectMaterials = projectMaterials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaDate() {
        return paDate;
    }

    public void setPaDate(String paDate) {
        this.paDate = paDate;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", managerName='" + managerName + '\'' +
                ", owner='" + owner + '\'' +
                ", construction='" + construction + '\'' +
                ", date='" + date + '\'' +
                ", paDate='" + paDate + '\'' +
                ", folderId='" + folderId + '\'' +
                ", code='" + code + '\'' +
                ", staff=" + staff +
                ", series='" + series + '\'' +
                ", state=" + state +
                ", approveDate='" + approveDate + '\'' +
                ", remark='" + remark + '\'' +
                ", explain='" + explain + '\'' +
                ", folder=" + folder +
                ", add='" + add + '\'' +
                ", city=" + city +
                ", oOwner=" + oOwner +
                ", oConstruction=" + oConstruction +
                ", projectMaterials=" + projectMaterials +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
