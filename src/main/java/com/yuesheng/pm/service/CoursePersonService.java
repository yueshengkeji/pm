package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.CoursePerson;
import com.yuesheng.pm.entity.Staff;

import java.util.List;

/**
 * Created by Administrator on 2016-08-20 步骤人员服务.
 */
public interface CoursePersonService {
    /**
     * 根据过程id获取审批人员集合
     *
     * @param courseId 过程id
     * @param staff    职员信息
     * @return 审批人员对象集合
     */
    List<CoursePerson> getPersonByCourseId(String courseId, Staff staff);

    /**
     * 更新步骤人员信息
     * @param cp
     */
    void update(CoursePerson cp);

    /**
     * 添加步骤人员角色到数据库
     * @param cp
     */
    void insert(CoursePerson cp);

    /**
     * 删除审批人员
     * @param id coursePerson 主键
     */
    void delete(String id);

    /**
     * 通过过程主键删除审批人员集合
     * @param courseId
     */
    void deleteByCourse(String courseId);
}
