package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ContractWordModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ContractWordModelMapper
 * @Description
 * @Author ssk
 * @Date 2024/1/31 0031 11:23
 */
@Mapper
public interface ContractWordModelMapper {
    int insert(ContractWordModel contractWordModel);
    int update(ContractWordModel contractWordModel);
    List<ContractWordModel> list();
    int delete(int id);
    ContractWordModel selectByType(int type);
}
