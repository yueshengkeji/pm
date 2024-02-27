package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.SystemLog;
import com.yuesheng.pm.service.SystemLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.net.SocketException;
import java.util.Objects;

/**
 * Created by 96339 on 2017/3/15 异常处理.
 *
 * @author XiaoSong
 * @date 2017/03/15
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

    @Autowired
    private SystemLogService logService;

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        //异常消息
        String errorMsg = e.getMessage();
        e.printStackTrace();
        if (e instanceof SocketException) {
//            ignore socket error
            return null;
        } else if (e instanceof IllegalStateException) {
            //ignore this error
            return null;
        } else if (e instanceof MyBatisSystemException) {
//            数据库连接异常
            //保存日志
            saveLog(httpServletRequest, errorMsg);
            return getJsonView(e.getMessage());
        } else if (e instanceof ClassCastException) {
            //ignore this error
            //保存日志
            saveLog(httpServletRequest, errorMsg);
            return null;
        } else {
            //保存日志
            saveLog(httpServletRequest, errorMsg);
            //返回异常信息
            return getJsonView(errorMsg);
        }
    }

    private void saveLog(HttpServletRequest httpServletRequest, String errorMsg) {
        try {
            SystemLog log = new SystemLog();
            Object o = httpServletRequest.getSession().getAttribute(Constant.SESSION_KEY);
            Staff staff = null;
            if (!Objects.isNull(o)) {
                staff = (Staff) o;
                log.setUserName(staff.getName());
                log.setUserId(staff.getId());
            }
            String params = null;
            if (httpServletRequest instanceof RequestWrapper) {
                params = ((RequestWrapper) httpServletRequest).getBody();
            }
            if (Objects.isNull(params)) {
                params = "";
            }

            log.setDatetime(DateUtil.getDatetime());
            log.setIp(LogInterceptor.getIpAddr(httpServletRequest));
            log.setUrl(httpServletRequest.getRequestURL().toString());
            log.setMethod(httpServletRequest.getMethod());
            log.setParams(errorMsg + ":" + params);
            log.setType("ERROR");
            logService.insert(log);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private ModelAndView getJsonView(String errorMsg) {
        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        mv.setView(view);
        mv.addObject("code", 500);
        mv.addObject("msg", errorMsg);
        return mv;
    }
}
