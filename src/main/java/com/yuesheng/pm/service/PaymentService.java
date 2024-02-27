package com.yuesheng.pm.service;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.Payment;
import com.yuesheng.pm.entity.PaymentType;
import com.yuesheng.pm.entity.Staff;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 96339 on 2016/12/27 合同付款服务.
 *
 */
public interface PaymentService {
    /**
     * 获取付款单+付款明细集合
     *
     * @param id 付款单id
     * @return
     */
    Payment getDetailById(String id);

    /**
     * 获取付款单基本信息
     *
     * @param id 付款单id
     * @return
     */
    Payment getPaymentById(String id);

    /**
     * 获取合同付款集合
     *
     * @param companyId:单位id,
     * @param startDate:开始时间，
     * @param endDate         结束时间
     * @return
     */
    List<Payment> getPaymentByCompany(String companyId, String startDate, String endDate);

    /**
     * 获取合同款项集合
     *
     * @return
     */
    List<PaymentType> getTypes();

    /**
     * 添加实际付款单，并自动审批
     * @param payment
     */
    void addRealityPay(Payment payment, Staff staff);

    /**
     * 审核实际付款单
     * @param realityId 实际付款单id
     * @param state 状态
     * @param staffId 审批人id
     * @param date 审批时间
     */
    void approve(String realityId, int state, String staffId, String date);

    /**
     * 更新合同付款单已付款金额,此更新会直接影响到合同本身的已付款金额
     * @param payment 付款单对象
     */
    void updatePayMoney(Payment payment);

    /**
     * 添加合同付款
     * @param payment
     */
    void addPayment(Payment payment) throws SQLException;

    int updatePayment(Payment payment);

    /**
     * 获取付款单集合
     * @param params {}
     * @return
     */
    List<Payment> getPaymentList(Map<String, Object> params);

    /**
     * 获取付款总数
     * @param params 查询参数
     * @return
     */
    int getPaymentTotal(Map<String, Object> params);

    /**
     * 获取合同付款单集合
     * @param contractId 合同id
     * @return
     */
    List<Payment> getPaymentByContract(String contractId);

    void delete(String id);

    /**
     * 删除合同付款单
     *
     * @param id          付款单主键
     * @param deleteFiles 是否删除合同附件
     */
    void delete(String id, boolean deleteFiles);

    /**
     * 添加合同付款单
     *
     * @param pay      付款对象
     * @param attaches 附件集合
     * @param autoFlow 是否自动发起流程
     * @param flowId   流程id
     * @return
     */
    Payment addPayment(Payment pay, String[] attaches, boolean autoFlow, String flowId);

    void approve(FlowMessage msg);

    /**
     * 根据合同付款id生成已付款金额对账单
     *
     * @param id
     */
    Payment genPayDetail(String id);

    /**
     * 通知发起人合同审批步骤
     */
    void notifyStaff(String id);

    /**
     * 根据编号获取付款单
     *
     * @param series
     * @return
     */
    Payment getPaymentBySeries(String series);

    /**
     * 更新合同付款打印时间
     *
     * @param id       合同付款id
     * @param datetime 打印时间
     * @return
     */
    int updatePrintDate(String id, String datetime);
}
