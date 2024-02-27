package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.mapper.RegistrantIdCheckMapper;
import com.yuesheng.pm.service.RegistrantIdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RegistrantIdCheckService")
public class RegistrantIdCheckServiceImpl implements RegistrantIdCheckService {
    @Autowired
    private RegistrantIdCheckMapper registrantIdCheckMapper;

    @Override
    public int getStatus() {
        return registrantIdCheckMapper.getStatus();
    }

    @Override
    public int updateStatus(int status) {
        return registrantIdCheckMapper.updateStatus(status);
    }

    @Override
    public int getStatusCollection() {
        return registrantIdCheckMapper.getStatusCollection();
    }

    @Override
    public int updateStatusCollection(int status) {
        return registrantIdCheckMapper.updateStatusCollection(status);
    }
}
