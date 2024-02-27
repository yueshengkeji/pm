package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProZujin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (ProZujin)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
@Mapper
public interface ProZujinMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujin queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujin> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proZujin 实例对象
     * @return 对象列表
     */
    List<ProZujin> queryAll(ProZujin proZujin);

    /**
     * 新增数据
     *
     * @param proZujin 实例对象
     * @return 影响行数
     */
    int insert(ProZujin proZujin);

    /**
     * 修改数据
     *
     * @param proZujin 实例对象
     * @return 影响行数
     */
    int update(ProZujin proZujin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<ProZujin> queryByParam(HashMap<String, Object> params);

    Integer queryCountByParam(HashMap<String, Object> params);

    /**
     * 更新租金统计信息
     *
     * @param proZujin
     * @return
     */
    int updateMoneyCount(ProZujin proZujin);

    /**
     * 更新保证金统计信息
     *
     * @param proZujin
     * @return
     */
    int updateBzj(ProZujin proZujin);

    /**
     * 查询统计信息
     *
     * @return
     */
    Map<String, Object> queryMoneyTotal(@Param("year") String year);

    /**
     * 查询年度欠费金额
     *
     * @param year
     * @return
     */
    Double queryEarlyMoney(String year);

    Map<String, Object> queryMoneyTotalv2(@Param("year") String year, @Param("type") Integer type);

    /**
     * 查询品牌分组
     * @param searchBrand 品牌名称
     * @return
     */
    List<ProZujin> queryBrand(@Param("searchBrand") String searchBrand);

    /**
     * 查询已过期，未更新过期状态的合同
     * @param endDate 过期时间
     * @return
     */
    List<ProZujin> queryExpire(@Param("endDate") String endDate);

    /**
     * 更新租赁合同类型
     * @param zujin
     * @return
     */
    int updateType(ProZujin zujin);
}