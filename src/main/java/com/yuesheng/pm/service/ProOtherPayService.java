package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProOtherPay;

import java.util.List;

/**
 * (ProOtherPay)表服务接口
 *
 * @author xiaoSong
 * @since 2022-10-19 10:56:02
 */
public interface ProOtherPayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProOtherPay queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ProOtherPay> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param proOtherPay 实例对象
     * @return 实例对象
     */
    ProOtherPay insert(ProOtherPay proOtherPay);

    /**
     * 修改数据
     *
     * @param proOtherPay 实例对象
     * @return 实例对象
     */
    ProOtherPay update(ProOtherPay proOtherPay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询所有付款
     *
     * @param proOtherPay
     * @return
     */
    List<ProOtherPay> queryAll(ProOtherPay proOtherPay);

    /**
     * 查询付款金额合计
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @param tagName   标签名称
     * @param state     审核状态
     * @return
     */
    Double getSumMoney(String startDate, String endDate, String tagName, Integer state);

    void approve(String id);

    /**
     * 查询未支付的单据列表
     * @param query
     * @return
     */
    List<ProOtherPay> queryNoPay(ProOtherPay query);

    /**
     * 审核数据
     * @param item
     */
    void approve(ProOtherPay item);

    /**
     * 同步没有支付时间的数据
     */
    void syncNoPay();

    /**
     * 通知申请人审批进度
     * @param id
     */
    void notifyApplyStaff(String id);
}