package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.PaymentDetail;
import com.yuesheng.pm.entity.ProDetailDP;
import com.yuesheng.pm.entity.RealityPay;
import com.yuesheng.pm.mapper.PaymentDetailMapper;
import com.yuesheng.pm.model.PaymentData;
import com.yuesheng.pm.service.ContractService;
import com.yuesheng.pm.service.PaymentDetailService;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 96339 on 2016/12/27.
 */
@Service("paymentDetailService")
public class PaymentDetailServiceImpl implements PaymentDetailService {
    @Autowired
    private PaymentDetailMapper paymentDetailMapper;
    @Autowired
    @Lazy
    private ContractService contractService;

    @Override
    public List<PaymentDetail> getDetailByPayId(String payId) {
        return paymentDetailMapper.getDetailByPayId(payId);
    }

    @Override
    public List<PaymentDetail> getDetailByCompany(String companyId, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("company", companyId);
        params.put("start", startDate);
        params.put("end", endDate);
        return paymentDetailMapper.getDetailByCompany(params);
    }


    @Override
    public List<PaymentDetail> getDetailByContract(String contractId) {
        return paymentDetailMapper.getDetailByContract(contractId);
    }

    @Override
    public void addRealityDetail(RealityPay realityPay) {
        paymentDetailMapper.addRealityDetail(realityPay);
    }

    @Override
    public void updatePayMoney(PaymentDetail detail) {
        paymentDetailMapper.updatePayMoney(detail);
    }

    @Override
    public void addDetail(PaymentDetail detail) {
        String cid = "";
        if (Objects.isNull(detail.getContract())) {
            cid = detail.getContractId();
        } else {
            cid = detail.getContract().getId();
        }
        Integer count = paymentDetailMapper.getContractPayCount(cid);
        if (Objects.isNull(count)) {
            count = 1;
        } else {
            count += 1;
        }
        detail.setSeries("PD_" + DateUtil.format(new Date(), DateUtil.PATTERN_IMAGE_NAME));
        detail.setPayCount(count);
        paymentDetailMapper.addDetail(detail);
        contractService.setApplyMoney(cid);
    }

    @Override
    public PaymentDetail getDetailById(String id) {
        return paymentDetailMapper.getDetailById(id);
    }

    @Override
    public Double getPayMoneyByDate(Map<String, Object> result) {
        return paymentDetailMapper.getPayMoneyByDate(result);
    }

    @Override
    public Double getPayMoneyByContract(String id) {
        return paymentDetailMapper.getPayMoneyByContract(id);
    }

    @Override
    public List<ProDetailDP> getProDetailDp(String companyId, String projectId) {
        return paymentDetailMapper.getProDetailDp(companyId, projectId);
    }

    @Override
    public List<PaymentData> getPaymentMoney(Map<String, Object> param) {
        return paymentDetailMapper.getPaymentMoney(param);
    }

    @Override
    public PaymentData getApplyPaymentMoney(String year) {
        return paymentDetailMapper.getApplyPaymentMoney(year);
    }

    @Override
    public List<PaymentData> getDetailByCompanyGroup(String startTime, String endTime, Integer approveState) {
        return paymentDetailMapper.getDetailByCompanyGroup(startTime, endTime, approveState);
    }

    @Override
    public Double getPayMoneyByCompany(String companyId, String startTime, String endTime, Integer approveState) {
        return paymentDetailMapper.getPayMoneyByCompany(companyId, startTime, endTime, approveState);
    }

    @Override
    public List<PaymentDetail> getPayDetailByDate(String startDate, String endDate, Integer type) {
        return paymentDetailMapper.getPayDetailByDate(startDate, endDate, type);
    }

    @Override
    public Double getApplyPaymentMoneyByContract(String contractId) {
        return paymentDetailMapper.getApplyPaymentMoneyByContract(contractId);
    }

    @Override
    public int approve(PaymentDetail pd) {
        return paymentDetailMapper.approve(pd);
    }

    @Override
    public int deleteByPayment(String paymentId) {
        return paymentDetailMapper.deleteByPayment(paymentId);
    }
}
