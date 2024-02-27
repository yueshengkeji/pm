package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.CompanyHeader;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/5/26 第三方单位资金往来信息管理服务.
 */
public interface CompanyHeaderService {
    /**
     * 添加单位资金往来信息
     * @param ch
     */
    void insert(CompanyHeader ch);

    /**
     * 更新单位资金往来信息
     * @param ch
     * @return
     */
    int update(CompanyHeader ch);

    /**
     * 删除单个单位资金往来信息，主键
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 根据单位id，删除所有资金往来信息
     * @param companyId
     * @return
     */
    int deleteByCompany(String companyId);

    /**
     * 查询所有单位资金往来信息
     * @return
     */
    List<CompanyHeader> queryCompanys();

    /**
     * 查询单位资金往来信息登记记录
     * @param params {start:开始时间，end=结束时间}
     * @return
     */
    List<CompanyHeader> queryHistory(Map<String,String> params);
}
