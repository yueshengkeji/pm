package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Declare;
import com.yuesheng.pm.entity.DeclareDetail;
import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.model.CourseModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/26 报销单服务.
 */
public interface DeclareService {
    /**
     * 获取报销单对象
     *
     * @param id
     * @return
     */
    Declare getById(String id);

    /**
     * 获取报销单+报销单明细集合
     *
     * @param id 报销单id
     * @return
     */
    Declare getDetailById(String id);

    /**
     * 获取报销单集合
     *
     * @param projectId 项目id
     * @return
     */
    List<Declare> getByProId(String projectId);

    /**
     * 获取报销单
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    List<Declare> getByData(String start, String end);

    /**
     * 获取名袭击和
     *
     * @param course 科目id
     * @return
     */
    List<DeclareDetail> getByCourse(String course, String start, String end);

    /**
     * * @param pageNumber 页码
     *
     * @param params{ page 分页对象
     *                order:排序
     *                staffId 是否查询指定的职员
     *                searchText:检索文本
     *                }
     * @return
     */
    List<Declare> queryByParams(Map<String, Object> params);

    /**
     * 获取总页数
     * @param params 参见queryByParams()
     * @return
     */
    int queryByParamsCount(Map<String, Object> params);

    /**
     * 添加报销单
     * @param declare1
     */
    void insert(Declare declare1);

    /**
     * 删除报销单
     * @param id 报销单id
     */
    void delete(String id);

    /**
     * 获取报销金额总和，通过项目分组
     *
     * @param year       查询年，null查询所有
     * @return
     */
    Map<Project, Double> getMoneyByProject(@Param("year") String year, Integer pageNum,Integer pageSize);

    /**
     * 查询报销金额总和
     *
     * @param year 指定时间，null等于查询所有总和
     * @return
     */
    Map<String, Object> getMoneyByDate(@Param("year") String year);


    List<CourseModel> getMoneyByCourse(String start, String end, Integer size, Integer number, String courseId);
}
