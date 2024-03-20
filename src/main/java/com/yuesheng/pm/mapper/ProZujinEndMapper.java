package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.ProZujinEnd;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProZujinEndMapper
 * @Description
 * @Author ssk
 * @Date 2024/3/16 0016 8:37
 */
@Mapper
public interface ProZujinEndMapper {
    int insert(ProZujinEnd proZujinEnd);

    int update(ProZujinEnd proZujinEnd);

    int delete(String id);

    List<ProZujinEnd> list(Map<String,Object> params);

    ProZujinEnd selectById(String id);
}
