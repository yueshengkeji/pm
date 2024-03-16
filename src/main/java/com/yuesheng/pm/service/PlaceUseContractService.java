package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.PlaceUseContract;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PlaceUseContractService
 * @Description
 * @Author ssk
 * @Date 2024/3/11 0011 9:33
 */
public interface PlaceUseContractService {
    int insert(PlaceUseContract placeUseContract);

    int update(PlaceUseContract placeUseContract);

    int delete(String id);

    List<PlaceUseContract> list(Map<String,Object> params);

    PlaceUseContract selectById(String id);
}
