package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ConcatBill;
import com.yuesheng.pm.entity.Term;

import java.util.HashMap;
import java.util.List;

/**
 * (ConcatBill)表服务接口
 *
 * @author xiaosong
 * @since 2024-02-29 16:16:45
 */
public interface ConcatBillService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ConcatBill queryById(String id);

    /**
     * 分页查询
     *
     * @param concatBill 筛选条件
     * @return 查询结果
     */
    List<ConcatBill> queryByPage(ConcatBill concatBill);

    /**
     * 新增数据
     *
     * @param concatBill 实例对象
     * @return 实例对象
     */
    ConcatBill insert(ConcatBill concatBill);

    /**
     * 修改数据
     *
     * @param concatBill 实例对象
     * @return 实例对象
     */
    ConcatBill update(ConcatBill concatBill);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 根据条款生成账单
     * @param term
     */
    void genByTerm(Term term);

    /**
     * 删除未收款的账单
     * @param sourceId 账单源id
     * @return
     */
    int deleteBySourceId(String sourceId);

    /**
     * 查询合同账单
     * @param param
     * @return
     */
    List<ConcatBill> queryByParam(HashMap<String, String> param);
}
