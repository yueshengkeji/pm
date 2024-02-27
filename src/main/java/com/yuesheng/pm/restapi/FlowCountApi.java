package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.FlowCount;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.DateGroupModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FlowCountService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Tag(name = "人/车流统计")
@RestController
@RequestMapping("/api/flowCount")
public class FlowCountApi extends BaseApi {
    @Autowired
    private FlowCountService flowCountService;

    @Operation(description = "添加车/客流数据")
    @PutMapping
    public ResponseModel insert(@RequestBody FlowCount flowCount, @SessionAttribute(Constant.SESSION_KEY) Staff staff){

        flowCount.setStaffId(staff.getId());
        flowCountService.insert(flowCount);
        return ResponseModel.ok(flowCount);
    }

    @Operation(description = "批量添加/车/客流数据")
    @PutMapping("array")
    public ResponseModel insertArray(@RequestBody FlowCount[] flowCounts,@SessionAttribute(Constant.SESSION_KEY)Staff staff){

        int row = 0;
        if(!Objects.isNull(flowCounts)){
            for (int i = 0; i < flowCounts.length; i++) {
                FlowCount fc = flowCounts[i];
                if(!Objects.isNull(fc)){
                    fc.setStaffId(staff.getId());
                    flowCountService.insert(fc);
                    row++;
                }
            }
        }

        return ResponseModel.ok(row);
    }

    @Operation(description = "查询车流/客流数据")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                              Integer itemsPerPage,
                              String startDate,
                              String endDate,
                              String sortBy,
                              String sortDesc){
        FlowCount query = new FlowCount();
        query.setDate(startDate);
        query.setCountDate(endDate);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<FlowCount> saleDataPage = (Page<FlowCount>) flowCountService.queryAll(query);
        return ResponsePage.ok(saleDataPage);
    }

    @Operation(description = "查询客流数据按天统计")
    @GetMapping("total")
    public ResponseModel total(Integer day){
        HashMap<String, List<DateGroupModel>> result = new HashMap();
        if (!Objects.isNull(day)) {
            String dayStr;
            if(day <= 9){
                dayStr = "0"+day;
            }else{
                dayStr = String.valueOf(day);
            }
            String date = DateUtil.format(new Date(), "yyyy-MM-");
            HashMap<String,Object> param = new HashMap<>();
            param.put("flowStartDate", date+"01");
            param.put("flowEndDate", date+dayStr);
            List<DateGroupModel> thanGroupModels = flowCountService.queryMoneyGroupFlowDate(param);
            String prevDate = DateUtil.format(DateUtil.getLastMonthStartTime(),"yyyy-MM-");
            param.put("flowStartDate", prevDate+"01");
            param.put("flowEndDate", prevDate+dayStr);
            List<DateGroupModel> prevGroupModels = flowCountService.queryMoneyGroupFlowDate(param);
            result.put("thanGroupModels",thanGroupModels);
            result.put("prevGroupModels",prevGroupModels);
        }

        return ResponseModel.ok(result);
    }

    @Operation(description = "删除客流数据")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable Integer id)
    {
        return ResponseModel.ok(flowCountService.deleteById(id));
    }
}
