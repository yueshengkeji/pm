package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/3/6 办文模板对象 sdpo012.
 *
 * @author XiaoSong
 * @date 2017/03/06
 */
public class Module extends BaseEntity {
    /**
     * 办文所在菜单目录      02
     */
    private ModuleMenu menu;
    /**
     * 模板文件名称        03
     */
    private String name;
    /**
     * 模板数据源         04
     */
    private byte[] source;

    public ModuleMenu getMenu() {
        return menu;
    }

    public void setMenu(ModuleMenu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }
}
