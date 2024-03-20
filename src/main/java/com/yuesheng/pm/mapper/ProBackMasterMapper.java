package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProBackMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ProBackMaster)表数据库访问层
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
@Mapper
public interface ProBackMasterMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProBackMaster queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param proBackMaster 查询条件
     * @return 对象列表
     */
    List<ProBackMaster> queryAllByLimit(ProBackMaster proBackMaster);

    /**
     * 统计总行数
     *
     * @param proBackMaster 查询条件
     * @return 总行数
     */
    long count(ProBackMaster proBackMaster);

    /**
     * 新增数据
     *
     * @param proBackMaster 实例对象
     * @return 影响行数
     */
    int insert(ProBackMaster proBackMaster);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProBackMaster> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProBackMaster> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProBackMaster> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProBackMaster> entities);

    /**
     * 修改数据
     *
     * @param proBackMaster 实例对象
     * @return 影响行数
     */
    int update(ProBackMaster proBackMaster);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 查询退料单明细
     * @param backId 退库单主键
     * @return
     */
    List<ProBackMaster> queryByBack(String backId);

    /**
     * 查询退库总数
     * @param proRowId 采购订单行id
     * @return
     */
    Double queryBackSum(@Param("proRowId") String proRowId);

}

