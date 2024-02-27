package com.yuesheng.pm.restapi;

import com.nimbusds.jose.util.Base64;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.Duty;
import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.StaffAdditionInfo;
import com.yuesheng.pm.interceptor.ApiInterceptor;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.LoginModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@Tag(name = "用户登录/注销")
@RestController
@RequestMapping("/api")
public class UserApi extends BaseApi {
    @Autowired
    private ManagerLogin managerLogin;
    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private StaffAdditionInfoService additionInfoService;
    @Autowired
    private MenuApi menuApi;
    @Autowired
    private ProStaffHwService proStaffHwService;

    @Operation(description = "用户注销操作")
    @RequestMapping(method = RequestMethod.GET, value = "user-logout/{token}")
    public ResponseModel loginOut(@PathVariable String token, @Parameter(hidden = true) HttpSession httpSession) throws Exception {
        try {
            ApiInterceptor.removeToken(token);
        } finally {
            managerLogin.loginOut(httpSession, null);
        }
        return new ResponseModel(200, "ok");
    }

    @NoToken
    @Operation(description = "用户登录操作")
    @RequestMapping(method = RequestMethod.POST, value = "user-login")
    public ResponseModel login(@RequestBody Staff staff, @Parameter(hidden = true) HttpSession httpSession) {
        if (staff != null) {
            try {
                staff.setPasswd(new RSAEncrypt().decrypt(staff.getPasswd()));
            } catch (Exception e) {
                // return new ResponseModel(500, e.getMessage());
            }
        }
        ModelAndView mav = managerLogin.verify(staff, httpSession);
        if (mav.getModelMap().get("info") != null) {
            return new ResponseModel(401, mav.getModelMap().get("info").toString());
        } else {
            Staff loginStaff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
            if (loginStaff != null) {
                return new ResponseModel(loginStaff.getToken());
            } else {
                return new ResponseModel(401, mav.getModelMap().get("info").toString());
            }
        }
    }


    @NoToken
    @Operation(description = "用户登录操作V2")
    @RequestMapping(method = RequestMethod.POST, value = "user-loginV2")
    @CrossOrigin
    public ResponseModel loginV2(@RequestBody Staff staff, @Parameter(hidden = true) HttpSession httpSession) {
        if (staff != null) {
            try {
                staff.setPasswd(new RSAEncrypt().decrypt(staff.getPasswd()));
            } catch (Exception e) {
                // return new ResponseModel(500, e.getMessage());
            }
        }
        ModelAndView mav = managerLogin.verify(staff, httpSession);
        if (mav.getModelMap().get("info") != null) {
            return new ResponseModel(401, mav.getModelMap().get("info").toString());
        } else {
            Staff loginStaff = (Staff) httpSession.getAttribute(Constant.SESSION_KEY);
            if (loginStaff != null) {
//                loginStaff.setHead(proStaffHwService.queryHeadByStaffId(loginStaff.getId()));
                return new ResponseModel(LoginModel.success(loginStaff,(List<Menu>)menuApi.getRootV2("3",loginStaff).getData()));
            } else {
                return new ResponseModel(401, "");
            }
        }
    }

    @Operation(description = "获取所有在线用户列表")
    @GetMapping("user-all")
    public ResponseModel getLoginUser() {
        return new ResponseModel(redisService.getAllValue(WebParam.USE_KEY));
    }

    @Operation(description = "强制退出指定用户")
    @PostMapping("force-user-logout/{token}")
    public ResponseModel forceUser(@PathVariable String token) {
        Object o = redisService.getValue(token);
        if (Objects.isNull(o)) {
            return new ResponseModel(500, "指定用户未在线");
        } else {
            redisService.deleteKey(token);
            return new ResponseModel("操作成功");
        }

    }

    @Operation(description = "通过微信openId登录")
    @PostMapping("openId")
    @NoToken
    public ResponseModel getUserByWxOpenId(String openId, HttpSession session) {
        StaffAdditionInfo info = additionInfoService.getStaffAdditionInfoByWxUserId(openId);
        if (!Objects.isNull(info)) {
            Staff staff = staffService.getStaffById(info.getStaffId());
            staff.setLastDate(DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL));
            staff.setDuty(dutyService.getDutyByStaffId(staff.getId()));
            staff.setRole(roleService.getRolesByStaff(staff.getId()));
            String token = WebParam.USE_KEY + Base64.encode(UUID.randomUUID().toString());
            staff.setToken(token);
            redisService.setKey(token, staff);
            redisService.expireKey(token, (long) 60 * 60 * 8, TimeUnit.SECONDS);
            session.setAttribute(Constant.SESSION_KEY, staff);
            return ResponseModel.ok(staff);
        } else {
            return ResponseModel.error("用户不存在,请绑定");
        }
    }


    @Operation(description = "通过微信openId登录")
    @PostMapping("openIdV2")
    @NoToken
    public ResponseModel getUserByWxOpenIdV2(String openId, HttpSession session) {
        StaffAdditionInfo info = additionInfoService.getStaffAdditionInfoByWxUserId(openId);
        if (!Objects.isNull(info)) {
            Staff staff = staffService.getStaffById(info.getStaffId());
            staff.setLastDate(DateUtil.format(DateUtil.getNowDate(), DateUtil.PATTERN_CLASSICAL));
            staff.setDuty(dutyService.getDutyByStaffId(staff.getId()));
            staff.setRole(roleService.getRolesByStaff(staff.getId()));
            String token = WebParam.USE_KEY + Base64.encode(UUID.randomUUID().toString());
            staff.setToken(token);
            redisService.setKey(token, staff);
            redisService.expireKey(token, (long) 60 * 60 * 8, TimeUnit.SECONDS);
//            staff.setHead(proStaffHwService.queryHeadByStaffId(staff.getId()));
            staff.setHead("");
            session.setAttribute(Constant.SESSION_KEY, staff);
            //查询用户权限菜单
            ResponseModel rm = menuApi.getRoot(3, staff);
            if (!Objects.isNull(rm.getData())) {
                return ResponseModel.ok(LoginModel.success(staff, (List<Menu>) rm.getData()));
            } else {
                return ResponseModel.ok(LoginModel.success(staff, null));
            }
        } else {
            return ResponseModel.error("用户不存在,请绑定");
        }
    }

    @Operation(description = "绑定微信openId")
    @PostMapping("bindOpenId/{openId}")
    @NoToken
    public ResponseModel bdOpenId(@RequestBody Staff staff, @PathVariable String openId, @Parameter(hidden = true) HttpSession session) {
        ResponseModel rm = login(staff, session);
        if (rm.getCode() == 200) {
            staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
            //验证成功，绑定
            StaffAdditionInfo info = new StaffAdditionInfo();
            info.setWxOpenId(null);
            info.setWxUserId(openId);
            info.setSystemId(CompanyWxUtil.APPID);
            info.setSystemName(CompanyWxUtil.AGENTID);
            info.setStaffId(staff.getId());
            additionInfoService.insert(info);
        }
        return rm;
    }

    /**
     * 供第三方调用，获取系统人员集合
     *
     * @param id   主键
     * @param type 获取类型{}
     * @return
     */
    @NoToken
    @GetMapping("getSystemPerson")
    public ResponseModel getUserInfoByType(String id, Integer type) {
        try {
            id = URLDecoder.decode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Staff> modelList = new ArrayList();
        switch (type) {
            case 1:
//                人员id
                modelList.add(staffService.getStaffById(id));
                break;
            case 2:
//                角色
                modelList.addAll(roleService.getStaffByRoleCoding(id));
                break;
            case 3:
//                根据职务编码，获取上级职务领导
//                Staff staff = staffService.getStaffById(id);
                Duty[] dutys = dutyService.getDutyByStaffId(id);
                if (dutys != null) {
                    for (Duty duty : dutys) {
                        if (duty != null) {
                            duty = dutyService.getById(duty.getParentId());
                            if (duty != null) {
                                modelList.addAll(dutyService.getStaffByDuty(duty.getId()));
                            }
                        }
                    }
                }
                break;
        }
        return new ResponseModel(modelList);
    }
}
