package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.StaffAdditionInfo;
import com.yuesheng.pm.mapper.StaffAdditionInfoMapper;
import com.yuesheng.pm.service.StaffAdditionInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @program: kailismart.com
 * @description: Staff附加信息，包含微信userId和OpenId
 * @author: zcj
 * @create: 2019-06-11 14:47
 **/
@Service("staffAdditionInfoService")
public class StaffAdditionInfoServiceImpl implements StaffAdditionInfoService {

    @Autowired
    private StaffAdditionInfoMapper staffAdditionInfoMapper;

    @Override
    public boolean insert( StaffAdditionInfo staffAdditionInfo ) {
        //判断是否插入成功标志
        boolean isInsert = false;

        //条件判断
        if(staffAdditionInfo != null
                && StringUtils.isNotBlank( staffAdditionInfo.getStaffId() )
                && ( StringUtils.isNotBlank( staffAdditionInfo.getWxOpenId() ) || StringUtils.isNotBlank( staffAdditionInfo.getWxUserId() ) )
                && StringUtils.isNotBlank( staffAdditionInfo.getSystemId() )
                && StringUtils.isNotBlank( staffAdditionInfo.getSystemName() )) {

            //判断ID是否为空,若为空，则手动赋予一个ID
            if (StringUtils.isBlank(staffAdditionInfo.getId())) {
                staffAdditionInfo.setId(UUID.randomUUID().toString());
            }

            StaffAdditionInfo old = getStaffAdditionInfoByWxUserId(staffAdditionInfo.getWxUserId());
            if (!Objects.isNull(old)) {
                deleteByWxUserId(staffAdditionInfo.getWxUserId());
            }

            Integer count = staffAdditionInfoMapper.insert(staffAdditionInfo);
            if (count != null && count > 0) {
                isInsert = true;
            }
        }

        return isInsert;
    }

    private int deleteByWxUserId(String wxUserId) {
        return staffAdditionInfoMapper.deleteByWxUserId(wxUserId);
    }

    @Override
    public StaffAdditionInfo getStaffAdditionInfoByStaffIdAndSystemId(String staffId, String systemId) {
        //返回对象
        StaffAdditionInfo staffAdditionInfo = null;

        //判断参数条件是否满足
        if (StringUtils.isNotBlank(staffId)) {
            PageHelper.startPage(1,1);
            staffAdditionInfo = staffAdditionInfoMapper.getStaffAdditionInfoByStaffIdAndSystemId(staffId, systemId);
        }

        return staffAdditionInfo;
    }

    @Override
    public StaffAdditionInfo getStaffAdditionInfoByWxUserId( String wxUserId) {
        //返回对象
        StaffAdditionInfo staffAdditionInfo = null;

        //判断参数条件是否满足
        if( StringUtils.isNotBlank( wxUserId ) ){
            staffAdditionInfo = staffAdditionInfoMapper.getStaffAdditionInfoByWxUserId( wxUserId );
        }

        return staffAdditionInfo;
    }

    @Override
    public StaffAdditionInfo getStaffAdditionInfoByWxOpenId( String wxOpenId) {
        //返回对象
        StaffAdditionInfo staffAdditionInfo = null;

        //判断参数条件是否满足
        if( StringUtils.isNotBlank( wxOpenId ) ){
            staffAdditionInfo = staffAdditionInfoMapper.getStaffAdditionInfoByWxOpenId(wxOpenId);
        }

        return staffAdditionInfo;
    }

    @Override
    public List<StaffAdditionInfo> getAllWxUser() {
        return staffAdditionInfoMapper.getAllWxUser();
    }

    @Override
    public int deleteByStaffId(String staffId) {
        return staffAdditionInfoMapper.deleteByStaffId(staffId);
    }
}
