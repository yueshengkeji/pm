package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-16 流程过程关系记录对象 sdpo020b_Relation.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
public class FlowCourseBRelation extends FlowCourseRelation {
    /**
     * 流程记录id
     */
    private String flowHistoryId;

    public String getFlowHistoryId() {
        return flowHistoryId;
    }

    public void setFlowHistoryId(String flowHistoryId) {
        this.flowHistoryId = flowHistoryId;
    }
}
