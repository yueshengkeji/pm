package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 96339 on 2017/5/22 项目科目/报销科目mapper.
 * @author XiaoSong
 * @date 2017/05/22
 */
@Mapper
public interface CourseMapper {
    /**
     * 添加科目
     *
     * @param course
     */
    void insert(Course course);

    /**
     * 通过目录id查询科目
     * @param parentId 目录id
     * @return 科目集合
     */
    List<Course> queryByParent(String parentId);

    /**
     * 获取根目录下科目集合
     * @return
     */
    List<Course> queryRoot();

    /**
     * 获取科目信息
     * @param id 科目id
     * @return
     */
    Course queryById(String id);

    /**
     * 更新科目信息
     * @param course 科目对象
     */
    void update(Course course);

    /**
     * 获取所有科目信息
     * @return 科目集合
     */
    List<Course> queryAll();

    Course queryByName(String name);

}
