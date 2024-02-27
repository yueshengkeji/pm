package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/api/role")
public class RoleApi {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisService redisService;

    @Operation(description = "添加角色")
    @PutMapping
    public ResponseModel insert(@Parameter(required = true) @RequestBody Role role) {
        roleService.addRole(role);
        return new ResponseModel(role);
    }

    @Operation(description = "添加指定职员列表到指定角色中")
    @PutMapping("insertStaff")
    public ResponseModel insertStaffToRole(@Parameter(name = "角色coding，staffList属性必须指定", required = true) @RequestBody Role role) {
        if (StringUtils.isBlank(role.getCoding()) || role.getStaffList() == null) {
            return new ResponseModel(401, "请指定角色编码或者职员信息集合");
        } else {
            for (Staff s : role.getStaffList()) {
                boolean isBind = roleService.isBind(role.getCoding(), s.getId());
                if (!isBind) {
                    if (!roleService.insert(role.getCoding(), s)) {
                        continue;
                    }
                }

            }
            return new ResponseModel(200, "添加添加");
        }
    }

    @Operation(description = "从角色中删除职员")
    @DeleteMapping("deleteStaff")
    public ResponseModel deleteStaffByRole(@Parameter(name = "职员id", required = true) String staffId, @Parameter(name = "角色编码", required = true) String coding) {
        if (StringUtils.isBlank(staffId)) {
            return new ResponseModel(401, "请指定职员id");
        } else if (StringUtils.isBlank(coding)) {
            return new ResponseModel(401, "请指定角色编码");
        }
        roleService.deletePerson(staffId, coding);
        return new ResponseModel(200, "删除成功");
    }

    @Operation(description = "获取角色集合")
    @GetMapping
    public ResponseModel getRoles(@Parameter(name="搜索字符串") String str) {
        return new ResponseModel(roleService.getRoles(StringUtils.isBlank(str) ? null : str));
    }

    @Operation(description = "获取指定角色中所有职员列表")
    @GetMapping("/getStaffByRole")
    public ResponseModel getStaffByRole(@Parameter(name="角色id") String roleId) {
        return new ResponseModel(roleService.getStaffByRoleCoding(roleId));
    }

    @Operation(description = "删除角色")
    @DeleteMapping
    public ResponseModel deleteRole(@Parameter(name="角色编码") String coding) {
        roleService.delete(coding);
        return new ResponseModel("删除成功");
    }

    @Operation(description = "获取当前登录职员信息绑定的角色集合")
    @GetMapping("getRolesByToken")
    public ResponseModel getRolesByStaff(@Parameter(name="登录成功返回的的token令牌信息") String token) {
        Staff staff = (Staff) redisService.getValue(token);
        return new ResponseModel(roleService.getRolesByStaff(staff.getId()));
    }
    @Operation(description = "通过角色id获取角色对象")
    @GetMapping("getById/{id}")
    public ResponseModel getById(@PathVariable String id){
        return new ResponseModel(roleService.getRoleByCoding(id));
    }

    @Operation(description = "获取公司所有司机类职员")
    @GetMapping("getDriverStaff")
    public ResponseModel getDriver() {
        List<Staff> staffList = roleService.getStaffByRoleCoding("BCBPNPZB");
        staffList.addAll(roleService.getStaffByRoleCoding("20S9DWBQ"));
        return new ResponseModel(staffList);
    }

    @Operation(description = "修改角色名称")
    @PostMapping("update")
    public ResponseModel update(@RequestBody Role role) {
        roleService.update(role);
        return new ResponseModel(role);
    }

}
