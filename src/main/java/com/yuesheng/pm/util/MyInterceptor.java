package com.yuesheng.pm.util;

import com.alibaba.fastjson.JSON;
import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/27 0027.
 * spring-mvc 拦截器
 *
 * @author XiaoSong
 * @date 2016/04/27
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    private static Map<String, String> roleMap;
    private static Map<String, String> res = new HashMap(16);

    private RedisService redisService;

    public MyInterceptor(@Autowired RedisService redisService) {
        this.redisService = redisService;
    }

    static {
        roleMap = WebParam.rolePermissions;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws IOException {
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("*"));
        String userAgent = httpServletRequest.getHeader("user-agent").toLowerCase();
        //判断是否是微信客户端请求,如果是则不管，由微信拦截器来处理流程
        if (userAgent.indexOf("micromessenger") > -1) {
            return true;
        }

        HttpSession session = httpServletRequest.getSession();
        Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
        if (staff == null) {
            String token = httpServletRequest.getParameter("token");
            if (StringUtils.isNotBlank(token)) {
                Object os = redisService.getValue(token);
                if (os != null) {
                    staff = (Staff) os;
                    session.setAttribute(Constant.SESSION_KEY, staff);
                    return true;
                } else {
                    httpServletResponse.sendRedirect("/login");
                }
            } else {
                httpServletResponse.sendRedirect("/login");
            }

            return false;
        } else if (o instanceof HandlerMethod) {
//            判断重复提交请求
            HandlerMethod handlerMethod = (HandlerMethod) o;
            Method method = handlerMethod.getMethod();
            SameUrlData annotation = method.getAnnotation(SameUrlData.class);
            if (annotation != null) {
                if (repeatDataValidator(httpServletRequest)) {
//                    如果重复相同数据
                    return false;
                }
            }

            /**
             * 权限验证
             */
            return urlIsExist(httpServletRequest, httpServletResponse, httpServletRequest.getServletPath(), staff);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
//        System.out.println("postHandle----------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        System.out.println("afterCompletion----------------2-1-2-2-1-1-2-1-2-1"+e);
    }

    /**
     * 验证同一个url数据是否相同提交  ,相同返回true
     *
     * @param httpServletRequest
     * @return
     */
    public boolean repeatDataValidator(HttpServletRequest httpServletRequest) {
        String params = JSON.toJSONString(httpServletRequest.getParameterMap());
        String url = httpServletRequest.getRequestURI();
        Map<String, String> map = new HashMap(16);
        map.put(url, params);
        String nowUrlParams = map.toString();

        Object preUrlParams = httpServletRequest.getSession().getAttribute("repeatData");
        if (preUrlParams == null) {
//            如果上一个数据为null,表示还没有访问页面
            httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
            return false;
        } else {     //否则，已经访问过页面
            if (preUrlParams.toString().equals(nowUrlParams)) {
                //如果上次url+数据和本次url+数据相同，则表示城府添加数据
                return true;
            } else {
                //如果上次 url+数据 和本次url加数据不同，则不是重复提交
                httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
                return false;
            }

        }
    }

    /**
     * 验证地址是否存在权限管理
     *
     * @param httpServletRequest
     * @param mapping
     * @param staff
     * @return
     */
    public static boolean urlIsExist(HttpServletRequest httpServletRequest, HttpServletResponse response, String mapping, Staff staff) throws IOException {
        String pageName = httpServletRequest.getParameter("pageName");
        String url = WebParam.urlMap.get(pageName);
        if (pageName != null && url != null) {
//            主菜单验证
            return rightVerify(pageName, staff, response);
        } else {
//            url验证,先判断该地址是否在权限管理中
            if (WebParam.urlMap.get(mapping) != null) {
                return rightVerify(mapping, staff, response);
            } else {
                String[] temp = mapping.split("/");
                if (temp.length >= 4) {
//                    去掉多级的
                    mapping = "/" + temp[1] + "/" + temp[2];
                    if (WebParam.urlMap.get(mapping) != null) {
                        return rightVerify(mapping, staff, response);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 验证权限
     *
     * @param mapping  请求的mapping地址
     * @param staff    职员
     * @param response
     * @return
     * @throws IOException
     */
    private static boolean rightVerify(String mapping, Staff staff, HttpServletResponse response) throws IOException {
        Role[] roles = staff.getRole();
        if (roles == null) {
            return false;
        }
        String url2 = "";
        for (Role r : roles) {
            url2 = roleMap.get(r.getCoding());
            if (url2 != null && url2.contains(mapping)) {
                return true;
            }
        }
        noRight(response);
        return false;
    }

    /**
     * 没有权限的处理
     *
     * @param response 响应对象
     * @throws IOException
     */
    private static void noRight(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        res.clear();
        res.put("error", "没有权限");
        //设置response状态未授权，提示信息
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, JSON.toJSONString(res));
    }
}
