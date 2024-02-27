package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.entity.Staff;

import java.util.List;

/**
 * Created by Administrator on 2016-08-20 角色服务.
 *
 */
public interface RoleService {
    /**
     * 根据角色编码获取人员集合
     * @param coding 角色编码
     * @return 职员集合
     */
    List<Staff> getStaffByRoleCoding(String coding);

    /**
     * 获取角色集合
     * @param str 检索字符串
     * @return
     */
    List<Role> getRoles(String str);

    /**
     * 添加角色
     * @param role 角色对象
     */
    void addRole(Role role);

    /**
     * 获取角色
     * @param coding 角色编码
     * @return
     */
    Role getRoleByCoding(String coding);

    /**
     * 添加职员到角色中
     * @param coding 角色编码
     * @param s 职员对象
     */
    boolean insert(String coding, Staff s);

    /**
     * 从角色中删除人员
     * @param staffId 人员id
     * @param roleId 角色id
     */
    void deletePerson(String staffId, String roleId);

    /**
     * 删除角色&角色中人员集合
     * @param roleId
     */
    void delete(String roleId);

    /**
     * 获取角色集合
     * @param menuId 菜单id
     * @return
     */
    List<Role> getRoleByMenu(String menuId);

    /**
     * 获取角色集合
     *
     * @param staffId 职员id
     * @return
     */
    Role[] getRolesByStaff(String staffId);

    /**
     * 删除该职员所有角色
     *
     * @param staffId 职员id
     * @return
     */
    int deletePersonAll(String staffId);

    /**
     * 是否绑定
     *
     * @param coding  角色编码
     * @param staffId 员工id
     * @return
     */
    boolean isBind(String coding, String staffId);

    /**
     * 修改角色
     * @param role
     * @return
     */
    int update(Role role);
}
