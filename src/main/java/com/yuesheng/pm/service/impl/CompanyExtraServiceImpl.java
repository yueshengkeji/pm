package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.CompanyExtra;
import com.yuesheng.pm.mapper.CompanyExtraMapper;
import com.yuesheng.pm.service.CompanyExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CompanyExtraServiceImpl
 * @Description
 * @Author ssk
 * @Date 2022/6/20 0020 9:47
 */
@Service
public class CompanyExtraServiceImpl implements CompanyExtraService {

    @Autowired
    private CompanyExtraMapper companyExtraMapper;

    @Override
    public int insert(CompanyExtra companyExtra) {
        return companyExtraMapper.insert(companyExtra);
    }

    @Override
    public int update(CompanyExtra companyExtra) {
        return companyExtraMapper.update(companyExtra);
    }
}
