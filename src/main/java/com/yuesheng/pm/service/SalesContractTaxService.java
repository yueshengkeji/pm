package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.SalesContractTax;

import java.util.List;

/**
 * @ClassName SalesContractTaxService
 * @Description
 * @Author ssk
 * @Date 2022/6/20 0020 17:05
 */
public interface SalesContractTaxService {
    /**
     * 新增
     * @param salesContractTax
     * @return
     */
    int insert(SalesContractTax salesContractTax);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 通过合同编号查询
     * @param agreementID
     * @return
     */
    List<SalesContractTax> selectByAgreementID(String agreementID);
}
