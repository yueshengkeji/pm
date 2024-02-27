package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.PurchaseContract;
import com.yuesheng.pm.mapper.PurchaseContractMapper;
import com.yuesheng.pm.service.PurchaseContractService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PurchaseContractService")
public class PurchaseContractServiceImpl implements PurchaseContractService {
    @Autowired
    private PurchaseContractMapper purchaseContractMapper;
    @Override
    public PurchaseContract selectByID(Integer id) {
        return purchaseContractMapper.selectByID(id);
    }

    @Override
    public PurchaseContract selectByAgreementID(String agreementID) {
        return purchaseContractMapper.selectByAgreementID(agreementID);
    }

    @Override
    public List<PurchaseContract> selectBySearch(String search) {
        return purchaseContractMapper.selectBySearch(search);
    }

    @Override
    public List<PurchaseContract> selectAll() {
        return purchaseContractMapper.selectAll();
    }

    @Override
    public List<PurchaseContract> selectForPage(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return purchaseContractMapper.selectForPage();
    }

    @Override
    public Integer selectCounts() {
        return purchaseContractMapper.selectCounts();
    }

    @Override
    public List<PurchaseContract> selectByDateForPage(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return purchaseContractMapper.selectByDateForPage(startDate,endDate);
    }

    @Override
    public List<PurchaseContract> selectByDateYear(String dateSearch) {
        return purchaseContractMapper.selectByDateYear(dateSearch);
    }

    @Override
    public Integer selectCountsByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(start) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start,DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth,DateUtil.PATTERN_CLASSICAL).getTime());
        return purchaseContractMapper.selectCountsByDate(startDate,endDate);
    }

    @Override
    public int insertContract(PurchaseContract purchaseContract) {
        return purchaseContractMapper.insertContract(purchaseContract);
    }

    @Override
    public int deleteContract(Integer id) {
        return purchaseContractMapper.deleteContract(id);
    }

    @Override
    public int updateContract(PurchaseContract purchaseContract) {
        return purchaseContractMapper.updateContract(purchaseContract);
    }
}
