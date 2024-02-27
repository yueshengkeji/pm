package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/4/28 菜单mapper.
 * @author XiaoSong
 * @date 2017/04/28
 */
@Mapper
public interface MenuMapper {
    /**
     * 添加菜单
     *
     * @param menu
     */
    void insert(Menu menu);

    /**
     * 更新菜单
     *
     * @param menu
     */
    void update(Menu menu);

    /**
     * 删除菜单
     *
     * @param menu
     * @return
     */
    int delete(Menu menu);

    /**
     * 根据条件查询菜单集合
     *
     * @param param
     * @return
     */
    List<Menu> getMenus(Map<String, Object> param);

    /**
     * 获取根目录集合
     *
     * @return
     */
    List<Menu> getRoot(@Param("type") Integer type);

    /**
     * 获取子集菜单
     *
     * @param parentId 父菜单集合
     * @return
     */
    List<Menu> getMenuByParent(String parentId);

    /**
     * 获取菜单
     *
     * @param id 主键
     * @return
     */
    Menu getMenuById(String id);

    /**
     * 添加角色到菜单权限中
     *
     * @param param : role 角色对象
     *              menuId 菜单id
     */
    void insertRoleToMenu(Map<String, Object> param);

    /**
     * 从菜单权限中删除角色
     *
     * @param id 关系表主键对象
     * @return
     */
    int deleteRoleToMenu(String id);

    /**
     * 获取菜单集合
     * @param roleId 角色id
     * @return
     */
    Menu[] getMenuByRole(String roleId);

    /**
     * 获取菜单关联角色对象
     * @param major 关系表主键
     * @return
     */
    Map<String,String> getRelationRole(String major);

    /**
     * 获取pm2菜单
     * @param parent 父菜单id
     * @return
     */
    List<Menu> getPm2MenuByParent(String parent);

    /**
     * 获取pm2根菜单集合
     * @return
     */
    List<Menu> getPm2Menu();

    /**
     * 检索pm2系统菜单
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
    List<Menu> getRootByRole(@Param("type") Integer type, @Param("roleId") String coding);

    /**
     * 获取子菜单集合
     *
     * @param parentId 父节点id
     * @param coding   角色编码
     * @return
     */
    List<Menu> getMenuByParentAndRole(@Param("parentId") String parentId, @Param("roleId") String coding);

    /**
     * 获取菜单对象
     *
     * @param frameCoding 窗口编号
     * @return
     */
    Menu getMenuByCoding(String frameCoding);

    /**
     * 更新sdeb003老系统菜单表
     *
     * @param menu
     * @return
     */
    int updatePm2Menu(Menu menu);

    /**
     * 获取新系统菜单
     * @param frameCoding
     * @return
     */
    Menu getProMenuByCoding(String frameCoding);
}
