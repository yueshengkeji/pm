package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.FLowMessageQuery;
import com.yuesheng.pm.model.FlowMessageModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FlowApproveService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.Constant;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Tag(name = "流程实例接口")
@RestController
@RequestMapping("/api/usedFlowInstance")
public class FlowMessageApi {
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private FlowApproveService approveService;

    @Autowired
    private StaffService staffService;


    @Operation(description = "发起流程实例")
    @PutMapping("startFlow")
    public ResponseModel startFlow(@RequestBody FlowMessageModel model, @Parameter(hidden = true) @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        FlowMessage message = model.getMessage();
        if (StringUtils.isBlank(message.getFrameColumn())) {
            message.setFrameColumn(getFrameColumn(message.getFrameCoding()));
        }
        FlowCourse course = model.getFlowCourse();
        Flow flow = model.getFlow();
        Map<String, Object> result = flowMessageService.startFlow(message, course, flow, staff);

//        executorService.schedule(() -> {
//            try {
//                flowMessageService.flowNotify(message.getId(), message);
//            } catch (Exception e) {
//                //ignore this error
//            }
//        }, 5000, TimeUnit.MILLISECONDS);

        return ResponseModel.ok(result);
    }

    @Operation(description = "根据表单对象id获取流程实例")
    @GetMapping("getByFrameId/{frameId}")
    public ResponseModel getByFrameId(@Parameter(name = "表单对象id") @PathVariable String frameId,
                                      @Parameter(name = "表单编码") String frameCoding) {
        FlowMessage message;
        if(StringUtils.isNotBlank(frameCoding)){
            message = flowMessageService.getMessage(frameId,frameCoding);
        }else{
            message = flowMessageService.getMessageByFrameId(frameId);
        }
        if (!Objects.isNull(message)) {
            message.setStaff(staffService.getStaffById(message.getStaffId()));
        }
        return new ResponseModel(message);
    }

    @Operation(description = "取消流程审批")
    @PostMapping("backFLowMessage/{id}")
    public ResponseModel backFLowMessage(@PathVariable String id) {
        FlowMessage message = flowMessageService.getMessageById(id);
        if (!Objects.isNull(message)) {
            List<FlowApprove> approveList = approveService.getFlowApproveByMessageId(message.getId());
            approveList.forEach(approve -> {
                if (approve.getApproveState() == 0 || approve.getApproveState() == 1) {
                    approve.setApproveState(2);
                    approveService.updateState(2, approve.getId());
                }
            });
            flowMessageService.updateMsgStatus(id, 4);
        }
        return new ResponseModel("操作成功");
    }

    @Operation(description = "更新审批流程状态")
    @PostMapping("updateState")
    public ResponseModel updateState(@RequestBody FlowMessage message) {
        flowMessageService.updateMsgStatus(message.getId(), message.getState());
        return ResponseModel.ok(message);
    }

    @Operation(description = "获取流程消息列表")
    @GetMapping("list")
    public ResponseModel list(FLowMessageQuery message) {
        PageHelper.startPage(message.getPageNumber(), message.getPageSize());
        List<FlowMessage> messages = flowMessageService.getMsgList(message);
        messages.forEach(msg -> {
            if (msg != null) {
                msg.setStaff(staffService.getStaffById(msg.getStaffId()));
                msg.setApproveList(approveService.getFlowApproveByMessageId(msg.getId()));
                flowMessageService.isError(msg);
            }
        });
        return new ResponseModel(messages);
    }

    @Operation(description = "删除流程实例")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        flowMessageService.deleteMessageById(id);
        return ResponseModel.ok(id);
    }

    @Operation(description = "获取当前用户发起的审批实例列表")
    @GetMapping("myList")
    public ResponsePage myList(String type, String str, Integer pageSize, Integer pageNumber, String sortName, String sortOrder, HttpSession session) {
        HashMap<String, Object> params = new HashMap(16);
        params.put("order", MaterialController.isSort(sortName, sortOrder));
        params.put("str", StringUtils.isBlank(str) ? null : str);
        params.put("pageNumber", pageNumber);
        params.put("pageSize", pageSize);
        params.put("type", type);
        params.put("staffId", ((Staff) session.getAttribute(Constant.SESSION_KEY)).getId());
        Page<FlowMessage> messages = (Page<FlowMessage>) flowMessageService.getMessageByStaff(params);
        return ResponsePage.ok(messages);
    }

    @Operation(description = "获取表单当前流程状态")
    @GetMapping("getFlowStateByFrame/{frameId}")
    public ResponseModel getFlowStateByFrame(@PathVariable String frameId) {
        HashMap<String, String> result = new HashMap<>();
        result.put("frameId", frameId);
        result.put("state", flowMessageService.getFlowMsgStateStr(frameId));
        return ResponseModel.ok(result);
    }

    @Operation(description = "再次推送审批消息到当前审批人")
    @PostMapping("reNotify")
    public ResponseModel reNotify(@RequestBody FlowMessage flowMessage){

        List<FlowApprove> fa = approveService.getFlowApproveByMessageId(flowMessage.getId());

        for (int i = 0; i < fa.size(); i++) {
            FlowApprove n = fa.get(i);
            if(!Objects.isNull(n) && n.getApproveState() <= 1){
                approveService.deleteNotifyHistory(n.getId());
            }
        }

        return ResponseModel.ok();
    }

    private String getFrameColumn(String coding) {
        switch (coding) {
            case "15306":
                return "pm01301";
            case "10563":
                return "pd06401";
            case "10564":
                return "pd00401";
            case "13270":
                return "pm07101";
            case "15304":
                return "pm03401";
            case "137214":
                return "po05701";
            default:
                return "";
        }
    }
}
