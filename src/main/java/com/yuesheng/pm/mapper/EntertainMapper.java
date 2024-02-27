package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Entertain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName EntertainMapper
 * @Description
 * @Author ssk
 * @Date 2023/5/6 0006 10:03
 */
@Mapper
public interface EntertainMapper {
    int insert(Entertain entertain);

    int update(Entertain entertain);

    int delete(String id);

    List<Entertain> list(Map<String,Object> params);

    int updateState(@Param("state") int state, @Param("id") String id);

    Entertain selectById(String id);
}
