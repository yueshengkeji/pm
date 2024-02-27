package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProWorkCheckShow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 可见的考勤人员名单(ProWorkCheckShow)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-11 15:54:14
 */
@Mapper
public interface ProWorkCheckShowMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 主键
     * @return 实例对象
     */
    ProWorkCheckShow queryById(String staffId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkCheckShow> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proWorkCheckShow 实例对象
     * @return 对象列表
     */
    List<ProWorkCheckShow> queryAll(ProWorkCheckShow proWorkCheckShow);

    /**
     * 新增数据
     *
     * @param proWorkCheckShow 实例对象
     * @return 影响行数
     */
    int insert(ProWorkCheckShow proWorkCheckShow);

    /**
     * 修改数据
     *
     * @param proWorkCheckShow 实例对象
     * @return 影响行数
     */
    int update(ProWorkCheckShow proWorkCheckShow);

    /**
     * 通过主键删除数据
     *
     * @param staffId 主键
     * @return 影响行数
     */
    int deleteById(String staffId);

}