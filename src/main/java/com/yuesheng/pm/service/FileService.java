package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Attach;

import java.util.List;

public interface FileService {
    /**
     * 获取附件列表
     *
     * @param moduleId 模块id
     * @return
     */
    List<Attach> getByService(String moduleId);
}
