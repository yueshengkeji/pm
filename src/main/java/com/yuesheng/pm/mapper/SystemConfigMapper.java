package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置主表(SystemConfig)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-07-07 14:40:20
 */
@Mapper
public interface SystemConfigMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SystemConfig queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SystemConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param systemConfig 实例对象
     * @return 对象列表
     */
    List<SystemConfig> queryAll(SystemConfig systemConfig);

    /**
     * 新增数据
     *
     * @param systemConfig 实例对象
     * @return 影响行数
     */
    int insert(SystemConfig systemConfig);

    /**
     * 修改数据
     *
     * @param systemConfig 实例对象
     * @return 影响行数
     */
    int update(SystemConfig systemConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过上级配置删除子配置
     *
     * @param parentId
     * @return
     */
    int deleteByParent(Integer parentId);

}