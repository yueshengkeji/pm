package com.yuesheng.pm.entity;

import java.util.List;

/**
 * 材料发货申请单主体 pro_send
 *
 * @author XiaoSong
 * @date 2019-12-09 10:09
 */
public class ProSend extends BaseEntity {
    /**
     * 客户名称
     */
    private String name;
    /**
     * 制单日期
     */
    private String date;
    /**
     * 收货地址明细
     */
    private String addressDetail;
    /**
     * 收货地址名称
     */
    private String addressName;
    /**
     * 收货人名称
     */
    private String acceptPerson;
    /**
     * 收货人联系方式
     */
    private String tel;
    /**
     * 货运方式
     */
    private String type;
    /**
     * 运费支付
     */
    private String payMoney;
    /**
     * 制单人
     */
    private Staff staff;
    /**
     * 发货申请单明细集合
     */
    private List<ProSendMaterial> proSendMaterialList;
    /**
     * 审核标记：0=未审核，1=已审核
     */
    private byte approveState = 0;

    public byte getApproveState() {
        return approveState;
    }

    public void setApproveState(byte approveState) {
        this.approveState = approveState;
    }

    public List<ProSendMaterial> getProSendMaterialList() {
        return proSendMaterialList;
    }

    public void setProSendMaterialList(List<ProSendMaterial> proSendMaterialList) {
        this.proSendMaterialList = proSendMaterialList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
