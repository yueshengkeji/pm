package com.yuesheng.pm.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.yuesheng.pm.config.DingTalkUrl;
import com.taobao.api.ApiException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

/**
 * @ClassName DingTalkAccessTokenUtil
 * @Description
 * @Author ssk
 * @Date 2022/7/20 0020 10:36
 */
public class DingTalkAccessTokenUtil {
    /**
     * 在使用access_token时，请注意：
     * access_token的有效期为7200秒（2小时），有效期内重复获取会返回相同结果并自动续期，过期后获取会返回新的access_token。
     * 开发者需要缓存access_token，用于后续接口的调用。因为每个应用的access_token是彼此独立的，所以进行缓存时需要区分应用来进行存储。
     * 不能频繁调用接口，否则会受到频率拦截。
     *
     * @param appKey
     * @param appSecret
     * @return
     */
    public static String getDingTalkAccessToken(String appKey, String appSecret) {

        DefaultDingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.GET_ACCESS_TOKEN_URL);
        OapiGettokenRequest request = new OapiGettokenRequest();

        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod(HttpMethod.GET.name());

        try {
            OapiGettokenResponse response = client.execute(request);
            if (response.isSuccess()) {
                return response.getAccessToken();
            } else {
                throw new DingTalkException(response.getErrorCode(), response.getErrmsg());
            }
        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
            e.printStackTrace();
            throw new DingTalkException(e.getErrCode(), e.getErrMsg());
        }
    }
}
