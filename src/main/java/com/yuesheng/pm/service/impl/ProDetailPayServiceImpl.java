package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.ProDetailPay;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.ProDetailPayMapper;
import com.yuesheng.pm.service.ProDetailPayService;
import com.yuesheng.pm.service.ProDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 96339 on 2017/2/14.
 */
@Service("proDetailPayService")
public class ProDetailPayServiceImpl implements ProDetailPayService {
    @Autowired
    private ProDetailPayMapper proDetailPayMapper;
    @Autowired
    @Lazy
    private ProDetailService proDetailService;

    @Override
    public ProDetailPay addPay(ProDetailPay pay) {
        proDetailPayMapper.addPay(pay);
        //更新主单据期末欠款
        proDetailService.updateEndOwe(pay.getMainId());
        return updateMainPayMoney(pay.getMainId());
    }

    @Override
    public ProDetailPay updateMainPayMoney(String mainId) {
        Map<String, BigDecimal> money = getMoneySumByMain(mainId);
        if(Objects.isNull(money))
        {
            return null;
        }
        Double payMoney = money.getOrDefault("pay_money", new BigDecimal(0.0)).doubleValue();
        Double billMoney = money.getOrDefault("bill_money", new BigDecimal(0.0)).doubleValue();
        proDetailService.updatePayAndBillMoney(mainId, payMoney, billMoney);
        ProDetailPay result = new ProDetailPay();
        result.setPayMoney(payMoney);
        result.setBillMoney(billMoney);
        return result;
    }

    @Override
    public int updatePayMoney(ProDetailPay pay) {
        int rows = proDetailPayMapper.updatePayMoney(pay);
        //更新主单据期末欠款
        rows += proDetailService.updateEndOwe(pay.getMainId());
        updateMainPayMoney(pay.getMainId());
        return rows;
    }

    @Override
    public int updateBillMoney(ProDetailPay pay) {
        int row = proDetailPayMapper.updateBillMoney(pay);
        updateMainPayMoney(pay.getMainId());
        return row;
    }

    @Override
    public int deletePay(String id) {
        ProDetailPay p = getPay(id);
        if (!Objects.isNull(p)) {
            int row = proDetailPayMapper.deletePay(id);
            updateMainPayMoney(p.getMainId());
            return row;
        } else {
            return 0;
        }
    }

    @Override
    public int deletePayByMainId(String mainId) {
        int row = proDetailPayMapper.deletePayByMainId(mainId);
        updateMainPayMoney(mainId);
        return row;
    }

    @Override
    public int updateLastMsg(String date, Staff staff, String id) {
        return proDetailPayMapper.updateLastMsg(date, staff, id);
    }

    @Override
    public List<ProDetailPay> getPayDetails(String mainId) {
        return proDetailPayMapper.getPayDetails(mainId);
    }

    @Override
    public ProDetailPay getPay(String id) {
        return proDetailPayMapper.getPayDetailById(id);
    }

    @Override
    public Map<String, BigDecimal> getMoneySumByMain(String id) {
        return proDetailPayMapper.getMoneySumByMain(id);
    }

    @Override
    public Double getPayMoneyByMain(String id) {
        return 0.0;
    }

    @Override
    public int deleteBySeries(String mainId, String billDate) {
        int row = proDetailPayMapper.deleteBySeries(mainId, billDate);
        updateMainPayMoney(mainId);
        return row;
    }

    @Override
    public ProDetailPay getBySeries(String mainId, String billDate) {
        List<ProDetailPay> pay = proDetailPayMapper.getBySeries(mainId, billDate);
        if (!pay.isEmpty()) {
            return pay.get(0);
        }
        return null;
    }

    @Override
    public ProDetailPay getByContractDetailId(String contractId, String detailId) {
        return proDetailPayMapper.getByContractDetailId(contractId, detailId);
    }

    @Override
    public int deleteByContractDetailId(String contractId, String detailId) {
        int row = proDetailPayMapper.deleteByContractDetailId(contractId, detailId);
        updateMainPayMoney(contractId);
        return row;
    }

    @Override
    public Double getPayMoney(String year) {
        return proDetailPayMapper.getPayMoney(year);
    }

    @Override
    public Double getBillMoney(String year) {
        return proDetailPayMapper.getBillMoney(year);
    }

    @Override
    public ProDetailPay updatePayAndBillMoney(ProDetailPay pay) {
        ProDetailPay temp = getPay(pay.getId());
        if(!Objects.isNull(temp)){
            proDetailPayMapper.updatePayAndBillMoney(pay);
            return updateMainPayMoney(temp.getMainId());
        }else{
            return null;
        }
    }
}
