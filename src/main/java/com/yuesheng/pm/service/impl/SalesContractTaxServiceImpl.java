package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.SalesContractTax;
import com.yuesheng.pm.mapper.SalesContractTaxMapper;
import com.yuesheng.pm.service.SalesContractTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName SalesContractTaxServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/6/20 0020 17:06
 */
@Service
public class SalesContractTaxServiceImpl implements SalesContractTaxService {
    @Autowired
    private SalesContractTaxMapper salesContractTaxMapper;

    @Override
    public int insert(SalesContractTax salesContractTax) {
        salesContractTax.setId(UUID.randomUUID().toString());
        return salesContractTaxMapper.insert(salesContractTax);
    }

    @Override
    public int delete(String id) {
        return salesContractTaxMapper.delete(id);
    }

    @Override
    public List<SalesContractTax> selectByAgreementID(String agreementID) {
        return salesContractTaxMapper.selectByAgreementID(agreementID);
    }
}
