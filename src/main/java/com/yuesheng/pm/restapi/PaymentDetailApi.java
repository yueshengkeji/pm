package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.PaymentDetail;
import com.yuesheng.pm.model.PaymentData;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.PaymentDetailService;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "合同付款明细管理")
@RestController
@RequestMapping("api/paymentDetail")
public class PaymentDetailApi extends BaseApi {
    @Autowired
    private PaymentDetailService paymentDetailService;

    @Operation(description = "获取合同付款明细列表")
    @GetMapping("payment/{payId}")
    public ResponseModel listByPayment(@Parameter(name="付款单主键") @PathVariable String payId) {
        return new ResponseModel(paymentDetailService.getDetailByPayId(payId));
    }

    @Operation(description = "获取合同付款明细")
    @GetMapping("contract/{contractId}")
    public ResponseModel listByContract(@Parameter(name="合同主键") @PathVariable String contractId) {
        return new ResponseModel(paymentDetailService.getDetailByContract(contractId));
    }

    @Operation(description = "根据单位分组获取本年合同付款报表(未付款)")
    @GetMapping("company")
    public ResponseModel listByCompany(String year) {
        String startTime;
        String endTime;
        if (StringUtils.isNotBlank(year)) {
            startTime = DateUtil.getYearFirstDay(year);
            endTime = DateUtil.getYearEndDay(year);
        } else {
            startTime = DateUtil.getYearFirstDay();
            endTime = DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        }


        List<PaymentData> dataList = paymentDetailService.getDetailByCompanyGroup(startTime, endTime, 0);
        for (PaymentData pd : dataList) {
            pd.setYtMoney(paymentDetailService.getPayMoneyByCompany(pd.getCid(), startTime, endTime, 1));
        }
        return new ResponseModel(dataList);
    }

    @Operation(description = "根据日期查询合同付款明细")
    @GetMapping("listByDate")
    public ResponsePage listByDate(String startDate, String endDate, Integer type, Integer page, Integer itemsPerPage, String sortBy, Boolean sortDesc) {
        startPage(page, itemsPerPage, sortBy, sortDesc);
        List<PaymentDetail> details = paymentDetailService.getPayDetailByDate(startDate, endDate, type);
        return ResponsePage.ok((Page) details);
    }

    @Operation(description = "导出合同付款明细")
    @GetMapping("exportByDate")
    public ResponseModel listByDate(String startDate, String endDate, Integer type) {
        if (StringUtils.isBlank(startDate)) {
            return ResponseModel.error("请指定查询日期");
        }
        Integer day = DateUtil.getOffsetDays(DateUtil.parse(startDate, DateUtil.PATTERN_CLASSICAL_SIMPLE), DateUtil.parse(endDate, DateUtil.PATTERN_CLASSICAL_SIMPLE));
        if (day > 160) {
            return ResponseModel.error("最大查询160天内数据");
        }
        List<PaymentDetail> details = paymentDetailService.getPayDetailByDate(startDate, endDate, type);
        String filePath = startDate + "到" + endDate + "付款报表.xlsx";
        String[] headers = new String[]{"合同名称", "单位名称", "批准(审核)付款时间", "申请付款金额", "批准金额", "申请人"};
        String[] headerField = new String[]{"Contract.Name", "Company.Name", "Payment.ApproveDate", "ApplyMoney", "Ratify", "Payment.Staff.Name"};
        filePath = ExcelParse.writeExcel(details, filePath, headers, headerField);
        return ResponseModel.ok(filePath);
    }

}
