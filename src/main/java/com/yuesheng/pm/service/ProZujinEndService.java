package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProZujinEnd;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ProZujinEndService
 * @Description
 * @Author ssk
 * @Date 2024/3/16 0016 8:40
 */
public interface ProZujinEndService {

    int insert(ProZujinEnd proZujinEnd);

    int update(ProZujinEnd proZujinEnd);

    int delete(String id);

    List<ProZujinEnd> list(Map<String,Object> params);

    ProZujinEnd selectById(String id);
}
