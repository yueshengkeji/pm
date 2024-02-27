package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProApplyDine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProApplyDine)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-08-15 16:23:56
 */
@Mapper
public interface ProApplyDineMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProApplyDine queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProApplyDine> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proApplyDine 实例对象
     * @return 对象列表
     */
    List<ProApplyDine> queryAll(ProApplyDine proApplyDine);

    /**
     * 新增数据
     *
     * @param proApplyDine 实例对象
     * @return 影响行数
     */
    int insert(ProApplyDine proApplyDine);

    /**
     * 修改数据
     *
     * @param proApplyDine 实例对象
     * @return 影响行数
     */
    int update(ProApplyDine proApplyDine);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}