package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.StaffAdditionInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: kailismart.com
 * @description: Staff附加信息，包含微信userId和OpenId
 * @author: zcj
 * @create: 2019-06-11 14:36
 **/
@Mapper
public interface StaffAdditionInfoMapper {

    /**
     * 插入一条Staff附加信息
     * @param staffAdditionInfo 需要插入的数据
     * @return  受影响行数
     */
    Integer insert( StaffAdditionInfo staffAdditionInfo );

    /**
     * 根据staffId和systemId获取Staff附加信息
     * @param staffId   用户ID
     * @param systemId  系统ID(可为空)
     * @return  Staff附加信息
     */
    StaffAdditionInfo getStaffAdditionInfoByStaffIdAndSystemId( @Param("staffId") String staffId ,@Param("systemId") String systemId );

    /**
     * 根据wxUserId获取Staff附加信息
     * @param wxUserId  微信UserId
     * @return  Staff附加信息
     */
    StaffAdditionInfo getStaffAdditionInfoByWxUserId( @Param("wxUserId") String wxUserId );

    /**
     * 根据微信OpenId获取Staff附加信息
     * @param wxOpenId 微信OpenId
     * @return Staff附加信息
     */
    StaffAdditionInfo getStaffAdditionInfoByWxOpenId(@Param("wxOpenId") String wxOpenId);

    /**
     * 获取所有绑定微信用户信息
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
    int deleteByStaffId(@Param("staffId") String staffId);

    /**
     * 删除用户绑定信息
     *
     * @param wxUserId 微信userId
     * @return
     */
    int deleteByWxUserId(@Param("wxUserId") String wxUserId);
}
