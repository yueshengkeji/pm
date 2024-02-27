package com.yuesheng.pm.entity;

import java.io.Serializable;

/**
 * (ProZujinYt)实体类
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
public class ProZujinYt implements Serializable {
    private static final long serialVersionUID = 366563118505217915L;

    private Integer id;

    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}