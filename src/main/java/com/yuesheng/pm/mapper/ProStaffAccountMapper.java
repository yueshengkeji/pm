package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProStaffAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工钱包(ProStaffBalance)表数据库访问层
 *
 * @author xiaoSong
 * @since 2022-05-20 11:16:49
 */
@Mapper
public interface ProStaffAccountMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProStaffAccount queryById(@Param("id") String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProStaffAccount> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proStaffAccount 实例对象
     * @return 对象列表
     */
    List<ProStaffAccount> queryAll(ProStaffAccount proStaffAccount);

    /**
     * 新增数据
     *
     * @param proStaffAccount 实例对象
     * @return 影响行数
     */
    int insert(ProStaffAccount proStaffAccount);

    /**
     * 修改数据
     *
     * @param proStaffAccount 实例对象
     * @return 影响行数
     */
    int update(ProStaffAccount proStaffAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(@Param("id") String id);
    /**
     * 查找卡余额信息
     * @param staffId 员工id
     * @return
     */
    ProStaffAccount queryByStaffId(@Param("staffId") String staffId);

    /**
     * 查询钱包数据（锁定行）
     * @param staffId 员工主键
     * @return
     */
    ProStaffAccount queryByIdForUpdate(@Param("staffId") String staffId);
}