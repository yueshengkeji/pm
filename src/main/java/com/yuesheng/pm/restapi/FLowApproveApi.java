package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.FlowApprove;
import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FlowApproveService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.StaffService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;

@Tag(name = "审批中过程实例接口")
@RestController
@RequestMapping("api/flowApprove")
public class FLowApproveApi {
    @Autowired
    private FlowApproveService flowApproveService;
    @Autowired
    private FlowMessageService messageService;
    @Autowired
    private StaffService staffService;

    @Operation(description = "获取审批步骤实例列表（记录表）")
    @GetMapping("record")
    public ResponseModel getCourseRecordList(@Parameter(description = "流程实例id") String flowMessageId) {
        return new ResponseModel(flowApproveService.getFlowApproveRecordByMessageId(flowMessageId));
    }
    @Operation(description = "获取审批步骤实例列表")
    @GetMapping("")
    public ResponseModel getCourseList(@Parameter(description = "流程实例id") String flowMessageId) {
        return new ResponseModel(flowApproveService.getFlowApproveByMessageId(flowMessageId));
    }

    @Operation(description = "获取审批步骤实例")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        FlowApprove fa = flowApproveService.getFlowApproveById(id);
        if (!Objects.isNull(fa)) {
            FlowMessage fm = messageService.getMessageById(fa.getFlowMessageId());
            if (!Objects.isNull(fm) && Objects.isNull(fm.getStaff())) {
                fm.setStaff(staffService.getStaffById(fm.getStaffId()));
            }
            fa.setMessage(fm);
        }
        return ResponseModel.ok(fa);
    }

    @Operation(description = "获取所有审批数据")
    @GetMapping("list")
    public ResponsePage listAll(String start,
                                String end,
                                Integer[] status,
                                Integer[] msgState,
                                Integer type,
                                String userId,
                                Integer pageSize,
                                Integer pageNumber,
                                String searchText,
                                String flowFilter) {

        flowFilter = "".equals(flowFilter) ? null : flowFilter;
        if ("".equals(start)) {
            start = null;
            end = null;
        }
        if (type == null) {
            type = 1;
        }
        if (pageNumber == null) {
            pageNumber = 1;
            pageSize = 10;
        }

        Page<FlowApprove> approves = (Page<FlowApprove>) flowApproveService.getMessagesAll2(userId,
                start, end, status, msgState, type, searchText, flowFilter, pageNumber, pageSize);
        return ResponsePage.ok(approves);
    }
}
