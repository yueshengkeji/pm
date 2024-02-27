package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.DingTalkStaffInfo;
import com.yuesheng.pm.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName DingTalkStaffCheckMapper
 * @Description
 * @Author ssk
 * @Date 2022/7/21 0021 11:14
 */
@Mapper
public interface DingTalkStaffInfoMapper {

    /**
     * 根据钉钉userId查询
     * @param userId 钉钉用户id
     * @return
     */
    DingTalkStaffInfo selectByDing(String userId);

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
    int update(@Param("staff") Staff staff,@Param("userId") String userId);

    /**
     * 获取所有绑定钉钉的员工
     * @return
     */
    List<DingTalkStaffInfo> selectAll();
}
