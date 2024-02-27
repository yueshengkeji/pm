package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.CompanyExtra;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName CompanyExtraMapper
 * @Description
 * @Author ssk
 * @Date 2022/6/20 0020 9:45
 */
@Mapper
public interface CompanyExtraMapper {

    int insert(CompanyExtra companyExtra);

    int update(CompanyExtra companyExtra);
}
