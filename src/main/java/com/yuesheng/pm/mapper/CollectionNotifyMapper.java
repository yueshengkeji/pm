package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.CollectionNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CollectionNotify)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-03-25 09:45:18
 */
@Mapper
public interface CollectionNotifyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CollectionNotify queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<CollectionNotify> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param collectionNotify 实例对象
     * @return 对象列表
     */
    List<CollectionNotify> queryAll(CollectionNotify collectionNotify);

    /**
     * 新增数据
     *
     * @param collectionNotify 实例对象
     * @return 影响行数
     */
    int insert(CollectionNotify collectionNotify);

    /**
     * 修改数据
     *
     * @param collectionNotify 实例对象
     * @return 影响行数
     */
    int update(CollectionNotify collectionNotify);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}