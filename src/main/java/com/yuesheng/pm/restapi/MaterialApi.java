package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.Cell;
import com.yuesheng.pm.model.MaterUseHistory;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.MaterialService;
import com.yuesheng.pm.service.OutMaterChildService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.EntityUtils;
import com.yuesheng.pm.util.ExcelParse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Tag(name = "原始材料基础信息")
@RestController
@RequestMapping("api/material")
public class MaterialApi extends BaseApi {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private OutMaterChildService outMaterChildService;

    @Operation(description = "精确查找材料库存信息")
    @GetMapping("queryStorage")
    public ResponseModel queryStorage(@RequestParam(defaultValue = "put_datetime") @Parameter(name="排序属性") String sort, @RequestParam(defaultValue = "desc") @Parameter(name="排序方式") String order, @Parameter(name="检索材料名称") String materialName, @Parameter(name="检索材料型号") String materialModel, @Parameter(name="检索材料品牌") String materialBrand, @Parameter(name="指定仓库") String storage) throws UnsupportedEncodingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("materialName", StringUtils.isBlank(materialName) ? null : URLDecoder.decode(materialName, "UTF-8"));
        params.put("materialModel", StringUtils.isBlank(materialModel) ? null : URLDecoder.decode(materialModel, "UTF-8"));
        params.put("materialBrand", StringUtils.isBlank(materialBrand) ? null : URLDecoder.decode(materialBrand, "UTF-8"));
        params.put("storage", storage);
        params.put("repertory", "1");
        startPage(1, 10, sort, order);
        Page<Material> materials = (Page<Material>) materialService.queryStorage(params);
        return ResponseModel.ok(materials);
    }

    @Operation(description = "查询材料信息")
    @GetMapping("seekMaterial")
    public ResponseModel getMaterialService(@RequestParam(defaultValue = "put_datetime") @Parameter(name="排序属性") String sort, @RequestParam(defaultValue = "desc") @Parameter(name="排序方式") String order, @Parameter(name = "数据页索引", required = true) Integer offset, @Parameter(name = "数据大小", required = true) Integer limit, @Parameter(name="指定材料目录") String folder, @Parameter(name="材料类型") Integer type, @Parameter(name="指定仓库") String storage, @Parameter(name="模糊查询字符串") String str, @Parameter(name="检索材料名称") String materialName, @Parameter(name="检索材料型号") String materialModel, @Parameter(name="检索材料品牌") String materialBrand, @Parameter(name="是否加载辅材") String isLoadFuCai, @Parameter(name="是否加载有库存的材料") Boolean repertory) throws UnsupportedEncodingException {
        Map<String, Object> result = new HashMap<>(6);
//        获取材料集合
        Page<Material> materials = list(sort, order, offset, limit, folder, type, storage, str, materialName, materialModel, materialBrand, isLoadFuCai, repertory);
//        获取总数目
        result.put("rows", materials);
        result.put("total", 1000);
        return new ResponseModel(result);
    }

    @Operation(description = "导出有库存的材料信息")
    @GetMapping("exportMaterial")
    public ResponseModel exportMaterial(@RequestParam(defaultValue = "put_datetime") @Parameter(name="排序属性") String sort,
                                        @RequestParam(defaultValue = "desc") @Parameter(name="排序方式") String order,
                                        @Parameter(name = "数据页索引", required = true) Integer offset,
                                        @Parameter(name = "数据大小", required = true) Integer limit,
                                        @Parameter(name="指定材料目录") String folder,
                                        @Parameter(name="材料类型") Integer type,
                                        @Parameter(name="指定仓库") String storage,
                                        @Parameter(name="模糊查询字符串") String str,
                                        @Parameter(name="检索材料名称") String materialName,
                                        @Parameter(name="检索材料型号") String materialModel,
                                        @Parameter(name="检索材料品牌") String materialBrand,
                                        @Parameter(name="是否加载辅材") String isLoadFuCai) throws UnsupportedEncodingException {

        //        获取材料集合
        Page<Material> materials = list(sort, order, offset, limit, folder, type, storage, str, materialName, materialModel, materialBrand, isLoadFuCai, true);
        if (materials.getTotal() > 5000) {
            return ResponseModel.error("数据过大，请缩小数据范围");
        }
        for (int i = 0; i < materials.size(); i++) {
            materials.get(i).setFolder(String.valueOf(i + 1));
        }
        String fileName = "库存材料列表.xlsx";
        fileName = ExcelParse.writeExcel(materials, fileName, new String[]{"Folder", "Name", "Model", "Brand", "Unit.Name", "StorageSum", "CostPrice", "LastPutDate", "PutMoney"}, Material.class);
        return ResponseModel.ok(fileName);
    }


    private Page<Material> list(@Parameter(name="排序属性") @RequestParam(defaultValue = "put_datetime") String sort, @Parameter(name="排序方式") @RequestParam(defaultValue = "desc") String order, @Parameter(name = "数据页索引", required = true) Integer offset, @Parameter(name = "数据大小", required = true) Integer limit, @Parameter(name="指定材料目录") String folder, @Parameter(name="材料类型") Integer type, @Parameter(name="指定仓库") String storage, @Parameter(name="模糊查询字符串") String str, @Parameter(name="检索材料名称") String materialName, @Parameter(name="检索材料型号") String materialModel, @Parameter(name="检索材料品牌") String materialBrand, @Parameter(name="是否加载辅材") String isLoadFuCai, @Parameter(name="是否加载有库存的材料") Boolean repertory) throws UnsupportedEncodingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("str", StringUtils.isBlank(str) ? null : URLDecoder.decode(str, "UTF-8"));
        params.put("materialName", StringUtils.isBlank(materialName) ? null : URLDecoder.decode(materialName, "UTF-8"));
        params.put("materialModel", StringUtils.isBlank(materialModel) ? null : URLDecoder.decode(materialModel, "UTF-8"));
        params.put("materialBrand", StringUtils.isBlank(materialBrand) ? null : URLDecoder.decode(materialBrand, "UTF-8"));
        params.put("storage", storage);
        params.put("repertory", repertory);
        params.put("folder", null != folder ? "'" + folder + "'" : null);
        params.put("isFuCai", isLoadFuCai);
//        获取材料
        startPage(offset, limit, sort, order, false);
        Page<Material> materials = (Page<Material>) materialService.getMaterialByType(params);
        //设置辅材价格
        if (params.get("isFuCai") != null) {
            materials.forEach(item -> {
                if (!Objects.isNull(item)) {
                    item.setPlanPrice(materialService.getFucaiPrice(item.getId()));
                }
            });
        }
//        //查询最后入库时间、入库单价
//        if (repertory != null && repertory) {
//            materials.forEach(sm -> {
//                if (StringUtils.equals(sm.getLastPutDate(), sm.getDate()) || StringUtils.isBlank(sm.getLastPutDate())) {
//                    //查找仓库中材料最后入库价格
//                    StorageMaterial putSm = putStorageMaterialService.getPutDateByMaterId(sm.getId());
//                    if (putSm != null) {
//                        sm.setLastPutDate(putSm.getPutDate());
//                        materialService.updateLastPutDate(sm);
//                    }
//                }
//            });
//        }
        return materials;
    }

    @GetMapping("listCount")
    public ResponseModel listCount(@RequestParam(defaultValue = "put_datetime") @Parameter(name="排序属性") String sort, @RequestParam(defaultValue = "desc") @Parameter(name="排序方式") String order, @Parameter(name = "数据页索引", required = true) Integer offset, @Parameter(name = "数据大小", required = true) Integer limit, @Parameter(name="指定材料目录") String folder, @Parameter(name="材料类型") Integer type, @Parameter(name="指定仓库") String storage, @Parameter(name="模糊查询字符串") String str, @Parameter(name="检索材料名称") String materialName, @Parameter(name="检索材料型号") String materialModel, @Parameter(name="检索材料品牌") String materialBrand, @Parameter(name="是否加载辅材") String isLoadFuCai, @Parameter(name="是否加载有库存的材料") Boolean repertory) throws UnsupportedEncodingException {
        return ResponseModel.ok(listCount(folder, type, storage, str, materialName, materialModel, materialBrand, isLoadFuCai, repertory));
    }

    private Integer listCount(String folder, Integer type, String storage, String str, String materialName, String materialModel, String materialBrand, String isLoadFuCai, Boolean repertory) throws UnsupportedEncodingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("str", StringUtils.isBlank(str) ? null : URLDecoder.decode(str, "UTF-8"));
        params.put("materialName", StringUtils.isBlank(materialName) ? null : URLDecoder.decode(materialName, "UTF-8"));
        params.put("materialModel", StringUtils.isBlank(materialModel) ? null : URLDecoder.decode(materialModel, "UTF-8"));
        params.put("materialBrand", StringUtils.isBlank(materialBrand) ? null : URLDecoder.decode(materialBrand, "UTF-8"));
        params.put("storage", storage);
        params.put("repertory", repertory);
        params.put("folder", null != folder ? "'" + folder + "'" : null);
        params.put("isFuCai", isLoadFuCai);
        return materialService.getMaterByTypeCount(params);
    }


    @Operation(description = "根据计划单材料数组添加材料，材料是否存在判定方法：名称+型号+品牌）")
    @PutMapping("/insertByPlanMaterial/{isFuCai}")
    public ResponseModel insertMaterial(@RequestBody PlanMaterial[] material, @PathVariable String isFuCai, @SessionAttribute("user") Staff staff) {
        for (int i = 0; i < material.length; i++) {
            materialService.insert(staff, material[i].getMaterial());
            if (material[i].getId() != null && "1".equals(isFuCai)) {
                //添加到辅材
                PlanMaterial pm = material[i];
                try {
                    if (pm.getPlanPrice() != null) {
                        pm.getMaterial().setPlanPrice(pm.getPlanPrice());
                    }
                    materialService.insertFucai(pm.getMaterial());
                } catch (Exception e) {
                    //忽略已存在的辅材
                    if (pm.getPlanPrice() != null) {
                        materialService.updateFucaiPrice(pm.getMaterial().getId(), pm.getPlanPrice());
                    }
                }
            }

        }
        return new ResponseModel(material);
    }

    @Operation(description = "通过任务单添加材料对象")
    @PutMapping("insertByProjectTask")
    public ResponseModel insertMaterial(@RequestBody ProjectTask[] projectTask, @SessionAttribute("user") Staff staff) {
        for (int i = 0; i < projectTask.length; i++) {
            ProjectTask pt = projectTask[i];
            for (int j = 0; j < pt.getMaterialList().size(); j++) {
                materialService.insert(staff, pt.getMaterialList().get(j).getMaterial());
            }
        }
        return new ResponseModel(projectTask);
    }

    @Operation(description = "添加材料")
    @PostMapping("insertMaterial")
    public ResponseModel insertMaterial(@RequestBody Material material, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        materialService.insert(staff, material);
        if (StringUtils.isNotBlank(material.getProperty())) {
            try {
                JSONObject object = JSONObject.parseObject(material.getProperty());
                if (!Objects.isNull(object.get("state"))) {
                    return new ResponseModel(500, object.get("msg").toString());
                }
            } catch (Exception ignore) {

            }
        }
        return new ResponseModel(material);
    }

    @GetMapping("outHistory")
    public ResponseModel getOutHistory(String name, String model, String brand, String startDate, String endDate, Integer page, String[] projectsId, Integer itemsPerPage, String sortBy, Boolean sortDesc) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("model", model);
        param.put("brand", brand);
        String p = getProjectsId(projectsId);
        if (!Objects.isNull(projectsId) && projectsId.length == 1) {
            param.put("single", projectsId[0]);
        } else {
            param.put("projectId", p);
        }
        if (StringUtils.isNotBlank(startDate)) {
            param.put("startDate", startDate);
            param.put("endDate", endDate);
        }
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<MaterUseHistory> historyList = (Page<MaterUseHistory>) outMaterChildService.getOutMaterHistory(param);
        param.clear();
        param.put("rows", historyList);
        param.put("total", historyList.getTotal());
        return new ResponseModel(param);
    }

    public static String getProjectsId(String[] projectsId) {
        StringBuffer sb = new StringBuffer();
        if (!Objects.isNull(projectsId) && projectsId.length > 0) {
            for (String p : projectsId) {
                sb.append("'");
                sb.append(p);
                sb.append("',");
            }
        }
        if (sb.length() > 0) {
            return sb.subSequence(0, sb.length() - 1).toString();
        } else {
            return null;
        }
    }


    @Operation(description = "导出出库记录")
    @GetMapping("exportOutHistory")
    public ResponseModel exportOutHistory(String name,
                                          String model,
                                          String brand,
                                          String startDate,
                                          String[] projectsId,
                                          String pn,
                                          String endDate,
                                          String sortBy,
                                          Boolean showPrice,
                                          Boolean sortDesc) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("model", model);
        String p = getProjectsId(projectsId);
        if (!Objects.isNull(projectsId) && projectsId.length == 1) {
            param.put("single", projectsId[0]);
        } else {
            param.put("projectId", p);
        }
        if(StringUtils.isBlank(pn)){
            pn = "";
        }
        param.put("brand", brand);
        if (StringUtils.isNotBlank(startDate)) {
            param.put("startDate", startDate);
            param.put("endDate", endDate);
        }
        Integer total = outMaterChildService.getOutMaterHistoryCount(param);
        if (total > 10000) {
            return new ResponseModel(500, "数据过大，请缩小筛选条件");
        }
        startPage(1, total, sortBy, sortDesc);
        List<MaterUseHistory> historyList = outMaterChildService.getOutMaterHistory(param);
        String fileName;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            fileName = pn + startDate + "-" + endDate + "已审核出库记录.xlsx";
        } else {
            fileName = pn + "已审核出库记录.xlsx";
        }
        for (int i = 0; i < historyList.size(); i++) {
            MaterUseHistory item = historyList.get(i);
            String coding = item.getMaterial().getId();
            if (StringUtils.isNotBlank(coding)) {
                coding = StringUtils.substring(coding, 0, StringUtils.indexOf(coding, "-"));
            }
            item.setCoding(coding);
            item.setIndex(i + 1);
        }
        String[] headers;
        if (Objects.isNull(showPrice) || !showPrice) {
            headers = new String[]{"Index", "Project.Name", "OutNumber", "Coding", "Material.Name", "Material.Model", "Material.Brand", "OutDate", "OutSum", "Material.Unit.Name"};
        } else {
            headers = new String[]{"Index", "Project.Name", "OutNumber", "Coding", "Material.Name", "Material.Model", "Material.Brand", "OutDate", "OutSum", "Material.Unit.Name", "OutPrice", "OutMoney"};
        }

        fileName = ExcelParse.writeExcel(historyList, fileName, headers, MaterUseHistory.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "删除辅材")
    @DeleteMapping("deleteFucai/{id}")
    public ResponseModel deleteFucai(String id) {
        return new ResponseModel(materialService.deleteFucai(id));
    }

    @Operation(description = "获取材料对象")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(materialService.getMaterialByid(id));
    }

    private List<Cell> getCells(MaterUseHistory materUseHistory) {
        String[] values = new String[]{"OutDate", "Material.Name", "Material.Model", "Material.Brand", "OutSum", "Material.Unit.Name", "OutPrice", "OutMoney"};
        return EntityUtils.getCells(materUseHistory, values);
    }

    private List<Cell> getHander() {
        ArrayList<Cell> arrayList = new ArrayList<>();
        String[] names = new String[]{"日期", "材料名称", "型号", "品牌", "出库数量", "单位", "单价", "合计金额"};
        for (int i = 0; i < names.length; i++) {
            Cell cell = new Cell();
            cell.setIndex(i);
            cell.setName(names[i]);
            if (i == 0) {
                cell.setWidth((float) 42.8 * 42 * 3);
            } else if (i == 1) {
                cell.setWidth((float) 48.8 * 48 * 3);
            } else if (i == 2) {
                cell.setWidth((float) 58.8 * 58 * 3);
            }
            arrayList.add(cell);
        }
        return arrayList;
    }

}
