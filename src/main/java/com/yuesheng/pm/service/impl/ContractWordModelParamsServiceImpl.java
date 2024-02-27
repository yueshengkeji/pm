package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ContractWordModelParams;
import com.yuesheng.pm.mapper.ContractWordModelParamsMapper;
import com.yuesheng.pm.service.ContractWordModelParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ContractWordModelParamsServiceImpl
 * @Description
 * @Author ssk
 * @Date 2024/2/1 0001 11:05
 */
@Service
public class ContractWordModelParamsServiceImpl implements ContractWordModelParamsService {

    @Autowired
    private ContractWordModelParamsMapper contractWordModelParamsMapper;

    @Override
    public int insert(ContractWordModelParams contractWordModelParams) {
        return contractWordModelParamsMapper.insert(contractWordModelParams);
    }

    @Override
    public int update(ContractWordModelParams contractWordModelParams) {
        return contractWordModelParamsMapper.update(contractWordModelParams);
    }

    @Override
    public List<ContractWordModelParams> list() {
        return contractWordModelParamsMapper.list();
    }

    @Override
    public int delete(int id) {
        return contractWordModelParamsMapper.delete(id);
    }

    @Override
    public List<ContractWordModelParams> listByIds(List<Integer> ids) {
        return contractWordModelParamsMapper.listByIds(ids);
    }


}
