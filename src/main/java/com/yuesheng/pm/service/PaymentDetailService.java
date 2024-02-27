package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.PaymentDetail;
import com.yuesheng.pm.entity.ProDetailDP;
import com.yuesheng.pm.entity.RealityPay;
import com.yuesheng.pm.model.PaymentData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/27 合同付款明细服务.
 */
public interface PaymentDetailService {
    /**
     * 获取合同付款明细集合
     *
     * @param payId 合同付款单id
     * @return
     */
    List<PaymentDetail> getDetailByPayId(String payId);

    /**
     * 获取合同付款明细集合
     *
     * @param companyId       单位id,
     * @param startDate:开始时间，
     * @param endDate         结束时间
     * @return 付款明细集合
     */
    List<PaymentDetail> getDetailByCompany(String companyId, String startDate, String endDate);

    /**
     * 获取合同付款明细集合
     *
     * @param contractId 合同id
     * @return
     */
    List<PaymentDetail> getDetailByContract(String contractId);

    /**
     * 添加实际付款合同明细
     *
     * @param realityPay
     */
    void addRealityDetail(RealityPay realityPay);

    /**
     * 更新合同付款明细实际付款金额
     *
     * @param detail
     */
    void updatePayMoney(PaymentDetail detail);

    /**
     * 添加合同付款明细
     *
     * @param detail
     */
    void addDetail(PaymentDetail detail);

    /**
     * 获取付款明细
     *
     * @param id 明细id
     * @return
     */
    PaymentDetail getDetailById(String id);

    /**
     * 获取已付款金额（根据已审核的付款单明细查询，pd01704=实际付款金额为准）
     *
     * @param result {start:开始日期，end:截至日期}
     * @return
     */
    Double getPayMoneyByDate(Map<String, Object> result);

    /**
     * 获取合同已付款金额
     *
     * @param id 合同id
     * @return
     */
    Double getPayMoneyByContract(String id);

    /**
     * 根据项目id和单位id，获取采购付款（已审核）的数据集合
     *
     * @param companyId 单位id
     * @param projectId 项目id
     * @return
     */
    List<ProDetailDP> getProDetailDp(String companyId, String projectId);

    /**
     * 获取付款金额统计数据
     *
     * @param param {start：开始日期,end：截止日期}
     * @return
     */
    List<PaymentData> getPaymentMoney(Map<String, Object> param);

    /**
     * 获取申请付款中金额统计
     *
     * @param year 年份
     * @return
     */
    PaymentData getApplyPaymentMoney(String year);

    /**
     * 查询
     * @param startTime 开始日期
     * @param endTime 截止日期
     * @param approveState 审核状态
     * @return
     */
    List<PaymentData> getDetailByCompanyGroup(@Param("startTime") String startTime,
                                              @Param("endTime") String endTime,
                                              @Param("approveState") Integer approveState);

    /**
     * 根据单位id查询付款合计
     *
     * @param companyId    单位id
     * @param startTime    开始日期
     * @param endTime      截止日期
     * @param approveState 审核状态
     * @return
     */
    Double getPayMoneyByCompany(@Param("companyId") String companyId,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime,
                                @Param("approveState") Integer approveState);

    /**
     * 根据日期查询付款明细
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @param type      类型：0=未审核，1=已审核
     * @return
     */
    List<PaymentDetail> getPayDetailByDate(String startDate, String endDate, Integer type);

    /**
     * 查询申请中付款金额合计
     * @param contractId 合同id
     * @return
     */
    Double getApplyPaymentMoneyByContract(String contractId);

    /**
     * 更新审批信息
     * @param pd
     * @return
     */
    int approve(PaymentDetail pd);

    /**
     * 删除合同付款明细
     * @param paymentId
     * @return
     */
    int deleteByPayment(String paymentId);
}
