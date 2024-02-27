package com.yuesheng.pm.entity;

/**
 * 可见的考勤人员名单(ProWorkCheckShow)实体类
 *
 * @author makejava
 * @since 2020-05-11 15:54:14
 */
public class ProWorkCheckShow extends BaseEntity {
    private static final long serialVersionUID = -27894136902621881L;
    /**
     * 职员id
     */
    private String staffId;
    /**
     * 0=不显示，1=显示
     */
    private Integer isShow;
    /**
     * 职员对象
     */
    private Staff staff;


    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}