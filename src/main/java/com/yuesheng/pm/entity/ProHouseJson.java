package com.yuesheng.pm.entity;

/**
 * (ProHouseJson)实体类
 *
 * @author xiaoSong
 * @since 2021-08-19 10:55:26
 */
public class ProHouseJson extends BaseEntity {
    private static final long serialVersionUID = -58019713977318825L;

    private String key;

    private String json;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}