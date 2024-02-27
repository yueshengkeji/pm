package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.SystemLog;
import com.yuesheng.pm.service.SystemLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;

/**
 * Created by 96339 on 2017/8/1 .
 *
 * @author XiaoSong
 * @date 2017/08/01
 */
public class LogInterceptor implements HandlerInterceptor {
    @Autowired
    private ThreadManager threadManager;
    @Autowired
    private SystemLogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        HttpSession hs = httpServletRequest.getSession();
        if (o instanceof WebSocketHttpRequestHandler) {
//        webSocket请求
        } else if (o instanceof DefaultServletHttpRequestHandler) {

        } else if (o instanceof ResourceHttpRequestHandler) {

        } else {      //mapping请求
            String url = httpServletRequest.getServletPath();
            if (url.endsWith("getApplyPaymentMoney") || url.endsWith("loadSHM")) {
                return;
            }
            SystemLog log = new SystemLog();
            log.setIp(getIpAddr(httpServletRequest));
            log.setUrl(url);
            log.setMethod(httpServletRequest.getMethod());
            try {
                StringBuffer sb = new StringBuffer();
                if (httpServletRequest instanceof RequestWrapper) {
                    RequestWrapper rw = (RequestWrapper) httpServletRequest;
                    sb.append(rw.getBody());
                }
                setRequestParam(httpServletRequest, sb);
                log.setParams(sb.toString());
                log.setType("INFO");
                setUser(hs, log);
                threadManager.execute(new TimerTask() {
                    @Override
                    public void run() {
                        log.setDatetime(DateUtil.getDatetime());
                        logService.insert(log);
                    }
                });
            } catch (Exception ignore) {

            }
        }
    }

    private void setUser(HttpSession hs, SystemLog log) {
        Object user = hs.getAttribute(Constant.SESSION_KEY);
        if (user != null) {
            Staff s = (Staff) user;
            log.setUserId(s.getId());
            log.setUserName(s.getName());
        }
    }

    private static void setRequestParam(HttpServletRequest request, StringBuffer sb) {
        Map<String, String[]> params = request.getParameterMap();
        params.forEach((key, value) -> {
            sb.append(key + "=");
            if (!Objects.isNull(value)) {
                for (int i = 0; i < value.length; i++) {
                    sb.append(value[i]);
                }
            }
        });
    }

    private String getRequestParam(HttpServletRequest httpServletRequest) {
        StringBuffer pv = new StringBuffer();
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        Iterator<String> iterator = params.keySet().iterator();
        String name;
        String[] values;
        while (iterator.hasNext()) {
            name = iterator.next();
            values = params.get(name);
            pv.append(name);
            pv.append("=");
            if (values != null) {
                for (String temp : values) {
                    pv.append(temp);
                }
            }
            pv.append(",");
        }
        return pv.toString();
    }

    /**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
