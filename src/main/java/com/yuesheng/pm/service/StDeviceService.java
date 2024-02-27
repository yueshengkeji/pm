package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 商汤-考勤机服务接口
 */
public interface StDeviceService {
    /**
     * 查询考勤机设备列表
     *
     * @return
     */
    List<StDeviceEntity> getDeviceList();

    /**
     * 获取商汤考勤机数据
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @return
     */
    List<ProWorkCheck> getStWorkCheckData(String startDate, String endDate);

    /**
     * 将stWorkCheckEntity转换为proWorkCheck类型
     *
     * @param target
     * @param source
     */
    void parseProWorkCheck(ProWorkCheck target, StWorkCheckEntity source);

    /**
     * 获取设备用户集合
     *
     * @return
     */
    List<Staff> getDeviceUsers(String userId);

    /**
     * 添加人员信息到考勤机设备
     *
     * @param staff  职员信息
     * @param attach 头像附件
     * @return
     */
    Map<String, Object> addStaffTODevice(Staff staff, Attach attach);

}
