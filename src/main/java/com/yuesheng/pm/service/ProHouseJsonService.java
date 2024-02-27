package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProHouseJson;
import com.yuesheng.pm.entity.ProZujinHouse;

import java.util.List;

/**
 * (ProHouseJson)表服务接口
 *
 * @author xiaoSong
 * @since 2021-08-19 10:55:26
 */
public interface ProHouseJsonService {

    /**
     * 通过ID查询单条数据
     *
     * @param key 主键
     * @return 实例对象
     */
    ProHouseJson queryById(String key);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProHouseJson> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proHouseJson 实例对象
     * @return 实例对象
     */
    ProHouseJson insert(ProHouseJson proHouseJson);

    /**
     * 修改数据
     *
     * @param proHouseJson 实例对象
     * @return 实例对象
     */
    ProHouseJson bindBrand(ProHouseJson proHouseJson);

    /**
     * 通过主键删除数据
     *
     * @param key 主键
     * @return 是否成功
     */
    boolean deleteById(String key);

    /**
     * 通过商铺信息更新geojson位置图信息
     *
     * @param item
     * @return
     */
    int bindBrand(ProZujinHouse item);

    int clearBrand(ProZujinHouse house);
}