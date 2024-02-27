package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-18 流程过程关系对象 sdpo020_Relation.
 *
 * @author XiaoSong
 * @date 2016/08/18
 */
public class FlowCourseRelation extends BaseEntity {
    /**
     * 向下：下一个过程id 02 向上：当前过程id
     */
    private String nextCourseId;
    /**
     * 向下：当前过程id 03 | 向上：父过程id
     */
    private String currentId;
    /**
     * 默认空字符串,预留
     */
    private String relation04 = "";
    /**
     * 默认为1，预留
     */
    private int relation05 = 1;

    public String getNextCourseId() {
        return nextCourseId;
    }

    public void setNextCourseId(String nextCourseId) {
        this.nextCourseId = nextCourseId;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getRelation04() {
        return relation04;
    }

    public void setRelation04(String relation04) {
        this.relation04 = relation04;
    }

    public int getRelation05() {
        return relation05;
    }

    public void setRelation05(int relation05) {
        this.relation05 = relation05;
    }
}
