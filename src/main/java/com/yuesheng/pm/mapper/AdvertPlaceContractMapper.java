package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.AdvertPlaceContract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AdvertPlaceContractMapper
 * @Description
 * @Author ssk
 * @Date 2024/3/14 0014 9:02
 */
@Mapper
public interface AdvertPlaceContractMapper {

    int insert(AdvertPlaceContract advertPlaceContract);

    int update(AdvertPlaceContract advertPlaceContract);

    int delete(String id);

    List<AdvertPlaceContract> list(Map<String,Object> params);

    AdvertPlaceContract selectById(String id);
}
