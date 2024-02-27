package com.yuesheng.pm.interceptor;

import com.nimbusds.jose.util.Base64;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.StaffAdditionInfo;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.CompanyWxUtil;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.listener.WebParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 微信公众号拦截器
 *
 * @author lujian
 */
@Component
public class WxPublicAccountInterceptor implements HandlerInterceptor {
    @Autowired
    private StaffAdditionInfoService staffAdditionInfoService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private RedisService redisService;

    /**
     * 用户授权
     */
    private static String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    /**
     * 用户信息初始化
     */
    private static String MEMBERINIT_URL = WebParam.NOTIFY_IP + "/wx/memberInit";
    /**
     * 请求失败跳转页面 TODO
     */
    private static String FAIL_URL = WebParam.NOTIFY_IP + "/wx/wxFail";

    /**
     * 临时session用户id
     */
    private final static String TEMP_SESSION_USER_ID = "email_system_auth";

    /**
     * 在请求处理之前进行调用（Controller方法调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));

        String userAgent = request.getHeader("user-agent");
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
        } else {
            userAgent = "";
        }
        //判断是否是微信
        if (userAgent.contains("micromessenger")) {
            String targetURL = request.getRequestURI();

            //连接请求参数
            List<String> paramStrs = new ArrayList<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramStr = "";
                String name = paramNames.nextElement();
                paramStr += name;
                String value = request.getParameter(name);
                paramStr += "=" + value;
                paramStrs.add(paramStr);
            }

            if (paramStrs.size() > 0) {
                targetURL += "?";
            }
            for (int i = 0; i < paramStrs.size(); i++) {
                String param = paramStrs.get(i);
                if (i == paramStrs.size() - 1) {
                    targetURL += param;
                } else {
                    targetURL += param + "&";
                }
            }

            if (!targetURL.contains("wx/memberInit")
                    && (targetURL.contains("managerIndex") ||
                    targetURL.contains("/managent/toApproveView") ||
                    targetURL.startsWith("/managent/systemInfo") ||
                    targetURL.startsWith("/managent/systemNotify"))
            ) {
                HttpSession session = request.getSession();

                //判断微信授权是否获取
                if (getWxApprove(session)) {
                    //获取用户授权并重定向
                    session.setAttribute("TARGETURL", targetURL);

                    //跳转到微信授权
                    String codeRequestUrl = getRequestCodeUrl();
                    response.sendRedirect(codeRequestUrl);
                    return false;
                } else {
                    boolean hasSessionUser = true;
                    if (session.getAttribute(Constant.SESSION_KEY) == null) {
                        hasSessionUser = false;
                    } else {
                        Staff loginUser = (Staff) session.getAttribute(Constant.SESSION_KEY);
                        //判断是不是临时session
                        if (TEMP_SESSION_USER_ID.equals(loginUser.getId())) {
                            hasSessionUser = false;
                        }
                    }

                    if (!hasSessionUser) {
                        //判断微信是否绑定员工信息,没有绑定则跳转到绑定页面

                        String openId = (String) session.getAttribute("SESSION_OPENID");
                        String userId = (String) session.getAttribute("SESSION_USERID");
                        //判断是否绑定微信信息
                        StaffAdditionInfo staffAdditionInfo = staffAdditionInfoService.getStaffAdditionInfoByWxOpenId(openId);
                        if (staffAdditionInfo == null) {
                            staffAdditionInfo = staffAdditionInfoService.getStaffAdditionInfoByWxUserId(userId);
                        }

                        //未绑定则跳转到绑定页面
                        if (staffAdditionInfo == null) {
                            if (!targetURL.contains("/wx/verifyBind")
                                    && !targetURL.contains("/wx/openToBind")) {
                                response.sendRedirect(WebParam.NOTIFY_IP + "/wx/openToBind?targetURL=" + targetURL);
                                return false;
                            }
                        } else {
                            //已绑定则保存用户信息到session
                            Staff staff = staffService.getStaffById(staffAdditionInfo.getStaffId());
                            staff.setDuty(dutyService.getDutyByStaffId(staff.getId()));
                            staff.setRole(roleService.getRolesByStaff(staff.getId()));
                            String token = WebParam.USE_KEY + Base64.encode(UUID.randomUUID().toString()).toString();
                            staff.setToken(token);
                            redisService.setKey(token, staff);
                            redisService.expireKey(token, (long) 60 * 60 * 8, TimeUnit.SECONDS);
                            session.setAttribute(Constant.SESSION_KEY, staff);
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean getWxApprove(HttpSession session) {
        return session == null || (session.getAttribute("SESSION_OPENID") == null && session.getAttribute("SESSION_USERID") == null);
    }

    /**
     * 获取微信code
     *
     * @param
     * @return
     */
    public static String getRequestCodeUrl() {
        String codeUrl = GET_CODE_URL.replace("APPID", CompanyWxUtil.APPID);
        codeUrl = codeUrl.replace("REDIRECT_URI", URLEncoder.encode(MEMBERINIT_URL));
        codeUrl = codeUrl.replace("SCOPE", CompanyWxUtil.SCOPE_CONFIRM);
        codeUrl = codeUrl.replace("STATE", CompanyWxUtil.RESPONSE_STATE_OK);
        // codeUrl = codeUrl.replace("wechat_redirect", URLEncoder.encode(FAIL_URL));
        return codeUrl;
    }


}
