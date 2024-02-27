package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ContractWordModel;

import java.util.List;

/**
 * @ClassName ContractWordModelService
 * @Description
 * @Author ssk
 * @Date 2024/1/31 0031 11:25
 */
public interface ContractWordModelService {
    int insert(ContractWordModel contractWordModel);
    int update(ContractWordModel contractWordModel);
    List<ContractWordModel> list();
    int delete(int id);
    ContractWordModel selectByType(int type);
}
