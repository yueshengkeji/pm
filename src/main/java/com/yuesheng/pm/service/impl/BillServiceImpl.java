package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.entity.Collection;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private ProZujinService zujinService;
    @Autowired
    private ProDetailPayService proDetailPayService;
    @Autowired
    private ProPutDetailService proPutDetailService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    @Lazy
    private InvoiceService invoiceService;

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
        zujin.setSh(false);
        zujin.setZlType(0);
        zujin.setStaffId(staff.getId());
        zujin.setStaffName(staff.getName());
        Date end = DateUtil.rollDay(DateFormat.parseData(contractEndDate), 999);
        zujin.setEndDatetime(DateUtil.format(end, DateUtil.PATTERN_CLASSICAL_SIMPLE));
        List<ProZujinHouse> list = new ArrayList<>();
        zujin.setHouses(list);
        zujinService.insert(zujin, staff);
        return 1;
    }

    @Override
    public int insertInvoicing(String contractSeries, String invoicingId, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        Invoice invoice = invoiceService.selectById(invoicingId);
        if (!Objects.isNull(zujin) && !Objects.isNull(invoice)) {
            ProDetailPay pay = new ProDetailPay();
            pay.setDetailId(invoicingId);
            pay.setBillMoney(money.doubleValue());
            pay.setBillDate(DateUtil.format(new Date(), "yyyy-MM-dd#"));
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
    public int deleteInvoicing(String contractSeries, String invoicingId, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        int row = 0;
        if (!Objects.isNull(zujin)) {
            row = proDetailPayService.deleteByContractDetailId(zujin.getId() + "", invoicingId);
            updateBillMoney(contractSeries);
        }
        return row;
    }

    @Override
    public int updateInvoicing(String contractSeries, String invoicingId, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        Invoice invoice = invoiceService.selectById(invoicingId);
        if (!Objects.isNull(zujin) && !Objects.isNull(invoice)) {
            ProDetailPay pay = proDetailPayService.getByContractDetailId(zujin.getId() + "", invoicingId);
            if (!Objects.isNull(pay)) {
                pay.setBillMoney(money.doubleValue());
                pay.setBillDate(DateUtil.format(new Date(), "yyyy-MM-dd#"));
                pay.setLastDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                pay.setLastStaff(staff.getId());
                updateBillMoney(contractSeries);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int insertProceeds(String contractSeries, String proceedsId, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        Collection collection = collectionService.selectById(Integer.valueOf(proceedsId));
        if (!Objects.isNull(zujin) && !Objects.isNull(collection)) {
            ProPutForDetail putForDetail = new ProPutForDetail();
            putForDetail.setPutId(proceedsId);
            putForDetail.setProMoney(money.doubleValue());
            setProDate(collection, putForDetail);
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

    private void setProDate(Collection collection, ProPutForDetail putForDetail) {
        Date date = collection.getpDate();
        if (Objects.isNull(date)) {
            putForDetail.setProDate(collection.getCollectID());
        } else {
            putForDetail.setProDate(DateUtil.format(date, "yyyy-MM-dd#"));
        }
    }

    @Override
    public int deleteProceeds(String contractSeries, String proceedsId, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        int row = 0;
        if (!Objects.isNull(zujin)) {
            row = proPutDetailService.deleteByContractDetailId(zujin.getId() + "", proceedsId);
            updateBillMoney(contractSeries);
        }
        return row;
    }

    @Override
    public int updateProceeds(String contractSeries, String proceedsId, String remark, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        Collection collection = collectionService.selectById(Integer.valueOf(proceedsId));
        if (!Objects.isNull(zujin) && !Objects.isNull(collection)) {
            ProPutForDetail detail = proPutDetailService.getByContractDetailId(zujin.getId() + "", proceedsId);
            if (!Objects.isNull(detail)) {
                detail.setProMoney(money.doubleValue());
                detail.setRemark(remark);
                setProDate(collection, detail);
                proPutDetailService.updateProMoney(detail);
                updateBillMoney(contractSeries);
                return 1;
            }
        }

        return 0;
    }

    @Override
    public int proceedsStateChange(String contractSeries, String proceedsId, boolean state, BigDecimal money, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        Collection collection = collectionService.selectById(Integer.valueOf(proceedsId));
        if (!Objects.isNull(zujin) && !Objects.isNull(collection)) {
            ProPutForDetail detail = proPutDetailService.getByContractDetailId(zujin.getId() + "", proceedsId);
            if (state) {
                //审核，生成财务已收款
                /*ProDetailPay pay = proDetailPayService.getBySeries(zujin.getId() + "", proceedsId);
                if (!Objects.isNull(pay)) {
                    pay.setPayMoney(money.doubleValue());
                    pay.setPayDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                    pay.setLastStaff(staff.getId());
                    pay.setPayDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
                    proDetailPayService.updatePayMoney(pay);
                    updateBillMoney(contractSeries);
                    return 1;
                }*/
                //    审核，生成实际已收款
                if (!Objects.isNull(detail)) {
                    detail.setPutMoney(money.doubleValue());
                    setPutDate(collection, detail);
                    proPutDetailService.updatePutMoney(detail);
                    updateBillMoney(contractSeries);
                    return 1;
                }

                return 0;
            } else {
                //未审核，删除财务已收款
                /*ProDetailPay pay = proDetailPayService.getBySeries(zujin.getId() + "", proceedsId);
                if (!Objects.isNull(pay)) {
                    pay.setPayMoney(0.0);
                    pay.setPayDate("");
                    pay.setLastStaff(staff.getId());
                    proDetailPayService.updatePayMoney(pay);
                    updateBillMoney(contractSeries);
                    return 1;
                }*/
                //未审核，取消实收金额
                // proPutDetailService.deleteByContractDetailId(zujin.getId()+"",proceedsId);
                if (!Objects.isNull(detail)) {
                    detail.setPutMoney(0.0d);
                    detail.setPutDate("");
                    proPutDetailService.updatePutMoney(detail);
                    updateBillMoney(contractSeries);
                    return 1;
                }
            }
        }
        return 0;
    }

    private void setPutDate(Collection collection, ProPutForDetail detail) {
        detail.setPutDate(DateUtil.format(new Date(), "yyyy-MM-dd#"));
    }

    @Override
    public int update(String companyName, String companyPerson, String companyPhone, String contractSeries, String contractEndDate, String billType, String remark, BigDecimal contractMoney, Staff staff) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
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

    @Override
    public int deleteContract(String contractSeries) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        int row;
        if (!Objects.isNull(zujin)) {
            //删除应收/实收账单
            row = proPutDetailService.deleteByContract(String.valueOf(zujin.getId()));
            //删除财务票据/款项数据
            row += proDetailPayService.deletePayByMainId(String.valueOf(zujin.getId()));
            //删除对账单主表
            zujinService.deleteById(zujin.getId());
            return row;
        }
        return -1;
    }

    private void updateBillMoney(String contractSeries) {
        ProZujin zujin = zujinService.queryBySeries(contractSeries);
        if (!Objects.isNull(zujin)) {
            Double money = proPutDetailService.getProMoneySumByMain(zujin.getId() + "");
            //应收金额合计
            zujin.setYsMoney(money);
            //实收金额合计
            money = proPutDetailService.getPutMoneySumByMain(zujin.getId() + "");
            zujin.setSjMoney(money);
            //财务已收合计
            Map<String, BigDecimal> moneyMap = proDetailPayService.getMoneySumByMain(zujin.getId() + "");
            if (!Objects.isNull(moneyMap)) {
                BigDecimal payMoney = moneyMap.get("pay_money");
                payMoney = isNull(payMoney);
                zujin.setCwMoney(payMoney.doubleValue());
                payMoney = moneyMap.get("bill_money");
                payMoney = isNull(payMoney);
                //已开票合计
                zujin.setKpMoney(payMoney.doubleValue());
            }
            zujinService.updateMoneyCount(zujin);
        }
    }

    private BigDecimal isNull(BigDecimal payMoney) {
        if (Objects.isNull(payMoney)) {
            payMoney = new BigDecimal(0.0);
        }
        return payMoney;
    }
}
