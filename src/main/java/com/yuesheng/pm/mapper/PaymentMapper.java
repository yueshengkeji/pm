package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Payment;
import com.yuesheng.pm.entity.PaymentType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/27 合同付款mapper.
 * @author XiaoSong
 * @date 2016/12/27
 *
 */
@Mapper
public interface PaymentMapper {
    /**
     * 获取付款单+付款明细集合
     * @param id 付款单id
     * @return
     */
    Payment getDetailById(@Param("id") String id);

    /**
     * 获取付款单基本信息
     * @param id 付款单id
     * @return
     */
    Payment getPaymentById(@Param("id") String id);

    /**
     * 获取合同付款集合
     * @param params {company:单位id,start:开始时间，end:结束时间}
     * @return
     */
    List<Payment> getPaymentByCompany(Map<String,String> params);
    /**
     * 获取合同款项集合
     * @return
     */
    List<PaymentType> getTypes();

    /**
     * 获取合同付款类型对象
     * @param id 合同付款类型id sdpd053 主键
     * @return
     */
    PaymentType getPaymentType(@Param("id") String id);

    /**
     * 添加实际付款单主表
     * @param payment 付款单主对象
     */
    void addRealityPay(Payment payment);
    /**
     * 审核实际付款单
     * @param params {realityId:实际付款单id,state:状态：2=通过，3=不通过，staffId:审批人员id,date:审批时间}
     */
    void approve(Map<String, Object> params);

    /**
     * 添加合同付款单
     * @param payment
     */
    void addPayment(Payment payment);

    /**
     * 更新付库单信息
     * @param payment
     * @return
     */
    int updatePayment(Payment payment);

    /**
     * 获取付款单集合
     * @param params 查询参数
     * @return
     */
    List<Payment> getPaymentList(Map<String, Object> params);

    /**
     * 获取付款单总数
     * @param params
     * @return
     */
    int getPaymentTotal(Map<String, Object> params);

    /**
     * 获取合同付款集合
     * @param contractId 合同主键
     * @return
     */
    List<Payment> getPaymentByContract(String contractId);

    /**
     * 删除合同付款单
     *
     * @param id 付款单id
     */
    void delete(String id);

    /**
     * 根据编号获取付款单
     *
     * @param series
     * @return
     */
    Payment getPaymentBySeries(@Param("series") String series);

    /**
     * 更新合同付款打印时间
     *
     * @param id       合同付款id
     * @param datetime 打印时间
     * @return
     */
    int updatePrintDate(@Param("id") String id, @Param("datetime") String datetime);

    /**
     * 更新审批状态
     * @param payment
     * @return
     */
    int updateApprove(Payment payment);
}
