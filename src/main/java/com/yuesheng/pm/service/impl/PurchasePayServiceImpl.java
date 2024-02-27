package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.PurchasePay;
import com.yuesheng.pm.mapper.PurchasePayMapper;
import com.yuesheng.pm.service.PurchasePayService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PurchasePayService")
public class PurchasePayServiceImpl implements PurchasePayService {
    @Autowired
    private PurchasePayMapper purchasePayMapper;

    @Override
    public List<PurchasePay> selectByDay(String dateSearch) {
        String start = dateSearch + " 00:00:00";
        String lastDayOfMonth = dateSearch + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return purchasePayMapper.selectByDay(startDate,endDate);
    }

    @Override
    public List<PurchasePay> selectByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return purchasePayMapper.selectByDate(startDate,endDate);
    }

    @Override
    public PurchasePay selectByPMark(String pMark) {
        return purchasePayMapper.selectByPMark(pMark);
    }

    @Override
    public List<PurchasePay> selectByID(Integer id) {
        return purchasePayMapper.selectByID(id);
    }

    @Override
    public int insert(PurchasePay purchasePay) {
        return purchasePayMapper.insert(purchasePay);
    }

    @Override
    public int delete(Integer id) {
        return purchasePayMapper.delete(id);
    }

    @Override
    public int deleteByID(Integer id) {
        return purchasePayMapper.deleteByID(id);
    }

    @Override
    public int deleteByPMark(String pMark) {
        return purchasePayMapper.deleteByPMark(pMark);
    }

    @Override
    public int update(PurchasePay purchasePay) {
        return purchasePayMapper.update(purchasePay);
    }
}
