package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.OutCarExpense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (OutCarExpense)表数据库访问层
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:02
 */
@Mapper
public interface OutCarExpenseMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutCarExpense queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param outCarExpense 查询条件
     * @return 对象列表
     */
    List<OutCarExpense> queryAllByLimit(OutCarExpense outCarExpense);

    /**
     * 统计总行数
     *
     * @param outCarExpense 查询条件
     * @return 总行数
     */
    long count(OutCarExpense outCarExpense);

    /**
     * 新增数据
     *
     * @param outCarExpense 实例对象
     * @return 影响行数
     */
    int insert(OutCarExpense outCarExpense);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutCarExpense> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OutCarExpense> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutCarExpense> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OutCarExpense> entities);

    /**
     * 修改数据
     *
     * @param outCarExpense 实例对象
     * @return 影响行数
     */
    int update(OutCarExpense outCarExpense);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    Map<String, String> queryMoneyCount(OutCarExpense carExpense);

    /**
     * 更新单据审核状态
     * @param expense
     * @return
     */
    int updateState(OutCarExpense expense);
}

