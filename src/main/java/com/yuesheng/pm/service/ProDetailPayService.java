package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.ProDetailPay;
import com.yuesheng.pm.entity.Staff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2017/2/14 采购付款明细.
 *
 */
public interface ProDetailPayService {
    /**
     * 添加付款明细对象
     * @param pay
     */
    ProDetailPay addPay(ProDetailPay pay);

    ProDetailPay updateMainPayMoney(String mainId);

    /**
     * 更新付款金额&时间
     * @param pay
     * @return
     */
    int updatePayMoney(ProDetailPay pay);
    /**
     * 更新收钱金额&时间
     */
    int updateBillMoney(ProDetailPay pay);

    /**
     * 删除付款明细对象
     * @param id 主键
     * @return
     */
    int deletePay(String id);

    /**
     * 删除付款明细集合
     * @param mainId 主单据id
     * @return
     */
    int deletePayByMainId(String mainId);

    /**
     * 更新最后修改时间
     * @param date 最后修改时间
     * @param staff 修改人
     * @param id 单据id
     * @return
     */
    int updateLastMsg(String date, Staff staff, String id);

    /**
     * 获取采购付款集合
     * @param mainId 主对象id
     * @return
     */
    List<ProDetailPay> getPayDetails(String mainId);

    /**
     * 获取采购付款单对象
     * @param id 主鍵
     * @return
     */
    ProDetailPay getPay(String id);

    /**
     * 获取已付款&已收票总额
     *
     * @param id 主对象id
     * @return
     */
    Map<String, BigDecimal> getMoneySumByMain(String id);

    Double getPayMoneyByMain(String id);

    /**
     * 删除财务开票/收款数据
     *
     * @param mainId   主单据id
     * @param billDate 开票日期
     * @return
     */
    int deleteBySeries(String mainId,
                       String billDate);

    /**
     * 查询开票信息
     *
     * @param mainId   合同编号
     * @param billDate 开票编号
     * @return
     */
    ProDetailPay getBySeries(String mainId, String billDate);

    /**
     * 通过合同明细查找财务收付款明细
     *
     * @param contractId 合同id
     * @param detailId   明细id
     * @return
     */
    ProDetailPay getByContractDetailId(String contractId, String detailId);

    /**
     * 通过合同id和合同明细id删除
     *
     * @param contractId 合同id
     * @param detailId   合同明细id
     * @return
     */
    int deleteByContractDetailId(String contractId, String detailId);

    /**
     * 查询年度付款金额合计
     * @param year
     * @return
     */
    Double getPayMoney(String year);

    /**
     * 查询年度收票金额合计
     * @param year
     * @return
     */
    Double getBillMoney(String year);

    /**
     * 更新付款和收票金额
     * @param pay
     * @return
     */
    ProDetailPay updatePayAndBillMoney(ProDetailPay pay);
}
