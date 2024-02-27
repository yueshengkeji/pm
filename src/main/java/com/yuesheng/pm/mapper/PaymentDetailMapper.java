package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.PaymentDetail;
import com.yuesheng.pm.entity.ProDetailDP;
import com.yuesheng.pm.entity.RealityPay;
import com.yuesheng.pm.model.PaymentData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/27 合同付款明细对象 sdpd017.
 *
 * @author XiaoSong
 * @date 2016/12/27
 */
@Mapper
public interface PaymentDetailMapper {
    /**
     * 获取合同付款明细集合
     *
     * @param payId 合同付款单id
     * @return
     */
    List<PaymentDetail> getDetailByPayId(@Param("payId") String payId);

    /**
     * 获取合同付款明细集合
     *
     * @param params {company:单位id,start:开始时间，end:结束时间}
     * @return 付款明细集合
     */
    List<PaymentDetail> getDetailByCompany(Map<String, Object> params);

    /**
     * 获取合同付款明细集合
     *
     * @param contractId 合同id
     * @return
     */
    List<PaymentDetail> getDetailByContract(@Param("contractId") String contractId);


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
     * 添加付款明细
     *
     * @param detail
     */
    void addDetail(PaymentDetail detail);

    /**
     * 获取付款明细对象
     *
     * @param id 主键
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
     * 获取合同付款金额总数
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
    List<ProDetailDP> getProDetailDp(@Param("companyId") String companyId, @Param("projectId") String projectId);

    /**
     * 获取付款金额统计数据
     *
     * @param param {start：开始日期,end：截止日期}
     * @return
     */
    List<PaymentData> getPaymentMoney(Map<String, Object> param);

    /**
     * 获取申请中的付款金额合计
     *
     * @param year 年/月/日（可选）
     * @return
     */
    PaymentData getApplyPaymentMoney(@Param("year") String year);

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
    List<PaymentDetail> getPayDetailByDate(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("type") Integer type);

    /**
     * 查询申请中付款金额合计
     * @param contractId 合同id
     * @return
     */
    Double getApplyPaymentMoneyByContract(@Param("contractId") String contractId);

    /**
     * 更新审批信息
     * @param pd
     * @return
     */
    int approve(PaymentDetail pd);

    /**
     * 查询合同付款次数
     * @param contractId
     * @return
     */
    Integer getContractPayCount(@Param("contract") String contractId);

    /**
     * 删除合同付款明细
     * @param paymentId
     * @return
     */
    int deleteByPayment(@Param("paymentId") String paymentId);
}
