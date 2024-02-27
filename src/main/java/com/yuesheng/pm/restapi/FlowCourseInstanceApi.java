package com.yuesheng.pm.restapi;

import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.FlowCourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/flowCourseInstance")
public class FlowCourseInstanceApi extends BaseApi{
    @Autowired
    private FlowCourseService flowCourseService;

    @Operation(description = "获取流程实例过程列表")
    @GetMapping("{flowMessageId}/{flowHistoryId}")
    public ResponseModel list(@PathVariable String flowMessageId,@PathVariable String flowHistoryId){
        return ResponseModel.ok(flowCourseService.getInstanceByFlowId(flowMessageId,flowHistoryId));
    }
}
