package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Course;

import java.util.List;

/**
 * Created by 96339 on 2017/5/22 科目服务.
 */
public interface CourseService {
    /**
     * 添加科目
     * @param course
     */
    void insert(Course course);

    /**
     * 查询子科目
     * @param parentId
     * @return
     */
    List<Course> queryByParent(String parentId);

    /**
     * 查询所有父科目
     * @return
     */
    List<Course> queryRoot();

    /**
     * 查询科目
     * @param id 科目id
     * @return
     */
    Course queryById(String id);

    /**
     * 更新科目
     * @param course 科目对象
     */
    void update(Course course);

    /**
     * 获取所有科目
     * @return
     */
    List<Course> queryAll();

    Course queryByName(String name);

}
