package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.CourseJudge;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 96339 on 2017/3/16 步骤判断mapper.
 * @author XiaoSong
 * @date 2017/03/16
 */
@Mapper
public interface CourseJudgeMapper {
    /**
     * 添加流程发起的过程判断对象
     * @param judge
     */
    void addCourseJudgeForMsg(CourseJudge judge);

    /**
     * 获取步骤判断对象集合
     *
     * @param courseId 步骤id
     * @return
     */
    List<CourseJudge> getByCourse(String courseId);

    /**
     * 添加步骤判断条件
     *
     * @param judge
     * @return
     */
    int insert(CourseJudge judge);

    /**
     * 更新步骤判断条件
     *
     * @param judge
     * @return
     */
    int update(CourseJudge judge);

    /**
     * 获取步骤判断条件列表
     *
     * @param courseId 过程id
     * @return
     */
    List<CourseJudge> getJudgeForMsg(@Param("courseId") String courseId);

    /**
     * 删除判断条件
     *
     * @param courseId 过程id
     * @return
     */
    int deleteByCourse(@Param("courseId") String courseId);

    /**
     * 删除判断条件
     *
     * @param id 过程id
     * @return
     */
    int deleteById(@Param("id") String id);

    /**
     * 删除实例判断条件
     * @param historyId 流程记录id
     * @return
     */
    int deleteCourseJudge(@Param("historyId") String historyId);
}
