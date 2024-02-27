package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 供应单位接口
 */
@Tag(name = "相关单位管理")
@RestController
@RequestMapping("/api/company")
public class CompanyApi extends BaseApi {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProcurementMaterService procurementMaterService;
    @Autowired
    private PutStorageMaterialService putStorageMaterialService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProcurementService procurementService;

    @Operation(description = "搜索供应单位")
    @GetMapping
    public ResponseModel getCompanys(@Parameter(name="搜素字符串") String companyName) {
        return new ResponseModel(companyService.seekAll(StringUtils.isBlank(companyName) ? null : companyName));
    }

    @Operation(description = "搜索供应单位(分野)")
    @GetMapping("list")
    public ResponsePage getCompanys(@Parameter(name="搜素字符串") String companyName,
                                    @Parameter(name="页码") @RequestParam(defaultValue = "1") Integer page,
                                    @Parameter(name="数据大小") @RequestParam(defaultValue = "10") Integer itemsPerPage,
                                    @Parameter(name="排序字段") @RequestParam(defaultValue = "logDate") String sortBy,
                                    @Parameter(name="排序方式") @RequestParam(defaultValue = "DESC") String sortDesc) {
        startPage(page, itemsPerPage, getSortName(sortBy), sortDesc);
        Page<Company> companyPage = (Page<Company>) companyService.seekAllV2(StringUtils.isBlank(companyName) ? null : companyName);
        return ResponsePage.ok(companyPage);
    }

    private String getSortName(String sortBy) {
        switch (sortBy) {
            case "logDate":
                return "pf00311";
            case "name":
                return "pf00302";
            case "address":
                return "pf00308";
            default:
                return "pf00311";

        }
    }

    @Operation(description = "修改单位")
    @PostMapping
    public ResponseModel update(@RequestBody Company company) {
        return new ResponseModel(companyService.updateCompany(company));
    }

    @Operation(description = "检索单位")
    @GetMapping("getCompany")
    public ResponseModel getCompany(@Parameter String companyName) {
        Map<String, Object> params = new HashMap<>();
        if (companyName != null) {
            List<Company> seek = companyService.seekAll(companyName);
            params.put("rows", seek);
        }
        return new ResponseModel(params);
    }

    @Operation(description = "增加单位")
    @PostMapping("addCompany")
    public ResponseModel addCompany(@RequestBody Company company, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        return new ResponseModel(companyService.insert(company, staff));
    }

    @Operation(description = "获取单位对象")
    @GetMapping("getCompanyById")
    public ResponseModel getCompanyById(String id) {
        return new ResponseModel(companyService.getCompanyById(id));
    }

    @Operation(description = "导出采购记录")
    @GetMapping("exportProHistory")
    public ResponseModel exportProHistory(@Parameter(name="单位id") String companyId,
                                          @Parameter(name="开始日期") String start,
                                          @Parameter(name="截止日期") String end,
                                          @Parameter(name="检索文本") String searchText) {
        Company company = companyService.getCompanyById(companyId);
        HashMap<String, Procurement> temp = new HashMap(16);
        HashMap params = new HashMap(16);
        params.put("companyId", companyId);
        params.put("start", start);
        params.put("end", end);
        params.put("searchText", searchText);
        List<ProMaterial> materials = procurementMaterService.getProMatersByCompany(params);
        if (materials.size() > 10000) {
            return ResponseModel.error("导出数据过大，请缩短日期范围,分批导出");
        }
        /*for (int i = 0; i < materials.size(); i++) {
            ProMaterial material = materials.get(i);
            Procurement p = temp.get(material.getProId());
            if (Objects.isNull(p)) {
                p = procurementService.getProcurementSimpleById(material.getProId());
            }
            if (!Objects.isNull(p)) {
                material.setP(p);
                temp.put(material.getProId(), p);
            }
        }*/
        String fileName = start + "到" + end + "-" + company.getName() + "采购记录.xlsx";
        fileName = ExcelParse.writeExcel(materials, fileName,
                new String[]{"P.PmNumber", "Material.Name", "Material.Model", "Material.Brand", "Sum",
                        "Material.Unit.Name", "PriceTax", "MoneyTax", "P.Tax", "P.PmDate"},
                ProMaterial.class);
        return ResponseModel.ok(fileName);
    }

    @Operation(description = "获取供应单位采购记录")
    @GetMapping("proHistory")
    public ResponsePage proHistory(@Parameter(name="单位id") String companyId,
                                   @Parameter(name="开始日期") String start,
                                   @Parameter(name="截止日期") String end,
                                   @Parameter(name="检索文本") String searchText,
                                   @Parameter(name="是否加载采购订单") Boolean loadProcurement,
                                   Integer page,
                                   Integer itemsPerPage,
                                   String sortBy,
                                   Boolean sortDesc) {
        HashMap params = new HashMap(16);
        params.put("companyId", companyId);
        params.put("start", start);
        params.put("end", end);
        params.put("searchText", searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<ProMaterial> materials = (Page<ProMaterial>) procurementMaterService.getProMatersByCompany(params);
        if (BooleanUtils.isTrue(loadProcurement)) {
            HashMap<String, Procurement> temp = new HashMap<>();
            for (int i = 0; i < materials.size(); i++) {
                ProMaterial material = materials.get(i);
                Procurement p = temp.get(material.getProId());
                if (Objects.isNull(p)) {
                    p = procurementService.getProcurementSimpleById(material.getProId());
                }
                if (!Objects.isNull(p)) {
                    material.setP(p);
                    temp.put(material.getProId(), p);
                }
            }
        }
        return ResponsePage.ok(materials);
    }

    @Operation(description = "获取供应单位入库记录")
    @GetMapping("putHistory")
    public ResponsePage putHistory(@Parameter(name="单位id") String companyId,
                                   @Parameter(name="开始日期") String start,
                                   @Parameter(name="截止日期") String end,
                                   @Parameter(name="搜索文本") String searchText,
                                   @Parameter(name="材料id") String materialId,
                                   Integer page,
                                   Integer itemsPerPage,
                                   String sortBy,
                                   Boolean sortDesc) {
        Map<String, String> params = new HashMap<>(16);
//      供应单位id
        params.put("company", companyId);
//      开始时间
        params.put("start", start);
//      结束时间
        params.put("end", end);
        params.put("searchText", StringUtils.isBlank(searchText) ? null : searchText);
        params.put("materialId", StringUtils.isBlank(materialId) ? null : materialId);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<StorageMaterial> storageMaters = (Page<StorageMaterial>) putStorageMaterialService.getMaterialByCompany(params);
        setProject(storageMaters);
        return ResponsePage.ok(storageMaters);
    }

    @Operation(description = "获取入库金额统计")
    @GetMapping("getPutTotalMoney")
    public ResponseModel getPutTotalMoney(@Parameter(name="单位id") String companyId,
                                          @Parameter(name="开始日期") String start,
                                          @Parameter(name="截止日期") String end,
                                          @Parameter(name="搜索文本") String searchText,
                                          @Parameter(name="材料id") String materialId){
        Map<String, String> params = new HashMap<>(16);
//      供应单位id
        params.put("company", companyId);
//      开始时间
        params.put("start", start);
//      结束时间
        params.put("end", end);
        params.put("searchText", StringUtils.isBlank(searchText) ? null : searchText);
        params.put("materialId", StringUtils.isBlank(materialId) ? null : materialId);
        Double putTotalMoney = putStorageMaterialService.getPutTotalMoney(params);
        return new ResponseModel(putTotalMoney);
    }

    @Operation(description = "导出供应单位入库记录")
    @GetMapping("exportPutHistory")
    public ResponseModel exportPutHistory(@Parameter(name="单位id") String companyId,
                                          @Parameter(name="开始日期") String start,
                                          @Parameter(name="截止日期") String end,
                                          @Parameter(name="搜索文本") String searchText,
                                          @Parameter(name="材料id") String materialId,
                                          @Parameter(name="排序名称") String sortBy,
                                          @Parameter(name="排序类型") Boolean sortDesc) {
        if (StringUtils.isBlank(start)) {
            return ResponseModel.error("请指定时间范围");
        }
        List<StorageMaterial> storageMaters = null;
        Map<String, String> params = new HashMap<>(16);
//      供应单位id
        params.put("company", companyId);
//      开始时间
        params.put("start", start);
//      结束时间
        params.put("end", end);
        params.put("searchText", StringUtils.isBlank(searchText) ? null : searchText);
        params.put("materialId", StringUtils.isBlank(materialId) ? null : materialId);
        startPage(1, 50000, sortBy, sortDesc);
        storageMaters = putStorageMaterialService.getMaterialByCompany(params);
        // if (storageMaters.size() > 10000) {
        //     return ResponseModel.error("导出数据超过最大限制，请缩短日期范围，分批导出!");
        // }
        setProject(storageMaters);
        String fileName = start + "-" + end + "已审核入库记录.xlsx";
        fileName = ExcelParse.writeExcel(storageMaters, fileName,
                new String[]{"Pm02719", "Project.Name", "PutNumber", "Pm02718", "Material.Name", "Material.Model", "Material.Brand", "PutDate",
                        "PutSum", "Material.Unit.Name", "TaxPrice", "MoneyTax", "Tax", "Company.Name"},
                StorageMaterial.class);
        return ResponseModel.ok(fileName);
    }

    @Operation(description = "通过单位名称查询")
    @GetMapping("getByName")
    public ResponseModel getByName(String companyName) {
        return ResponseModel.ok(companyService.getByName(companyName));
    }

    @Operation(description = "模糊查询材料供应商")
    @GetMapping("getByMaterial")
    public ResponseModel getByMaterial(String name, String model, String brand) {
        startPage(1, 10, "pf00302", "desc");
        List<Company> companies = companyService.getByMaterial(name, model, brand);
        return ResponseModel.ok(companies);
    }

    private void setProject(List<StorageMaterial> storageMaters) {
        HashMap<String, Project> temp = new HashMap<>(16);
        for (int i = 0; i < storageMaters.size(); i++) {
            StorageMaterial item = storageMaters.get(i);
            Project p = temp.get(item.getProjectId());
            if (Objects.isNull(p)) {
                p = projectService.getProjectByid(item.getProjectId());
            }
            if (!Objects.isNull(p)) {
                item.setProject(p);
            }
            String coding= item.getMaterial().getId();
            if(StringUtils.isNotBlank(coding)){
                coding = StringUtils.substring(coding,0,StringUtils.indexOf(coding,"-"));
            }
            item.setPm02718(coding);
            item.setPm02719(i + 1);
        }
    }

}
