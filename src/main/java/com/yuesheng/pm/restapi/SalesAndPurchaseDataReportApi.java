package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售与采购合同数据报表
 * @author ssk
 * @since 2022-2-17
 */
@Tag(name = "销售与采购合同数据报表")
@RestController
@RequestMapping("api/salesAndPurchaseDataReport")
public class SalesAndPurchaseDataReportApi {

    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private PurchaseContractService purchaseContractService;
    @Autowired
    private PurchaseCollectService purchaseCollectService;
    @Autowired
    private PurchasePayService purchasePayService;

    @Operation(description = "获取年数据报表")
    @GetMapping("getYearDataReport")
    public ResponseModel getYearData(String dateSearch){
        Map<String,Object> params = new HashMap<>();
        if (dateSearch.equals("全部")){
            List<SalesContract> salesContractsAll = salesContractService.selectAll();
            List<Invoice> invoices = invoiceService.selectAllDone();
            BigDecimal allTotalInvoice = BigDecimal.valueOf(0);//开票
            BigDecimal allTotalReceive = BigDecimal.valueOf(0);//收款
            BigDecimal allTotalSalesMoney = BigDecimal.valueOf(0);//销售总额
            BigDecimal allTaxAmount = BigDecimal.valueOf(0);//应缴税额

            if (salesContractsAll != null){
                for (int k = 0;k < salesContractsAll.size();k++){
//                    allTotalInvoice = allTotalInvoice.add(salesContractsAll.get(k).getInvoicedMoney());
                    allTotalReceive = allTotalReceive.add(salesContractsAll.get(k).getCollectedMoney());
                    allTotalSalesMoney = allTotalSalesMoney.add(salesContractsAll.get(k).getAgreementMoney());
//                    if (salesContractsAll.get(k).getTax() != null){
//                        allTaxAmount = allTaxAmount.add(salesContractsAll.get(k).getAgreementMoney().multiply(BigDecimal.valueOf(Integer.valueOf(salesContractsAll.get(k).getTax())).divide(BigDecimal.valueOf(100))));
//                    }
                }
            }
            if (invoices != null){
                for (int i = 0;i < invoices.size();i++){
                    allTotalInvoice = allTotalInvoice.add(invoices.get(i).getInvoiceMoney());
                    if (invoices.get(i).getTax() == null || invoices.get(i).getTax().equals("")){
                        SalesContract salesContract = salesContractService.selectByID(invoices.get(i).getAgreementID());
                        String tax = salesContract.getTax();
                        System.out.println(tax);
                        if (salesContract.getTax() != null || "".equals(salesContract.getTax())){
                            allTaxAmount = allTaxAmount.add(invoices.get(i).getInvoiceMoney().multiply(BigDecimal.valueOf(Double.valueOf(salesContract.getTax())).divide(BigDecimal.valueOf(100))));
                        }
                    }else {
                        allTaxAmount = allTaxAmount.add(invoices.get(i).getInvoiceMoney().multiply(BigDecimal.valueOf(Double.valueOf(invoices.get(i).getTax().split("/")[1])).divide(BigDecimal.valueOf(100))));
                    }
                }
            }

            List<PurchaseContract> purchaseContractsAll = purchaseContractService.selectAll();
            BigDecimal allTotalCollected = BigDecimal.valueOf(0);//收票
            BigDecimal allTotalPay = BigDecimal.valueOf(0);//支出
            BigDecimal allTotalPurchaseMoney = BigDecimal.valueOf(0);//采购总额

            if (purchaseContractsAll != null){
                for (int i = 0;i < purchaseContractsAll.size();i++){
                    allTotalCollected = allTotalCollected.add(purchaseContractsAll.get(i).getCollectedMoney());
                    allTotalPay = allTotalPay.add(purchaseContractsAll.get(i).getPaidMoney());
                    allTotalPurchaseMoney = allTotalPurchaseMoney.add(purchaseContractsAll.get(i).getAgreementMoney());
                }
            }

            params.put("allTotalInvoice",allTotalInvoice);
            params.put("allTotalReceive",allTotalReceive);
            params.put("allTotalSalesMoney",allTotalSalesMoney);
            params.put("allTaxAmount",allTaxAmount);
            params.put("allTotalCollected",allTotalCollected);
            params.put("allTotalPay",allTotalPay);
            params.put("allTotalPurchaseMoney",allTotalPurchaseMoney);
        }else {
            String year = dateSearch.substring(0, 4);
            List<SalesContract> salesContracts = salesContractService.selectByDateYear(year);

            BigDecimal yearTotalSalesMoney = BigDecimal.valueOf(0);//销售总额
//            BigDecimal yearTaxAmount = BigDecimal.valueOf(0);//应缴税额

            if (salesContracts != null) {
                for (int i = 0; i < salesContracts.size(); i++) {
                    yearTotalSalesMoney = yearTotalSalesMoney.add(salesContracts.get(i).getAgreementMoney());
//                    if (salesContracts.get(i).getTax() != null) {
//                        yearTaxAmount = yearTaxAmount.add(salesContracts.get(i).getAgreementMoney().multiply(BigDecimal.valueOf(Integer.valueOf(salesContracts.get(i).getTax())).divide(BigDecimal.valueOf(100))));
//                    }
                }
            }

            List<PurchaseContract> purchaseContracts = purchaseContractService.selectByDateYear(year);

            BigDecimal yearTotalPurchaseMoney = BigDecimal.valueOf(0);//采购总额

            if (purchaseContracts != null) {
                for (int i = 0; i < purchaseContracts.size(); i++) {
                    yearTotalPurchaseMoney = yearTotalPurchaseMoney.add(purchaseContracts.get(i).getAgreementMoney());
                }
            }

            List<String> YM = new ArrayList();
            YM.add(year + "-01");
            YM.add(year + "-02");
            YM.add(year + "-03");
            YM.add(year + "-04");
            YM.add(year + "-05");
            YM.add(year + "-06");
            YM.add(year + "-07");
            YM.add(year + "-08");
            YM.add(year + "-09");
            YM.add(year + "-10");
            YM.add(year + "-11");
            YM.add(year + "-12");

            List<BigDecimal> invoiceYearData = new ArrayList<>();
            List<BigDecimal> receiveYearData = new ArrayList<>();
            List<BigDecimal> collectedYearData = new ArrayList<>();
            List<BigDecimal> payYearData = new ArrayList<>();
            List<BigDecimal> salesData = new ArrayList<>();
            List<BigDecimal> purchaseData = new ArrayList<>();

            BigDecimal yearTotalInvoice = BigDecimal.valueOf(0);//开票
            BigDecimal yearTotalReceive = BigDecimal.valueOf(0);//收款
            BigDecimal yearTotalCollected = BigDecimal.valueOf(0);//收票
            BigDecimal yearTotalPay = BigDecimal.valueOf(0);//支出
            BigDecimal yearTaxAmount = BigDecimal.valueOf(0);//应缴税额

            BigDecimal tempTaxAmount = BigDecimal.valueOf(0);
            BigDecimal tempInvoice = BigDecimal.valueOf(0);
            BigDecimal tempReceive = BigDecimal.valueOf(0);
            BigDecimal tempCollected = BigDecimal.valueOf(0);
            BigDecimal tempPay = BigDecimal.valueOf(0);
            BigDecimal tempSales = BigDecimal.valueOf(0);
            BigDecimal tempPurchase = BigDecimal.valueOf(0);
            for (int i = 0; i < YM.size(); i++) {
                List<Invoice> invoices = invoiceService.selectCheckedByDate(YM.get(i));
                List<Collection> collections = collectionService.selectByDate(YM.get(i));
                List<PurchaseCollect> purchaseCollects = purchaseCollectService.selectByDate(YM.get(i));
                List<PurchasePay> purchasePays = purchasePayService.selectByDate(YM.get(i));
                List<SalesContract> salesContracts1 = salesContractService.selectByDate(YM.get(i));
                List<PurchaseContract> purchaseContracts1 = purchaseContractService.selectByDateForPage(YM.get(i));

                //开票
                if (invoices != null) {
                    for (int j = 0; j < invoices.size(); j++) {
                        tempInvoice = tempInvoice.add(invoices.get(j).getInvoiceMoney());
                        if (invoices.get(j).getTax() == null || invoices.get(j).getTax().equals("")){
                            SalesContract salesContract = salesContractService.selectByID(invoices.get(j).getAgreementID());
                            if (salesContract.getTax() != null || salesContract.getTax().equals("")){
                                tempTaxAmount = tempTaxAmount.add(invoices.get(j).getInvoiceMoney().multiply(BigDecimal.valueOf(Double.valueOf(salesContract.getTax())).divide(BigDecimal.valueOf(100))));
                            }
                        }else {
                            tempTaxAmount = tempTaxAmount.add(invoices.get(j).getInvoiceMoney().multiply(BigDecimal.valueOf(Double.valueOf(invoices.get(j).getTax().split("/")[1])).divide(BigDecimal.valueOf(100))));
                        }
                    }
                    yearTotalInvoice = yearTotalInvoice.add(tempInvoice);
                    yearTaxAmount = yearTaxAmount.add(tempTaxAmount);
                    invoiceYearData.add(tempInvoice);
                    tempInvoice = BigDecimal.valueOf(0);
                    tempTaxAmount = BigDecimal.valueOf(0);
                }

                //收款
                if (collections != null) {
                    for (int j = 0; j < collections.size(); j++) {
                        tempReceive = tempReceive.add(collections.get(j).getCollectMoney());
                    }
                    yearTotalReceive = yearTotalReceive.add(tempReceive);
                    receiveYearData.add(tempReceive);
                    tempReceive = BigDecimal.valueOf(0);
                }

                //采购收票
                if (purchaseCollects != null) {
                    for (int j = 0; j < purchaseCollects.size(); j++) {
                        tempCollected = tempCollected.add(purchaseCollects.get(j).getCollectMoney());
                    }
                    yearTotalCollected = yearTotalCollected.add(tempCollected);
                    collectedYearData.add(tempCollected);
                    tempCollected = BigDecimal.valueOf(0);
                }

                //采购支付
                if (purchasePays != null) {
                    for (int j = 0; j < purchasePays.size(); j++) {
                        tempPay = tempPay.add(purchasePays.get(j).getPayMoney());
                    }
                    yearTotalPay = yearTotalPay.add(tempPay);
                    payYearData.add(tempPay);
                    tempPay = BigDecimal.valueOf(0);
                }

                //销售合同
                if (salesContracts1 != null) {
                    for (int j = 0; j < salesContracts1.size(); j++) {
                        tempSales = tempSales.add(salesContracts1.get(j).getAgreementMoney());
                    }
                    salesData.add(tempSales);
                    tempSales = BigDecimal.valueOf(0);
                }

                //采购合同
                if (purchaseContracts1 != null) {
                    for (int j = 0; j < purchaseContracts1.size(); j++) {
                        tempPurchase = tempPurchase.add(purchaseContracts1.get(j).getAgreementMoney());
                    }
                    purchaseData.add(tempPurchase);
                    tempPurchase = BigDecimal.valueOf(0);
                }
            }


            params.put("yearTotalInvoice", yearTotalInvoice);
            params.put("yearTotalReceive", yearTotalReceive);
            params.put("yearTotalSalesMoney", yearTotalSalesMoney);
            params.put("yearTaxAmount", yearTaxAmount);
            params.put("yearTotalCollected", yearTotalCollected);
            params.put("yearTotalPay", yearTotalPay);
            params.put("invoiceYearData", invoiceYearData);
            params.put("receiveYearData", receiveYearData);
            params.put("collectedYearData", collectedYearData);
            params.put("payYearData", payYearData);
            params.put("yearTotalPurchaseMoney", yearTotalPurchaseMoney);
            params.put("salesData", salesData);
            params.put("purchaseData", purchaseData);
        }
        return new ResponseModel(params);
    }

    @Operation(description = "获取月数据报表")
    @GetMapping("getMonthDataReport")
    public ResponseModel getMonthData(String dateSearch){
        List<SalesContract> salesContracts = salesContractService.selectByDate(dateSearch);

        BigDecimal monthTotalSalesMoney = BigDecimal.valueOf(0);//销售总额
//        BigDecimal monthTaxAmount = BigDecimal.valueOf(0);//应缴税额
        if (salesContracts != null){
            for (int i = 0;i < salesContracts.size();i++){
                monthTotalSalesMoney = monthTotalSalesMoney.add(salesContracts.get(i).getAgreementMoney());
//                if (salesContracts.get(i).getTax() != null){
//                    monthTaxAmount = monthTaxAmount.add(salesContracts.get(i).getAgreementMoney().multiply(BigDecimal.valueOf(Integer.valueOf(salesContracts.get(i).getTax())).divide(BigDecimal.valueOf(100))));
//                }
            }
        }

        List<PurchaseContract> purchaseContracts = purchaseContractService.selectByDateForPage(dateSearch);
        BigDecimal monthTotalPurchaseMoney = BigDecimal.valueOf(0);//采购总额
        if (purchaseContracts != null){
            for (int i = 0;i < purchaseContracts.size();i++){
                monthTotalPurchaseMoney = monthTotalPurchaseMoney.add(purchaseContracts.get(i).getAgreementMoney());
            }
        }

        List<String> YMD = new ArrayList<>();
        for (int a = 1;a < 32;a++){
            if (a < 10){
                YMD.add(dateSearch + "-0" + a);
            }else if (a == 10 || a > 10){
                YMD.add(dateSearch + "-" + a);
            }
        }

        List<BigDecimal> invoiceMonthData = new ArrayList<>();
        List<BigDecimal> receiveMonthData = new ArrayList<>();
        List<BigDecimal> collectedMonthData = new ArrayList<>();
        List<BigDecimal> payMonthData = new ArrayList<>();

        BigDecimal monthTotalInvoice = BigDecimal.valueOf(0);//开票
        BigDecimal monthTotalReceive = BigDecimal.valueOf(0);//收款
        BigDecimal monthTotalCollected = BigDecimal.valueOf(0);//收票
        BigDecimal monthTotalPay = BigDecimal.valueOf(0);//支出

        BigDecimal tempInvoice = BigDecimal.valueOf(0);
        BigDecimal tempReceive = BigDecimal.valueOf(0);
        BigDecimal tempCollected = BigDecimal.valueOf(0);
        BigDecimal tempPay = BigDecimal.valueOf(0);
        for (int i = 0;i < YMD.size();i++){
            List<Invoice> invoices = invoiceService.selectByDay(YMD.get(i));
            List<Collection> collections = collectionService.selectByDay(YMD.get(i));
            List<PurchaseCollect> purchaseCollects = purchaseCollectService.selectByDay(YMD.get(i));
            List<PurchasePay> purchasePays = purchasePayService.selectByDay(YMD.get(i));

            if (invoices != null){
                for (int j = 0;j < invoices.size();j++){
                    tempInvoice = tempInvoice.add(invoices.get(j).getInvoiceMoney());
                }
                monthTotalInvoice = monthTotalInvoice.add(tempInvoice);
                invoiceMonthData.add(tempInvoice);
                tempInvoice = BigDecimal.valueOf(0);
            }

            if (collections != null){
                for (int j = 0;j < collections.size();j++){
                    tempReceive = tempReceive.add(collections.get(j).getCollectMoney());
                }
                monthTotalReceive = monthTotalReceive.add(tempReceive);
                receiveMonthData.add(tempReceive);
                tempReceive = BigDecimal.valueOf(0);
            }

            if (purchaseCollects != null){
                for (int j = 0;j < purchaseCollects.size();j++){
                    tempCollected = tempCollected.add(purchaseCollects.get(j).getCollectMoney());
                }
                monthTotalCollected = monthTotalCollected.add(tempCollected);
                collectedMonthData.add(tempCollected);
                tempCollected = BigDecimal.valueOf(0);
            }

            if (purchasePays != null){
                for (int j = 0;j < purchasePays.size();j++){
                    tempPay = tempPay.add(purchasePays.get(j).getPayMoney());
                }
                monthTotalPay = monthTotalPay.add(tempPay);
                payMonthData.add(tempPay);
                tempPay = BigDecimal.valueOf(0);
            }
        }
        Map<String,Object> params = new HashMap<>();
        params.put("monthTotalInvoice",monthTotalInvoice);
        params.put("monthTotalReceive",monthTotalReceive);
        params.put("monthTotalSalesMoney",monthTotalSalesMoney);
        params.put("monthTotalCollected",monthTotalCollected);
        params.put("monthTotalPay",monthTotalPay);
        params.put("monthTotalPurchaseMoney",monthTotalPurchaseMoney);
        params.put("invoiceMonthData",invoiceMonthData);
        params.put("receiveMonthData",receiveMonthData);
        params.put("collectedMonthData",collectedMonthData);
        params.put("payMonthData",payMonthData);
        return new ResponseModel(params);
    }
}
