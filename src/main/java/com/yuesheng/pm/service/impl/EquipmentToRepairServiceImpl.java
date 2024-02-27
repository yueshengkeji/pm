package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.EquipmentToRepair;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.mapper.EquipmentToRepairMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName EquipmentToRepairServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/10/27 0027 16:55
 */
@Service("equipmentToRepairService")
public class EquipmentToRepairServiceImpl implements EquipmentToRepairService {
    @Autowired
    private EquipmentToRepairMapper equipmentToRepairMapper;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    private SystemConfigService configService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StaffService staffService;

    @Override
    public int insert(EquipmentToRepair equipmentToRepair) {
        equipmentToRepair.setId(UUID.randomUUID().toString());
        equipmentToRepair.setCreateTime(DateUtil.getDatetime());
        equipmentToRepair.setState(0);
        int insert = equipmentToRepairMapper.insert(equipmentToRepair);

        if (insert == 1){
            //通知人
        SystemConfig equipment_to_repair = configService.queryByCoding("EQUIPMENT_ROLE_ID");
        List<Staff> staffList = roleService.getStaffByRoleCoding(equipment_to_repair.getValue());

            Map<String,Object> msg = new HashMap<>();
            msg.put("title","送修申报");
            msg.put("content",equipmentToRepair.getCause());
            msg.put("mTitle","");
            msg.put("url", WebParam.VUETIFY_BASE+"/equipmentToRepair/detailView" + equipmentToRepair.getId());
            flowNotifyService.msgNotify(staffList,msg);
        }

        return insert;
    }

    @Override
    public int delete(String id) {
        return equipmentToRepairMapper.delete(id);
    }

    @Override
    public int update(EquipmentToRepair equipmentToRepair) {
        int update = equipmentToRepairMapper.update(equipmentToRepair);
        if (equipmentToRepair.getState() == 2 || equipmentToRepair.getState() == 3){
            equipmentToRepair.setNotifyTime(DateUtil.getDatetime());
            equipmentToRepairMapper.update(equipmentToRepair);
            List<Staff> staffList = new ArrayList<>();
            staffList.add(equipmentToRepair.getStaffApplicant());

            Map<String,Object> msg = new HashMap<>();
            msg.put("title","送修结果");
            msg.put("content",equipmentToRepair.getState() == 2 ? "已收货":"已报废");
            msg.put("mTitle","");
            msg.put("url",WebParam.VUETIFY_BASE+"/equipmentToRepair/components/equipmentNotify/" + equipmentToRepair.getId());
            flowNotifyService.msgNotify(staffList,msg);
        }
        return update;
    }

    @Override
    public int updateState(String id, Integer state) {
        return equipmentToRepairMapper.updateState(id,state);
    }

    @Override
    public EquipmentToRepair selectById(String id) {
        return equipmentToRepairMapper.selectById(id);
    }

    @Override
    public List<EquipmentToRepair> list(Map<String,Object> params) {
        return equipmentToRepairMapper.list(params);
    }

    @Override
    public int updateNotify(String id, Integer notifyFlag) {
        return equipmentToRepairMapper.updateNotify(id,notifyFlag);
    }

    @Override
    public void updateNotifyTiming() {
        Date endDate = new Date();
        Date startDate = new Date(endDate.getTime() - 3 * 24 * 3600 * 1000l);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String start = s.format(startDate);
        equipmentToRepairMapper.updateNotifyTiming(start);
    }
}
