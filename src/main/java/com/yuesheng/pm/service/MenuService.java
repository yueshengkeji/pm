package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/4/28 菜单服务.
 */
public interface MenuService {
    /**
     * 添加菜单
     * @param menu
     */
    void insert(Menu menu);

    /**
     * 更新菜单
     * @param menu
     */
    void update(Menu menu);

    /**
     * 删除菜单
     * @param menu
     * @return
     */
    int delete(Menu menu);

    /**
     * 根据条件查询菜单集合
     * @param param
     * @return
     */
    List<Menu> getMenus(Map<String,Object> param);

    /**
     * 获取根目录集合
     *
     * @return
     */
    List<Menu> getRoot(Integer type);

    /**
     * 获取子集菜单
     * @param parentId 父菜单集合
     * @return
     */
    List<Menu> getMenuByParent(String parentId);

    /**
     * 获取菜单
     * @param id 主键
     * @return
     */
    Menu getMenuById(String id);

    /**
     * 添加角色到菜单权限中
     * @param role 角色对象
     * @param menuId 菜单id
     */
    void insertRoleToMenu(Role role, String menuId);

    /**
     * 从菜单中删除角色
     * @param id 关系表主键
     * @return
     */
    int deleteRoleToMenu(String id);

    /**
     * 根据角色id获取菜单集合
     * @param roleId 角色id
     * @return
     */
    Menu[] getMenuByRole(String roleId);

    /**
     * 获取角色对象
     * @param major 菜单与角色关系对象
     * @return
     */
    Map<String,String> getRelationRole(String major);

    /**
     * 获取pm2菜单集合
     * @param parent 上级id
     * @return
     */
    List<Menu> getPm2Menu(String parent);

    /**
     * 获取菜单名称
     *
     * @param str 检索串
     * @return
     */
    List<Menu> seekPmMenu(String str);

    /**
     * 获取跟菜单集合
     *
     * @param type   菜单类型
     * @param coding 角色编码
     * @return
     */
    List<Menu> getRootByRole(Integer type, String coding);

    /**
     * 获取子菜单集合
     *
     * @param parentId 父节点id
     * @param coding   角色编码
     * @return
     */
    List<Menu> getMenuByParentAndRole(String parentId, String coding);

    /**
     * 获取老系统菜单对象
     *
     * @param frameCoding 窗口编号
     * @return
     */
    Menu getMenuByCoding(String frameCoding);
    /**
     * 获取新系统菜单对象
     *
     * @param frameCoding 窗口编号
     * @return
     */
    Menu getProMenuByCoding(String frameCoding);
}
