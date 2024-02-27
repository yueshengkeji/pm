package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.HouseBillDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (ProHouseBillDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-17 13:11:39
 */
@Mapper
public interface ProHouseBillDetailMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    HouseBillDetail queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<HouseBillDetail> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param houseBillDetail 实例对象
     * @return 对象列表
     */
    List<HouseBillDetail> queryAll(HouseBillDetail houseBillDetail);

    /**
     * 新增数据
     *
     * @param houseBillDetail 实例对象
     * @return 影响行数
     */
    int insert(HouseBillDetail houseBillDetail);

    /**
     * 修改数据
     *
     * @param houseBillDetail 实例对象
     * @return 影响行数
     */
    int update(HouseBillDetail houseBillDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 获取明细集合
     *
     * @param mainId 主单据id
     * @return 数据集合
     */
    List<HouseBillDetail> queryByMain(String mainId);

    /**
     * 查询主单据已收金额合计
     *
     * @param mainId 主单据id
     * @return
     */
    Double queryMoneyByMainId(String mainId);

    /**
     * 作废明细
     *
     * @param id 明细主键
     * @return
     */
    int destroy(String id);

    /**
     * 获取金额合计
     *
     * @param result 指定年份和月份（月份可选）
     * @return
     */
    Double getMoneyByYear(Map<String, Object> result);
}