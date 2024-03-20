package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProBack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (ProBack)表数据库访问层
 *
 * @author xiaosong
 * @since 2024-03-13 10:23:19
 */
@Mapper
public interface ProBackMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProBack queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param proBack 查询条件
     * @return 对象列表
     */
    List<ProBack> queryAllByLimit(ProBack proBack);

    /**
     * 统计总行数
     *
     * @param proBack 查询条件
     * @return 总行数
     */
    long count(ProBack proBack);

    /**
     * 新增数据
     *
     * @param proBack 实例对象
     * @return 影响行数
     */
    int insert(ProBack proBack);

    /**
     * 修改数据
     *
     * @param proBack 实例对象
     * @return 影响行数
     */
    int update(ProBack proBack);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

