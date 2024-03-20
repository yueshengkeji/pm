package com.yuesheng.pm.entity;


/**
 * (ProBackMaster)实体类
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
public class ProBackMaster extends BaseEntity {
    private static final long serialVersionUID = -79581168687553555L;
    /**
     * 主键
     */
    private String id;
    /**
     * 退货单主键
     */
    private String proBackId;
    /**
     * 材料
     */
    private Material material;
    /**
     * 退货数量
     */
    private Double sum;
    /**
     * 退货单价
     */
    private Double price;
    /**
     * 退货金额
     */
    private Double money;
    /**
     * 采购明细id
     */
    private String proRowId;
    /**
     * 项目
     */
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProBackId() {
        return proBackId;
    }

    public void setProBackId(String proBackId) {
        this.proBackId = proBackId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getProRowId() {
        return proRowId;
    }

    public void setProRowId(String proRowId) {
        this.proRowId = proRowId;
    }

}

