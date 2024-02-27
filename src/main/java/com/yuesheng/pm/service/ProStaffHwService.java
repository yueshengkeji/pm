package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProStaffHw;
import com.yuesheng.pm.entity.Staff;

import java.util.List;

/**
 * 汉王考勤机用户信息(ProStaffHw)表服务接口
 *
 * @author makejava
 * @since 2020-05-06 10:19:45
 */
public interface ProStaffHwService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProStaffHw queryById(Integer id);

    /**
     * 通过ID查询单条数据
     *
     * @param staffId 员工主键
     * @return 实例对象
     */
    ProStaffHw queryById(String staffId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProStaffHw> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proStaffHw 实例对象
     * @return 实例对象
     */
    ProStaffHw insert(ProStaffHw proStaffHw);

    /**
     * 修改数据
     *
     * @param proStaffHw 实例对象
     * @return 实例对象
     */
    ProStaffHw update(ProStaffHw proStaffHw);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 获取员工头像数据（base64字符串）
     *
     * @param staffId 员工id
     * @return
     */
    String queryHeadByStaffId(String staffId);

    /**
     * 添加职员到考勤关系表
     *
     * @param s
     */
    void insertRelation(Staff s);

    /**
     * 查询职员是否绑定考勤机
     *
     * @param staffId 职员id
     * @return
     */
    ProStaffHw queryByStaffId(String staffId);

    /**
     * 查询职员是否绑定考勤机列表
     *
     * @param staffId 职员id
     * @return
     */
    List<ProStaffHw> queryListByStaffId(String staffId);
}