package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;

/**
 * Created by Administrator on 2019-07-19.
 * @author XiaoSong
 * @date 2019/07/19
 */
public class OutMaterData extends BaseEntity {
    private String pid;
    private String pname;
    private Double money;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Double getMoney() {
        return money == null ? 0 : money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
