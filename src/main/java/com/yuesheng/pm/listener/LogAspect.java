package com.yuesheng.pm.listener;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.SystemLog;
import com.yuesheng.pm.service.SystemLogService;
import com.yuesheng.pm.util.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private ThreadManager threadManager;
    @Autowired
    private SystemLogService logService;
    @Pointcut("@annotation(io.swagger.v3.oas.annotations.Operation)")
    public void logPointCut() {

    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    @AfterThrowing(pointcut = "logPointCut()",throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint,Exception e){
        handleErrorLog(joinPoint,e);
    }

    protected void handleErrorLog(JoinPoint joinPoint,Exception e){
        handleLog(joinPoint,e, e.getMessage());
//        SystemLog operLog = new SystemLog();
//        operLog.setType("ERROR");
//        HttpServletRequest request = getRequest();
//        StringBuffer sb = new StringBuffer();
//        // 请求参数
//        if (request instanceof RequestWrapper) {
//            RequestWrapper rw = (RequestWrapper) request;
//            sb.append(rw.getBody());
//        }
//        operLog.setIp(getIpAddr(request));
//        setRequestParam(request, sb);
//        operLog.setParams(sb.toString());
//        // 设置请求方式
//        operLog.setMethod(ServletUtils.getRequest().getMethod());
//        // 处理设置注解上的参数
//        operLog.setResult(e.getMessage());
//        threadManager.execute(new TimerTask() {
//            @Override
//            public void run() {
//                operLog.setDatetime(DateUtil.getDatetime());
//                logService.insert(operLog);
//            }
//        });
    }

    protected Staff getCurrentUser() {
        HttpSession session = getRequest().getSession();
        return (Staff) session.getAttribute(Constant.SESSION_KEY);
    }

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Operation controllerApi = getAnnotationLog(joinPoint);
            if (controllerApi == null) {
                return;
            }
            // 获取当前的用户
            Staff currentUser = getCurrentUser();

            // *========数据库日志=========*//
            SystemLog operLog = new SystemLog();
            operLog.setType("INFO");
            HttpServletRequest request = getRequest();
            StringBuffer sb = new StringBuffer();
            // 请求参数
            if (request instanceof RequestWrapper) {
                RequestWrapper rw = (RequestWrapper) request;
                sb.append(rw.getBody());
            }
            operLog.setIp(getIpAddr(request));
            setRequestParam(request, sb);
            operLog.setParams(sb.toString());
            // 返回参数
            operLog.setResult(StrUtils.toString(jsonResult));
            operLog.setUrl(getRequest().getRequestURI());
            if (currentUser != null) {
                operLog.setUserName(currentUser.getName());
                operLog.setUserId(currentUser.getId());
            }

            if (e != null) {
                operLog.setType("ERROR");
            }
            // 设置请求方式
            operLog.setMethod(getRequest().getMethod());
            // 处理设置注解上的参数
            setControllerMethodDescription(controllerApi, operLog);
            threadManager.execute(new TimerTask() {
                @Override
                public void run() {
                    operLog.setDatetime(DateUtil.getDatetime());
                    logService.insert(operLog);
                }
            });
        } catch (Exception exp) {
            // 记录本地异常日志
            exp.printStackTrace();
        }
    }

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

    private void setControllerMethodDescription(Operation api, SystemLog log) {
        if (!Objects.isNull(api)) {
            log.setTitle(api.description());
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Operation getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Operation.class);
        }
        return null;
    }
}
