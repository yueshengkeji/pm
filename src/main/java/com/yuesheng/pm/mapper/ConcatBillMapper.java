package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ConcatBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * (ConcatBill)表数据库访问层
 *
 * @author xiaosong
 * @since 2024-02-29 16:16:45
 */
@Mapper
public interface ConcatBillMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ConcatBill queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param concatBill 查询条件
     * @return 对象列表
     */
    List<ConcatBill> queryAllByLimit(ConcatBill concatBill);

    /**
     * 统计总行数
     *
     * @param concatBill 查询条件
     * @return 总行数
     */
    long count(ConcatBill concatBill);

    /**
     * 新增数据
     *
     * @param concatBill 实例对象
     * @return 影响行数
     */
    int insert(ConcatBill concatBill);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ConcatBill> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ConcatBill> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ConcatBill> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ConcatBill> entities);

    /**
     * 修改数据
     *
     * @param concatBill 实例对象
     * @return 影响行数
     */
    int update(ConcatBill concatBill);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);
    /**
     * 通过sourceId删除数据
     *
     * @param sourceId sourceId
     * @return 影响行数
     */
    int deleteBySourceId(String sourceId);
    /**
     * 通过sourceId删除数据
     *
     * @param concatId sourceId
     * @return 影响行数
     */
    int deleteByConcatId(String concatId);

    /**
     * 查询合同账单
     * @param param
     * @return
     */
    List<ConcatBill> queryByParam(HashMap<String, String> param);
}

