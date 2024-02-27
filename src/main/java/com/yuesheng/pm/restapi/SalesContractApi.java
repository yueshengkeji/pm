package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.entity.Collection;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.InvoiceModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 销售合同登记
 *
 * @author ssk
 * @since 2021-12-17
 */
@Tag(name = "销售合同登记")
@RestController
@RequestMapping("api/salesContract")
public class SalesContractApi {

    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private BillService billService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SalesContractFilesService salesContractFilesService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private RegistrantIdCheckService registrantIdCheckService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private SalesContractTaxService salesContractTaxService;
    @Autowired
    private SalesContractManagerService salesContractManagerService;
    @Autowired
    private SalesContractLogsService salesContractLogsService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private InvoiceFileService invoiceFileService;

    @Operation(description = "搜索功能")
    @GetMapping("getSearchContract")
    public ResponseModel getSearchContract(String search) {
        Map<String, Object> params = new HashMap<>();
        List<SalesContract> salesContracts = salesContractService.selectBySearch(search);
        params.put("rows", salesContracts);
        return new ResponseModel(params);
    }

    @Operation(description = "获取销售合同信息")
    @GetMapping("getSalesContract")
    public ResponseModel getSalesContractService(Integer itemsPerPage, Integer page, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Map<String, Object> params = new HashMap<>();
        List<String> staffId = new ArrayList<>();

        if (staff.getId().equals("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2")){
            List<SalesContract> salesContracts1 = salesContractService.selectForPage(page,itemsPerPage);
            Integer integer = salesContractService.selectCounts();
            params.put("rows", salesContracts1);
            params.put("totalDesserts", integer);
        }else {
            Duty[] dutyByStaffId = dutyService.getDutyByStaffId(staff.getId());

            List<Duty> dutyByRoot = new ArrayList<>();
            List<Duty> temp = new ArrayList<>();
            if (dutyByStaffId != null) {
                for (int i = 0; i < dutyByStaffId.length; i++) {
                    if (dutyByStaffId[i] != null){
                        if (dutyByStaffId[i].getId() != null){
                            temp = dutyService.getDutyByRoot(dutyByStaffId[i].getId());
                            temp.forEach(item -> {
                                dutyByRoot.add(item);
                            });
                        }
                    }
                }
            }
            dutyByRoot.forEach(dutyItem -> {
                List<Staff> staffByDuty = dutyService.getStaffByDuty(dutyItem.getId());
                staffByDuty.forEach(staffItem -> {
                    staffId.add(staffItem.getId());
                });
            });

            staffId.add(staff.getId());
            boolean contains = staffId.contains("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2");
            if (contains == false) {
                staffId.add("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2");
            }
            List<SalesContract> salesContracts = salesContractService.selectByDuty(page, itemsPerPage, staffId);
            Integer totalDesserts = salesContractService.selectByDutyCounts(staffId);

            params.put("rows", salesContracts);
            params.put("totalDesserts", totalDesserts);
        }

        return new ResponseModel(params);
    }

    @Operation(description = "判断是否存在相同的合同编号")
    @GetMapping("getSalesContractIsExist")
    public ResponseModel getSalesContractIsExist(@Parameter String agreementID) {
        Map<String, Object> params = new HashMap<>();
        String result = null;
        List<SalesContract> salesContracts = salesContractService.selectAll();
        for (int i = 0; i < salesContracts.size(); i++) {
            if (salesContracts.get(i).getAgreementID().equals(agreementID)) {
                result = "已存在";
            }
        }
        params.put("result", result);
        return new ResponseModel(params);
    }

    @Operation(description = "新增销售合同登记")
    @PutMapping("insertSalesContract")
    public ResponseModel insertSalesContractService(@RequestBody SalesContract salesContract, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        salesContract.setRegistrant(staff.getName());
        salesContract.setRegistrantId(staff.getId());
        if (salesContract.getPaymentDays() == null) {
            salesContract.setPaymentDays(0);
        }
        if (salesContract.getCity() != null && salesContract.getCity().getId() == null){
            ChineseHeaderGet chineseHeaderGet = new ChineseHeaderGet();
            salesContract.getCity().setId(AESEncrypt.getRandom8Id());
            salesContract.getCity().setCoding(chineseHeaderGet.getSpells(salesContract.getCity().getName()));
            Region region = new Region();
            region.setName(salesContract.getCity().getName());
            region.setId(salesContract.getCity().getId());
            region.setCoding(salesContract.getCity().getCoding());
            regionService.insert(region);
        }
        int res = salesContractService.insertContract(salesContract);
        if (res == 1) {

            SalesContract salesContract1 = salesContractService.selectByID(salesContract.getContractId());
            for (int i = 0; i < salesContract.getFiles().size(); i++) {
                salesContract.getFiles().get(i).setMark(salesContract1.getContractId());
                salesContractFilesService.insert(salesContract.getFiles().get(i));
            }

            billService.insert(salesContract.getCompanyName(), salesContract.getContactMan(), salesContract.getPhone(), salesContract.getContractId(), DateUtil.format(addDate(salesContract.getSignDate(), salesContract.getPaymentDays())), String.valueOf(salesContract.getPaymentDays()), salesContract.getRemark(), salesContract.getAgreementMoney(), staff);

            Company company = new Company();
            List<Company> seek = companyService.seek(salesContract.getCompanyName());
            if (seek.size() != 0) {
                company = seek.get(0);
                company.setAddress(salesContract.getCompanyAddress());
                company.setOpenAccount(salesContract.getBankName());
                company.setBankNumber(salesContract.getBankAccount());
                company.setPhone(salesContract.getPhone());
                company.setJurisdiction(salesContract.getJurisdiction());
                company.setStreet(salesContract.getStreet());
                companyService.updateCompany(company);
            } else if (seek.size() == 0) {
                company.setName(salesContract.getCompanyName());
                company.setAddress(salesContract.getCompanyAddress());
                company.setOpenAccount(salesContract.getBankName());
                company.setBankNumber(salesContract.getBankAccount());
                company.setPhone(salesContract.getPhone());
                company.setJurisdiction(salesContract.getJurisdiction());
                company.setStreet(salesContract.getStreet());
                companyService.insert(company, staff);
            }
            if (!salesContract.getRetentionPercent().equals("0")) {
                Collection collection = new Collection();
                collection.setAgreementID(salesContract.getContractId());
                if (salesContract.getpDate() != null) {
                    collection.setCollectID(formatDateForID(salesContract.getpDate()));
                } else {
                    collection.setCollectID("0000.00.00#");
                }
                collection.setCollectMoney(salesContract.getRetentionMoney());
                collection.setRemark("质保金应收款时间为质保期到期时间");
                collection.setPercent(salesContract.getRetentionPercent());
                collection.setpDate(salesContract.getpDate());
                collection.setRegistrantId(staff.getId());
                collection.setRegistrant(staff.getName());
                collection.setAgreementName(salesContract.getAgreementName());
                collection.setBillMark(formatMarkTime(new Date()));
                collection.setStatusNumber(0);
                int res2 = collectionService.insertCollection(collection);
                if (res2 > 0) {
                    Collection collection1 = collectionService.selectByBillMark(collection.getBillMark());
                    billService.insertProceeds(collection.getAgreementID(), String.valueOf(collection1.getID()), collection.getRemark(), collection.getCollectMoney(), staff);
                }
            }
        }
        return new ResponseModel(salesContract);
    }

    @Operation(description = "修改销售合同登记")
    @PutMapping("updateSalesContract")
    public ResponseModel updateSalesContract(@RequestBody SalesContract salesContract, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        salesContract.setRegistrant(staff.getName());
        salesContract.setModifierId(staff.getId());
        if (salesContract.getPaymentDays() == null) {
            salesContract.setPaymentDays(0);
        }
        if (salesContract.getCity() != null && salesContract.getCity().getId() == null){
            ChineseHeaderGet chineseHeaderGet = new ChineseHeaderGet();
            salesContract.getCity().setId(AESEncrypt.getRandom8Id());
            salesContract.getCity().setCoding(chineseHeaderGet.getSpells(salesContract.getCity().getName()));
            Region region = new Region();
            region.setName(salesContract.getCity().getName());
            region.setId(salesContract.getCity().getId());
            region.setCoding(salesContract.getCity().getCoding());
            regionService.insert(region);
        }
        int res = salesContractService.updateContract(salesContract);
        if (res > 0) {

            for (int i = 0; i < salesContract.getFiles().size(); i++) {
                salesContractFilesService.insert(salesContract.getFiles().get(i));
            }

            billService.update(salesContract.getCompanyName(),salesContract.getContactMan(),salesContract.getPhone(),salesContract.getContractId(),DateUtil.format(addDate(salesContract.getSignDate(), salesContract.getPaymentDays())), String.valueOf(salesContract.getPaymentDays()), salesContract.getRemark(), salesContract.getAgreementMoney(), staff);

            List<Invoice> invoices = invoiceService.selectByAgreementID(salesContract.getContractId());
            if (invoices != null) {
                for (int j = 0; j < invoices.size(); j++) {
                    invoices.get(j).setPercent((invoices.get(j).getInvoiceMoney().multiply(BigDecimal.valueOf(100))).divide(salesContract.getAgreementMoney(), 2, BigDecimal.ROUND_HALF_UP) + "%");
                    invoiceService.updateInvoice(invoices.get(j));
                }
            }

            Integer paymentDays = salesContract.getPaymentDays();
            List<Collection> collections = collectionService.selectByAgreementID(salesContract.getContractId());
            if (collections != null) {
                for (int i = 0; i < collections.size(); i++) {
                    if (collections.get(i).getRemark() != null && collections.get(i).getRemark().equals("质保金应收款时间为质保期到期时间")) {
                        if (collections.get(i).getCollectMoney() != salesContract.getRetentionMoney()) {
                            if (collections.get(i).getStatus().equals("已审核")) {
                                BigDecimal collectMoney = collections.get(i).getCollectMoney();
                                salesContract.setCollectedMoney(salesContract.getCollectedMoney().subtract(collectMoney));
                                salesContractService.updateContract(salesContract);
                                collections.get(i).setStatus("未审核");
                                collections.get(i).setStatusNumber(0);
                                collections.get(i).setCreateTime(null);
                            }
                        }

                        if (salesContract.getpDate() != null) {
                            collections.get(i).setCollectID(formatDateForID(salesContract.getpDate()));
                        } else {
                            collections.get(i).setCollectID("0000.00.00#");
                        }
                        collections.get(i).setpDate(salesContract.getpDate());
                        collections.get(i).setCollectMoney(salesContract.getRetentionMoney());
                        collections.get(i).setPercent(salesContract.getRetentionPercent());
                        billService.proceedsStateChange(salesContract.getContractId(), String.valueOf(collections.get(i).getID()), false, collections.get(i).getCollectMoney(), staff);
                        int res2 = collectionService.updateCollection(collections.get(i));
                        if (res2 > 0) {
                            billService.updateProceeds(salesContract.getContractId(), String.valueOf(collections.get(i).getID()), collections.get(i).getRemark(), collections.get(i).getCollectMoney(), staff);
                        }
                    } else {
                        Invoice invoice = invoiceService.selectByMark(collections.get(i).getpMark());
//                        collections.get(i).setpDate(addDate(salesContract.getSignDate(), paymentDays));
                        collections.get(i).setPercent((collections.get(i).getCollectMoney().multiply(BigDecimal.valueOf(100))).divide(salesContract.getAgreementMoney(), 2, BigDecimal.ROUND_HALF_UP) + "%");
                        int res2 = collectionService.updateCollection(collections.get(i));
                        if (res2 > 0) {
                            billService.updateProceeds(salesContract.getContractId(), String.valueOf(collections.get(i).getID()), collections.get(i).getRemark(), collections.get(i).getCollectMoney(), staff);
                        }
                    }
                }
            }

        }
        return new ResponseModel(salesContract);
    }

    @Operation(description = "删除销售合同登记")
    @PutMapping("deleteSalesContract")
    public ResponseModel deleteSalesContract(@RequestBody SalesContract salesContract, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        if (salesContract.getState() == 1){
            return new ResponseModel(500,null,"该合同登记已审核，禁止删除！");
        }else {
            int res = salesContractService.deleteContract(salesContract.getContractId());
            if (res == 1) {
                collectionService.deleteCollectionByAgreementID(salesContract.getContractId());
                invoiceService.deleteInvoiceByAgreementID(salesContract.getContractId());
                salesContractFilesService.deleteByMark(salesContract.getContractId());
                billService.deleteContract(salesContract.getContractId());
            }
            return new ResponseModel(salesContract);
        }
    }

    @Operation(description = "获取开票信息")
    @GetMapping("getInvoice")
    public ResponseModel getInvoiceService(String agreementID) {

        Map<String, Object> params = new HashMap<>();
        List<Invoice> invoices = invoiceService.selectByAgreementID(agreementID);
        params.put("rows", invoices);
        return new ResponseModel(params);
    }

    @Operation(description = "新增开票登记")
    @PutMapping("insertInvoice")
    public ResponseModel insertInvoice(@RequestBody Invoice invoice, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        invoice.setRegistrant(staff.getName());
        invoice.setRegistrantId(staff.getId());
        invoice.setpMark(formatMarkTime(new Date()));
        Invoice invoiceReturn = null;
        invoice.setBillMark(formatMarkTime(new Date()));
        if (invoice.getStatus().equals("已审核")){
            invoice.setStatusNumber(1);
        }else {
            invoice.setStatusNumber(0);
        }
        if (invoice.getTax().equals("默认")){
            invoice.setTax(null);
        }
        int res = invoiceService.insertInvoice(invoice);
        if (res > 0) {
            if (invoice.getStatus().equals("已审核")) {
                Invoice invoice1 = invoiceService.selectByBillMark(invoice.getBillMark());
                billService.insertInvoicing(invoice.getAgreementID(), String.valueOf(invoice1.getID()), invoice.getRemark(), invoice.getInvoiceMoney(), staff);
            }

            for (int i = 0;i < invoice.getInvoiceFiles().size();i++){
                invoice.getInvoiceFiles().get(i).setId(UUID.randomUUID().toString());
                invoice.getInvoiceFiles().get(i).setMark(invoice.getID());
                invoiceFileService.insert(invoice.getInvoiceFiles().get(i));
            }

            //新增开票对合同下总开票进行修改
            SalesContract salesContract = salesContractService.selectByID(invoice.getAgreementID());
            salesContract.setInvoicedMoney(salesContract.getInvoicedMoney().add(invoice.getInvoiceMoney()));
            salesContractService.updateContract(salesContract);

            if (invoice.getReturnDate() != null) {
                //预收款
                Collection collection = new Collection();
                collection.setCollectID(invoice.getInvoiceID());
                collection.setAgreementID(invoice.getAgreementID());
                collection.setCollectMoney(invoice.getInvoiceMoney());
                collection.setPercent(invoice.getPercent());
                collection.setpDate(invoice.getReturnDate());
                collection.setStatus("未审核");
                collection.setpMark(invoice.getpMark());
                collection.setRegistrantId(staff.getId());
                collection.setRegistrant(staff.getName());
                collection.setAgreementName(invoice.getAgreementName());
                collection.setBillMark(formatMarkTime(new Date()));
                collection.setStatusNumber(0);
                int res2 = collectionService.insertCollection(collection);
                if (res2 > 0) {
                    Collection collection1 = collectionService.selectByBillMark(collection.getBillMark());
                    billService.insertProceeds(collection.getAgreementID(), String.valueOf(collection1.getID()), collection.getRemark(), collection.getCollectMoney(), staff);

                    //日志
                    SalesContractLogs salesContractLogsC = new SalesContractLogs();
                    salesContractLogsC.setAgreementID(collection.getAgreementID());
                    salesContractLogsC.setType(1);
                    salesContractLogsC.setModifyType(0);
                    salesContractLogsC.setModifyStaff(staff);
                    salesContractLogsC.setModifyMSG(staff.getName() + "因新增开票，而自动新增对应收款，收款金额为:" + collection.getCollectMoney()
                            + "收款状态为:" + collection.getStatus());
                    salesContractLogsService.insertLogs(salesContractLogsC);
                }
            }

            //日志
            SalesContractLogs salesContractLogs = new SalesContractLogs();
            salesContractLogs.setAgreementID(invoice.getAgreementID());
            salesContractLogs.setType(0);
            salesContractLogs.setModifyType(0);
            salesContractLogs.setModifyStaff(staff);
            if (invoice.getTax() != null && invoice.getTax() != "") {
                salesContractLogs.setModifyMSG(staff.getName() + "新增了开票,开票金额为:" + invoice.getInvoiceMoney()
                        + ",占比为:" + invoice.getPercent() + "%,税率类型为(占比%/税率%):" + invoice.getTax());
            } else if (invoice.getTax() == null || invoice.getTax() == "") {
                salesContractLogs.setModifyMSG(staff.getName() + "新增了开票,开票金额为:" + invoice.getInvoiceMoney()
                        + ",占比为:" + invoice.getPercent() + "%,税率为销售合同默认税率或该合同无税率");
            }
            salesContractLogsService.insertLogs(salesContractLogs);

            //返回有id的开票对象
            invoiceReturn = invoiceService.selectBypMarkAndRID(invoice.getpMark(), staff.getId());
        }
        return new ResponseModel(invoiceReturn);
    }

    @Operation(description = "删除开票登记")
    @PutMapping("deleteInvoice")
    public ResponseModel deleteInvoice(@RequestBody Invoice invoice, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {

        Invoice tempInvoice = invoiceService.selectById(invoice.getID());
        if (Objects.isNull(tempInvoice)){
            return ResponseModel.ok();
        }else if (StringUtils.equals(tempInvoice.getStatus(),"已审核")){
            return new ResponseModel(500,null,"开票信息已审核，禁止删除");
        }else {
            int res = invoiceService.deleteInvoice(invoice.getID());
            if (res == 1) {
                billService.deleteInvoicing(invoice.getAgreementID(), String.valueOf(invoice.getID()), staff);

                //删除开票对总开票进行修改
                SalesContract salesContract = salesContractService.selectByID(invoice.getAgreementID());
                salesContract.setInvoicedMoney(salesContract.getInvoicedMoney().subtract(invoice.getInvoiceMoney()));
                salesContractService.updateContract(salesContract);

                //日志
                SalesContractLogs salesContractLogs = new SalesContractLogs();
                salesContractLogs.setAgreementID(invoice.getAgreementID());
                salesContractLogs.setType(0);
                salesContractLogs.setModifyType(1);
                salesContractLogs.setModifyStaff(staff);
                if (invoice.getTax() != null && invoice.getTax() != "") {
                    salesContractLogs.setModifyMSG(staff.getName() + "删除了开票,开票金额为:" + invoice.getInvoiceMoney()
                            + ",占比为:" + invoice.getPercent() + ",税率类型为(占比%/税率%):" + invoice.getTax());
                } else if (invoice.getTax() == null || invoice.getTax() == "") {
                    salesContractLogs.setModifyMSG(staff.getName() + "删除了开票,开票金额为:" + invoice.getInvoiceMoney()
                            + ",占比为:" + invoice.getPercent() + ",税率为销售合同默认税率或该合同无税率");
                }
                salesContractLogsService.insertLogs(salesContractLogs);

                //删除预收款
                Collection collection = collectionService.selectByMark(invoice.getpMark());
                if (collection != null){
                    if (StringUtils.equals(collection.getStatus(),"已审核")){
                        return new ResponseModel(invoice);
                    }else {
                        int res2 = collectionService.deleteCollectionByPMark(invoice.getpMark());
                        if (res2 == 1) {
//                        if (collection.getStatus().equals("已审核")) {
//                            salesContract.setCollectedMoney(salesContract.getCollectedMoney().subtract(collection.getCollectMoney()));
//                            salesContractService.updateContract(salesContract);
//                        }

                            //日志
                            SalesContractLogs salesContractLogsC = new SalesContractLogs();
                            salesContractLogsC.setAgreementID(collection.getAgreementID());
                            salesContractLogsC.setType(1);
                            salesContractLogsC.setModifyType(1);
                            salesContractLogsC.setModifyStaff(staff);
                            salesContractLogsC.setModifyMSG(staff.getName() + "因删除开票，而自动删除对应收款，收款金额为:" + collection.getCollectMoney()
                                    + "收款状态为:" + collection.getStatus());
                            salesContractLogsService.insertLogs(salesContractLogsC);

                            billService.deleteProceeds(collection.getAgreementID(), String.valueOf(collection.getID()), staff);
                        }
                    }
                }

            }
            return new ResponseModel(invoice);
        }
    }

    @Operation(description = "获取收款信息")
    @GetMapping("getCollect")
    public ResponseModel getCollectionService(String agreementID) {
        Map<String, Object> params = new HashMap<>();
        List<Collection> collections = collectionService.selectByAgreementID(agreementID);
        params.put("rows", collections);
        return new ResponseModel(params);
    }

    @Operation(description = "新增收款登记")
    @PutMapping("insertCollect")
    public ResponseModel insertCollect(@RequestBody Collection collection, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        collection.setRegistrant(staff.getName());
        collection.setRegistrantId(staff.getId());
        collection.setStatus("未审核");
        SalesContract salesContract = salesContractService.selectByID(collection.getAgreementID());
        if (salesContract != null) {
            collection.setpDate(addDate(salesContract.getSignDate(), salesContract.getPaymentDays()));
        }
        collection.setBillMark(formatMarkTime(new Date()));
        collection.setStatusNumber(0);
        int res = collectionService.insertCollection(collection);
        if (res > 0) {
            Collection collection1 = collectionService.selectByBillMark(collection.getBillMark());
            billService.insertProceeds(collection.getAgreementID(), String.valueOf(collection1.getID()), collection.getRemark(), collection.getCollectMoney(), staff);

            //日志
            SalesContractLogs salesContractLogsC = new SalesContractLogs();
            salesContractLogsC.setAgreementID(collection.getAgreementID());
            salesContractLogsC.setType(1);
            salesContractLogsC.setModifyType(1);
            salesContractLogsC.setModifyStaff(staff);
            salesContractLogsC.setModifyMSG(staff.getName() + "新增收款，收款金额为:" + collection.getCollectMoney()
                    + "收款状态为:" + collection.getStatus());
            salesContractLogsService.insertLogs(salesContractLogsC);
        }
        return new ResponseModel(collection);
    }

    @Operation(description = "删除收款登记")
    @PutMapping("deleteCollect")
    public ResponseModel deleteCollect(@RequestBody Collection collection, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Collection tempCollect = collectionService.selectById(collection.getID());
        if (Objects.isNull(tempCollect)){
            return new ResponseModel(500,null,"收款信息不存在");
        }else if (StringUtils.equals(collection.getStatus(),"已审核") || StringUtils.equals(collection.getStatus(), "已回款")){
            return new ResponseModel(500,null,"收款信息已审核，禁止删除");
        }else {
            int res = collectionService.deleteCollection(collection.getID());
            if (res > 0) {
                billService.deleteProceeds(collection.getAgreementID(), String.valueOf(collection.getID()), staff);

                if (collection.getStatus().equals("已审核")) {
                    SalesContract salesContract = salesContractService.selectByID(collection.getAgreementID());
                    salesContract.setCollectedMoney(salesContract.getCollectedMoney().subtract(collection.getCollectMoney()));
                    salesContractService.updateContract(salesContract);
                }

                //日志
                SalesContractLogs salesContractLogsC = new SalesContractLogs();
                salesContractLogsC.setAgreementID(collection.getAgreementID());
                salesContractLogsC.setType(1);
                salesContractLogsC.setModifyType(1);
                salesContractLogsC.setModifyStaff(staff);
                salesContractLogsC.setModifyMSG(staff.getName() + "删除收款，收款金额为:" + collection.getCollectMoney()
                        + "收款状态为:" + collection.getStatus());
                salesContractLogsService.insertLogs(salesContractLogsC);

            }
            return new ResponseModel(collection);
        }
    }


    @Operation(description = "审核状态")
    @PutMapping("checkStatus")
    public ResponseModel checkStatus(@RequestBody Collection collection, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {

        if (collection.getStatus().equals("已回款")) {
            collection.setStatus("已审核");
//            collection.setStatusNumber(1);
        } else if (collection.getStatus().equals("未回款")) {
            collection.setStatus("未审核");
//            collection.setStatusNumber(0);
        }

        collection.setRegistrant(staff.getName());
        if (collection.getpDate() != null) {
            collection.setCreateTime(DateUtil.getNowDate());
        }
        String status = collection.getStatus();
        if (status.equals("已审核")) {
            collection.setStatusNumber(1);
            int res = collectionService.updateCollection(collection);
            if (res > 0) {
                billService.proceedsStateChange(collection.getAgreementID(), String.valueOf(collection.getID()), true, collection.getCollectMoney(), staff);

                SalesContract salesContract = salesContractService.selectByID(collection.getAgreementID());
                salesContract.setCollectedMoney(salesContract.getCollectedMoney().add(collection.getCollectMoney()));
                if (collection.getTradeAcceptanceInterest() != null){
                    salesContract.setCollectedMoney(salesContract.getCollectedMoney().add(collection.getTradeAcceptanceInterest()));
                }
                int i = salesContractService.updateContract(salesContract);

                String collectTypeStr = "";
                switch (collection.getCollectType()){
                    case 0:collectTypeStr = "现金";break;
                    case 1:collectTypeStr = "商业承兑";break;
                    case 2:collectTypeStr = "保理";break;
                    case 3:collectTypeStr = "固定资产";break;
                }
                //日志
                SalesContractLogs salesContractLogsC = new SalesContractLogs();
                salesContractLogsC.setAgreementID(collection.getAgreementID());
                salesContractLogsC.setType(1);
                salesContractLogsC.setModifyType(2);
                salesContractLogsC.setModifyStaff(staff);
                salesContractLogsC.setModifyMSG(staff.getName() + "修改了收款，编号为:" + collection.getCollectID()
                        + "将收款状态修改为了已回款" + "，收款类型为" +collectTypeStr);
                salesContractLogsService.insertLogs(salesContractLogsC);

            }
        } else if (status.equals("未审核")) {
            collection.setStatusNumber(0);
            collection.setCreateTime(null);
            collection.setTradeAcceptanceDate(null);
            collection.setTradeAcceptanceInterest(null);
            collection.setFactoringDate(null);
            collection.setFactoringTime(null);
            collection.setFixedAssets(null);
            collection.setFixedAssetsStatus(null);
            collection.setCollectType(null);
            int res = collectionService.updateCollection(collection);
            if (res > 0) {
                billService.proceedsStateChange(collection.getAgreementID(), String.valueOf(collection.getID()), false, collection.getCollectMoney(), staff);

                SalesContract salesContract = salesContractService.selectByID(collection.getAgreementID());
                salesContract.setCollectedMoney(salesContract.getCollectedMoney().subtract(collection.getCollectMoney()));
                int i = salesContractService.updateContract(salesContract);

                //日志
                SalesContractLogs salesContractLogsC = new SalesContractLogs();
                salesContractLogsC.setAgreementID(collection.getAgreementID());
                salesContractLogsC.setType(1);
                salesContractLogsC.setModifyType(2);
                salesContractLogsC.setModifyStaff(staff);
                salesContractLogsC.setModifyMSG(staff.getName() + "修改了收款，编号为:" + collection.getCollectID()
                        + "将收款状态修改为了未回款");
                salesContractLogsService.insertLogs(salesContractLogsC);

            }
        }
        return new ResponseModel(collection);
    }

    public Date addDate(Date date, long day) {
        long time = date.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }

    public String formatMarkTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        return format;
    }

    public String formatYearMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String format = sdf.format(date);
        return format;
    }

    public String formatDateForID(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd#");
        String format = sdf.format(date);
        return format;
    }

    @Operation(description = "查询月销售合同登记")
    @GetMapping("monthSalesContract")
    public ResponseModel getMonthSalesContract(Integer itemsPerPage, Integer page, @Parameter String dateSearch, @Parameter String btnColor, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Map<String, Object> params = new HashMap<>();
        List<SalesContract> salesContracts = new ArrayList<>();
        Integer totalDesserts = null;
        List<String> staffId = new ArrayList<>();


        Duty[] dutyByStaffId = dutyService.getDutyByStaffId(staff.getId());

        List<Duty> dutyByRoot = new ArrayList<>();
        List<Duty> temp = new ArrayList<>();
        if (dutyByStaffId != null) {
            for (int i = 0; i < dutyByStaffId.length; i++) {
                if (dutyByStaffId[i] != null){
                    if (dutyByStaffId[i].getId() != null){
                        temp = dutyService.getDutyByRoot(dutyByStaffId[i].getId());
                        temp.forEach(item -> {
                            dutyByRoot.add(item);
                        });
                    }
                }
            }
        }
        dutyByRoot.forEach(dutyItem -> {
            List<Staff> staffByDuty = dutyService.getStaffByDuty(dutyItem.getId());
            staffByDuty.forEach(staffItem -> {
                staffId.add(staffItem.getId());
            });
        });
        staffId.add(staff.getId());
        boolean contains = staffId.contains("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2");
        if (contains == false) {
            staffId.add("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2");
        }

        if (staff.getId().equals("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2")){
            salesContracts = salesContractService.selectByDate2(page,itemsPerPage,dateSearch);
            totalDesserts = salesContractService.selectByDateCounts(dateSearch);
            params.put("totalDesserts", totalDesserts);
            params.put("rows", salesContracts);
        } else if (btnColor.equals("#546E7A") && !staff.getId().equals("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2")) {
            salesContracts = salesContractService.selectByDateForPage(page, itemsPerPage, dateSearch, staffId);
            totalDesserts = salesContractService.selectByDateCounts(dateSearch, staffId);
            params.put("totalDesserts", totalDesserts);
            params.put("rows", salesContracts);
        } else if (btnColor.equals("#43A047") && !staff.getId().equals("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2")) {
            salesContracts = salesContractService.selectByDateAndStaffId(dateSearch, staff.getId(), page, itemsPerPage);
            totalDesserts = salesContractService.selectByDateAndStaffIdCounts(dateSearch, staff.getId());
            params.put("totalDesserts", totalDesserts);
            params.put("rows", salesContracts);
        }


        List<SalesContract> salesContracts1 = salesContractService.selectByDate(dateSearch);
        BigDecimal monthTotalMoney = BigDecimal.valueOf(0);
        if (salesContracts1 != null) {
            for (int i = 0; i < salesContracts1.size(); i++) {
                monthTotalMoney = monthTotalMoney.add(salesContracts1.get(i).getAgreementMoney());
            }
            params.put("monthTotalMoney", monthTotalMoney);
        }

        List<Invoice> invoices = invoiceService.selectByDate(dateSearch);
        BigDecimal monthTotalInvoiced = BigDecimal.valueOf(0);
        if (invoices != null) {
            for (int i = 0; i < invoices.size(); i++) {
                monthTotalInvoiced = monthTotalInvoiced.add(invoices.get(i).getInvoiceMoney());
            }
            params.put("monthTotalInvoiced", monthTotalInvoiced);
        }

//        List<Collection> collections = collectionService.selectByDate(dateSearch);
        List<Collection> collections = collectionService.selectByPDate(dateSearch);
        BigDecimal monthTotalCollected = BigDecimal.valueOf(0);
        if (collections != null) {
            for (int i = 0; i < collections.size(); i++) {
                monthTotalCollected = monthTotalCollected.add(collections.get(i).getCollectMoney());
            }
            params.put("monthTotalCollected", monthTotalCollected);
        }

        return new ResponseModel(params);
    }

    @Operation(description = "获取销售合同附件")
    @GetMapping("showSalesFiles")
    public ResponseModel showSalesFiles(@Parameter String id) {

        if (id != null) {
            List<SalesFiles> salesFiles = salesContractFilesService.select(id);
            return new ResponseModel(salesFiles);
        }
        return new ResponseModel(null);
    }

    @Operation(description = "删除销售合同附件")
    @PutMapping("deleteSalesFile")
    public ResponseModel deleteSalesFile(@RequestBody SalesFiles salesFiles) {
        salesContractFilesService.delete(Integer.valueOf(salesFiles.getId()));
        return new ResponseModel(salesFiles);
    }

    @Operation(description = "获取月开票信息")
    @GetMapping("getMonthInvoiceMSG")
    public ResponseModel getMonthInvoiceMSG(String dateSearch, Integer itemsPerPage, Integer page) {
        Map<String, Object> params = new HashMap<>();
        Integer totalDesserts = invoiceService.selectCountsByDate(dateSearch);
        List<Invoice> invoices = invoiceService.selectByDateForPage(page, itemsPerPage, dateSearch);
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
            invoices.get(i).setTax(salesContract.getTax());
        }
        params.put("invoices", invoices);
        params.put("totalDesserts", totalDesserts);
        return new ResponseModel(params);
    }

    @Operation(description = "获取合同明细")
    @GetMapping("getAgreementMSG")
    public ResponseModel getAgreementMSG(@Parameter String agreementID) {
        Map<String, Object> params = new HashMap<>();
        SalesContract salesContract = salesContractService.selectByID(agreementID);
        if (ObjectUtils.isNotEmpty(salesContract)){
            String status = "未审核";
            List<Collection> collections = collectionService.selectByAgreementIDAndStatus(agreementID, status);
            BigDecimal toCollectMoney = BigDecimal.valueOf(0);
            for (int i = 0; i < collections.size(); i++) {
                toCollectMoney = toCollectMoney.add(collections.get(i).getCollectMoney());
            }
            salesContract.setToCollectMoney(toCollectMoney);
            params.put("salesContract", salesContract);
        }

        return new ResponseModel(params);
    }

    @Operation(description = "导出开票明细")
    @GetMapping("exportInvoice")
    public ResponseModel exportInvoice(@Parameter String dateSearch) {
        List<Invoice> invoices = invoiceService.selectByDate(dateSearch);
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
        }
        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader());
        rows.add(header);
        for (int i = 0; i < invoices.size(); i++) {
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell(invoices.get(i), row.getIndex()));
            rows.add(row);
        }
        String mark = formatMarkTime(new Date());
        String filename = mark + "开票明细报表.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "开票时间", "单位名称", "合同名称", "合同金额", "开票金额", "备注"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 1 || i == 2 || i == 4 || i == 5) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 3 || i == 6) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell(Invoice invoice, int index) {
        String[] headers = new String[]{"Invoice.Index", "Invoice.CreateTime", "Invoice.Company", "Invoice.AgreementName",
                "Invoice.AgreementMoney", "Invoice.InvoiceMoney", "Invoice.Remark"};
        InvoiceModel model = new InvoiceModel();
        model.setIndex(index);
        model.setInvoice(invoice);
        return EntityUtils.getCells(model, headers);
    }

    @Operation(description = "我的销售合同")
    @GetMapping("getMySalesContracts")
    public ResponseModel getMySalesContracts(Integer itemsPerPage, Integer page, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Map<String, Object> params = new HashMap<>();
        List<SalesContract> salesContracts = salesContractService.selectByStaffId(page, itemsPerPage, staff.getId());
        Integer totalDesserts = salesContractService.selectByStaffIdCounts(staff.getId());

        params.put("totalDesserts", totalDesserts);
        params.put("rows", salesContracts);
        return new ResponseModel(params);
    }

    @Operation(description = "获取初始化状态下月销售信息")
    @GetMapping("getMonthSalesContractMSG")
    public ResponseModel getMonthSalesContractMSG(String dateSearch) {
        Map<String, Object> params = new HashMap<>();
        List<SalesContract> salesContracts1 = salesContractService.selectByDate(dateSearch);
        BigDecimal monthTotalMoney = BigDecimal.valueOf(0);
        if (salesContracts1 != null) {
            for (int i = 0; i < salesContracts1.size(); i++) {
                monthTotalMoney = monthTotalMoney.add(salesContracts1.get(i).getAgreementMoney());
            }
            params.put("monthTotalMoney", monthTotalMoney);
        }

        List<Invoice> invoices = invoiceService.selectByDate(dateSearch);
        BigDecimal monthTotalInvoiced = BigDecimal.valueOf(0);
        if (invoices != null) {
            for (int i = 0; i < invoices.size(); i++) {
                monthTotalInvoiced = monthTotalInvoiced.add(invoices.get(i).getInvoiceMoney());
            }
            params.put("monthTotalInvoiced", monthTotalInvoiced);
        }

//        List<Collection> collections = collectionService.selectByDate(dateSearch);
        List<Collection> collections = collectionService.selectByPDate(dateSearch);
        BigDecimal monthTotalCollected = BigDecimal.valueOf(0);
        if (collections != null) {
            for (int i = 0; i < collections.size(); i++) {
                monthTotalCollected = monthTotalCollected.add(collections.get(i).getCollectMoney());
            }
            params.put("monthTotalCollected", monthTotalCollected);
        }

        return new ResponseModel(params);
    }

    @Operation(description = "获取已审核月开票信息")
    @GetMapping("getMonthInvoiceMSGChecked")
    public ResponseModel getMonthInvoiceMSGChecked(Integer itemsPerPage, Integer page, String dateSearch) {
        Map<String, Object> params = new HashMap<>();
        Integer totalDesserts = invoiceService.selectCheckedCountsByDate(dateSearch);
        List<Invoice> invoices = invoiceService.selectCheckedByDateForPage(page, itemsPerPage, dateSearch);
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
            if(invoices.get(i).getTax() == null  || invoices.get(i).getTax().equals("")){
                invoices.get(i).setTax(salesContract.getTax());
            }
            invoices.get(i).setCompanyAddress(salesContract.getCompanyAddress());
            invoices.get(i).setProjectBase(salesContract.getProjectBase());
        }
        params.put("invoices", invoices);
        params.put("totalDesserts", totalDesserts);
        return new ResponseModel(params);
    }

    @Operation(description = "导出已审核月开票信息")
    @GetMapping("exportInvoiceChecked")
    public ResponseModel exportInvoiceChecked(String dateSearch) {

        List<Invoice> invoices = invoiceService.selectCheckedByDate(dateSearch);
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
            invoices.get(i).setTax(invoices.get(i).getTax() != "" && invoices.get(i).getTax() != null ? invoices.get(i).getTax().split("/")[1] : salesContract.getTax());
            invoices.get(i).setCompanyAddress(salesContract.getCompanyAddress());
            invoices.get(i).setProjectBase(salesContract.getProjectBase());
        }
        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader2());
        rows.add(header);
        for (int i = 0; i < invoices.size(); i++) {
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell2(invoices.get(i), row.getIndex()));
            rows.add(row);
        }
        String mark = formatMarkTime(new Date());
        String filename = mark + "财务销售开票明细报表.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader2() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "合同名称", "项目基地", "单位名称", "单位地址", "合同金额", "开票内容", "开票金额", "税率(%)", "开票时间", "开票单位"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 1 || i == 2 || i == 3 || i == 5 || i == 7 || i == 9 || i == 10) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 4 || i == 6) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell2(Invoice invoice, int index) {
        String[] headers = new String[]{"Invoice.Index", "Invoice.AgreementName", "Invoice.ProjectBase", "Invoice.Company", "Invoice.CompanyAddress",
                "Invoice.AgreementMoney", "Invoice.InvoiceContent", "Invoice.InvoiceMoney", "Invoice.Tax", "Invoice.CreateTime", "Invoice.InvoiceCompany"};
        InvoiceModel model = new InvoiceModel();
        model.setIndex(index);
        model.setInvoice(invoice);
        return EntityUtils.getCells(model, headers);
    }

    @Operation(description = "搜索开票信息")
    @GetMapping("searchInvoice")
    public ResponseModel searchInvoice(Integer itemsPerPage, Integer page, String search) {
        Map<String, Object> params = new HashMap<>();
        List<Invoice> invoices = invoiceService.searchInvoice(page, itemsPerPage, search);
        Integer totalDesserts = invoiceService.searchInvoiceCounts(search);
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
            invoices.get(i).setTax(salesContract.getTax());
        }
        params.put("totalDesserts", totalDesserts);
        params.put("invoices", invoices);
        return new ResponseModel(params);
    }

    @Operation(description = "搜索已审核开票")
    @GetMapping("searchInvoiceChecked")
    public ResponseModel searchInvoiceChecked(Integer itemsPerPage, Integer page, String search) {
        Map<String, Object> params = new HashMap<>();
        List<Invoice> invoices = invoiceService.searchInvoiceChecked(page, itemsPerPage, search);
        Integer totalDesserts = invoiceService.searchInvoiceCheckedCounts(search);
        for (int i = 0; i < invoices.size(); i++) {
            SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
            invoices.get(i).setIndex(i + 1);
            invoices.get(i).setAgreementName(salesContract.getAgreementName());
            invoices.get(i).setCompany(salesContract.getCompanyName());
            invoices.get(i).setAgreementMoney(salesContract.getAgreementMoney());
            invoices.get(i).setTax(salesContract.getTax());
        }
        params.put("invoices", invoices);
        params.put("totalDesserts", totalDesserts);
        return new ResponseModel(params);
    }

    @Operation(description = "获取税率类型")
    @GetMapping("getTax")
    public ResponseModel getTax(String agreementID) {
        Map<String, Object> params = new HashMap<>();
        List<SalesContractTax> salesContractTaxes = salesContractTaxService.selectByAgreementID(agreementID);
        params.put("rows", salesContractTaxes);
        return new ResponseModel(params);
    }

    @Operation(description = "新增税率")
    @PutMapping("insertTax")
    public ResponseModel insertTax(@RequestBody SalesContractTax salesContractTax, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        salesContractTax.setAdderID(staff.getId());
        int res = salesContractTaxService.insert(salesContractTax);
        return new ResponseModel(res);
    }

    @Operation(description = "删除税率")
    @PutMapping("deleteTax")
    public ResponseModel deleteTax(@RequestBody SalesContractTax salesContractTax) {
        int res = salesContractTaxService.delete(salesContractTax.getId());
        return new ResponseModel(res);
    }

    @Operation(description = "新增指定项目经理")
    @PutMapping("insertManager")
    public ResponseModel insertManager(@RequestBody SalesContractManager salesContractManager, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        salesContractManager.setAppointManID(staff.getId());
        int res = salesContractManagerService.insert(salesContractManager);
        return new ResponseModel(res);
    }

    @Operation(description = "删除合同项目经理")
    @PutMapping("deleteManager")
    public ResponseModel deleteManager(@RequestBody SalesContractManager salesContractManager) {
        int res = salesContractManagerService.delete(salesContractManager.getId());
        return new ResponseModel(res);
    }

    @Operation(description = "获取合同中所指定的项目经理")
    @GetMapping("getManagers")
    public ResponseModel getManagers(String agreementID) {
        Map<String, Object> params = new HashMap<>();
        List<SalesContractManager> salesContractManagers = salesContractManagerService.selectByAgreementID(agreementID);
        params.put("rows", salesContractManagers);
        return new ResponseModel(params);
    }

    @Operation(description = "获取项目经理下的销售合同")
    @GetMapping("getSalesContractByManager")
    public ResponseModel getSalesContractByManager(Integer itemsPerPage, Integer page, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Map<String, Object> params = new HashMap<>();
        List<SalesContract> salesContracts = salesContractManagerService.selectByManager(page, itemsPerPage, staff.getId());
        Integer totalDesserts = salesContractManagerService.selectByManagerCounts(staff.getId());

        params.put("rows", salesContracts);
        params.put("totalDesserts", totalDesserts);
        return new ResponseModel(params);
    }

    @Operation(description = "获取合同下同类型税率开票记录")
    @GetMapping("getTotalPercentByTax")
    public ResponseModel getTotalPercentByTax(String tax, String agreementID) {
        Double totalPercent = 0.00;
        if (tax != null && tax != ""){
            List<Invoice> invoices = invoiceService.selectByTax(tax, agreementID);
            if (invoices != null) {
                for (int a = 0; a < invoices.size(); a++) {
                    totalPercent = totalPercent + Double.valueOf(invoices.get(a).getPercent().replace("%", ""));
                }
            }
        }else if (tax == null || tax.equals("")){
            List<Invoice> invoices = invoiceService.selectByNullTax(agreementID);
            if (invoices != null){
                for (int b = 0;b < invoices.size(); b++){
                    totalPercent = totalPercent + Double.valueOf(invoices.get(b).getPercent().replace("%",""));
                }
            }
        }

        return new ResponseModel(totalPercent);
    }

    @Operation(description = "获取项目地")
    @GetMapping("getProjectMap")
    public ResponseModel getProjectMap(){
        return new ResponseModel(salesContractService.getProjectMap());
    }

    @Operation(description = "设置经纬度")
    @PostMapping("setProjectMap")
    public ResponseModel setProjectMap(){
        return new ResponseModel(salesContractService.setProjectLngLat());
    }
}
