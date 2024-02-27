package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.Flow;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.FlowService;
import com.yuesheng.pm.service.MenuService;
import com.yuesheng.pm.service.WordModuleService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Tag(name = "老系统基础流程数据api")
@RestController
@RequestMapping("/api/usedFlow")
public class FlowApi extends BaseApi {

    @Autowired
    private FlowService flowService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private WordModuleService wordModuleService;

    @Operation(description = "根据窗口编号获取可用流程列表")
    @GetMapping("getByCoding")
    public ResponseModel getByCoding(String coding) {
        return ResponseModel.ok(flowService.getFlowByFrameCoding(coding));
    }

    @Operation(description = "通过流程名称检索")
    @GetMapping("getByName")
    public ResponseModel seekFlow(String name) {
        return ResponseModel.ok(flowService.getFLowByName(name));
    }

    @Operation(description = "获取流程目录")
    @GetMapping("getFolder")
    public ResponseModel getFolder(String parent) {
        return new ResponseModel(flowService.getRootFolder(parent));
    }

    @Operation(description = "获取所有流程目录")
    @GetMapping("getFolderAll")
    public ResponseModel getFolderAll() {
        return new ResponseModel(flowService.getFolderAll());
    }

    @Operation(description = "获取所有流程")
    @GetMapping
    public ResponseModel getFLow(String name,
                                 Integer page,
                                 Integer itemsPerPage,
                                 String[] sortBy,
                                 Boolean[] sortDesc) {
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<Flow> page1 = (Page<Flow>) flowService.getAllFLow(name);
        page1.forEach(flow -> {
            flow.setMenu(menuService.getMenuByCoding(flow.getFrameCoding()));
            flow.setWordModule(wordModuleService.queryById(flow.getTemplement()));
            flow.setFolderObj(flowService.getFolderById(flow.getFolder()));
        });
        HashMap<String, Object> result = new HashMap<>();
        result.put("total", page1.getTotal());
        result.put("rows", page1);
        return new ResponseModel(result);
    }

    @Operation(description = "修改流程")
    @PostMapping
    public ResponseModel update(@RequestBody Flow flow) {
        flowService.update(flow);
        return new ResponseModel(1);
    }

    @Operation(description = "添加流程")
    @PutMapping
    public ResponseModel insert(@RequestBody Flow flow, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        flowService.insert(flow, staff);
        return new ResponseModel(flow);
    }

    @Operation(description = "删除流程")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        flowService.delete(id);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "通过办文目录id查询流程列表")
    @GetMapping("getFlowByModule/{id}")
    public ResponseModel getFlowByModule(@PathVariable String id) {
        return ResponseModel.ok(flowService.getFlowByAF(id));
    }
}
