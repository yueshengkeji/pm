package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.OutCarHistory;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.OutCarHistoryGroup;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.OutCarHistoryService;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Tag(name = "用车登记")
@RestController
@RequestMapping("api/outCarHistory")
public class OutCarHistoryApi extends BaseApi {
    @Autowired
    private OutCarHistoryService outCarHistoryService;

    @Autowired
    private FlowNotifyService notifyService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Operation(description = "根据id查询数据")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(outCarHistoryService.queryById(id));
    }

    @Operation(description = "登记用车申请")
    @PutMapping
    public ResponseModel insert(@RequestBody OutCarHistory history, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        history.setStaff(staff);
        if (Objects.isNull(history.getSection()) || StringUtils.isBlank(history.getSection().getId())) {
            history.setSection(staff.getSection());
        }
        outCarHistoryService.insert(history);
        List<Staff> staffs = new ArrayList<>();
        staffs.add(staff);
        HashMap<String, Object> np = new HashMap<>();
        np.put("title", "用车登记");
        np.put("mTitle", "到达地点后，请点击该通知，点击到达并拍照记录");
        np.put("content", "一路顺风");
        np.put("url", WebParam.VUETIFY_BASE + "/wxLocation/updateLocation/" + history.getId());
        notifyService.msgNotify(staffs, np);
        imgCompress(history.getStartImg());
        return ResponseModel.ok(history);
    }

    @Operation(description = "用车报销补登")
    @PostMapping("/extraInsert")
    public ResponseModel extraInsert(@RequestBody OutCarHistory history){
        return ResponseModel.ok(outCarHistoryService.insert(history));
    }

    private void imgCompress(String imgPath) {
        if(imgPath.length() > 45){
            taskExecutor.execute(() -> {
                byte[] bytes = NetRequestUtil.sendGetRequest("http://192.168.3.253:8086/1001/accessories/" + imgPath.replace("\\","/"));
                ImageCompress ic = ImageCompress.instanceImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ic.compressMaxPic(baos, bytes);
                try {
                    FtpUtil.uploadFile(baos.toByteArray(), imgPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, 1500);
        }
    }

    @Operation(description = "修改用车登记信息")
    @PostMapping("{reAdd}")
    public ResponseModel update(@PathVariable String reAdd, @RequestBody OutCarHistory history, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (StringUtils.equals(history.getStaff().getId(), staff.getId())) {
            history.setEndTime(DateUtil.getDatetime());

            int house = DateUtil.getOffsetHours(DateUtil.parse(history.getStartTime()), DateUtil.parse(history.getEndTime()));
            if (house > 24) {
                OutCarHistory och = new OutCarHistory();
                och.setId(history.getId());
                och.setEndImg(history.getEndImg());
                outCarHistoryService.update(och);
                imgCompress(history.getEndImg());
                return ResponseModel.error("出发时间到结束时间不能超过24小时，特殊情况请联系技术/部门经理说明情况，后台处理");
            }
            if (history.getIsParkingCost() == 1) {
                List<Staff> staffs = new ArrayList<>();
                staffs.add(staff);
                HashMap<String, Object> np = new HashMap<>();
                np.put("title", "用车到达");
                np.put("mTitle", "本次用车产生停车费，请您支付停车费后上传付款截图凭证，否则将影响到您报销");
                np.put("content", "出门在外，注意安全，点击本通知上传付款截图凭证");
                np.put("url", WebParam.VUETIFY_BASE + "/wxLocation/updateParkingCost/" + history.getId());
                notifyService.msgNotify(staffs, np);
            }

            outCarHistoryService.update(history);
            imgCompress(history.getEndImg());

            if (StringUtils.equals(reAdd, "reAdd")) {
                //根据此次结束里程，再次添加出发里程记录
                OutCarHistory newOutCarHistory = new OutCarHistory();
                newOutCarHistory.setProject(history.getProject());
                newOutCarHistory.setStartImg(history.getEndImg());
                newOutCarHistory.setStartLatitude(history.getEndLatitude());
                newOutCarHistory.setStartLongitude(history.getEndLongitude());
                newOutCarHistory.setStartAddrName(history.getEndAddrName());
                newOutCarHistory.setRemark(history.getRemark());
                insert(newOutCarHistory, staff);
            }
            return ResponseModel.ok(history);
        } else {
            return ResponseModel.error("只能修改自己的数据");
        }
    }

    @Operation(description = "上传停车费凭证")
    @PostMapping("updateParkingCost")
    public ResponseModel updateParkingCost(@RequestBody OutCarHistory u, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
//        String filePath = parseFile(u.getParkingCostImg(), staff);
//        if(StringUtils.isNotBlank(filePath)) {
//            u.setParkingCostImg(filePath);
//        }
        outCarHistoryService.update(u);
        return ResponseModel.ok("success");
    }

    @Operation(description = "查询记录列表")
    @GetMapping("list")
    public ResponsePage list(String staffId,
                             String startDate,
                             String endDate,
                             String sortBy,
                             String sortDesc,
                             Integer page,
                             Integer itemsPerPage,
                             Boolean expenseFlag) {
        OutCarHistory q = new OutCarHistory();
        q.setExpenseFlag(expenseFlag);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            q.setStaff(staff);
        }
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            q.setStartTime(startDate);
            q.setEndTime(endDate);
        }
        startPage(page, itemsPerPage, sortBy, sortDesc);
        Page<OutCarHistory> historyPage = (Page<OutCarHistory>) outCarHistoryService.queryAll(q);
        return ResponsePage.ok(historyPage);
    }


    @Operation(description = "查询记录列表(项目分组)")
    @GetMapping("listGroupProject")
    public ResponseModel listGroupProject(String staffId,
                                          String startDate,
                                          String endDate,
                                          String sortBy,
                                          String sortDesc,
                                          Integer page,
                                          Integer itemsPerPage) {
        OutCarHistory q = new OutCarHistory();
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            q.setStaff(staff);
        }
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            q.setStartTime(startDate);
            q.setEndTime(endDate);
        }
        List<OutCarHistoryGroup> outCarHistoryGroups = outCarHistoryService.queryGroupProject(q);
        outCarHistoryGroups.forEach(item -> {
            if (Objects.isNull(item.getProject()) || StringUtils.isBlank(item.getProject().getId())) {
                item.setOutCarHistoryList(outCarHistoryService.queryByNoProject(q));
            } else {
                q.setProject(item.getProject());
                item.setOutCarHistoryList(outCarHistoryService.queryAll(q));
            }
        });
        return ResponseModel.ok(outCarHistoryGroups);
    }

    @Operation(description = "获取当前用户最后一条数据")
    @GetMapping("getLastData")
    public ResponseModel getLastData(@SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        OutCarHistory query = new OutCarHistory();
        query.setStaff(staff);
        startPage(1, 1, "datetime", "desc");
        List<OutCarHistory> history = outCarHistoryService.queryAll(query);
        if (!history.isEmpty()) {
            return ResponseModel.ok(history.get(0));
        }
        return ResponseModel.ok();
    }

    @Operation(description = "补充事由")
    @PostMapping("updateRemark")
    public ResponseModel updateRemark(@RequestBody OutCarHistory history) {
        OutCarHistory o = new OutCarHistory();
        o.setId(history.getId());
        o.setRemark(history.getRemark());
        outCarHistoryService.update(o);
        return ResponseModel.ok(history);
    }

    @Operation(description = "压缩里程图片")
    @PostMapping("compress/{id}")
    public ResponseModel compress(@PathVariable String id){
        OutCarHistory och = outCarHistoryService.queryById(id);
        if(!Objects.isNull(och)){
            imgCompress(och.getStartImg());
            imgCompress(och.getEndImg());
        }
        return ResponseModel.ok();
    }
}
