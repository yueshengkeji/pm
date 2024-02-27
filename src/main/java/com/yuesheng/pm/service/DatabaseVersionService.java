package com.yuesheng.pm.service;

/**
 * 数据库表结构版本自动升级服务
 */
public interface DatabaseVersionService {
    /**
     * 升级服务
     * @return
     */
    public int update();

    /**
     * 查询最新版本号
     * @return
     */
    String queryVersion();
}
