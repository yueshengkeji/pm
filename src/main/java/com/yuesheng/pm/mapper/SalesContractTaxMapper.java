package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.SalesContractTax;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SalesContractTaxMapper
 * @Description
 * @Author ssk
 * @Date 2022/6/20 0020 17:00
 */
@Mapper
public interface SalesContractTaxMapper {
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
