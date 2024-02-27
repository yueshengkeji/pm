package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.CheckMater;
import com.yuesheng.pm.entity.CheckMaterChild;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.CheckMaterMapper;
import com.yuesheng.pm.service.CheckMaterChildService;
import com.yuesheng.pm.service.CheckMaterService;
import com.yuesheng.pm.service.StorageService;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.NumberFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author XiaoSong
 * @date 2017/3/1
 */
@Service("checkMaterService")
public class CheckMaterServiceImpl implements CheckMaterService {
    @Autowired
    private CheckMaterMapper checkMaterMapper;
    @Autowired
    private CheckMaterChildService checkMaterChildService;
    @Autowired
    private StorageService storageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMater(CheckMater mater) {
        mater.setId(UUID.randomUUID().toString());
        mater.setDate(DateFormat.getDate());
        if (StringUtils.isBlank(mater.getRemark())) {
            mater.setRemark("");
        }
        if (StringUtils.isBlank(mater.getCheckNumber())) {
            mater.setCheckNumber(genCheckNumber());
        }
        checkMaterMapper.addMater(mater);
        String mainId = mater.getId();
        for (CheckMaterChild child : mater.getMaterList()) {
            child.setMainId(mainId);
            child.setId(UUID.randomUUID().toString());
            checkMaterChildService.checkMater(child);
        }
    }

    private String genCheckNumber() {
        PageHelper.startPage(1,1);
        String number = checkMaterMapper.getMaxCheckNumber(DateUtil.getDate());
        return NumberFormat.createSeries(number);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCheckMater(CheckMater mater) {
        //更新子表,如果id为null，则新增
        for (CheckMaterChild child : mater.getMaterList()) {
            if (child.getId() == null) {
                //新添加的
                child.setMainId(mater.getId());
                child.setId(UUID.randomUUID().toString());
                checkMaterChildService.checkMater(child);
            } else {
                checkMaterChildService.updateCheckMater(child);
            }
        }
        return checkMaterMapper.updateCheckMater(mater);
    }

    @Override
    @Transactional
    public int deleteCheckMater(String id) {
        //删除子表
        checkMaterChildService.deleteMaterByMain(id);
        //删除主单据
        return checkMaterMapper.deleteCheckMater(id);
    }

    @Override
    public List<CheckMater> getCheckMater(Map<String, Object> params) {
        return checkMaterMapper.getCheckMater(params);
    }

    @Override
    public int getCheckMaterCount(Map<String, Object> params) {
        return checkMaterMapper.getCheckMaterCount(params);
    }

    @Override
    public CheckMater getCheckById(String id) {
        return checkMaterMapper.getCheckById(id);
    }

    @Override
    @Transactional
    public CheckMater addMater(CheckMater check, Staff staff) {
        check.setStaff(staff);
        addMater(check);      //添加
        //审核
        approve(check, check.getStaff());
        //更新材料单价和金额
        List<CheckMaterChild> cmList = check.getMaterList();
        if (cmList != null) {
            for (CheckMaterChild cmc : cmList) {
                cmc.setStorageId(check.getStorage().getId());
                if (cmc.getMaterial() != null && cmc.getStorageId() != null) {
                    storageService.updateStoragePrice(cmc);
                }
            }
        }
        return check;
    }

    @Override
    @Transactional
    public Map<String, Object> approve(CheckMater checkMater, Staff staff) {
        Map<String, Object> result = new HashMap(16);
        int state = checkMater.getState();
        List<CheckMaterChild> cmcList = checkMaterChildService.getMaterList(checkMater.getId());
        if (state == 0) {
            //审核操作
            checkMater.setState(1);
            checkMater.setApproveStaff(staff);
            checkMater.setApproveDate(DateUtil.getDate());
            updateCheckMater(checkMater);
            //更新库存
            cmcList.forEach(item -> {
                storageService.updateByCheck(item, checkMater.getStorage());
            });
        } else {
            //反审核操作
            checkMater.setState(0);
            checkMater.setApproveDate("");
            Staff s = new Staff();
            s.setCoding("");
            checkMater.setApproveStaff(s);
            updateCheckMater(checkMater);
            //回滚盘点前的库存数
            cmcList.forEach(item -> {
                storageService.reUpdateByCheck(item, checkMater.getStorage());
            });
        }

        result.put("state", state);
        result.put("id", checkMater.getId());
        return result;
    }
}
