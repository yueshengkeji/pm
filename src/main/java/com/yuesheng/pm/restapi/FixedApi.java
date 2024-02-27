package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.FixedApply;
import com.yuesheng.pm.entity.FixedAssets;
import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FixedApplyService;
import com.yuesheng.pm.service.FixedAssetsService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Tag(name = "固定资产申请表")
@RestController
@RequestMapping("api/fixedApply")
public class FixedApi extends BaseApi {

    @Autowired
    private FixedApplyService fixedApplyService;

    @Autowired
    private FixedAssetsService assetsService;

    @Operation(description = "新增固定资产采购申请")
    @PutMapping
    public ResponseModel insert(@RequestBody FixedApply fixed, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        fixed.setStaff(staff);
        fixed.setSection(staff.getSection());
        fixedApplyService.insert(fixed);
        return ResponseModel.ok(fixed);
    }

    @Operation(description = "修改固定资产采购申请")
    @PostMapping
    public ResponseModel update(@RequestBody FixedApply fixed, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        fixed.setStaff(staff);
        fixed.setSection(staff.getSection());
        fixedApplyService.update(fixed);
        return ResponseModel.ok(fixed);
    }

    @Operation(description = "删除固定资产采购申请")
    @DeleteMapping
    public ResponseModel delete(String id) {
        return ResponseModel.ok(fixedApplyService.deleteById(id));
    }

    @Operation(description = "查询固定资产申请")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        FixedApply fixedApply = fixedApplyService.queryById(id);
        if (Objects.isNull(fixedApply)) {
            return ResponseModel.ok();
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("fixedId", id);
        fixedApply.setAssetsList(assetsService.queryByParam(param));
        return ResponseModel.ok(fixedApply);
    }

    @Operation(description = "审核固定资产申请单")
    @PostMapping("approve/{id}/{approveStaffCoding}")
    @NoToken
    public ResponseModel approve(@PathVariable String id, @PathVariable String approveStaffCoding) {
        fixedApplyService.approve(id, approveStaffCoding);
        return ResponseModel.ok(id);
    }

    @Operation(description = "查询固定资产申请单列表")
    @GetMapping("list")
    public ResponsePage list(Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             String sortDesc,
                             String title,
                             String sectionId,
                             String staffId) {
        FixedApply apply = new FixedApply();
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            apply.setStaff(staff);
        }
        if (StringUtils.isNotBlank(sectionId)) {
            Section section = new Section();
            section.setId(sectionId);
            apply.setSection(section);
        }
        apply.setTitle(title);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<FixedApply> list = (Page<FixedApply>) fixedApplyService.queryByParam(apply);
        return ResponsePage.ok(list);
    }

    @Operation(description = "查询固定资产明细列表")
    @GetMapping("detailList")
    public ResponsePage detailList(Integer page,
                                   Integer itemsPerPage,
                                   String sortBy,
                                   String sortDesc,
                                   String searchText,
                                   String section,
                                   String person) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("str", searchText);
        param.put("person", person);
        param.put("section", section);
        param.put("state", "1");
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<FixedAssets> assets = (Page<FixedAssets>) assetsService.queryByParam(param);
        return ResponsePage.ok(assets);
    }

    @Operation(description = "查询固定资产明细")
    @GetMapping("detailById/{id}")
    public ResponseModel detailById(@PathVariable String id) {
        return ResponseModel.ok(assetsService.queryById(id));
    }

    @Operation(description = "批量添加固定资产明细")
    @PutMapping("list")
    public ResponseModel addList(@RequestBody FixedAssets[] fixedAssets) {
        if (fixedAssets != null) {
            for (int i = 0; i < fixedAssets.length; i++) {
                FixedAssets fa = fixedAssets[i];
                if (!Objects.isNull(fa)) {
                    fa.setId(UUID.randomUUID().toString());
                    fa.setState((byte) 1);
                    assetsService.insert(fa);
                }
            }
        }
        return ResponseModel.ok();
    }

    @Operation(description = "删除固定资产明细")
    @DeleteMapping("deleteDetail/{id}")
    public ResponseModel deleteDetail(@PathVariable String id) {
        return ResponseModel.ok(assetsService.delete(id));
    }
}
