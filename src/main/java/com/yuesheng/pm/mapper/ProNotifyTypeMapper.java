package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProNotifyType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProNotifyType)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-10-11 15:11:31
 */
@Mapper
public interface ProNotifyTypeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    ProNotifyType queryById(String staffId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProNotifyType> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proNotifyType 实例对象
     * @return 对象列表
     */
    List<ProNotifyType> queryAll(ProNotifyType proNotifyType);

    /**
     * 新增数据
     *
     * @param proNotifyType 实例对象
     * @return 影响行数
     */
    int insert(ProNotifyType proNotifyType);

    /**
     * 修改数据
     *
     * @param proNotifyType 实例对象
     * @return 影响行数
     */
    int update(ProNotifyType proNotifyType);

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 影响行数
     */
    int deleteById(String staffId);

}