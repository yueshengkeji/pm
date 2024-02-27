package com.yuesheng.pm.restapi;

import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.exception.TaskException;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.util.ZipUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

@Tag(name = "项目管理")
@RequestMapping("/api/project")
@RestController
public class ProjectApi extends BaseApi {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private OutMaterService outMaterService;
    @Autowired
    private PutStorageService putStorageService;
    @Autowired
    private PlanService planService;
    @Autowired
    private BackMaterService backMaterService;
    @Autowired
    private ProMaterialHistoryService historyService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private ISysJobService jobService;
    @Autowired
    private ProjectTaskService taskService;
    @Autowired
    private ProjectAuthorService authorService;
    @Autowired
    private ExpenseSubjectService expenseSubjectService;
    @Autowired
    private OutCarExpenseService expenseService;
    @Autowired
    private StaffService staffService;

    @Operation(description = "检索项目")
    @GetMapping("search")
    public ResponseModel getProjects(String str, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        try {
            List<Project> projects;
            List<ProjectAuth> projectList = projectService.getProjectAuthByStaff(staff.getId());
            if (projectList.isEmpty()) {
                startPage(1, 10, "pa00145", "desc");
                projects = projectService.seek(StringUtils.isBlank(str) ? "" : URLDecoder.decode(str, "UTF-8"));
            } else {
                projects = projectService.seek(StringUtils.isBlank(str) ? "" : URLDecoder.decode(str, "UTF-8"), staff.getId());
            }
            return new ResponseModel(projects);
        } catch (UnsupportedEncodingException e) {
            return new ResponseModel(null);
        }
    }

    @Operation(description = "获取项目明细")
    @GetMapping("getById/{id}")
    public ResponseModel getById(@PathVariable String id) {
        Project project = projectService.getProjectByid(id);
        if (!Objects.isNull(project)) {
            project.setoOwner(companyService.getCompanyById(project.getOwner()));
            project.setFolder(projectService.getFolderById(project.getFolderId()));
        }
        return new ResponseModel(project);
    }

    @Operation(description = "获取项目成本情况报表")
    @GetMapping("getUseMaterial")
    public ResponseModel getUseMaterial(String id) {

        Project p = projectService.getProjectByid(id);
        Map<String, Object> resultMap = new HashMap<>();
        ProMaterialHistory query = new ProMaterialHistory();
        query.setProjectId(id);
        List<ProMaterialHistory> histories = historyService.queryAll(query);

        if (histories.isEmpty()) {
            projectService.syncData(id);
            histories = historyService.queryAll(query);
        }

        Double outMoneys = outMaterService.getOutMaterialMoney(id);
        Double putMoneys = putStorageService.getPutMoneyByProject(id);
        Double proMoneys = procurementService.getProMoneyByProject(id);
        Double planMoneys = planService.getPlanMoneyByProject(id);
        Double backMoneys = backMaterService.getBackMoneyByProject(id);
        Double carInputMoney = expenseService.queryInputMoneyByProject(id);

        HashMap<String, Object> param = new HashMap<>();
        param.put("projectName", p.getName());
        param.put("status", "1");
        param.put("isProject", "1");
        List<ExpenseSubModel> expenseMoneys = expenseSubjectService.list(param);
        Double expenseMoney = 0.0;
        for (ExpenseSubModel esm : expenseMoneys) {
            if (!Objects.isNull(esm)) {
                if (!Objects.isNull(esm.getTotalMoney())) {
                    expenseMoney += esm.getTotalMoney();
                }
                ProMaterialHistory pmh = new ProMaterialHistory();
                Material m = new Material();
                Course course = esm.getCourse();
                if (Objects.isNull(course)) {
                    m.setName("科目被删除");
                } else {
                    m.setName(esm.getCourse().getName());
                }
                m.setModel(esm.getRemark());
                pmh.setMaterial(m);
                pmh.setProMoney(esm.getTotalMoney());
                pmh.setType("报销科目");
                histories.add(pmh);
            }
        }

        resultMap.put("rows", histories);
        resultMap.put("expenseMoneys", expenseMoney);
        resultMap.put("outMoneys", Objects.isNull(outMoneys) ? 0.0 : outMoneys);
        resultMap.put("proMoneys", Objects.isNull(proMoneys) ? 0.0 : proMoneys);
        resultMap.put("putMoneys", Objects.isNull(putMoneys) ? 0.0 : putMoneys);
        resultMap.put("planMoneys", Objects.isNull(planMoneys) ? 0.0 : planMoneys);
        resultMap.put("backMoneys", Objects.isNull(backMoneys) ? 0.0 : backMoneys);
        resultMap.put("carInputMoney", Objects.isNull(carInputMoney) ? 0.0 : carInputMoney);
        resultMap.put("materialNumber", histories.size());
        return new ResponseModel(resultMap);
    }

    @Operation(description = "刷新项目材料使用情况")
    @PutMapping("refreshUseMaterial/{id}")
    public ResponseModel refreshUseMaterial(@PathVariable String id) {
        return new ResponseModel(projectService.syncData(id));
    }

    @Operation(description = "获取分页项目集合")
    @GetMapping("getProjects")
    public ResponseModel getProjects(String searchText,
                                     Integer pageSize,
                                     Integer pageNumber,
                                     String sortName,
                                     String sortOrder,
                                     @SessionAttribute(Constant.SESSION_KEY) Staff staff) {

        Map<String, Object> params = new HashMap(16);
        List<ProjectAuth> projectList = projectService.getProjectAuthByStaff(staff.getId());
        if (!projectList.isEmpty()) {
            params.put("staffId", staff.getId());
        }
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("str", "".equals(searchText) ? null : searchText);
        List<Project> rows = projectService.getProjects(params, pageNumber,pageSize);
        pageSize = projectService.getProjectsCount(params);
        params.clear();
        params.put("rows", rows);
        params.put("total", pageSize);
        return new ResponseModel(params);
    }

    @Operation(description = "删除项目")
    @DeleteMapping
    public ResponseModel delete(@Parameter(name="项目id") String id) {
        Project p = projectService.getProjectByid(id);
        if (!Objects.isNull(p) && p.getState() == 0) {
            projectService.delete(id);
            flowMessageService.deleteMessage(id);
            return ResponseModel.ok(1);
        } else {
            return ResponseModel.error("项目已审核，不可删除");
        }
    }

    @Operation(description = "获取项目目录列表")
    @GetMapping("folder")
    public ResponseModel getFolder() {
        return new ResponseModel(projectService.queryFolders(null));
    }

    @Operation(description = "添加项目")
    @PutMapping
    public ResponseModel insert(@RequestBody @Parameter(name="项目对象") Project project, @Parameter(hidden = true) @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws Exception {
        projectService.insert(project, null, staff);
        return new ResponseModel(project);
    }

    @Operation(description = "修改项目")
    @PostMapping
    public ResponseModel update(@RequestBody Project project) throws Exception {
        projectService.update(project);
        return ResponseModel.ok(project);
    }


    @Operation(description = "检索已审核项目")
    @GetMapping("searchByApprove")
    public ResponseModel searchByApprove(String str) {
        startPage(1, 10, "pa00145", "desc");
        return new ResponseModel(projectService.seekByApprove(str));
    }

    @Operation(description = "验证项目是否存在，不存在返回null")
    @GetMapping("getByName")
    public ResponseModel nameIsExist(String name) {
        return new ResponseModel(projectService.getProjectByName(name));
    }

    @Operation(description = "获取项目经理列表")
    @GetMapping("getManager/{id}")
    public ResponseModel getManager(@PathVariable String id) {
        return new ResponseModel(projectService.getProjectManager(id));
    }

    @Operation(description = "通过合同id获取项目集合")
    @GetMapping("getProjectByContract")
    public ResponseModel getProjectByContract(String contractId) {
        System.out.println(projectService.getProjectByCon(contractId));
        return new ResponseModel(projectService.getProjectByCon(contractId));
    }

    @Operation(description = "添加项目事项提醒任务")
    @PutMapping("insertProjectTask")
    public ResponseModel insertProjectTask(@RequestBody ProjectTask task, @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws TaskException, SchedulerException {
        task.setStaff(staff);
        taskService.insert(task);
        SysJob sysJob = new SysJob();
        sysJob.setJobName(task.getName());
        sysJob.setJobGroup("project");
        sysJob.setInvokeTarget("projectService.notifyUser('" + task.getProject().getId() + "','" + task.getName() + "','" + task.getId() + "')");
        sysJob.setConcurrent("0");
        sysJob.setStatus(Constant.Status.NORMAL.getValue());
        sysJob.setMisfirePolicy("2");
        String date = DateUtil.format(DateUtil.parse(task.getTaskDatetime(), DateUtil.PATTERN_CLASSICAL_NORMAL), "ss mm HH dd MM yyyy");
        StringBuffer sb = new StringBuffer(date.substring(0, date.length() - 4));
        sb.append("? " + date.substring(date.length() - 4));
        sysJob.setCronExpression(sb.toString());
        jobService.insertJob(sysJob);
        return ResponseModel.ok(sysJob);
    }

    @Operation(description = "删除项目事项提醒任务")
    @DeleteMapping("deleteProjectTask/{id}")
    public ResponseModel deleteProjectTask(@Parameter(name="任务id") @PathVariable String id) {
        ProjectTask task = taskService.queryById(id);
        SysJob sysJob = new SysJob();
        sysJob.setInvokeTarget("projectService.notifyUser('" + task.getProject().getId() + "','" + task.getName() + "','" + task.getId() + "')");
        List<SysJob> sysJobs = jobService.selectJobList(sysJob);
        StringBuffer sb = new StringBuffer();
        sysJobs.forEach(item -> {
            try {
                jobService.deleteJob(item);
            } catch (SchedulerException e) {
                sb.append(e.getMessage());
            }
        });
        if (sb.length() > 0) {
            return ResponseModel.error(sb.toString());
        } else {
            taskService.delete(id);
            return ResponseModel.ok();
        }
    }

    @Operation(description = "获取任务事项列表")
    @GetMapping("taskList/{projectId}")
    public ResponseModel taskList(@PathVariable String projectId) {
        return ResponseModel.ok(taskService.queryByProject(projectId));
    }

    @Operation(description = "获取项目验证信息")
    @GetMapping("author/{id}")
    @NoToken
    public ResponseModel author(@PathVariable String id) {
        return ResponseModel.ok(authorService.queryById(id));
    }

    @Operation(description = "设置项目验证信息")
    @PostMapping("updateAuthor")
    public ResponseModel updateAuthor(@RequestBody ProjectAuthor author) {
        authorService.insert(author);
        return ResponseModel.ok();
    }

    @Operation(description = "下载项目校验程序")
    @GetMapping("downloadProjectAuthor/{id}")
    public synchronized ResponseModel downloadProjectAuthor(@PathVariable String id) throws Exception {
        String fileName = id + ".zip";
        if (new File(WebParam.assetsPath + File.separator + fileName).isFile()) {
            return ResponseModel.ok(WebParam.TEMP_FOLDER + fileName);
        }
        String folder = WebParam.assetsPath + "project-author";
        setProjectId(folder, id);
        ZipUtil.zipFile(new File(folder), WebParam.assetsPath + File.separator + fileName);
        return ResponseModel.ok(WebParam.TEMP_FOLDER + fileName);
    }

    @Operation(description = "查询项目授权列表")
    @GetMapping("auth/{id}")
    public ResponseModel auth(@PathVariable String id) {
        List<ProjectAuth> auths = projectService.getProjectAuth(id);
        auths.forEach(item -> {
            item.setStaff(staffService.getStaffById(item.getStaff().getId()));
        });
        return ResponseModel.ok(auths);
    }

    @Operation(description = "新增项目授权")
    @PostMapping("auth")
    public ResponseModel insertAuth(@RequestBody ProjectAuth auth) {
        return ResponseModel.ok(projectService.insertProjectAuth(auth));
    }

    @Operation(description = "删除项目授权")
    @DeleteMapping("auth/{id}")
    public ResponseModel deleteAuth(@PathVariable String id) {
        return ResponseModel.ok(projectService.deleteProjectAuth(id));
    }

    private void setProjectId(String folder, String id) throws IOException {
        Properties p = new Properties();
        FileInputStream fis = new FileInputStream(folder + File.separator + "config" + File.separator + "application.properties");
        p.load(fis);
        p.setProperty("telnet.projectId", id);
        FileWriter fileWriter = new FileWriter(folder + File.separator + "config" + File.separator + "application.properties");
        p.store(fileWriter, "");
        IOUtils.closeQuietly(fileWriter);
        IOUtils.closeQuietly(fis);
    }


}
