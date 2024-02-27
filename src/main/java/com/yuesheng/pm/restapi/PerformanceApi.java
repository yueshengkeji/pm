package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Performance;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.PerformanceService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "考核管理")
@RequestMapping("api/performance")
@RestController
public class PerformanceApi extends BaseApi {

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private FlowMessageService messageService;

    @Operation(description = "提交考核表")
    @PutMapping
    public ResponseModel insert(@RequestBody Performance performance) {
        performanceService.insert(performance);
        return ResponseModel.ok(performance);
    }

    @Operation(description = "提交打分审批")
    @PutMapping("/insertScore")
    public ResponseModel insertScore(@RequestBody Performance performance){
        performance.setTempScoreFlag(1);
        performanceService.insert(performance);
        return ResponseModel.ok(performance);
    }

    @Operation(description = "查询考核明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(performanceService.queryById(id));
    }

    @Operation(description = "查询考核打分明细")
    @GetMapping("/getPerByScoreId/{id}")
    public ResponseModel getPerByScoreId(@PathVariable String id) {
        return ResponseModel.ok(performanceService.getPerByScoreId(id));
    }

    @Operation(description = "修改考核表")
    @PostMapping
    public ResponseModel update(@RequestBody Performance per) {
        performanceService.update(per);
        return ResponseModel.ok(per);
    }

    @Operation(description = "删除考核表")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        messageService.deleteMessage(id);
        performanceService.deleteById(id);
        return ResponseModel.ok();
    }

    @Operation(description = "查询考核记录")
    @GetMapping("list")
    public ResponseModel list(Integer page,
                              Integer itemsPerPage,
                              String sortBy,
                              Boolean sortDesc,
                              String staffId,
                              Integer flag,
                              String month) {
        Performance performance = new Performance();
        performance.setMonth(month);
        performance.setStaffId(staffId);
        performance.setFlag(flag);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<Performance> list = (Page<Performance>) performanceService.queryByPage(performance);
        list.forEach(item -> {
            item.setStaff(staffService.getStaffById(item.getStaffId()));
            if(StringUtils.equals(item.getStaffId(),item.getSignStaffId())){
                item.setSign(item.getStaff());
            }else if(StringUtils.isNotBlank(item.getSignStaffId())){
                item.setSign(staffService.getStaffById(item.getSignStaffId()));
            }
        });
        return ResponseModel.ok(list);
    }

    @Operation(description = "导出考核记录")
    @GetMapping("exportList")
    public ResponseModel exportList(Integer page,
                                    Integer itemsPerPage,
                                    String sortBy,
                                    Boolean sortDesc,
                                    String staffId,
                                    Integer flag,
                                    String month) {
        Performance performance = new Performance();
        performance.setMonth(month);
        performance.setStaffId(staffId);
        performance.setFlag(flag);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<Performance> list = (Page<Performance>) performanceService.queryByPage(performance);
        list.forEach(item -> {
            item.setStaff(staffService.getStaffById(item.getStaffId()));
            if(StringUtils.equals(item.getStaffId(),item.getSignStaffId())){
                item.setSign(item.getStaff());
            }else if(StringUtils.isNotBlank(item.getSignStaffId())){
                item.setSign(staffService.getStaffById(item.getSignStaffId()));
            }
        });
        String fileName = "考核记录.xlsx";
        fileName = ExcelParse.writeExcel(list, fileName, new String[]{
                "Id", "Section.Name", "Staff.Name",
                "ScoreSum", "Sign.Name", "Remark"
        }, Performance.class);
        return ResponseModel.ok(fileName);
    }
}
