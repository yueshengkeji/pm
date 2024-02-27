package com.yuesheng.pm.util;

import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.Mail;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 96339 on 2017/6/11.
 * @author XiaoSong
 * @date 2017/06/11
 */
public class MailUtil {
    private final static Logger logge = LoggerFactory.getLogger(MailUtil.class);

    public static boolean send(Mail mail) {
        // 发送email
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName(WebParam.getHOST());
            // 字符编码集的设置
            email.setCharset(WebParam.ENCODING);
            // 收件人的邮箱
            email.addTo(mail.getReceiver());
            // 发送人的邮箱 | 别名
            email.setFrom(WebParam.SEND_EMAIL,WebParam.NICE_NAME);
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(WebParam.SEND_EMAIL,WebParam.SEND_PASSWD);
            // 要发送的邮件主题
            email.setSubject(mail.getSubject());
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(mail.getMessage());
            // 发送
            email.send();
            return true;
        } catch (EmailException e) {
            logge.error(" 发送邮件到 " + mail.getReceiver()+" 失败");
            return false;
        }
    }
}
