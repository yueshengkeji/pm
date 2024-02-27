package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.PurchaseCollect;
import com.yuesheng.pm.mapper.PurchaseCollectMapper;
import com.yuesheng.pm.service.PurchaseCollectService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PurchaseCollectService")
public class PurchaseCollectServiceImpl implements PurchaseCollectService {
    @Autowired
    private PurchaseCollectMapper purchaseCollectMapper;

    @Override
    public List<PurchaseCollect> selectByDay(String dateSearch) {
        String start = dateSearch + " 00:00:00";
        String lastDayOfMonth = dateSearch + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return purchaseCollectMapper.selectByDay(startDate,endDate);
    }

    @Override
    public List<PurchaseCollect> selectByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return purchaseCollectMapper.selectByDate(startDate,endDate);
    }

    @Override
    public PurchaseCollect selectByPMark(String pMark) {
        return purchaseCollectMapper.selectByPMark(pMark);
    }

    @Override
    public List<PurchaseCollect> selectByID(Integer id) {
        return purchaseCollectMapper.selectByID(id);
    }

    @Override
    public int insert(PurchaseCollect purchaseCollect) {
        return purchaseCollectMapper.insert(purchaseCollect);
    }

    @Override
    public int delete(Integer id) {
        return purchaseCollectMapper.delete(id);
    }

    @Override
    public int deleteByID(Integer id) {
        return purchaseCollectMapper.deleteByID(id);
    }

    @Override
    public int update(PurchaseCollect purchaseCollect) {
        return purchaseCollectMapper.update(purchaseCollect);
    }
}
