package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.HouseBillEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoSong
 * @date 2020/4/15
 * 存量资产服务接口
 */
public interface HouseBillService {

    /**
     * 添加存量经营数据
     *
     * @param houseBillEntity
     */
    int insert(HouseBillEntity houseBillEntity);

    /**
     * 更新存量资产数据
     *
     * @param houseBillEntity
     * @return
     */
    int update(HouseBillEntity houseBillEntity);

    /**
     * 删除存量资产数据
     *
     * @param id 主键
     * @return
     */
    int delete(String id);

    /**
     * 查询存量资产数据集合
     *
     * @param params     {start:开始时间，end:截止时间，str:检索字符串}
     * @return
     */
    List<HouseBillEntity> queryList(Map<String, Object> params,Integer pageNum,Integer pageSize );

    /**
     * 查询存量资产数据总量
     *
     * @param id 主键
     * @return
     */
    HouseBillEntity queryById(String id);

    /**
     * 查询存量资产数据集合
     *
     * @param params {start:开始时间，end:截止时间，str:检索字符串}
     * @return 数据总量
     */
    int queryListCount(HashMap<String, Object> params);
}
