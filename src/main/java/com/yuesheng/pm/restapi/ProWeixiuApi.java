package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ProWeixiuGroup;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("api/weixiuApi")
public class ProWeixiuApi extends BaseApi {

    @Autowired
    private ProWeixiuService weixiuService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SystemConfigService configService;

    @Autowired
    private ProWeixiuPersonService weixiuPersonService;

    @Autowired
    private StaffService staffService;

    @Operation(description = "外部人员报修登记")
    @PutMapping("ext")
    @NoToken
    public ResponseModel insert(@RequestBody ProWeixiu weixiu) {
        weixiu.setId(UUID.randomUUID().toString());
        weixiu.setDatetime(DateUtil.format(new Date()));
        if(!Objects.isNull(weixiu.getProject())){
            weixiu.setProjectId(weixiu.getProject().getId());
            weixiu.setProjectName(weixiu.getProject().getName());
        }
        weixiu = weixiuService.insert(weixiu);

        sendNotify(weixiu);

        return new ResponseModel(weixiu);
    }

    private void sendNotify(ProWeixiu wx) {
        Map<String, Object> param = new HashMap<>();
        param.put("title", wx.getProject().getName() + "-报修单");
        param.put("mTitle", "报修人：" + wx.getName() + ",手机号" + wx.getTel());
        param.put("content", wx.getTitle());
        param.put("url", WebParam.VUETIFY_BASE+"/projectAfter/insertExtDetail/" + wx.getId());
        ProWeixiuPerson pwp = weixiuPersonService.getByProjectName(wx.getProjectName());
        if (Objects.isNull(pwp) || StringUtils.isBlank(pwp.getStaffId())) {
            //未找到负责人，通知所有报修角色
            SystemConfig sc = configService.queryByCoding(Constant.WEIXIU_ROLE_CODING);
            if (!Objects.isNull(sc) && StringUtils.isNotBlank(sc.getValue())) {
                List<Staff> staffList = roleService.getStaffByRoleCoding(sc.getValue());
                flowNotifyService.msgNotify(staffList, param);
            }
        } else {
            //找到负责人，通知该用户，并通知系统配置的其他人员
            SystemConfig sc = configService.queryByCoding(Constant.WEIXIU_ROLE_CODING_OTHER);
            List<Staff> staffList = new ArrayList<>();
            if (!Objects.isNull(sc) && StringUtils.isNotBlank(sc.getValue())) {
                staffList.addAll(roleService.getStaffByRoleCoding(sc.getValue()));
            }
            String[] staffId = pwp.getStaffId().split(";");
            for (String si : staffId) {
                Staff s = staffService.getStaffById(si);
                if (!Objects.isNull(s)) {
                    staffList.add(s);
                }
            }
            flowNotifyService.msgNotify(staffList, param);
        }
    }

    @Operation(description = "添加维修数据")
    @PutMapping
    public ResponseModel insert(@RequestBody ProWeixiu weixiu, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        weixiu.setStaffId(staff.getId());
        weixiu.setId(UUID.randomUUID().toString());
        weixiu.setDatetime(DateUtil.format(new Date()));
        weixiu.setProjectId(weixiu.getProject().getId());
        weixiu.setProjectName(weixiu.getProject().getName());
        return new ResponseModel(weixiuService.insert(weixiu));
    }

    @Operation(description = "反馈维修结果")
    @PostMapping
    public ResponseModel update(@RequestBody ProWeixiu weixiu,
                                @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        weixiu.setProjectId(weixiu.getProject().getId());
        weixiu.setProjectName(weixiu.getProject().getName());
        weixiu.setReturnTime(DateUtil.getDatetime());
        weixiu.setReturnStaff(staff);
        return new ResponseModel(weixiuService.update(weixiu));
    }

    @Operation(description = "获取维修数据")
    @GetMapping("list")
    public ResponseModel selectList(String searchText,
                                    Integer itemsPerPage,
                                    Integer page,
                                    String startDate,
                                    String endDate,
                                    Boolean noReturn) {

        Map<String, Object> result = new HashMap<>(4);
        result.put("searchText", searchText);
        result.put("noReturn", noReturn);
        if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
            result.put("startDate", startDate + " 00:00:00");
            result.put("endDate", endDate + " 23:59:59");
        }

        startPage(page, itemsPerPage, "datetime", "desc");
        Page<ProWeixiu> weixiuList = (Page<ProWeixiu>) weixiuService.queryByParam(result);
        result.clear();
        result.put("rows", weixiuList);
        result.put("total", weixiuList.getTotal());
        return new ResponseModel(result);
    }


    @Operation(description = "导出维修数据")
    @GetMapping("exportList")
    public ResponseModel exportList(String searchText,
                                    String startDate,
                                    String endDate,
                                    Boolean noReturn) {

        Map<String, Object> result = new HashMap<>(4);
        result.put("searchText", searchText);
        result.put("noReturn", noReturn);
        if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
            result.put("startDate", startDate + " 00:00:00");
            result.put("endDate", endDate + " 23:59:59");
        }

        startPage(1, 2000, "datetime", "desc");
        Page<ProWeixiu> weixiuList = (Page<ProWeixiu>) weixiuService.queryByParam(result);
        String fileName = "报修记录.xlsx";
        fileName = ExcelParse.writeExcel(weixiuList, fileName, new String[]{
                "ProjectName", "Title", "Datetime", "Staff.Name", "Tel", "ReturnContent", "ReturnStaffName", "ReturnTime"
        }, ProWeixiu.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "获取维修统计数据")
    @GetMapping("getWeiXiuCount")
    public ResponseModel getCount() {
        HashMap<String, Object> result = new HashMap<>();
        Date date = new Date();

        //今天的总数
        String startDate = DateUtil.format(date, DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 00:00:00";
        String endDate = DateUtil.format(date, DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";
        Integer count = weixiuService.queryWeiXiuCountByDate(startDate, endDate);
        result.put("dayCount", count);

        //昨天的总数
        startDate = DateUtil.format(DateUtil.rollDay(date, -1), DateUtil.PATTERN_CLASSICAL_SIMPLE + " 00:00:00");
        endDate = DateUtil.format(DateUtil.rollDay(date, -1), DateUtil.PATTERN_CLASSICAL_SIMPLE + " 23:59:59");
        count = weixiuService.queryWeiXiuCountByDate(startDate, endDate);
        result.put("prevDayCount", count);

        //本月总数
        startDate = DateUtil.format(DateUtil.getMonthStartTime(), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 00:00:00";
        endDate = DateUtil.format(DateUtil.getMonthEndTime(), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";
        count = weixiuService.queryWeiXiuCountByDate(startDate, endDate);
        result.put("monthCount", count);

        //上月总数
        startDate = DateUtil.format(DateUtil.getLastMonthStartTime(), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 00:00:00";
        endDate = DateUtil.format(DateUtil.getLastMonthEndTime(), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";
        count = weixiuService.queryWeiXiuCountByDate(startDate, endDate);
        result.put("prevMonthCount", count);

        //本年总数
        startDate = DateUtil.getYearFirstDay() + " 00:00:00";
        endDate = DateUtil.format(date, DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";
        count = weixiuService.queryWeiXiuCountByDate(startDate, endDate);
        result.put("yearCount", count);

        //查询未反馈信息
        List<ProWeixiu> weixius = weixiuService.selectNoReturn();
        result.put("noReturn", weixius);
        result.put("noReturnCount", weixiuService.selectNoReturnCount());

        return new ResponseModel(result);
    }

    @Operation(description = "通过项目分组维修数据")
    @GetMapping("getByProjectGroup")
    public ResponseModel getByProjectGroup(String startDate,
                                           String endDate) {
        if (StringUtils.isBlank(startDate)) {
            Date date = DateUtil.rollDay(DateUtil.getNowDate(), -30);
            startDate = DateUtil.format(date, DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 00:00:00";
            endDate = DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";
        } else {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
        }
        startPage(1, 500, "wx_count", "desc");
        Page<ProWeixiuGroup> weixiuGroups = (Page<ProWeixiuGroup>) weixiuService.queryByProjectGroup(startDate, endDate);
        // ArrayList<ProWeixiuGroup>
        weixiuGroups.forEach(item -> {
            if (StringUtils.isNotBlank(item.getProjectName())) {
                List<Project> projectList = projectService.seek(item.getProjectName());
                if (!projectList.isEmpty()) {
                    Project p = projectList.get(0);
                    item.setProjectId(p.getId());
                    item.setProjectManager(projectService.getProjectManager(p.getId()));
                    item.setSaleStaff(p.getStaff());
                }
            }
        });
        return new ResponseModel(weixiuGroups);
    }

    @Operation(description = "导出项目分组维修数据为excel")
    @GetMapping("exportExcel")
    public ResponseModel export(String startDate, String endDate) throws UnsupportedEncodingException {

        String[] header = new String[]{"项目名称", "项目经理", "业务员", "报修次数", "明细"};
        String[] fields = new String[]{"ProjectName", "ParseProjectManager", "SaleStaff.Name", "Count", "Action"};

        List<Object> weixiuGroups = (List<Object>) getByProjectGroup(startDate, endDate).getData();
        for (Object weixiuGroup : weixiuGroups) {
            ProWeixiuGroup data = (ProWeixiuGroup) weixiuGroup;
            data.setActionParam("/" + startDate + "/" + endDate + "/" + URLEncoder.encode(data.getProjectName(), "UTF-8"));
        }

        String fileName = startDate + "-" + endDate + "-维修统计报表.xlsx";
        fileName = ExcelParse.writeExcel(weixiuGroups, fileName, header, fields);
        return new ResponseModel(fileName);
    }

    @Operation(description = "获取维修数据")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return new ResponseModel(weixiuService.queryById(id));
    }

    @Operation(description = "查询维修负责人列表")
    @GetMapping("personList")
    public ResponsePage personList(String searchText, Integer page, Integer itemsPerPage) {
        ProWeixiuPerson pwp = new ProWeixiuPerson();
        pwp.setProjectName(searchText);
        startPage(page, itemsPerPage, "date", "DESC");
        return ResponsePage.ok((Page) weixiuPersonService.queryAll(pwp));
    }

    @Operation(description = "新增维修负责人信息")
    @PutMapping("person")
    public ResponseModel insertPerson(@RequestBody ProWeixiuPerson proWeixiuPerson) {
        return ResponseModel.ok(weixiuPersonService.insert(proWeixiuPerson));
    }

    @Operation(description = "修改维修负责人信息")
    @PostMapping("person")
    public ResponseModel updatePerson(@RequestBody ProWeixiuPerson proWeixiuPerson) {
        return ResponseModel.ok(weixiuPersonService.update(proWeixiuPerson));
    }

}
