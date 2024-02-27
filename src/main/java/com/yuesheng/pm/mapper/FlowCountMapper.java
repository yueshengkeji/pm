package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.FlowCount;
import com.yuesheng.pm.model.DateGroupModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 客流统计(FlowCount)表数据库访问层
 *
 * @author xiaosong
 * @since 2023-08-22 08:47:48
 */
@Mapper
public interface FlowCountMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FlowCount queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param flowCount 查询条件
     * @return 对象列表
     */
    List<FlowCount> queryAllByLimit(FlowCount flowCount);

    /**
     * 统计总行数
     *
     * @param flowCount 查询条件
     * @return 总行数
     */
    long count(FlowCount flowCount);

    /**
     * 新增数据
     *
     * @param flowCount 实例对象
     * @return 影响行数
     */
    int insert(FlowCount flowCount);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<FlowCount> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<FlowCount> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<FlowCount> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<FlowCount> entities);

    /**
     * 修改数据
     *
     * @param flowCount 实例对象
     * @return 影响行数
     */
    int update(FlowCount flowCount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 分组统计客流数据
     * @param param {saleStartDate:开始日期,saleEndDate:截止日期}
     * @return
     */
    List<DateGroupModel> queryMoneyGroupFlowDate(HashMap<String, Object> param);
}

