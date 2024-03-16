package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowCourseBRelation;
import com.yuesheng.pm.entity.FlowCourseRelation;

import java.util.List;

/**
 * Created by Administrator on 2016-08-18 过程关系表服务接口.
 *
 */
public interface FlowCourseRelationService {
    /**
     * 通过过程id获取关系对象
     *
     * @param courseId 过程对象
     * @return 过程关系对象集合
     */
    List<FlowCourseBRelation> getRelationByCourseId(String courseId);

    /**
     * 添加流转关系对象到sdpo020b_relation表中
     *
     * @param relation 流转关系对象
     */
    void addRelationB(FlowCourseBRelation relation);

    /**
     * 更新当前过程与父过程关系
     * @param fcr 关系对象
     */
    void update(FlowCourseRelation fcr);
    /**
     * 根据当前过程id获取父节点
     * @param courseId 当前过程id
     * @return
     */
    FlowCourseRelation getParent(String courseId);

    /**
     * 添加父节点关系
     * @param fcr
     */
    void insert(FlowCourseRelation fcr);

    /**
     * 删除过程关系
     * @param relationId 关系表主键
     */
    void deleteById(String relationId);

    /**
     * 获取下一过程列表
     * @param currentId 当前过程id
     * @param historyId 流程记录id
     * @return
     */
    List<FlowCourseBRelation> getNextCourseB(String currentId, String historyId);

    /**
     * 获取父（上级）过程
     * @param courseId 过程id
     * @param flowHistoryId 流程记录id
     * @return
     */
    FlowCourseBRelation getParentB(String courseId,String flowHistoryId);

    int deleteByHistoryId(String flowHistoryId);

    /**
     * 删除关系记录表
     * @param histroryId
     * @return
     */
    int deleteRelationBHistory(String histroryId);

    /**
     * 删除记录
     * @param histroryId
     * @param b 是否添加被删除数据到附加记录表
     * @return
     */
    int deleteByHistoryId(String histroryId, boolean b);

    /**
     * 删除过程关系（向上）
     * @param courseId 过程id
     * @return
     */
    int deleteRelationByCourseId(String courseId);

    /**
     * 删除过程关系（向下）
     * @param courseId
     * @return
     */
    int deleteRelationByRelation02(String courseId);

    int updateRelationBy03(String courseId, String parentId);

    /**
     * 获取指定过程的子节点
     * @param courseId 过程id
     * @param isChild 是否加在子孙节点
     * @return
     */
    List<FlowCourseBRelation> getRelationByCourseId(String courseId, boolean isChild);
}
