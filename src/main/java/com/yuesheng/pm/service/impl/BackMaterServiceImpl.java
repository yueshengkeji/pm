package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.BackMater;
import com.yuesheng.pm.entity.BackMaterChild;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.BackMaterMapper;
import com.yuesheng.pm.service.BackMaterChildService;
import com.yuesheng.pm.service.BackMaterService;
import com.yuesheng.pm.service.StorageService;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.NumberFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author XiaoSong
 * @date 2017/2/22
 * 退料单服务实现
 */
@Service("backMaterService")
public class BackMaterServiceImpl implements BackMaterService {
    @Autowired
    private BackMaterMapper backMaterMapper;
    @Autowired
    private BackMaterChildService backMaterChildService;
    @Autowired
    private StorageService storageService;

    @Override
    public BackMater addBackMater(BackMater back) {
        back.setId(UUID.randomUUID().toString());
        if (StringUtils.isBlank(back.getDate())) {
            back.setDate(DateFormat.getDate());
        }
        if (StringUtils.isBlank(back.getBackNumber())) {
            back.setBackNumber(newNumber());
        }
        if (Objects.isNull(back.getBackStaff())) {
            back.setBackStaff(back.getStaff());
        }
        if (Objects.isNull(back.getSection())) {
            back.setSection(back.getStaff().getSection());
        }
        backMaterMapper.addBackMater(back);
        //遍历材料对象，并添加到数据库
        for (BackMaterChild child : back.getMaters()) {
            child.setId(UUID.randomUUID().toString());
            child.setBackId(back.getId());
            backMaterChildService.addMater(child);
        }
        return back;
    }

    @Override
    public int updateBackMater(BackMater back) throws Exception {
        for (BackMaterChild child : back.getMaters()) {
            backMaterChildService.update(child);
        }
        return backMaterMapper.updateBackMater(back);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBack(String id) {
        backMaterChildService.deleteList(id);
        return backMaterMapper.deleteBack(id);
    }

    @Override
    @Transactional
    public void approveBack(String id, String date, String staffCoding, String state) {
        boolean approve = StringUtils.equals(state, "0");
        BackMater bm = getBackById(id);
        if (!Objects.isNull(bm)) {
            List<BackMaterChild> bmc = backMaterChildService.getMaters(id);
            bm.setMaters(bmc);

            if (StringUtils.isBlank(bm.getBackNumber())) {
                bm.setBackNumber(newNumber());
                backMaterMapper.updateBackMater(bm);
            }

            if (approve) {
                bm.setApproveDate(DateUtil.getDate());
                bm.setApproveStaff(staffCoding);
                bm.setApproveState((byte) 1);
            } else {
                bm.setApproveStaff("");
                bm.setApproveDate("");
                bm.setApproveState((byte) 0);
            }

            for (int i = 0; i < bmc.size(); i++) {
                BackMaterChild item = bmc.get(i);
                if (!Objects.isNull(item)) {
                    storageService.updateBackMaterial(item, bm.getStorage().getId(), approve);
                }
            }

            backMaterMapper.updateApprove(bm);
        }
//        backMaterMapper.approveBack(id, date, staffCoding, state);
    }

    private String newNumber() {
        String number = getBackNumber();
        return NumberFormat.createSeries(number);
    }

    @Override
    public List<BackMater> getBackMater(Map<String, Object> params) {
        return backMaterMapper.getBackMater(params);
    }

    @Override
    public String getBackNumber() {
        PageHelper.startPage(1,1);
        return backMaterMapper.getBackNumber(DateUtil.getDate());
    }

    @Override
    public Integer getBackMaterCount(Map<String, Object> params) {
        return backMaterMapper.getBackMaterCount(params);
    }

    @Override
    public List<BackMater> getMatersByParam(Map<String, String> params) {
        return backMaterMapper.getMatersByParam(params);
    }

    @Override
    public BackMater getBackById(String id) {
        return backMaterMapper.getBackById(id);
    }

    @Override
    public Double getBackMoneyByProject(String projectId) {
        return backMaterChildService.getBackMoneyByProject(projectId);
    }

    @Override
    public BackMater addBackMater(BackMater backMater, Staff staff) {
        backMater.setStaff(staff);
        backMater = addBackMater(backMater);
        if (backMater.getId() != null) {      //自动审核
            try {
                approveBack(backMater.getId(), backMater.getDate(), backMater.getStaff().getCoding(), 0 + "");
            } catch (Exception e) {     //退料数量大于领料数量,删除该订单
                approveBack(backMater.getId(), backMater.getDate(), backMater.getStaff().getCoding(), 1 + "");
                deleteBack(backMater.getId());
                throw e;
            }
        }
        return backMater;
    }

    @Override
    @Transactional
    public BackMater updateBackMater(BackMater backMater, Staff staff) throws Exception {
        BackMater bm = getBackById(backMater.getId());
        if (Objects.isNull(bm)) {
            throw new Exception("退料单不存在");
        } else if (bm.getApproveState() == 1) {
            throw new Exception("退料单已审核，禁止修改，请反审核后再修改");
        }
        //删除旧退料单
        deleteBack(backMater.getId());
        //添加新的退料单
        addBackMater(backMater, staff);
        return backMater;
    }
}
