package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.entity.SalesContractManager;

import java.util.List;

/**
 * @ClassName SalesContractManagerService
 * @Description
 * @Author ssk
 * @Date 2022/6/22 0022 8:31
 */
public interface SalesContractManagerService {
    /**
     * 新增记录
     * @param salesContractManager
     * @return
     */
    int insert(SalesContractManager salesContractManager);

    /**
     * 删除记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 查询当前合同下指定的项目经理
     * @param agreementID
     * @return
     */
    List<SalesContractManager> selectByAgreementID(String agreementID);
    /**
     * 查询项目经理被授权的销售合同
     * @param managerID
     * @return
     */
    List<SalesContract> selectByManager(Integer pageNum,Integer pageSize, String managerID);
    /**
     * 查询项目经理被授权的销售合同(计数)
     * @param managerID
     * @return
     */
    Integer selectByManagerCounts(String managerID);
}
