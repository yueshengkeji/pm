package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.BreakFlowModel;
import com.yuesheng.pm.model.FLowConsentModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.SelectApprove;
import com.yuesheng.pm.service.FlowApproveService;
import com.yuesheng.pm.service.FlowCourseService;
import com.yuesheng.pm.service.FlowMessageService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "我的审批消息")
@RestController
@RequestMapping("/api/myApprove")
public class MyApproveApi {
    @Autowired
    private FlowApproveService flowApproveService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private FlowCourseService flowCourseService;

    @Operation(description = "获取待审批消息集合")
    @GetMapping
    public ResponseModel getMyApproves(@Parameter(name="数据大小", required = true) Integer pageSize,
                                       @Parameter(name="数据索引位置", required = true) Integer pageNumber,
                                       @Parameter(name="检索字符串(可选)") String searchText,
                                       @Parameter(name="用户id", required = true) String userId) {
        String[] msgStatus = new String[]{"1"};
        String[] status = new String[]{"0", "1"};
        List<FlowApprove> approveList = flowApproveService.getMessages(userId, null, null, Arrays.asList(status), Arrays.asList(msgStatus), 1, searchText, pageNumber, pageSize, null);
        approveList.forEach(item -> {
            if (item.getLastCourse() == 0) {
                flowApproveService.setLastCourse(item);
            }
        });
        return new ResponseModel(approveList);
    }

    @Operation(description = "获取已审批消息集合(包含驳回的)")
    @GetMapping("consent")
    public ResponseModel getMyApprovesOk(@Parameter(name="数据大小", required = true) Integer pageSize,
                                         @Parameter(name="数据索引位置", required = true) Integer pageNumber,
                                         @Parameter(name="检索字符串(可选)") String searchText,
                                         @Parameter(name="用户id", required = true) String userId,
                                         @Parameter(name="分期支付标记,0=未分期支付，1=分期支付") String fqFlag) {
        String[] msgStatus = new String[]{"1", "2", "3"};
        String[] status = new String[]{"3", "4", "7"};
        HashMap<String, String> params = new HashMap<>();
        params.put("status", StringUtils.join(status, ","));
        params.put("msgStatus", StringUtils.join(msgStatus, ","));
        params.put("str", searchText);
        params.put("pageSize", pageSize + "");
        params.put("pageNumber", pageNumber + "");
        params.put("fqFlag", fqFlag);
        params.put("userId", userId);
        params.put("sort", "a.po00408 DESC");
        params.put("type", "1");
        if (StringUtils.isNotBlank(searchText)) {
            return new ResponseModel(flowApproveService.getMessagesHistory(params));
        } else {
            return new ResponseModel(flowApproveService.getMessagesHistoryNow(params));
        }
    }

    @Operation(description = "获取知会我的消息集合")
    @GetMapping("notifyMy")
    public ResponseModel getMyApprovesNotifyMy(@Parameter(name="数据大小", required = true) Integer pageSize,
                                               @Parameter(name="数据索引位置", required = true) Integer pageNumber,
                                               @Parameter(name="检索字符串(可选)") String searchText,
                                               @Parameter(name="用户id", required = true) String userId,
                                               @Parameter(name="5=未读,6=已读") String[] status,
                                               @Parameter(name = "分期支付标记,0=未分期支付，1=分期支付") String fqFlag) {
        String[] msgStatus = new String[]{"1", "2"};
        if (Objects.isNull(status) || status.length == 0) {
            status = new String[]{"5", "6"};
        }
        return new ResponseModel(flowApproveService.getMessages(userId, null, null, Arrays.asList(status), Arrays.asList(msgStatus), 1, searchText,pageNumber, pageSize, fqFlag));
    }

    @Operation(description = "获取知会我的消息未读总数")
    @GetMapping("notifyMyNoReadCount")
    public ResponseModel notifyMyNoReadCount(@SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("staffId", staff.getId());
        param.put("msgState", "1,2");
        param.put("status", "5");
        return ResponseModel.ok(flowApproveService.notifyMyCount(param));
    }

    @Operation(description = "获取我知会的消息集合")
    @GetMapping("iNotify")
    public ResponseModel getMyApprovesINotify(@Parameter(name="数据大小", required = true) Integer pageSize,
                                              @Parameter(name="数据索引位置", required = true) Integer pageNumber,
                                              @Parameter(name="检索字符串(可选)") String searchText,
                                              @Parameter(name="用户id", required = true) String userId,
                                              @Parameter(name="分期支付标记,0=未分期支付，1=分期支付") String fqFlag) {
        String[] msgStatus = new String[]{"1", "2"};
        String[] status = new String[]{"5", "6"};
        return new ResponseModel(flowApproveService.getMessages(userId, null, null, Arrays.asList(status), Arrays.asList(msgStatus), 2, searchText, pageNumber, pageSize, fqFlag));
    }

    /**
     * 审批通过
     *
     * @param approve 审批流程消息对象
     * @param fqFlag  是否分期支付同意，1=是，0=否
     * @return {flow_success='exists'(该流程已经同意过，不能重复操作),consent='ok'(审批通过操作成功),'pubPerson'=FlowCourse对象（下一个步骤为自由选人，提示当前审批人选择下一步骤审批人）}
     */
    @Operation(description = "审批通过")
    @PostMapping("/consent/{fqFlag}")
    public ResponseModel consent(@RequestBody FlowApprove approve, @PathVariable String fqFlag) {
        if (StringUtils.isBlank(approve.getId())) {
            return new ResponseModel(401, "通过步骤id未指定");
        }
        if (StringUtils.isBlank(approve.getContent())) {
            approve.setContent("同意");
        }
        //更新【我的审批】状态为同意
        FLowConsentModel result = flowApproveService.consentApprove(approve);
        if (result.getState() == 1) {
            if ("1".equals(fqFlag)) {
                //更新审批标签
                flowApproveService.updateFlag(approve.getId(), fqFlag);
                flowMessageService.updateFlag(approve.getMessage().getId(), fqFlag);
            }
        }
        return new ResponseModel(result);
    }

    @Operation(description = "自由选人审批通过")
    @PostMapping("/freeConsent")
    public ResponseModel publicConsent(@RequestBody SelectApprove selectApprove, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        return new ResponseModel(flowApproveService.publicApprove(selectApprove, staff));
    }

    @Operation(description = "追加审批意见")
    @PostMapping("/appendConsent")
    public ResponseModel appendConsent(@RequestBody FlowApprove approve) {
        if (approve == null) {
            return new ResponseModel(401, "步骤id未指定");
        }
        if (StringUtils.isBlank(approve.getId())) {
            return new ResponseModel(401, "步骤id未指定");
        }
        if (StringUtils.isBlank(approve.getContent())) {
            approve.setContent("同意");
        }
        Map<String, Object> result = flowApproveService.appendApprove(approve);
        result.put("consent", "ok");
        return ResponseModel.ok(result);
    }

    @Operation(description = "知会人员")
    @PostMapping("/notify/{flowApproveId}")
    public ResponseModel notify(@Parameter(name="职员列表") @RequestBody String[] staffList, @Parameter(name="流程审批消息id") @PathVariable String flowApproveId) {
        flowApproveService.inFromUser(staffList, flowApproveId);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "中断流程")
    @PostMapping("/breakFlow")
    public ResponseModel breakFlow(@RequestBody FlowApprove fa) {
        return ResponseModel.ok(flowApproveService.breakFlow(fa));
    }
    @Operation(description = "驳回到指定节点")
    @PostMapping("/breakToCourse")
    public ResponseModel breakFlow(@RequestBody BreakFlowModel model) {
        FlowApprove fa = model.getApprove();
        FlowCourse link = model.getLink();
        if(Objects.isNull(link)) {
            return ResponseModel.ok(flowApproveService.breakFlow(fa));
        }else{
            //中断到指定节点
            return ResponseModel.ok(flowApproveService.breakFLow(fa,link));
        }
    }

    @Operation(description = "撤回审批")
    @PostMapping(value = "/recall")
    public ResponseModel recall(@RequestBody FlowApprove approve) {
        FlowApprove temp = flowApproveService.isApprove(approve);
        //判断该过程是否同意过，如果为null则没有同意过
        if (temp == null) {
            return new ResponseModel(500, "指定的审批消息不存在！");
        }
        FlowMessage msg = flowMessageService.getMessageById(approve.getFlowMessageId());
        if (msg == null) {
            return new ResponseModel(500, "指定的撤回消息主体不存在");
        }
        int row = flowApproveService.recall(approve, msg);
        if(row == -1){
            return new ResponseModel(500,"流程已全部审批完成，不能撤回");
        } else if(row == -2){
            return new ResponseModel(500,"下一步已经有人审批，不能撤回");
        }
//        temp.setApproveDate("");
//        temp.setApproveState(1);
//        temp.setContent("");
//        flowApproveService.updateFlowApproveStatus(temp);
        flowApproveService.updateFlag(approve.getId(), "");
        //判断之前是否还有分批付款标签，如果没有则更新主单据,标签取消
        if (!flowApproveService.isFlag(msg.getId(), "1")) {
            flowMessageService.updateFlag(msg.getId(), "");
        }
        return new ResponseModel(row);
    }

    @Operation(description = "设置审批/知会消息为已读")
    @PostMapping("read")
    public ResponseModel read(@RequestBody FlowApprove approve) {
        String state = flowApproveService.getApproveState(approve.getId());
        if ((StringUtils.equals("5",state) || StringUtils.equals("0",state))
                && (approve.getApproveState() == 1 || approve.getApproveState() == 6)) {
            flowApproveService.updateState(approve.getApproveState(), approve.getId());
            return new ResponseModel(200, approve.getId(), "操作成功");
        }
        return new ResponseModel("操作失败，更新状态只能是：0|1|5|6");
    }

    @Operation(description = "标记所有未读消息为已读")
    @PostMapping("readAll")
    public ResponseModel readAll(@SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        return ResponseModel.ok(flowApproveService.updateStateAll(staff.getId()));
    }

    @Operation(description = "加签审批人")
    @PostMapping("appendApprove/{approveId}")
    public ResponseModel appendApprove(@Parameter(name="审批消息id") @PathVariable String approveId,
                                       @RequestBody CoursePerson cp) {

        if (Objects.isNull(cp) || Objects.isNull(cp.getStaff()) || cp.getStaff().isEmpty()) {
            return ResponseModel.error("请指定审批人");
        }

        FlowApprove approve = flowApproveService.getFlowApproveById(approveId);

        if (approve == null) {
            return new ResponseModel(500, "步骤不存在");
        }

        cp.getStaff().forEach(item -> {
            FlowApprove fa = new FlowApprove();
            BeanUtils.copyProperties(approve, fa);
            fa.setId(UUID.randomUUID().toString());
            fa.setAccrptDate(DateFormat.getDateTime());
            fa.setReadDate("");
            fa.setApproveDate("");
            fa.setAcceptUser(item);
            fa.setApproveState(0);
            fa.setSendUser(approve.getAcceptUser());
            fa.setStaffId(approve.getAcceptStaffId());
            fa.setAcceptStaffId(item.getId());
            fa.setContent("");
            fa.setPo00421(approve.getAcceptUser().getName() + "邀请加签");
            flowApproveService.addApprove(fa);
        });

        FlowMessage fm = approve.getMessage();
        if (Objects.isNull(fm)) {
            fm = flowMessageService.getMessageById(approve.getFlowMessageId());
        }

        FlowCourse mfc = flowCourseService.getThanFlowCourseBByCourseId(approve.getCourseId(), fm.getHistroryId());
        if (!Objects.isNull(mfc) && mfc.getPo02005() == 0) {
            mfc.setPo02005((byte) 1);
            //非会签，更新为会签
            flowCourseService.updateThanFlowCourseBByCourseId(mfc);
        }

        return ResponseModel.ok();
    }
}
