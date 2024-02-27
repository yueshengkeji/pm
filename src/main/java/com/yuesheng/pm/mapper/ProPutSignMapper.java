package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProPutSign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 入库签字表(ProPutSign)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-05 11:07:57
 */
@Mapper
public interface ProPutSignMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProPutSign queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProPutSign> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proPutSign 实例对象
     * @return 对象列表
     */
    List<ProPutSign> queryAll(ProPutSign proPutSign);

    /**
     * 新增数据
     *
     * @param proPutSign 实例对象
     * @return 影响行数
     */
    int insert(ProPutSign proPutSign);

    /**
     * 修改数据
     *
     * @param proPutSign 实例对象
     * @return 影响行数
     */
    int update(ProPutSign proPutSign);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}