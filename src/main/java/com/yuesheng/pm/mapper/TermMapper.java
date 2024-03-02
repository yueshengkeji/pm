package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Term;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

}

