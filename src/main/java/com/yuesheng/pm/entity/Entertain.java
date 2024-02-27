package com.yuesheng.pm.entity;

/**
 * @ClassName Entertain
 * @Description
 * @Author ssk
 * @Date 2023/5/6 0006 9:32
 */
public class Entertain extends BaseEntity{

    /**
     * 接待部门
     */
    private Section section;

    /**
     * 负责人
     */
    private Staff staff;

    /**
     * 宴请对象
     */
    private String entertainObject;

    /**
     * 宴请人数
     */
    private Integer entertainNumber;

    /**
     * 陪同人数
     */
    private Integer accompanyingNumber;

    /**
     * 招待事由
     */
    private String entertainReason;

    /**
     * 预计费用
     */
    private Double costPlan;

    /**
     * 实际费用
     */
    private Double costActual;

    /**
     * 备注
     */
    private String remark;

    /**
     * 招待类型  0:就餐 1:住宿 2:招待用品 3:其他
     */
    private String entertainTypes;

    /**
     * 招待类型其他
     */
    private String entertainTypeOther;

    /**
     * 招待物品烟酒等级  0:A级 2:B级 3:C级
     */
    private String entertainTobaccoAlcohol;

    /**
     * 招待方式  0:自行安排 1:公司安排 2:外出
     */
    private Integer entertainWay;

    /**
     * 招待类别  0:六楼餐厅
     */
    private String entertainCategory;

    /**
     * 招待时间
     */
    private String entertainTime;

    /**
     * 招待用餐标准  0:A类 1:B类 2:C类 3:其他
     */
    private Integer entertainDiningStandard;

    /**
     * 招待用餐标准其他
     */
    private String entertainDiningOther;

    /**
     * 是否商务用车  0:否 1:是
     */
    private Integer entertainCar;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 审核状态
     */
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getEntertainObject() {
        return entertainObject;
    }

    public void setEntertainObject(String entertainObject) {
        this.entertainObject = entertainObject;
    }

    public Integer getEntertainNumber() {
        return entertainNumber;
    }

    public void setEntertainNumber(Integer entertainNumber) {
        this.entertainNumber = entertainNumber;
    }

    public Integer getAccompanyingNumber() {
        return accompanyingNumber;
    }

    public void setAccompanyingNumber(Integer accompanyingNumber) {
        this.accompanyingNumber = accompanyingNumber;
    }

    public String getEntertainReason() {
        return entertainReason;
    }

    public void setEntertainReason(String entertainReason) {
        this.entertainReason = entertainReason;
    }

    public Double getCostPlan() {
        return costPlan;
    }

    public void setCostPlan(Double costPlan) {
        this.costPlan = costPlan;
    }

    public Double getCostActual() {
        return costActual;
    }

    public void setCostActual(Double costActual) {
        this.costActual = costActual;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEntertainTypes() {
        return entertainTypes;
    }

    public void setEntertainTypes(String entertainTypes) {
        this.entertainTypes = entertainTypes;
    }

    public String getEntertainTypeOther() {
        return entertainTypeOther;
    }

    public void setEntertainTypeOther(String entertainTypeOther) {
        this.entertainTypeOther = entertainTypeOther;
    }

    public String getEntertainTobaccoAlcohol() {
        return entertainTobaccoAlcohol;
    }

    public void setEntertainTobaccoAlcohol(String entertainTobaccoAlcohol) {
        this.entertainTobaccoAlcohol = entertainTobaccoAlcohol;
    }

    public Integer getEntertainWay() {
        return entertainWay;
    }

    public void setEntertainWay(Integer entertainWay) {
        this.entertainWay = entertainWay;
    }

    public String getEntertainCategory() {
        return entertainCategory;
    }

    public void setEntertainCategory(String entertainCategory) {
        this.entertainCategory = entertainCategory;
    }

    public String getEntertainTime() {
        return entertainTime;
    }

    public void setEntertainTime(String entertainTime) {
        this.entertainTime = entertainTime;
    }

    public Integer getEntertainDiningStandard() {
        return entertainDiningStandard;
    }

    public void setEntertainDiningStandard(Integer entertainDiningStandard) {
        this.entertainDiningStandard = entertainDiningStandard;
    }

    public String getEntertainDiningOther() {
        return entertainDiningOther;
    }

    public void setEntertainDiningOther(String entertainDiningOther) {
        this.entertainDiningOther = entertainDiningOther;
    }

    public Integer getEntertainCar() {
        return entertainCar;
    }

    public void setEntertainCar(Integer entertainCar) {
        this.entertainCar = entertainCar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
