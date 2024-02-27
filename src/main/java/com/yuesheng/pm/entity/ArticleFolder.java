package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2016/12/24 办文目录     sdpo011.
 * @author XiaoSong
 * @date 2016/12/24
 */
public class ArticleFolder extends BaseEntity {
    /**
     * 02        目录名称
     */
    private String name;
    /**
     * 03    上级目录
     */
    private ArticleFolder parent;
    /**
     * 03 上级目录id
     */
    private String parentId;
    /**
     * 05   当前办文目录关联的word模板
     */
    private WordModule wm;
    /**
     * 09 目录类型，3=不关联，0=关联模板，2=关联流程，1=关联表单
     */
    private byte type;
    /**
     * 05 模板id
     */
    private String moduleId;
    /**
     * 根元素id = 所有父元素id+当前元素id
     */
    private String rootId;
    private boolean childrenNode;

    /**
     * child is null
     */
    private Boolean isChildrenNode;


    public void setChildrenNode(Boolean childrenNode) {
        isChildrenNode = childrenNode;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ArticleFolder getParent() {
        return parent;
    }

    public void setParent(ArticleFolder parent) {
        this.parent = parent;
    }

    public WordModule getWm() {
        return wm;
    }

    public void setWm(WordModule wm) {
        this.wm = wm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildrenNode(boolean childrenNode) {
        this.childrenNode = childrenNode;
    }

    public boolean getChildrenNode() {
        return childrenNode;
    }
}
