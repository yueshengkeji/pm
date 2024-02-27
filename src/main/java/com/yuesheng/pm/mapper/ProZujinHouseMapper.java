package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProZujinHouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * (ProZujinHouse)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Mapper
public interface ProZujinHouseMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujinHouse queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujinHouse> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proZujinHouse 实例对象
     * @return 对象列表
     */
    List<ProZujinHouse> queryAll(ProZujinHouse proZujinHouse);

    /**
     * 新增数据
     *
     * @param proZujinHouse 实例对象
     * @return 影响行数
     */
    int insert(ProZujinHouse proZujinHouse);

    /**
     * 修改数据
     *
     * @param proZujinHouse 实例对象
     * @return 影响行数
     */
    int update(ProZujinHouse proZujinHouse);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<ProZujinHouse> queryByParam(HashMap<String, Object> params);

    Integer queryCountByParam(HashMap<String, Object> params);

    List<String> queryFloor();
}