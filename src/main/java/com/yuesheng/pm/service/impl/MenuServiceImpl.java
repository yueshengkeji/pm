package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.mapper.MenuMapper;
import com.yuesheng.pm.service.MenuService;
import com.yuesheng.pm.service.RoleService;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 96339 on 2017/4/28.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public void insert(Menu menu) {
        menu.setId(UUID.randomUUID().toString());
        menuMapper.insert(menu);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.update(menu);
        if (StringUtils.isBlank(menu.getBeanName())) {
            menu.setBeanName("");
        }
        menuMapper.updatePm2Menu(menu);
    }

    @Override
    public int delete(Menu menu) {
        return menuMapper.delete(menu);
    }

    @Override
    public List<Menu> getMenus(Map<String, Object> param) {
        return menuMapper.getMenus(param);
    }

    @Override
    public List<Menu> getRoot(Integer type) {
        return menuMapper.getRoot(type);
    }

    @Override
    public List<Menu> getMenuByParent(String parentId) {
        return menuMapper.getMenuByParent(parentId);
    }

    @Override
    public Menu getMenuById(String id) {
        return menuMapper.getMenuById(id);
    }

    @Override
    public void insertRoleToMenu(Role role, String menuId) {
        if (StringUtils.isBlank(menuId) || StringUtils.isBlank(role.getCoding())) {
            return;
        }
        role.setId(UUID.randomUUID().toString());
        Map<String, Object> param = new HashMap(16);
        param.put("role", role);
        param.put("menuId", menuId);
        menuMapper.insertRoleToMenu(param);
    }

    @Override
    public int deleteRoleToMenu(String id) {
        Map<String, String> role = getRelationRole(id);
        Role r = roleService.getRoleByCoding(role.get("coding"));
        Menu m = getMenuById(role.get("menu_id"));
        if (Objects.isNull(r)) {
            r = new Role();
            r.setCoding(role.get("coding"));
        }
        //删除权限缓存
        WebParam.deleteRole(r, m);
        return menuMapper.deleteRoleToMenu(id);
    }

    @Override
    public Menu[] getMenuByRole(String roleId) {
        return menuMapper.getMenuByRole(roleId);
    }

    @Override
    public Map<String, String> getRelationRole(String major) {
        return menuMapper.getRelationRole(major);
    }

    @Override
    public List<Menu> getPm2Menu(String parent) {
        return menuMapper.getPm2MenuByParent(parent);
    }

    @Override
    public List<Menu> seekPmMenu(String str) {
        return menuMapper.seekPmMenu(str);
    }

    @Override
    public List<Menu> getRootByRole(Integer type, String coding) {
        return menuMapper.getRootByRole(type, coding);
    }

    @Override
    public List<Menu> getMenuByParentAndRole(String parentId, String coding) {
        return menuMapper.getMenuByParentAndRole(parentId, coding);
    }

    @Override
    public Menu getMenuByCoding(String frameCoding) {
        if (StringUtils.isNotBlank(frameCoding)) {
            PageHelper.startPage(1,1);
            return menuMapper.getMenuByCoding(frameCoding);
        }

        return null;
    }

    @Override
    public Menu getProMenuByCoding(String frameCoding) {
        return menuMapper.getProMenuByCoding(frameCoding);
    }
}
