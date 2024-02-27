package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProWeixiuPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProWeixiuPerson)表数据库访问层
 *
 * @author xiaoSong
 * @since 2023-02-08 16:06:30
 */
@Mapper
public interface ProWeixiuPersonMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProWeixiuPerson queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWeixiuPerson> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proWeixiuPerson 实例对象
     * @return 对象列表
     */
    List<ProWeixiuPerson> queryAll(ProWeixiuPerson proWeixiuPerson);

    /**
     * 新增数据
     *
     * @param proWeixiuPerson 实例对象
     * @return 影响行数
     */
    int insert(ProWeixiuPerson proWeixiuPerson);

    /**
     * 修改数据
     *
     * @param proWeixiuPerson 实例对象
     * @return 影响行数
     */
    int update(ProWeixiuPerson proWeixiuPerson);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}