package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.DingTalkStaffInfo;
import com.yuesheng.pm.entity.Staff;

import java.util.List;

/**
 * @ClassName DingTalkStaffInfoService
 * @Description
 * @Author ssk
 * @Date 2022/7/21 0021 14:03
 */
public interface DingTalkStaffInfoService {

    /**
     * 根据钉钉userId查询
     * @param userId 钉钉用户id
     * @return
     */
    DingTalkStaffInfo selectByDing(String userId);

    /**
     * @param dingTalkStaffInfo
     * @return
     */
    int insertDing(DingTalkStaffInfo dingTalkStaffInfo);

    /**
     * 更新信息（当更换手机号登录钉钉，再进入系统时更换userId）
     * @param staff
     * @param userId
     * @return
     */
    int update(Staff staff,String userId);

    /**
     * 根据员工Id查询
     * @param staffId
     * @return
     */
    DingTalkStaffInfo selectByStaffId(String staffId);

    /**
     * 根据员工id删除
     * @param staffId
     * @return
     */
    int deleteByStaffId(String staffId);

    /**
     * 获取所有绑定钉钉的员工
     * @return
     */
    List<DingTalkStaffInfo> selectAll();
}
