package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.RoleMenuModel;
import com.yuesheng.pm.service.MenuService;
import com.yuesheng.pm.service.RoleService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "菜单管理")
@RequestMapping("/api/menu")
@RestController
public class MenuApi {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Operation(description = "获取菜单集合")
    @GetMapping("root")
    public ResponseModel getRoot(@Parameter(description = "指定菜单类型，0=系统自有视图菜单，1/2=老系统菜单,3=vue前端菜单") Integer type
            , @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        if (staff.getName().equals("1001")) {
            List<Menu> menus = menuService.getRoot(type);
            sortMenus(menus);
            menus.forEach(item->{
                ResponseModel responseModel = getChild(item.getId(),staff);
                if(responseModel.getData() != null){
                    item.setChildren((ArrayList<Menu>) responseModel.getData());
                }

            });
            return new ResponseModel(menus);
        } else {
            ArrayList<Menu> arrayList = new ArrayList<>();
            ArrayList<Menu> menus = null;
            Role[] role = staff.getRole();
            if (!Objects.isNull(role) && role.length > 0) {
                for (int i = 0; i < role.length; i++) {
                    arrayList.addAll(menuService.getRootByRole(type, role[i].getCoding()));
                }
                menus = getMenu(arrayList);
                sortMenus(menus);
                menus.forEach(item->{
                    ResponseModel responseModel = getChild(item.getId(), staff);
                    if (responseModel.getData() != null) {
                        item.setChildren((ArrayList<Menu>) responseModel.getData());
                    }
                });
            }
            return new ResponseModel(menus);
        }

    }

    @Operation(description = "获取菜单集合")
    @GetMapping("root/{type}")
    public ResponseModel getRootV2(@PathVariable String type, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        return getRoot(Integer.valueOf(type), staff);
    }

    private void sortMenus(List<Menu> menus) {
        menus.sort((item, item2) -> {
            if (item == null && item2 == null) {
                return 0;
            }
            if (item == null) {
                return -1;
            }
            if (item2 == null) {
                return 1;
            }
            if (Objects.isNull(item.getSort())) {
                return -1;
            } else if (Objects.isNull(item2.getSort())) {
                return 1;
            } else {
                return item.getSort() > item2.getSort() ? 1 : -1;
            }
        });
    }

    @Operation(description = "获取当前登录用户菜单集合")
    @GetMapping("mymenu")
    public ResponseModel getMenus(@SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        Role[] roles = staff.getRole();
        if (!Objects.isNull(roles) && roles.length > 0) {
            for (int i = 0; i < roles.length; i++) {
                menuArrayList.addAll(Arrays.asList(menuService.getMenuByRole(roles[i].getId())));
            }
        }
        return new ResponseModel(menuArrayList);
    }

    @Operation(description = "通过菜单id获取子菜单集合")
    @GetMapping("child")
    public ResponseModel getChild(@Parameter(name="菜单id") String parentId,
                                  @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        if (staff.getName().equals("1001")) {
            return new ResponseModel(menuService.getMenuByParent(parentId));
        } else {
            ArrayList<Menu> arrayList = new ArrayList();
            ArrayList<Menu> menus = null;
            Role[] roles = staff.getRole();
            if (!Objects.isNull(roles) && roles.length > 0) {
                for (int i = 0; i < roles.length; i++) {
                    arrayList.addAll(menuService.getMenuByParentAndRole(parentId, roles[i].getCoding()));
                }
                menus = getMenu(arrayList);
                sortMenus(menus);
            }
            return new ResponseModel(menus);
        }
    }

    private ArrayList<Menu> getMenu(ArrayList<Menu> arrayList) {
        ArrayList<Menu> menus = new ArrayList<>();
        HashMap<String, Boolean> menuMap = new HashMap<String, Boolean>();
        arrayList.forEach(item -> {
            if (!menuMap.containsKey(item.getId())) {
                menus.add(item);
                menuMap.put(item.getId(), true);
            }
        });
        return menus;
    }

    @Operation(description = "获取菜单中绑定的角色集合")
    @GetMapping("{menuId}")
    public ResponseModel getRoleByMenu(@PathVariable @Parameter(name="菜单id") String menuId) {
        return new ResponseModel(roleService.getRoleByMenu(menuId));
    }

    @Operation(description = "绑定角色到菜单权限")
    @PutMapping("bind")
    public ResponseModel addRoleToMenu(@RequestBody RoleMenuModel model) {
        Role role = new Role();
        role.setCoding(model.getCoding());
        menuService.insertRoleToMenu(role, model.getMenuId());
        return new ResponseModel(200, "操作成功");
    }

    @Operation(description = "删除角色绑定的菜单权限")
    @DeleteMapping("unbind/{id}")
    public ResponseModel deleteRoleToMenu(@PathVariable @Parameter(name="角色与菜单绑定关系主键") String id) {
        return new ResponseModel(menuService.deleteRoleToMenu(id));
    }

    @Operation(description = "添加菜单")
    @PutMapping
    public ResponseModel insert(@RequestBody Menu menu) {
        menuService.insert(menu);
        WebParam.urlMap.put(menu.getUrl(), menu.getId());
        return new ResponseModel(menu);
    }

    @Operation(description = "更新菜单")
    @PostMapping
    public ResponseModel update(@RequestBody Menu menu) {
        menuService.update(menu);
        WebParam.urlMap.put(menu.getUrl(), menu.getId());
        return new ResponseModel(menu);
    }

    @Operation(description = "删除菜单")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable @Parameter(name="菜单id") String id) {
        Menu menu = new Menu();
        menu.setId(id);
        return new ResponseModel(menuService.delete(menu));
    }

    @Operation(description = "查询窗口编号")
    @GetMapping("getMenuCoding")
    public ResponseModel getMenuCode(@Parameter(name="菜单名称") String menuName) {
        List<Menu> menuList = menuService.seekPmMenu(menuName);
        return new ResponseModel(menuList);
    }

    @Operation(description = "通过id获取菜单对象")
    @GetMapping("getById/{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(menuService.getMenuById(id));
    }

    @Operation(description = "搜索pm菜单")
    @GetMapping("searchMenu")
    public ResponseModel searchMenu(String menuName) {
        return new ResponseModel(menuService.seekPmMenu(menuName));
    }
}
