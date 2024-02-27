package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016-08-20 角色mapper.
 * @author XiaoSong
 * @date 2016/08/20
*/
@Mapper
public interface RoleMapper {
    /**
     * 根据角色编码获取该角色所有职员
     * @param coding 角色编码
     * @return 职员集合
     */
    List<Staff> getStaffListByRoleCoding(@Param("coding") String coding);

    /**
     * 根据角色编码获取角色对象
     * @param coding
     * @return
     */
    Role getRoleByCoding(@Param("coding") String coding);

    /**
     * 获取角色集合
     * @param str 检索的字符串
     * @return
     */
    List<Role> getRoles(@Param("str") String str);

    /**
     * 添加角色
     * @param role 角色对象
     */
    void addRole(Role role);

    /**
     * 添加职员到角色中
     * @param coding 角色编码
     * @param s 职员
     */
    void insert(@Param("coding") String coding,@Param("staff") Staff s);

    /**
     * 从角色中删除人员
     * @param staffId 人员id
     * @param roleId 角色id
     */
    void deletePerson(@Param("staffId") String staffId,@Param("roleId") String roleId);

    /**
     * 删除角色&角色中人员集合
     * @param roleId 角色id
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

    int deletePersonAll(String staffId);

    /**
     * 判定角色和员工是否绑定，绑定返回角色coding,未绑定返回null
     *
     * @param coding
     * @param staffId
     */
    String isBind(@Param("roleId") String coding, @Param("staffId") String staffId);

    /**
     * 修改角色
     * @param role
     * @return
     */
    int update(Role role);
}
