package com.yuesheng.pm.entity;

/**
 * 汉王考勤机用户信息(ProStaffHw)实体类
 *
 * @author makejava
 * @since 2020-05-06 10:19:45
 */
public class ProStaffHw extends BaseEntity {
    private static final long serialVersionUID = 379778956019419153L;
    /**
     * 用户id
     */
    private String staffId;
    /**
     * 用户头像
     */
    private String head;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

}