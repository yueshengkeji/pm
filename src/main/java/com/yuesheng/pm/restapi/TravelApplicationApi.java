package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.TravelApplication;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.Row;
import com.yuesheng.pm.model.TravelApplicationModel;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.TravelApplicationService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 出差申请
 * @author ssk
 * @since 2022-3-2
 */
@Tag(name = "出差申请")
@RestController
@RequestMapping("api/travelApplication")
public class TravelApplicationApi {

    @Autowired
    TravelApplicationService travelApplicationService;
    @Autowired
    private FlowMessageService flowMessageService;

    @Operation(description = "获取出差人申请数据")
    @GetMapping("getTravelApplicationMSG")
    public ResponseModel getTravelApplicationMSG(Integer itemsPerPage, Integer page, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Map<String, Object> params = new HashMap<>();

        List<TravelApplication> travelApplications = travelApplicationService.selectByTraveller(page, itemsPerPage, staff.getId());
        if (travelApplications != null) {
            for (int i = 0; i < travelApplications.size(); i++) {
                travelApplications.get(i).setIndex(i + 1);
            }
        }
        Integer totalDesserts = travelApplicationService.selectCountsByTraveller(staff.getId());

        params.put("rows",travelApplications);
        params.put("totalDesserts",totalDesserts);
        return new ResponseModel(params);
    }

    @Operation(description = "新增出差申请")
    @PutMapping("insertTravelApplication")
    public ResponseModel insertTravelApplication(@RequestBody TravelApplication travelApplication, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff){
        travelApplication.setTraveller(staff.getName());
        travelApplication.setDepartment(staff.getSection().getName());
        travelApplication.setTravellerId(staff.getId());
        travelApplication.setDepartmentId(staff.getSection().getId());
        travelApplication.setCreateTime(DateUtil.getNowDate());
        if (travelApplication.getTransportation().equals("其他") && travelApplication.getOtherTransport() != null) {
            travelApplication.setTransportation(travelApplication.getOtherTransport());
        }
        String uuid = UUID.randomUUID().toString();
        travelApplication.setId(uuid);
        travelApplicationService.insertTravelApplication(travelApplication);
        return new ResponseModel(travelApplication);
    }

    @Operation(description = "删除未通过记录")
    @PutMapping("deleteTravelApplication")
    public ResponseModel deleteTravelApplication(@RequestBody TravelApplication travelApplication, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {

        int i = travelApplicationService.deleteTravelApplication(travelApplication.getId());

        //删除审批流程
        flowMessageService.deleteMessage(travelApplication.getId() + "");

        return new ResponseModel(i);
    }

    @Operation(description = "通过id获取数据")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        TravelApplication ta = travelApplicationService.selectById(id);
        return new ResponseModel(ta);
    }


    @Operation(description = "获取出差数据")
    @GetMapping("getMonthTravelApplication")
    public ResponseModel getMonthTravelApplication(Integer itemsPerPage, Integer page, String dateSearch, String departmentId){
        Map<String,Object> params = new HashMap<>();
        List<TravelApplication> travelApplications = null;
        Integer totalDesserts = null;
        if (departmentId == null || departmentId.equals("全部")){
            travelApplications = travelApplicationService.selectByDateSearch(page, itemsPerPage, dateSearch);
            totalDesserts = travelApplicationService.selectCountsByDateSearch(dateSearch);
        } else if (departmentId != null && !departmentId.equals("全部")){
            travelApplications = travelApplicationService.selectByDateSearchDepartment(page, itemsPerPage,dateSearch,departmentId);
            totalDesserts = travelApplicationService.selectCountsByDateSearchDepartment(dateSearch,departmentId);
        }

        if (travelApplications != null){
            for (int i = 0;i < travelApplications.size();i++){
                travelApplications.get(i).setIndex(i + 1);
            }
        }
        params.put("travelApplications",travelApplications);
        params.put("totalDesserts",totalDesserts);
        return new ResponseModel(params);
    }

    @Operation(description = "导出出差数据")
    @GetMapping("exportTravelApplication")
    public ResponseModel exportTravelApplication(@Parameter String dateSearch,String departmentId){
        List<TravelApplication> travelApplications = null;
        if (dateSearch != null && departmentId == null){
            travelApplications = travelApplicationService.selectTravelApplicationByDateSearch(dateSearch);
        }else if (dateSearch == null && departmentId != null){
            travelApplications = travelApplicationService.selectTravelApplicationByPosition(departmentId);
        }else if (dateSearch != null && departmentId != null){
            travelApplications = travelApplicationService.selectByDateAndDepartment(dateSearch,departmentId);
        }

        if (travelApplications != null){
            for (int i = 0;i < travelApplications.size();i++){
                travelApplications.get(i).setIndex(i + 1);
                if (travelApplications.get(i).getTravelType() == 0){
                    travelApplications.get(i).setTravelTypeString("市内公务");
                }else if (travelApplications.get(i).getTravelType() == 1){
                    travelApplications.get(i).setTravelTypeString("外地出差");
                }

                if (travelApplications.get(i).getStatus() == 0){
                    travelApplications.get(i).setStatusString("未审核");
                }else if (travelApplications.get(i).getStatus() == 1){
                    travelApplications.get(i).setStatusString("已审核");
                }
            }
        }
        List<Row> rows = new ArrayList<>();
        Row header = new Row();
        header.setIndex(0);
        header.setCell(getHeader());
        rows.add(header);
        for (int i = 0; i < travelApplications.size(); i++){
            Row row = new Row();
            row.setIndex(i + 1);
            row.setCell(getCell(travelApplications.get(i), row.getIndex()));
            rows.add(row);
        }
        String mark = formatMarkTime(new Date());
        String filename = mark + "出差记录.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "出差人员", "填报日期", "出差地点" , "交通工具", "出差时间", "出差类型","审核状态","交通费","住宿费","其他费用","费用总计计"};
        for (int i = 0; i < names.length; i++){
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 1 || i == 2 || i == 4 || i == 5 || i == 6 || i == 7) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 3 ) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell(TravelApplication travelApplication, int index) {
        String[] headers = new String[]{"TravelApplication.Index", "TravelApplication.Traveller", "TravelApplication.CreateTime", "TravelApplication.Place",
                "TravelApplication.Transportation","TravelApplication.StartTime", "TravelApplication.TravelTypeString", "TravelApplication.StatusString",
        "TravelApplication.TravelFee","TravelApplication.StayFee","TravelApplication.OtherFee","TravelApplication.TotalFee"};
        TravelApplicationModel model = new TravelApplicationModel();
        model.setIndex(index);
        model.setTravelApplication(travelApplication);
        return EntityUtils.getCells(model, headers);
    }

    public String formatMarkTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        return format;
    }

    @Operation(description = "更新出差记录")
    @PutMapping("updateTravelApplication")
    public ResponseModel updateTravelApplication(@RequestBody TravelApplication travelApplication){
        int i = travelApplicationService.updateTravelApplication(travelApplication);
        return new ResponseModel(i);
    }

    @Operation(description = "搜索")
    @GetMapping("getSearchMSG")
    public ResponseModel getSearchMSG(String search){
        Map<String, Object> params = new HashMap<>();

        List<TravelApplication> travelApplications = travelApplicationService.selectTravelApplicationBySearch(search);

        if (travelApplications != null){
            for (int i = 0;i < travelApplications.size();i++){
                travelApplications.get(i).setIndex(i + 1);
            }
        }

        params.put("travelApplications",travelApplications);

        return new ResponseModel(params);
    }


}
