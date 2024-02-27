package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Performance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 绩效考核表(Performance)表数据库访问层
 *
 * @author xiaosong
 * @since 2023-10-17 09:02:38
 */
@Mapper
public interface PerformanceMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Performance queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param performance 查询条件
     * @return 对象列表
     */
    List<Performance> queryAllByLimit(Performance performance);

    /**
     * 统计总行数
     *
     * @param performance 查询条件
     * @return 总行数
     */
    long count(Performance performance);

    /**
     * 新增数据
     *
     * @param performance 实例对象
     * @return 影响行数
     */
    int insert(Performance performance);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Performance> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Performance> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Performance> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Performance> entities);

    /**
     * 修改数据
     *
     * @param performance 实例对象
     * @return 影响行数
     */
    int update(Performance performance);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

