package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.EquipmentToRepair;

import java.util.List;
import java.util.Map;

/**
 * @ClassName EquipmentToRepairService
 * @Description
 * @Author ssk
 * @Date 2022/10/27 0027 16:54
 */
public interface EquipmentToRepairService {
    /**
     * 新增送修
     * @param equipmentToRepair
     * @return
     */
    int insert(EquipmentToRepair equipmentToRepair);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 修改
     * @param equipmentToRepair
     * @return
     */
    int update(EquipmentToRepair equipmentToRepair);

    /**
     * 更新送修状态
     * @param id
     * @param state
     * @return
     */
    int updateState(String id, Integer state);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    EquipmentToRepair selectById(String id);

    /**
     * 送修集合
     * @param params
     * @return
     */
    List<EquipmentToRepair> list(Map<String,Object> params);

    /**
     * 结果确认
     * @param id
     * @param notifyFlag
     * @return
     */
    int updateNotify(String id,Integer notifyFlag);

    /**
     * 更新结果确认 3 天
     */
    void updateNotifyTiming();
}
