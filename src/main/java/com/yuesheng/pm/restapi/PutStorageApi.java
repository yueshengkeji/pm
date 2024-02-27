package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.PutStorageModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "入库单管理")
@RestController
@RequestMapping("api/putStorage")
public class PutStorageApi extends BaseApi {
    @Autowired
    private PutStorageService putStorageService;
    @Autowired
    private PutStorageMaterialService putStorageMaterialService;
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private ProPutSignService proPutSignService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ProcurementMaterService procurementMaterService;


    @Operation(description = "通过入库单id查询入库单对象")
    @GetMapping("byId/{id}")
    public ResponseModel getById(@PathVariable String id) {
        PutStorage ps = putStorageService.getStorageById(id);
        if (!Objects.isNull(ps)) {
            ps.setProcurement(procurementService.getProcurementById(ps.getProId()));
            ps.setStaff(staffService.getStaffById(ps.getPutPerson()));
        }
        return ResponseModel.ok(ps);
    }


    @Operation(description = "查询入库单集合")
    @GetMapping("list")
    public ResponsePage getPutStorage(@Parameter(name="审核标记筛选：0=未审核,1=已审核,0,1=所有") String type,
                                      @Parameter(name="数据开始日期") String start,
                                      @Parameter(name="数据截止日期") String end,
                                      @Parameter(name="数据索引位置") Integer page,
                                      @Parameter(name="数据大小") Integer itemsPerPage,
                                      @Parameter(name="指定仓库") String storage,
                                      @Parameter(name="排序字段名称") String sortBy,
                                      @Parameter(name="排序类型：asc/desc") Boolean sortDesc,
                                      @Parameter(name="指定供应单位名称") String searchCompany,
                                      @Parameter(name="指定入库单号") String searchSeries,
                                      @Parameter(name="全局搜索字符串") String searchText) {
        HashMap<String, Object> params = new HashMap(16);
        sortBy = getSortName(sortBy);
        params.put("str", searchText);
        params.put("searchCompany", searchCompany);
        params.put("start", start);
        params.put("end", end);
        params.put("type", type);
        params.put("storage", storage);
        params.put("searchSeries", searchSeries);
        Page<PutStorage> storages;
        if (StringUtils.isBlank(searchCompany) && StringUtils.isBlank(searchText)) {
            //快速检索
            params.put("str", searchSeries);
            if (StringUtils.isBlank(sortBy)) {
                startPage(page, itemsPerPage, new String[]{"pm02614", "pm02603", "pm02602"}, new Boolean[]{true, true, true}, false);
                storages = (Page<PutStorage>) putStorageService.getPutStorageListFast(params);
            } else {
                startPage(page, itemsPerPage, sortBy, sortDesc, false);
                storages = (Page<PutStorage>) putStorageService.getPutStorageListFast(params);
            }
        } else {
            //全局搜索
            if (StringUtils.isBlank(sortBy)) {
                startPage(page, itemsPerPage, new String[]{"pm02614", "pm02603", "pm02602"}, new Boolean[]{true, true, true}, false);
            } else {
                startPage(page, itemsPerPage, sortBy, sortDesc, false);
            }
            storages = (Page<PutStorage>) putStorageService.getPutStorageList(params);
        }
        return ResponsePage.ok(storages);
    }

    @GetMapping("getPutCount")
    public ResponseModel getPutCount(@Parameter(name="审核标记筛选：0=未审核,1=已审核,0,1=所有") String type,
                                     @Parameter(name="数据开始日期") String start,
                                     @Parameter(name="数据截止日期") String end,
                                     @Parameter(name="指定仓库") String storage,
                                     @Parameter(name="指定供应单位名称") String searchCompany,
                                     @Parameter(name="指定入库单号") String searchSeries,
                                     @Parameter(name="全局搜索字符串") String searchText) {
        HashMap<String, Object> params = new HashMap(16);
        params.put("str", searchText);
        params.put("searchCompany", searchCompany);
        params.put("start", start);
        params.put("end", end);
        params.put("type", type);
        params.put("storage", storage);
        params.put("searchSeries", searchSeries);
        if (StringUtils.isBlank(searchCompany) && StringUtils.isBlank(searchText)) {
            //快速检索
            return ResponseModel.ok(putStorageService.getPutStoragesListFastCount(params));
        } else {
            //全局搜索
            return ResponseModel.ok(putStorageService.getPutStorageListCount(params));
        }
    }

    private String getSortName(String sortName) {
        if (StringUtils.isBlank(sortName)) {
            return null;
        }
        switch (sortName) {
            case "approveType":
                return "pm02617";
            case "staff.name":
                return "pm02609";
            case "putSerial":
                return "pm02603";
            case "company.name":
                return "pm02604";
            case "project.name":
                return "pa00102";
            case "putDate":
                return "a.pm02602";
            default:
//                 return "a.pm02614 DESC,a.pm02603 DESC,a.pm02602 DESC";
                return null;
        }
    }

    @Operation(description = "获取入库单材料列表")
    @GetMapping("material")
    public ResponseModel getMaterial(@Parameter(name="入库单id") String putStorageId) {
        return new ResponseModel(putStorageMaterialService.getMaterAllByPutId(putStorageId));
    }

    @Operation(description = "获取入库单对象")
    @GetMapping("{putSerial}")
    public ResponseModel getPutStorage(@Param("入库单编号") @PathVariable String putSerial) {
        return new ResponseModel(putStorageService.getPutBySerial(com.yuesheng.pm.util.StrUtils.decodeStr(putSerial)));
    }

    @Operation(description = "采购订单一键入库")
    @PutMapping("putProMaterial")
    public ResponseModel putProMaterial(@RequestBody PutStorageModel putStorageModel, @Parameter(hidden = true) @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        PutStorage putStorage = new PutStorage();
        Storage storage = new Storage();
        storage.setId(putStorageModel.getStorageId());
        putStorage.setStorage(storage);
        putStorage.setProId(putStorageModel.getProId());
        int state = putStorageService.putStorageByPro(putStorage, procurementService.getProcurementById(putStorage.getProId()), staff);
        String errorMsg = getPutError(state);
        if (errorMsg.equals("")) {
            return new ResponseModel(4);
        } else {
            return new ResponseModel(500, errorMsg);
        }
    }

    public static String getPutError(int state) {
        String errorMsg = "";
        if (state == -1) {
            errorMsg = "采购订单不存在";
        } else if (state == -4) {
            errorMsg = "该订单已经入库";
        } else if (state == -2) {
            errorMsg = "只能入库自己的采购单";
        } else if (state == -5) {
            errorMsg = "未指定入库仓库";
        } else if (state == -3) {
            errorMsg = "没有可入库的材料";
        }
        return errorMsg;
    }

    @Operation(description = "指定材料入库", summary = "返回当前采购单入库状态：0=未入库，3=部分入库，4=完全入库")
    @PutMapping("partPutProMaterial")
    public ResponseModel partPutMaterial(@RequestBody PutStorageModel putStorageModel, @Parameter(hidden = true) @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        PutStorage putStorage1 = new PutStorage();
        putStorage1.setProId(putStorageModel.getProId());
        Storage storage = new Storage();
        storage.setId(putStorageModel.getStorageId());
        if (StringUtils.isNotBlank(putStorageModel.getSaleMoney())) {
            putStorage1.setSaleMoney(putStorageModel.getSaleMoney());
        }

        putStorage1.setStorage(storage);
        putStorage1.setMaterialList(putStorageModel.getMaterialList());
        if (StringUtils.isNotBlank(putStorageModel.getRemark())) {
            putStorage1.setRemark(putStorageModel.getRemark());
        }
        if (!Objects.isNull(putStorageModel.getTax())) {
            putStorage1.setTax(putStorageModel.getTax());
        }
        List<StorageMaterial> s = putStorageModel.getMaterialList();
        if (!Objects.isNull(s) && s.size() > 0) {
            Project p = new Project();
            p.setId(s.get(0).getProjectId());
            putStorage1.setProject(p);
        }
        int state = putStorageService.addPutStorageSelect(putStorage1, staff);
        String errorMsg = getPutError(state);
        if (errorMsg.equals("")) {
            return new ResponseModel(procurementService.getPutState(putStorageModel.getProId()));
        } else {
            return new ResponseModel(500, errorMsg);
        }
    }

    @Operation(description = "指定材料入库-签字确认", summary = "返回当前采购单入库状态：0=等待签字入库，3=部分入库，4=完全入库")
    @PutMapping("partPutProMaterialAndSign")
    public ResponseModel partPutMaterialAndSign(@RequestBody PutStorageModel putStorageModel, @Parameter(hidden = true) @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        PutStorage ps = new PutStorage();
        ps.setProId(putStorageModel.getProId());
        Storage storage = new Storage();
        storage.setId(putStorageModel.getStorageId());
        ps.setStorage(storage);
        ps.setMaterialList(putStorageModel.getMaterialList());
        ps.setProjectLeader(staffService.getStaffById(putStorageModel.getProjectLeaderId()));
        if (StringUtils.isNotBlank(putStorageModel.getSaleMoney())) {
            ps.setSaleMoney(putStorageModel.getSaleMoney());
        }
        ps.setPm02629(putStorageModel.getPastDate());
        if (StringUtils.isNotBlank(putStorageModel.getRemark())) {
            ps.setRemark(putStorageModel.getRemark());
        }
        if (!Objects.isNull(putStorageModel.getTax())) {
            ps.setTax(putStorageModel.getTax());
        }

        int state = putStorageService.addPutSign(ps, staff);
        String error = getPutError(state);
        if (error.equals("")) {
            return new ResponseModel(procurementService.getPutState(putStorageModel.getProId()));
        } else {
            return new ResponseModel(500, error);
        }
    }

    @Operation(description = "根据采购订单id查询是否存在签名确定入库单", summary = "返回null则代表没有，如果存在，则提示用户删除")
    @GetMapping("getProSign/{proId}")
    public ResponseModel querySignByProId(@PathVariable @Parameter(name="采购订单id") String proId) {
        ProPutSign sign = new ProPutSign();
        sign.setProId(proId);
        sign.setType(0);
        sign = proPutSignService.queryByParam(sign);
        if (!Objects.isNull(sign)) {
            sign.setSignStaff(staffService.getStaffById(sign.getSignStaffId()));
        }
        return new ResponseModel(sign);
    }

    @Operation(description = "删除入库签字")
    @DeleteMapping("deleteProSign/{proId}")
    public ResponseModel deleteSignByProId(@PathVariable @Parameter(name="采购订单id") String proId) {
        ProPutSign sign = new ProPutSign();
        sign.setProId(proId);
        sign.setType(0);
        List<ProPutSign> proPutSigns = proPutSignService.queryListByParam(sign);
        StringBuffer sb = new StringBuffer();
        proPutSigns.forEach(item -> {
            proPutSignService.deleteById(item.getId());
            sb.append(1);
        });
        return new ResponseModel(sb.length());
    }

    @Operation(description = "查询未生成账单的入库单列表")
    @GetMapping("noDetailList")
    public ResponseModel noDetailList(String startDate, String endDate) {
        startPage(1, 500, "pm02602", "desc");
        return new ResponseModel(putStorageService.getNoDetailList(startDate, endDate));
    }

    @Operation(description = "初始化采购对账单明细")
    @PutMapping("initProDetail/{proId}")
    public ResponseModel initProDetail(@PathVariable String proId) {
        Procurement p = procurementService.getProcurementById(proId);
        procurementService.genProDetail(p, procurementMaterService.getProMatersByProId(p.getId()), false, null);
        return ResponseModel.ok();
    }

    @Operation(description = "生成采购入库对账单")
    @PutMapping("genProDetail/{proId}/{putId}")
    public ResponseModel genProDetail(@PathVariable String proId, @PathVariable String putId) {
        Procurement p = procurementService.getProcurementById(proId);
        List<StorageMaterial> materials = putStorageMaterialService.getMaterAllByPutId(putId);
        procurementService.genProDetailByPut(p, materials, true, putId);
        return ResponseModel.ok();
    }

    @Operation(description = "生成仓库入库审核对账单")
    @PutMapping("genPutDetail/{putId}")
    public ResponseModel genPutDetail(@PathVariable String putId) {
        putStorageService.genPutDetail(putStorageService.getStorageById(putId));
        return ResponseModel.ok();
    }

    @Operation(description = "查询已审核的入库总金额（含税）")
    @GetMapping("getPutMoney")
    public ResponseModel getPutMoney(String startDate, String endDate) {
        Double money = putStorageMaterialService.getPutMoneyByDate(startDate, endDate);
        return ResponseModel.ok(money);
    }

    @Operation(description = "根据时间分组查询入库总金额（含税）")
    @GetMapping("getPutMoneyByGroup")
    public ResponseModel getPutMoneyByGroup(String[] startDate, String[] endDate) {
        HashMap<String, Double> result = new HashMap<>();
        for (int i = 0; i < startDate.length; i++) {
            Double m = putStorageMaterialService.getPutMoneyByDate(startDate[i], endDate[i]);
            if (Objects.isNull(m)) {
                m = new Double(0);
            }
            result.put(startDate[i], m);
        }
        return ResponseModel.ok(result);
    }

    @Operation(description = "审核/反审核入库单")
    @PostMapping("approve")
    public ResponseModel approve(@RequestBody PutStorage putStorage, @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws Exception {
        return ResponseModel.ok(putStorageService.approve(putStorage, staff));
    }

    @Operation(description = "删除入库单")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        return ResponseModel.ok(putStorageService.delete(id, staff));
    }

    @Operation(description = "新增其他入库")
    @PutMapping("otherPut")
    public ResponseModel insert(@RequestBody PutStorage storage, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        putStorageService.addOtherStorage(storage, staff);
        storage.setStaff(staff);
        return ResponseModel.ok(storage);
    }

    @Operation(description = "根据项目，查询入库单材料(库存数>0的数据)")
    @GetMapping("getMaterByProject/{projectId}")
    public ResponseModel getMaterByProject(@PathVariable String projectId, String searchText) {
        startPage(1, 5000, "pm02614", "desc");
        List<StorageMaterial> storageMaterials = putStorageMaterialService.getMaterListByProject(projectId, searchText);
        return ResponseModel.ok(storageMaterials);
    }

    @Operation(description = "根据采购单材料行id，查询入库单材料行数据")
    @GetMapping("getByProMaterId/{proMaterId}")
    public ResponseModel getByProMaterId(@PathVariable String proMaterId) {
        List<StorageMaterial> sm = putStorageMaterialService.getByProMaterId(proMaterId);
        sm.forEach(item -> {
            item.setPutStorage(putStorageService.getStorageById(item.getStorageId()));
        });
        return ResponseModel.ok(sm);
    }

    @Operation(description = "查询材料入库记录")
    @GetMapping("putHistory")
    public ResponseModel putHistory(String[] materIdArray) {
        ArrayList<StorageMaterial> smList = new ArrayList<>();
        if (!Objects.isNull(materIdArray) && materIdArray.length < 100) {
            for (String id : materIdArray) {
                StorageMaterial sm = putStorageMaterialService.getPutDetailByMaterId(id);
                smList.add(sm);
            }
        }
        return ResponseModel.ok(smList);
    }

    @Operation(description = "查询入库总数")
    @GetMapping("getTotal")
    public ResponseModel getTotal(String start, String end, Integer type, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Map<String, Object> params = new HashMap(16);
        params.put("start", start);
        params.put("end", end);
        params.put("type", type);
        params.put("staffCoding", staff.getCoding());
        Integer sum = putStorageService.getCount(params);
        return ResponseModel.ok(sum);
    }

    @Operation(description = "删除入库单材料行")
    @DeleteMapping("deleteMaterial/{putId}/{id}")
    public ResponseModel deleteMaterial(@PathVariable String putId,@PathVariable String id){
        PutStorage ps = putStorageService.getStorageById(putId);
        if(!Objects.isNull(ps) && ps.getApproveType() == 0){
            putStorageMaterialService.deleteMaterById(putId,id);
            return ResponseModel.ok(id);
        }else{
            return ResponseModel.error("入库单不存在或已审核,禁止修改");
        }
    }

    @Operation(description = "查询入库单列表")
    @GetMapping("byProId/{proId}")
    public ResponseModel byProId(@PathVariable String proId){
        List<PutStorage> psList = putStorageService.getPutStorageByProId(proId);
        psList.forEach(item->{
            item.setApprove(staffService.getStaffByCoding(item.getApproveStaff()));
        });
        return ResponseModel.ok(psList);
    }
}
