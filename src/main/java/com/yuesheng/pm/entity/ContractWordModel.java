package com.yuesheng.pm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ContractWordModel
 * @Description
 * @Author ssk
 * @Date 2024/1/31 0031 11:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractWordModel {
    private int id;
    private String name;
    private String richText;
    private int type;//合同类型：0=租赁合同，1=物业合同，2=多经类合同，9=作废
    private String paramsArr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParamsArr() {
        return paramsArr;
    }

    public void setParamsArr(String paramsArr) {
        this.paramsArr = paramsArr;
    }
}
