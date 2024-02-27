package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.FlowCourseBRelation;
import com.yuesheng.pm.entity.FlowCourseRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-18 流程过程关系对象mapper sdpo020_Relation.
 * @author XiaoSong
 * @date 2016/08/18
 */
@Mapper
public interface FlowCourseRelationMapper {
    /**
     * 通过过程id获取关系对象
     * @param courseId 过程对象
     * @return 过程关系对象集合
     */
    List<FlowCourseBRelation> getRelationByCourseId(String courseId);

    /**
     * 通过过程id和流程记录id 查询下一个过程列表
     * @param courseId
     * @param flowHistoryId
     * @return
     */
    List<FlowCourseBRelation> getNextRelationB(@Param("courseId") String courseId,
                                               @Param("flowHistoryId") String flowHistoryId);

    /**
     * 查找父过程
     * @param courseId 过程id
     * @param flowHistoryId 流程记录id
     * @return
     */
    FlowCourseBRelation getParentB(@Param("courseId")String courseId,@Param("flowHistoryId")String flowHistoryId);

    /**
     * 添加流转关系对象到sdpo020b_relation表中
     * @param relation 流转关系对象
     */
    void addRelationB(FlowCourseBRelation relation);

    /**
     * 更新过程关系，向上更新
     * @param fcr 关系对象
     */
    void update(FlowCourseRelation fcr);

    /**
     * 获取当前过程关系对象，向上（父节点）查询
     * @param courseId  当前过程id
     * @return
     */
    FlowCourseRelation getParent(String courseId);

    /**
     * 添加过程关系
     * @param fcr
     */
    void insert(FlowCourseRelation fcr);

    /**
     * 删除过程关系表
     * @param relationId 过程主键
     */
    void deleteById(@Param("relationId") String relationId);

    List<FlowCourseBRelation> queryRelationByHistoryId(@Param("flowHistoryId") String flowHistoryId);

    int insertRelationBHistory(FlowCourseBRelation item);

    /**
     * 删除过程关系记录
     * @param flowHistoryId
     * @return
     */
    int deleteRelationB(@Param("flowHistoryId") String flowHistoryId);

    /**
     * 删除过程关系记录
     * @param historyId
     * @return
     */
    int deleteRelationBHistory(@Param("historyId") String historyId);

    /**
     * 删除过程关系（向上）
     * @param courseId 过程id
     * @return
     */
    int deleteRelationByCourseId(@Param("courseId") String courseId);

    /**
     * 删除过程关系（向下）
     * @param courseId
     * @return
     */
    int deleteRelationByRelation02(@Param("courseId") String courseId);

    /**
     * 更新子节点的父节点为新的节点
     * @param courseId
     * @param parentId
     * @return
     */
    int updateRelationBy03(@Param("courseId") String courseId,@Param("parentId") String parentId);
}
