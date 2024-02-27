package com.yuesheng.pm.model;

/**
 * 企业微信配置信息封装
 */
public class WxConfig {
    /**
     * 企业应用id
     */
    private String agentId;
    /**
     * 企业id
     */
    private String appId;
    /**
     * 企业secret
     */
    private String secret;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
