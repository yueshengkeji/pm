package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ContractWordRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName ContractWordRecordMapper
 * @Description
 * @Author ssk
 * @Date 2024/2/2 0002 15:05
 */
@Mapper
public interface ContractWordRecordMapper {
    int insert(ContractWordRecord contractWordRecord);
    ContractWordRecord selectLastOneByContractId(String contractId);
}
