package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.ApplyMaterial;
import com.yuesheng.pm.entity.City;

import java.util.List;

/**
 * Created by xiaoSong on 2016/8/29.
 * 申请单材料model，支持spring-mvc集合映
 * @author XiaoSong
 * @date 2016/08/29
 */
public class AppMaterModel {
    /**
     * 收货地址
     */
    private City city;
    /**
     * 采购材料
     */
    private List<ApplyMaterial> material;
    /**
     * 添加后是否自动关闭窗口（前端需要）
     */
    private boolean autoClose;
    /**
     * 项目名称
     */
    private String projectName;

    public List<ApplyMaterial> getMaterial() {
        return material;
    }

    public void setMaterial(List<ApplyMaterial> material) {
        this.material = material;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public boolean getAutoClose() {
        return autoClose;
    }

    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
