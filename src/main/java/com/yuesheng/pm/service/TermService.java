package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Term;

import java.util.List;

/**
 * (Term)表服务接口
 *
 * @author xiaosong
 * @since 2024-02-29 15:33:29
 */
public interface TermService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Term queryById(String id);

    /**
     * 分页查询
     *
     * @param term 筛选条件
     * @return 查询结果
     */
    List<Term> queryByPage(Term term);

    /**
     * 新增数据
     *
     * @param term 实例对象
     * @return 实例对象
     */
    Term insert(Term term);

    /**
     * 修改数据
     *
     * @param term 实例对象
     * @return 实例对象
     */
    Term update(Term term);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询有效期条款并生成账单
     */
    void genAllTerm();

    /**
     * 根据合同id删除条款
     * @param concatId
     * @return
     */
    int deleteByConcat(String concatId);
}
