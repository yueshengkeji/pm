package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.model.WorkLogExportModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "工作日记")
@RestController
@RequestMapping("api/workLog")
public class ProWorkLogApi extends BaseApi {
    @Autowired
    private ProWorkLogService workLogService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private ProWorkLogFileService fileService;
    @Autowired
    private AttachController attachController;
    @Autowired
    private FlowNotifyService notifyService;
    @Autowired
    private StaffService staffService;

    @Operation(description = "导出工作日记")
    @GetMapping("export")
    public ResponseModel export(@Parameter(name="职员id") String staffId,
                                @Parameter(name="部门id") String sectionId,
                                @Parameter(name="开始日期") String startDate,
                                @Parameter(name="过滤字符") String searchText,
                                @Parameter(name="截止日期") String endDate,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        String temp = null;
        if (StringUtils.isNotBlank(sectionId)) {
            List<Section> manager = sectionService.getSectionByManagerId(staff.getId());
            List<Section> sections = new ArrayList<>();
            for (Section section : manager) {
                sections.addAll(sectionService.getAllSectionByParent(section.getId()));
            }
            if (sections.isEmpty()) {
                temp = null;
            } else {
                StringBuffer sb = new StringBuffer();
                sections.forEach(s -> {
                    sb.append("'");
                    sb.append(s.getId());
                    sb.append("'");
                    sb.append(",");
                });
                sb.delete(sb.length() - 1, sb.length());
                temp = sb.toString();
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("staffId", staffId);
        param.put("sectionId", temp);
        Date nextFirstDate = null;
        Date nextEndDate = null;

        startDate = DateUtil.format(DateUtil.getFirstWorkday(), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        endDate = DateUtil.format(DateUtil.rollDay(DateUtil.getLastWorkday(), 2), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        param.put("startDate", startDate + " 00:00:00");
        param.put("endDate", endDate + " 23:59:59");


        nextFirstDate = DateUtil.rollDay(DateUtil.parse(param.get("startDate").toString()), 7);
        nextEndDate = DateUtil.rollDay(DateUtil.parse(param.get("endDate").toString()), 7);
        param.put("searchText", searchText);
        Integer total = workLogService.queryByParamCount(param);
        List<ProWorkLog> logList = workLogService.queryByParam(param,1, total);
        param.put("startDate", DateUtil.format(nextFirstDate, DateUtil.PATTERN_CLASSICAL));
        param.put("endDate", DateUtil.format(nextEndDate, DateUtil.PATTERN_CLASSICAL));
        Integer nextWeekTotal = workLogService.queryByParamCount(param);
        List<ProWorkLog> nextWeekLogList = workLogService.queryByParam(param, 1, nextWeekTotal);
        List<Row> rows = new ArrayList<>();
        Row hander = new Row();
        hander.setIndex(0);
        hander.setCell(getHander());
        rows.add(hander);
        if (nextWeekLogList.size() > logList.size()) {
            for (int i = 0; i < nextWeekLogList.size(); i++) {
                Row row = new Row();
                row.setIndex(i + 1);
                try {
                    row.setCell(getCells(logList.get(i), nextWeekLogList.get(i), row.getIndex()));
                } catch (IndexOutOfBoundsException e) {
                    row.setCell(getCells(null, nextWeekLogList.get(i), row.getIndex()));
                }
                rows.add(row);
            }
        } else {
            for (int i = 0; i < logList.size(); i++) {
                Row row = new Row();
                row.setIndex(i + 1);
                try {
                    row.setCell(getCells(logList.get(i), nextWeekLogList.get(i), row.getIndex()));
                } catch (IndexOutOfBoundsException e) {
                    row.setCell(getCells(logList.get(i), null, row.getIndex()));
                }
                rows.add(row);
            }
        }
        String fileName = staff.getSection().getName() +
                "第" + (Integer.parseInt(DateUtil.getWeekOfYear()) + 1) +
                "周工作计划.xlsx";
        fileName = ExcelParse.writeExcel(rows, fileName);
        return new ResponseModel(fileName);
    }

    private List<Cell> getHander() {
        ArrayList<Cell> arrayList = new ArrayList<>();
        String[] names = new String[]{"序号", "工作人员", "内容", "备注", "下周计划", "备注",};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 2 || i == 4) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 3 || i == 5) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            arrayList.add(cell);
        }
        return arrayList;
    }

    private List<Cell> getCells(ProWorkLog log, ProWorkLog nextWeekLog, int index) {
        String[] headers = new String[]{"Index", "ThanWeekLog.Staff.Name", "ThanWeekLog.Content", "ThanWeekLog.Remark",
                "NextWeekLog.Content", "NextWeekLog.Remark"};
        WorkLogExportModel model = new WorkLogExportModel();
        model.setIndex(index);
        model.setThanWeekLog(log);
        model.setNextWeekLog(nextWeekLog);
        return EntityUtils.getCells(model, headers);
    }

    @Operation(description = "查询日记列表")
    @GetMapping("list")
    public ResponseModel list(@Parameter(name="职员id") String staffId,
                              @Parameter(name="部门id") String sectionId,
                              @Parameter(name="开始日期") String startDate,
                              @Parameter(name="搜索字符串") String searchText,
                              @Parameter(name="截止日期") String endDate,
                              @Parameter(name="条目数") Integer page,
                              @Parameter(name="页码") Integer itemsPerPage,
                              @Parameter(name="排序列") String sortBy,
                              @Parameter(name="排序方式") String sortDesc,
                              @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (Objects.isNull(sortBy)) {
            sortBy = new String("work_date");
            sortDesc = "DESC";
        }

        String temp = null;
        if (StringUtils.isNotBlank(sectionId)) {
            List<Section> manager = sectionService.getSectionByManagerId(staff.getId());
            List<Section> sections = new ArrayList<>();
            for (Section section : manager) {
                sections.addAll(sectionService.getAllSectionByParent(section.getId()));
            }
            if (sections.isEmpty()) {
                temp = null;
            } else {
                StringBuffer sb = new StringBuffer();
                sections.forEach(s -> {
                    sb.append("'");
                    sb.append(s.getId());
                    sb.append("'");
                    sb.append(",");
                });
                sb.delete(sb.length() - 1, sb.length());
                temp = sb.toString();
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("staffId", staffId);
        param.put("sectionId", temp);
        if (StringUtils.isNotBlank(startDate)) {

            param.put("startDate", startDate + " 00:00:00");
            param.put("endDate", endDate + " 23:59:59");

        }

        param.put("searchText", searchText);
        if (page == null) {
            page = 1;
            itemsPerPage = 10000;
        }

        if (itemsPerPage == -1) {
            itemsPerPage = 10000;
        }
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<ProWorkLog> logList = (Page<ProWorkLog>) workLogService.queryByParam(param, page, itemsPerPage);
        Map<String, ProWorkLog> endDateMap = new HashMap<>();
        logList.forEach(log -> {
            ProWorkLog oldLog = null;
            if ((oldLog = endDateMap.get(log.getContent())) != null) {
                //该日记已存在，更新该日记截止时间
                oldLog.setEndDate(log.getWorkDate());
            }
            endDateMap.put(log.getContent(), log);
        });
        param.clear();
        param.put("rows", logList);
        param.put("total", logList.getTotal());
        return new ResponseModel(param);
    }

    @Operation(description = "添加工作日记")
    @PutMapping
    public ResponseModel insert(@RequestBody ProWorkLog log, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        boolean isTodayBefore = DateUtil.isToDayBefore(log.getWorkDate());
        if (isTodayBefore) {
            if (Objects.isNull(log.getStaff())) {
                log.setStaff(staff);
                log.setSection(staff.getSection());
            }
            if (checkWeight(log) > 100) {
                return ResponseModel.error("当月权重累计不能超过100");
            }
            workLogService.insert(log);
            return new ResponseModel(log);
        } else {
            return new ResponseModel(500, null, "只可填写当日或以后的工作内容");
        }
    }

    @Operation(description = "通知指定员工本周工作内容")
    @GetMapping("notify")
    public ResponseModel notifyStaff(String staffId) {
        Map<String, Object> param = new HashMap<>();
        param.put("staffId", staffId);
        param.put("startDate", DateUtil.formatDate(DateUtil.getFirstWorkday()) + " 00:00:00");
        param.put("endDate", DateUtil.formatDate(DateUtil.getLastWorkday()) + " 23:59:59");
        startPage(1, 100, "work_date", "desc");
        Page<ProWorkLog> logs = (Page<ProWorkLog>) workLogService.queryByParam(param, 1, 100);
        List<Staff> staffList = new ArrayList<>();
        staffList.add(staffService.getStaffById(staffId));

        param.put("title", "工作任务通知");
        param.put("mTitle", "本周有" + logs.size() + "项工作计划");
        param.put("content", "请点击查看详情，完成工作任务后请标注完成，未完成时，请备注未完成原因");
        param.put("url", WebParam.VUETIFY_BASE + "/workLog/list");
        notifyService.msgNotify(staffList, param);
        return ResponseModel.ok(logs);
    }

    @Operation(description = "更新工作日记")
    @PostMapping
    public ResponseModel update(@RequestBody ProWorkLog log) {
        if (checkWeight(log) > 100) {
            return ResponseModel.error("当月权重累计不能超过100");
        }
        workLogService.update(log);
        return new ResponseModel(log);
    }

    @Operation(description = "删除工作日记")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable Long id) {
        List<Attach> files = (List<Attach>) getFiles(id).getData();
        files.forEach(f -> {
            attachController.deleteFile(f.getId());
        });
        workLogService.deleteById(id);
        return new ResponseModel(id);
    }

    @Operation(description = "获取工作日记文件列表")
    @GetMapping("files/{logId}")
    public ResponseModel getFiles(@PathVariable Long logId) {
        List<ProWorkLogFile> files = fileService.queryById(logId);
        List<Attach> fs = new ArrayList<>();
        files.forEach(item -> {
            Attach attach = attachController.getAttachById(item.getFileId());
            if (!Objects.isNull(attach)) {
                fs.add(attach);
            }
        });
        return new ResponseModel(fs);
    }

    @Operation(description = "修改工作日记状态")
    @PostMapping("updateState")
    public ResponseModel updateState(@RequestBody ProWorkLog log) {
        workLogService.updateState(log);
        return ResponseModel.ok(log);
    }

    @Operation(description = "修改工作日记备注")
    @PostMapping("updateRemark")
    public ResponseModel updateRemark(@RequestBody ProWorkLog log) {
        if (checkWeight(log) > 100) {
            return ResponseModel.error("当月权重累计不能超过100");
        }
        workLogService.updateRemark(log);
        return ResponseModel.ok(log);
    }

    @Operation(description = "修改工作完成情况")
    @PostMapping("updateNote")
    public ResponseModel updateNote(@RequestBody ProWorkLog log, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (StringUtils.equals(log.getStaff().getId(), staff.getId())) {
            ProWorkLog l = workLogService.queryById(log.getId());
            if (Objects.isNull(l)) {
                return ResponseModel.error("当前单据已删除");
            }
            if (BooleanUtils.isTrue(l.getEdit())) {
                return ResponseModel.error("当前单据已审批，禁止修改");
            }
            workLogService.updateRemark(log);
            return ResponseModel.ok();
        } else {
            return ResponseModel.error("当前不可修改他人数据");
        }
    }

    @Operation(description = "修改工作日记评分")
    @PostMapping("updateScore")
    public ResponseModel updateScore(@RequestBody ProWorkLog log) {
        if (checkWeight(log) > 100) {
            return ResponseModel.error("当月权重累计不能超过100");
        }
        workLogService.updateScore(log);
        return ResponseModel.ok(log);
    }

    private Double checkWeight(ProWorkLog log) {
        HashMap<String, Object> param = new HashMap<>();
        String startDate = null;
        String endDate = null;
        if(log.getWorkDate() != null){
            Date sd = DateUtil.getMonthStartTime(log.getWorkDate());
            Date ed = DateUtil.getMonthEndTime(log.getWorkDate());
            startDate = DateUtil.format(sd, DateUtil.PATTERN_CLASSICAL);
            endDate = DateUtil.format(ed, DateUtil.PATTERN_CLASSICAL);
        }else{
            startDate = DateUtil.format(DateUtil.getMonthStartTime(), DateUtil.PATTERN_CLASSICAL);
            endDate = DateUtil.format(DateUtil.getMonthEndTime(), DateUtil.PATTERN_CLASSICAL);
        }
        param.put("staffId", log.getStaff().getId());
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        List<ProWorkLog> logs = workLogService.queryByParam(param, 1, 1000);
        Double count = 0.0;
        for (ProWorkLog item :
                logs) {
            if (!Objects.isNull(item.getWeight())) {
                if (Objects.isNull(log.getId())) {
                    count += item.getWeight();
                } else if (log.getId() != item.getId()) {
                    count += item.getWeight();
                }
            }
        }
        if (!Objects.isNull(log.getWeight())) {
            count += log.getWeight();
        }
        return count;
    }
}
