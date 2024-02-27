package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购合同登记
 *
 * @author ssk
 * @since 2022-1-10
 */
@Tag(name = "采购合同登记")
@RestController
@RequestMapping("api/purchaseContract")
public class PurchaseContractApi {

    @Autowired
    private PurchaseContractService purchaseContractService;
    @Autowired
    private PurchaseContractFilesService purchaseContractFilesService;
    @Autowired
    private PurchaseCollectService purchaseCollectService;
    @Autowired
    private PurchasePayService purchasePayService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PayService payServiceImpl;

    @Operation(description = "获取采购合同信息")
    @GetMapping("getPurchaseContract")
    public ResponseModel getPurchaseContractService(Integer itemsPerPage, Integer page) {
        Map<String, Object> params = new HashMap<>();

        List<PurchaseContract> purchaseContracts = purchaseContractService.selectForPage(page, itemsPerPage);

        Integer totalDesserts = purchaseContractService.selectCounts();

        params.put("rows", purchaseContracts);
        params.put("totalDesserts", totalDesserts);
        return new ResponseModel(params);
    }

    @Operation(description = "判断是否存在相同的合同编号")
    @GetMapping("getPurchaseContractIsExist")
    public ResponseModel getPurchaseContractIsExist(String agreementID) {
        PurchaseContract purchaseContract = purchaseContractService.selectByAgreementID(agreementID);
        String res = null;
        if (purchaseContract != null) {
            res = "已存在";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("result", res);
        return new ResponseModel(params);
    }

    @Operation(description = "新增采购合同登记")
    @PutMapping("insetPurchaseContract")
    public ResponseModel insertPurchaseContract(@RequestBody PurchaseContract purchaseContract, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        purchaseContract.setRegistrant(staff.getName());
        purchaseContract.setCreateDate(DateUtil.getNowDate());
        if (purchaseContract.getPaymentDays() == null){
            purchaseContract.setPaymentDays(0);
        }
        int res = purchaseContractService.insertContract(purchaseContract);
        if (res > 0) {

            PurchaseContract purchaseContract1 = purchaseContractService.selectByAgreementID(purchaseContract.getAgreementID());
            for (int i = 0; i < purchaseContract.getFiles().size(); i++) {
                purchaseContract.getFiles().get(i).setMark(Integer.valueOf(purchaseContract1.getId()));
                purchaseContractFilesService.insert(purchaseContract.getFiles().get(i));
            }

            payServiceImpl.insert(purchaseContract.getCompanyName(),"","",purchaseContract.getAgreementID(),DateUtil.format(addDate(purchaseContract.getSignDate(),purchaseContract.getPaymentDays())),String.valueOf(purchaseContract.getPaymentDays()),purchaseContract.getRemark(),purchaseContract.getAgreementMoney(),staff);
            Company company = new Company();
            List<Company> seek = companyService.seek(purchaseContract.getCompanyName());
            if (seek.size() != 0){
                company = seek.get(0);
                company.setAddress(purchaseContract.getCompanyAddress());
                company.setOpenAccount(purchaseContract.getBankName());
                company.setBankNumber(purchaseContract.getBankAccount());
                companyService.updateCompany(company);
            }else if (seek.size() == 0){
                company.setName(purchaseContract.getCompanyName());
                company.setAddress(purchaseContract.getCompanyAddress());
                company.setOpenAccount(purchaseContract.getBankName());
                company.setBankNumber(purchaseContract.getBankAccount());
                companyService.insert(company,staff);
            }
        }

        return new ResponseModel(purchaseContract);
    }

    @Operation(description = "删除采购合同登记")
    @PutMapping("deletePurchaseContract")
    public ResponseModel deletePurchaseContract(@RequestBody PurchaseContract purchaseContract, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        int res = purchaseContractService.deleteContract(Integer.valueOf(purchaseContract.getId()));
        if (res > 0) {
            purchaseCollectService.deleteByID(Integer.valueOf(purchaseContract.getId()));
            purchasePayService.deleteByID(Integer.valueOf(purchaseContract.getId()));
            purchaseContractFilesService.deleteByMark(Integer.valueOf(purchaseContract.getId()));
        }
        return new ResponseModel(res);
    }

    @Operation(description = "更新采购合同登记")
    @PutMapping("updatePurchaseContract")
    public ResponseModel updatePurchaseContract(@RequestBody PurchaseContract purchaseContract, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        purchaseContract.setRegistrant(staff.getName());
        if (purchaseContract.getPaymentDays() == null){
            purchaseContract.setPaymentDays(0);
        }
        int res = purchaseContractService.updateContract(purchaseContract);
        if (res > 0) {

            payServiceImpl.update(purchaseContract.getCompanyName(),"","",purchaseContract.getAgreementID(),DateUtil.format(addDate(purchaseContract.getSignDate(),purchaseContract.getPaymentDays())),String.valueOf(purchaseContract.getPaymentDays()),purchaseContract.getRemark(),purchaseContract.getAgreementMoney(),staff);

            for (int i = 0; i < purchaseContract.getFiles().size(); i++) {
                purchaseContractFilesService.insert(purchaseContract.getFiles().get(i));
            }

            List<PurchaseCollect> purchaseCollects = purchaseCollectService.selectByID(Integer.valueOf(purchaseContract.getId()));
            if (purchaseCollects != null){
                for (int i = 0;i < purchaseCollects.size();i++){
                    purchaseCollects.get(i).setPercent((purchaseCollects.get(i).getCollectMoney().multiply(BigDecimal.valueOf(100))).divide(purchaseContract.getAgreementMoney(),2,BigDecimal.ROUND_HALF_UP) + "");
                    int res2 = purchaseCollectService.update(purchaseCollects.get(i));
                    if (res2 > 0){
                        payServiceImpl.updateInvoicing(purchaseContract.getAgreementID(),purchaseCollects.get(i).getCollectID(),purchaseCollects.get(i).getRemark(),purchaseCollects.get(i).getCollectMoney(),staff);
                    }
                }
            }

            List<PurchasePay> purchasePays = purchasePayService.selectByID(Integer.valueOf(purchaseContract.getId()));
            if (purchasePays != null){
                for (int j = 0;j < purchasePays.size();j++){
                    purchasePays.get(j).setPercent((purchasePays.get(j).getPayMoney().multiply(BigDecimal.valueOf(100))).divide(purchaseContract.getAgreementMoney(),2,BigDecimal.ROUND_HALF_UP) + "");
                    PurchaseCollect purchaseCollect = purchaseCollectService.selectByPMark(purchasePays.get(j).getpMark());
                    purchasePays.get(j).setpDate(addDate(purchaseCollect.getCollectDate(),purchaseContract.getPaymentDays()));
                    int res3 = purchasePayService.update(purchasePays.get(j));
                    if (res3 > 0){
                        payServiceImpl.updateProceeds(purchaseContract.getAgreementID(),purchasePays.get(j).getPayID(),purchasePays.get(j).getRemark(),purchasePays.get(j).getPayMoney(),staff);
                    }
                }
            }

        }
        return new ResponseModel(res);
    }

    @Operation(description = "获取采购合同附件")
    @GetMapping("showPurchaseFiles")
    public ResponseModel showPurchaseFiles(@Parameter String id) {

        if (id != null) {
            List<PurchaseFiles> purchaseFiles = purchaseContractFilesService.select(Integer.valueOf(id));
            return new ResponseModel(purchaseFiles);
        }
        return new ResponseModel(null);
    }

    @Operation(description = "删除采购合同附件")
    @PutMapping("deletePurchaseFile")
    public ResponseModel deletePurchaseFile(@RequestBody PurchaseFiles purchaseFiles) {
        purchaseContractFilesService.delete(Integer.valueOf(purchaseFiles.getId()));
        return new ResponseModel(purchaseFiles);
    }

    @Operation(description = "获取收票信息")
    @GetMapping("getPurchaseCollect")
    public ResponseModel getPurchaseCollect(@Parameter Integer id) {
        Map<String, Object> params = new HashMap<>();
        List<PurchaseCollect> purchaseCollects = purchaseCollectService.selectByID(id);
        params.put("rows", purchaseCollects);
        return new ResponseModel(params);
    }

    @Operation(description = "新增收票登记")
    @PutMapping("insertPurchaseCollect")
    public ResponseModel insertPurchaseCollect(@RequestBody PurchaseCollect purchaseCollect, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        purchaseCollect.setRegistrant(staff.getName());
        purchaseCollect.setpMark(formatMarkTime(new Date()));
        int res = purchaseCollectService.insert(purchaseCollect);
        if (res > 0) {

            PurchaseContract purchaseContract = purchaseContractService.selectByID(purchaseCollect.getMark());
            purchaseContract.setCollectedMoney(purchaseContract.getCollectedMoney().add(purchaseCollect.getCollectMoney()));
            purchaseContractService.updateContract(purchaseContract);

            payServiceImpl.insertInvoicing(purchaseContract.getAgreementID(),purchaseCollect.getCollectID(),purchaseCollect.getRemark(),purchaseCollect.getCollectMoney(),staff);

            //预支付
            PurchasePay purchasePay = new PurchasePay();
            purchasePay.setPayID(purchaseCollect.getCollectID());
            purchasePay.setPayMoney(purchaseCollect.getCollectMoney());
            purchasePay.setPercent(purchaseCollect.getPercent());
            purchasePay.setpDate(addDate(purchaseCollect.getCollectDate(), purchaseCollect.getPaymentDays()));
            purchasePay.setMark(purchaseCollect.getMark());
            purchasePay.setpMark(purchaseCollect.getpMark());
            purchasePayService.insert(purchasePay);

        }
        return new ResponseModel(res);
    }

    @Operation(description = "删除收票登记")
    @PutMapping("deletePurchaseCollect")
    public ResponseModel deletePurchaseCollect(@RequestBody PurchaseCollect purchaseCollect, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        int res = purchaseCollectService.delete(Integer.valueOf(purchaseCollect.getId()));
        if (res > 0) {

            PurchaseContract purchaseContract = purchaseContractService.selectByID(purchaseCollect.getMark());
            purchaseContract.setCollectedMoney(purchaseContract.getCollectedMoney().subtract(purchaseCollect.getCollectMoney()));
            purchaseContractService.updateContract(purchaseContract);

            payServiceImpl.deleteInvoicing(purchaseContract.getAgreementID(),purchaseCollect.getCollectID(),staff);

            //删除预支付
            PurchasePay purchasePay = purchasePayService.selectByPMark(purchaseCollect.getpMark());
            if (purchasePay.getStatus().equals("已审核")) {
                int res2 = purchasePayService.deleteByPMark(purchaseCollect.getpMark());
                if (res2 > 0){
                    purchaseContract.setPaidMoney(purchaseContract.getPaidMoney().subtract(purchasePay.getPayMoney()));
                    purchaseContractService.updateContract(purchaseContract);
                }
            }

        }
        return new ResponseModel(purchaseCollect);
    }

    @Operation(description = "获取支付信息")
    @GetMapping("getPurchasePay")
    public ResponseModel getPurchasePay(@Parameter Integer id) {
        Map<String, Object> params = new HashMap<>();
        List<PurchasePay> purchasePays = purchasePayService.selectByID(id);
        params.put("rows", purchasePays);
        return new ResponseModel(params);
    }

    @Operation(description = "新增支付登记")
    @PutMapping("insertPurchasePay")
    public ResponseModel insertPurchasePay(@RequestBody PurchasePay purchasePay, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        purchasePay.setRegistrant(staff.getName());

        int res = purchasePayService.insert(purchasePay);
        if (res > 0){
            payServiceImpl.insertProceeds(purchasePay.getAgreementID(),purchasePay.getPayID(),purchasePay.getRemark(),purchasePay.getPayMoney(),staff);
        }
        return new ResponseModel(purchasePay);
    }

    @Operation(description = "删除支付登记")
    @PutMapping("deletePurchasePay")
    public ResponseModel deletePurchasePay(@RequestBody PurchasePay purchasePay, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        int res = purchasePayService.delete(Integer.valueOf(purchasePay.getId()));
        if (res > 0) {

            if (purchasePay.getStatus().equals("已审核")) {
                PurchaseContract purchaseContract = purchaseContractService.selectByID(purchasePay.getMark());
                purchaseContract.setPaidMoney(purchaseContract.getPaidMoney().subtract(purchasePay.getPayMoney()));
                purchaseContractService.updateContract(purchaseContract);
            }

            payServiceImpl.deleteProceeds(purchasePay.getAgreementID(),purchasePay.getPayID(),staff);
        }
        return new ResponseModel(purchasePay);
    }

    @Operation(description = "审核状态")
    @PutMapping("checkStatus")
    public ResponseModel checkStatus(@RequestBody PurchasePay purchasePay, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        String status = purchasePay.getStatus();
        purchasePay.setRegistrant(staff.getName());
        if (purchasePay.getpDate() != null){
            purchasePay.setPayDate(DateUtil.getNowDate());
        }
        if (status.equals("已审核")) {
            int res = purchasePayService.update(purchasePay);
            if (res > 0) {
                PurchaseContract purchaseContract = purchaseContractService.selectByID(purchasePay.getMark());
                purchaseContract.setPaidMoney(purchaseContract.getPaidMoney().add(purchasePay.getPayMoney()));
                int res2 = purchaseContractService.updateContract(purchaseContract);
                if (res2 > 0){
                    payServiceImpl.proceedsStateChange(purchasePay.getAgreementID(),purchasePay.getPayID(),true,purchasePay.getPayMoney(),staff);
                }
            }
        } else if (status.equals("未审核")) {
            int res = purchasePayService.update(purchasePay);
            if (res > 0) {
                PurchaseContract purchaseContract = purchaseContractService.selectByID(purchasePay.getMark());
                purchaseContract.setPaidMoney(purchaseContract.getPaidMoney().subtract(purchasePay.getPayMoney()));
                int res3 = purchaseContractService.updateContract(purchaseContract);
                if (res3 > 0){
                    payServiceImpl.proceedsStateChange(purchasePay.getAgreementID(),purchasePay.getPayID(),false,purchasePay.getPayMoney(),staff);
                }
            }
        }
        return new ResponseModel(purchasePay);
    }

    @Operation(description = "查询月销售合同登记")
    @GetMapping("monthPurchaseContract")
    public ResponseModel getMonthPurchaseContract(String dateSearch) {
        Map<String, Object> params = new HashMap<>();
        List<PurchaseContract> purchaseContracts = purchaseContractService.selectByDateForPage(dateSearch);

        List<PurchaseCollect> purchaseCollects = purchaseCollectService.selectByDate(dateSearch);
        BigDecimal monthTotalCollected = BigDecimal.valueOf(0);
        if (purchaseCollects != null){
            for (int i = 0;i < purchaseCollects.size();i++){
                monthTotalCollected = monthTotalCollected.add(purchaseCollects.get(i).getCollectMoney());
            }
            params.put("monthTotalCollected",monthTotalCollected);
        }

        List<PurchasePay> purchasePays = purchasePayService.selectByDate(dateSearch);
        BigDecimal monthTotalPaid = BigDecimal.valueOf(0);
        if (purchasePays != null){
            for (int i = 0;i < purchasePays.size();i++){
                monthTotalPaid = monthTotalPaid.add(purchasePays.get(i).getPayMoney());
            }
            params.put("monthTotalPaid",monthTotalPaid);
        }
        params.put("rows", purchaseContracts);
        return new ResponseModel(params);
    }

    @Operation(description = "搜索功能")
    @GetMapping("getSearchContract")
    public ResponseModel getSearchContract(String search) {
        Map<String, Object> params = new HashMap<>();
        List<PurchaseContract> purchaseContracts = purchaseContractService.selectBySearch(search);
        params.put("rows", purchaseContracts);
        return new ResponseModel(params);
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

    public String formatDateForID(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd#");
        String format = sdf.format(date);
        return format;
    }
}
