package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.*;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ExpenseApi
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 14:13
 */
@RestController
@Tag(name = "报销单")
@RequestMapping("api/expenseApi")
public class ExpenseApi extends BaseApi{
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExpenseSubjectService expenseSubjectService;
    @Autowired
    private ExpenseFileService fileService;
    @Autowired
    private FlowMessageService messageService;
    @PostMapping("insertExpense")
    @Operation(description = "新增报销单")
    public ResponseModel insertExpense(@RequestBody Expense expense,@SessionAttribute(Constant.SESSION_KEY) Staff staff){
        expense.setStaff(staff);
        expenseService.insert(expense);
        return new ResponseModel(expense);
    }

    @PostMapping("update")
    @Operation(description = "修改单据")
    public ResponseModel update(@RequestBody Expense expense,@SessionAttribute(Constant.SESSION_KEY)Staff staff){
        ResponseModel rm = checkUpdate(expense);
        if(rm.getCode() == 200){
            expense.setStaff(staff);
            expenseService.update(expense);
            return ResponseModel.ok(expense);
        }else{
            return rm;
        }
    }

    private ResponseModel checkUpdate(Expense expense){
        FlowMessage fm = messageService.getMessageByFrameId(expense.getId());
        if(!Objects.isNull(fm)){
            if(fm.getState() == 2){
                return ResponseModel.error("流程已经审批完成，禁止修改");
            }
            messageService.deleteMessage(expense.getId());
        }
        return ResponseModel.ok();
    }

    @DeleteMapping("deleteFile")
    @Operation(description = "删除附件")
    public ResponseModel delete(String fileUrl)
    {
     fileService.deleteByUrl(fileUrl);
     return ResponseModel.ok();
    }

    @GetMapping("list")
    @Operation(description = "报销单列表")
    public ResponseModel list(@Parameter(name="检索字符串") String str,
                              @Parameter(name="开始日期") String startDate,
                              @Parameter(name="截止日期") String endDate,
                              @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                              @Parameter(name = "数据当前索引位置", required = true) Integer page,
                              @Parameter(name="排序名称") String sortName,
                              @Parameter(name="排序方式：desc/asc") String sortOrder,
                              @Parameter(name="是否只看本人数据") Boolean ifMine,
                              @Parameter(name="是否只看某人数据") Boolean ifSomeone,
                              @Parameter(name="报销人") String someone,
                              @Parameter(name="项目锁定") String searchProjectName,
                              @Parameter(name="科目锁定") String searchCourse,
                              @Parameter(name="审批状态") int status,
                              @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("end", StringUtils.isBlank(endDate) ? null : endDate);
        if (ifSomeone){
            params.put("staff",ifSomeone ? someone : null);
        }
        if (ifMine){
            params.put("staff",ifMine ? staff.getId() : null);
        }
        params.put("searchProjectName",StringUtils.isBlank(searchProjectName) ? null : searchProjectName);
        params.put("searchCourse",StringUtils.isBlank(searchCourse) ? null : searchCourse);
        if (ifMine){
            params.put("status",null);
        }else {
            params.put("status",ifMine ? null : (status == 0 ? 0 : 1));
        }

        List<Expense> list1 = expenseService.list(params);
        Double totalEMoney = 0.00;
        for (int i = 0;i < list1.size();i++){
            totalEMoney += list1.get(i).getTotalMoney();
        }

        startPage(page,itemsPerPage,sortName,sortOrder);
        Page<Expense> list = (Page<Expense>) expenseService.list(params);

        params.clear();
        params.put("expenseList",list);
        params.put("total",list.getTotal());
        params.put("totalEMoney",totalEMoney);

        return new ResponseModel(params);
    }

    @GetMapping("queryCourses")
    @Operation(description = "获取科目列表")
    public ResponseModel queryCourses(){
        return new ResponseModel(courseService.queryAll());
    }

    @GetMapping("getById")
    @Operation(description = "获取指定报销单")
    public ResponseModel getById(String id){
        Expense expense = expenseService.selectById(id);
        expense.setExpenseSubjects(expenseSubjectService.getByMark(id));
        return new ResponseModel(expense);
    }

    @PostMapping("deleteById")
    @Operation(description = "删除指定报销单")
    public ResponseModel deleteById(@RequestBody Expense expense){
        return new ResponseModel(expenseService.delete(expense.getId()));
    }

    @GetMapping("getByCourse")
    @Operation(description = "根据科目获取报销单")
    public ResponseModel getByCourse(@Parameter(name="检索字符串") String str,
                                     @Parameter(name="开始日期") String startDate,
                                     @Parameter(name="截止日期") String endDate,
                                     @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                     @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                     @Parameter(name="排序名称") String sortName,
                                     @Parameter(name="排序方式：desc/asc") String sortOrder,
                                     @Parameter(name="是否只看本人数据") Boolean ifMine,
                                     @Parameter(name="是否只看某人数据") Boolean ifSomeone,
                                     @Parameter(name="报销人") String someone,
                                     @Parameter(name="项目锁定") String searchProjectName,
                                     @Parameter(name="科目锁定") String searchCourse,
                                     @Parameter(name="审批状态") int status,
                                     @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("end", StringUtils.isBlank(endDate) ? null : endDate);
        params.put("staff",ifSomeone ? someone : null);
        params.put("searchProjectName",StringUtils.isBlank(searchProjectName) ? null : searchProjectName);
        Course c = courseService.queryByName(searchCourse);
        if(!Objects.isNull(c)){
            params.put("searchCourse",c.getId());
        }
        params.put("status",status == 0 ? 0 : 1);

        List<ExpenseSubModel> list1 = expenseSubjectService.list(params);
        Double totalSubMoney = 0.00;
        for (int i = 0;i < list1.size();i++){
            totalSubMoney += list1.get(i).getTotalMoney();
        }

        startPage(page,itemsPerPage,sortName,sortOrder);
        Page<ExpenseSubModel> list = (Page<ExpenseSubModel>) expenseSubjectService.list(params);

        params.clear();
        params.put("expenseSubjectList",list);
        params.put("total",list.getTotal());
        params.put("totalSubMoney",totalSubMoney);

        return new ResponseModel(params);
    }

    @GetMapping("exportExpenseList")
    @Operation(description = "导出报销单")
    public ResponseModel exportExpenseList(@Parameter(name="检索字符串") String str,
                                           @Parameter(name="开始日期") String startDate,
                                           @Parameter(name="截止日期") String endDate,
                                           @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                           @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                           @Parameter(name="排序名称") String sortName,
                                           @Parameter(name="排序方式：desc/asc") String sortOrder,
                                           @Parameter(name="是否只看本人数据") Boolean ifMine,
                                           @Parameter(name="是否只看某人数据") Boolean ifSomeone,
                                           @Parameter(name="报销人") String someone,
                                           @Parameter(name="项目锁定") String searchProjectName,
                                           @Parameter(name="科目锁定") String searchCourse,
                                           @Parameter(name="审批状态") int status,
                                           @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("end", StringUtils.isBlank(endDate) ? null : endDate);
        if (ifSomeone){
            params.put("staff",ifSomeone ? someone : null);
        }
        if (ifMine){
            params.put("staff",ifMine ? staff.getId() : null);
        }
        params.put("searchProjectName",StringUtils.isBlank(searchProjectName) ? null : searchProjectName);
        params.put("searchCourse",StringUtils.isBlank(searchCourse) ? null : searchCourse);
        if (ifMine){
            params.put("status",ifMine ? null : (status == 0 ? 0 : 1));
        }else {
            params.put("status",ifMine ? null : (status == 0 ? 0 : 1));
        }

        List<Expense> list = expenseService.list(params);
        for (int i = 0;i < list.size(); i++){
            list.get(i).setIndex(i + 1);
        }

        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader());
        rows.add(header);
        for (int i = 0; i < list.size(); i++) {
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell(list.get(i), row.getIndex()));
            rows.add(row);
        }
        String filename = (StringUtils.isBlank(startDate) ? "" : startDate) + "-" + (StringUtils.isBlank(endDate) ? "" : endDate) + "报销表.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "项目", "报销人", "费用", "申请时间"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 3 || i == 2 || i == 4) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 1 ) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell(Expense expense, int index) {
        String[] headers = new String[]{"Expense.Index", "Expense.Project", "Expense.Staff.Name", "Expense.TotalMoney",
                "Expense.ApplyDate"};
        ExpenseModel model = new ExpenseModel();
        model.setIndex(index);
        model.setExpense(expense);
        return EntityUtils.getCells(model, headers);
    }

    public String formatMarkTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        return format;
    }

    @GetMapping("exportSubExpenseList")
    @Operation(description = "导出科目报销单")
    public ResponseModel exportPersonalExpenseList(@Parameter(name="检索字符串") String str,
                                                   @Parameter(name="开始日期") String startDate,
                                                   @Parameter(name="截止日期") String endDate,
                                                   @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                                   @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                                   @Parameter(name="排序名称") String sortName,
                                                   @Parameter(name="排序方式：desc/asc") String sortOrder,
                                                   @Parameter(name="是否只看本人数据") Boolean ifMine,
                                                   @Parameter(name="是否只看某人数据") Boolean ifSomeone,
                                                   @Parameter(name="报销人") String someone,
                                                   @Parameter(name="项目锁定") String searchProjectName,
                                                   @Parameter(name="科目锁定") String searchCourse,
                                                   @Parameter(name="审批状态") int status,
                                                   @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("end", StringUtils.isBlank(endDate) ? null : endDate);
        params.put("staff",ifSomeone ? someone : null);
        params.put("searchProjectName",StringUtils.isBlank(searchProjectName) ? null : searchProjectName);
        Course c = courseService.queryByName(searchCourse);
        if(!Objects.isNull(c)){
            params.put("searchCourse",c.getId());
        }
        params.put("status",status == 0 ? 0 : 1);

        List<ExpenseSubModel> list = expenseSubjectService.list(params);
        for (int i = 0;i < list.size(); i++){
            list.get(i).setIndex(i + 1);
        }

        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader());
        rows.add(header);
        for (int i = 0; i < list.size(); i++) {
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell2(list.get(i), row.getIndex()));
            rows.add(row);
        }
        String filename = (StringUtils.isBlank(startDate) ? "" : startDate) + "-" + (StringUtils.isBlank(endDate) ? "" : endDate) + searchCourse + "报销表.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);


        return new ResponseModel(filename);
    }

    @Operation(description = "删除报销科目")
    @DeleteMapping("deleteSubject/{id}")
    public ResponseModel deleteSubject(@PathVariable String id){
        return ResponseModel.ok(expenseSubjectService.delete(id));
    }

    @Operation(description = "修改报销科目")
    @PostMapping("updateCourse")
    public ResponseModel update(@RequestBody Course course){
        courseService.update(course);
        return ResponseModel.ok(course);
    }

    private List<Cell> getCell2(ExpenseSubModel expenseSubModel, int index) {
        String[] headers = new String[]{"ExpenseSubModel.Index", "ExpenseSubModel.Project", "ExpenseSubModel.Staff.Name", "ExpenseSubModel.TotalMoney",
                "ExpenseSubModel.ApplyDate"};
        ExpenseSubjectModel model = new ExpenseSubjectModel();
        model.setIndex(index);
        model.setExpenseSubModel(expenseSubModel);
        return EntityUtils.getCells(model, headers);
    }
}
