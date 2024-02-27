package com.yuesheng.pm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ContractWordModelParams
 * @Description
 * @Author ssk
 * @Date 2024/2/1 0001 10:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractWordModelParams {
    private int id;
    private String name;
    private String markName;

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

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }
}
