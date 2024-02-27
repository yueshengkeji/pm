package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.entity.Staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (ProZujin)表服务接口
 *
 * @author xiaoSong
 * @since 2021-07-07 13:52:28
 */
public interface ProZujinService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProZujin queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProZujin> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proZujin 实例对象
     * @return 实例对象
     */
    ProZujin insert(ProZujin proZujin, Staff staff);

    /**
     * 修改数据
     *
     * @param proZujin 实例对象
     * @return 实例对象
     */
    ProZujin update(ProZujin proZujin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<ProZujin> queryByParam(HashMap<String, Object> params);

    Integer queryCountByParam(HashMap<String, Object> params);

    /**
     * 更新租金统计信息
     *
     * @param proZujin
     * @return
     */
    int updateMoneyCount(ProZujin proZujin);

    /**
     * 修改保证金合计
     *
     */
    int updateBzj(ProZujin zujin);

    /**
     * 查询统计信息
     *
     * @return
     */
    Map<String, Object> queryMoneyTotal(String year);

    /**
     * 查询年度欠费金额
     *
     * @param year 年
     * @return
     */
    Double queryEarlyMoney(String year);

    Map<String, Object> queryMoneyTotal(String year, Integer type);

    /**
     * 根据合同编号查询应收账单
     *
     * @param contractSeries 合同编号
     * @return
     */
    ProZujin queryBySeries(String contractSeries);

    /**
     * 根据合同编号查询应付款账单
     *
     * @param contractSeries 合同编号
     * @return
     */
    ProZujin queryByPay(String contractSeries);

    /**
     * 搜索品牌
     * @param searchText
     * @return
     */
    List<ProZujin> queryBrand(String searchText);

    /**
     * 查询已过期，未更新过期状态的合同
     */
    int check();
}