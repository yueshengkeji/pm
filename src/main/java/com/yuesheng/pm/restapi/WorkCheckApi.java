package com.yuesheng.pm.restapi;

import com.yuesheng.pm.entity.ProWorkCheck;
import com.yuesheng.pm.entity.ProWorkCheckPermission;
import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.CompanyWxUtil;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.NetRequestUtil;
import com.yuesheng.pm.listener.WebParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

@Tag(name = "考勤报表")
@RestController
@RequestMapping("api/workCheck")
public class WorkCheckApi {
    @Autowired
    private ProWorkCheckService workCheckService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private ProWorkCheckPermissionService permissionService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private FlowNotifyService notifyService;

    @Operation(description = "获取所有考勤数据")
    @GetMapping("list")
    public ResponseModel list(@Parameter(name="开始时间") String startDate,
                              @Parameter(name="截止时间") String endDate,
                              @Parameter(name="是否获取隐藏的考勤数据") Integer isShow,
                              @Parameter(name="考勤数据类型：") Integer type) {
        return ResponseModel.ok(workCheckService.queryAllByDate(startDate, endDate, isShow, type));
    }

    @Operation(description = "导出考勤数据")
    @GetMapping("exportList")
    public ResponseModel exportList(String path) {
        return ResponseModel.ok(path);
    }

    @Operation(description = "获取部门内考勤数据")
    @GetMapping("listByDept")
    public ResponseModel listByDept(@Parameter(name="开始时间") String startDate,
                                    @Parameter(name="截止时间") String endDate,
                                    @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        Section section = staff.getSection();
        List<Section> allSection = otherPermission(staff);
        if (StringUtils.equals(section.getManagerid(), staff.getId()) ||
                StringUtils.equals(section.getCoding(), staff.getId()) ||
                !allSection.isEmpty()) {
            if (allSection.isEmpty()) {
                List<Section> sections = sectionService.getSectionByManagerId(staff.getId());
                sections.forEach(item -> {
                    allSection.add(item);
                    allSection.addAll(sectionService.getSectionByParent(item.getId(), true));
                });
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < allSection.size(); i++) {
                List<Staff> staffList = sectionService.getStaffList(allSection.get(i).getId());
                staffList.forEach(item -> {
                    if (!Objects.isNull(item)) {
                        sb.append("'");
                        sb.append(item.getId());
                        sb.append("'");
                        sb.append(",");
                    }
                });
            }

            if (sb.length() > 0) {
                return ResponseModel.ok(workCheckService.queryAllByStaffIds(startDate, endDate, sb.substring(0, sb.length() - 1).toString()));
            } else {
                return ResponseModel.ok();
            }
        } else {
            return ResponseModel.error(staff.getName() + "没有" + section.getName() + "的考勤数据权限");
        }
    }

    /**
     * 检测考勤权限
     *
     * @param staff 职员信息
     * @return
     */
    private List<Section> otherPermission(Staff staff) {
        List<ProWorkCheckPermission> permissions = permissionService.queryById(staff.getId());
        List<Section> sections = new ArrayList<>();
        permissions.forEach(item -> {
            sections.add(sectionService.getSection(item.getSectionId()));
            sections.addAll(sectionService.getSectionByParent(item.getId(), true));
        });
        return sections;
    }

    @Operation(description = "获取当前登录用户考勤数据")
    @GetMapping("listByStaff")
    public ResponseModel listByStaff(@Parameter(name="开始时间") String startDate,
                                     @Parameter(name="截止时间") String endDate,
                                     @Parameter(name="员工id") String staffId) {
        if (StringUtils.isBlank(startDate)) {
            return ResponseModel.error("请指定查询时间范围");
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("staffId", staffId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return ResponseModel.ok(workCheckService.queryByStaff(param));
    }

    @Operation(description = "同步加班数据、请假数据到考勤表")
    @PostMapping("syncLeaveAndOvertime")
    public ResponseModel syncLeaveAndOvertime(@Parameter(name="开始时间") String startDate,
                                              @Parameter(name="截止时间") String endDate) {
        workCheckService.syncLeaveAndOvertime(startDate, endDate);
        return ResponseModel.ok();
    }

    @Operation(description = "获取考勤附件")
    @GetMapping("file/{type}/{signBgAvatar}")
    public ResponseModel file(@PathVariable String signBgAvatar, @PathVariable Integer type) {
        FileInputStream fis = null;
        FileOutputStream outputStream = null;
        try {
            String fileName = WebParam.assetsPath + signBgAvatar + ".png";
            File file = new File(fileName);
            if (file.exists()) {
                fis = new FileInputStream(file);
                return ResponseModel.ok(new String(Base64.getEncoder().encode(IOUtils.toByteArray(fis))));
            }
            if (type == 2) {
                //获取企业微信数据
                String data = CompanyWxUtil.getMedia(signBgAvatar);
                outputStream = new FileOutputStream(fileName);
                IOUtils.write(Base64.getDecoder().decode(data), outputStream);
                return ResponseModel.ok(data);
            } else {
                //获取商汤考勤机数据
                outputStream = new FileOutputStream(fileName);
                byte[] bytes = NetRequestUtil.sendGetRequest("https://link.bi.sensetime.com/v1/image/2/" + signBgAvatar);
                IOUtils.write(bytes, outputStream);
                return ResponseModel.ok(new String(Base64.getEncoder().encode(bytes)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(fis);
        }
        return ResponseModel.error("未查询到附件");

    }

    @Operation(description = "获取考勤数据明细")
    @GetMapping("{id}")
    public ResponseModel getById(@PathVariable String id) {
        return ResponseModel.ok(workCheckService.queryById(id));
    }

    @Operation(description = "根据日期、时间、员工id，查询考勤数据")
    @GetMapping("getByDatetime")
    public ResponseModel getByDatetime(String date, String time, String staffId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("date", date);
        param.put("time", time);
        param.put("staffId", staffId);
        return ResponseModel.ok(workCheckService.queryByDatetime(param));
    }

    @Operation(description = "申请考勤补卡数据")
    @PutMapping("insertWorkCheck")
    public ResponseModel insert(@RequestBody ProWorkCheck check, @SessionAttribute(Constant.SESSION_KEY) Staff staff) {
        check.setType((byte) -1);
        check.setId(UUID.randomUUID().toString());
        check.setStaffId(staff.getId());
        check.setAttache("");
        check.setStaffName(staff.getName() + "," + staff.getSection().getName());
        if (!workCheckService.exists(check, check.getStaffId())) {
            workCheckService.insert(check);
            Section s = staff.getSection();
            if (!Objects.isNull(s)) {
                Staff manager = staffService.getStaffById(s.getManagerid());
                if (!Objects.isNull(manager)) {
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("title", "考勤补卡审核");
                    param.put("content", staff.getName() + ";考勤时间：" + check.getDate() + " " + check.getTime());
                    param.put("mTitle", "点击进入，审核或驳回");
                    param.put("url", WebParam.VUETIFY_BASE + "/workCheck/approve/" + check.getId());
                    notifyService.msgNotify(manager, param);
                }
            }
        }
        return ResponseModel.ok(check);
    }

    @Operation(description = "审核考勤补卡数据")
    @PutMapping("approve")
    public ResponseModel approve(@RequestBody ProWorkCheck check) {
        if (check.getType() == -1) {
            HashMap<String, Object> param = new HashMap<>();
            param.put("title", "考勤补卡审核通知");
            param.put("mTitle", "考勤时间：" + check.getDate() + " " + check.getTime());
            param.put("content", "被驳回");
            param.put("url", WebParam.VUETIFY_BASE + "/workCheck/approve/" + check.getId());
            notifyService.msgNotify(staffService.getStaffById(check.getStaffId()), param);
        } else {
            workCheckService.updateType(check);
        }
        return ResponseModel.ok(check);
    }

}
