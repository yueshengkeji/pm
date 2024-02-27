package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Leave;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.LeaveService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "请假单管理")
@RestController
@RequestMapping("api/vacation")
public class LeaveApi extends BaseApi {
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private FlowMessageService flowMessageService;

    @Operation(description = "获取我的请假单集合")
    @GetMapping
    public ResponseModel getLeaves(@SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        return new ResponseModel(leaveService.getLeaveByStaff(staff.getId()));
    }

    @Operation(description = "获取请假单集合")
    @GetMapping("all")
    public ResponseModel getLeaves(@Parameter(name="数据大小") Integer pageSize,
                                   @Parameter(name="数据页码") Integer pageNumber,
                                   @Parameter(name="检索字符串") String searchText,
                                   @Parameter(name="排序名称") String sortName,
                                   @Parameter(name="排序方式") String sortOrder,
                                   @Parameter(name="开始日期") String start,
                                   @Parameter(name="截止日期") String end,
                                   @Parameter(name="请假单状态,0=未审核，1=已审核") String state) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("start", start);
        result.put("end", end);
        result.put("searchText", "".equals(searchText) ? null : searchText);
        result.put("state", StringUtils.isBlank(state) ? null : state);
        startPage(pageNumber,pageSize,sortName,sortOrder);
        List<Leave> leaveList = leaveService.getLeaveByParam(result);
        int count = leaveService.getCountByParam(result);
        result.clear();
        result.put("rows", leaveList);
        result.put("total", count);
        return new ResponseModel(result);
    }

    @Operation(description = "导出请假单")
    @GetMapping("export")
    public ResponseModel export(@Parameter(name="检索字符串") String searchText,
                                @Parameter(name="排序名称") String sortName,
                                @Parameter(name="排序方式") String sortOrder,
                                @Parameter(name="开始日期") String start,
                                @Parameter(name="截止日期") String end,
                                @Parameter(name="请假单状态,0=未审核，1=已审核") String state) {
        if(StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            return ResponseModel.error("请指定时间范围");
        }
        Map<String, Object> result = new HashMap<>(16);
        result.put("start", start);
        result.put("end", end);
        result.put("searchText", "".equals(searchText) ? null : searchText);
        result.put("order", MaterialController.isSort(sortName, sortOrder));
        result.put("state", StringUtils.isBlank(state) ? null : state);
        int count = leaveService.getCountByParam(result);
        startPage(1,count,sortName ,sortOrder);
        List<Leave> leaveList = leaveService.getLeaveByParam(result);
        for (Leave leave : leaveList) {
            Integer[] days = DateUtil.diff(DateUtil.parse(leave.getStartDate(), DateUtil.PATTERN_CLASSICAL_NORMAL), DateUtil.parse(leave.getEndDate(), DateUtil.PATTERN_CLASSICAL_NORMAL));
            if (days[1] >= 8) {
                leave.setLeaveNumber(1.0);
                leave.setLeaveHouse(0.0);
            } else {
                leave.setLeaveNumber(Double.valueOf(days[0]));
                leave.setLeaveHouse(Double.valueOf(days[1] + "." + ((days[2] > 0) ? 5 : 0)));
            }
        }
        String fileName = DateUtil.format(DateUtil.parse(start), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(end), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-请假单报表.xlsx";
        fileName = ExcelParse.writeExcel(leaveList, fileName,
                new String[]{"Series", "Type", "LeaveNumber", "LeaveHouse", "StartDate", "EndDate", "Staff.Name", "Remark"}, Leave.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "添加请假单")
    @PostMapping
    public ResponseModel addLeave(@RequestBody Leave leave, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        leave.setStaff(staff);
        leaveService.insert(leave);
        return new ResponseModel(leave);
    }

    @Operation(description = "删除请假单")
    @DeleteMapping("{id}")
    public ResponseModel deleteLeave(@PathVariable @Parameter(name="请假单id") String id) {
        Leave leave = leaveService.getLeaveById(id);
        if (leave != null && leave.getApproveStatus() != 1) {        //审批完成后不予删除
            //删除流程消息和单据
            flowMessageService.deleteMessage(id);
            leaveService.delete(id);
            return new ResponseModel(200, "操作成功");
        } else {
            return new ResponseModel(500, "单据已审核，禁止删除");
        }
    }

    @Operation(description = "获取请假单明细")
    @GetMapping("getById/{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(leaveService.getLeaveById(id));
    }
}
