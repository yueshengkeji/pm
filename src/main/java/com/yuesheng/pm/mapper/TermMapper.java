package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Term;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (Term)表数据库访问层
 *
 * @author xiaosong
 * @since 2024-02-29 15:33:29
 */
@Mapper
public interface TermMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Term queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param term 查询条件
     * @return 对象列表
     */
    List<Term> queryAllByLimit(Term term);

    /**
     * 统计总行数
     *
     * @param term 查询条件
     * @return 总行数
     */
    long count(Term term);

    /**
     * 新增数据
     *
     * @param term 实例对象
     * @return 影响行数
     */
    int insert(Term term);

    /**
     * 修改数据
     *
     * @param term 实例对象
     * @return 影响行数
     */
    int update(Term term);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);


    /**
     * 查询指定日期范围内，有效的条款信息
     * @param startDate 开始日期
     * @param endDate 截止日期
     * @param payCycle 支付周期：month=月支付周期，quarter=季度支付周期
     * @return
     */
    List<Term> queryByDate(@Param("startDate") Date startDate,
                           @Param("endDate") Date endDate,
                           @Param("type")String payCycle);

    /**
     * 删除条款
     * @param concatId 合同id
     * @return
     */
    int deleteByConcat(@Param("concatId") String concatId);
}

