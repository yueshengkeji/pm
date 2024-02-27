package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.StaffAdditionInfo;
import com.yuesheng.pm.service.DutyService;
import com.yuesheng.pm.service.RoleService;
import com.yuesheng.pm.service.StaffAdditionInfoService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.CompanyWxUtil;
import com.yuesheng.pm.util.NetRequestUtil;
import com.yuesheng.pm.listener.WebParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信Controller
 *
 * @author lujian
 * @date 2019/03/09
 */
@Controller
@RequestMapping("/wx")
public class WxController {
    /**
     * 获取用户信息
     */
    private static String GET_USERINFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffAdditionInfoService staffAdditionInfoService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private RoleService roleService;

    /**
     * 微信用户信息初始化
     *
     * @param request
     */
    @RequestMapping("/memberInit")
    public synchronized void memberInit(HttpServletRequest request, HttpServletResponse response) {
        //获取code
        String code = request.getParameter("code");
        //获取网页授权webAccessToken，openId
        Map<String, Object> userInfo = getUserInfo(code);

        //获取微信用户基础信息（授权方式需要为手动授权）
        request.getSession().setAttribute("SESSION_OPENID", userInfo.get("openId"));
        request.getSession().setAttribute("SESSION_USERID", userInfo.get("userId"));
        request.getSession().setAttribute("SESSION_CODE", code);

        //转发到目标页面
        String targetUrl = (String) request.getSession().getAttribute("TARGETURL");

        try {
            response.sendRedirect(WebParam.NOTIFY_IP + targetUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转到绑定页面
     *
     * @param targetUrl 未绑定前访问的目标url
     * @return
     */
    @RequestMapping("/openToBind")
    public ModelAndView openToBind(@RequestParam(name = "targetURL") String targetUrl) {
        ModelAndView view = new ModelAndView("/wx/bind");
        System.out.println("跳了view哦");
        view.addObject("targetUrl", targetUrl);
        return view;
    }

    /**
     * 用户绑定
     *
     * @return
     */
    @RequestMapping("/verifyBind")
    @ResponseBody
    public Map<String, Object> verifyBind(Staff staff, HttpSession session) {
        Map<String, Object> verResult = new HashMap();
        String wxOpenId = (String) session.getAttribute("SESSION_OPENID");
        String wxUserId = (String) session.getAttribute("SESSION_USERID");
        if (staffAdditionInfoService.getStaffAdditionInfoByWxOpenId(wxOpenId) == null
                && staffAdditionInfoService.getStaffAdditionInfoByWxUserId(wxUserId) == null) {
            try {
                verResult = verify(staff);
                String loginCode = (String) verResult.get("loginCode");
                staff = (Staff) verResult.get("staff");
                if ("1".equals(loginCode) && staff != null) {
                    StaffAdditionInfo info = new StaffAdditionInfo();
                    info.setWxOpenId(wxOpenId);
                    info.setWxUserId(wxUserId);
                    info.setSystemId(CompanyWxUtil.APPID);
                    info.setSystemName(CompanyWxUtil.AGENTID);
                    info.setStaffId(staff.getId());
                    boolean res = staffAdditionInfoService.insert(info);
                    if (!res) {
                        verResult.put("loginCode", "-1");
                        verResult.put("errMsg", "信息绑定微信绑定失败");
                    }
                    staffService.syncUserInfo(wxUserId, staff.getId());
                } else {
                    verResult.put("loginCode", "-1");
                    verResult.put("errMsg", "信息校验失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            verResult.put("loginCode", "-1");
            verResult.put("errMsg", "已绑定");
        }
        return verResult;
    }

    /**
     * 获取微信token
     *
     * @return
     */
    @RequestMapping("/getWxToken")
    @ResponseBody
    public Map<String, Object> getWxToken() {
        Map<String, Object> result = new HashMap<>();
        result.put("state", 1);
        result.put("data", CompanyWxUtil.getToken());
        return result;
    }

    /**
     * 授权获取用户信息
     *
     * @param code
     * @return
     */
    public static Map<String, Object> getUserInfo(String code) {
        Map<String, Object> result = new HashMap<>(16);
        String accessToken = CompanyWxUtil.getToken(CompanyWxUtil.APPID,CompanyWxUtil.SECRET);

        LinkedHashMap<String, String> params1 = new LinkedHashMap<>();
        params1.put("code", code);
        params1.put("access_token", accessToken);
        String responseStr1 = NetRequestUtil.sendGetRequest(GET_USERINFO_URL, params1);
        JSONObject responseObject1 = JSONObject.parseObject(responseStr1);
        String openId = (String) responseObject1.get("OpenId");
        String userId = (String) responseObject1.get("UserId");
        result.put("openId", openId);
        result.put("userId", userId);
        return result;
    }

    /**
     * 验证用户名和密码是否正确
     *
     * @param staff {name："登录用户名",passwd："登录密码",comId：""}
     * @return loginCode："1=验证成功"，staff:"用户对象"
     * @throws Exception
     */
    public Map<String, Object> verify(Staff staff) throws Exception {

        Map<String, Object> result = new HashMap<>(16);
        String loginCode = "1";
        //加密登陆
        AESEncrypt.encryptBASE64(staff.getPasswd().getBytes());
        Staff temp = staffService.loginIn(staff.getName(), AESEncrypt.encryptBASE64(staff.getPasswd().getBytes()));
        if (temp == null)        //不加密登陆
        {
            temp = staffService.loginIn(staff.getName(), staff.getPasswd());
        }
        staff = temp;
        if ("1".equals(loginCode)) {
            if (staff.getId() == null || "".equals(staff.getId())) {      //兼容pm2
                staff = staffService.login(staff.getName());
                staff.setDuty(dutyService.getDutyByStaffId(staff.getId()));
                staff.setRole(roleService.getRolesByStaff(staff.getId()));
            }
            if (staff == null) {
                loginCode = "-1";
                result.put("info", "用户名不存在或密码错误");
            } else if (staff.getIsLogin() == 1) {
                loginCode = "-2";
                result.put("info", "您已被管理员限制登录");
            }

        } else {
            result.put("info", "验证失败");
            loginCode = "-3";
        }

        result.put("loginCode", loginCode);
        result.put("staff", staff);
        return result;
    }


}
