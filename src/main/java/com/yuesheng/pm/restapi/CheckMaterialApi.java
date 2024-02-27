package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.CheckMater;
import com.yuesheng.pm.entity.CheckMaterChild;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.CheckMaterChildService;
import com.yuesheng.pm.service.CheckMaterService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Tag(name = "仓库盘点管理")
@RestController
@RequestMapping("api/checkMater")
public class CheckMaterialApi extends BaseApi {
    @Autowired
    private CheckMaterService checkMaterService;
    @Autowired
    private CheckMaterChildService materChildService;
    @Autowired
    private StaffService staffService;

    @Operation(description = "查询盘点单列表")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String startDate,
                             String endDate,
                             String sortBy,
                             Boolean sortDesc,
                             String searchText) {
        Map<String, Object> params = new HashMap(16);
        //获取材料集合
        params.put("str", searchText);
        params.put("start", startDate);
        params.put("end", endDate);
        startPage(page, itemsPerPage, getSortName(sortBy), sortDesc);
        Page<CheckMater> materials = (Page<CheckMater>) checkMaterService.getCheckMater(params);       //获取材料
        return ResponsePage.ok(materials);
    }

    @Operation(description = "添加盘点单")
    @PutMapping
    public ResponseModel insert(@RequestBody CheckMater checkMater, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        return ResponseModel.ok(checkMaterService.addMater(checkMater, staff));
    }

    @Operation(description = "获取盘点单材料")
    @GetMapping("listMater/{checkId}")
    public ResponseModel listMater(@PathVariable String checkId) {
        return ResponseModel.ok(materChildService.getMaterList(checkId));
    }

    @Operation(description = "删除盘点单")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        CheckMater temp = checkMaterService.getCheckById(id);
        if (Objects.isNull(temp)) {
            return ResponseModel.error("盘点单不存在");
        } else if (temp.getState() == 1) {
            return ResponseModel.error("单据已审核，禁止删除，请反审核后操作");
        }
        checkMaterService.deleteCheckMater(id);
        return ResponseModel.ok(id);
    }

    @Operation(description = "修改盘点单")
    @PostMapping
    public ResponseModel update(@RequestBody CheckMater checkMater, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        CheckMater temp = checkMaterService.getCheckById(checkMater.getId());
        if (Objects.isNull(temp)) {
            return ResponseModel.error("盘点单不存在");
        } else if (temp.getState() == 1) {
            return ResponseModel.error("单据已审核，禁止修改，请反审核后操作");
        }
        checkMaterService.deleteCheckMater(checkMater.getId());
        checkMaterService.addMater(checkMater, staff);
        return ResponseModel.ok(checkMater);
    }

    @Operation(description = "审核/反审核盘点单")
    @PostMapping("approve")
    public ResponseModel approve(@RequestBody CheckMater checkMater, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Map<String, Object> result = checkMaterService.approve(checkMater, staff);
        if (result.containsKey("error")) {
            return ResponseModel.error(result.get("error").toString());
        }
        return ResponseModel.ok(result);
    }

    @Operation(description = "查询已审核的盘点记录")
    @GetMapping("history")
    public ResponsePage history(String startDate,
                                String endDate,
                                String searchText,
                                Integer page,
                                Integer itemsPerPage,
                                String sortBy,
                                Boolean sortDesc) {
        HashMap<String, Object> param = new HashMap<>(4);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("searchText", searchText);
        param.put("state", "1");
        startPage(page, itemsPerPage, getSortName(sortBy), sortDesc);
        Page<CheckMaterChild> cmcList = (Page<CheckMaterChild>) materChildService.listByParam(param);
        setStaff(cmcList);
        return ResponsePage.ok(cmcList);
    }

    @Operation(description = "获取盘点金额统计")
    @GetMapping("getCheckTotalMoney")
    public ResponseModel getCheckTotalMoney(@Parameter(name="搜索文本") String searchText,
                                           @Parameter(name="开始日期") String startDate,
                                           @Parameter(name="截止日期") String endDate) {
        HashMap<String, Object> params = new HashMap<>(4);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("searchText", searchText);
        params.put("state", "1");
        Double getCheckTotalMoney = materChildService.getCheckTotalMoney(params);
        return new ResponseModel(getCheckTotalMoney);
    }

    @Operation(description = "导出已审核的盘点记录为excel")
    @GetMapping("downloadHistory")
    public ResponseModel downloadHistory(String startDate,
                                         String endDate,
                                         String searchText,
                                         String sortBy,
                                         Boolean sortDesc) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return ResponseModel.error("请指定开始/截止日期");
        }
        HashMap<String, Object> param = new HashMap<>(4);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("searchText", searchText);
        param.put("state", "1");
        startPage(1, 10000, getSortName(sortBy), sortDesc);
        Page<CheckMaterChild> cmcList = (Page<CheckMaterChild>) materChildService.listByParam(param);
        setStaff(cmcList);
        String fileName = "已审核盘点材料清单" + startDate + "-" + endDate + ".xlsx";
        fileName = ExcelParse.writeExcel(cmcList, fileName, new String[]{
                "Cm.CheckNumber", "Material.Name", "Material.Model", "Material.Brand", "Cm.CheckDate",
                "RealitySum", "Material.Unit.Name", "Price", "Money", "Cm.Staff.Name"
        }, CheckMaterChild.class);
        return ResponseModel.ok(fileName);
    }

    private void setStaff(Page<CheckMaterChild> cmcList) {
        cmcList.forEach(item -> {
            CheckMater cm = item.getCm();
            if (!Objects.isNull(cm)) {
                cm.setStaff(staffService.getStaffByCoding(cm.getStaff().getCoding()));
            }
        });
    }

    private String getSortName(String name) {
        if (StringUtils.isNotBlank(name)) {
            switch (name) {
                case "money":
                    return "pm01605";
                case "price":
                    return "pm01606";
                case "realitySum":
                    return "pm01607";
                case "sum":
                    return "pm01604";
                case "state":
                    return "pm01513";
                case "checkNumber":
                case "cm.checkNumber":
                    return "pm01503";
                case "checkDate":
                case "cm.checkDate":
                default:
                    return "pm01502";
            }
        } else {
            return "pm01502";
        }
    }
}
