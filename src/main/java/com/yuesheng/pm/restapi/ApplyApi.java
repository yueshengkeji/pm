package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Tag(name = "申请单主体")
@RestController
@RequestMapping("api/apply")
public class ApplyApi extends BaseApi {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private PlanMaterialService planMaterialService;
    @Autowired
    private ProjectService projectService;


    @Operation(description = "获取申请单列表",  summary = "返回值中total属性为数据总数，rows属性为数据集合")
    @GetMapping
    public ResponseModel getApplyList(@Parameter(name = "检索字符串") String str,
                                      @Parameter(name = "数据大小", required = true) Integer itemsPerPage,
                                      @Parameter(name = "数据当前索引位置", required = true) Integer page,
                                      @Parameter(name = "排序名称(默认按照审批时间排序)，参考Apply对象属性") String sortName,
                                      @Parameter(name ="排序类型：asc(升序)/desc(降序)") String sortOrder,
                                      @Parameter(name ="单据审核状态，1=已审核，0=未审核，'0,1'=所有单据)", required = true) String approves,
                                      @Parameter(name ="加载当前职员信息）") String staffId,
                                      @Parameter(name ="可选（指定职员coding）") String staffCoding,
                                      @Parameter(name ="采购状态，0=完全未采购，1=部分未采购,2=完全采购,可组合传递：'0,1'", required = true) String proStatus,
                                      @Parameter(name ="是否加载未采购的材料列表") Boolean isNoProMater,
                                      @Parameter(name ="是否加载等待通知的单据") Boolean isNotify,
                                      @Parameter(name ="项目id") String projectId,
                                      @SessionAttribute(Constant.SESSION_KEY) Staff staff) throws UnsupportedEncodingException {
        HashMap<String, Object> params = new HashMap();
        List<ProjectAuth> projectList = projectService.getProjectAuthByStaff(staff.getId());
        if(!projectList.isEmpty()){
            params.put("projectAuth",staff.getId());
        }
        sortName = getSortName(sortName, sortOrder);
        params.put("order", MaterialController.isSort(sortName, StringUtils.isBlank(sortOrder) ? "DESC" : sortOrder));
        params.put("approve", approves);
        params.put("state", proStatus);
        params.put("projectId", projectId);
        params.put("staffId", StringUtils.isBlank(staffId) ? null : staffId);
        params.put("str", StringUtils.isBlank(str) ? null : str);
        if (!Objects.isNull(params.get("staffId"))) {
            params.put("staffId", staff.getCoding());
        } else if (StringUtils.isNotBlank(staffCoding)) {
            params.put("staffId", staffCoding);
        }
        if (BooleanUtils.isTrue(isNotify)) {
            params.put("notifyStaffCoding", staff.getCoding());
        }
        startPage(page, itemsPerPage, sortName, sortOrder);
        Page<Apply> applyList = (Page<Apply>) applyService.getApplyListV2(params);
        //移除空项目数据
        for (int i = 0; i < applyList.size(); i++) {
            if (applyList.get(i).getProject() == null) {
                applyList.remove(i);
            }
        }
        int count = (int) applyList.getTotal();
        params.clear();
        params.put("rows", applyList);
        params.put("total", count);

        /*applyList.forEach(apply -> {
            Staff staff1 = apply.getStaff();
            staff1.setHead(proStaffHwService.queryHeadByStaffId(staff1.getId()));
        });*/
        if (BooleanUtils.isTrue(isNoProMater)) {
            for (Apply a : applyList) {
                a.setApplyMaterialList(applyMaterialService.getApplyMaterials(a.getId()));
            }
        } else {      //获取所有材料
            /*for (Apply a : applyList) {
                a.setApplyMaterialList(applyMaterialService.getMaterAllByApply(a.getId()));
            }*/
        }
        return new ResponseModel(params);
    }

    @Operation(description = "添加申请单")
    @PostMapping
    public ResponseModel addApply(@RequestBody Apply apply, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        if (applyService.verifySeries(apply.getSerialNumber()) != null) {
            return new ResponseModel(500, "申请单名称已存在");
        }
        apply.setStaff(staff);
        applyService.addApply(apply);
//        添加申请单材料
        if (apply.getId() != null && apply.getApplyMaterialList() != null) {
            for (ApplyMaterial material : apply.getApplyMaterialList()) {
                if (material != null) {
                    material.setApplyid(apply.getId());
//                    材料行主键
                    material.setMajor(UUID.randomUUID().toString());
//                    添加到数据库
                    applyMaterialService.addMater(material);
                }
            }
        }
        return new ResponseModel(apply);
    }

    @Operation(description = "删除申请单")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        Apply apply = applyService.getApplyById(id);
        //未采购才可删除
        if (apply != null && apply.getState() == 0) {
            //不是自己的单据，不能删除
            if (!apply.getStaff().getId().equals(staff.getId())) {
                return new ResponseModel(500, "不能删别人的单据!");
            }
            //已审核，反审核后再删除
            if (apply.getAudit() == 1) {
                applyService.approve(id, 0, staff.getCoding(), DateFormat.getDate());
            }
            //删除流程
            flowMessageService.deleteMessage(id);
            //删除单据
            applyService.delete(id);
            return new ResponseModel(200, "操作成功!");
        }
//        已采购，不能删除
        return new ResponseModel(500, "已经采购或单据不存在，删除失败");
    }

    @Operation(description = "获取采购申请单明细")
    @GetMapping("getById/{id}")
    public ResponseModel getById(@PathVariable String id, Boolean notPro) {
        Apply apply = applyService.getApplyById(id);
        if (!Objects.isNull(apply)) {
            if (notPro != null && notPro) {
                apply.setApplyMaterialList(applyMaterialService.getApplyMaterials(id));
            } else {
                apply.setApplyMaterialList(applyMaterialService.getMaterAllByApply(id));
            }
            apply.getApplyMaterialList().forEach(applyMaterial -> {
                if (StringUtils.isNotBlank(applyMaterial.getPlanRowId())) {
                    PlanMaterial pm = planMaterialService.getMaterialByOk(applyMaterial.getPlanRowId());
                    if (!Objects.isNull(pm)) {
                        applyMaterial.setCnfParam(pm.getCnfStr());
                        applyMaterial.setPlanSum(pm.getPlanSum());
                        applyMaterial.setApplySum(pm.getApplySum());
                    }
                }
            });
        }
        return new ResponseModel(apply);
    }

    @Operation(description = "修改申请单")
    @PutMapping
    public ResponseModel update(@RequestBody Apply apply, @SessionAttribute(Constant.SESSION_KEY) @Parameter(hidden = true) Staff staff) {
        //删除单据
        ResponseModel result = delete(apply.getId(), staff);
        //不能修改其他人的单据
        if (result.getCode() == 500) {
            apply.setId(null);
            return ResponseModel.error("操作失败:" + result.getMsg());
        } else {
            //重新添加单据
            return addApply(apply, staff);
        }
    }

    @Operation(description = "更新申请单提醒日期")
    @PutMapping("updateNotify/{id}")
    public ResponseModel updateNotify(@PathVariable String id,
                                      String notifyDate,
                                      @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        applyService.updateNotify(id, notifyDate, staff.getCoding());
        return ResponseModel.ok();
    }

    @Operation(description = "更新申请单状态：0=未采购，1=部分采购，2=完全采购")
    @PutMapping("updateState/{id}")
    public ResponseModel updateProState(@PathVariable String id, Integer state) {
        applyService.updateState(id, state);
        return ResponseModel.ok();
    }

    @Operation(description = "查询材料申请记录")
    @GetMapping("getApplyByMater")
    public ResponseModel getApplyByMater(String materId, String projectId) {
        return ResponseModel.ok(applyMaterialService.getMaterByProid(materId, projectId));
    }

    @Operation(description = "验证申请单是否存在")
    @GetMapping("seriesNumber")
    public ResponseModel seriesNumber(String seriesNumber) {
        return ResponseModel.ok(applyService.getBySeries(seriesNumber));
    }

    private String getSortName(String sortName, String order) {
        if (StringUtils.isNotBlank(sortName)) {
            switch (sortName) {
                case "projectid":
                    sortName = "pm03402";
                    break;
                case "prepareDate":
                    sortName = "pm03406";
                    break;
                case "approveDate":
                    sortName = "pm03415";
                    break;
                case "serialNumber":
                    sortName = "pm03403";
                    break;
                case "pm03416":
                    sortName = "pm03416";
                    break;
                case "date":
                    sortName = "pm03414";
                    break;
                default:
                    sortName = "pm03416";
                    break;
            }
        } else {
            sortName = "pm03416";
        }
        return sortName;
    }
}
