package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by 96339 on 2017/12/20 固定资产对象-pro_fa.
 *
 * @author XiaoSong
 * @date 2017/12/20
 */
@Schema(name="固定资产明细表")
public class FixedAssets extends Material {

    @Schema(name="资产编号")
    private String series;

    @Schema(name="采购日期")
    private String proDate;

    @Schema(name="资产所在部门")
    private String section;

    @Schema(name="资产使用人")
    private String person;

    @Schema(name="拥有数量")
    private Double haveSum;

    @Schema(name="盘点数量")
    private Double checkSum;

    @Schema(name="复盘数量")
    private Double reSum;

    @Schema(name="审批状态：0=未审批，1=已审批")
    private byte state = 1;

    @Schema(name="固定资产申请表id")
    private String fixedId;

    @Schema(name="固定资产价格")
    private Double money;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getFixedId() {
        return fixedId;
    }

    public void setFixedId(String fixedId) {
        this.fixedId = fixedId;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Double getHaveSum() {
        return haveSum;
    }

    public void setHaveSum(Double haveSum) {
        this.haveSum = haveSum;
    }

    public Double getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(Double checkSum) {
        this.checkSum = checkSum;
    }

    public Double getReSum() {
        return reSum;
    }

    public void setReSum(Double reSum) {
        this.reSum = reSum;
    }

}
