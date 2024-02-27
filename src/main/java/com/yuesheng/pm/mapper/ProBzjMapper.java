package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProBzj;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProBzj)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-08-31 09:32:20
 */
@Mapper
public interface ProBzjMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProBzj queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProBzj> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proBzj 实例对象
     * @return 对象列表
     */
    List<ProBzj> queryAll(ProBzj proBzj);

    /**
     * 新增数据
     *
     * @param proBzj 实例对象
     * @return 影响行数
     */
    int insert(ProBzj proBzj);

    /**
     * 修改数据
     *
     * @param proBzj 实例对象
     * @return 影响行数
     */
    int update(ProBzj proBzj);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}