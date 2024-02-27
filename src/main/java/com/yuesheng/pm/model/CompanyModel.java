package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.Company;

/**
 * Created by 96339 on 2017/12/13.
 * 单位采购金额统计模型
 *
 * @author XiaoSong
 * @date 2017/12/13
 */
public class CompanyModel extends BaseEntity {
    /**
     * 单位对象
     */
    private Company company;
    /**
     * 采购金额总数
     */
    private Double money;
    /**
     * 采购次数
     */
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
