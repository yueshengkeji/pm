package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDownloadHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购订单下载记录服务
 */
public interface ProDownloadHistoryService {
    /**
     * 添加下载记录
     *
     * @param history
     * @return
     */
    int insert(ProDownloadHistory history);

    /**
     * 删除下载记录
     *
     * @param proId 采购订单id
     * @return
     */
    int delete(@Param("proId") String proId);

    /**
     * 查询下载记录集合
     *
     * @param proId 采购订单id
     * @return
     */
    List<ProDownloadHistory> queryByProId(@Param("proId") String proId);

    /**
     * 更新状态
     *
     * @param history
     * @return
     */
    int update(ProDownloadHistory history);
}
