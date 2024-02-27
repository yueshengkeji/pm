package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Collection;

/**
 * @ClassName CollectModel
 * @Description
 * @Author ssk
 * @Date 2023/4/28 0028 10:01
 */
public class CollectModel {
    private Collection collection;
    private int index;

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
