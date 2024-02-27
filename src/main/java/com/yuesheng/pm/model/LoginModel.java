package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Staff;

import java.util.List;

public class LoginModel extends BaseEntity {

    private Staff staff;

    private List<Menu> menus;

    public static LoginModel success(Staff staff, List<Menu> menus){
        return new LoginModel(staff, menus);
    }

    public LoginModel(Staff staff, List<Menu> menus) {
        this.staff = staff;
        this.menus = menus;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
