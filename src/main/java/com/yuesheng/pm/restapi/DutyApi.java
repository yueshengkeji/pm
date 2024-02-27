package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Duty;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.StaffDutyBandModel;
import com.yuesheng.pm.service.DutyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "职务管理")
@RequestMapping("/api/duty")
@RestController
public class DutyApi {
    @Autowired
    private DutyService dutyService;

    @Operation(description = "检索职务信息")
    @GetMapping
    public ResponseModel getDuty(@Parameter(name= "搜索字符串，可选") String str) {
        List<Duty> dutyList = dutyService.getBySeek(StringUtils.isBlank(str) ? "" : str);
        setChildren(dutyList);
        return new ResponseModel(dutyList);
    }

    @Operation(description = "获取所有职务")
    @GetMapping("list")
    public ResponseModel list() {
        List<Duty> dutyList = dutyService.getByParent("");
        setChildren(dutyList);
        return new ResponseModel(dutyList);
    }

    public void setChildren(List<Duty> dutyList) {
        dutyList.forEach(item -> {
            //获取该职务内人员总数
            Integer count = dutyService.getStaffCountByDuty(item.getId());
            item.setCount(count);
            List<Duty> children = dutyService.getByParent(item.getId());
            setChildren(children);
            item.setChildren(children);
        });
    }

    @Operation(description = "添加职务")
    @PutMapping
    public ResponseModel insertDuty(@RequestBody Duty duty) {
        try {
            dutyService.insert(duty);
        } catch (Exception e) {
            return new ResponseModel(500, e.getMessage());
        }
        return new ResponseModel(200, "添加成功");
    }

    @Operation(description = "修改职务")
    @PostMapping
    public ResponseModel updateDuty(@RequestBody Duty duty) {
        try {
            dutyService.update(duty);
        } catch (Exception e) {
            new ResponseModel(500, e.getMessage());
        }
        return new ResponseModel(200, "添加成功");
    }

    @Operation(description = "添加指定人员到指定职务中")
    @PostMapping("/insertStaffToDuty")
    public ResponseModel insertPersonToDuty(@RequestBody @Parameter(name="职员与职务绑定关系模型") StaffDutyBandModel staffDutyBandModel) {
        Map<String, String> param = new HashMap<>();
        param.put("staffId", staffDutyBandModel.getStaffId());
        param.put("dutyId", staffDutyBandModel.getDutyId());
        try {
            dutyService.insertPerson(param);
        } catch (SQLException throwables) {
            new ResponseModel(500, throwables.getMessage());
        }
        return new ResponseModel("添加成功");
    }

    @Operation(description = "将职员从职务中删除")
    @DeleteMapping("/deletePerson")
    public ResponseModel deletePerson(String staffId, String dutyId) {
        return new ResponseModel(200, dutyService.deletePerson(dutyId, staffId) + "");
    }

    @Operation(description = "获取职务内所有职员信息")
    @GetMapping("/getStaffByDutyId")
    public ResponseModel getStaffByDutyId(@Parameter(name="职务id") String dutyId) {
        return new ResponseModel(dutyService.getStaffByDuty(dutyId));
    }

    @Operation(description = "删除职务")
    @DeleteMapping
    public ResponseModel delete(String id) {
        return new ResponseModel(dutyService.delete(id));
    }

    @Operation(description = "获取职务")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id){
        return ResponseModel.ok(dutyService.getById(id));
    }
}
