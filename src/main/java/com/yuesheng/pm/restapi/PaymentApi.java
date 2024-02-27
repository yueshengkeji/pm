package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Payment;
import com.yuesheng.pm.entity.PaymentDetail;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.PaymentModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.PaymentDetailService;
import com.yuesheng.pm.service.PaymentService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "合同付款管理")
@RestController
@RequestMapping("api/payment")
public class PaymentApi extends BaseApi {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentDetailService paymentDetailService;

    @Operation(description = "获取合同付款详细信息")
    @GetMapping("getById/{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(paymentService.getDetailById(id));
    }

    @Operation(description = "获取合同付款性质")
    @GetMapping("getPayTypeList")
    public ResponseModel getPayTypes() {
        return new ResponseModel(paymentService.getTypes());
    }

    @Operation(description = "获取合同付款列表")
    @GetMapping
    public ResponseModel getPaymentList(@Parameter(name="检索字符串") String searchText,
                                        @Parameter(name = "数据大小", required = true) Integer pageSize,
                                        @Parameter(name = "当前数据索引", required = true) Integer pageNumber,
                                        @Parameter(name="排序属性名称") String sortName,
                                        @Parameter(name="排序方式：asc/desc") String sortOrder,
                                        @Parameter(name="审核状态：0=未审核,1=已审核") String approveStatus,
                                        @Parameter(name="单位id") String company,
                                        @Parameter(name="筛选指定员工的数据") String staffId) {
        Map<String, Object> params = new HashMap<>(5);
        params.put("str", searchText);
        params.put("company", company);
        params.put("approveStatus", approveStatus);
        params.put("isAll", StringUtils.isBlank(staffId) ? null : "'" + staffId + "'");
        startPage(pageNumber,pageSize,getSortName(sortName),sortOrder);
        Page<Payment> paymentList = (Page<Payment>) paymentService.getPaymentList(params);
        for (Payment pay : paymentList) {
            List<PaymentDetail> pds = paymentDetailService.getDetailByPayId(pay.getId());
            if(!pds.isEmpty()){
                pay.setDetails(pds);
            }
        }
        params.clear();
        params.put("rows", paymentList);
        params.put("total", paymentList.getTotal());
        return new ResponseModel(params);
    }
    @Operation(description = "导出excel")
    @GetMapping("exportExcel")
    public ResponseModel exportExcel(@Parameter(name="检索字符串") String searchText,
                                        @Parameter(name="排序属性名称") String sortName,
                                        @Parameter(name="排序方式：asc/desc") String sortOrder,
                                        @Parameter(name="审核状态：0=未审核,1=已审核") String approveStatus,
                                        @Parameter(name="单位id") String company,
                                        @Parameter(name="筛选指定员工的数据") String staffId) {
        Map<String, Object> params = new HashMap<>(5);
        params.put("str", searchText);
        params.put("company", company);
        params.put("approveStatus", approveStatus);
        params.put("isAll", StringUtils.isBlank(staffId) ? null : "'" + staffId + "'");
        startPage(1,2000,getSortName(sortName),sortOrder);
        Page<Payment> paymentList = (Page<Payment>) paymentService.getPaymentList(params);
        for (Payment pay : paymentList) {
            List<PaymentDetail> pds = paymentDetailService.getDetailByPayId(pay.getId());
            if(!pds.isEmpty()){
                pay.setCompany(pds.get(0).getCompany());
                pay.setDetails(pds);
            }
        }
        String fileName = "付款记录.xlsx";
        fileName = ExcelParse.writeExcel(paymentList,fileName,new String[]{"Series",
                "Company.Name","PayDate","ApproveDate","Staff.Name","Moneys","PaymentType.Name","ApproveStatus"},Payment.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "添加付款单")
    @PostMapping
    public ResponseModel addPayment(@RequestBody PaymentModel paymentModel, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Payment pay = paymentModel.getPay();
        pay.setStaff(staff);
        Payment oldPay = paymentService.getPaymentById(pay.getId());
        if (Objects.isNull(oldPay)) {
            paymentService.addPayment(pay, paymentModel.getAttachs(), false, null);
            return new ResponseModel(pay);
        } else {
            return updatePayment(paymentModel, staff);
        }
    }

    @Operation(description = "修改付款单")
    @PutMapping
    public ResponseModel updatePayment(@RequestBody PaymentModel paymentModel, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Payment pay = paymentService.getPaymentById(paymentModel.getPay().getId());
        if (Objects.isNull(pay)) {
            return new ResponseModel(500, "付款单不存在");
        } else if (pay.getApproveStatus() == 1) {
            return new ResponseModel(500, "单据已审核");
        } else if (!StringUtils.equals(staff.getId(), pay.getStaff().getId())) {
            return new ResponseModel(500, "单据不属于您");
        } else {
            paymentService.delete(pay.getId(), false);
            return addPayment(paymentModel, staff);
        }
    }

    @Operation(description = "删除付款单")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Payment pay = paymentService.getPaymentById(id);
        if (Objects.isNull(pay)) {
            return new ResponseModel(500, "付款单不存在");
        } else if (pay.getApproveStatus() == 1) {
            return new ResponseModel(500, "单据已审核");
        } else if (!StringUtils.equals(staff.getId(), pay.getStaff().getId())) {
            return new ResponseModel(500, "单据不属于您");
        } else {
            paymentService.delete(pay.getId(), true);
            return new ResponseModel(200, "删除成功");
        }
    }

    @Operation(description = "获取申请中付款总金额(按年)")
    @GetMapping("getApplyPaymentMoney")
    public ResponseModel getApplyPaymentMoney(String year) {
        return new ResponseModel(paymentDetailService.getApplyPaymentMoney(year));
    }

    @Operation(description = "获取申请中付款总金额（按指定日期范围）")
    @GetMapping("getApplyMoneyByDate")
    public ResponseModel getApplyMoneyByDate(@Param("开始日期") String startDate,
                                             @Parameter(name="截止日期") String endDate,
                                             @Parameter(name="0=未审核，1=已审核") String state) {
        HashMap<String, Object> param = new HashMap<>(3);
        param.put("start", startDate);
        param.put("end", endDate);
        param.put("state", state);
        return new ResponseModel(paymentDetailService.getPayMoneyByDate(param));
    }

    @Operation(description = "保存打印记录")
    @PostMapping("savePrintHistory/{id}")
    public ResponseModel savePrintHistory(@PathVariable String id, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        paymentService.updatePrintDate(id, staff.getName() + "@" + DateUtil.getDatetime());
        return ResponseModel.ok();
    }

    @Operation(description = "根据编号获取付款单")
    @GetMapping("getBySeries")
    public ResponseModel getBySeries(String series) {
        return ResponseModel.ok(paymentService.getPaymentBySeries(series));
    }

    public String getSortName(String sortName) {
        if (StringUtils.isBlank(sortName)) {
            return "pd06406";
        }
        switch (sortName) {
            case "series":
                return "pd06402";
            case "date":
                return "pd06406";
            case "approveDate":
                return "pd06410";
            case "company.name":
                return "pd06403";
            case "staff.name":
                return "pd06404";
            case "approveStatus":
                return "pd06408";
            default:
                return "pd06406";
        }
    }
}
