package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.SysJob;
import com.yuesheng.pm.entity.SysJobLog;
import com.yuesheng.pm.exception.TaskException;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ISysJobLogService;
import com.yuesheng.pm.service.ISysJobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "定时任务管理")
@RequestMapping("api/sysJob")
@RestController
public class SysJobApi extends BaseApi {

    @Autowired
    private ISysJobService jobService;

    @Autowired
    private ISysJobLogService logService;

    @Operation(description = "添加定时任务")
    @PutMapping
    public ResponseModel insert(@RequestBody SysJob sysJob) throws TaskException, SchedulerException {
        jobService.insertJob(sysJob);
        return ResponseModel.ok(sysJob);
    }

    @Operation(description = "查询定时任务列表")
    @GetMapping("list")
    public ResponsePage list(String sortBy,
                             Boolean sortDesc,
                             String searchText,
                             String jobGroup,
                             Integer page,
                             Integer itemsPerPage) {
        SysJob query = new SysJob();
        query.setJobGroup(jobGroup);
        query.setJobName(searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        return ResponsePage.ok((Page) jobService.selectJobList(query));
    }

    @Operation(description = "查询定时任务")
    @GetMapping("{id}")
    public ResponseModel queryById(@PathVariable Long id) {
        return ResponseModel.ok(jobService.selectJobById(id));
    }

    @Operation(description = "删除定时任务")
    @DeleteMapping("{id}")
    public ResponseModel deleteById(@PathVariable Long id) throws SchedulerException {
        SysJob job = jobService.selectJobById(id);
        return ResponseModel.ok(jobService.deleteJob(job));
    }

    @Operation(description = "修改定时任务")
    @PostMapping
    public ResponseModel update(@RequestBody SysJob job) throws TaskException, SchedulerException {
        return ResponseModel.ok(jobService.updateJob(job));
    }

    @Operation(description = "查询定时任务日志")
    @GetMapping("logList")
    public ResponsePage logList(String invokeTarget,
                                String jobName,
                                Integer page,
                                Integer itemsPerPage,
                                String sortBy,
                                Boolean sortDesc) {
        SysJobLog query = new SysJobLog();
        query.setInvokeTarget(invokeTarget);
        query.setJobName(jobName);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        return ResponsePage.ok((Page) logService.selectJobLogList(query));
    }
}
