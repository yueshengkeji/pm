package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.SalesContractLogs;

import java.util.List;

/**
 * @ClassName SalesContractLogsService
 * @Description
 * @Author ssk
 * @Date 2022/6/24 0024 10:41
 */
public interface SalesContractLogsService {
    /**
     * 新增日志
     * @param salesContractLogs
     * @return
     */
    int insertLogs(SalesContractLogs salesContractLogs);

    /**
     * 查询日志
     * @param agreementID 合同编号
     * @param type 类型
     * @return
     */
    List<SalesContractLogs> selectLogs(String agreementID, Integer type);
}
