package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.HouseBillEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoSong
 * @date 2020/4/15
 * 存量经营资产管理mapper
 */
@Mapper
public interface HouseBillMapper {
    /**
     * 添加存量经营数据
     *
     * @param houseBillEntity
     */
    void insert(HouseBillEntity houseBillEntity);

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
     * @param params     {start:开始时间，end:截止时间，str:检索字符串,order:排序类型,dateType:筛选时间类型}
     * @return
     */
    List<HouseBillEntity> queryList(Map<String, Object> params);

    /**
     * 查询存量资产数据
     *
     * @param id 主键
     * @return
     */
    HouseBillEntity queryById(String id);

    /**
     * 查询数据总量
     *
     * @param params 查询参数参见：queryList函数
     * @return 数据总量
     */
    int queryListCount(HashMap<String, Object> params);
}
