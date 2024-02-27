package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProHouseJson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProHouseJson)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-08-19 10:55:26
 */
@Mapper
public interface ProHouseJsonMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param key 主键
     * @return 实例对象
     */
    ProHouseJson queryById(String key);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProHouseJson> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proHouseJson 实例对象
     * @return 对象列表
     */
    List<ProHouseJson> queryAll(ProHouseJson proHouseJson);

    /**
     * 新增数据
     *
     * @param proHouseJson 实例对象
     * @return 影响行数
     */
    int insert(ProHouseJson proHouseJson);

    /**
     * 修改数据
     *
     * @param proHouseJson 实例对象
     * @return 影响行数
     */
    int update(ProHouseJson proHouseJson);

    /**
     * 通过主键删除数据
     *
     * @param key 主键
     * @return 影响行数
     */
    int deleteById(String key);

}