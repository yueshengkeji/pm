package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ContractWordModel;
import com.yuesheng.pm.mapper.ContractWordModelMapper;
import com.yuesheng.pm.service.ContractWordModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CotractWordModelServiceImpl
 * @Description
 * @Author ssk
 * @Date 2024/1/31 0031 11:25
 */
@Service
public class ContractWordModelServiceImpl implements ContractWordModelService {
    @Autowired
    private ContractWordModelMapper contractWordModelMapper;

    @Override
    public int insert(ContractWordModel contractWordModel) {
        return contractWordModelMapper.insert(contractWordModel);
    }

    @Override
    public int update(ContractWordModel contractWordModel) {
        return contractWordModelMapper.update(contractWordModel);
    }

    @Override
    public List<ContractWordModel> list() {
        return contractWordModelMapper.list();
    }

    @Override
    public int delete(int id) {
        return contractWordModelMapper.delete(id);
    }

    @Override
    public ContractWordModel selectByType(int type) {
        return contractWordModelMapper.selectByType(type);
    }
}
