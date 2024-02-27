package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.*;
import com.yuesheng.pm.service.EquipmentToRepairFileService;
import com.yuesheng.pm.service.EquipmentToRepairService;
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
 * @ClassName EquipmentToRepairApi
 * @Description
 * @Author ssk
 * @Date 2022/10/28 0028 13:43
 */
@Tag(name = "设备故障送修")
@RestController
@RequestMapping("api/equipmentToRepair")
public class EquipmentToRepairApi extends BaseApi{
    @Autowired
    private EquipmentToRepairService equipmentToRepairService;
    @Autowired
    private EquipmentToRepairFileService equipmentToRepairFileService;

    @Operation(description = "新增送修")
    @PostMapping("insertRepair")
    public ResponseModel insertRepair(@RequestBody EquipmentToRepair equipmentToRepair, @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        equipmentToRepair.setStaffApplicant(staff);
        equipmentToRepairService.insert(equipmentToRepair);
        return new ResponseModel(equipmentToRepair);
    }

    @Operation(description = "更新送修")
    @PostMapping("updateRepair")
    public ResponseModel updateRepair(@RequestBody EquipmentToRepair equipmentToRepair, @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        equipmentToRepair.setStaffPurchaser(staff);
        equipmentToRepairService.update(equipmentToRepair);
        return new ResponseModel(equipmentToRepair);
    }

    @Operation(description = "结果通知")
    @PostMapping("resultConfirm")
    public ResponseModel resultConfirm(@RequestBody EquipmentToRepair equipmentToRepair, @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        equipmentToRepair.setNotifyFlag(1);
        equipmentToRepairService.updateNotify(equipmentToRepair.getId(),equipmentToRepair.getNotifyFlag());
        return new ResponseModel(equipmentToRepair);
    }

    @Operation(description = "送修设备列表")
    @GetMapping("list")
    public ResponseModel list(@Parameter(name="检索字符串") String str,
                              @Parameter(name="开始日期") String startDate,
                              @Parameter(name="截止日期") String endDate,
                              @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                              @Parameter(name = "数据当前索引位置", required = true) Integer page,
                              @Parameter(name = "状态，0=未发货，1=已发货，2=已收货，3=已报废)") String state,
                              @Parameter(name="排序名称") String sortName,
                              @Parameter(name="排序方式：desc/asc") String sortOrder,
                              @Parameter(name="查看时间") String searchDate,
                              @Parameter(name="是否只看本人数据") Boolean ifMine,
                              @Parameter(name="项目锁定") String searchProjectName,
                              @SessionAttribute(Constant.SESSION_KEY) Staff staff){
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("end", StringUtils.isBlank(endDate) ? null : endDate);
        params.put("state",state);
        params.put("staff",null);
        params.put("searchDate",StringUtils.isBlank(searchDate) ? null : searchDate);
        params.put("searchProjectName",StringUtils.isBlank(searchProjectName) ? null : searchProjectName);

        startPage(page,itemsPerPage,sortName,sortOrder);
        Page<EquipmentToRepair> equipmentToRepairs = (Page<EquipmentToRepair>)equipmentToRepairService.list(params);
        params.clear();
        params.put("equipmentToRepairs",equipmentToRepairs);
        params.put("total",equipmentToRepairs.getTotal());
        return new ResponseModel(params);
    }

    @Operation(description = "根据id获取")
    @GetMapping("getById")
    public ResponseModel getById(String id){
        return new ResponseModel(equipmentToRepairService.selectById(id));
    }

    @Operation(description = "绑定附件")
    @PostMapping("bindFile")
    public ResponseModel bindFile(@RequestBody List<EquipmentToRepairFile> files){
        files.forEach(item -> {
            equipmentToRepairFileService.insert(item);
        });
        return new ResponseModel("ok");
    }

    @Operation(description = "获取附件集合")
    @GetMapping("getFiles")
    public ResponseModel getFiles(String id){
        return new ResponseModel(equipmentToRepairFileService.getFiles(id));
    }

    @Operation(description = "删除附件")
    @PostMapping("deleteFile")
    public ResponseModel deleteFile(@RequestBody ProSubcontractFile proSubcontractFile){
        return new ResponseModel(equipmentToRepairFileService.delete(proSubcontractFile.getAttachId()));
    }

    @Operation(description = "删除送修记录")
    @PostMapping("deleteRepair")
    public ResponseModel deleteRepair(@RequestBody EquipmentToRepair equipmentToRepair,@SessionAttribute(Constant.SESSION_KEY) Staff staff){
        return new ResponseModel(equipmentToRepairService.delete(equipmentToRepair.getId()));
    }

    @Operation(description = "导出送修记录")
    @GetMapping("exportRepair")
    public ResponseModel exportRepair(@Parameter(name="检索字符串") String str,
                                      @Parameter(name="开始日期") String startDate,
                                      @Parameter(name="截止日期") String endDate,
                                      @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                      @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                      @Parameter(name = "状态，0=未发货，1=已发货，2=已收货，3=已报废)") String state,
                                      @Parameter(name="排序名称") String sortName,
                                      @Parameter(name="排序方式：desc/asc") String sortOrder,
                                      @Parameter(name="查看时间") String searchDate,
                                      @Parameter(name="是否只看本人数据") Boolean ifMine,
                                      @Parameter(name="项目锁定") String searchProjectName,
                                      @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("start", StringUtils.isBlank(startDate) ? null : startDate);
        params.put("end", StringUtils.isBlank(endDate) ? null : endDate);
        params.put("state",state);
        params.put("staff",null);
        params.put("searchDate",StringUtils.isBlank(searchDate) ? null : searchDate);
        params.put("searchProjectName",StringUtils.isBlank(searchProjectName) ? null : searchProjectName);

        List<EquipmentToRepair> list = equipmentToRepairService.list(params);

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
        String mark = formatMarkTime(new Date());
        String filename = mark + "设备送修记录表.xlsx";
        filename = ExcelParse.writeExcel(rows, filename);
        return new ResponseModel(filename);
    }

    private List<Cell> getHeader() {
        ArrayList<Cell> list = new ArrayList<>();
        String[] names = new String[]{"序号", "项目", "设备名", "品牌", "序列号", "数量", "申报人", "时间"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 3 || i == 2 || i == 4 || i == 5 || i == 6) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 1 || i == 7) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            list.add(cell);
        }
        return list;
    }

    private List<Cell> getCell(EquipmentToRepair equipmentToRepair, int index) {
        String[] headers = new String[]{"EquipmentToRepair.Index", "EquipmentToRepair.ProjectName", "EquipmentToRepair.EquipmentName", "EquipmentToRepair.Brand",
                "EquipmentToRepair.SerialNumber","EquipmentToRepair.Number", "EquipmentToRepair.StaffApplicant.Name", "EquipmentToRepair.CreateTime"};
        EquipmentToRepairModel model = new EquipmentToRepairModel();
        model.setIndex(index);
        model.setEquipmentToRepair(equipmentToRepair);
        return EntityUtils.getCells(model, headers);
    }

    public String formatMarkTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        return format;
    }
}
