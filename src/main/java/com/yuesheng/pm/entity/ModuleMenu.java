package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/3/6 办文模板目录对象   sdpo013  .
 * @author XiaoSong
 * @date 2017/03/06
 */
public class ModuleMenu extends BaseEntity {
    /**
     * 目录名称
     */
    private String name;
    /**
     * 上级目录
     */
    private ArticleFolder parent;       

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArticleFolder getParent() {
        return parent;
    }

    public void setParent(ArticleFolder parent) {
        this.parent = parent;
    }
}
