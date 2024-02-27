package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.OutCarExpense;

import java.util.List;
import java.util.Map;

/**
 * (OutCarExpense)表服务接口
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:10
 */
public interface OutCarExpenseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutCarExpense queryById(String id);

    /**
     * 分页查询
     *
     * @param outCarExpense 筛选条件
     * @return 查询结果
     */
    List<OutCarExpense> queryByPage(OutCarExpense outCarExpense);

    /**
     * 新增数据
     *
     * @param outCarExpense 实例对象
     * @return 实例对象
     */
    OutCarExpense insert(OutCarExpense outCarExpense);

    /**
     * 修改数据
     *
     * @param outCarExpense 实例对象
     * @return 实例对象
     */
    OutCarExpense update(OutCarExpense outCarExpense);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询
     * @param carExpense
     * @return
     */
    Map<String, String> queryMoneyCount(OutCarExpense carExpense);

    /**
     * 查询项目用车成本（实际报销记录）
     * @param projectId 项目id
     * @return
     */
    Double queryInputMoneyByProject(String projectId);

    /**
     * 查询项目用车成本（系统自动记录）
     * @param projectId
     * @return
     */
    Double querySystemMoneyByProject(String projectId);
}
