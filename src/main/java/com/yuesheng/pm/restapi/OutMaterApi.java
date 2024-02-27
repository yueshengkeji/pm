package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.OutMaterChildService;
import com.yuesheng.pm.service.OutMaterService;
import com.yuesheng.pm.service.ProPutSignService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "出库管理")
@RestController
@RequestMapping("api/outMater")
public class OutMaterApi extends BaseApi {
    @Autowired
    private OutMaterService outMaterService;
    @Autowired
    private OutMaterChildService outMaterChildService;

    @Autowired
    private ProPutSignService proPutSignService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Operation(description = "获取出库单列表")
    @GetMapping("list")
    public ResponsePage list(String start,
                             String end,
                             String searchText,
                             Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             Boolean sortDesc,
                             String storage,
                             String staff,
                             String type) {
        Map<String, Object> params = new HashMap(16);
        params.put("start", "".equals(start) ? null : start);
        params.put("end", "".equals(end) ? null : end);
        params.put("str", "".equals(searchText) ? null : searchText);
        params.put("storage", "".equals(storage) ? null : storage);
        params.put("type", type);
        params.put("staff", "".equals(staff) ? null : staff);
        sortBy = getSortName(sortBy);
        if (StringUtils.isBlank(sortBy)) {
            startPage(page, itemsPerPage, new String[]{"a.pm02002", "a.pm02003"},
                    new Boolean[]{true, false}, false);
        } else {
            startPage(page, itemsPerPage, sortBy, sortDesc, false);
        }
        Page<MaterOut> maters = (Page<MaterOut>) outMaterService.getOutMaterList(params);
        return ResponsePage.ok(maters);
    }


    @GetMapping("outCount")
    public ResponseModel outCount(String start,
                                  String end,
                                  String searchText,
                                  String storage,
                                  String staff,
                                  String type) {
        Map<String, Object> params = new HashMap(16);
        params.put("start", "".equals(start) ? null : start);
        params.put("end", "".equals(end) ? null : end);
        params.put("str", "".equals(searchText) ? null : searchText);
        params.put("storage", "".equals(storage) ? null : storage);
        params.put("type", type);
        params.put("staff", "".equals(staff) ? null : staff);
        return ResponseModel.ok(outMaterService.getOutSumByParam(params));
    }


    @Operation(description = "获取最近出库单对象（30天内）")
    @GetMapping("recentlyOut")
    public ResponseModel recentlyOut(@SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        String date = DateFormat.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateFormat.parseData(date));
        MaterOut materOut = outMaterService.getNowOutMater(staff.getCoding(), date);
        int i = 0;
        while (materOut == null) {
            materOut = outMaterService.getNowOutMater(staff.getCoding(), date);
            if (i == 30) {
                break;
            }
            i++;
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
            date = DateFormat.parseString(calendar.getTime());
        }
        return ResponseModel.ok(materOut);
    }

    @Operation(description = "根据项目查询出库单列表")
    @GetMapping("listByProject")
    public ResponseModel listByProject(String searchStr, String projectId, Integer page, Integer itemsPerPage, String sortName, String sortOrder) {
        sortName = getSortName(sortName);
        if (StringUtils.isBlank(sortName)) {
            sortName = "pm02003";
            sortOrder = "desc";
        }
        if (Objects.isNull(page)) {
            page = 1;
            itemsPerPage = 10;
        }
        startPage(page, itemsPerPage, getSortName(sortName), sortOrder);
        return ResponseModel.ok(outMaterService.getOutMaterByProjectId(projectId, searchStr));
    }

    private MaterOut insertSign(MaterOut materOut) {

        //登记待签字信息
        ProPutSign sign = new ProPutSign();
        sign.setId(UUID.randomUUID().toString());
        sign.setPutDate(DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        sign.setPutobj("");
        sign.setSignImg("");
        sign.setStaffId(materOut.getStaff().getId());
        sign.setSignStaffId(materOut.getOutPerson().getId());
        sign.setType(0);
        sign.setProId(materOut.getId());
        sign.setPutId(materOut.getPutId());
        sign.setPastDate(DateUtil.format(DateUtil.rollDay(new Date(), 3), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        proPutSignService.insert(sign);

        //通知签字人员
        List<Staff> staffSign = new ArrayList<>();
        staffSign.add(materOut.getOutPerson());
        HashMap<String, Object> param = new HashMap<>();
        param.put("title", "材料出库签单");
        param.put("mTitle", "等待" + materOut.getOutPerson().getName() + "签字确认");
        param.put("content", "发送人：" + materOut.getStaff().getName());
        param.put("url", WebParam.VUETIFY_BASE + "/storage/out/signOut/" + sign.getId());
        flowNotifyService.msgNotify(staffSign, param);
        return materOut;
    }

    @Operation(description = "添加出库单")
    @PutMapping("{isApprove}/{sign}")
    public ResponseModel insert(@RequestBody MaterOut materOut,
                                @Parameter(name="1=自动审核，0=不自动审核") @PathVariable int isApprove,
                                @Parameter(name="是否签字确认审核") @PathVariable String sign,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        if (StringUtils.equals(sign, "sign")) {
            outMaterService.addOutMater(materOut, staff, 0);
            insertSign(materOut);
        } else {
            outMaterService.addOutMater(materOut, staff, isApprove);
        }
        return ResponseModel.ok(materOut);
    }

    @Operation(description = "获取出库单材料列表")
    @GetMapping("outMaterList/{outId}")
    public ResponseModel outMaterList(@PathVariable String outId) {
        return ResponseModel.ok(outMaterChildService.getOutMatersByOutId(outId));
    }

    @Operation(description = "审核/反审核出库单")
    @PostMapping("approve")
    public ResponseModel approve(@RequestBody MaterOut out, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        try {
            outMaterService.approve(out, staff);
        } catch (Exception e) {
            return ResponseModel.error(e.getMessage());
        }
        return ResponseModel.ok(out);
    }

    @Operation(description = "删除出库单")
    @DeleteMapping("{id}")
    public ResponseModel delete(@PathVariable String id) {
        outMaterService.deleteOut(id);
        return ResponseModel.ok(id);
    }

    @Operation(description = "删除出库材料")
    @DeleteMapping("deleteMater/{materId}")
    public ResponseModel deleteMater(@PathVariable String materId) {
        outMaterChildService.deleteOutMater(materId);
        return ResponseModel.ok(materId);
    }

    @Operation(description = "通过出库单编号获取出库单")
    @GetMapping("getOutByNumber/{outNumber}")
    public ResponseModel getOutByNumber(@PathVariable String outNumber) {
        return ResponseModel.ok(outMaterService.getOutMaterByNumber(outNumber));
    }

    @Operation(description = "通过出库单编号获取出库单")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(outMaterService.getOutMaterById(id));
    }

    @Operation(description = "查询材料最后一次出库记录")
    @GetMapping("getLastOutMater/{materId}")
    public ResponseModel getLastOutMater(@PathVariable String materId) {
        MaterOutChild moc = outMaterChildService.getLastOutMater(materId);
        if (!Objects.isNull(moc)) {
            moc.setMaterOut(outMaterService.getOutMaterById(moc.getMaterOutId()));
        }
        return ResponseModel.ok(moc);
    }

    @Operation(description = "查询出库金额合计")
    @GetMapping("getOutMaterHistoryMoney")
    public ResponseModel getOutMaterHistoryMoney(String name,
                                                 String model,
                                                 String brand,
                                                 String startDate,
                                                 String[] projectsId,
                                                 String endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("model", model);
        String p = MaterialApi.getProjectsId(projectsId);
        param.put("projectId", p);
        param.put("brand", brand);
        if (StringUtils.isNotBlank(startDate)) {
            param.put("startDate", startDate);
            param.put("endDate", endDate);
        }
        return ResponseModel.ok(outMaterChildService.getOutMaterHistoryMoney(param));
    }

    @Operation(description = "查询出库单总数")
    @GetMapping("getTotal")
    public ResponseModel getTotal(String start, String end, Integer type, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Map<String, Object> params = new HashMap(16);
        params.put("start", start);
        params.put("end", end);
        params.put("type", type);
        params.put("staffCoding", staff.getCoding());
        int count = outMaterService.getOutSum(params);
        return ResponseModel.ok(count);
    }

    private String getSortName(String sortBy) {
        if (StringUtils.isBlank(sortBy)) {
            return null;
        }
        switch (sortBy) {
            case "project.name":
                return "pm02004";
            case "outNumber":
                return "pm02003";
            case "outDate":
                return "pm02002";
            case "outPerson.name":
                return "pm02021";
            case "company.name":
                return "pm02008";
            case "state":
                return "pm02019";
            default:
                return null;
        }
    }
}
