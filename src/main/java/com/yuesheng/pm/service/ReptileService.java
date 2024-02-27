package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Reptile;

import java.util.List;

public interface ReptileService {
    /**
     * 开启系统爬虫任务
     */
    void startTask();

    /**
     * 添加
     * @param reptile
     * @return
     */
    Reptile insert(Reptile reptile);

    /**
     * 查询
     * @param id
     * @return
     */
    Reptile selectById(String id);

    /**
     * 通过条件查询
     * @param reptile
     * @return
     */
    List<Reptile> selectByParam(Reptile reptile);

    void notifyStaff();

    void notifyStaffV2();

    void notifyStaffByApp();
}
