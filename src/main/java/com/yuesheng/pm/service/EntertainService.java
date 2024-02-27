package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Entertain;

import java.util.List;
import java.util.Map;

/**
 * @ClassName EntertainService
 * @Description
 * @Author ssk
 * @Date 2023/5/6 0006 9:51
 */
public interface EntertainService {
    int insert(Entertain entertain);

    int update(Entertain entertain);

    int delete(String id);

    List<Entertain> list(Map<String,Object> params);

    int updateState(int state,String id);

    Entertain selectById(String id);
}
