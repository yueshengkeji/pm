package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.AdvertPlaceContract;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AdvertPlaceContractService
 * @Description
 * @Author ssk
 * @Date 2024/3/14 0014 9:07
 */
public interface AdvertPlaceContractService {

    int insert(AdvertPlaceContract advertPlaceContract);

    int update(AdvertPlaceContract advertPlaceContract);

    int delete(String id);

    List<AdvertPlaceContract> list(Map<String,Object> params);

    AdvertPlaceContract selectById(String id);
}
