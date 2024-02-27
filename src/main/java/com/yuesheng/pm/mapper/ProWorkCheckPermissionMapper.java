package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProWorkCheckPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProWorkCheckPermission)表数据库访问层
 *
 * @author xiaoSong
 * @since 2023-03-06 08:51:46
 */
@Mapper
public interface ProWorkCheckPermissionMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    List<ProWorkCheckPermission> queryById(String staffId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkCheckPermission> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proWorkCheckPermission 实例对象
     * @return 对象列表
     */
    List<ProWorkCheckPermission> queryAll(ProWorkCheckPermission proWorkCheckPermission);

    /**
     * 新增数据
     *
     * @param proWorkCheckPermission 实例对象
     * @return 影响行数
     */
    int insert(ProWorkCheckPermission proWorkCheckPermission);

    /**
     * 修改数据
     *
     * @param proWorkCheckPermission 实例对象
     * @return 影响行数
     */
    int update(ProWorkCheckPermission proWorkCheckPermission);

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 影响行数
     */
    int deleteById(String staffId);

}