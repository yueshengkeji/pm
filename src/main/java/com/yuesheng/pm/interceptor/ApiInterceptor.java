package com.yuesheng.pm.interceptor;

import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.restapi.ManagerLogin;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DataUtil;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.MyInterceptor;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class ApiInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    private static ApiInterceptor instance;

    public ApiInterceptor() {
        instance = this;
    }
    public static void putToken(String token, Object data, Long format) {
        instance.redisService.setKey(token, data);
        instance.redisService.expireKey(token, format, TimeUnit.SECONDS);
    }

    public static void removeToken(String toString) {
        instance.redisService.deleteKey(toString);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("accept-token");
        response.setHeader("Access-Control-Allow-Origin", DataUtil.getNotifyWxUrl());
        if (redisService.existsKey(token)) {
            HttpSession hs = request.getSession();
            Object key = request.getSession().getAttribute(Constant.SESSION_KEY);
            Staff staff;
            if (key == null) {
                staff = (Staff) redisService.getValue(token);
                request.getSession().setAttribute(Constant.SESSION_KEY, staff);
            } else {
                staff = (Staff) key;
            }
            if (StringUtils.equals(staff.getId(), "email_system_auth")) {
                //邮箱审批的临时session，直接返回false，并销毁该session
                try {
                    ManagerLogin.loginOut(request.getSession(), null);
                    ApiInterceptor.removeToken(token);
                } catch (Exception e) {

                }
                return false;
            }
            setLastUpdate(staff);
            return MyInterceptor.urlIsExist(request, response, request.getServletPath(), staff);
        } else if (handler instanceof HandlerMethod) {
            //免验证
            HandlerMethod handle = (HandlerMethod) handler;
            NoToken noToken = handle.getMethod().getAnnotation(NoToken.class);
            if (noToken != null && noToken.pass()) {
                return true;
            } else {
                Object user = request.getSession().getAttribute(Constant.SESSION_KEY);
                if (!Objects.isNull(user) && user instanceof Staff) {
                    return true;
                }
            }
        } else {
            Object user = request.getSession().getAttribute(Constant.SESSION_KEY);
            if (!Objects.isNull(user) && user instanceof Staff) {
                return true;
            }
        }
        if (StringUtils.isNotBlank(token)) {
            response.sendError(402, "accept-token已过期！");
        } else {
            response.sendError(402, "请在请求头中传入\"accept-token\"参数！");
        }
        return false;
    }

    private void setLastUpdate(Staff staff) {
        if (StringUtils.isNotBlank(staff.getLastLogin()) &&
                DateUtil.getOffsetMinutes(DateUtil.parse(staff.getLastLogin()), new Date()) > 30) {
            staff.setLastLogin(DateUtil.format(new Date()));
            putToken(staff.getToken(), staff, (long) 60 * 60 * 8);
        }
    }

}
