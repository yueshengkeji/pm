package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.ApplyMaterial;
import com.yuesheng.pm.entity.ProMaterial;
import com.yuesheng.pm.entity.Procurement;
import com.yuesheng.pm.model.ProMaterReport;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.ApplyMaterialService;
import com.yuesheng.pm.service.ApplyService;
import com.yuesheng.pm.service.ProcurementMaterService;
import com.yuesheng.pm.service.ProcurementService;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Tag(name = "采购订单材料明细")
@RestController
@RequestMapping("api/procurementMaterial")
public class ProcurementMaterialApi extends BaseApi {
    @Autowired
    private ProcurementMaterService procurementMaterServiceService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private ApplyService applyService;

    @Operation(description = "获取采购订单所有材料列表")
    @GetMapping
    public ResponseModel getMaterial(@Parameter(name="订单主体id") String proId, @RequestParam(defaultValue = "true") @Parameter(name="是否加载申请单价格") Boolean lap) {
        List<ProMaterial> materialList = procurementMaterServiceService.getProMatersByProId(proId);
        if (lap) {
            materialList.forEach(item -> {
                ApplyMaterial am = applyMaterialService.getMaterById(item.getMajor());
                if (!Objects.isNull(am)) {
                    item.setApplyid(am.getApplyid());
                    item.setApplyPrice(am.getPlanPrice());
                } else {
                    item.setApplyid("申请单已被删除");
                }
            });
        }
        return new ResponseModel(materialList);
    }

    private void setApplyPrice(List<ProMaterial> materialList) {
        for (ProMaterial mp : materialList) {
            try {
                ApplyMaterial am = applyMaterialService.getMaterById(mp.getMajor());
                if (!Objects.isNull(am)) {
                    mp.setApplyPrice(am.getPlanPrice());
                }
            } catch (NullPointerException e) {

            }
        }
    }

    @Operation(description = "获取未入库的材料列表")
    @GetMapping("noPut")
    public ResponseModel getNoPutMaterial(@Parameter(name="订单主体id") String proId) {
        return new ResponseModel(procurementMaterServiceService.getNotMatersByProId(proId));
    }

    @Operation(description = "获取材料历史采购单价列表（含税）")
    @GetMapping("price")
    public ResponseModel getProPrice(@Parameter(name="材料名称") String name,
                                     @Parameter(name="材料型号") String model,
                                     @Parameter(name="材料品牌") String brand,
                                     Integer size) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(name)) {
            name = URLDecoder.decode(name, "UTF-8");
        }
        if (StringUtils.isNotBlank(model)) {
            model = URLDecoder.decode(model, "UTF-8");
        }
        if (StringUtils.isNotBlank(brand)) {
            brand = URLDecoder.decode(brand, "UTF-8");
        }
        if (Objects.isNull(size)) {
            size = 10;
        }
        return new ResponseModel(procurementMaterServiceService.getHistoryPriceV2(name, model, brand, size, null, null));
    }

    @Operation(description = "删除采购订单中材料")
    @DeleteMapping("{proId}/{id}")
    public ResponseModel deleteById(@PathVariable @Parameter(name="采购订单id") String proId,
                                    @PathVariable @Parameter(name="采购订单材料明细行id") String id) {
        Procurement procurement = procurementService.getProcurementById(proId);
        if (Objects.isNull(procurement)) {
            return new ResponseModel(500, "采购订单不存在");
        } else if (procurement.getState() == 1) {
            return new ResponseModel(500, "采购订单已审核");
        } else {
            procurementMaterServiceService.deletemater(id);
            return new ResponseModel("操作成功");
        }
    }

    @Operation(description = "回滚材料到申请单状态")
    @PostMapping("returnToApply")
    public ResponseModel returnToApply(@RequestBody ProMaterial[] material) {
        StringBuffer sb = new StringBuffer();
        if (!Objects.isNull(material)) {
            for (ProMaterial pm : material) {
                if (!Objects.isNull(pm)) {
                    ProMaterial temp = procurementMaterServiceService.getMatersById(pm.getId());
                    if (temp.getInSum() != 0) {
                        sb.append(pm.getMaterial().getName());
                        sb.append("已入库，无法回滚\r\n");
                        continue;
                    }
                    ApplyMaterial am = applyMaterialService.getMaterById(temp.getMajor());
                    if (Objects.isNull(am)) {
                        sb.append(pm.getMaterial().getName());
                        sb.append("在申请单中不存在\r\n");
                    } else {
                        applyMaterialService.updateMaterProSum(am.getMajor(), -temp.getSum());
                        applyService.updateState(am.getApplyid(), 1);
                        procurementMaterServiceService.deletemater(pm.getId());
                    }
                }
            }
        }
        return new ResponseModel(sb.toString());
    }

    @Operation(description = "获取采购材料记录")
    @GetMapping("list")
    public ResponseModel list(String companyName,
                              String projectName,
                              String staffCoding,
                              String searchText,
                              Integer putState,
                              String startDate,
                              String endDate,
                              Integer page,
                              String state,
                              Integer itemsPerPage) {
        Map<String, Object> param = new HashMap<>();
        param.put("companyName", companyName);
        param.put("projectName", projectName);
        param.put("staffCoding", staffCoding);
        param.put("searchText", searchText);
        param.put("putState", putState);
        param.put("state", state);
        if (StringUtils.isNotBlank(startDate)) {
            param.put("startDate", startDate + " 00:00:00");
            param.put("endDate", endDate + " 23:59:59");
        }
        startPage(page, itemsPerPage, "pm01302", "DESC");
        Page<ProMaterReport> pmList = (Page) procurementMaterServiceService.getMaterialByParam(param);
        Map<String, Double> totalInfo = procurementMaterServiceService.getMaterialByQueryCount(param);
        param.clear();
        param.put("total", pmList.getTotal());
        param.put("rows", pmList);
        param.put("money", totalInfo.get("moneyTaxSum"));
        return new ResponseModel(param);
    }


    @Operation(description = "导出采购材料记录")
    @GetMapping("exportList")
    public ResponseModel exportList(String companyName,
                                    String projectName,
                                    String staffCoding,
                                    String searchText,
                                    Integer putState,
                                    String startDate,
                                    String endDate,
                                    String state,
                                    Integer page,
                                    Integer itemsPerPage) {
        Map<String, Object> param = new HashMap<>();
        param.put("companyName", companyName);
        param.put("projectName", projectName);
        param.put("staffCoding", staffCoding);
        param.put("searchText", searchText);
        param.put("putState", putState);
        param.put("state", state);
        if (StringUtils.isNotBlank(startDate)) {
            param.put("startDate", startDate + " 00:00:00");
            param.put("endDate", endDate + " 23:59:59");
        }
        startPage(page, 5000, "pm01302", "DESC");
        Page<ProMaterReport> pmList = (Page) procurementMaterServiceService.getMaterialByParam(param);
        String fileName = "采购记录.xlsx";
        fileName = ExcelParse.writeExcel(pmList, fileName, new String[]{
                "Material.Name", "Material.Model", "Material.Brand", "Sum", "Material.Unit.Name", "InSum",
                "PriceTax", "MoneyTax", "Company.Name", "Project.Name"
        }, ProMaterReport.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "获取未到货的材料列表")
    @GetMapping("noDhList")
    public ResponsePage<ProMaterial> getNoDhList(Integer page,
                                                 Integer itemPerPage,
                                                 String sortBy,
                                                 Boolean sortDesc,
                                                 String searchText,
                                                 String projectId,
                                                 String dhStartDate,
                                                 String dhEndDate) {
        HashMap<String, String> query = new HashMap<>();
        query.put("searchText", StringUtils.isBlank(searchText) ? null : searchText);
        query.put("projectId", StringUtils.isBlank(projectId) ? null : projectId);
        query.put("dhStartDate", StringUtils.isBlank(dhStartDate) ? null : dhStartDate);
        query.put("dhEndDate", StringUtils.isBlank(dhEndDate) ? null : dhEndDate);
        startPage(page, itemPerPage, sortBy, sortDesc);
        return new ResponsePage((Page<ProMaterial>) procurementMaterServiceService.getNoDhList(query));
    }

    @Operation(description = "获取作废采购单材料")
    @GetMapping("destroyList")
    public ResponseModel<ProMaterial> destroyList(String proId) {
        List<ProMaterial> materials = procurementMaterServiceService.getDestroyList(proId);
        return ResponseModel.ok(materials);
    }

    @Operation(description = "根据时间范围查询采购金额合计(已审核)")
    @GetMapping("getProMoneyByDate")
    public ResponseModel getProMoneyByDate(String startDate, String endDate) {
        return ResponseModel.ok(procurementMaterServiceService.getProMoneyByDate(null, startDate, endDate));
    }

}
