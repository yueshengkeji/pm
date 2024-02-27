package com.yuesheng.pm.model;

/**
 * Created by 96339 on 2017/6/11.
 *
 * @author XiaoSong
 * @date 2017/06/11
 */
public class Mail {
    /**
     * 收件人的邮箱
     */
    private String receiver;
    /**
     * 主题
     */
    private String subject;
    /**
     * 信息(支持HTML)
     */
    private String message;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
