package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ProPutForDetail;
import com.yuesheng.pm.entity.ProZujin;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ProPutDetailService;
import com.yuesheng.pm.service.ProZujinService;
import com.yuesheng.pm.service.ProcurementMaterService;
import com.yuesheng.pm.service.ProcurementService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@Tag(name = "采购入库/应收实收明细")
@RestController
@RequestMapping("api/proPutDetail")
public class ProPutDetailApi extends BaseApi {

    @Autowired
    private ProPutDetailService putDetailService;
    @Autowired
    private ProZujinService zujinService;

    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private ProcurementMaterService materService;

    @Operation(description = "查询采购/入库明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(putDetailService.getDetail(id));
    }


    @Operation(description = "根据对账单ID查询明细列表")
    @GetMapping("getPutDetail/{detailId}")
    public ResponseModel<ProPutForDetail> putDetailList(@PathVariable String detailId,
                                                        String startDate,
                                                        String endDate,
                                                        @RequestParam(defaultValue = "false") Boolean loadProInfo) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mainId", detailId);
        params.put("start", startDate);
        if(!Objects.isNull(endDate)){
            endDate = DateUtil.format(DateUtil.rollDay(DateUtil.parse(endDate,"yyyy-MM-dd"),1),DateUtil.PATTERN_CLASSICAL_SIMPLE);
            params.put("end", endDate);
        }
        return ResponseModel.ok(putDetailService.getDetailByDate(params, loadProInfo));
    }

    @Operation(description = "查询所有明细列表")
    @GetMapping("list")
    public ResponsePage list(Integer itemsPerPage,
                             Integer page,
                             String sortBy,
                             String sortDesc,
                             String searchText,
                             @Parameter(name="0:应收，1:实收") Integer type,
                             @Parameter(name="0:租赁合同，1:物业合同") Integer contractType,
                             String startDate,
                             String endDate) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("searchText", searchText);
        param.put("type", type);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("contractType", contractType);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<ProPutForDetail> proPutForDetailPage = (Page<ProPutForDetail>) putDetailService.getDetail(param);
        proPutForDetailPage.forEach(item -> {
            try {
                item.setZujin(zujinService.queryById(Integer.valueOf(item.getMainId())));
            } catch (NumberFormatException e) {

            }
        });
        ResponsePage<ProPutForDetail> rp = ResponsePage.ok(proPutForDetailPage);
        rp.getData().put("moneyCount", putDetailService.getDetailCount(param));
        return rp;
    }

    @Operation(description = "导出所有明细列表")
    @GetMapping("exportList")
    public ResponseModel list(
            String sortBy,
            String sortDesc,
            String searchText,
            @Parameter(name="0:应收，1:实收") Integer type,
            @Parameter(name="0:租赁合同，1:物业合同") Integer contractType,
            String startDate,
            String endDate) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("searchText", searchText);
        param.put("type", type);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("contractType", contractType);
        startPage(1, 5000, sortBy, sortDesc);
        Page<ProPutForDetail> proPutForDetailPage = (Page<ProPutForDetail>) putDetailService.getDetail(param);
        proPutForDetailPage.forEach(item -> {
            try {
                item.setZujin(zujinService.queryById(Integer.valueOf(item.getMainId())));
            } catch (NumberFormatException e) {

            }
            if (Objects.isNull(item.getZujin())) {
                item.setZujin(new ProZujin());
            }
        });

        String fileName = "金额明细.xlsx";
        fileName = ExcelParse.writeExcel(proPutForDetailPage, fileName, new String[]{
                "Zujin.Company", "Zujin.Brand", "ProDate", "ProMoney", "PutDate", "PutMoney", "Staff.Name",
                "Remark"
        }, ProPutForDetail.class);

        return ResponseModel.ok(fileName);
    }

    @Operation(description = "添加明细")
    @PutMapping
    public ResponseModel insert(@RequestBody ProPutForDetail detail, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        putDetailService.addDetail(detail, staff);
        return ResponseModel.ok(detail);
    }

    @Operation(description = "修改明细")
    @PostMapping
    public ResponseModel<ProPutForDetail> updatePutDetail(@RequestBody ProPutForDetail detail, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        ProPutForDetail result = putDetailService.updateDetail(detail);
        putDetailService.updateLastMsg(DateFormat.getDate(), staff, detail.getId());
        return ResponseModel.ok(result);
    }

    @Operation(description = "查询采购入库金额年度合计")
    @GetMapping("getMoneySum/{year}")
    public ResponseModel getMoneySum(@PathVariable String year) {
        HashMap<String, Double> sumMap = new HashMap<>();
        sumMap.put("proMoney", putDetailService.getProMoneySum(year));
        sumMap.put("putMoney", putDetailService.getPutMoneySum(year));
        return ResponseModel.ok(sumMap);
    }

    @Operation(description = "重新计算采购金额")
    @PostMapping("setProMoney")
    public ResponseModel setProMoney(@RequestBody ProPutForDetail detail) {
        procurementService.genProDetail(
                procurementService.getProcurementById(detail.getProId()),
                materService.getProMatersByProId(detail.getProId()),
                true, detail.getPutId());
        return ResponseModel.ok();
    }

}
