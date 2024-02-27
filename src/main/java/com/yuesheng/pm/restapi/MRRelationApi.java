package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.MenuService;
import com.yuesheng.pm.service.RoleService;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "菜单与角色关系接口")
@RestController
@RequestMapping("/api/menu-role")
public class MRRelationApi {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Operation(description = "给菜单设定角色")
    @PutMapping
    public ResponseModel insert(@RequestBody Role role, @Parameter(name="菜单id") String menuId) {
        menuService.insertRoleToMenu(role, menuId);
        //追加角色到系统菜单权限参数中
        WebParam.insertRole(role, menuService.getMenuById(menuId));
        return new ResponseModel(role);
    }

    @Operation(description = "获取菜单中可操作的角色集合")
    @GetMapping
    public ResponseModel get(@Parameter(name="菜单id") String menuId) {
        return new ResponseModel(roleService.getRoleByMenu(menuId));
    }

    @Operation(description = "从菜单中删除角色权限")
    @DeleteMapping
    public ResponseModel delete(@Parameter(name="获取菜单角色集合返回的角色id值") String id) {
        Map<String, String> role = menuService.getRelationRole(id);
        Role r = roleService.getRoleByCoding(role.get("coding"));
        Menu m = menuService.getMenuById(role.get("menu_id"));
        menuService.deleteRoleToMenu(id);
        //删除权限缓存
        WebParam.deleteRole(r, m);
        return new ResponseModel(id);
    }
}
