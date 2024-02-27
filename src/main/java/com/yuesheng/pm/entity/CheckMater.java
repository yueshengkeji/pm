package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

import java.util.List;

/**
 * Created by 96339 on 2017/3/1 材料盘点     sdpm015.
 * @author XiaoSong
 * @date 2017/03/01
 */
public class CheckMater extends BaseEntity {
    /**
     * 主键 01
     */
    private String id;
    /**
     * 盘点日期 02
     */
    @Excel(name = "盘点日期", width = 3096)
    private String checkDate;
    /**
     * 盘点单号 03
     */
    @Excel(name = "盘点单号", width = 3096)
    private String checkNumber;
    /**
     * 备注 05
     */
    private String remark;
    /**
     * 制单日期 07
     */
    private String date;
    /**
     * 审核日期 12
     */
    private String approveDate;
    /**
     * 审核状态 13
     */
    private int state;
    /**
     * 制单人 09
     */
    private Staff staff;
    /**
     * 审核人 11
     */
    private Staff approveStaff;
    /**
     * 仓库对象 04
     */
    private Storage storage;
    /**
     * 盘点材料集合
     */
    private List<CheckMaterChild> materList;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public List<CheckMaterChild> getMaterList() {
        return materList;
    }

    public void setMaterList(List<CheckMaterChild> materList) {
        this.materList = materList;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
