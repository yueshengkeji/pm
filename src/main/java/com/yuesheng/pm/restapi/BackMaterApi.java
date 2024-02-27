package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.BackMater;
import com.yuesheng.pm.entity.BackMaterChild;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.BackMaterChildService;
import com.yuesheng.pm.service.BackMaterService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "材料退库管理")
@RestController
@RequestMapping("api/backMater")
public class BackMaterApi extends BaseApi {

    @Autowired
    private BackMaterService backService;

    @Autowired
    private BackMaterChildService backMaterService;

    @Operation(description = "查询退料单列表")
    @GetMapping("list")
    public ResponsePage list(String start,
                             String end,
                             String str,
                             Integer itemsPerPage,
                             Integer page,
                             String sortBy,
                             Boolean sortDesc) {
        Map<String, Object> params = new HashMap(16);
        //获取材料集合
        params.put("str", str);
        params.put("start", start);
        params.put("end", end);
        startPage(page, itemsPerPage, getSortName(sortBy), sortDesc);
        Page<BackMater> backList = (Page<BackMater>) backService.getBackMater(params);
        return ResponsePage.ok(backList);
    }

    /**
     * 添加材料退库单(自动审核)
     *
     * @param backMater 退库单对象
     * @param session
     * @return
     */
    @Operation(description = "新增退料单-自动审核")
    @PutMapping("/addBackMater")
    public ResponseModel addBackMater(@RequestBody BackMater backMater, HttpSession session) {
        backService.addBackMater(backMater, (Staff) session.getAttribute(Constant.SESSION_KEY));
        return ResponseModel.ok(backMater);
    }

    /**
     * 删除退料单
     *
     * @param id 退料单主键
     * @return
     */
    @Operation(description = "删除退料单")
    @DeleteMapping("{id}")
    public ResponseModel deleteBackMater(@Parameter(name="退料单id") @PathVariable String id, HttpSession session) {
        //先反审核，再删除
        try {
            backService.deleteBack(id);
        } catch (Exception e) {
            ResponseModel.error(e.getMessage());
        }
        return ResponseModel.ok(id);
    }

    @Operation(description = "获取退料单材料列表")
    @GetMapping("materList/{backId}")
    public ResponseModel materList(@Parameter(name="退料单id") @PathVariable String backId) {
        return ResponseModel.ok(backMaterService.getMaters(backId));
    }

    @Operation(description = "审核、反审核退料单")
    @PostMapping("approve/{id}/{state}")
    public ResponseModel approve(@PathVariable String id, @PathVariable int state, HttpSession session) {
        try {
            backService.approveBack(id, DateFormat.getDate(), ((Staff) session.getAttribute(Constant.SESSION_KEY)).getCoding(), Integer.toString(state));
        } catch (Exception e) {
            return ResponseModel.error(e.getMessage());
        }
        return ResponseModel.ok(id);
    }

    @Operation(description = "修改入库单")
    @PostMapping("update")
    public ResponseModel update(@RequestBody BackMater backMater, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        try {
            backService.updateBackMater(backMater, staff);
        } catch (Exception e) {
            return ResponseModel.error(e.getMessage());
        }
        return ResponseModel.ok(backMater);
    }

    @Operation(description = "查询退料单明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        BackMater bm = backService.getBackById(id);
        if (!Objects.isNull(bm)) {
            bm.setMaters(backMaterService.getMaters(id));
        }
        return ResponseModel.ok(bm);
    }

    @Operation(description = "更新退料单材料")
    @RequestMapping("/updateBMChild")
    public ResponseModel updateBackMater(BackMaterChild mater) throws Exception {
        return ResponseModel.ok(backMaterService.update(mater));
    }

    @Operation(description = "获取退料单集合")
    @GetMapping("/history")
    public ResponsePage getBmHistory(@Parameter(name="开始时间") String startDate,
                                     @Parameter(name="截止时间") String endDate,
                                     @Parameter(name="搜索字符串") String searchText,
                                     @Parameter(name="项目id列表") String[] projects,
                                     @Parameter(name="数据页码") Integer page,
                                     @Parameter(name="数据大小") Integer itemsPerPage,
                                     @Parameter(name="排序字段") String sortBy,
                                     @Parameter(name="是否升序") Boolean sortDesc) throws Exception {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            throw new Exception("请指定查询日期范围");
        }
        Map<String, String> params = new HashMap<>(16);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("searchText", searchText);
        params.put("state", "1");
        if (!Objects.isNull(projects) && projects.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < projects.length; i++) {
                sb.append("'");
                sb.append(projects[i]);
                sb.append("',");
            }
            if (sb.length() > 0) {
                params.put("projects", sb.substring(0, sb.length() - 1));
            }
        }

        startPage(page, itemsPerPage, getSortName(sortBy), sortDesc);
        List<BackMaterChild> maters = backMaterService.getMaterByParam(params);
        HashMap<String, BackMater> bmCache = new HashMap<>();
        maters.forEach(item -> {
            String backId = item.getBackId();
            if (bmCache.containsKey(backId)) {
                item.setBm(bmCache.get(backId));
            } else {
                BackMater bm = backService.getBackById(backId);
                bmCache.put(backId, bm);
                item.setBm(bm);
            }
        });
        return ResponsePage.ok((Page) maters);
    }

    @Operation(description = "获取退库金额统计")
    @GetMapping("getBackTotalMoney")
    public ResponseModel getBackTotalMoney(@Parameter(name="搜索文本") String searchText,
                                           @Parameter(name="开始日期") String startDate,
                                           @Parameter(name="截止日期") String endDate,
                                           @Parameter(name="项目id列表") String[] projects) {
        Map<String, String> params = new HashMap<>(16);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("searchText", searchText);
        params.put("state", "1");
        if (!Objects.isNull(projects) && projects.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < projects.length; i++) {
                sb.append("'");
                sb.append(projects[i]);
                sb.append("',");
            }
            if (sb.length() > 0) {
                params.put("projects", sb.substring(0, sb.length() - 1));
            }
        }
        Double getBackTotalMoney = backMaterService.getBackTotalMoney(params);
        return new ResponseModel(getBackTotalMoney);
    }

    @Operation(description = "导出退库记录为excel")
    @GetMapping("exportExcel")
    public ResponseModel exportExcel(@Parameter(name="开始时间") String startDate,
                                     @Parameter(name="截止时间") String endDate,
                                     @Parameter(name="搜索字符串") String searchText,
                                     @Parameter(name="项目id列表") String[] projects,
                                     @Parameter(name="排序字段") String sortBy,
                                     @Parameter(name="是否升序") Boolean sortDesc,
                                     @Parameter(name="是否导出单价") Boolean exportMoney) throws Exception {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            throw new Exception("请指定查询日期范围");
        }
        Map<String, String> params = new HashMap<>(16);
        params.put("startDate", startDate + " 00:00:00");
        params.put("endDate", endDate + " 23:59:59");
        params.put("searchText", searchText);
        params.put("state", "1");
        if (!Objects.isNull(projects) && projects.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < projects.length; i++) {
                sb.append("'");
                sb.append(projects[i]);
                sb.append("',");
            }
            if (sb.length() > 0) {
                params.put("projects", sb.substring(0, sb.length() - 1));
            }
        }

        startPage(1, 50000, getSortName(sortBy), sortDesc);
        List<BackMaterChild> maters = backMaterService.getMaterByParam(params);
        HashMap<String, BackMater> bmCache = new HashMap<>();
        maters.forEach(item -> {
            String backId = item.getBackId();
            if (bmCache.containsKey(backId)) {
                item.setBm(bmCache.get(backId));
            } else {
                BackMater bm = backService.getBackById(backId);
                bmCache.put(backId, bm);
                item.setBm(bm);
            }
        });

        String fileName = "已审核退库材料清单" + startDate + "-" + endDate + ".xlsx";
        String[] exportHeaders;
        if (exportMoney) {
            exportHeaders = new String[]{
                    "Bm.Project.Name", "Bm.BackNumber", "Material.Name", "Material.Model", "Material.Brand",
                    "Bm.Date", "Sum", "Material.Unit.Name", "Price", "Money", "Bm.BackStaff.Name"};
        } else {
            exportHeaders = new String[]{
                    "Bm.Project.Name", "Bm.BackNumber", "Material.Name", "Material.Model", "Material.Brand",
                    "Bm.Date", "Sum", "Material.Unit.Name", "Bm.BackStaff.Name"};
        }
        fileName = ExcelParse.writeExcel(maters, fileName, exportHeaders, BackMaterChild.class);

        return ResponseModel.ok(fileName);
    }

    private String getSortName(String sortBy) {
        if (StringUtils.isNotBlank(sortBy)) {
            switch (sortBy) {
                case "project.name":
                case "bm.project.name":
                    return "pm02404";
                case "company.name":
                    return "pm02408";
                case "storage.name":
                    return "pm02406";
                case "staff.name":
                    return "pm02413";
                case "approveState":
                    return "pm02419";
                case "backNumber":
                case "bm.backNumber":
                    return "pm02403";
                case "sum":
                    return "pm02504";
                case "price":
                    return "pm02505";
                case "money":
                    return "pm02506";
                case "bm.backStaff.name":
                case "backStaff.name":
                    return "pm02405";
                case "backDate":
                case "bm.backDate":
                default:
                    return "pm02402";
            }
        }
        return "pm02402";
    }

}
