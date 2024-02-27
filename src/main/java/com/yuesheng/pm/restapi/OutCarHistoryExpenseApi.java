package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.OutCarExpenseDetailService;
import com.yuesheng.pm.service.OutCarExpenseService;
import com.yuesheng.pm.service.OutCarHistoryService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "用车报销")
@RestController
@RequestMapping("api/outCarExpense")
public class OutCarHistoryExpenseApi extends BaseApi {
    @Autowired
    private OutCarExpenseService expenseService;

    @Autowired
    private OutCarExpenseDetailService detailService;

    @Autowired
    private OutCarHistoryService historyService;

    @Autowired
    private FlowMessageService messageService;

    @Operation(description = "新增报销")
    @PutMapping
    public ResponseModel insert(@RequestBody OutCarExpense carExpense,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        carExpense.setStaffId(staff.getId());
        expenseService.insert(carExpense);
        return ResponseModel.ok(carExpense);
    }

    @Operation(description = "查询报销列表")
    @GetMapping("list")
    public ResponsePage list(String staffId,
                             Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             Integer state,
                             Boolean sortDesc) {
        OutCarExpense carExpense = new OutCarExpense();
        carExpense.setStaffId(staffId);
        carExpense.setState(state);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        List<OutCarExpense> expenseList = expenseService.queryByPage(carExpense);
        Map<String, String> moneyCount = expenseService.queryMoneyCount(carExpense);
        ResponsePage<OutCarExpense> rp = ResponsePage.ok((Page) expenseList);
        if(moneyCount != null){
            rp.getData().putAll(moneyCount);
        }
        return rp;
    }

    @Operation(description = "查询报销明细列表")
    @GetMapping("detailList/{expenseId}")
    public ResponseModel detailList(@PathVariable String expenseId) {
        OutCarExpenseDetail detail = new OutCarExpenseDetail();
        detail.setOutCarExpenseId(expenseId);
        return ResponseModel.ok(detailService.queryByPage(detail));
    }

    @Operation(description = "查询已审核报销明细")
    @GetMapping("detailList")
    public ResponsePage detailList(Integer page,
                                    Integer itemsPerPage,
                                    String searchText,
                                    String projectName,
                                    String startDate,
                                    String endDate,
                                    String staffId,
                                    @RequestParam(defaultValue = "datetime") String sortBy,
                                    @RequestParam(defaultValue = "DESC") String sortDesc){
        HashMap<String,Object> param = new HashMap<>();
        param.put("searchText", searchText);
        param.put("projectName", projectName);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("staffId", staffId);
        startPage(page,itemsPerPage,sortBy,sortDesc);
        Page<OutCarExpenseDetail> details = (Page) detailService.queryByParam(param);
        ResponsePage<OutCarExpenseDetail> rp = ResponsePage.ok(details);
        DateGroupModel model = detailService.queryMoney(param);
        rp.getData().put("moneyTotal", model);
        return rp;
    }
    @Operation(description = "导出已审核报销明细")
    @GetMapping("exportDetailList")
    public ResponseModel exportDetailList(Integer page,
                                    String searchText,
                                    String projectName,
                                    String startDate,
                                    String endDate,
                                    String staffId,
                                    @RequestParam(defaultValue = "datetime") String sortBy,
                                    @RequestParam(defaultValue = "DESC") String sortDesc){
        HashMap<String,Object> param = new HashMap<>();
        param.put("searchText", searchText);
        param.put("projectName", projectName);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("staffId", staffId);
        startPage(page,5000,sortBy,sortDesc);
        Page<OutCarExpenseDetail> details = (Page) detailService.queryByParam(param);
        String fileName = "用车报销统计.xlsx";
        fileName = ExcelParse.writeExcel(details,fileName,new String[]{
                "Expense.Datetime","History.Staff.Name","History.Remark","History.StartTime","History.StartAddrName",
                "History.EndTime","History.EndAddrName","History.SystemKm","History.InputKm","History.IsParkingCost",
                "History.Project.Name"
        },OutCarExpenseDetail.class);
        return ResponseModel.ok(fileName);
    }

    @Operation(description = "删除报销")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        FlowMessage fm = messageService.getMessageByFrameId(id);
        if(!Objects.isNull(fm) && fm.getState() == 2){
            return ResponseModel.error("单据已审批，禁止删除");
        }
        messageService.deleteMessage(id);
        return ResponseModel.ok(expenseService.deleteById(id));
    }

    @Operation(description = "获取报销明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        OutCarExpense expense = expenseService.queryById(id);
        if (!Objects.isNull(expense)) {
            OutCarExpenseDetail query = new OutCarExpenseDetail();
            query.setOutCarExpenseId(id);
            List<OutCarExpenseDetail> details = detailService.queryByPage(query);
            ArrayList<OutCarHistory> histories = new ArrayList<>();
            details.forEach(item -> {
                histories.add(historyService.queryById(item.getOutHistoryId()));
            });
            expense.setDetailHistory(histories);
            return ResponseModel.ok(expense);
        } else {
            return ResponseModel.ok();
        }
    }
}
