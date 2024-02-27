package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.OutCarExpenseDetail;
import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.model.OutCarExpenseCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * (OutCarExpenseDetail)表数据库访问层
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:10
 */
@Mapper
public interface OutCarExpenseDetailMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OutCarExpenseDetail queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param outCarExpenseDetail 查询条件
     * @return 对象列表
     */
    List<OutCarExpenseDetail> queryAllByLimit(OutCarExpenseDetail outCarExpenseDetail);

    /**
     * 统计总行数
     *
     * @param outCarExpenseDetail 查询条件
     * @return 总行数
     */
    long count(OutCarExpenseDetail outCarExpenseDetail);

    /**
     * 新增数据
     *
     * @param outCarExpenseDetail 实例对象
     * @return 影响行数
     */
    int insert(OutCarExpenseDetail outCarExpenseDetail);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutCarExpenseDetail> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OutCarExpenseDetail> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutCarExpenseDetail> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OutCarExpenseDetail> entities);

    /**
     * 修改数据
     *
     * @param outCarExpenseDetail 实例对象
     * @return 影响行数
     */
    int update(OutCarExpenseDetail outCarExpenseDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    int deleteByExpense(String expenseId);

    /**
     * 查询项目费用里程合计
     * @param projectId 项目id
     * @return
     */
    OutCarExpenseCount queryMoneyByProject(@Param("projectId") String projectId);

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

