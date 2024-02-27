package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-08-02 实体超类.
 * @author XiaoSong
 * @date 2016/08/02
 */
@JsonIgnoreProperties(value = {"handler"})
public class BaseEntity implements Serializable {
    private static final long serialVersionUid = 616445466042862696L;

    /**
     * 主键id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
