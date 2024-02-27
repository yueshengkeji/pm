package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.CoursePerson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-16 审批过程人员mapper.
 * @author XiaoSong
 * @date 2016/08/16
 */
@Mapper
public interface CoursePersonMapper {
    /**
     * 根据过程id获取审批人员集合
     * @param courseId 过程id
     * @return 审批人员对象集合
     */
    List<CoursePerson> getPersonByCourseId(String courseId);

    /**
     * 添加审批人员
     * @param cp
     */
    void insert(CoursePerson cp);

    /**
     * 更新审批人员信息
     * @param cp
     */
    void update(CoursePerson cp);

    /**
     * 删除审批人员信息
     * @param id
     */
    void delete(String id);

    /**
     * 删除审批人员集合
     * @param courseId 过程id
     */
    void deleteByCourse(String courseId);
}
