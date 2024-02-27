package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ApplyForCar;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.StatisticByDriver;
import com.yuesheng.pm.entity.StatisticByProject;
import com.yuesheng.pm.model.ApplyForCarModel;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.service.ApplyForCarService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Tag(name = "用车申请")
@RestController
@RequestMapping("api/applyForCar")
public class ApplyForCarApi {

    @Autowired
    private ApplyForCarService applyForCarService;
    @Autowired
    private StaffService staffService;

    @Operation(description = "获取登录人申请单集合")
    @GetMapping("getApplyForCar")
    public ResponseModel getApplyForCar(Integer pageSize,
                                        Integer pageNumber,
                                        String searchText,
                                        String sortName,
                                        String sortOrder,
                                        String staffId,
                                        String begin,
                                        String end,
                                        String driverId,
                                        String projectId
                                        ){

        Map<String, Object> params = new HashMap<>();
        if (begin == null) {
            begin = "";
            end = "";
        }
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("staffId", staffId);
        params.put("searchText", "".equals(searchText) ? null : searchText);
        params.put("begin", begin);
        params.put("end", end);
        params.put("driverId",driverId);
        params.put("projectId",projectId);
        List<ApplyForCar> applyForCars = applyForCarService.selectByParam(pageNumber, pageSize, params);
        Integer total = applyForCarService.getCountByParam(params);
        for (int i = 0;i < applyForCars.size();i++){
            Staff staffById = staffService.getStaffById(applyForCars.get(i).getApplicantId());
            applyForCars.get(i).setApplicant(staffById.getName());
            applyForCars.get(i).setPhoneNumber(staffById.getTel());
        }
        params.clear();
        params.put("rows",applyForCars);
        params.put("total",total);
        return new ResponseModel(params);
    }

    @Operation(description = "新增用车申请")
    @PutMapping("insertApplyForCar")
    public ResponseModel insertApplyForCar(@RequestBody ApplyForCar applyForCar,@SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff){
        applyForCar.setCheckState("未审核");
        applyForCar.setRunState("未出车");
        applyForCar.setMarkId(UUID.randomUUID().toString());
        applyForCar.setCreateTime(DateUtil.getNowDate());
        applyForCar.setApplicantId(staff.getId());

        applyForCarService.insert(applyForCar);

        ApplyForCar applyForCar1 = applyForCarService.selectByMarkId(applyForCar.getMarkId());
        return new ResponseModel(applyForCar1);
    }

    @Operation(description = "导出用车申请单列表")
    @GetMapping("exportApplyForCar")
    public ResponseModel exportApplyForCar(Integer pageSize,
                                           Integer pageNumber,
                                           String searchText,
                                           String sortName,
                                           String sortOrder,
                                           String staffId,
                                           String begin,
                                           String end,
                                           String driverId,
                                           String projectId){
        Map<String, Object> params = new HashMap<>();
        if (begin == null) {
            begin = "";
            end = "";
        }
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("staffId", staffId);
        params.put("searchText", "".equals(searchText) ? null : searchText);
        params.put("begin", begin);
        params.put("end", end);
        params.put("driverId",driverId);
        params.put("projectId",projectId);
        List<ApplyForCar> applyForCars = applyForCarService.selectByParamNoPage(params);
        for (int i = 0;i < applyForCars.size();i++){
            Staff staffById = staffService.getStaffById(applyForCars.get(i).getApplicantId());
            applyForCars.get(i).setApplicant(staffById.getName());
            applyForCars.get(i).setPhoneNumber(staffById.getTel());
            applyForCars.get(i).setIndex(i+1);
        }

        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader());
        rows.add(header);
        for (int i = 0; i < applyForCars.size(); i++) {
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell(applyForCars.get(i), row.getIndex()));
            rows.add(row);
        }
        String mark = formatMarkTime(new Date());
        String filename = mark + "用车申请单.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "用车时间", "项目名称", "事由", "目的地", "司机", "申请人", "申请人联系方式", "状态"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 1 || i == 4 || i == 5 || i == 6 || i == 7) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 2 || i == 3  || i == 4) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell(ApplyForCar applyForCar, int index) {
        String[] headers = new String[]{"ApplyForCar.Index", "ApplyForCar.UseTime", "ApplyForCar.ProjectName", "ApplyForCar.Remark",
                "ApplyForCar.Direction", "ApplyForCar.Driver", "ApplyForCar.Applicant", "ApplyForCar.PhoneNumber", "ApplyForCar.CheckState"};
        ApplyForCarModel model = new ApplyForCarModel();
        model.setIndex(index);
        model.setApplyForCar(applyForCar);
        return EntityUtils.getCells(model, headers);
    }

    public String formatMarkTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        return format;
    }

    @Operation(description = "按项目统计")
    @GetMapping("getStatisticByProject")
    public ResponseModel getStatisticByProject(Integer page,Integer itemsPerPage){
        Map<String, Object> params = new HashMap<>();
        List<StatisticByProject> statisticByProjects = applyForCarService.selectByGroup(page,itemsPerPage);
        Integer total = applyForCarService.selectByGroupCounts();
        params.put("rows",statisticByProjects);
        params.put("total",total);
        return new ResponseModel(params);
    }

    @Operation(description = "按司机统计")
    @GetMapping("getStatisticByDriver")
    public ResponseModel getStatisticByDriver(Integer page,Integer itemsPerPage,String startDate,String endDate){
        Map<String, Object> params = new HashMap<>();

        List<StatisticByDriver> StatisticByDrivers = applyForCarService.selectByDate(page,itemsPerPage,startDate, endDate);
        Integer total = applyForCarService.selectByDateCounts(startDate, endDate);

        params.put("rows",StatisticByDrivers);
        params.put("total",total);
        return new ResponseModel(params);
    }

    @Operation(description = "设置司机")
    @PutMapping("setDriver")
    public ResponseModel setDriver(@RequestBody ApplyForCar applyForCar){
        Staff driverStaff = new Staff();
        driverStaff.setName(applyForCar.getDriver());
        driverStaff.setId(applyForCar.getDriverId());
        applyForCarService.setDriver(driverStaff,applyForCar.getId());
        return new ResponseModel(applyForCar);
    }

    @Operation(description = "通过Id查询用车单")
    @GetMapping("getApplyForCarById")
    public ResponseModel getApplyForCarById(Integer id){
        ApplyForCar applyForCar = applyForCarService.selectById(id);
        Staff staffById = staffService.getStaffById(applyForCar.getApplicantId());
        applyForCar.setApplicant(staffById.getName());
        applyForCar.setPhoneNumber(staffById.getTel());
        return new ResponseModel(applyForCar);
    }

    @Operation(description = "获取司机待出勤任务")
    @GetMapping("getDriverTask")
    public ResponseModel getDriverTask(Integer page,Integer itemsPerPage,@SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff){
        Map<String, Object> params = new HashMap<>();
        List<ApplyForCar> driverTask = applyForCarService.getDriverTask(page,itemsPerPage,staff.getId());
        Integer total = applyForCarService.getDriverTaskCounts(staff.getId());
        params.put("rows",driverTask);
        params.put("total",total);
        return new ResponseModel(params);
    }

    @Operation(description = "获取司机已完成任务")
    @GetMapping("getDriverTaskDone")
    public ResponseModel getDriverTaskDone(Integer page,Integer itemsPerPage,@SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff){
        Map<String, Object> params = new HashMap<>();
        List<ApplyForCar> driverTaskDone = applyForCarService.getDriverTaskDone(page, itemsPerPage, staff.getId());
        Integer total = applyForCarService.getDriverTaskDoneCounts(staff.getId());

        params.put("rows",driverTaskDone);
        params.put("total",total);
        return new ResponseModel(params);
    }

    @Operation(description = "更新里程数")
    @PutMapping("setMileage")
    public ResponseModel setMileage(@RequestBody ApplyForCar applyForCar){
        if (applyForCar.getRunState().equals("未出车") && applyForCar.getMileageStart() != null){
            applyForCar.setRunState("已出发");
            applyForCar.setStartTime(formatDateTime(DateUtil.getNowDate()));
        }else if (applyForCar.getRunState().equals("已出发") && applyForCar.getMileageEnd() != null){
            applyForCar.setRunState("已到达");
            applyForCar.setEndTime(formatDateTime(DateUtil.getNowDate()));
        }
        applyForCarService.update(applyForCar);
        return new ResponseModel(applyForCar);
    }

    @Operation(description = "获取司机未完成任务信息")
    @GetMapping("getTaskToDo")
    public ResponseModel getTaskToDo (){
        Map<String, Object> params = new HashMap<>();
        List<ApplyForCar> taskToDo = applyForCarService.getTaskToDo();
        params.put("row",taskToDo);
        return new ResponseModel(params);
    }

    public String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        return format;
    }
}
