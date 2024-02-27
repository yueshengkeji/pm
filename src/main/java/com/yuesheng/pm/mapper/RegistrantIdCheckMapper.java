package com.yuesheng.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistrantIdCheckMapper {

    int getStatus();

    int updateStatus(int status);

    int getStatusCollection();

    int updateStatusCollection(int status);
}
