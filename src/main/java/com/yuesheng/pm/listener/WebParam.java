package com.yuesheng.pm.listener;

import com.yuesheng.pm.config.AppParamConfig;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Menu;
import com.yuesheng.pm.entity.Role;
import com.yuesheng.pm.service.MaterialService;
import com.yuesheng.pm.service.MenuService;
import com.yuesheng.pm.service.RoleService;
import com.yuesheng.pm.service.WordModuleService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.ThreadManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 96339 on 2016/12/24.
 *
 * @author XiaoSong
 * @date 2016/12/24
 */
@WebListener
public class WebParam implements ServletContextListener {
    public static String USE_KEY = "USER_KEY_";
    /**
     * 项目根路径
     */
    public static String webRootPath;
    /**
     * 项目资源路径
     */
    public static String assetsPath;
    /**
     * 项目临时目录
     */
    public static String TEMP_FOLDER;
    /**
     * 邮件服务器地址
     */
    public static String HOST;
    /**
     * 邮件编码
     */
    public static String ENCODING;
    /**
     * 发送人邮箱
     */
    public static String SEND_EMAIL;
    /**
     * 发送人密码
     */
    public static String SEND_PASSWD;
    /**
     * 发送人别名
     */
    public static String NICE_NAME;
    /**
     * 消息推送延迟时间（毫秒）
     */
    public static String NOTIFY_HOUR;
    /**
     * 消息推送ip服务器地址
     */
    public static String NOTIFY_IP;
    /**
     * 邮箱临时存放的地址
     */
    public static String EMAIL_FOLDER;
    /**
     * 办公用品材料编码长度
     */
    public static int WORK_M_L;
    /**
     * 是否debug模式，0=是，1=否
     */
    public static boolean SYSTEM_IS_DEBUG = false;
    /**
     * ftp服务器密码
     */
    public static String FTP_PASSWD = "ZHMKJ85516128FORsongCREATE";
    /**
     * ftp服务器用户名
     */
    public static String FTP_USER = "user_read";
    /**
     * ftp服务器地址
     */
    public static String FTP_ADDRESS = "192.168.3.253";
    /**
     * ftp服务器根目录
     */
    public static String FTP_ROOT_FOLDER = "/1001/Accessories";
    /**
     * 是否关闭静态审批集群分发
     */
    public static boolean SYSTEM_CLOSE_APPROVE_CLOSE = true;
    /**
     * 系统log路径
     */
    public static String SYSTEM_LOG_PATH;
    /**
     * body背景色
     */
    public static String SYSTEM_MENU_BG;

    /**
     * 角色对应的菜单集合
     */
    public static Map<String, String> rolePermissions = new HashMap(16);
    /**
     * 权限管理的地址集合，value=地址所对应的id
     */
    public static Map<String, String> urlMap = new HashMap(16);
    /**
     * 系统角色集合
     */
    public static List<Role> roleList;
    /**
     * 前端项目上下文
     */
    public static String VUETIFY_BASE = "/vuetify";
    /**
     * 使用sftp管理文件
     */
    public static Boolean SFTP = false;

    public static WebApplicationContext webApplicationContext;

    public AppParamConfig appParamConfig;

    public static String getHOST() {
        return HOST;
    }


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("开始初始化...");
        //获取项目根路径
        webRootPath = servletContextEvent.getServletContext().getRealPath("/");
        /*
         * 获取service对象，此处不能使用自动注解注入，需手动注入
         */
        webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        appParamConfig = webApplicationContext.getBean(AppParamConfig.class);


        initParam();

        System.out.print("系统根目录初始化完成：" + webRootPath);
        System.out.println(",系统资源目录初始化完成:" + assetsPath + "\n系统推送邮箱用户名:" + SEND_EMAIL + "\n密码:" + URLEncoder.encode(SEND_PASSWD) + ",\n邮箱别名：" + NICE_NAME + "，邮箱编码：" + ENCODING);


        System.out.println("系统数据自检开始");
//        获取材料服务
        MaterialService materialService = (MaterialService) webApplicationContext.getBean("materialService");
        WordModuleService wms = (WordModuleService) webApplicationContext.getBean("wordModuleService");
        Folder folder = materialService.folderExist(Constant.MATER_FOLDER_GENERATE);
        if (folder == null) {
            folder = new Folder();
            folder.setId(Constant.MATER_FOLDER_GENERATE);
            folder.setName("系统自动生成");
            folder.setParent(Constant.MATER_FOLDER_GENERATE);
            folder.setCode("");
            materialService.addFolder(folder);
        }
        /*
         * 判断是否添加系统默认目录
         */
        Folder f = wms.queryFolder(Constant.WORD_FOLDER_GENERATE);
        if (f == null) {
            f = new Folder();
            f.setId(Constant.WORD_FOLDER_GENERATE);
            f.setName("系统自动生成");
            f.setParent("");
            f.setRootId(f.getId());
            wms.insertFolder(f);
        }
        System.out.println("材料库自检完成,办文模板目录初始化完成，开始加载系统角色&权限集合....");

        /*
         * 角色处理
         * 获取角色服务
         */
        RoleService roleService = (RoleService) webApplicationContext.getBean("roleService");
        roleList = roleService.getRoles(null);
        MenuService menuService = (MenuService) webApplicationContext.getBean("menuService");
        String roleId = "";
        StringBuffer buffer = new StringBuffer();
        for (Role role : roleList) {
//            遍历角色对应的所有权限
            roleId = role.getCoding();
            for (Menu menu : menuService.getMenuByRole(roleId)) {
                if (menu != null) {
                    buffer.append(menu.getUrl());
                    buffer.append(",");
                }
            }
            rolePermissions.put(roleId, buffer.toString());
            buffer.setLength(0);
        }

        /*
         * 有权限的url集合
         */
        List<Menu> menuList = menuService.getMenus(null);
        for (Menu m : menuList) {
            urlMap.put(m.getUrl(), m.getId());
        }
        System.out.println("权限自检完成，启动成功");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("服务停止,销毁定时任务");
        try {
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            defaultScheduler.shutdown(true);
            String[] names = webApplicationContext.getBeanDefinitionNames();
            ((ThreadManager) webApplicationContext.getBean("threadManager")).shutdown();
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        } finally {
            roleList = null;
            rolePermissions = null;
            webRootPath = null;
            assetsPath = null;
        }
        System.gc();
    }

    /**
     * 追加角色到权限中
     *
     * @param role
     */
    public static void insertRole(Role role, Menu menu) {
        if (menu == null) {
            return;
        }
//        判断该角色是否存在
        String temp = rolePermissions.get(role.getCoding());
        if (temp != null) {
//            存在
            temp += menu.getUrl() + ",";
        } else {
//            不存在，添加到map中
            temp = menu.getUrl() + ",";
        }
        rolePermissions.put(role.getCoding(), temp);
    }

    /**
     * 从菜单中删除该角色
     *
     * @param role 角色对象
     */
    public static void deleteRole(Role role, Menu menu) {
        if (role.getCoding() != null && menu != null) {
            String temp = rolePermissions.get(role.getCoding());
            if (org.apache.commons.lang3.StringUtils.isNotBlank(temp) && StringUtils.isNotBlank(menu.getUrl())) {
                temp = temp.replace(menu.getUrl(), "");
                rolePermissions.put(role.getCoding(), temp);
            }
        }
    }

    public void initParam() {
        assetsPath = appParamConfig.getAssetsPath();

        SEND_EMAIL = appParamConfig.getSEND_EMAIL();
        HOST = appParamConfig.getHOST();
        ENCODING = appParamConfig.getENCODING();
        EMAIL_FOLDER = appParamConfig.getEMAIL_FOLDER();
        SEND_PASSWD = appParamConfig.getSEND_PASSWD();
        NICE_NAME = appParamConfig.getNICE_NAME();

        NOTIFY_HOUR = appParamConfig.getNOTIFY_HOUR();
        NOTIFY_IP = appParamConfig.getNOTIFY_IP();

        WORK_M_L = appParamConfig.getWORK_M_L();

        SYSTEM_IS_DEBUG = appParamConfig.isSYSTEM_IS_DEBUG();
        SYSTEM_CLOSE_APPROVE_CLOSE = appParamConfig.isSYSTEM_CLOSE_APPROVE_CLOSE();
        SYSTEM_LOG_PATH = appParamConfig.getSYSTEM_LOG_PATH();
        SYSTEM_MENU_BG = appParamConfig.getSYSTEM_MENU_BG();

        TEMP_FOLDER = appParamConfig.getTEMP_FOLDER();

        File file = new File(WebParam.webRootPath + EMAIL_FOLDER);
        if (!file.exists()) {
            System.out.println("临时文件夹不存在，创建" + file.mkdir());
        }

        FTP_ADDRESS = appParamConfig.getFTP_ADDRESS();
        FTP_PASSWD = appParamConfig.getFTP_PASSWD();
        ;
        FTP_USER = appParamConfig.getFTP_USER();
        FTP_ROOT_FOLDER = appParamConfig.getFTP_ROOT_FOLDER();

    }

}
