package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Declare;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/26 报销单mapper.
 * @author XiaoSong
 * @date 2016/12/26
 */
@Mapper
public interface DeclareMapper {
    /**
     * 获取报销单对象
     * @param id
     * @return
     */
    Declare getById(@Param("id") String id);

    /**
     * 获取报销单+报销单明细集合
     * @param id 报销单id
     * @return
     */
    Declare getDetailById(@Param("id") String id);

    /**
     * 获取报销单集合
     * @param projectId 项目id
     * @return
     */
    List<Declare> getByProId(@Param("projectId") String projectId);

    /**
     * 获取报销单
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    List<Declare> getByData(@Param("startDate") String start,@Param("endData") String end);

    /**
     * 查询报销单集合
     * @param params 参数参见DeclareService.queryByParams()接口
     * @return
     */
    List<Declare> queryByParams(Map<String, Object> params);

    /**
     * 获取总页数
     * @param params 参见service接口方法
     * @return
     */
    int queryByParamCount(Map<String, Object> params);

    /**
     * 添加报销单
     * @param declare1
     */
    void insert(Declare declare1);

    /**
     * 删除报销单
     * @param id
     */
    void delete(String id);

    /**
     * 获取报销金额总和，通过项目分组
     *
     * @param year       查询年，null查询所有
     * @return
     */
    List<Map<String, Object>> getMoneyByProject(@Param("year") String year);

    /**
     * 查询报销金额总和
     *
     * @param year 指定时间，null等于查询所有总和
     * @return
     */
    Map<String, Object> getMoneyByDate(@Param("year") String year);

}
