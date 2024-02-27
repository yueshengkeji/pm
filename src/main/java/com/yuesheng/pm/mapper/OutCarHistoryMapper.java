package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.model.OutCarHistoryGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (OutCarHistory)表数据库访问层
 *
 * @author xiaosong
 * @since 2023-03-29 14:54:12
 */
@Mapper
public interface OutCarHistoryMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutCarHistory queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param outCarHistory 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<OutCarHistory> queryAllByLimit(OutCarHistory outCarHistory, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param outCarHistory 查询条件
     * @return 总行数
     */
    long count(OutCarHistory outCarHistory);

    /**
     * 新增数据
     *
     * @param outCarHistory 实例对象
     * @return 影响行数
     */
    int insert(OutCarHistory outCarHistory);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutCarHistory> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OutCarHistory> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutCarHistory> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OutCarHistory> entities);

    /**
     * 修改数据
     *
     * @param outCarHistory 实例对象
     * @return 影响行数
     */
    int update(OutCarHistory outCarHistory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 查询所有记录
     * @param q
     * @return
     */
    List<OutCarHistory> queryAll(OutCarHistory q);

    /**
     * 通过项目分组查询
     * @param q
     * @return
     */
    List<OutCarHistoryGroup> queryGroupProject(OutCarHistory q);

    /**
     * 查询空项目记录列表
     * @param q
     * @return
     */
    List<OutCarHistory> queryByNoProject(OutCarHistory q);
}

