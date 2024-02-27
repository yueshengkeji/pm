package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SalesContractLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName SalesContractLogsMapper
 * @Description
 * @Author ssk
 * @Date 2022/6/24 0024 10:24
 */
@Mapper
public interface SalesContractLogsMapper {

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
    List<SalesContractLogs> selectLogs(@Param("agreementID") String agreementID, @Param("type") Integer type);
}
