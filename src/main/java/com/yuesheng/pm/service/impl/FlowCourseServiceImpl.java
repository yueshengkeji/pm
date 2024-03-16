package com.yuesheng.pm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.FlowCourseMapper;
import com.yuesheng.pm.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2016-08-16 流程过程服务接口.
 */
@Service("flowCourseService")
public class FlowCourseServiceImpl implements FlowCourseService {
    @Autowired
    private FlowCourseMapper flowCourseMapepr;
    @Autowired
    private CoursePersonService coursePersonService;
    @Autowired
    private FlowCourseRelationService flowCourseRelationService;
    @Autowired
    private CourseJudeService judeService;
    @Autowired
    @Lazy
    private FlowMessageService messageService;
    @Autowired
    @Lazy
    private FlowApproveService approveService;
    @Autowired
    private StaffService staffService;
    @Autowired
    @Lazy
    private CoursePersonAttachedService attachedService;

    @Override
    public FlowCourse getFlowCourseFirst(String flowId) {
        PageHelper.startPage(1,1);
        return flowCourseMapepr.getFlowCourseFirst(flowId);
    }

    @Override
    public List<FlowCourse> getFlowCourseByFlow(String flowId) {
        return flowCourseMapepr.getFlowCourseByFlow(flowId);
    }

    @Override
    public void addFlowCourseB(FlowCourse flowCourse) {
        flowCourseMapepr.addFlowCourseBt(flowCourse);
    }

    @Override
    public void addFlowCourseBInstance(FlowCourse flowCourse) {
        //统一流程的相同过程只能添加一次
        if (Objects.isNull(flowCourseMapepr.getInstanceById(flowCourse.getId(), flowCourse.getFlowId()))) {
            flowCourseMapepr.addFlowCourseBInstance(flowCourse);
        }
    }

    @Override
    public FlowCourse getFlowCourseById(String id) {
        return flowCourseMapepr.getFlowCourseById(id);
    }

    @Override
    public FlowCourse getNextFlowCourseBByCourseId(String currentId, String historyId, FlowMessage msg) {
        List<FlowCourseBRelation> courseBRelations = flowCourseRelationService.getNextCourseB(currentId, historyId);
        if (courseBRelations.isEmpty()) {
            return null;
        } else if (courseBRelations.size() == 1) {
            //没有多个过程，直接返回
            FlowCourse fc = getById(courseBRelations.get(0).getNextCourseId(), historyId);
            return fc;
        }
        //多个过程，遍历判断下一过程
        String s = msg.getSql();
        if(StringUtils.isBlank(s)){
            s = "";
        }
        StringBuilder sql = new StringBuilder(s);
        if (StringUtils.isBlank(sql.toString())) {
            sql = new StringBuilder(messageService.getSqlById(msg.getId()));
        }
        if (StringUtils.isNotBlank(sql.toString())) {
            for (FlowCourseBRelation courseBRelation : courseBRelations) {
                StringBuilder sbd = new StringBuilder();
                sbd.append(sql);
                FlowCourse fc = flowCourseMapepr.getById(courseBRelation.getNextCourseId(), historyId);
                LogManager.getLogger("mylog").info("flowCourse:" + fc);
                if (!Objects.isNull(fc) && StringUtils.isNotBlank(fc.getPo02019())) {
                    //拼接sql
                    try {
                        sbd.append(" and (");
                        sbd.append("").append(fc.getPo02019());
                        sbd.append(")");
                        sbd.append(" and ").append(msg.getFrameColumn()).append("='").append(msg.getFrameId()).append("'");
                        Map<String, Object> result = flowCourseMapepr.execJudgeSql(sbd.toString());
                        if (!Objects.isNull(result) && !result.isEmpty()) {
                            //条件通过，确定下一过程
                            return fc;
                        }
                    } catch (Exception e) {
                        LogManager.getLogger("mylog").error("execSql error:" + sbd);
                    }
                }
            }
        }
        //没有条件判断sql，默认返回第一个过程
        return getById(courseBRelations.get(0).getNextCourseId(), historyId);
//        return flowCourseMapepr.getNextFlowCourseBByCourseId(currentId, historyId);
    }

    @Override
    public FlowCourse getNotifyCourseBByMsgId(String courseId, String flowMessageId) {
        return flowCourseMapepr.getNotifyCourseBByMsgId(courseId, flowMessageId);
    }

    @Override
    public List<FlowCourse> getNextCourses(String id) {
        return flowCourseMapepr.getNextCourses(id);
    }

    @Override
    public String getParentId(String id) {
        PageHelper.startPage(1,1);
        return flowCourseMapepr.getParentId(id);
    }

    @Override
    public void update(FlowCourse course) {
        flowCourseMapepr.update(course);
        //更新当前过程与上级过程关系
        FlowCourseRelation fcr = course.getFcr();
        //向上：父节点关系不为null，更新节点关系
        if (fcr != null && StringUtils.isNotBlank(fcr.getCurrentId())) {
            if (fcr.getId() == null || "".equals(fcr.getId())) {        //添加
                fcr.setId(UUID.randomUUID().toString());
                flowCourseRelationService.insert(fcr);
            } else {      //更新修改
                flowCourseRelationService.update(fcr);
            }
        } else {      //删除父节点

        }
        //更新审批人员角色集合
        List<CoursePerson> cpList = course.getPersonList();
        for (CoursePerson cp : cpList) {
            coursePersonService.update(cp);
        }
        //更新条件判断集合
        List<CourseJudge> judges = course.getJudgeList();
        for (CourseJudge judge : judges) {
            judge.setCourseId(course.getId());
            judeService.update(judge);
        }
        flowCourseMapepr.updateJudgeSql(course);
        //更新动态代理信息
        CourseInvoke cn = flowCourseMapepr.getInvoke(course.getId());
        if (Objects.isNull(cn)) {
            flowCourseMapepr.insertInvoke(course);
        } else {
            flowCourseMapepr.updateInvoke(course);
        }
    }

    @Override
    public void insert(FlowCourse course) {
        if (course.getFlowId() == null) {
            return;
        }
        if (course.getId() == null) {
            course.setId(UUID.randomUUID().toString());
        }
        flowCourseMapepr.insert(course);
        //添加过程关系表
        FlowCourseRelation fcr = course.getFcr();
        if (fcr != null && StringUtils.isNotBlank(fcr.getCurrentId())) {
            fcr.setId(UUID.randomUUID().toString());
            fcr.setNextCourseId(course.getId());
            flowCourseRelationService.insert(fcr);
        }
        //添加审批人员集合到数据库
        List<CoursePerson> cpList = course.getPersonList();
        if (cpList != null) {
            for (CoursePerson cp : cpList) {
                if (cp != null) {
                    cp.setCourseId(course.getId());
                    cp.setId(UUID.randomUUID().toString());
                    coursePersonService.insert(cp);
                }
            }
        }
        //添加条件判断集合
        List<CourseJudge> judges = course.getJudgeList();
        for (CourseJudge judge : judges) {
            judge.setCourseId(course.getId());
            judeService.update(judge);
        }
        flowCourseMapepr.updateJudgeSql(course);
    }

    @Override
    @Transactional
    public void delete(String courseId) {
        FlowCourse fc = getFlowCourseById(courseId);
        if(!Objects.isNull(fc)){
            //获取该过程后续的所有过程
            List<FlowCourse> courseList = getNextCoursesAll(String.valueOf(fc.getSerial()),fc.getFlowId());
            courseList.forEach(item->{
                item.setSerial(item.getSerial()-1);
                flowCourseMapepr.update(item);
            });

            //获取改过程的子节点过程
            String parentId = getParentId(courseId);
            if(StringUtils.isNotBlank(parentId)){
                flowCourseRelationService.updateRelationBy03(courseId,parentId);
//                List<FlowCourseBRelation> relations = flowCourseRelationService.getRelationByCourseId(courseId);
//                relations.forEach(item->{
//                    //更新子节点的父节点为删除节点的父节点
//
//                });
            }

            //删除向上过程关系
            flowCourseRelationService.deleteRelationByCourseId(courseId);
            //删除过程
            flowCourseMapepr.delete(courseId);
            //删除审批/知会人员
            coursePersonService.deleteByCourse(courseId);
            //删除条件
            judeService.deleteByCourse(courseId);
            //删除向下过程关系
            flowCourseRelationService.deleteRelationByRelation02(courseId);
            //删除动态代理配置
            flowCourseMapepr.deleteInvoke(courseId);
        }

    }

    private List<FlowCourse> getNextCoursesAll(String courseNo, String flowId) {
        return flowCourseMapepr.getNextCoursesAll(courseNo,flowId);
    }

    @Override
    public int insertJudge(FlowCourse course) {
        int row = judeService.insert(course.getJudge());
        row += flowCourseMapepr.updateJudgeSql(course);
        return row;
    }

    @Override
    public int updateJudge(FlowCourse course) {
        int row = judeService.update(course.getJudge());
        row += flowCourseMapepr.updateJudgeSql(course);
        return row;
    }

    @Override
    public FlowCourse getNextFlowCourseBByCourseId(String courseId) {
        return flowCourseMapepr.getCourseBByCourseId(courseId);
    }

    @Override
    public FlowCourse getThanFlowCourseBByCourseId(String courseId, String histroryId) {
        return flowCourseMapepr.getThanFlowCourseByCourseId(courseId, histroryId);
    }

    @Override
    public int updateThanFlowCourseBByCourseId(FlowCourse mfc) {
        return flowCourseMapepr.updateThanFlowCourseBByCourseId(mfc);
    }

    @Override
    public FlowCourse getById(String id, String flowId) {
        return flowCourseMapepr.getById(id, flowId);
    }

    @Override
    public FlowCourse getInstanceById(String id, String flowId) {
        return flowCourseMapepr.getInstanceById(id, flowId);
    }

    @Override
    public List<FlowCourse> getInstanceByFlowId(String flowMessageId, String flowHistoryId) {
        FlowMessage fm = messageService.getMessageById(flowMessageId);
        fm.setStaff(staffService.getStaffById(fm.getStaffId()));
        List<FlowCourse> fc = flowCourseMapepr.getInstanceByFlowId(flowHistoryId);
        ArrayList<FlowCourse> result = new ArrayList<>();
        FlowCourse first = null;
        //查找父元素
        for (FlowCourse item : fc) {
            FlowCourseRelation fcr = flowCourseRelationService.getParent(item.getId());
            if (Objects.isNull(fcr)) {
                first = item;
            } else {
                item.setFcr(fcr);
            }
        }

        if (!Objects.isNull(first)) {
            result.add(first);
            setNext(first, flowHistoryId, fm, result);
        }

        return result;
    }

    private void setNext(FlowCourse fc, String flowHistoryId, FlowMessage fm, ArrayList<FlowCourse> container) {
        if (!Objects.isNull(fc)) {
            List<FlowApprove> fa = approveService.getFlowApproveByCourse(fc.getId(), fm.getId());
            fc.setFlowApproves(fa);
            FlowCourse temp = getNextFlowCourseBByCourseId(fc.getId(), flowHistoryId, fm);

            if (fa.isEmpty()) {
                fc.getPersonList().forEach(item -> {
                    item.setStaff(messageService.getStaffByType(item, fm.getStaff()));
                });
            } else {
                fa.forEach(f -> {
                    f.setAcceptUser(staffService.getStaffById(f.getAcceptStaffId()));
                });
            }

            if (!Objects.isNull(temp)) {
                container.add(temp);
                setNext(temp, flowHistoryId, fm, container);
            }
        }
    }

    @Override
    public void clearCourseB(String startDatetime, String endDatetime) {
        PageHelper.startPage(1, 100, "po00302 asc");
        Page<FlowMessage> msgs = (Page<FlowMessage>) messageService.getOverMessage(startDatetime, endDatetime);
        int page = msgs.getPages();
        for (int i = 2; i <= page; i++) {
            msgs.forEach(item -> {
                List<FlowCourse> fc = flowCourseMapepr.getInstanceByFlowId(item.getHistroryId());
                addHistory(fc);
                deleteInstanceByFlowId(item.getHistroryId());
                flowCourseRelationService.deleteByHistoryId(item.getHistroryId());
                attachedService.deleteByFlowHistoryId(item.getHistroryId());
            });
            PageHelper.startPage(i, 100, "po00302 asc");
            msgs = (Page<FlowMessage>) messageService.getOverMessage(startDatetime, endDatetime);
        }
    }

    @Override
    public int deleteInstanceByFlowId(String historyId) {
        flowCourseMapepr.deleteBByFlowId(historyId);
        return flowCourseMapepr.deleteInstanceByFlowId(historyId);
    }
    @Override
    public FlowCourse getFlowCourseBById(String courseId,String flowId) {
        PageHelper.startPage(1,1);
        return flowCourseMapepr.getFlowCourseBById(courseId,flowId);
    }


    @Override
    public FlowCourse getFlowCourseBByInstance(String courseId) {
        PageHelper.startPage(1,1);
        return flowCourseMapepr.getFlowCourseBByInstance(courseId);
    }

    @Override
    public int deleteCourseBInstanceHistory(String histroryId) {
        return flowCourseMapepr.deleteCourseBInstanceHistory(histroryId);
    }

    private int addHistory(List<FlowCourse> fc) {
        fc.forEach(item -> {
            try {
                flowCourseMapepr.addFLowCourseBHistory(item);
                flowCourseMapepr.addFlowCourseBInstanceHistory(item);
            } catch (Exception e) {
                // ignore error
            }
        });
        return 0;
    }
}
