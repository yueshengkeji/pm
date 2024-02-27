package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.RoleMapper;
import com.yuesheng.pm.service.RoleService;
import com.yuesheng.pm.util.AESEncrypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author XiaoSong
 * @date 2016-08-20 角色服务实现
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Staff> getStaffByRoleCoding(String coding) {
        return roleMapper.getStaffListByRoleCoding(coding);
    }

    @Override
    public List<Role> getRoles(String str) {
        return roleMapper.getRoles(str);
    }

    @Override
    public void addRole(Role role) {
        List<Role> temp = getRoles(role.getName());
        for (Role r : temp) {
            if (r.getName().equals(role.getName())) {
                LogManager.getFormatterLogger(RoleServiceImpl.class).error("该角色已存在");
                return;
            }
        }
        if (StringUtils.isBlank(role.getRemark())) {
            role.setRemark("");
        }
        while (true) {
            role.setCoding(AESEncrypt.getRandom8Id());
            if (getRoleByCoding(role.getCoding()) == null) {
                roleMapper.addRole(role);
                return;
            }
        }
    }

    @Override
    public Role getRoleByCoding(String coding) {
        return roleMapper.getRoleByCoding(coding);
    }

    @Override
    public boolean insert(String coding, Staff s) {
        try {
            roleMapper.insert(coding, s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void deletePerson(String staffId, String roleId) {
        roleMapper.deletePerson(staffId, roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String roleId) {
        roleMapper.deletePersonAll(roleId);
        roleMapper.delete(roleId);
    }

    @Override
    public List<Role> getRoleByMenu(String menuId) {
        return roleMapper.getRoleByMenu(menuId);
    }

    @Override
    public Role[] getRolesByStaff(String staffId) {
        return roleMapper.getRolesByStaff(staffId);
    }

    @Override
    public int deletePersonAll(String staffId) {
        return roleMapper.deletePersonAll(staffId);
    }

    @Override
    public boolean isBind(String coding, String staffId) {
        coding = roleMapper.isBind(coding, staffId);
        return !Objects.isNull(coding);
    }

    @Override
    public int update(Role role) {
        return roleMapper.update(role);
    }
}
