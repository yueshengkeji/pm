package com.yuesheng.pm.mapper;


import com.yuesheng.pm.entity.ProPlate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProPlate)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-08-06 15:44:35
 */
@Mapper
public interface ProPlateMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProPlate queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProPlate> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proPlate 实例对象
     * @return 对象列表
     */
    List<ProPlate> queryAll(ProPlate proPlate);

    /**
     * 新增数据
     *
     * @param proPlate 实例对象
     * @return 影响行数
     */
    int insert(ProPlate proPlate);

    /**
     * 修改数据
     *
     * @param proPlate 实例对象
     * @return 影响行数
     */
    int update(ProPlate proPlate);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 影响行数
     */
    int deleteByStaffId(@Param("staffId") String staffId);

    int deleteByPlate(@Param("plate") String plate);
}