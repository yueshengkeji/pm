package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 采购订单报表记录(ProReport)表数据库访问层
 *
 * @author xiaoSong
 * @since 2021-06-24 09:44:25
 */
@Mapper
public interface ProReportMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProReport queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProReport> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param param 实例对象
     * @return 对象列表
     */
    List<ProReport> queryAll(Map<String, Object> param);

    /**
     * 新增数据
     *
     * @param proReport 实例对象
     * @return 影响行数
     */
    int insert(ProReport proReport);

    /**
     * 修改数据
     *
     * @param proReport 实例对象
     * @return 影响行数
     */
    int update(ProReport proReport);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过主键删除数据
     *
     * @param proId 主键
     * @return 影响行数
     */
    int deleteByProId(String proId);

    /**
     * 根据条件查询数据总数
     *
     * @param param
     * @return
     */
    int queryAllCount(Map<String, Object> param);

    /**
     * 根据采购订单明细行查找报表数据
     *
     * @param proMaterRowId
     * @return
     */
    ProReport queryByMaterId(String proMaterRowId);

    /**
     * 根据订单主体分组过去采购明细数据
     *
     * @param params 查询参数
     * @return
     */
    List<ProReport> queryByProGroup(Map<String, Object> params);
}