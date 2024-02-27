package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.DownloadRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author gui_lin
 * @Description 描述
 * 2022/1/15
 */
@Mapper
public interface DownloadRecordMapper {
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
