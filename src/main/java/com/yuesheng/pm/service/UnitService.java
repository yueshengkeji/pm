package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Unit;

import java.util.List;

/**
 * Created by Administrator on 2016-08-12 材料单位服务接口.
 *
 */
public interface UnitService {
    /**
     * 通过单位id获取材料对象
     * @param id 材料id
     * @return 单位对象
     */
    Unit getUnit(String id);

    /**
     * 根据单位名称获取单位对象
     * @param name 单位名称
     * @return
     */
    Unit isExist(String name);

    /**
     * 添加材料单位
     * @param unit 单位对象
     */
    void addUnit(Unit unit);

    /**
     * 获取单位集合
     *
     * @param str 检索串
     * @return
     */
    List<Unit> seek(String str);

    /**
     * 获取材料单位
     *
     * @param materialId 材料id
     * @return
     */
    Unit getUnitByMaterial(String materialId);

    /**
     * 添加材料与单位绑定
     * @param material
     * @return
     */
    int addUnitToMater(Material material);

    /**
     * 删除材料与单位的绑定
     * @param materId 材料id
     * @param id 单位id
     * @return
     */
    int deleteBind(String materId, String id);

    /**
     * 查询材料所有单位列表
     * @param materId 材料id
     * @return
     */
    List<Unit> getUnitAllByMaterial(String materId);
}
