package com.yuesheng.pm.entity;

/**
 * 入库签字表(ProPutSign)实体类
 *
 * @author makejava
 * @since 2020-06-05 11:07:57
 */
public class ProPutSign extends BaseEntity {
    private static final long serialVersionUID = -75690039133130337L;

    /**
     * 入库人员
     */
    private String staffId;
    /**
     * 入库时间
     */
    private String putDate;

    /**
     * 签名时间
     */
    private String signDate;

    /**
     * 签名人员id
     */
    private String signStaffId;
    /**
     * 签名人名称
     */
    private String signStaffName;
    /**
     * 签名状态：0未签名，1已签名,2=超时自动签名
     */
    private Integer type;

    /**
     * 入库单原始数据
     */
    private String putobj;

    /**
     * 签名图片base64
     */
    private String signImg;
    /**
     * 入库单id
     */
    private String putId;
    /**
     * 采购单id
     */
    private String proId;

    /**
     * 过期日期
     */
    private String pastDate;

    /**
     * 采购订单对象
     */
    private Procurement procurement;
    /**
     * 签字人员对象
     */
    private Staff signStaff;
    private PutStorage putStorage;
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getSignStaff() {
        return signStaff;
    }

    public void setSignStaff(Staff signStaff) {
        this.signStaff = signStaff;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getSignStaffId() {
        return signStaffId;
    }

    public void setSignStaffId(String signStaffId) {
        this.signStaffId = signStaffId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPutobj() {
        return putobj;
    }

    public void setPutobj(String putobj) {
        this.putobj = putobj;
    }

    public String getSignImg() {
        return signImg;
    }

    public void setSignImg(String signImg) {
        this.signImg = signImg;
    }

    public void setPutId(String putId) {
        this.putId = putId;
    }

    public String getPutId() {
        return putId;
    }

    public void setSignStaffName(String signStaffName) {
        this.signStaffName = signStaffName;
    }

    public String getSignStaffName() {
        return signStaffName;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getPastDate() {
        return pastDate;
    }

    public void setPastDate(String pastDate) {
        this.pastDate = pastDate;
    }

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public void setPutStorage(PutStorage putStorage) {
        this.putStorage = putStorage;
    }

    public PutStorage getPutStorage() {
        return putStorage;
    }
}