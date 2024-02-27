package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.BackMater;
import com.yuesheng.pm.entity.BackMaterChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.mapper.BackMaterChildMapper;
import com.yuesheng.pm.service.BackMaterChildService;
import com.yuesheng.pm.service.BackMaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 96339 on 2017/2/22.
 */
@Service("backMaterChildService")
public class BackMaterChildServiceImpl implements BackMaterChildService {
    @Autowired
    private BackMaterChildMapper backMaterChildMapper;
    @Autowired
    @Lazy
    private BackMaterService backMaterService;

    @Override
    public void addMater(BackMaterChild child) {
        backMaterChildMapper.addMater(child);
    }

    @Override
    public int update(BackMaterChild child) throws Exception {
        BackMater bm = backMaterService.getBackById(child.getBackId());
        if (Objects.isNull(bm)) {
            throw new Exception("退料单不存在");
        } else if (bm.getApproveState() == 1) {
            throw new Exception("单据已审核,禁止删除");
        }
        return backMaterChildMapper.update(child);
    }

    @Override
    public int delete(String id) {
        return backMaterChildMapper.delete(id);
    }

    @Override
    public int deleteList(String backId) {
        return backMaterChildMapper.deleteList(backId);
    }

    @Override
    public List<BackMaterChild> getMaters(String backId) {
        return backMaterChildMapper.getMaterByBack(backId);
    }

    @Override
    public Material getMaterByMaterId(String id) {
        return backMaterChildMapper.getMaterByMaterId(id);
    }

    @Override
    public List<BackMaterChild> getMaterUseByMaterId(String id) {
        List<BackMaterChild> bmcList = backMaterChildMapper.getMaterUseByMaterId(id);
        for (BackMaterChild bmc : bmcList) {
            bmc.setBm(backMaterService.getBackById(bmc.getBackId()));
        }
        return bmcList;
    }

    @Override
    public Double getBackMoneyByProject(String projectId) {
        return backMaterChildMapper.getBackMoneyByProject(projectId);
    }

    @Override
    public List<BackMaterChild> getMaterByParam(Map<String, String> params) {
        return backMaterChildMapper.getMaterByParam(params);
    }

    @Override
    public Double getBackTotalMoney(Map<String, String> params) {
        return backMaterChildMapper.getBackTotalMoney(params);
    }

    @Override
    public List<BackMaterChild> getBackMaterByOutMater(String outMater) {
        return backMaterChildMapper.getBackMaterByOutMater(outMater);
    }
}
