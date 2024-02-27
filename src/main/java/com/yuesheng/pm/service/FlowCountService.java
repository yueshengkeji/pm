package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowCount;
import com.yuesheng.pm.model.DateGroupModel;

import java.util.HashMap;
import java.util.List;

/**
 * 客流统计(FlowCount)表服务接口
 *
 * @author xiaosong
 * @since 2023-08-22 08:47:48
 */
public interface FlowCountService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FlowCount queryById(Integer id);

    /**
     * 分页查询
     *
     * @param flowCount 筛选条件
     * @return 查询结果
     */
    List<FlowCount> queryByPage(FlowCount flowCount);

    /**
     * 新增数据
     *
     * @param flowCount 实例对象
     * @return 实例对象
     */
    FlowCount insert(FlowCount flowCount);

    /**
     * 修改数据
     *
     * @param flowCount 实例对象
     * @return 实例对象
     */
    FlowCount update(FlowCount flowCount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询所有数据
     * @param query
     * @return
     */
    List<FlowCount> queryAll(FlowCount query);

    /**
     * 分组统计客流数据
     * @param param {saleStartDate:开始日期,saleEndDate:截止日期}
     * @return
     */
    List<DateGroupModel> queryMoneyGroupFlowDate(HashMap<String, Object> param);
}
