package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.Certificate;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.service.CertificateService;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gui_lin
 * @Description 检查证书临近过期
 * 2022/1/17
 */
@Component
public class CheckOverdue {
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private RedisService redisService;

    /**
     * 检查证书临近过期
     * @param: []
     * @return:
     */
    @Scheduled(cron = "0 0 9 ? * MON-FRI")
    public void info() {
        List<String> name = new ArrayList<>();
        List<Certificate> certificateList = certificateService.selectAllCertificate(null);
        Staff staff =(Staff) redisService.getValue("sinkMsgStaff");
        String title = "更新证书消息提醒";
        String content = "";
        String status = "";
        String linkedUrl = "";
        certificateList.forEach(item -> {
            if (item.getExpirationTime() != null && item.getPushMsg() != null && item.getPushMsg() != 1){
                int time =(int) ((item.getExpirationTime().getTime() - DateFormat.parseData(DateFormat.getDate()).getTime()) / (1000*3600*24));
                if (time <= 30 && 0 < time){
                    name.add(item.getName());
                    item.setPushMsg(1);
                    certificateService.updateCertificate(item);
                }if (time < 0){
                    item.setState(0);
                    certificateService.updateCertificate(item);
                }
            }
        });
        if (name.size() != 0){
            for (int i=0; i<2 && i<name.size() ;i++){
                content = content.concat(name.get(i) + "、");
            }
            content = (String) content.subSequence(0,content.length()-1);
            content = content.concat("等,共" + name.size() + "个证书即将到期，请登录系统更新证书");
            CompanyWxUtil.sendMsg(flowNotifyService.getWxUserIdOrWxOpenId(staff),title,content,status,linkedUrl);
        }
    }
}
