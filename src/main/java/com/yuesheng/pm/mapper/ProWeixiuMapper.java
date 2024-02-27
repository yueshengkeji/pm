package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProWeixiu;
import com.yuesheng.pm.model.ProWeixiuGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (ProWeixiu)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-08-14 14:15:38
 */
@Mapper
public interface ProWeixiuMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProWeixiu queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWeixiu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proWeixiu 实例对象
     * @return 对象列表
     */
    List<ProWeixiu> queryAll(ProWeixiu proWeixiu);

    /**
     * 新增数据
     *
     * @param proWeixiu 实例对象
     * @return 影响行数
     */
    int insert(ProWeixiu proWeixiu);

    /**
     * 修改数据
     *
     * @param proWeixiu 实例对象
     * @return 影响行数
     */
    int update(ProWeixiu proWeixiu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 通过参数查询报修记录
     *
     * @param result
     * @return
     */
    List<ProWeixiu> queryByParam(Map<String, Object> result);

    /**
     * 通过参数查询报修总数
     *
     * @param param
     * @return
     */
    Integer queryCountByParam(Map<String, Object> param);

    /**
     * 通过项目分组查询报修记录
     *
     * @param startDate 数据开始日期
     * @param endDate   数据截止日期
     * @return
     */
    List<ProWeixiuGroup> queryByProjectGroup(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 查询项目报修总数
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    Integer queryWeiXiuCountByDate(@Param("start") String startDate, @Param("end") String endDate);

    /**
     * 查询未反馈的报修记录
     *
     * @return
     */
    List<ProWeixiu> selectNoReturn();

    /**
     * 查询未反馈的报修记录总数
     *
     * @return
     */
    Integer selectNoReturnCount();

}