package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.DownloadRecord;

import java.util.List;

/**
 * @author gui_lin
 * @Description 证书下载记录接口
 * 2022/1/15
 */
public interface DownloadRecordService {
    /**
     * 获取全部的下载记录
     * @param str 检索字符串
     * @return
     */
    List<DownloadRecord> selectAllDownloadRecord(String str);

    /**
     * 添加下载记录
     * @param downloadRecord 下载信息
     */
    void insertDownloadRecord(DownloadRecord downloadRecord);

    /**
     * 根据证书id查询下载记录
     * @param id 证书id
     * @return
     */
    List<DownloadRecord> selectDownloadRecordByCerId(Long id);

}
