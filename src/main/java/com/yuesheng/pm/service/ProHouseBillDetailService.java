package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.HouseBillDetail;

import java.util.List;
import java.util.Map;

/**
 * (ProHouseBillDetail)表服务接口
 *
 * @author makejava
 * @since 2020-04-17 11:27:41
 */
public interface ProHouseBillDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    HouseBillDetail queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<HouseBillDetail> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param houseBillDetail 实例对象
     * @return 实例对象
     */
    HouseBillDetail insert(HouseBillDetail houseBillDetail);

    /**
     * 修改数据
     *
     * @param houseBillDetail 实例对象
     * @return 实例对象
     */
    HouseBillDetail update(HouseBillDetail houseBillDetail);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 获取明细集合
     *
     * @param mainId 主单据id
     * @return 数据集合
     */
    List<HouseBillDetail> queryByMain(String mainId);

    /**
     * 查询主单据已收金额合计
     *
     * @param mainId 主单据id
     * @return
     */
    Double queryMoneyByMainId(String mainId);

    /**
     * 作废明细
     *
     * @param id 明细主键
     * @return
     */
    int destroy(String id);

    /**
     * 获取金额合计
     *
     * @param result 指定年份和月份（月份可选）
     * @return
     */
    Double getMoneyByYear(Map<String, Object> result);

    void approve(FlowMessage msg);
}