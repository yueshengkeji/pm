package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FixedApply;
import com.yuesheng.pm.entity.FlowMessage;

import java.util.List;

/**
 * (ProFixedApply)表服务接口
 *
 * @author xiaoSong
 * @since 2022-11-29 09:41:20
 */
public interface FixedApplyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FixedApply queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<FixedApply> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param fixedApply 实例对象
     * @return 实例对象
     */
    FixedApply insert(FixedApply fixedApply);

    /**
     * 修改数据
     *
     * @param fixedApply 实例对象
     * @return 实例对象
     */
    FixedApply update(FixedApply fixedApply);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 根据条件查询数据
     *
     * @param apply
     * @return
     */
    List<FixedApply> queryByParam(FixedApply apply);

    /**
     * 审核固定资产申请表（资产清单会复制到采购申请单中）
     *
     * @param id
     * @param approveStaffCoding 审核人编码
     */
    void approve(String id, String approveStaffCoding);

    /**
     * 检测当天采购订单，是否有采购固定资产信息,如果有采购，更新固定资产采购时间
     */
    void checkProcurementFixed();

    void approve(FlowMessage msg);
}