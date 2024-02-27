package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProMaterialHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目耗材记录表(ProMaterialHistory)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-06-18 15:02:14
 */
@Mapper
public interface ProMaterialHistoryMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProMaterialHistory queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProMaterialHistory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proMaterialHistory 实例对象
     * @return 对象列表
     */
    List<ProMaterialHistory> queryAll(ProMaterialHistory proMaterialHistory);

    /**
     * 新增数据
     *
     * @param proMaterialHistory 实例对象
     * @return 影响行数
     */
    int insert(ProMaterialHistory proMaterialHistory);

    /**
     * 修改数据
     *
     * @param proMaterialHistory 实例对象
     * @return 影响行数
     */
    int update(ProMaterialHistory proMaterialHistory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}