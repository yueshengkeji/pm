package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.FlowCourseBRelation;
import com.yuesheng.pm.entity.FlowCourseRelation;
import com.yuesheng.pm.mapper.FlowCourseRelationMapper;
import com.yuesheng.pm.service.FlowCourseRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016-08-18.
 */
@Service("flowCourseRelationService")
public class FlowCourseRelationServiceImpl implements FlowCourseRelationService {
    @Autowired
    private FlowCourseRelationMapper flowCourseRelationMapper;

    @Override
    public List<FlowCourseBRelation> getRelationByCourseId(String courseId) {
        return flowCourseRelationMapper.getRelationByCourseId(courseId);
    }

    @Override
    public void addRelationB(FlowCourseBRelation relation) {
        flowCourseRelationMapper.addRelationB(relation);
    }

    @Override
    public void update(FlowCourseRelation fcr) {
        flowCourseRelationMapper.update(fcr);
    }

    @Override
    public FlowCourseRelation getParent(String courseId) {
        //一个子节点只能有一个父节点
        PageHelper.startPage(1,1);
        return flowCourseRelationMapper.getParent(courseId);
    }

    @Override
    public void insert(FlowCourseRelation fcr) {
        flowCourseRelationMapper.insert(fcr);
    }

    @Override
    public void deleteById(String relationId) {
        flowCourseRelationMapper.deleteById(relationId);
    }

    @Override
    public List<FlowCourseBRelation> getNextCourseB(String currentId, String historyId) {
        return flowCourseRelationMapper.getNextRelationB(currentId,historyId);
    }

    @Override
    public FlowCourseBRelation getParentB(String courseId,String flowHistoryId){
        PageHelper.startPage(1,1);
        return flowCourseRelationMapper.getParentB(courseId,flowHistoryId);
    }

    @Override
    public int deleteByHistoryId(String flowHistoryId){
        List<FlowCourseBRelation> relations = flowCourseRelationMapper.queryRelationByHistoryId(flowHistoryId);
        relations.forEach(item->{
            flowCourseRelationMapper.insertRelationBHistory(item);
        });
        return flowCourseRelationMapper.deleteRelationB(flowHistoryId);
    }

    @Override
    public int deleteRelationBHistory(String histroryId) {
        return flowCourseRelationMapper.deleteRelationBHistory(histroryId);
    }

    @Override
    public int deleteByHistoryId(String histroryId, boolean b) {
        if(b){
            return deleteByHistoryId(histroryId);
        }else{
            return flowCourseRelationMapper.deleteRelationB(histroryId);
        }
    }

    @Override
    public int deleteRelationByCourseId(String courseId) {
        return flowCourseRelationMapper.deleteRelationByCourseId(courseId);
    }

    @Override
    public int deleteRelationByRelation02(String courseId) {
        return flowCourseRelationMapper.deleteRelationByRelation02(courseId);
    }

    @Override
    public int updateRelationBy03(String courseId, String parentId) {
        return flowCourseRelationMapper.updateRelationBy03(courseId,parentId);
    }

    @Override
    public List<FlowCourseBRelation> getRelationByCourseId(String courseId, boolean isChild) {
        List<FlowCourseBRelation> result = getRelationByCourseId(courseId);
        if(isChild && !result.isEmpty()){
            result.forEach(item->{
                result.addAll(getRelationByCourseId(item.getCurrentId(),isChild));
            });
        }
        return result;
    }
}
