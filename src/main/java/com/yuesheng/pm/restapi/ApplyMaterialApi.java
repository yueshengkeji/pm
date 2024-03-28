package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ApplyMaterial;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.ApplyMaterialService;
import com.yuesheng.pm.service.ApplyService;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Tag(name = "申请单材料明细接口")
@RestController
@RequestMapping("api/applyMaterial")
public class ApplyMaterialApi {
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private RedisService redisService;

    @Operation(description = "获取申请单中所有材料列表", summary = "返回指定申请单中的材料列表")
    @GetMapping
    public ResponseModel getByApplyId(@Parameter(name = "申请单主体id") String applyId) {
        return new ResponseModel(applyMaterialService.getMaterAllByApply(applyId));
    }

    @Operation(description = "获取申请单中未采购的材料列表", summary = "返回指定申请单中的未采购的材料列表")
    @GetMapping("noProcurement")
    public ResponseModel getNoProByApplyId(@Parameter(name = "申请单主体id") String applyId) {
        return new ResponseModel(applyMaterialService.getApplyMaterials(applyId));
    }

    @Operation(description = "获取申请单主体信息")
    @GetMapping("getApply/{id}")
    public ResponseModel getApplyById(@PathVariable @Parameter(name = "申请单明细行id") String id) {
        ApplyMaterial applyMaterial = applyMaterialService.getMaterById(id);
        if (Objects.isNull(applyMaterial)) {
            return new ResponseModel(null);
        } else {
            return new ResponseModel(applyService.getApplyById(applyMaterial.getApplyid()));
        }
    }

    /**
     * 更新材料行备注信息
     *
     * @param applyMaterial
     * @return
     */
    @Operation(description = "update apply mater row 'remark' info")
    @PostMapping("/updateApplyRemark")
    public ResponseModel updateApplyRemark(@RequestBody ApplyMaterial applyMaterial,
                                           @SessionAttribute(Constant.SESSION_KEY) Staff s) {
        if (StringUtils.isBlank(applyMaterial.getRemark())) {
            return new ResponseModel(0);
        }
        // ApplyMaterial material = applyMaterialService.getMaterById(applyMaterial.getId());
        // int row = 0;
        // if (StringUtils.isBlank(material.getRemark())) {
        //     row = applyMaterialService.updateRemark(applyMaterial.getId(), s.getName() + " : " + applyMaterial.getRemark());
        // } else {
        //     row = applyMaterialService.updateRemark(applyMaterial.getId(), material.getRemark() + ";" + s.getName() + " : " + applyMaterial.getRemark());
        // }
        // return new ResponseModel(row);
        return new ResponseModel(applyMaterialService.updateRemark(applyMaterial.getId(), applyMaterial.getRemark()));
    }

    @Operation(description = "查询申请单材料是否在采购编辑")
    @GetMapping("isProEdit/{major}")
    public ResponseModel isProEdit(@PathVariable String major) {
        return ResponseModel.ok(redisService.getValue(Constant.PRO_EDIT_TEMP + major));
    }

    @Operation(description = "记录申请单材料正在采购编辑,5分钟过期")
    @PostMapping("insertProEdit/{major}")
    public ResponseModel insertProEdit(@PathVariable String major, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        redisService.setKey(Constant.PRO_EDIT_TEMP + major, staff.getName() + "正在采购编辑...");
        redisService.expireKey(Constant.PRO_EDIT_TEMP + major, 5, TimeUnit.MINUTES);
        return ResponseModel.ok(major);
    }

    @Operation(description = "查询申请单明细列表")
    @GetMapping("listByPlanRowId/{planRowId}")
    public ResponseModel listByPlanRowId(@PathVariable String planRowId) {
        List<ApplyMaterial> amList = applyMaterialService.listByPlanRowId(planRowId);
        amList.forEach(item -> {
            item.setApply(applyService.getApplyById(item.getApplyid()));
        });
        return ResponseModel.ok(amList);
    }

}
