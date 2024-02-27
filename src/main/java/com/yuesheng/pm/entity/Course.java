package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/5/22 项目科目 sdpb006.
 * @author XiaoSong
 * @date 2017/05/22
 */
public class Course extends BaseEntity {
    /**
     * 科目名称  02
     */
    private String name;
    /**
     * 父科目id  03
     */
    private String parentId;
    /**
     * 科目编号  05
     */
    private String series;
    /**
     * 1=系统自带，-1=自定义添加   06
     */
    private int type;
    /**
     * isCompany=是否公司科目    11
     */
    private byte isCompany;
    /**
     * isDetail=是否科目明细     09
     */
    private byte isDetail;
    /**
     * isProject=是否项目成本    12
     */
    private byte isProject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(byte isCompany) {
        this.isCompany = isCompany;
    }

    public byte getIsDetail() {
        return isDetail;
    }

    public void setIsDetail(byte isDetail) {
        this.isDetail = isDetail;
    }

    public byte getIsProject() {
        return isProject;
    }

    public void setIsProject(byte isProject) {
        this.isProject = isProject;
    }
}
