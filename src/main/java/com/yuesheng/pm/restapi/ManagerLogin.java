package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nimbusds.jose.util.Base64;
import com.yuesheng.pm.entity.FlowApprove;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.service.DutyService;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.service.RoleService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.*;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by XiaoSong on 2016-08-05 后台管理控制器.
 *
 * @author XiaoSong
 * @date 2016/08/05
 */
@Controller
public class ManagerLogin {
    private static final Map<String, HttpSession> STAFF_LIST = new ConcurrentHashMap<>();
    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/loginByRectUrl/{rectUrl}")
    public ModelAndView verifyRect(Staff staff, @PathVariable String rectUrl, HttpSession session) {
        ModelAndView mav = new ModelAndView("/login");
        try {
            if (staff == null || StringUtils.isBlank(staff.getName())) {
                return mav;
            }
            String comId = staff.getComId();
            Map<String, Object> verResult = verify(staff);
            String loginCode = (String) verResult.get("loginCode");
            staff = (Staff) verResult.get("staff");
            if ("1".equals(loginCode)) {
                staff.setComId(comId);
                staff.setDuty(dutyService.getDutyByStaffId(staff.getId()));
                staff.setRole(roleService.getRolesByStaff(staff.getId()));
                staff.setLastDate(DateUtil.format(DateUtil.getNowDate(), "yyyy-MM-dd HH:mm:ss"));
                staffService.updateLoginTime(staff);
                STAFF_LIST.put(staff.getId(), session);
                String token = WebParam.USE_KEY + Base64.encode(UUID.randomUUID().toString()).toString();
                staff.setToken(token);
                redisService.setKey(token, staff);
                redisService.expireKey(token, (long) 60 * 60 * 8, TimeUnit.SECONDS);
                session.setMaxInactiveInterval(36000);
                session.setAttribute(Constant.SESSION_KEY, staff);
            } else {
                mav.addObject("info", verResult.get("info"));
                return mav;
            }
        } catch (IOException e) {
            LoggerFactory.getLogger(ManagerLogin.class).error(e.getLocalizedMessage());
            return mav;
        } catch (Exception e) {
            LoggerFactory.getLogger(ManagerLogin.class).error(e.getLocalizedMessage());
            return mav;
        }
        mav.setViewName("redirect:/managent/getPage?pageName=" + rectUrl.replaceAll("&", "/"));
        return mav;
    }

    /**
     * 用户登录
     *
     * @param staff
     * @return
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping("/login")
    public ModelAndView verify(Staff staff, HttpSession session) {
        ModelAndView mav = new ModelAndView("/login");
        try {
            if (staff == null || StringUtils.isBlank(staff.getName())) {
                mav.addObject("info", "");
                return mav;
            }
            String comId = staff.getComId();
            Map<String, Object> verResult = verify(staff);
            String loginCode = (String) verResult.get("loginCode");
            staff = (Staff) verResult.get("staff");
            if ("1".equals(loginCode)) {
                staff.setComId(comId);
//                保存用户信息到session中
                staff.setDuty(dutyService.getDutyByStaffId(staff.getId()));
                staff.setRole(roleService.getRolesByStaff(staff.getId()));
                staff.setLastDate(DateUtil.format(DateUtil.getNowDate(), "yyyy-MM-dd HH:mm:ss"));
                staffService.updateLoginTime(staff);
                STAFF_LIST.put(staff.getId(), session);
                String token = WebParam.USE_KEY + Base64.encode(UUID.randomUUID().toString()).toString();
                staff.setToken(token);
                redisService.setKey(token, staff);
                redisService.expireKey(token, (long) 60 * 60 * 8, TimeUnit.SECONDS);
                //                两小时未活动过期
                session.setMaxInactiveInterval(36000);
                session.setAttribute(Constant.SESSION_KEY, staff);
                mav.addObject("staff", staff);
            } else {
                mav.addObject("info", verResult.get("info"));
                return mav;
            }
        } catch (IOException e) {
            LoggerFactory.getLogger(ManagerLogin.class).error(e.getLocalizedMessage());
            return mav;
        } catch (Exception e) {
            LoggerFactory.getLogger(ManagerLogin.class).error(e.getLocalizedMessage());
            return mav;
        }
        mav.setViewName("redirect:/managent/getPage?pageName=managerIndex");
        return mav;
    }

    @GetMapping("/")
    public ModelAndView index(HttpSession httpSession) {
        Object user = httpSession.getAttribute(Constant.SESSION_KEY);
        ModelAndView mav = new ModelAndView();
        if (Objects.isNull(user)) {
            mav.addObject("info", "");
            mav.setViewName("/login");
        } else {
            mav.setViewName("managerIndex");
        }
        return mav;
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
        if (StringUtils.isBlank(staff.getPasswd())) {
            staff.setPasswd("");
        }
        try {
            staff.setPasswd(new RSAEncrypt().decrypt(staff.getPasswd()));
        } catch (Exception e) {
            // return new ResponseModel(500, e.getMessage());
        }
        Staff temp = staffService.loginIn(staff.getName(), AESEncrypt.encryptBASE64(staff.getPasswd().getBytes()));
        if (temp == null)        //不加密登陆
        {
            temp = staffService.loginIn(staff.getName(), staff.getPasswd());
        }
        staff = temp;
        if ("1".equals(loginCode)) {
            if (staff.getId() == null || "".equals(staff.getId())) {      //兼容pm2
                staff = staffService.login(staff.getName());
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

    private void staffDb(Staff staff) {
        staff.setDbName(getDsName(staff.getComId()));
    }

    private String getDsName(String comId) {
        if (comId == null) {
            return "";
        }
        switch (comId) {
            case "1001":
                return "dataSource_zmkj";
            case "1004":
                return "dataSource_lyg";
            default:
                break;
        }
        return "dataSource_zmkj";
    }

    /**
     * 移除session对象
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logOut(HttpSession session, String info) {
        ModelAndView mav = new ModelAndView("login");
        try {
            loginOut(session, info);
            mav.addObject("info", info);
        } catch (Exception exception) {

        }
        return mav;
    }

    public static void loginOut(HttpSession session, String info) {
        try {
            Staff staff = (Staff) session.getAttribute(Constant.SESSION_KEY);
            STAFF_LIST.remove(staff.getId());
            session.setAttribute("info", info);
            session.invalidate();
        } catch (Exception e) {

        }
    }

    /**
     * 移除用户登录信息
     */
    private static void removeSession() {

    }

    /**
     * 获取在线用户
     *
     * @return
     */
    @RequestMapping("/managent/getInUser")
    @ResponseBody
    public List<Staff> getInUser() {
        Iterator<String> iterator = STAFF_LIST.keySet().iterator();
        String uid;
        HttpSession httpSession;
        List<Staff> staffs = new ArrayList<>();
        while (iterator.hasNext()) {
            uid = iterator.next();
            httpSession = STAFF_LIST.get(uid);
            try {
                staffs.add((Staff) httpSession.getAttribute(Constant.SESSION_KEY));
            } catch (Exception e) {
                try {
                    httpSession.invalidate();
                } catch (Exception e1) {
                    iterator.remove();
                }

            }
        }
        return staffs;
    }

    /**
     * 获取指定的视图
     *
     * @return
     */
    @RequestMapping("/getPageByTest/{pageName}")
    public String getViewByTest(@PathVariable String pageName) {
        return pageName;
    }

    public static Map<String, HttpSession> getStaffList() {
        return STAFF_LIST;
    }

    /**
     * 给所有用户发送消息
     */
    @RequestMapping("/managent/sendMsgToUsers")
    @ResponseBody
    public void sendMsgToUsers(String title, String content, String passwd) {
        if ("XSYTOW..".equals(passwd)) {
            Map<String, Object> msg = new HashMap<>(16);
            Map<String, String> data = new HashMap(16);
            data.put("title", title);
            data.put("msg", content);

            msg.put("type", 1);
            msg.put("data", data);

            Iterator<String> kets = STAFF_LIST.keySet().iterator();
            while (kets.hasNext()) {
                String key = kets.next();
                try {
                    MyWebSocketHandle.sendMeg(key, JSON.toJSONString(msg, SerializerFeature.DisableCircularReferenceDetect));
                } catch (IllegalStateException e) {
                    //忽略session过期异常
                }
            }
        }
    }

    /**
     * 创建session的key
     *
     * @param session
     * @param k
     * @return
     */
    @RequestMapping("/emailAuth")
    @ResponseBody
    public String emailAuth(HttpSession session, String k) {
        if ("emailkey".equals(new String(AESEncrypt.decode(k)))) {
            Staff staff = new Staff();
            staff.setId("email_system_auth");
            staff.setName("邮件审批系统系统");
            String token = WebParam.USE_KEY + Base64.encode(UUID.randomUUID().toString()).toString();
            staff.setToken(token);
            redisService.setKey(token, staff);
            redisService.expireKey(token, 900, TimeUnit.SECONDS);
            session.setAttribute(Constant.SESSION_KEY, staff);
            return "ok";
        }
        return "";
    }

    /**
     * 接收分布式主服务审批文件变化
     */
    @RequestMapping("/flowApprove")
    @ResponseBody
    public String flowApprove(String fa, String fileName) {
        try {
            ApproveInfoTask.approveMainDispose(JSON.parseObject(fa, FlowApprove.class), fileName);
        } catch (IOException e) {
            return e.getMessage();
        }
        return "ok";
    }


    /**
     * 读取日志
     *
     * @param searchText 检索串
     * @return
     */
    @RequestMapping("/managent/getLogByStr")
    @ResponseBody
    public Map<String, Object> readLogByStr(String searchText, String type, Integer pageNumber, Integer pageSize, String day) {
        Map<String, Object> result = new HashMap<>(16);
        List<String> logs = null;
        if ("0".equalsIgnoreCase(type)) {
            //异常信息
            logs = LogReadUtils.getLogByError(searchText, day);
        } else {
            //操作日志信息
            if (searchText == null) {
                logs = LogReadUtils.getLogByDay("".equals(day) ? null : day, pageNumber, pageSize);
            } else {
                logs = LogReadUtils.getLogByLine(searchText, day, pageNumber, pageSize);
            }
        }
        try {
            result.put("rows", logs);
            result.put("total", logs.size());
        } catch (NullPointerException e) {
            result.put("rows", logs);
            result.put("total", 0);
        }
        return result;
    }
}
