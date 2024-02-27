package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.DownloadRecord;
import com.yuesheng.pm.mapper.DownloadRecordMapper;
import com.yuesheng.pm.service.DownloadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gui_lin
 * @Description 证书下载记录实现
 * 2022/1/15
 */
@Service("DownloadRecordService")
public class DownloadRecordServiceImpl implements DownloadRecordService {
    @Autowired
    DownloadRecordMapper downloadRecordMapper;

    @Override
    public List<DownloadRecord> selectAllDownloadRecord(String str) {
        return downloadRecordMapper.selectAllDownloadRecord(str);
    }

    @Override
    public void insertDownloadRecord(DownloadRecord downloadRecord) {
        downloadRecordMapper.insertDownloadRecord(downloadRecord);
    }

    @Override
    public List<DownloadRecord> selectDownloadRecordByCerId(Long id) {
        return downloadRecordMapper.selectDownloadRecordByCerId(id);
    }
}
