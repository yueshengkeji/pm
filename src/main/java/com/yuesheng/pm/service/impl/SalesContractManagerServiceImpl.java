package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.entity.SalesContractManager;
import com.yuesheng.pm.mapper.SalesContractManagerMapper;
import com.yuesheng.pm.service.SalesContractManagerService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName SalesContractManagerServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/6/22 0022 8:32
 */
@Service
public class SalesContractManagerServiceImpl implements SalesContractManagerService {
    @Autowired
    private SalesContractManagerMapper salesContractManagerMapper;
    @Override
    public int insert(SalesContractManager salesContractManager) {
        salesContractManager.setId(UUID.randomUUID().toString());
        salesContractManager.setCreateTime(DateUtil.getNowDate());
        return salesContractManagerMapper.insert(salesContractManager);
    }

    @Override
    public int delete(String id) {
        return salesContractManagerMapper.delete(id);
    }

    @Override
    public List<SalesContractManager> selectByAgreementID(String agreementID) {
        return salesContractManagerMapper.selectByAgreementID(agreementID);
    }

    @Override
    public List<SalesContract> selectByManager(Integer pageNum,Integer pageSize,String managerID) {
        PageHelper.startPage(pageNum,pageSize,false);
        return salesContractManagerMapper.selectByManager(managerID);
    }

    @Override
    public Integer selectByManagerCounts(String managerID) {
        return salesContractManagerMapper.selectByManagerCounts(managerID);
    }
}
