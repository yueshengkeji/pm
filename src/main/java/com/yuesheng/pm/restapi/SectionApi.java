package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.SectionService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(name = "部门管理")
@RestController
@RequestMapping("/api/section")
public class SectionApi {
    @Autowired
    private SectionService sectionService;

    @Autowired
    private StaffService staffService;

    @Operation(description = "通过部门编码获取部门对象")
    @GetMapping("/coding/{coding}")
    public ResponseModel getSection(@Parameter(name="部门编码") @PathVariable String coding) {
        return new ResponseModel(sectionService.getSection(coding));
    }

    @GetMapping
    @Operation(description = "模糊查询部门列表")
    public ResponseModel seekSection(@Parameter(name="搜索字符串") String str) {
        return new ResponseModel(sectionService.getSectionList(StringUtils.isBlank(str) ? "" : str));
    }

    @GetMapping("list")
    @Operation(description = "获取所有部门")
    public ResponseModel list() {
        List<Section> sectionList = sectionService.getSectionList("");
        sectionList.forEach(section -> {
            Staff staff = staffService.getStaffById(section.getManagerid());
            Staff staff2 = staffService.getStaffById(section.getAssistManager());
            if (!Objects.isNull(staff) && staff.getIsLogin() == 0) {
                section.setManagerName(staff.getName());
            }
            if(!Objects.isNull(staff2)){
                section.setAssistManagerName(staff2.getName());
            }
            /*List<Staff> staffList = sectionService.getStaffList(section.getId());
            StringBuilder userNames = new StringBuilder();
            staffList.forEach(user -> {
                if (!Objects.isNull(user)) {
                    userNames.append(user.getName() + ";");
                }
            });
            if (userNames.length() > 0) {
                userNames.delete(userNames.length() - 1, userNames.length());
            }
            section.setUserNames(userNames.toString());*/
            section.setParent(sectionService.getSection(section.getParentid()));
        });
        return new ResponseModel(sectionList);
    }

    @GetMapping("treeList")
    @Operation(description = "获取所有部门树节点")
    public ResponseModel treeList() {
        return ResponseModel.ok(sectionService.getSectionByParent("", true));
    }

    @Operation(description = "通过部门管理员id查询部门列表")
    @GetMapping("/manager/{managerId}")
    public ResponseModel getByManager(@Parameter(name="管理员id") @PathVariable String managerId) {
        return new ResponseModel(sectionService.getSectionByManagerId(managerId));
    }

    @Operation(description = "获取直接子部门集合，不包含孙子辈嵌套部门")
    @GetMapping("/parent/{id}")
    public ResponseModel getByParent(@Parameter(name="部门id") @PathVariable String id) {
        return new ResponseModel(sectionService.getSectionByParent(id));
    }

    @Operation(description = "获取部门内所有职员列表")
    @GetMapping("/getStaffBySectionId")
    public ResponseModel getStaffBySectionId(@Parameter(name="部门id") String sectionId) {
        List<Staff> staffList = sectionService.getStaffList(sectionId);

        if (staffList == null || staffList.size() < 1 || staffList.get(0) == null) {
            return new ResponseModel(null);
        }
        return new ResponseModel(staffList);
    }

    @PutMapping
    @Operation(description = "添加部门")
    public ResponseModel insert(@RequestBody Section section) {
        sectionService.insert(section);
        return new ResponseModel(section);
    }

    @PostMapping
    @Operation(description = "修改部门")
    public ResponseModel update(@RequestBody Section section) {
        sectionService.update(section);
        return new ResponseModel(section);
    }

    @GetMapping("isLeader")
    @Operation(description = "判断当前用户是否为部门领导")
    public ResponseModel isLeader(@SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Section section = staff.getSection();
        if (section.getManagerid().equals(staff.getId())) {
            return new ResponseModel(section);
        } else {
            return new ResponseModel(false);
        }
    }
}
