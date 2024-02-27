package com.yuesheng.pm.entity;

import java.util.List;

/**
 * @author gui_lin
 * @Description 证书目录
 * 2022/1/10
 */
public class Catalogue {
    /**
     * id
     */
    private Integer id;

    /**
     * 目录名称
     */
    private String name;

    /**
     * 上级目录名称
     */
    private String parent;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子目录列表
     */
    private List<Catalogue> children;

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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Catalogue> getChildren() {
        return children;
    }

    public void setChildren(List<Catalogue> children) {
        this.children = children;
    }
}
