package com.yuesheng.pm.entity;

import com.yuesheng.pm.config.DingTalkConfig;

/**
 * @ClassName DingTalkNoticeMSG 钉钉链接消息
 * @Description
 * @Author ssk
 * @Date 2022/8/4 0004 8:25
 */
public class DingTalkNoticeMSG {

    /**
     * 微应用AgentID
     */
    private Long agent_id = Long.valueOf(DingTalkConfig.AGENT_ID);

    /**
     * 接收者userid列表,最大用户列表长度100 以 , 隔开  userid1,userid2
     */
    private String user_list;

    /**
     * 接收者的部门id列表，最大列表长度20,接收者是部门ID时，包括子部门下的所有用户 暂未使用
     */
    private String dept_id_list;

    /**
     * 是否发送给企业全部用户
     */
    private boolean to_all_user;

    /**
     * 消息类型
     */
    private String msgType = "link";

//    /**
//     * 图片路径
//     */
//    private String picUrl;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容描述
     */
    private String content;

    /**
     * 跳转路径
     */
    private String linkUrl;

    public String getMsgType() {
        return msgType;
    }
//
//    public String getPicUrl() {
//        return picUrl;
//    }
//
//    public void setPicUrl(String picUrl) {
//        this.picUrl = picUrl;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Long getAgent_id() {
        return agent_id;
    }

    public String getUser_list() {
        return user_list;
    }

    public void setUser_list(String user_list) {
        this.user_list = user_list;
    }

    public String getDept_id_list() {
        return dept_id_list;
    }

    public void setDept_id_list(String dept_id_list) {
        this.dept_id_list = dept_id_list;
    }

    public boolean isTo_all_user() {
        return to_all_user;
    }

    public void setTo_all_user(boolean to_all_user) {
        this.to_all_user = to_all_user;
    }

}
