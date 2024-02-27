package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.CheckMaterChild;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.mapper.CheckMaterChildMapper;
import com.yuesheng.pm.service.CheckMaterChildService;
import com.yuesheng.pm.service.CheckMaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 96339 on 2017/3/1.
 */
@Service("checkMaterChildService")
public class CheckMaterChildServiceImpl implements CheckMaterChildService {
    @Autowired
    private CheckMaterChildMapper checkMaterChildMapper;
    @Autowired
    @Lazy
    private CheckMaterService checkMaterService;
    @Override
    public void checkMater(CheckMaterChild mater) {
        checkMaterChildMapper.checkMater(mater);
    }

    @Override
    public int updateCheckMater(CheckMaterChild mater) {
        return checkMaterChildMapper.updateCheckMater(mater);
    }

    @Override
    public int deleteMaterByMain(String mainId) {
        return checkMaterChildMapper.deleteMaterByMain(mainId);
    }

    @Override
    public int deleteById(String id) {
        return checkMaterChildMapper.deleteById(id);
    }

    @Override
    public List<CheckMaterChild> getMaterList(String mainId) {
        return checkMaterChildMapper.getMaterList(mainId);
    }

    @Override
    public Material getMaterByMaterId(String id) {
        PageHelper.startPage(1,1);
        return checkMaterChildMapper.getMaterByMaterId(id);
    }

    @Override
    public List<CheckMaterChild> getMaterUseByMaterId(String id) {
        List<CheckMaterChild> cmcList = checkMaterChildMapper.getMaterUseByMaterId(id);
        for (CheckMaterChild cmc : cmcList) {
            cmc.setCm(checkMaterService.getCheckById(cmc.getMainId()));
        }
        return cmcList;
    }

    @Override
    public List<CheckMaterChild> listByParam(HashMap<String, Object> param) {
        return checkMaterChildMapper.listByParam(param);
    }

    @Override
    public Double getCheckTotalMoney(HashMap<String, Object> params) {
        return checkMaterChildMapper.getCheckTotalMoney(params);
    }
}
