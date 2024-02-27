package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.HouseBillEntity;
import com.yuesheng.pm.mapper.HouseBillMapper;
import com.yuesheng.pm.service.HouseBillService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HouseBillServiceImpl implements HouseBillService {
    @Autowired
    private HouseBillMapper houseBillMapper;

    @Override
    public int insert(HouseBillEntity houseBillEntity) {
        int v = verify(houseBillEntity);
        if (v != -1) {
            houseBillEntity.setId(UUID.randomUUID().toString());
            houseBillEntity.setDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            houseBillMapper.insert(houseBillEntity);
            return v;
        } else {
            return v;
        }

    }

    private int verify(HouseBillEntity houseBillEntity) {
        int v = 1;
        if (houseBillEntity == null) {
            v = -1;
        } else if (houseBillEntity.getMoney() == null || houseBillEntity.getMoney() <= 0.0) {
            v = -2;
        } else if (houseBillEntity.getStaff() == null) {
            v = -3;
        }
        return v;
    }

    @Override
    public int update(HouseBillEntity houseBillEntity) {
        int v = verify(houseBillEntity);
        if (v > -1) {
            if (StringUtils.isNotBlank(houseBillEntity.getId())) {
                return houseBillMapper.update(houseBillEntity);
            } else {
                return -1;
            }
        } else {
            return v;
        }

    }

    @Override
    public int delete(String id) {
        return houseBillMapper.delete(id);
    }

    @Override
    public List<HouseBillEntity> queryList(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, false);
        return houseBillMapper.queryList(params);
    }

    @Override
    public HouseBillEntity queryById(String id) {
        return houseBillMapper.queryById(id);
    }

    @Override
    public int queryListCount(HashMap<String, Object> params) {
        return houseBillMapper.queryListCount(params);
    }
}
