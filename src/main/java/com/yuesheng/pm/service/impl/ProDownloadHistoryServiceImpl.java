package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProDownloadHistory;
import com.yuesheng.pm.mapper.ProDownloadHistoryMapper;
import com.yuesheng.pm.service.ProDownloadHistoryService;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProDownloadHistoryServiceImpl implements ProDownloadHistoryService {
    @Autowired
    private ProDownloadHistoryMapper proDownloadHistoryMapper;

    @Override
    public int insert(ProDownloadHistory history) {
        if (history.getProcurement() == null || StringUtils.isBlank(history.getProcurement().getId())) {
            return -1;
        } else if (history.getStaff() == null || StringUtils.isBlank(history.getStaff().getId())) {
            return -2;
        } else if (StringUtils.isBlank(history.getDateTime())) {
            history.setDateTime(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL));
        }
        if (StringUtils.isBlank(history.getId())) {
            history.setId(UUID.randomUUID().toString());
        }
        if (queryByProId(history.getProcurement().getId()).size() > 0) {
            return -3;
        }
        return proDownloadHistoryMapper.insert(history);
    }

    @Override
    public int delete(String proId) {
        return proDownloadHistoryMapper.delete(proId);
    }

    @Override
    public List<ProDownloadHistory> queryByProId(String proId) {
        return proDownloadHistoryMapper.queryByProId(proId);
    }

    @Override
    public int update(ProDownloadHistory history) {
        return proDownloadHistoryMapper.update(history);
    }
}
