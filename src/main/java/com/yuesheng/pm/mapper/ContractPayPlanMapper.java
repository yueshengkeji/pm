package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ContractPayPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaoSong
 * @date 2020/3/19
 * 合同预申请付款单
 */
@Mapper
public interface ContractPayPlanMapper {
    /**
     * 添加预申请付款单
     *
     * @param plan
     */
    void insert(ContractPayPlan plan);

    /**
     * 更新预申请付款单状态
     *
     * @param plan
     */
    void updateState(ContractPayPlan plan);

    /**
     * 查询合同预申请付款单
     *
     * @param contractId 合同id
     * @return
     */
    ContractPayPlan queryByContractId(@Param("contractId") String contractId);

    /**
     * 删除预付款申请单
     *
     * @param contractId 合同id
     */
    void deleteByContractId(@Param("contractId") String contractId);
}
