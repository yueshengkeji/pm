package com.yuesheng.pm.service;

public interface RegistrantIdCheckService {
    int getStatus();
    int updateStatus(int status);
    int getStatusCollection();
    int updateStatusCollection(int status);
}
