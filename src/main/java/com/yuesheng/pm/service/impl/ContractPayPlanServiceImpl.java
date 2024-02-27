package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ContractPayPlanMapper;
import com.yuesheng.pm.service.ContractPayPlanService;
import com.yuesheng.pm.service.ContractService;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.PaymentService;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiaoSong
 * @date 2020/3/19
 */
@Service("contractPayPlanService")
public class ContractPayPlanServiceImpl implements ContractPayPlanService {
    @Autowired
    private ContractPayPlanMapper contractPayPlanMapper;
    @Autowired
    @Lazy
    private ContractService contractService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private FlowNotifyService flowNotifyService;

    @Override
    public void insert(ContractPayPlan plan) {
        contractPayPlanMapper.insert(plan);
    }

    @Override
    public void updateState(ContractPayPlan plan) {
        contractPayPlanMapper.updateState(plan);
    }

    @Override
    public ContractPayPlan queryByContractId(String contractId) {
        PageHelper.startPage(1,1);
        return contractPayPlanMapper.queryByContractId(contractId);
    }

    @Override
    public void deleteByContract(String contractId) {
        contractPayPlanMapper.deleteByContractId(contractId);
    }

    @Override
    public void startContractPay(String frameId) {

        ContractPayPlan payPlan = queryByContractId(frameId);
        Contract contract = contractService.getContractById(frameId);
        if (payPlan != null && payPlan.getPayMoney() > 0) {
            Payment payment = new Payment();
            payment.setSeries(contract.getPartyB().getName() + "-付款申请：" + payPlan.getPayMoney());
            payment.setCompany(contract.getPartyB());
            payment.setMoneys(payPlan.getPayMoney());
            payment.setPayDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            payment.setStaff(contract.getStaff());
            payment.setRemark(payPlan.getRemark() == null ? "" : payPlan.getRemark());
            payment.setId(UUID.randomUUID().toString());

            PaymentDetail pd = new PaymentDetail();
            pd.setApplyMoney(payPlan.getPayMoney());
            pd.setApplyDate(payment.getPayDate());
            pd.setCompany(payment.getCompany());
            pd.setContract(contract);
            pd.setContractId(contract.getId());
            pd.setyMoney(0.0);
            pd.setRate((float) 1.0);
            try {
                pd.setProjectId(contract.getProjects().get(0).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            pd.setRemark("");
//          付款类型（性质）
            PaymentType pt = new PaymentType();
            pt.setId(payPlan.getPayTypeId());
            pd.setPaymentType(pt);

            ArrayList<PaymentDetail> pds = new ArrayList<>();
            pds.add(pd);
            payment.setDetails(pds);

//            添加付款申请，自动发起审批
            paymentService.addPayment(payment, new String[0], true, payPlan.getFlowId());
//            修改预付款申请状态
            payPlan.setState(true);
            updateState(payPlan);
        }
        //通知起草人，合同审批完成
        ArrayList<Staff> arrayList = new ArrayList();
        arrayList.add(contract.getStaff());
        Map<String, Object> msgMap = new HashMap();
        msgMap.put("title", "合同审批完成");
        msgMap.put("mTitle", contract.getName());
        msgMap.put("content", "审批通过");
        msgMap.put("url", WebParam.VUETIFY_BASE + "/purchaseContractForKaiLi/list/contractList");
        flowNotifyService.msgNotify(arrayList, msgMap);

    }
}
