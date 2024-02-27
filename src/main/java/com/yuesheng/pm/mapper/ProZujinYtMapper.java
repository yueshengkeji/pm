package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProZujinYt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProZujinYt)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Mapper
public interface ProZujinYtMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujinYt queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujinYt> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proZujinYt 实例对象
     * @return 对象列表
     */
    List<ProZujinYt> queryAll(ProZujinYt proZujinYt);

    /**
     * 新增数据
     *
     * @param proZujinYt 实例对象
     * @return 影响行数
     */
    int insert(ProZujinYt proZujinYt);

    /**
     * 修改数据
     *
     * @param proZujinYt 实例对象
     * @return 影响行数
     */
    int update(ProZujinYt proZujinYt);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}