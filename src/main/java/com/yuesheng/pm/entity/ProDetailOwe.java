package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;

import static com.yuesheng.pm.util.NumberFormat.formatDouble;

/**
 * Created by 96339 on 2017/2/10 采购明细之欠款明细  pro_detail_owe   proDetail主对象.
 *
 * @author XiaoSong
 * @date 2017/02/10
 */
public class ProDetailOwe extends BaseEntity {
    /**
     * 主键
     */
    private String id;
    /**
     * 主对象id
     */
    private String mainId;
    /**
     * 年初欠款金额
     */
    @Excel(name = "年初欠款", width = 3000)
    private Double oweMoney = 0.0;
    /**
     * 欠款时间
     */
    private String oweDate;
    /**
     * 类型：1=欠款，0=欠票,3=修改日志
     */
    private int type;
    /**
     * 登记时间
     */
    private String date;
    /**
     * 登记人员
     */
    private Staff staff;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public Double getOweMoney() {
        if (oweMoney != null) {
            return formatDouble(oweMoney);
        }
        return oweMoney;
    }

    public void setOweMoney(Double oweMoney) {
        this.oweMoney = oweMoney;
    }

    public String getOweDate() {
        return oweDate;
    }

    public void setOweDate(String oweDate) {
        this.oweDate = oweDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
