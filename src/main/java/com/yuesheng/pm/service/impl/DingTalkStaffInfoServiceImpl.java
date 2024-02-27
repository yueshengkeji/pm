package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.DingTalkStaffInfo;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.DingTalkStaffInfoMapper;
import com.yuesheng.pm.service.DingTalkStaffInfoService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName DingTalkStaffInfoServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/7/21 0021 14:05
 */
@Service
public class DingTalkStaffInfoServiceImpl implements DingTalkStaffInfoService {

    @Autowired
    private DingTalkStaffInfoMapper dingTalkStaffInfoMapper;

    @Override
    public DingTalkStaffInfo selectByDing(String userId) {
        return dingTalkStaffInfoMapper.selectByDing(userId);
    }

    @Override
    public int insertDing(DingTalkStaffInfo dingTalkStaffInfo) {
        dingTalkStaffInfo.setId(UUID.randomUUID().toString());
        dingTalkStaffInfo.setCreateTime(DateUtil.getNowDate());
        return dingTalkStaffInfoMapper.insertDing(dingTalkStaffInfo);
    }

    @Override
    public int update(Staff staff, String userId) {
        return dingTalkStaffInfoMapper.update(staff,userId);
    }

    @Override
    public DingTalkStaffInfo selectByStaffId(String staffId) {
        PageHelper.startPage(1,1);
        return dingTalkStaffInfoMapper.selectByStaffId(staffId);
    }

    @Override
    public int deleteByStaffId(String staffId) {
        return dingTalkStaffInfoMapper.deleteByStaffId(staffId);
    }

    @Override
    public List<DingTalkStaffInfo> selectAll() {
        return dingTalkStaffInfoMapper.selectAll();
    }
}
