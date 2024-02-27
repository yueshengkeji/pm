package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.SalesContractLogs;
import com.yuesheng.pm.mapper.SalesContractLogsMapper;
import com.yuesheng.pm.service.SalesContractLogsService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName SalesContractLogsServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/6/24 0024 10:44
 */
@Service
public class SalesContractLogsServiceImpl implements SalesContractLogsService {
    @Autowired
    private SalesContractLogsMapper salesContractLogsMapper;
    @Override
    public int insertLogs(SalesContractLogs salesContractLogs) {
        salesContractLogs.setId(UUID.randomUUID().toString());
        salesContractLogs.setCreateTime(DateUtil.getNowDate());
        return salesContractLogsMapper.insertLogs(salesContractLogs);
    }

    @Override
    public List<SalesContractLogs> selectLogs(String agreementID, Integer type) {
        return salesContractLogsMapper.selectLogs(agreementID,type);
    }
}
