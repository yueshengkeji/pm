package com.yuesheng.pm.model;

import com.yuesheng.pm.annotation.Excel;
import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.entity.Staff;

/**
 * Created by 96339 on 2017/1/6.
 * 材料使用记录model
 *
 * @author XiaoSong
 * @date 2017/01/06
 */
public class MaterUseHistory extends BaseEntity {
    /**
     * 明细行主键
     */
    private String detailId;
    /**
     * 材料对象
     */
    private Material material;
    /**
     * 入库数量
     */
    private Double putSum = 0.0;
    /**
     * 入库单号
     */
    private String putNumber;
    /**
     * 出库数量
     */
    @Excel(name = "数量",width = 3096)
    private Double outSum = 0.0;
    /**
     * 出库单号
     */
    @Excel(name = "单号",width = 3096)
    private String outNumber;
    /**
     * 入库与出库时间
     */
    @Excel(name = "时间",width = 3096)
    private String putDate, outDate;
    /**
     * 项目
     */
    private Project project;
    /**
     * 入库人员
     */
    private Staff staff;
    /**
     * 出入库金额 根据当前获取的数据类型
     */
    @Excel(name = "总额")
    private double money;
    /**
     * 税率
     */
    @Excel(name = "税率")
    private Double tax;
    /**
     * 出库单价
     */
    @Excel(name = "单价",width = 3096)
    private Double outPrice;
    /**
     * 出库金额
     */
    @Excel(name = "金额",width = 3096)
    private Double outMoney;
    @Excel(name = "序号",width = 2048)
    private int index;
    @Excel(name = "材料编码",noBreak = true,width = 4096)
    private String coding;

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }

    public Double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getPutSum() {
        return putSum;
    }

    public void setPutSum(Double putSum) {
        this.putSum = putSum;
    }

    public String getPutNumber() {
        return putNumber;
    }

    public void setPutNumber(String putNumber) {
        this.putNumber = putNumber;
    }

    public Double getOutSum() {
        return outSum;
    }

    public void setOutSum(Double outSum) {
        this.outSum = outSum;
    }

    public String getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(String outNumber) {
        this.outNumber = outNumber;
    }

    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTax() {
        return this.tax;
    }
}
