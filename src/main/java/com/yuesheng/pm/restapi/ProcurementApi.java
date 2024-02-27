package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ProMaterialModel;
import com.yuesheng.pm.model.ProcurementModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@Tag(name = "采购订单主体")
@RestController
@RequestMapping("api/procurement")
public class ProcurementApi extends BaseApi {
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private ProcurementMaterService procurementMaterService;
    @Autowired
    private ProDownloadHistoryService proDownloadHistoryService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private ApplyDeleteService applyDeleteService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private PlanMaterialService planMaterialService;

    @Operation(description = "获取采购订单列表", summary = "返回值total属性=数据总数，rows=数据列表")
    @GetMapping
    public ResponseModel getListByParam(@Parameter(name="数据大小") Integer pageSize,
                                        @Parameter(name="当前数据索引位置") Integer pageNumber,
                                        @Parameter(name="全局检索字符串（需要对字符串编码）") String str,
                                        @Parameter(name="指定数据开始日期") String start,
                                        @Parameter(name="指定数据截止日期") String end,
                                        @Parameter(name="排序名称（参考采购订单主体对象属性") String sortName,
                                        @Parameter(name="排序类型：asc(升序)/desc(降序)") String sortOrder,
                                        @Parameter(name="指定职员id") String staffId,
                                        @Parameter(name="入库状态：0=未入库，3=部分入库，4=完全入库，例：加载可入库的单据：'0,3'=可以入库") String[] putState,
                                        @Parameter(name="筛选：采购单位id") String companyId,
                                        @Parameter(name="筛选：是否有合同,0=无合同，1=有合同") String isContract,
                                        @Parameter(name="筛选：是否有发票的订单") String isInvoice,
                                        @Parameter(name="筛选：订单合同状态(0=未付清，1=有合同未登记,2=已付清未开票，3=已付清已开票，4=没有合同，不传此参数 获取所有)") String filter,
                                        @Parameter(name="筛选：是否发起审批,1=未发起,null=获取所有") String approveState,
                                        @Parameter(name="模糊查询：搜索采购单位字符串(需要对字符串编码)") String searchCompany,
                                        @Parameter(name="筛选：采购订单状态：0=未审核，1=已审核,2=未发起'0,1,2'=所有单据") String[] state,
                                        @Parameter(name="筛选：1=未下载凭证，不传默认加载所有，") String isDownload,
                                        @Parameter(name="单据类型，1=加载作废单据，不传默认加载正常单据") String discard,
                                        @Parameter(name="指定材料字符串") String searchMaterial,
                                        @Parameter(name="") String isColie) {
        Map<String, Object> rp = new HashMap<>(16);
        rp.put("str", StringUtils.isBlank(str) ? null : str);
        rp.put("searchProject", StringUtils.isBlank(searchCompany) ? null : searchCompany);
        rp.put("start", start);
        rp.put("end", end);
        sortName = getSortName(sortName);
        // rp.put("order", MaterialController.isSort(sortName, StringUtils.isBlank(sortOrder) ? "DESC" : sortOrder));
        if (!Objects.isNull(state) && state.length != 3) {
            rp.put("state", Constant.inStrParse(state));
        }
        rp.put("staffId", StringUtils.isBlank(staffId) ? null : staffId);
        rp.put("company", "".equals(companyId) ? null : companyId);
        rp.put("isContract", isContract);
        rp.put("isInvoice", isInvoice);
        rp.put("as", approveState);
        rp.put("isDownload", isDownload);
        rp.put("discard", StringUtils.isBlank(discard) ? "" : "_discard");
        rp.put("isColie", StringUtils.isBlank(isColie) ? null : isColie);
        rp.put("searchMaterial", StringUtils.isBlank(searchMaterial) ? null : searchMaterial);
        rp.put("sort", sortName + " " + sortOrder);
        filter = "".equals(filter) ? null : filter;
        if (filter != null) {
            switch (filter) {
                case "0":       //未付清
                    filter = "f.pd00409 > f.pd00448";
                    break;
                case "1":       //有合同，未登记
                    filter = "a.pm01318 = 1 AND a.pm01305=''";
                    break;
                case "2":       //已付清，未开票
                    filter = "f.pd00448 >= f.pd00409";
                    rp.put("isInvoice", "'1'");
                    break;
                case "3":       //已付清，开票
                    filter = "f.pd00448 >= f.pd00409";
                    rp.put("isInvoice", "'2'");
                    break;
                case "4":       //没有合同
                    rp.put("isContract", "0");
                    filter = "a.pm01305=''";
                    break;
                default:        //所有
                    filter = null;
                    break;
            }
            rp.put("filter", filter);
        }
        if (putState != null && putState.length > 0) {
            rp.put("putState", Constant.inStrParse(putState));
        }
        startPage(pageNumber, pageSize, sortName, sortOrder, false);
        return ResponseModel.ok(procurementService.getProcurementByParam(rp));
    }

    @GetMapping("getCount")
    public ResponseModel getCount(@Parameter(name="全局检索字符串（需要对字符串编码）") String str,
                                  @Parameter(name="指定数据开始日期") String start,
                                  @Parameter(name="指定数据截止日期") String end,
                                  @Parameter(name="指定职员id") String staffId,
                                  @Parameter(name="入库状态：0=未入库，3=部分入库，4=完全入库，例：加载可入库的单据：'0,3'=可以入库") String[] putState,
                                  @Parameter(name="筛选：采购单位id") String companyId,
                                  @Parameter(name="筛选：是否有合同,0=无合同，1=有合同") String isContract,
                                  @Parameter(name="筛选：是否有发票的订单") String isInvoice,
                                  @Parameter(name="筛选：订单合同状态(0=未付清，1=有合同未登记,2=已付清未开票，3=已付清已开票，4=没有合同，不传此参数 获取所有)") String filter,
                                  @Parameter(name="筛选：是否发起审批,1=未发起,null=获取所有") String approveState,
                                  @Parameter(name="模糊查询：搜索采购单位字符串(需要对字符串编码)") String searchCompany,
                                  @Parameter(name="筛选：采购订单状态：0=未审核，1=已审核,2=未发起'0,1,2'=所有单据") String[] state,
                                  @Parameter(name="筛选：1=未下载凭证，不传默认加载所有，") String isDownload,
                                  @Parameter(name="单据类型，1=加载作废单据，不传默认加载正常单据") String discard,
                                  @Parameter(name="指定材料字符串") String searchMaterial,
                                  @Parameter(name="") String isColie) {
        Map<String, Object> rp = new HashMap<>(16);
        rp.put("str", StringUtils.isBlank(str) ? null : str);
        rp.put("searchProject", StringUtils.isBlank(searchCompany) ? null : searchCompany);
        rp.put("start", start);
        rp.put("end", end);
        if (!Objects.isNull(state) && state.length != 3) {
            rp.put("state", Constant.inStrParse(state));
        }
        rp.put("staffId", StringUtils.isBlank(staffId) ? null : staffId);
        rp.put("company", "".equals(companyId) ? null : companyId);
        rp.put("isContract", isContract);
        rp.put("isInvoice", isInvoice);
        rp.put("as", approveState);
        rp.put("isDownload", isDownload);
        rp.put("discard", StringUtils.isBlank(discard) ? "" : "_discard");
        rp.put("isColie", StringUtils.isBlank(isColie) ? null : isColie);
        rp.put("searchMaterial", StringUtils.isBlank(searchMaterial) ? null : searchMaterial);
        filter = "".equals(filter) ? null : filter;
        if (filter != null) {
            switch (filter) {
                case "0":       //未付清
                    filter = "f.pd00409 > f.pd00448";
                    break;
                case "1":       //有合同，未登记
                    filter = "a.pm01318 = 1 AND a.pm01305=''";
                    break;
                case "2":       //已付清，未开票
                    filter = "f.pd00448 >= f.pd00409";
                    rp.put("isInvoice", "'1'");
                    break;
                case "3":       //已付清，开票
                    filter = "f.pd00448 >= f.pd00409";
                    rp.put("isInvoice", "'2'");
                    break;
                case "4":       //没有合同
                    rp.put("isContract", "0");
                    filter = "a.pm01305=''";
                    break;
                default:        //所有
                    filter = null;
                    break;
            }
            rp.put("filter", filter);
        }
        if (putState != null && putState.length > 0) {
            rp.put("putState", Constant.inStrParse(putState));
        }
        return ResponseModel.ok(procurementService.getProCountByParam(rp));
    }

    @Operation(description = "获取采购订单明细")
    @GetMapping("getById/{id}")
    public ResponseModel getById(@PathVariable String id,
                                 Boolean noLoadApply) {
        Procurement p = procurementService.getProcurementById(id);
        if (!Objects.isNull(p)) {
            List<ProMaterial> proMaterialList = procurementMaterService.getProMatersByProId(id);
            if (BooleanUtils.isFalse(noLoadApply)) {
                for (ProMaterial mp : proMaterialList) {
                    try {
                        PlanMaterial pm = planMaterialService.getMaterialByOk(mp.getPlanRowId());
                        if (!Objects.isNull(pm)) {
                            mp.setPlanSum(pm.getPlanSum());
                        }
                    } catch (NullPointerException e) {

                    }
                }
            }
            p.setMaterial(proMaterialList);
        }
        return new ResponseModel(p);
    }

    @Operation(description = "添加采购订单",summary = "采购订单添加时，可调用附件上传接口，将返回值mainId赋予采购订单id新增即可绑定附件")
    @PostMapping
    public ResponseModel addProcurement(@RequestBody ProcurementModel procurementModel,
                                        @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Procurement procurement = procurementModel.getProcurement();
        String[] applyy = procurementModel.getApplyList();
        String[] attachs = procurementModel.getAttachs();
        Map<String, JSONObject> updateMater = procurementModel.getUml();
        procurement.setStaff(staff);
        long timeout = System.currentTimeMillis();
        if (setMaterials(procurementModel, procurement)) return new ResponseModel(401, "未指定订单材料");
        System.out.println("材料设置耗时:" + (System.currentTimeMillis() - timeout));
        procurementService.addProcurement(procurement, applyy, updateMater);
        //添加附件
        addAttach(procurement, attachs);
        return new ResponseModel(procurementModel);
    }

    private boolean setMaterials(ProcurementModel procurementModel, Procurement procurement) {
        List<ProMaterial> proMaterials = new ArrayList<>();
        List<ProMaterialModel> models = procurementModel.getMaterialModel();
        if (Objects.isNull(models)) {
            return true;
        }
        models.forEach(item -> {
            ProMaterial material = new ProMaterial();
            BeanUtils.copyProperties(item, material);
//            if (item.isUpdate()) {
                Material temp = materialService.insert(procurement.getStaff(), item.getMaterial());
                if (!Objects.isNull(temp) && !StringUtils.equals(temp.getId(), item.getMaterial().getId())) {
                    material.setMaterial(temp);
                    procurementModel.setUpdate(true);
                }
//
//            }
            proMaterials.add(material);
        });
        procurement.setMaterial(proMaterials);
        return false;
    }

    @Operation(description = "设置订单为完全入库（消单功能）", summary = "操作成功返回订单id")
    @PutMapping("setPutAll/{id}")
    public ResponseModel setPutALl(@PathVariable String id) {
        procurementService.updatePutState((byte) 4, id);
        return new ResponseModel(id);
    }

    @Operation(description = "作废采购订单")
    @DeleteMapping("destroy/{proId}")
    public ResponseModel destroy(@Parameter(name="订单id") @PathVariable String proId,
                                 @Parameter(name="作废说明") String desc, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        /*Procurement p = procurementService.getProcurementById(proId);
        if (p == null) {
            return new ResponseModel(500, "采购订单不存在");
        }
        if (!p.getStaff().getId().equals(staff.getId())) {
            return new ResponseModel(500, "只能作废自己的单据");
        }
        p.setMaterial(procurementMaterService.getProMatersByProId(proId));
        if (procurementService.deleteProcurement(proId) == -1) {
            return new ResponseModel(500, "订单材料有入库记录，作废失败");
        }
        if (StringUtils.isNotBlank(desc)) {
            if (StringUtils.isBlank(p.getRemark())) {
                p.setRemark(desc);
            } else {
                p.setRemark(p.getRemark() + "," + desc);
            }
        }
        flowMessageService.deleteMessage(proId);
        procurementService.insertDiscard(p);
        proPutDetailService.deleteByProId(proId);*/
        return new ResponseModel("功能已被禁用");
    }

    @Operation(description = "更新采购订单")
    @PutMapping
    public ResponseModel updatePro(@RequestBody ProcurementModel model, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        long time = System.currentTimeMillis();

        //删除材料
        String[] delTemp = model.getDelMater();
        if(!Objects.isNull(delTemp) && delTemp.length > 0){
            for (int x = 0;x < delTemp.length;x++){
                String did = delTemp[x];
                if(StringUtils.isNotBlank(did)){
                    procurementMaterService.deletemater(did);
                }
            }
        }

        Procurement pro = model.getProcurement();
        pro.setStaff(staff);
        if (setMaterials(model, pro)) {
            return new ResponseModel("未指定订单材料");
        }
        Map<String, JSONObject> updateMater = model.getUml();
        procurementService.updatePro(pro, staff, updateMater);
        //添加附件
        addAttach(pro, model.getAttachs());
        try {
            JSONObject jo = new JSONObject();
            jo.put("type", "upp");
            MyWebSocketHandle.sendMsg(staff.getId(), jo.toJSONString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseModel(model);
    }

    @Operation(description = "更新订单状态")
    @PostMapping("updateState")
    public ResponseModel updateState(@RequestBody Procurement p) {
        return ResponseModel.ok(procurementService.updateState(p));
    }

    @Operation(description = "修改采购订单合同")
    @PutMapping("updateContract")
    public ResponseModel updateContract(@RequestBody ProcurementModel procurementModel,
                                        @SessionAttribute(Constant.SESSION_KEY)
                                        @Parameter(hidden = true) Staff staff) {
        Procurement procurement = procurementModel.getProcurement();
        procurement.setVoucherCoding(staff.getCoding());
        return new ResponseModel(procurementService.updateProContract(procurement, staff));
    }

    @Operation(description = "修改采购订单税率")
    @PutMapping("updateTax")
    public ResponseModel updateTax(@RequestBody ProcurementModel procurementModel) {
        String errormsg = "";
        if (procurementService.updateTax(procurementModel.getProcurement()) == -1) {
            errormsg = "税率修改失败";
        }
        return new ResponseModel(errormsg);
    }

    @Operation(description = "删除采购订单")
    @DeleteMapping("delete")
    public ResponseModel delete(String id) {
        if (procurementService.deleteProcurement(id) == -1) {
            return new ResponseModel(500, "该单据已审核或已入库，不能删除");
        }
        return new ResponseModel("删除成功");
    }

    @Operation(description = "获取采购订单审核盖章图片")
    @GetMapping("getApproveFlag/{id}")
    public ResponseModel getApproveFlag(@PathVariable @Parameter(name="订单id") String id,
                                        @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) throws IOException {
        Procurement procurement = procurementService.getProcurementById(id);
        List<ProDownloadHistory> pdh = proDownloadHistoryService.queryByProId(id);
        if (Objects.isNull(procurement)) {
            return new ResponseModel(200, "采购订单不存在");
        } else if (procurement.getState() != 1) {
            return new ResponseModel(200, "订单未审核");
        } else if (!procurement.getStaff().getId().equals(staff.getId())) {
            return new ResponseModel(200, "该单据非当前用户订单");
        } else {
            ProDownloadHistory h = null;
            if (!pdh.isEmpty()) {
                h = pdh.get(0);
                if (StringUtils.isBlank(h.getImg())) {
                    procurement.setMaterial(procurementMaterService.getProMatersByProId(id));
                    byte[] images = HtmlToImageUtil.convertProcurementToImage(procurement, 1836, 726);
                    h.setImg(new String(Base64.getEncoder().encode(images)));
                }
            } else {
                h = procurementService.genDownloadHistory(procurement);
            }
            return new ResponseModel(h.getImg());
        }
    }

    @Operation(description = "标记该订单为已下载盖章图片")
    @PutMapping("updateApproveFlag/{id}")
    public ResponseModel updateApproveFlag(@PathVariable @Parameter(name="采购订单id") String id,
                                           @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        List<ProDownloadHistory> histories = proDownloadHistoryService.queryByProId(id);
        if (histories.size() > 0) {
            ProDownloadHistory history = histories.get(0);
            history.setStaff(staff);
            history.setState((byte) 1);
            history.setDateTime(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL));
            return new ResponseModel(proDownloadHistoryService.update(history));
        }
        return new ResponseModel(500, "-1");
    }

    @Operation(description = "申请删除订单")
    @PutMapping("applyDelete")
    public ResponseModel applyDelete(@RequestBody ApplyDelete applyDelete) {
        if (Objects.isNull(applyDelete.getProId())) {
            return new ResponseModel("请指定采购订单id");
        } else if (StringUtils.isBlank(applyDelete.getRemark())) {
            return new ResponseModel("请填写申请理由");
        }
        applyDelete.setId(UUID.randomUUID().toString());
        applyDeleteService.applyDelete(applyDelete);
        return new ResponseModel("已发送申请，请等待领导同意");
    }

    /**
     * 获取所有审批删除的订单信息
     *
     * @return
     */
    @Operation(description = "获取所有审批删除的订单信息")
    @GetMapping("/getApplyDeleteAll")
    public ResponseModel getApplyDeleteAll() {
        List<ApplyDelete> adList = applyDeleteService.queryAll();
        for (ApplyDelete ad : adList) {
            try {
                Procurement p = procurementService.getProcurementById(ad.getProId());
                if (p != null) {
                    ad.setRemark(p.getPmNumber() + "-----" + ad.getRemark());
                    ad.setProcurement(p);
                }
            } catch (Exception ignored) {

            }
        }
        return new ResponseModel(adList);
    }

    @Operation(description = "查询采购订单是否申请过删除")
    @GetMapping("/getApplyDeleteByProId/{proId}")
    public ResponseModel getApplyDeleteByProId(@PathVariable String proId) {
        ApplyDelete ad = new ApplyDelete();
        ad.setProId(proId);
        ad = applyDeleteService.queryByParam(ad);
        return ResponseModel.ok(ad);
    }

    /**
     * 更新申请删除采购订单状态
     *
     * @param applyDelete 申请删除单据对象
     * @return
     */
    @Operation(description = "更新申请删除采购订单状态")
    @PostMapping("/updateApplyDeleteState")
    public ResponseModel updateApplyDeleteState(@RequestBody ApplyDelete applyDelete, HttpSession httpSession) {
        Staff staff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
        if (applyDelete == null || applyDelete.getProId() == null) {
            return new ResponseModel(500, "订单不存在");
        } else {
//            更新删除记录状态
            applyDeleteService.updateState(applyDelete);
//            反审核采购订单
            procurementService.approve(applyDelete.getProId(), 0, staff.getCoding(), DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            //作废单据
            flowMessageService.deleteMessage(applyDelete.getProId());
            Procurement p = procurementService.getProcurementById(applyDelete.getProId());
            p.setMaterial(procurementMaterService.getProMatersByProId(applyDelete.getProId()));
            if (procurementService.deleteProcurement(p.getId()) == -1) {
                return new ResponseModel(500, "操作失败");
            }
            procurementService.insertDiscard(p);
        }
        return new ResponseModel("操作成功");
    }

    /**
     * 取消申请删除
     *
     * @param applyDelete 申请删除单据对象
     * @return
     */
    @Operation(description = "取消订单删除申请")
    @PostMapping("/cancelApplyDeleteState")
    @ResponseBody
    public ResponseModel cancelApplyDeleteState(@RequestBody ApplyDelete applyDelete) {
        if (applyDelete == null || applyDelete.getProId() == null) {
            return new ResponseModel(500, "单据不存在");
        } else {
            applyDeleteService.delete(applyDelete.getProId());
            return new ResponseModel("操作成功");
        }
    }

    @Operation(description = "获取项目材料采购记录")
    @GetMapping("{projectId}/{materialId}")
    public ResponseModel listByProject(@PathVariable String projectId, @PathVariable String materialId) {
        List<Procurement> procurements = procurementService.getProByMaterial(materialId, projectId);
        // StringBuffer companyNames = new StringBuffer();
        // procurements.forEach(procurement -> {
        //     if (companyNames.indexOf(procurement.getCompany().getName()) == -1) {
        //         companyNames.append(procurement.getCompany().getName());
        //         companyNames.append(";");
        //     }
        // });
        return new ResponseModel(procurements);
    }

    @Operation(description = "查询申请单材料采购订单列表")
    @GetMapping("listByApplyMaterial/{applyMaterialId}")
    public ResponseModel listByApplyMaterial(@PathVariable String applyMaterialId) {
        List<ProMaterial> materials = procurementMaterService.getProMaterByApply(applyMaterialId);
        materials.forEach(item -> {
            item.setP(procurementService.getProcurementById(item.getProId()));
            if (!Objects.isNull(item.getMaterial())) {
                item.setMaterial(materialService.getMaterialByid(item.getMaterial().getId()));
            }
        });
        return ResponseModel.ok(materials);
    }


    private String getSortName(String sortName) {
        if (StringUtils.isNotBlank(sortName)) {
            switch (sortName) {
                case "staff.name":
                    sortName = "a.pm01310";
                    break;
                case "pmDate":
                    sortName = "a.pm01302";
                    break;
                case "pmNumber":
                    sortName = "a.pm01303";
                    break;
                case "voucherDate":
                    sortName = "a.pm01308";
                    break;
                case "state":
                    sortName = "a.pm01314";
                    break;
                case "company.name":
                    sortName = "a.pm01304";
                    break;
                default:
                    sortName = "pm01302";
            }
        } else {
            sortName = "pm01302";
        }
        return sortName;
    }

    private void addAttach(Procurement pro, String[] attachs) {
        //添加附件
        if (attachs != null && attachs.length > 0) {
//            attachService.deleteAttachByModuleId(pro.getId(), "sdpm013FJ");
            Map<String, String> ap = new HashMap<>(16);
            ap.put("table", "sdpm013FJ");
            //主键列
            ap.put("field", "pm013FJ01");
            //附件主表列
            ap.put("field2", "pm013FJ02");
            ap.put("moduleId", pro.getId());
            for (String id : attachs) {
                ap.put("id", id);
                List<Attach> attaches = attachService.getAttachByRelation(ap);
                if (attaches.isEmpty() && StringUtils.isNotBlank(id)) {
                    attachService.addAttachRelation(ap);
                }
            }
        }
    }

    @Operation(description = "获取订单集合")
    @GetMapping("getProByContract")
    public ResponseModel getProByContract(String contractId) {
        return new ResponseModel(procurementService.getProcurementByContract(contractId));
    }

    @Operation(description = "上传采购订单物流信息")
    @PutMapping("expressCode/{id}")
    @NoToken
    public ResponseModel expressCOde(@RequestBody Procurement procurement) {
        int row = procurementService.expressCode(procurement);
        if (row == -1) {
            return new ResponseModel(500, "快递单号无效");
        } else {
            return ResponseModel.ok(procurement);
        }
    }

    @Operation(description = "导出excel")
    @PutMapping("exportExcel")
    public ResponseModel exportExcel(@RequestBody Procurement procurement) {
        String fileName = procurement.getPmNumber() + "-采购清单.xlsx";
        String[] headers = new String[]{"Material.Name", "Material.Model", "Material.Unit.Name",
                "Material.Brand", "Sum", "InSum", "ApplyPrice",
                "PriceTax", "MoneyTax", "TaxMoney", "Remark"};
        fileName = ExcelParse.writeExcel(procurement.getMaterial(), fileName, headers, ProMaterial.class);
        return ResponseModel.ok(fileName);
    }

    @Operation(description = "修复采购订单采购状态")
    @PostMapping("updateProState/{id}")
    public ResponseModel updateProState(@PathVariable String id) {
        procurementService.rebackState(id);
        return ResponseModel.ok(procurementService.getProcurementById(id));
    }

}
