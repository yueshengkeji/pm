package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProStaffHw;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 汉王考勤机用户信息(ProStaffHw)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-06 10:19:45
 */
@Mapper
public interface ProStaffHwMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProStaffHw queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProStaffHw> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proStaffHw 实例对象
     * @return 对象列表
     */
    List<ProStaffHw> queryAll(ProStaffHw proStaffHw);

    /**
     * 新增数据
     *
     * @param proStaffHw 实例对象
     * @return 影响行数
     */
    int insert(ProStaffHw proStaffHw);

    /**
     * 修改数据
     *
     * @param proStaffHw 实例对象
     * @return 影响行数
     */
    int update(ProStaffHw proStaffHw);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 获取员工头像数据（base64字符串）
     *
     * @param staffId 员工id
     * @return
     */
    String queryHeadByStaffId(@Param("staffId") String staffId);

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 员工主键
     * @return 实例对象
     */
    ProStaffHw queryByIdV2(String staffId);

    /**
     * 查询职员是否绑定考勤机设备
     *
     * @param staffId 职员id
     * @return
     */
    ProStaffHw queryByStaffId(@Param("staffId") String staffId);

    /**
     * 查询职员是否绑定考勤机列表
     *
     * @param staffId 职员id
     * @return
     */
    List<ProStaffHw> queryListByStaffId(String staffId);
}