package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProZujinHouseR;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProZujinHouseR)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-07-12 09:29:58
 */
@Mapper
public interface ProZujinHouseRMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujinHouseR queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujinHouseR> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proZujinHouseR 实例对象
     * @return 对象列表
     */
    List<ProZujinHouseR> queryAll(ProZujinHouseR proZujinHouseR);

    /**
     * 新增数据
     *
     * @param proZujinHouseR 实例对象
     * @return 影响行数
     */
    int insert(ProZujinHouseR proZujinHouseR);

    /**
     * 修改数据
     *
     * @param proZujinHouseR 实例对象
     * @return 影响行数
     */
    int update(ProZujinHouseR proZujinHouseR);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过租金id删除商铺关系
     *
     * @param zujinId
     * @return
     */
    int deleteByZujinId(@Param("zjId") Integer zujinId);

    /**
     * 更新类型
     * @param houseR
     * @return
     */
    int updateType(ProZujinHouseR houseR);
}