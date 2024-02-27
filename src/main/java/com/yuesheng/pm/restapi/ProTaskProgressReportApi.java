package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProTaskProgressReport;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ProTaskProgressReportService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProTaskProgressReportApi
 * @Description
 * @Author ssk
 * @Date 2022/9/14 0014 10:52
 */
@Tag(name = "项目任务进度汇报")
@RestController
@RequestMapping("api/proTaskProgressReport")
public class ProTaskProgressReportApi {

    @Autowired
    private ProTaskProgressReportService proTaskProgressReportService;

    @Operation(description = "获取汇报列表")
    @GetMapping("getReports")
    public ResponseModel getReports(Integer itemsPerPage, Integer page, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff){
        Map<String, Object> params = new HashMap<>();
        List<ProTaskProgressReport> proTaskProgressReports = proTaskProgressReportService.selectAllForPage(page, itemsPerPage);
        Integer totalCounts = proTaskProgressReportService.selectAllCounts();

        params.put("rows",proTaskProgressReports);
        params.put("total",totalCounts);
        return new ResponseModel(params);
    }

    @Operation(description = "新增汇报")
    @PostMapping("insertReport")
    public ResponseModel insertReport(@RequestBody ProTaskProgressReport proTaskProgressReport,@SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff){
        proTaskProgressReport.setStaff(staff);
        return new ResponseModel(proTaskProgressReportService.insert(proTaskProgressReport));
    }

    @Operation(description = "根据项目名称获取汇报列表")
    @GetMapping("getByProName")
    public ResponseModel getByProName(String projectName){
        Map<String,Object> params = new HashMap<>();
        List<ProTaskProgressReport> proTaskProgressReports = proTaskProgressReportService.selectByPro(projectName);
        params.put("rows",proTaskProgressReports);

        

        return new ResponseModel(params);
    }

    @Operation(description = "根据id获取汇报信息")
    @GetMapping("getById")
    public ResponseModel getById(String id){
        return new ResponseModel(proTaskProgressReportService.selectById(id));
    }
}
