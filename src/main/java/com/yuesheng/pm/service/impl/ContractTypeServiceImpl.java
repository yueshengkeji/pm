package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ContractType;
import com.yuesheng.pm.mapper.ContractTypeMapper;
import com.yuesheng.pm.service.ContractTypeService;
import com.yuesheng.pm.util.AESEncrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
@Service("contractTypeService")
public class ContractTypeServiceImpl implements ContractTypeService {
    @Autowired
    ContractTypeMapper contractTypeMapper;

    @Override
    public ContractType getTypeByStr(String str) {
        return contractTypeMapper.getTypeByStr(str);
    }

    @Override
    public ContractType getTypeById(String id) {
        return contractTypeMapper.getTypeById(id);
    }

    @Override
    public List<ContractType> getTypeByParent(String parentId) {
        return contractTypeMapper.getTypeByParent(parentId);
    }

    @Override
    public List<ContractType> seek(String str) {
        return contractTypeMapper.seek(str);
    }

    @Override
    public List<ContractType> getContractTypes() {
        return contractTypeMapper.getContractTypes();
    }

    @Override
    public int insert(ContractType contractType) {
        contractType.setId(AESEncrypt.getRandom8Id());
        contractType.setParentId(StringUtils.isBlank(contractType.getParentId()) ? "" : contractType.getParentId());
        contractType.setRootId(StringUtils.isBlank(contractType.getTempRoot()) ? contractType.getId() : contractType.getTempRoot() + contractType.getId());
        return contractTypeMapper.insert(contractType);
    }

    @Override
    public int update(ContractType contractType) {
        return contractTypeMapper.update(contractType);
    }

    @Override
    public int delete(String id) {
        return contractTypeMapper.delete(id);
    }
}
