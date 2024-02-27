package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Material;
import com.yuesheng.pm.entity.Unit;
import com.yuesheng.pm.mapper.UnitMapper;
import com.yuesheng.pm.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Administrator on 2016-08-12 材料单位服务实现.
 */
@Service("unitService")
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitMapper unitMapper;

    @Override
    public Unit getUnit(String id) {
        return unitMapper.getUnit(id);
    }

    @Override
    public Unit isExist(String name) {
        PageHelper.startPage(1, 1);
        return unitMapper.isExist(name);
    }

    @Override
    public void addUnit(Unit unit) {
        int i = 0;
        while (i <= 10) {
            i++;
            try {
                unit.setId(UUID.randomUUID().toString().substring(0, 10));
                unitMapper.addUnit(unit);
            } catch (Exception e) {
                System.err.println("insert unit error:" + e.getMessage());
                continue;
            }
            break;
        }

    }

    @Override
    public List<Unit> seek(String str) {
        return unitMapper.seek(str);
    }

    @Override
    public Unit getUnitByMaterial(String materialId) {
        return unitMapper.getUnitByMater(materialId);
    }

    @Override
    public int addUnitToMater(Material material) {
        if (Objects.isNull(getUnitByMaterial(material.getId()))) {
            return unitMapper.addUnitToMater(material);
        } else {
            return 1;
        }
    }

    @Override
    public int deleteBind(String materId, String id) {
        return unitMapper.deleteBind(materId,id);
    }

    @Override
    public List<Unit> getUnitAllByMaterial(String materId) {
        return unitMapper.getUnitAllByMaterial(materId);
    }
}
