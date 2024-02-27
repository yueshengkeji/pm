package com.yuesheng.pm.entity;

/**
 * (ProWorkCheckPermission)实体类
 *
 * @author xiaoSong
 * @since 2023-03-06 08:51:46
 */
public class ProWorkCheckPermission extends BaseEntity {
    private static final long serialVersionUID = -31858949256219436L;

    private String staffId;

    private String sectionId;


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

}