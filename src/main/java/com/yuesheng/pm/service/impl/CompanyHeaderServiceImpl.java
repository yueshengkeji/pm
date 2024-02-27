package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.CompanyHeader;
import com.yuesheng.pm.mapper.CompanyHeaderMapper;
import com.yuesheng.pm.service.CompanyHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/5/26.
 */
@Service("companyHeaderService")
public class CompanyHeaderServiceImpl implements CompanyHeaderService {

    @Autowired
    private CompanyHeaderMapper companyHeaderMapper;

    @Override
    public void insert(CompanyHeader ch) {
        companyHeaderMapper.insert(ch);
    }

    @Override
    public int update(CompanyHeader ch) {
        return companyHeaderMapper.update(ch);
    }

    @Override
    public int delete(String id) {
        return companyHeaderMapper.delete(id);
    }

    @Override
    public int deleteByCompany(String companyId) {
        return companyHeaderMapper.deleteByCompany(companyId);
    }

    @Override
    public List<CompanyHeader> queryCompanys() {
        return companyHeaderMapper.queryCompanys();
    }

    @Override
    public List<CompanyHeader> queryHistory(Map<String, String> params) {
        return companyHeaderMapper.queryHistory(params);
    }
}
