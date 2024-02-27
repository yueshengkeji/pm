package com.yuesheng.pm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appParamConfig")
public class AppParamConfig {
    /**
     * 项目资源路径
     */
    @Value("${Assets.FTPFolder}")
    private String assetsPath;
    /**
     * 项目临时目录
     */
    @Value("${System.tempFolder}")
    private String TEMP_FOLDER;
    /**
     * 邮件服务器地址
     */
    @Value("${Email.hostName}")
    private String HOST;
    /**
     * 邮件编码
     */
    @Value("${Email.ENCODING}")
    private String ENCODING;
    /**
     * 发送人邮箱
     */
    @Value("${Email.userName}")
    private String SEND_EMAIL;
    /**
     * 发送人密码
     */
    @Value("${Email.passwd}")
    private String SEND_PASSWD;
    /**
     * 发送人别名
     */
    @Value("${Email.nice}")
    private String NICE_NAME;
    /**
     * 消息推送延迟时间（毫秒）
     */
    @Value("${Notify.hour}")
    private String NOTIFY_HOUR;
    /**
     * 消息推送ip服务器地址
     */
    @Value("${Notify.ip}")
    private String NOTIFY_IP;
    /**
     * 邮箱临时存放的地址
     */
    @Value("${Email.folder}")
    private String EMAIL_FOLDER;
    /**
     * 办公用品材料编码长度
     */
    @Value("${Article.codelength}")
    private int WORK_M_L;
    /**
     * 是否debug模式，0=是，1=否
     */
    @Value("${System.debug}")
    private boolean SYSTEM_IS_DEBUG = false;
    /**
     * ftp服务器密码
     */
    @Value("${Ftp.Passwd}")
    private String FTP_PASSWD = "ZHMKJ85516128FORsongCREATE";
    /**
     * ftp服务器用户名
     */
    @Value("${Ftp.UserName}")
    private String FTP_USER = "user_read";
    /**
     * ftp服务器地址
     */
    @Value("${Ftp.Addr}")
    private String FTP_ADDRESS = "192.168.3.253";
    /**
     * ftp服务器根目录
     */
    @Value("${Ftp.Root}")
    private String FTP_ROOT_FOLDER = "/1001/Accessories";
    /**
     * 是否关闭静态审批集群分发
     */
    @Value("${System.closeApproveGroup}")
    private boolean SYSTEM_CLOSE_APPROVE_CLOSE = true;
    /**
     * 系统log路径
     */
    @Value("${System.log}")
    private String SYSTEM_LOG_PATH;
    /**
     * body背景色
     */
    @Value("${System.menuBg}")
    private String SYSTEM_MENU_BG;

    public String getAssetsPath() {
        return assetsPath;
    }

    public void setAssetsPath(String assetsPath) {
        this.assetsPath = assetsPath;
    }

    public String getTEMP_FOLDER() {
        return TEMP_FOLDER;
    }

    public void setTEMP_FOLDER(String TEMP_FOLDER) {
        this.TEMP_FOLDER = TEMP_FOLDER;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public String getENCODING() {
        return ENCODING;
    }

    public void setENCODING(String ENCODING) {
        this.ENCODING = ENCODING;
    }

    public String getSEND_EMAIL() {
        return SEND_EMAIL;
    }

    public void setSEND_EMAIL(String SEND_EMAIL) {
        this.SEND_EMAIL = SEND_EMAIL;
    }

    public String getSEND_PASSWD() {
        return SEND_PASSWD;
    }

    public void setSEND_PASSWD(String SEND_PASSWD) {
        this.SEND_PASSWD = SEND_PASSWD;
    }

    public String getNICE_NAME() {
        return NICE_NAME;
    }

    public void setNICE_NAME(String NICE_NAME) {
        this.NICE_NAME = NICE_NAME;
    }

    public String getNOTIFY_HOUR() {
        return NOTIFY_HOUR;
    }

    public void setNOTIFY_HOUR(String NOTIFY_HOUR) {
        this.NOTIFY_HOUR = NOTIFY_HOUR;
    }

    public String getNOTIFY_IP() {
        return NOTIFY_IP;
    }

    public void setNOTIFY_IP(String NOTIFY_IP) {
        this.NOTIFY_IP = NOTIFY_IP;
    }

    public String getEMAIL_FOLDER() {
        return EMAIL_FOLDER;
    }

    public void setEMAIL_FOLDER(String EMAIL_FOLDER) {
        this.EMAIL_FOLDER = EMAIL_FOLDER;
    }

    public int getWORK_M_L() {
        return WORK_M_L;
    }

    public void setWORK_M_L(int WORK_M_L) {
        this.WORK_M_L = WORK_M_L;
    }

    public boolean isSYSTEM_IS_DEBUG() {
        return SYSTEM_IS_DEBUG;
    }

    public void setSYSTEM_IS_DEBUG(boolean SYSTEM_IS_DEBUG) {
        this.SYSTEM_IS_DEBUG = SYSTEM_IS_DEBUG;
    }

    public String getFTP_PASSWD() {
        return FTP_PASSWD;
    }

    public void setFTP_PASSWD(String FTP_PASSWD) {
        this.FTP_PASSWD = FTP_PASSWD;
    }

    public String getFTP_USER() {
        return FTP_USER;
    }

    public void setFTP_USER(String FTP_USER) {
        this.FTP_USER = FTP_USER;
    }

    public String getFTP_ADDRESS() {
        return FTP_ADDRESS;
    }

    public void setFTP_ADDRESS(String FTP_ADDRESS) {
        this.FTP_ADDRESS = FTP_ADDRESS;
    }

    public String getFTP_ROOT_FOLDER() {
        return FTP_ROOT_FOLDER;
    }

    public void setFTP_ROOT_FOLDER(String FTP_ROOT_FOLDER) {
        this.FTP_ROOT_FOLDER = FTP_ROOT_FOLDER;
    }

    public boolean isSYSTEM_CLOSE_APPROVE_CLOSE() {
        return SYSTEM_CLOSE_APPROVE_CLOSE;
    }

    public void setSYSTEM_CLOSE_APPROVE_CLOSE(boolean SYSTEM_CLOSE_APPROVE_CLOSE) {
        this.SYSTEM_CLOSE_APPROVE_CLOSE = SYSTEM_CLOSE_APPROVE_CLOSE;
    }

    public String getSYSTEM_LOG_PATH() {
        return SYSTEM_LOG_PATH;
    }

    public void setSYSTEM_LOG_PATH(String SYSTEM_LOG_PATH) {
        this.SYSTEM_LOG_PATH = SYSTEM_LOG_PATH;
    }

    public String getSYSTEM_MENU_BG() {
        return SYSTEM_MENU_BG;
    }

    public void setSYSTEM_MENU_BG(String SYSTEM_MENU_BG) {
        this.SYSTEM_MENU_BG = SYSTEM_MENU_BG;
    }
}
