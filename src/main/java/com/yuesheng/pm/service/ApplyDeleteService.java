package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ApplyDelete;

import java.util.List;

/**
 * Created by Administrator on 2019-11-25 删除订单记录.
 */
public interface ApplyDeleteService {
    /**
     * 申请删除订单
     *
     * @param applyDetele
     */
    void applyDelete(ApplyDelete applyDetele);

    /**
     * 更新删除订单状态
     *
     * @param applyDelete
     */
    void updateState(ApplyDelete applyDelete);

    /**
     * 获取所有申请删除中的订单集合
     *
     * @return
     */
    List<ApplyDelete> queryAll();

    /**
     * 查询申请删除订单对象
     *
     * @param ad
     * @return
     */
    ApplyDelete queryByParam(ApplyDelete ad);

    /**
     * 删除申请信息
     *
     * @param proId 订单id
     * @return
     */
    int delete(String proId);
}
