package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProDisposeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 处置单明细(ProDisposeDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-28 10:39:51
 */
@Mapper
public interface ProDisposeDetailMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProDisposeDetail queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProDisposeDetail> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proDisposeDetail 实例对象
     * @return 对象列表
     */
    List<ProDisposeDetail> queryAll(ProDisposeDetail proDisposeDetail);

    /**
     * 新增数据
     *
     * @param proDisposeDetail 实例对象
     * @return 影响行数
     */
    int insert(ProDisposeDetail proDisposeDetail);

    /**
     * 修改数据
     *
     * @param proDisposeDetail 实例对象
     * @return 影响行数
     */
    int update(ProDisposeDetail proDisposeDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}