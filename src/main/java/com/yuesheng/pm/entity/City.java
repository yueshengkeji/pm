package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

/**
 * Created by Administrator on 2016-08-11 收货地址对象 sdpm019 || 地区对象 sdpj015.
 * @author XiaoSong
 * @date 2016/08/11
 */
@Schema(name="地址对象")
public class City extends BaseEntity {
    /**
     * 地址名称  02
     */
    @Schema(name="地址名称")
    private String name;
    /**
     * 地址编码  03
     */
    @Schema(name="编码")
    private String coding;
    /**
     * 联系人名称 07
     */
    @Schema(name="联系人")
    private String person;
    /**
     * 联系人电话 08
     */
    @Schema(name="电话")
    private String tel;
    /**
     * 传真
     */
    @Schema(name="传真")
    private String cz;
    /**
     * 联系人邮箱
     */
    @Schema(name="邮箱")
    private String email;
    /**
     * 备注
     */
    @Schema(name="备注")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "地址：{" +
                "详细地址='" + name + '\'' +
                ", 工程='" + coding + '\'' +
                ", 联系人='" + person + '\'' +
                ", 手机号='" + tel + '\'' +
                '}';
    }
}
