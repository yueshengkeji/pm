package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.FlowCourse;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.CourseJudeService;
import com.yuesheng.pm.service.CoursePersonService;
import com.yuesheng.pm.service.FlowCourseRelationService;
import com.yuesheng.pm.service.FlowCourseService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "流程过程管理")
@RestController
@RequestMapping("api/course")
public class FlowCourseApi extends BaseApi {
    @Autowired
    private FlowCourseService flowCourseService;
    @Autowired
    private FlowCourseRelationService flowCourseRelationService;
    @Autowired
    private CoursePersonService coursePersonService;
    @Autowired
    private CourseJudeService judeService;

    @Operation(description = "通过流程id获取步骤列表")
    @GetMapping("getByFlowId/{flowId}")
    public ResponseModel getCourseListByFlow(@PathVariable String flowId,
                                             @SessionAttribute(Constant.SESSION_KEY) Staff s) {
        List<FlowCourse> courseList = flowCourseService.getFlowCourseByFlow(flowId);
        for (FlowCourse fc : courseList) {
            fc.setFcr(flowCourseRelationService.getParent(fc.getId()));
            fc.setPersonList(coursePersonService.getPersonByCourseId(fc.getId(), s));
            fc.setJudgeList(judeService.getByCourse(fc.getId()));
        }
        return new ResponseModel(courseList);
    }

    @Operation(description = "通过id获取步骤对象")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(flowCourseService.getFlowCourseById(id));
    }

    @Operation(description = "修改步骤")
    @PostMapping
    public ResponseModel update(@RequestBody FlowCourse course) {
        flowCourseService.update(course);
        return new ResponseModel(course);
    }

    @Operation(description = "添加步骤")
    @PutMapping
    public ResponseModel insert(@RequestBody FlowCourse course) {
        flowCourseService.insert(course);
        return new ResponseModel(course);
    }

    @Operation(description = "删除步骤")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        try {
            flowCourseService.delete(id);
        } catch (Exception e) {
            return new ResponseModel(500, e, e.getMessage());
        }
        return new ResponseModel("操作成功");
    }

    @Operation(description = "获取判断条件列表")
    @GetMapping("judge/{courseId}")
    public ResponseModel getJudge(@PathVariable String courseId) {
        return new ResponseModel(judeService.getByCourse(courseId));
    }

    @Operation(description = "添加过程条件判断")
    @PutMapping("insertJudge")
    public ResponseModel insertJudge(@RequestBody FlowCourse course) {
        flowCourseService.insertJudge(course);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "修改过程条件判断")
    @PostMapping("updateJudge")
    public ResponseModel updateJudge(@RequestBody FlowCourse course) {
        flowCourseService.updateJudge(course);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "删除过程条件")
    @DeleteMapping("deleteJudge/{id}")
    public ResponseModel deleteJudge(@PathVariable String id) {
        judeService.deleteById(id);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "删除审批对象")
    @DeleteMapping("deletePerson/{id}")
    public ResponseModel deletePerson(@PathVariable String id) {
        coursePersonService.delete(id);
        return new ResponseModel("操作成功");
    }
}



