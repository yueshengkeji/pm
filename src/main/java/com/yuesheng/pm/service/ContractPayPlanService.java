package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ContractPayPlan;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaoSong
 * @date 2020/3/19
 * 合同预付款申请服务接口
 */
public interface ContractPayPlanService {
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
    void deleteByContract(String contractId);

    /**
     * 开始合同付款申请
     *
     * @param frameId
     */
    void startContractPay(String frameId);
}
