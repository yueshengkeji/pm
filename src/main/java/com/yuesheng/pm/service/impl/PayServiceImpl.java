package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.service.PayService;
import com.yuesheng.pm.service.ProDetailPayService;
import com.yuesheng.pm.service.ProPutDetailService;
import com.yuesheng.pm.service.ProZujinService;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("PayService")
public class PayServiceImpl implements PayService {
    @Autowired
    private ProZujinService zujinService;
    @Autowired
    private ProDetailPayService proDetailPayService;
    @Autowired
    private ProPutDetailService proPutDetailService;

    @Override
    public int insert(String companyName,
                      String companyPerson,
                      String companyPhone,
                      String contractSeries,
                      String contractEndDate,
                      String billType,
                      String remark, BigDecimal contractMoney, Staff staff) {
        ProZujin zujin = new ProZujin();
        zujin.setCompany(companyName);
        zujin.setZlPerson(companyPerson);
        zujin.setZlPersonTel(companyPhone);
        zujin.setYearRental(contractMoney.doubleValue());
        zujin.setSeries(contractSeries);
        zujin.setRemark(remark);
        zujin.setCompanyTypeId(Integer.valueOf(billType));
        zujin.setSh(true);
        zujin.setZlType(0);
        Date end = DateUtil.rollDay(DateFormat.parseData(contractEndDate), 999);
        zujin.setEndDatetime(DateUtil.format(end, DateUtil.PATTERN_CLASSICAL_SIMPLE));
        List<ProZujinHouse> list = new ArrayList<>();
        zujin.setHouses(list);
        zujinService.insert(zujin, staff);
        return 1;
    }

    @Override
    public int insertInvoicing(String contractSeries, String invoicingSeries, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        if (!Objects.isNull(zujin)) {
            ProDetailPay pay = new ProDetailPay();
            pay.setBillMoney(money.doubleValue());
            pay.setBillDate(invoicingSeries);
            pay.setStaff(staff);
            pay.setLastStaff(staff.getId());
            pay.setLastDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            pay.setMainId(zujin.getId() + "");
            pay.setId(UUID.randomUUID().toString());
            pay.setDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            proDetailPayService.addPay(pay);
            updateBillMoney(contractSeries);
        }
        return 1;
    }

    @Override
    public int deleteInvoicing(String contractSeries, String invoicingSeries, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        int row = 0;
        if (!Objects.isNull(zujin)) {
            row = proDetailPayService.deleteBySeries(zujin.getId() + "", invoicingSeries);
            updateBillMoney(contractSeries);
        }
        return row;
    }

    @Override
    public int updateInvoicing(String contractSeries, String invoicingSeries, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        if (!Objects.isNull(zujin)) {
            ProDetailPay pay = proDetailPayService.getBySeries(zujin.getId() + "", invoicingSeries);
            if (!Objects.isNull(pay)) {
                pay.setBillMoney(money.doubleValue());
                pay.setLastDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                pay.setLastStaff(staff.getId());
                updateBillMoney(contractSeries);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int insertProceeds(String contractSeries, String proceedsSeries, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        if (!Objects.isNull(zujin)) {
            ProPutForDetail putForDetail = new ProPutForDetail();
            putForDetail.setProMoney(money.doubleValue());
            putForDetail.setProDate(proceedsSeries);
            putForDetail.setRemark(remark);
            putForDetail.setMainId(zujin.getId() + "");
            putForDetail.setStaff(staff);
            putForDetail.setLastStaff(staff);
            putForDetail.setLastDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            proPutDetailService.addDetail(putForDetail, staff);
            updateBillMoney(contractSeries);
        }
        return 0;
    }

    @Override
    public int deleteProceeds(String contractSeries, String proceedsSeries, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        int row = 0;
        if (!Objects.isNull(zujin)) {
            row = proPutDetailService.deleteBySeries(zujin.getId() + "", proceedsSeries);
            updateBillMoney(contractSeries);
        }
        return row;
    }

    @Override
    public int updateProceeds(String contractSeries, String proceedsSeries, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        if (!Objects.isNull(zujin)) {
            List<ProPutForDetail> detailList = proPutDetailService.getBySeries(zujin.getId() + "", proceedsSeries);
            if (!detailList.isEmpty()) {
                ProPutForDetail detail = detailList.get(0);
                detail.setProMoney(money.doubleValue());
                detail.setRemark(remark);
                proPutDetailService.updatePutMoney(detail);
                updateBillMoney(contractSeries);
                return 1;
            }
        }

        return 0;
    }

    @Override
    public int proceedsStateChange(String contractSeries, String proceedsSeries, boolean state, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        if (!Objects.isNull(zujin)) {
            if (state) {
                //审核，生成财务已付款
                ProDetailPay pay = proDetailPayService.getBySeries(zujin.getId() + "", proceedsSeries);
                if (!Objects.isNull(pay)) {
                    pay.setPayMoney(money.doubleValue());
                    pay.setPayDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                    pay.setLastStaff(staff.getId());
                    pay.setPayDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                    proDetailPayService.updatePayMoney(pay);
                    updateBillMoney(contractSeries);
                    return 1;
                }
            } else {
                //未审核，删除财务已付款
                ProDetailPay pay = proDetailPayService.getBySeries(zujin.getId() + "", proceedsSeries);
                if (!Objects.isNull(pay)) {
                    pay.setPayMoney(0.0);
                    pay.setPayDate("");
                    pay.setLastStaff(staff.getId());
                    proDetailPayService.updatePayMoney(pay);
                    updateBillMoney(contractSeries);
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public int update(String companyName, String companyPerson, String companyPhone, String contractSeries, String contractEndDate, String billType, String remark, BigDecimal contractMoney, Staff staff) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        if (!Objects.isNull(zujin)) {
            zujin.setCompany(companyName);
            zujin.setZlPerson(companyPerson);
            zujin.setZlPersonTel(companyPhone);
            zujin.setYearRental(contractMoney.doubleValue());
            zujin.setSeries(contractSeries);
            zujin.setRemark(remark);
            zujin.setCompanyTypeId(Integer.valueOf(billType));
            zujin.setEndDatetime(contractEndDate);
            zujinService.update(zujin);
            return 1;
        }
        return 0;
    }

    private void updateBillMoney(String contractSeries) {
        ProZujin zujin = zujinService.queryByPay(contractSeries);
        if (!Objects.isNull(zujin)) {
            Double money = proPutDetailService.getProMoneySumByMain(zujin.getId() + "");
            //应付金额合计
            zujin.setYsMoney(money);
            //财务已付合计
            Map<String, BigDecimal> moneyMap = proDetailPayService.getMoneySumByMain(zujin.getId() + "");
            if (!Objects.isNull(moneyMap)) {
                BigDecimal payMoney = moneyMap.get("pay_money");
                payMoney = isNull(payMoney);
                zujin.setCwMoney(payMoney.doubleValue());
                payMoney = moneyMap.get("bill_money");
                payMoney = isNull(payMoney);
                //已收票合计
                zujin.setKpMoney(payMoney.doubleValue());
                zujinService.updateMoneyCount(zujin);
            }
        }
    }

    private BigDecimal isNull(BigDecimal payMoney) {
        if (Objects.isNull(payMoney)) {
            payMoney = new BigDecimal(0.0);
        }
        return payMoney;
    }
}
