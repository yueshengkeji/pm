package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.PurchaseCollect;

import java.util.List;

public interface PurchaseCollectService {
    /**
     * 日查询
     * @param dateSearch
     * @return
     */
    List<PurchaseCollect> selectByDay(String dateSearch);

    /**
     * 月查询
     * @param dateSearch
     * @return
     */
    List<PurchaseCollect> selectByDate(String dateSearch);

    /**
     * 通过pMark查询
     * @param pMark
     * @return
     */
    PurchaseCollect selectByPMark(String pMark);

    /**
     * 查询合同下的开票记录
     * @param id
     * @return
     */
    List<PurchaseCollect> selectByID(Integer id);

    /**
     * 插入合同开票登记
     * @param purchaseCollect
     * @return
     */
    int insert(PurchaseCollect purchaseCollect);

    /**
     * 删除合同开票登记
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 删除已删除合同下收票记录
     * @return
     */
    int deleteByID(Integer id);

    /**
     * 更新合同开票登记信息
     * @param purchaseCollect
     * @return
     */
    int update(PurchaseCollect purchaseCollect);
}
