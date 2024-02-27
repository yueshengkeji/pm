package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ContractWordModelParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ContractWordModelParamsMapper
 * @Description
 * @Author ssk
 * @Date 2024/2/1 0001 11:01
 */
@Mapper
public interface ContractWordModelParamsMapper {
    int insert(ContractWordModelParams contractWordModelParams);
    int update(ContractWordModelParams contractWordModelParams);
    List<ContractWordModelParams> list();
    int delete(int id);
    List<ContractWordModelParams> listByIds(List<Integer> ids);
}
