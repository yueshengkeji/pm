package com.yuesheng.pm.entity;

import java.io.Serializable;

/**
 * 系统日志(SystemLog)实体类
 *
 * @author xiaoSong
 * @since 2020-11-04 16:36:57
 */
public class SystemLog implements Serializable {
    private static final long serialVersionUID = -49318247879093700L;

    private Integer id;
    /**
     * 日志类型
     */
    private String type;
    /**
     * 日志时间
     */
    private String datetime;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 操作人id
     */
    private String userId;
    /**
     * 操作人姓名
     */
    private String userName;
    /**
     * 请求参数
     */
    private String params = "";
    /**
     * 客户端ip地址
     */
    private String ip;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 返回结果
     */
    private String result = "";
    private String title;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}