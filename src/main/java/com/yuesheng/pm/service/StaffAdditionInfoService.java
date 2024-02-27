package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.StaffAdditionInfo;

import java.util.List;

/**
 * @program: kailismart.com
 * @description: Staff附加信息，包含微信userId和OpenId
 * @author: zcj
 * @create: 2019-06-11 14:45
 **/
public interface StaffAdditionInfoService {

    /**
     * 插入一条Staff附加信息
     * @param staffAdditionInfo 需要插入的数据
     * @return  true:插入成功，false:插入失败
     */
    boolean insert( StaffAdditionInfo staffAdditionInfo );

    /**
     * 根据staffId和systemId获取Staff附加信息
     * @param staffId   用户ID
     * @param systemId  系统ID（可为空）
     * @return  Staff附加信息
     */
    StaffAdditionInfo getStaffAdditionInfoByStaffIdAndSystemId( String staffId , String systemId );

    /**
     * 根据wxUserId获取Staff附加信息
     * @param wxUserId  微信UserId
     * @return  Staff附加信息
     */
    StaffAdditionInfo getStaffAdditionInfoByWxUserId( String wxUserId );

    /**
     * 根据微信OpenId获取Staff附加信息
     * @param wxOpenId 微信OpenId
     * @return Staff附加信息
     */
    StaffAdditionInfo getStaffAdditionInfoByWxOpenId(String wxOpenId);

    /**
     * 获取所有微信绑定用户
     *
     * @return
     */
    List<StaffAdditionInfo> getAllWxUser();

    /**
     * 解除该员工微信绑定
     *
     * @param staffId
     * @return
     */
    int deleteByStaffId(String staffId);
}
