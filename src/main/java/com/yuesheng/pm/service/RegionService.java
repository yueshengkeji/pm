package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Region;

import java.util.List;

/**
 * Created by 96339 on 2016/11/19 地区服务.
 *
 */
public interface RegionService {
    /**
     * 获取所有地区
     *
     * @return 地区集合，最多100条
     */
    List<Region> getRegions(String name);

    /**
     * 通过地区id获取地区对象
     * @param id
     * @return
     */
    Region getCityById(String id);

    /**
     * 添加地区
     * @param city
     */
    void insert(Region city);

    /**
     * 修改地区信息
     *
     * @param region
     */
    int update(Region region);

    void convert();
}
