package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Course;
import com.yuesheng.pm.mapper.CourseMapper;
import com.yuesheng.pm.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by 96339 on 2017/5/22.
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void insert(Course course) {
        Course c = queryByName(course.getName());
        if (Objects.isNull(c)) {
            courseMapper.insert(course);
        } else {
            course.setId(c.getId());
        }
    }

    @Override
    public List<Course> queryByParent(String parentId) {
        return courseMapper.queryByParent(parentId);
    }

    @Override
    public List<Course> queryRoot() {
        return courseMapper.queryRoot();
    }

    @Override
    public Course queryById(String id) {
        return courseMapper.queryById(id);
    }

    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }

    @Override
    public List<Course> queryAll() {
        return courseMapper.queryAll();
    }

    @Override
    public Course queryByName(String name) {
        return courseMapper.queryByName(name);
    }
}
