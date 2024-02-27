package com.yuesheng.pm.util;

import com.yuesheng.pm.listener.WebParam;

/**
 * @program: kailismart.com
 * @description: 获取配置常量地址
 * @author: zcj
 * @create: 2019-06-11 09:25
 **/
public class DataUtil {

    /**
     * 获取企业微信通知详情URL
     * @return  企业微信通知详情URL
     */
    public static String getNotifyWxUrl() {
        return WebParam.NOTIFY_IP;
    }
}
