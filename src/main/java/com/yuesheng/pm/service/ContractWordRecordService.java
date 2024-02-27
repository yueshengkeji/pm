package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ContractWordRecord;
import com.yuesheng.pm.entity.ProZujin;

/**
 * @ClassName ContractWoedRecordService
 * @Description
 * @Author ssk
 * @Date 2024/2/2 0002 15:11
 */
public interface ContractWordRecordService {
    int insert(ProZujin proZujin);
    ContractWordRecord selectLastOneByContractId(String contractId);
}
