package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

/**
 * Created by 96339 on 2017/3/1 盘点单材料明细 sdpm016.
 *
 * @author XiaoSong
 * @date 2017/03/01
 */
public class CheckMaterChild extends StorageMater {
    /**
     * 盘点单主单据id
     */
    private String mainId;
    /**
     * 填写的实际数量
     */
    @Excel(width = 2048, name = "盘点实际数量")
    private Double realitySum;
    /**
     * 盘点单对象
     */
    @Excel(name = "盘点单")
    private CheckMater cm;

    public CheckMater getCm() {
        return cm;
    }

    public void setCm(CheckMater cm) {
        this.cm = cm;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public Double getRealitySum() {
        return realitySum;
    }

    public void setRealitySum(Double realitySum) {
        this.realitySum = realitySum;
    }
}
