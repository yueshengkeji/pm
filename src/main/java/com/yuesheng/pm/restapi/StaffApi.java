package com.yuesheng.pm.restapi;

import com.github.pagehelper.Page;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.LoginModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Tag(name = "职员基础信息管理")
@RestController
@RequestMapping("/api/staff")
public class StaffApi extends BaseApi {
    @Autowired
    private StaffService staffService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProStaffHwService proStaffHwService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StaffAdditionInfoService staffAdditionInfoService;
    @Autowired
    private DingTalkStaffInfoService dingTalkStaffInfoService;
    @Autowired
    private DingTalkApiService dingTalkApiService;
    @Autowired
    private ProNotifyTypeService notifyTypeService;

    @Autowired
    private ProPlateService plateService;

    @Autowired
    private StDeviceService stDeviceService;

    @Autowired
    private MenuApi menuApi;

    @Autowired
    private StaffCardService cardService;

    @Operation(description = "查询职员信息列表")
    @GetMapping
    public ResponseModel getStaffs(@Parameter(name="搜索字符串，可选") String str) throws UnsupportedEncodingException {
        return new ResponseModel(staffService.seek(StringUtils.isBlank(str) ? "" : URLDecoder.decode(str, "utf-8")));
    }

    @Operation(description = "查询职员信息列表")
    @GetMapping("all")
    public ResponseModel getStaff(String status, String searchText, Integer page, Integer itemsPerPage, String sortBy, Boolean sortDesc) {
        HashMap<String, Object> result = new HashMap<>();
        int count = 0;
        if ("unLogin".equals(status)) {
            List<Staff> staffList = staffService.queryUnLogin("");
            count = staffList.size();
            result.put("rows", setStaffInfo(staffList));
        } else {
            sortBy = getSortBy(sortBy);
            startPage(page, itemsPerPage, sortBy, sortDesc);
            Page<Staff> staffPage = (Page<Staff>) staffService.seek(searchText);
            count = (int) staffPage.getTotal();
            result.put("rows", setStaffInfo(staffPage));
        }
        result.put("count", count);
        return new ResponseModel(result);
    }

    private String getSortBy(String sortBy) {
        switch (sortBy) {
            case "lastLogin":
                return "pj00411";
            case "roleName":
            case "duty":
            case "additionInfo":
            case "plates":
                return "pj00401";
            case "tel":
                return "pj00420";
            case "section.name":
                return "pj00417";
            case "name":
                return "pj00402";
            default:
                return "pj00425";
        }
    }

    private List<StaffPlate> setStaffInfo(List<Staff> staffList) {
        List<StaffPlate> result = new ArrayList<>();
        staffList.forEach(s -> {
            StaffPlate staffPlate = new StaffPlate();
            BeanUtils.copyProperties(s, staffPlate);
            //查询员工职务
            staffPlate.setDuty(dutyService.getDutyByStaffId(s.getId()));
            //查询员工角色
            Role[] roles = roleService.getRolesByStaff(s.getId());
            if (roles != null) {
                Role r = null;
                String[] rns = new String[roles.length];
                for (int i = 0; i < roles.length; i++) {
                    if ((r = roles[i]) != null) {
                        rns[i] = r.getName();
                    }
                }
                staffPlate.setRole(roles);
                staffPlate.setRoleName(rns);
            }
            //查询员工车牌信息
            ProPlate query = new ProPlate();
            query.setStaffId(s.getId());
            staffPlate.setPlate(plateService.queryAll(query));
            //查询员工是否绑定微信
            staffPlate.setAdditionInfo(staffAdditionInfoService.getStaffAdditionInfoByStaffIdAndSystemId(s.getId(), null));
            //是否绑定钉钉
            staffPlate.setDingTalkStaffInfo(dingTalkStaffInfoService.selectByStaffId(s.getId()));
            result.add(staffPlate);
        });
        return result;
    }

    @Operation(description = "同步职员车辆信息")
    @GetMapping("syncUserCar")
    public ResponseModel syncUserCar() {
        staffService.syncCarPlan();
        return new ResponseModel("success");
    }

    @Operation(description = "添加职员信息")
    @PostMapping
    public ResponseModel insert(@RequestBody StaffPlate staff) {
        Map<String, Object> result = staffService.insert(staff);
        if ("1".equals(result.get("state"))) {
            List<ProPlate> plates = staff.getPlate();
            if (plates != null && !plates.isEmpty()) {
                plates.forEach(item -> {
                    insertPlate(staff, item);
                });
            }
        }
        return new ResponseModel(result);
    }

    @GetMapping("restartSyncCar")
    public ResponseModel restartSyncCar() {
        List<Staff> staffList = staffService.seek("");
        for (Staff staff : staffList) {
            ProPlate query = new ProPlate();
            query.setStaffId(staff.getId());
            List<ProPlate> plates = plateService.queryAll(query);
            plates.forEach(item -> {
                if (staff.getId().equals("09b29623-b4f9-45de-9380-447c9fdfcec9")) {
                    System.out.println(item.getPlate());
                }
                //删除车牌
                deleteCarPLate(item.getPlate());
                //重新添加
                StaffPlate sp = new StaffPlate();
                sp.setId(staff.getId());
                sp.setName(staff.getName());
                sp.setTel(staff.getTel());
                insertPlate(sp, item);
            });
        }
        return new ResponseModel("success");
    }

    @DeleteMapping("deleteCarPlate")
    public ResponseModel deleteCarPLate(String plate) {
        plateService.deleteByPlate(plate);
        String rs = new String(NetRequestUtil.sendGetRequest("http://192.168.3.112:8081/open/ts/removeFixedCar?plate=" + plate + "&us=admin&ps=zm123"));
        if (StringUtils.containsIgnoreCase(rs, "connect timed out")) {
            return new ResponseModel(500, "车牌系统网络未连接，操作失败");
        } else {
            return new ResponseModel("success");
        }
    }

    private boolean insertPlate(@RequestBody StaffPlate staff, ProPlate item) {
        LinkedHashMap param = new LinkedHashMap();
        param.put("us", "admin");
        param.put("ps", "zm123");
        if (item != null && StringUtils.isNotBlank(item.getPlate())) {
            param.put("plate", item.getPlate());
            param.put("nice", staff.getName());
            param.put("carTypeId", "3");
            param.put("start", DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL_SIMPLE));
            param.put("end", "2050-01-01");
            param.put("tel", staff.getTel());
            try {
                String rs = NetRequestUtil.sendGetRequest("http://192.168.3.112:8081/open/ts/saveFixedCar", param);
                if (StringUtils.containsIgnoreCase(rs, "connect timed out")) {
                    return false;
                } else {
                    if (staff.getId() != null) {
                        item.setId(UUID.randomUUID().toString());
                        item.setStaffId(staff.getId());
                        plateService.insert(item);
                    }
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            return true;
        }
    }

    @Operation(description = "更新职员信息")
    @PutMapping
    public ResponseModel update(@RequestBody StaffPlate staff) throws Exception {
        staffService.update(staff);
        if (staff.getIsLogin() == 1) {
            ProPlate proPlate = new ProPlate();
            proPlate.setStaffId(staff.getId());
            List<ProPlate> plates = plateService.queryAll(proPlate);
            plates.forEach(p -> {
                //离职，清空车牌信息
                NetRequestUtil.sendGetRequest("http://192.168.3.112:8081/open/ts/removeFixedCar?plate=" + p.getPlate() + "&us=admin&ps=zm123");
            });
            //离职，从钉钉剔除
            dingTalkApiService.deleteUser(staff);
            StaffAdditionInfo info = staffAdditionInfoService.getStaffAdditionInfoByStaffIdAndSystemId(staff.getId(), null);
            if(!Objects.isNull(info))
            {
                //删除企业微信通讯录
                CompanyWxUtil.deleteUser(info.getWxUserId());
                staffAdditionInfoService.deleteByStaffId(staff.getId());
            }
        } else {
            //判断车牌号是否存在，如果不存在，添加新的
            List<ProPlate> proPlates = staff.getPlate();
            if (!Objects.isNull(proPlates)) {
                for (int i = 0; i < proPlates.size(); i++) {
                    ProPlate item = proPlates.get(i);
                    if (StringUtils.isNotBlank(item.getPlate())) {
                        ProPlate query = new ProPlate();
                        query.setPlate(item.getPlate());
                        List<ProPlate> plates = plateService.queryAll(query);
                        if (plates.isEmpty()) {
                            //添加新的车牌号到道闸
                            if (!insertPlate(staff, item)) {
                                throw new Exception("车牌系统网络未连接，操作失败");
                            }
                        }
                    }
                }
            }
        }
        return new ResponseModel("操作成功");
    }

    @Operation(description = "获取离职职员列表")
    @GetMapping("offStaff")
    public ResponseModel getUnLoginStaff(String str) {
        return new ResponseModel(staffService.queryUnLogin(StringUtils.isBlank(str) ? "" : str));
    }

    @Operation(description = "通过token获取职员对象")
    @GetMapping("getStaffByToken")
    @NoToken
    public ResponseModel getByToken(@Parameter(name="登陆成功后返回的token字符串", required = true) String token,HttpSession session) {
        if (StringUtils.isBlank(token)) {
            new ResponseModel(401, "年轻人，一定要传token!");
        }
        Staff staff = (Staff) redisService.getValue(token);
        try {
            if(Objects.isNull(staff)){
                staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
            }
            if (Objects.isNull(staff)) {
                return ResponseModel.error("登录凭证无效");
            }
            staff.setHead(proStaffHwService.queryHeadByStaffId(staff.getId()));
            staff.setRole(roleService.getRolesByStaff(staff.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel(staff);
    }

    @Operation(description = "通过token获取职员对象")
    @GetMapping("getStaffByTokenV2")
    @NoToken
    public ResponseModel getByTokenV2(@Parameter(name="登陆成功后返回的token字符串", required = true) String token,
                                      HttpSession session) {
        if (StringUtils.isBlank(token)) {
            new ResponseModel(401, "年轻人，一定要传token!");
        }
        Staff staff = (Staff) redisService.getValue(token);
        List<Menu> menus = new ArrayList<>();
        try {
            if(Objects.isNull(staff)){
                staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
            }
            if (Objects.isNull(staff)) {
                return ResponseModel.error("登录凭证无效");
            }
            staff.setHead(proStaffHwService.queryHeadByStaffId(staff.getId()));
            staff.setRole(roleService.getRolesByStaff(staff.getId()));
            menus = (List<Menu>) menuApi.getRootV2("3",staff).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseModel(LoginModel.success(staff,menus));
    }

    @Operation(description = "修改职员登陆密码")
    @PostMapping("updatePasswd")
    public ResponseModel updateLoginInfo(@RequestBody Staff staff) {
        try {
            staff.setPasswd(new RSAEncrypt().decrypt(staff.getPasswd()));
        } catch (Exception e) {
            return new ResponseModel(500, e.getMessage());
        }
        return new ResponseModel(staffService.updateUserPasswd(staff));
    }

    @Operation(description = "设置用户是否限制登陆")
    @PostMapping("setIsLogin")
    public ResponseModel setStaffIsLogin(@Parameter(name="职员id") String id, @Parameter(name="是否禁止登陆，0=可以登陆，1=禁止登陆") Byte isOffLogin) {
        if (StringUtils.isBlank(id)) {
            return new ResponseModel(401, "请指定职员id");
        }
        if (Objects.isNull(isOffLogin)) {
            return new ResponseModel(401, "请指定是否登陆元素");
        }
        Staff staff = new Staff();
        staff.setId(id);
        staff.setIsLogin(isOffLogin);
        staffService.updateIsLogin(staff);
        return new ResponseModel(200, "操作成功");
    }

    @Operation(description = "通过职员id获取职员对象")
    @GetMapping("/getById/{id}")
    public ResponseModel selectById(@PathVariable String id) {
        Staff staff = staffService.getStaffById(id);
        if(!Objects.isNull(staff))
        {
            staff.setDuty(dutyService.getDutyByStaffId(id));
            staff.setRole(roleService.getRolesByStaff(id));
        }
        return new ResponseModel(staff);
    }

    @Operation(description = "同步与企业微信用户信息")
    @GetMapping("/syncWeiCharUser")
    public ResponseModel syncUser() {
        staffService.syncUserInfo();
        return new ResponseModel("操作成功");
    }

    @Operation(description = "解除该员工微信绑定")
    @PostMapping("unBindWeiChar/{id}")
    public ResponseModel unBindWeiChar(@PathVariable String id) {
        staffAdditionInfoService.deleteByStaffId(id);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "同步钉钉信息")
    @GetMapping("syncDingTalkUser")
    public ResponseModel syncDingTalkUser() {
        staffService.syncDingTalkUser();
        return new ResponseModel("操作成功");
    }

    @Operation(description = "解除钉钉绑定")
    @PostMapping("unBindDingTalk/{id}")
    public ResponseModel unBindDingTalk(@PathVariable String id) {
        dingTalkStaffInfoService.deleteByStaffId(id);
        return new ResponseModel("操作成功");
    }

    @Operation(description = "重置登录密码为：@123@123")
    @PostMapping("resetPasswd/{id}")
    public ResponseModel resetPasswd(@PathVariable String id) {
        Staff staff = staffService.getStaffById(id);
        staff.setPasswd("@123@123");
        staffService.updateUserPasswd(staff);
        return new ResponseModel("操作成功");
    }

    /**
     * 查询职员头像
     * @param staffId
     * @return
     */
    @GetMapping("getHead/{staffId}")
    public ResponseModel getHead(@PathVariable String staffId) {
        return new ResponseModel(proStaffHwService.queryHeadByStaffId(staffId));
    }

    @Operation(description = "查询职员在考勤机中录入的信息")
    @GetMapping("infoByStDevice")
    public ResponseModel infoByStDevice(String staffId) {
        List<ProStaffHw> proStaffHwList = proStaffHwService.queryListByStaffId(staffId);
        if (proStaffHwList.isEmpty()) {
            return new ResponseModel(500, "该员工未绑定考勤机");
        }
        for (ProStaffHw proStaffHw : proStaffHwList) {
            if (Objects.isNull(proStaffHw)) {
                continue;
            } else {
                String id = proStaffHw.getId().substring(1, proStaffHw.getId().length() - 1);
                if(StringUtils.isBlank(id)){
                    continue;
                }
                List<Staff> staffList = stDeviceService.getDeviceUsers(id);
                Staff staff = null;
                if (staffList.size() == 1) {
                    staff = setHead(staffList);
                } else {
                    staffList = stDeviceService.getDeviceUsers(proStaffHw.getId().substring(1));
                    if (staffList.size() == 1) {
                        staff = setHead(staffList);
                    }
                }
                if (staff != null) {
                    return new ResponseModel(staff);
                }
            }
        }
        return new ResponseModel(500, "该员工未绑定考勤机");
    }

    @Operation(description = "查询推送通知类型")
    @GetMapping("getNotifyType/{id}")
    public ResponseModel getNotifyType(@PathVariable String id) {
        return ResponseModel.ok(notifyTypeService.queryById(id));
    }

    @Operation(description = "更新推送通知类型")
    @PostMapping("updateNotifyType")
    public ResponseModel updateNotifyType(@RequestBody ProNotifyType notifyType) {
        ProNotifyType temp = notifyTypeService.queryById(notifyType.getStaffId());
        if (Objects.isNull(temp)) {
            notifyTypeService.insert(notifyType);
        } else {
            notifyTypeService.update(notifyType);
        }
        return ResponseModel.ok(notifyType);
    }

    @Operation(description = "添加推送通知类型")
    @PutMapping("insertNotifyType")
    public ResponseModel insertNotifyType(@RequestBody ProNotifyType notifyType) {
        notifyTypeService.insert(notifyType);
        return ResponseModel.ok(notifyTypeService.queryById(notifyType.getStaffId()));
    }

    @Operation(description = "更新用户手机型号和品牌")
    @PutMapping("updateMobileInfo")
    public ResponseModel updateMobileInfo(@RequestBody Staff staff){
        return ResponseModel.ok(staffService.updateMobileInfo(staff));
    }
    @Operation(description = "获取登录职员用户和密码")
    @GetMapping("getUserLogin")
    public ResponseModel getUserLogin(@SessionAttribute(Constant.SESSION_KEY)Staff staff){
        Staff staff1 = staffService.getUserLogin(staff.getCoding());
        try {
            staff1.setPasswd(new RSAEncrypt().encrypt(staff1.getPasswd()));
        } catch (Exception e) {
            return ResponseModel.error(e.getMessage());
        }
        return ResponseModel.ok(staff1);
    }

    @Operation(description = "查询员工名片")
    @GetMapping("getStaffCardById/{id}")
    @NoToken
    public ResponseModel getStaffCardById(@PathVariable String id)
    {
        return ResponseModel.ok(cardService.queryById(id));
    }

    @Operation(description = "通过职员查询员工第一个名片")
    @GetMapping("getStaffCardByStaffId/{staffId}")
    public ResponseModel getStaffCardByStaffId(@PathVariable String staffId){
        return ResponseModel.ok(cardService.queryByStaff(staffId));
    }

    @Operation(description = "通过职员查询员工所有名片")
    @GetMapping("getAllStaffCardByStaffId/{staffId}")
    public ResponseModel getAllStaffCardByStaffId(@PathVariable String staffId){
        return ResponseModel.ok(cardService.queryAllByStaff(staffId));
    }

    @Operation(description = "修改名片信息")
    @PostMapping("updateCard")
    public ResponseModel updateCard(@RequestBody StaffCard card){
        cardService.update(card);
        return ResponseModel.ok(card);
    }

    @Operation(description = "生成当前员工电子名片")
    @PutMapping("genStaffCard")
    public ResponseModel genStaffCard(@SessionAttribute(Constant.SESSION_KEY)Staff staff){
        StaffCard sc= new StaffCard();
        sc.setStaff(staff);
        sc.setName(staff.getName());
        sc.setTel(staff.getTel());
        sc.setEmail(staff.getEmail());
        sc.setPhone(staff.getTel());
        sc.setDuty(staff.getDutyName());
        cardService.insert(sc);
        return ResponseModel.ok(sc);
    }

    private Staff setHead(List<Staff> staffList) {
        Staff staff;
        staff = staffList.get(0);
        byte[] res = NetRequestUtil.sendGetRequest("https://link.bi.sensetime.com/v1/image/1/" + staff.getHead());
        staff.setHead(new String(Base64.getEncoder().encode(res)));
        return staff;
    }
}
