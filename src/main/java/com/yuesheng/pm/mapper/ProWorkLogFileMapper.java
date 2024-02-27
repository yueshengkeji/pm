package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProWorkLogFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作日记附件关系表(ProWorkLogFile)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-07-03 10:28:17
 */
@Mapper
public interface ProWorkLogFileMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<ProWorkLogFile> queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProWorkLogFile> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proWorkLogFile 实例对象
     * @return 对象列表
     */
    List<ProWorkLogFile> queryAll(ProWorkLogFile proWorkLogFile);

    /**
     * 新增数据
     *
     * @param proWorkLogFile 实例对象
     * @return 影响行数
     */
    int insert(ProWorkLogFile proWorkLogFile);

    /**
     * 修改数据
     *
     * @param proWorkLogFile 实例对象
     * @return 影响行数
     */
    int update(ProWorkLogFile proWorkLogFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}