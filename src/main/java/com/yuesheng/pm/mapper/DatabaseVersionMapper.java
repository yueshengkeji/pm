package com.yuesheng.pm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库sql执行mapper
 */
@Mapper
public interface DatabaseVersionMapper {
    /**
     * 执行sql
     *
     * @param sql sql语句
     * @return
     */
    public int execSql(@Param("sql") String sql);

    /**
     * 获取最新更新版本日期
     *
     * @return
     */
    String queryNowVersionDate();

    /**
     * 查询最新版本号
     * @return
     */
    String queryNowVersion();

}
