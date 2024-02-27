package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.StaffCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (StaffCard)表数据库访问层
 *
 * @author xiaosong
 * @since 2024-01-09 13:39:32
 */
@Mapper
public interface StaffCardMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    StaffCard queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param staffCard 查询条件
     * @return 对象列表
     */
    List<StaffCard> queryAllByLimit(StaffCard staffCard);

    /**
     * 统计总行数
     *
     * @param staffCard 查询条件
     * @return 总行数
     */
    long count(StaffCard staffCard);

    /**
     * 新增数据
     *
     * @param staffCard 实例对象
     * @return 影响行数
     */
    int insert(StaffCard staffCard);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffCard> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<StaffCard> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<StaffCard> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<StaffCard> entities);

    /**
     * 修改数据
     *
     * @param staffCard 实例对象
     * @return 影响行数
     */
    int update(StaffCard staffCard);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 查询员工名片
     * @param staffId
     * @return
     */
    List<StaffCard> queryByStaff(@Param("staffId") String staffId);
}

