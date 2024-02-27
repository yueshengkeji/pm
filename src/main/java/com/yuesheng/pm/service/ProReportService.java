package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProReport;
import com.yuesheng.pm.entity.Procurement;

import java.util.List;
import java.util.Map;

/**
 * 采购订单报表记录(ProReport)表服务接口
 *
 * @author xiaoSong
 * @since 2021-06-24 09:44:26
 */
public interface ProReportService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProReport queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProReport> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proReport 实例对象
     * @return 实例对象
     */
    ProReport insert(ProReport proReport);

    /**
     * 修改数据
     *
     * @param proReport 实例对象
     * @return 实例对象
     */
    ProReport update(ProReport proReport);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据条件查询所有报表数据
     *
     * @param param
     * @return
     */
    List<ProReport> queryAll(Map<String, Object> param);

    int deleteByProId(String proId);

    /**
     * 根据条件查询数据总数
     *
     * @param param
     * @return
     */
    int queryAllCount(Map<String, Object> param);

    /**
     * 根据订单id生成报表
     *
     * @param proId
     */
    void genReport(String proId);

    /**
     * 根据订单对象生成报表
     *
     * @param p
     */
    void genByProcurement(Procurement p);

    /**
     * 根据时间删除不存在的数据
     *
     * @param startDate 开始时间
     * @param endDate   截止时间
     * @return
     */
    Integer deleteByDate(String startDate, String endDate);

    List<ProReport> queryByProGroup(Map<String, Object> params);

    /**
     * 同步采购订单数据（前一天到今天的数据）
     */
    void syncProcurementData();
}