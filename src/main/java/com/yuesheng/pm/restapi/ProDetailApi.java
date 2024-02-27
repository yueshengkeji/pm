package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProDetail;
import com.yuesheng.pm.entity.ProDetailOwe;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ProDetailOweService;
import com.yuesheng.pm.service.ProDetailService;
import com.yuesheng.pm.service.ProPutDetailService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("api/proDetail")
public class ProDetailApi extends BaseApi {
    @Autowired
    private ProPutDetailService proPutDetailService;
    @Autowired
    private ProDetailService proDetailService;
    @Autowired
    private ProDetailOweService proDetailOweService;

    /**
     * 导出采购明细
     */
    @GetMapping("exportExcel")
    public ResponseModel exportExcel(String year,
                                     String searchText,
                                     Integer page,
                                     Integer itemsPerPage,
                                     @RequestParam(defaultValue = "a.date") String sortBy,
                                     @RequestParam(defaultValue = "asc") String sortDesc,
                                     String type) {
        String fileName = type + year + "-" + DateUtil.format(new Date(), "MM-dd") + "采购明细对账单.xlsx";
//        String tempName = WebParam.assetsPath + fileName;
//        if (new File(tempName).isFile()) {
//            return ResponseModel.ok(WebParam.TEMP_FOLDER + fileName);
//        }
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", ("".equals(searchText) ? null : searchText));
        params.put("year", year);
        params.put("type", ("all".equals(type) ? null : type));
        //a.date asc
        List<ProDetail> details = new ArrayList<>();
        List<ProDetail> proDetails = null;
        if (itemsPerPage > 100 && itemsPerPage < 2000) {
            int pageNumber = 100;
            while (itemsPerPage > (pageNumber * (page - 1))) {
                startPage(page, pageNumber, sortBy, sortDesc);
                proDetails = proDetailService.getDetailListByYear(params);
                fillProDetailOwe(proDetails);
                details.addAll(proDetails);
                page++;
            }
            params.clear();

            fileName = ExcelParse.writeExcel(details, fileName, new String[]{
                    "Series", "ComName", "MoneyOwe.OweMoney", "YearPro", "YetMoney", "EndOwe", "Tax", "SettleType", "PutMoney", "Error", "OweBillMoney",
                    "SubjectSeries", "Remark", "PaperOwe"
            }, ProDetail.class);
            return ResponseModel.ok(fileName);
        } else {
            if (itemsPerPage == -1 || itemsPerPage >= 2000) {
                page = 1;
            }
            long time = System.currentTimeMillis();
            startPage(page, 2000, sortBy, sortDesc);
            proDetails = proDetailService.getDetailListByYear(params);
            LogManager.getLogger("mylog").info("query detail time:"+(System.currentTimeMillis()-time));
            time = System.currentTimeMillis();
            fillProDetailOwe(proDetails);
            LogManager.getLogger("mylog").info("query detail time2:"+(System.currentTimeMillis()-time));

            params.clear();
            fileName = ExcelParse.writeExcel(proDetails, fileName, new String[]{
                    "Series", "ComName", "YearOwe", "YearPro", "YetMoney", "EndOwe", "Tax", "SettleType", "PutMoney", "Error", "OweBillMoney",
                    "SubjectSeries", "Remark", "PaperOwe"
            }, ProDetail.class);
            return ResponseModel.ok(fileName);
        }
    }

    @GetMapping("list")
    public ResponsePage list(String year,
                             String searchText,
                             String type,
                             Integer page,
                             Integer itemsPerPage,
                             @RequestParam(defaultValue = "a.date") String sortBy,
                             @RequestParam(defaultValue = "asc") String sortDesc) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", ("".equals(searchText) ? null : searchText));
        params.put("year", year);
        params.put("type", ("all".equals(type) ? null : type));
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<ProDetail> proDetails = (Page<ProDetail>) proDetailService.getDetailByYearV2(params);
        fillProDetailOwe(proDetails);
        return ResponsePage.ok(proDetails);
    }

    @Operation(description = "新增对账单单位")
    @PutMapping
    public ResponseModel insert(@RequestBody ProDetail detail, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        detail.setComName(detail.getCompany().getName());
        Map<String, Object> result = proDetailService.addDetail(detail, staff);
        if (result.get("status").equals("1")) {
            //期初欠票
            ProDetailOwe owe = detail.getBillOwe();
            if (!Objects.isNull(owe)) {
                owe.setMainId(detail.getId());
                owe.setStaff(staff);
                proDetailOweService.addOwe(owe);
            }
            //期初欠款
            ProDetailOwe money = detail.getMoneyOwe();
            if (!Objects.isNull(money)) {
                money.setMainId(detail.getId());
                money.setStaff(staff);
                proDetailOweService.addOwe(money);
            }
            return ResponseModel.ok(detail);
        } else {
            return ResponseModel.error(result.get("msg").toString());
        }
    }

    @Operation(description = "修改对账单信息")
    @PostMapping
    public ResponseModel update(@RequestBody ProDetail detail, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        detail.setComName(detail.getCompany().getName());
        detail.setLastDate(DateFormat.getDate());
        detail.setLastStaff(staff);
        proDetailService.updateDetail(detail);
        /*ProDetailOwe billOwe = detail.getBillOwe();
        ProDetailOwe moneyOwe = detail.getMoneyOwe();
        if (!Objects.isNull(billOwe)) {
            billOwe.setStaff(staff);
            billOwe.setMainId(detail.getId());
            proDetailOweService.updateMoney(billOwe);
        }
        if (!Objects.isNull(moneyOwe)) {
            moneyOwe.setStaff(staff);
            moneyOwe.setMainId(detail.getId());
            proDetailOweService.updateMoney(moneyOwe);
        }*/
        return ResponseModel.ok(detail);
    }

    @Operation(description = "删除对账单数据")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        proDetailService.deleteDetail(id);
        proPutDetailService.deleteDetailForMain(id);
        return ResponseModel.ok(id);
    }

    @Operation(description = "修改年初欠款/欠票金额")
    @PostMapping("updateOwe")
    public ResponseModel updateOwe(@RequestBody ProDetailOwe owe,
                                   @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        owe.setStaff(staff);
        proDetailOweService.updateMoney(owe);
        return ResponseModel.ok(owe);
    }


    @Operation(description = "新增年初欠款/欠票金额")
    @PutMapping("insertOwe")
    public ResponseModel insertOwe(@RequestBody ProDetailOwe owe) {
        proDetailOweService.addOwe(owe);
        return ResponseModel.ok(owe);
    }

    @Operation(description = "修改实际欠款/实际欠票金额")
    @PostMapping("updateBillMoney")
    public ResponseModel updateBillMoney(@RequestBody ProDetail detail) {
        proDetailService.updateMoney(detail);
        return ResponseModel.ok(detail);
    }


    private void fillProDetailOwe(final List<ProDetail> details) {
        for (ProDetail detail : details) {
            //本年购入-入库金额=与采购误差
            setData(detail);
        }
    }

    private void setData(ProDetail detail) {
        if (Objects.isNull(detail.getYearPro())) {
            detail.setYearPro(0.0);
        }
        if (Objects.isNull(detail.getPutMoney())) {
            detail.setPutMoney(0.0);
        }
        if (Objects.isNull(detail.getYetMoney())) {
            detail.setYetMoney(0.0);
        }
        if (Objects.isNull(detail.getBillMoney())) {
            detail.setBillMoney(0.0);
        }
        detail.setError(detail.getYearPro() - detail.getPutMoney());
//        detail.setMoneyOwe(proDetailOweService.getOweByDate(null, detail.getId(), 1));
//        detail.setBillOwe(proDetailOweService.getOweByDate(null, detail.getId(), 0));
        //年初欠票
        if (Objects.isNull(detail.getYearBillFinance())) {
//            ProDetailOwe mo = new ProDetailOwe();
//            mo.setOweMoney(0.0);
//            mo.setMainId(detail.getId());
//            mo.setType(0);
//            detail.setMoneyOwe(mo);
            detail.setYearBillFinance(0.0);
        }
        //年初欠款
        if (Objects.isNull(detail.getYearOwe())) {
            // billOwe
//            ProDetailOwe mo = new ProDetailOwe();
//            mo.setOweMoney(0.0);
//            mo.setMainId(detail.getId());
//            mo.setType(1);
//            detail.setBillOwe(mo);
            detail.setYearOwe(0.0);
        }
        //期末欠款(实际欠款) = 期初欠款 + 本年购入 - 本年已付款
        detail.setEndOwe(detail.getYearOwe() + detail.getYearPro() - detail.getYetMoney());
        //欠票金额(实际欠票) = 期初欠票 + 本年购入款 - 收票金额
        detail.setOweBillMoney(detail.getYearBillFinance() + detail.getYearPro() - detail.getBillMoney());
        //账面欠款 = 实际欠款 - 实际欠票
        detail.setPaperOwe(detail.getEndOwe() - detail.getOweBillMoney());
        //更新数据统计字段
        // proDetailService.updateMoney(detail);
    }
}
