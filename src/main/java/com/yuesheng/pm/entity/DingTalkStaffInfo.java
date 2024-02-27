package com.yuesheng.pm.entity;

import java.util.Date;

/**
 * @ClassName DingTalkStaffInfo
 * @Description
 * @Author ssk
 * @Date 2022/7/21 0021 13:50
 */
public class DingTalkStaffInfo extends BaseEntity {

    /**
     * 员工id
     */
    private String staffId;

    /**
     * 钉钉用户id
     */
    private String dingTalkUserId;

    /**
     *员工在当前开发者企业账号范围内的唯一标识,同一小程序应用所属的组织下的unionid是唯一的
     */
    private String unionId;

    /**
     * 绑定时间
     */
    private Date createTime;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDingTalkUserId() {
        return dingTalkUserId;
    }

    public void setDingTalkUserId(String dingTalkUserId) {
        this.dingTalkUserId = dingTalkUserId;
    }
}
