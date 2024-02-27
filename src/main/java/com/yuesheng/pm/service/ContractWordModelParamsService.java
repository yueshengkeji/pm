package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ContractWordModelParams;

import java.util.List;

/**
 * @ClassName ContractWordModelParamsService
 * @Description
 * @Author ssk
 * @Date 2024/2/1 0001 11:03
 */
public interface ContractWordModelParamsService {
    int insert(ContractWordModelParams contractWordModelParams);
    int update(ContractWordModelParams contractWordModelParams);
    List<ContractWordModelParams> list();
    int delete(int id);
    List<ContractWordModelParams> listByIds(List<Integer> ids);
}
