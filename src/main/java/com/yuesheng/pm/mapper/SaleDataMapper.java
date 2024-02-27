package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SaleData;
import com.yuesheng.pm.model.DateGroupModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * (SaleData)表数据库访问层
 *
 * @author xiaosong
 * @since 2023-06-26 15:31:13
 */
@Mapper
public interface SaleDataMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SaleData queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param saleData 查询条件
     * @return 对象列表
     */
    List<SaleData> queryAllByLimit(SaleData saleData);

    /**
     * 统计总行数
     *
     * @param saleData 查询条件
     * @return 总行数
     */
    long count(SaleData saleData);

    /**
     * 新增数据
     *
     * @param saleData 实例对象
     * @return 影响行数
     */
    int insert(SaleData saleData);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SaleData> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SaleData> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SaleData> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SaleData> entities);

    /**
     * 修改数据
     *
     * @param saleData 实例对象
     * @return 影响行数
     */
    int update(SaleData saleData);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 查询销售数据
     *
     * @param query
     * @return
     */
    List<SaleData> queryAll(HashMap<String, Object> query);

    /**
     * 查询销售金额合计
     *
     * @param query
     * @return
     */
    Double queryMoney(HashMap<String, Object> query);

    /**
     * 通过日期分组统计销售金额
     * * @param param {saleStartDate:开始日期,saleEndDate:截止日期}
     *
     * @return
     */
    List<DateGroupModel> queryMoneyGroupSaleDate(HashMap<String, Object> param);

    /**
     * 按天和品牌统计数据
     * @param saleStartDate 开始日期
     * @param saleEndDate 截止日期
     * @return
     */
    List<DateGroupModel> queryByDayTopList(@Param("saleStartDate")String saleStartDate,
                                           @Param("saleEndDate") String saleEndDate);

    /**
     * 按品牌统计数据
     * @param saleStartDate 开始日期
     * @param saleEndDate 截止日期
     * @return
     */
    List<DateGroupModel> queryByDateTopList(@Param("saleStartDate")String saleStartDate,
                                           @Param("saleEndDate") String saleEndDate);
}

