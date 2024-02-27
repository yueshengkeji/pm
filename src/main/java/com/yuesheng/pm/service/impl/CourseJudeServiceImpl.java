package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.CourseJudge;
import com.yuesheng.pm.mapper.CourseJudgeMapper;
import com.yuesheng.pm.service.CourseJudeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by 96339 on 2017/3/16.
 */
@Service("courseJudeService")
public class CourseJudeServiceImpl implements CourseJudeService {
    @Autowired
    private CourseJudgeMapper courseJudgeMapper;

    @Override
    public void addCourseJudgeForMsg(CourseJudge judge) {
        courseJudgeMapper.addCourseJudgeForMsg(judge);
    }

    @Override
    public List<CourseJudge> getByCourse(String courseId) {
        return courseJudgeMapper.getByCourse(courseId);
    }

    @Override
    public int insert(CourseJudge judge) {

        if (StringUtils.isBlank(judge.getValue())) {
            judge.setValue("");
        }

        judge.setValue2(judge.getValue());

        return courseJudgeMapper.insert(judge);
    }

    @Override
    public int update(CourseJudge judge) {

        if (StringUtils.isBlank(judge.getValue())) {
            judge.setValue("");
        }

        judge.setValue2(judge.getValue());

        if (StringUtils.isBlank(judge.getId()) || StringUtils.equals("-1", judge.getId())) {
            judge.setId(UUID.randomUUID().toString());
            return courseJudgeMapper.insert(judge);

        } else {

            return courseJudgeMapper.update(judge);
        }
    }

    @Override
    public List<CourseJudge> getJudgeForMsg(String courseId) {
        return courseJudgeMapper.getJudgeForMsg(courseId);
    }

    @Override
    public int deleteByCourse(String courseId) {
        return courseJudgeMapper.deleteByCourse(courseId);
    }

    @Override
    public int deleteById(String id) {
        return courseJudgeMapper.deleteById(id);
    }
}
