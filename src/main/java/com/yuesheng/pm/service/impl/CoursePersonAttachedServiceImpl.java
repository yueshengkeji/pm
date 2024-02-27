package com.yuesheng.pm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.CoursePerson;
import com.yuesheng.pm.entity.CoursePersonAttached;
import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.mapper.CoursePersonAttachedMapper;
import com.yuesheng.pm.service.CoursePersonAttachedService;
import com.yuesheng.pm.service.FlowMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016-08-17.
 */
@Service("coursePersonAttachedService")
public class CoursePersonAttachedServiceImpl implements CoursePersonAttachedService {
    @Autowired
    private CoursePersonAttachedMapper coursePersonAttachedMapper;
    @Autowired
    @Lazy
    private FlowMessageService messageService;

    @Override
    public void addAttached(CoursePersonAttached attached) {
        coursePersonAttachedMapper.addPersonAttached(attached);
    }

    @Override
    public List<CoursePersonAttached> getPersonByCourseAFlowId(String currentId, String histroryId) {
        return coursePersonAttachedMapper.getPersonByCourseFlowId(currentId, histroryId);
    }

    @Override
    public List<CoursePerson> getPersonsByType(String historyId, String courseId, int type) {
        return coursePersonAttachedMapper.getPersonsByType(historyId, courseId, type);
    }

    @Override
    public CoursePersonAttached addHistory(CoursePersonAttached attached) {
        coursePersonAttachedMapper.addHistory(attached);
        return attached;
    }

    @Override
    public int deleteByFlowHistoryId(String flowHistoryId) {
        List<CoursePersonAttached> attacheds = coursePersonAttachedMapper.getByFlowHistoryId(flowHistoryId);
        attacheds.forEach(this::addHistory);
        return coursePersonAttachedMapper.deleteByFlowHistoryId(flowHistoryId);
    }

    @Override
    public void clearByDatetime(String startDatetime, String endDatetime) {
        PageHelper.startPage(1, 100, "po00302 asc");
        Page<FlowMessage> msgs = (Page<FlowMessage>) messageService.getOverMessage(startDatetime, endDatetime);
        int page = msgs.getPages();
        for (int i = 2; i <= page; i++) {
            msgs.forEach(item -> {
                deleteByFlowHistoryId(item.getHistroryId());
            });
            PageHelper.startPage(i, 100, "po00302 asc");
            msgs = (Page<FlowMessage>) messageService.getOverMessage(startDatetime, endDatetime);
        }
    }

    @Override
    public int deleteHistoryByFlowId(String histroryId) {
        return coursePersonAttachedMapper.deleteHistory(histroryId);
    }

    @Override
    public int deleteByFlowHistoryId(String histroryId, boolean insertHistory) {
        if (insertHistory) {
            return deleteByFlowHistoryId(histroryId);
        } else {
            return coursePersonAttachedMapper.deleteByFlowHistoryId(histroryId);
        }
    }
}
