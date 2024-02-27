package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.Invoice;
import com.yuesheng.pm.entity.InvoiceFile;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.InvoiceMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ssk
 * @date 2021-12-17
 */
@Service("InvoiceService")
public class InvoiceServiceImpl implements InvoiceService, FileService {

    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private BillService billService;
    @Autowired
    private InvoiceFileService fileService;
    @Autowired
    private AttachService attachService;

    @Override
    public List<Invoice> selectByDateForPage(Integer pageNum, Integer pageSize, String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth, DateUtil.PATTERN_CLASSICAL).getTime());
        PageHelper.startPage(pageNum, pageSize);
        return invoiceMapper.selectByDateForPage(startDate, endDate);
    }

    @Override
    public Integer selectCountsByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth, DateUtil.PATTERN_CLASSICAL).getTime());
        return invoiceMapper.selectCountsByDate(startDate, endDate);
    }

    @Override
    public List<Invoice> selectByDay(String dateSearch) {
        String start = dateSearch + " 00:00:00";
        String end = dateSearch + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(end, DateUtil.PATTERN_CLASSICAL).getTime());
        return invoiceMapper.selectByDay(startDate, endDate);
    }

    @Override
    public List<Invoice> selectByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth, DateUtil.PATTERN_CLASSICAL).getTime());
        return invoiceMapper.selectByDate(startDate, endDate);
    }

    @Override
    public List<Invoice> selectCheckedByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth, DateUtil.PATTERN_CLASSICAL).getTime());
        return invoiceMapper.selectCheckedByDate(startDate, endDate);
    }

    @Override
    public Invoice selectByMark(String pMark) {
        return invoiceMapper.selectByMark(pMark);
    }

    @Override
    public Invoice selectByInvoiceID(String invoiceID) {
        return invoiceMapper.selectByInvoiceID(invoiceID);
    }

    @Override
    public List<Invoice> selectByAgreementID(String agreementID) {
        return invoiceMapper.selectByAgreementID(agreementID);
    }

    @Override
    public int insertInvoice(Invoice invoice) {
        invoice.setID(UUID.randomUUID().toString());
        return invoiceMapper.insertInvoice(invoice);
    }

    @Override
    public int deleteInvoice(String ID) {
        return invoiceMapper.deleteInvoice(ID);
    }

    @Override
    public int deleteInvoiceByAgreementID(String agreementID) {
        return invoiceMapper.deleteInvoiceByAgreementID(agreementID);
    }

    @Override
    public int updateInvoice(Invoice invoice) {
        return invoiceMapper.updateInvoice(invoice);
    }

    @Override
    public List<Invoice> selectAll() {
        return invoiceMapper.selectAll();
    }

    @Override
    public Invoice selectBypMarkAndRID(String pMark, String registrantId) {
        return invoiceMapper.selectBypMarkAndRID(pMark, registrantId);
    }

    @Override
    public Invoice selectById(String id) {
        return invoiceMapper.selectById(id);
    }

    @Override
    public List<Invoice> selectCheckedByDateForPage(Integer pageNum, Integer pageSize, String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth, DateUtil.PATTERN_CLASSICAL).getTime());
        PageHelper.startPage(pageNum, pageSize);
        return invoiceMapper.selectCheckedByDateForPage(startDate, endDate);
    }

    @Override
    public Integer selectCheckedCountsByDate(String dateSearch) {
        String start = dateSearch + "-01 00:00:00";
        String lastDayOfMonth = DateUtil.getLastDayOfMonth(dateSearch) + " 23:59:59";
        java.sql.Date startDate = new java.sql.Date(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime());
        java.sql.Date endDate = new java.sql.Date(DateUtil.parse(lastDayOfMonth, DateUtil.PATTERN_CLASSICAL).getTime());
        return invoiceMapper.selectCheckedCountsByDate(startDate, endDate);
    }

    @Override
    public List<Invoice> searchInvoice(Integer pageNum,Integer pageSize,String search) {
        PageHelper.startPage(pageNum,pageSize );
        return invoiceMapper.searchInvoice(search);
    }

    @Override
    public Integer searchInvoiceCounts(String search) {
        return invoiceMapper.searchInvoiceCounts(search);
    }

    @Override
    public List<Invoice> searchInvoiceChecked(Integer pageNum,Integer pageSize, String search) {
        PageHelper.startPage(pageNum,pageSize);
        return invoiceMapper.searchInvoiceChecked( search);
    }

    @Override
    public Integer searchInvoiceCheckedCounts(String search) {
        return invoiceMapper.searchInvoiceCheckedCounts(search);
    }

    @Override
    public Invoice selectForBill(Invoice invoice) {
        return invoiceMapper.selectForBill(invoice);
    }

    @Override
    public Invoice selectByBillMark(String billMark) {
        return invoiceMapper.selectByBillMark(billMark);
    }

    @Override
    public boolean setStatus(String id) {
        invoiceMapper.setStatus(id);
        return true;
    }

    @Override
    public boolean actionForChecked(List<Staff> staffList, Invoice invoice) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("title", "开票通知");
        msg.put("content", "您的" + invoice.getAgreementName() + "开票申请已通过");
        msg.put("url", WebParam.VUETIFY_BASE + "/salesContract/list/salesContractList");
        msg.put("mTitle", 200);
        flowNotifyService.msgNotify(staffList, msg);
        return true;
    }

    @Override
    public List<Invoice> selectByTax(String tax, String agreementID) {
        return invoiceMapper.selectByTax(tax, agreementID);
    }

    @Override
    public List<Invoice> selectByNullTax(String agreementID) {
        return invoiceMapper.selectByNullTax(agreementID);
    }

    @Override
    public List<Invoice> selectAllDone() {
        return invoiceMapper.selectAllDone();
    }

    @Override
    public int approve(String id) {
        Invoice invoice = selectById(id);
        if (!Objects.isNull(invoice)) {
            invoice.setStatus("已审核");
            invoice.setStatusNumber(1);
            updateInvoice(invoice);
            Staff staffById = staffService.getStaffById(invoice.getRegistrantId());
            List<Staff> list = new ArrayList<>();
            list.add(staffById);
            actionForChecked(list, invoice);
            billService.insertInvoicing(invoice.getAgreementID(), String.valueOf(invoice.getID()), invoice.getRemark(), invoice.getInvoiceMoney(), staffById);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Invoice> getByCompany(Map<String, String> params) {
        return invoiceMapper.getByCompany(params);
    }

    @Override
    public List<Invoice> list(Map<String, String> params) {
        return invoiceMapper.list(params);
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        List<InvoiceFile> files = fileService.select(moduleId);
        ArrayList<Attach> attaches = new ArrayList<>();
        files.forEach(item -> {
            String name = item.getName();
            String url = item.getUrl();
            attaches.add(attachService.getById(attachService.findId(name, url)));
        });
        return attaches;
    }
}
