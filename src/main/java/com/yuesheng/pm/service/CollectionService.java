package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.Collection;

import java.util.List;
import java.util.Map;

/**
 * 合同收款登记
 * @author ssk
 * @since 2021-12-17
 */
public interface CollectionService {
    /**
     * 日查询
     * @param dateSearch
     * @return
     */
    List<Collection> selectByDay(String dateSearch);

    /**
     * 月查询
     * @param dateSearch
     * @return
     */
    List<Collection> selectByDate(String dateSearch);

    /**
     * 通过pMark查询
     * @param pMark
     * @return
     */
    Collection selectByMark(String pMark);

    /**
     * 查询合同下的收款记录
     * @param agreementID
     * @return
     */
    List<Collection> selectByAgreementID(String agreementID);

    /**
     * 插入合同收款登记
     * @param collection
     * @return
     */
    int insertCollection(Collection collection);

    /**
     * 删除合同收款登记
     * @param ID
     * @return
     */
    int deleteCollection(Integer ID);

    /**
     * 删除已删除合同下收款记录
     * @param agreementID
     * @return
     */
    int deleteCollectionByAgreementID(String agreementID);

    /**
     * 根据预收款标记删除
     * @param pMark
     * @return
     */
    int deleteCollectionByPMark(String pMark);

    /**
     * 更新合同收款登记信息
     *
     * @param collection
     * @return
     */
    int updateCollection(Collection collection);

    /**
     * 通过id获取收款信息
     *
     * @param id 收款id
     * @return
     */
    Collection selectById(Integer id);

    /**
     * 更新预收款日期
     *
     * @param collection
     * @return
     */
    Integer updateDate(Collection collection);

    /**
     * 查询(当日预收款到期/已过期)并且未确认收款的数据
     */
    List<Collection> selectToDay();

    /**
     * 通知到期未收款的数据到企业微信
     *
     * @return
     */
    boolean expireNotify();

    /**
     * 查询所有
     * @return
     */
    List<Collection> selectAll();

    /**
     * 搜索查询
     * @param str
     * @return
     */
    List<Collection> selectBySearch(String str,String staffId);

    /**
     * 根据合同id和收款状态查询
     * @param agreementId
     * @param status
     * @return
     */
    List<Collection> selectByAgreementIDAndStatus(String agreementId,String status);

    /**
     * 根据预收款日期查询
     * @param dateSearch
     * @return
     */
    List<Collection> selectByPDate(String dateSearch);

    /**
     * 通过bill标记查询
     * @param billMark
     * @return
     */
    Collection selectByBillMark(String billMark);

    List<Collection> collectList(Map<String,Object> params);
}
