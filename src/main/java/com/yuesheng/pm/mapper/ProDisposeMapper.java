package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProDispose;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 处置单主表(ProDispose)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-28 10:28:56
 */
@Mapper
public interface ProDisposeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProDispose queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProDispose> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proDispose 实例对象
     * @return 对象列表
     */
    List<ProDispose> queryAll(ProDispose proDispose);

    /**
     * 新增数据
     *
     * @param proDispose 实例对象
     * @return 影响行数
     */
    int insert(ProDispose proDispose);

    /**
     * 修改数据
     *
     * @param proDispose 实例对象
     * @return 影响行数
     */
    int update(ProDispose proDispose);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 通过指定参数获取处置单集合
     *
     * @param param
     * @return
     */
    List<ProDispose> queryAllByParam(Map<String, Object> param);
}