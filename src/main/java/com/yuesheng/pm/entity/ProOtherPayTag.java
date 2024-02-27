package com.yuesheng.pm.entity;

/**
 * (ProOtherPayTag)实体类
 *
 * @author xiaoSong
 * @since 2022-10-19 10:56:34
 */
public class ProOtherPayTag extends BaseEntity {
    private static final long serialVersionUID = 900735984478641222L;

    private String id;

    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}