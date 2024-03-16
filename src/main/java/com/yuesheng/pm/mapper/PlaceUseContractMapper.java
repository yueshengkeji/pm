package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.PlaceUseContract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PlaceUseContractMapper
 * @Description
 * @Author ssk
 * @Date 2024/3/11 0011 9:32
 */
@Mapper
public interface PlaceUseContractMapper {

    int insert(PlaceUseContract placeUseContract);

    int update(PlaceUseContract placeUseContract);

    int delete(String id);

    List<PlaceUseContract> list(Map<String,Object> params);

    PlaceUseContract selectById(String id);
}
