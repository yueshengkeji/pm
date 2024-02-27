package com.yuesheng.pm.entity;

import java.util.UUID;

/**
 * Created by Administrator on 2019-11-25.
 *
 * @author XiaoSong
 * @date 2019/11/25
 */
public class ApplyDelete extends BaseEntity {
    private String proId;
    private String remark;
    private String date;
    private boolean state;
    private Procurement procurement;

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public ApplyDelete() {

    }

    public ApplyDelete(String proId, String remark) {
        this.setId(UUID.randomUUID().toString());
        this.proId = proId;
        this.remark = remark;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
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
}
