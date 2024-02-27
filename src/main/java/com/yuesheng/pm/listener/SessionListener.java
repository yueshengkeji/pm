package com.yuesheng.pm.listener;

import com.yuesheng.pm.restapi.ManagerLogin;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.util.Constant;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by 96339 on 2017/5/17 session监听器.
 *
 * @author XiaoSong
 * @date 2017/05/17
 */
public class SessionListener implements HttpSessionListener {

    @Autowired
    private RedisService redisService;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    /**
     * 销毁时调用
     *
     * @param httpSessionEvent HttpSession事件源
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Map<String, HttpSession> staffMap = ManagerLogin.getStaffList();
        HttpSession session = httpSessionEvent.getSession();
        try {
            Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
            //没有（别处登陆）强制退出信息，清除该用户所在在map中的值
            if (session.getAttribute(Constant.SESSTIONLISTENER_INFO) == null) {
                if (staff != null) {
                    staffMap.remove(staff.getId());
                    redisService.deleteKey(staff.getToken());
                }
            }
        } catch (Exception ignored) {
            Logger.getLogger("ERROR").info("sessionDestroyed error:" + session + ",Exception:" + ignored.getMessage());
        }
    }
}
