package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SalesContract;
import com.yuesheng.pm.entity.SalesContractManager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SalesContractManagerMapper
 * @Description
 * @Author ssk
 * @Date 2022/6/22 0022 8:28
 */
@Mapper
public interface SalesContractManagerMapper {

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
    List<SalesContract> selectByManager(String managerID);

    /**
     * 查询项目经理被授权的销售合同(计数)
     * @param managerID
     * @return
     */
    Integer selectByManagerCounts(String managerID);
}
