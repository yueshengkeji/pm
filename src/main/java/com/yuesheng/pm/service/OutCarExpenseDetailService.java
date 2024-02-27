package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.OutCarExpenseDetail;
import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.model.OutCarExpenseCount;

import java.util.HashMap;
import java.util.List;

/**
 * (OutCarExpenseDetail)表服务接口
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:11
 */
public interface OutCarExpenseDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutCarExpenseDetail queryById(String id);

    /**
     * 分页查询
     *
     * @param outCarExpenseDetail 筛选条件
     * @return 查询结果
     */
    List<OutCarExpenseDetail> queryByPage(OutCarExpenseDetail outCarExpenseDetail);

    /**
     * 新增数据
     *
     * @param outCarExpenseDetail 实例对象
     * @return 实例对象
     */
    OutCarExpenseDetail insert(OutCarExpenseDetail outCarExpenseDetail);

    /**
     * 修改数据
     *
     * @param outCarExpenseDetail 实例对象
     * @return 实例对象
     */
    OutCarExpenseDetail update(OutCarExpenseDetail outCarExpenseDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 删除报销明细列表
     * @param expenseId 报销单id
     * @return
     */
    boolean deleteByExpense(String expenseId);

    List<OutCarExpenseDetail> insertBatch(List<OutCarExpenseDetail> details,String expenseId);

    /**
     * 查询项目成本
     * @param projectId
     * @return
     */
    OutCarExpenseCount queryMoneyByProject(String projectId);

    /**
     * 查询已审核报销明细
     * @param param {startDate:开始日期，endDate:截止日期,projectName:项目名称，searchText:检索字符串}
     * @return
     */
    List<OutCarHistory> queryByParam(HashMap<String, Object> param);

    /**
     * 查询已审核金额合计
     * @param param {startDate:开始日期，endDate:截止日期,projectName:项目名称，searchText:检索字符串}
     * @return title信息为系统记录里程
     */
    DateGroupModel queryMoney(HashMap<String, Object> param);
}
