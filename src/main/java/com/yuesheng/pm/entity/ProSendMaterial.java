package com.yuesheng.pm.entity;

/**
 * @author XiaoSong
 * @date 2019-12-09 10:13
 */
public class ProSendMaterial extends BaseEntity {
    /**
     * 发货申请单主体id
     */
    private String proId;
    /**
     * 发货材料
     */
    private Material material;
    /**
     * 发货数量
     */
    private Double sendNum;
    /**
     * 实际发货数量
     */
    private Double sNum;
    /**
     * 拟定发货日期
     */
    private String sendDate;
    /**
     * 图纸镜像页码
     */
    private String imgPage;
    /**
     * 备注信息
     */
    private String remark;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Double getSendNum() {
        return sendNum;
    }

    public void setSendNum(Double sendNum) {
        this.sendNum = sendNum;
    }

    public Double getsNum() {
        return sNum;
    }

    public void setsNum(Double sNum) {
        this.sNum = sNum;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getImgPage() {
        return imgPage;
    }

    public void setImgPage(String imgPage) {
        this.imgPage = imgPage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
