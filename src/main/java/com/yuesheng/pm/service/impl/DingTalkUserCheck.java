package com.yuesheng.pm.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.taobao.api.ApiException;
import com.yuesheng.pm.config.DingTalkConfig;
import com.yuesheng.pm.config.DingTalkUrl;
import com.yuesheng.pm.service.RedisService;
import com.yuesheng.pm.util.DingTalkAccessTokenUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.concurrent.TimeUnit.HOURS;

/**
 * @ClassName DingTalkUserCheck
 * @Description
 * @Author ssk
 * @Date 2022/7/20 0020 11:11
 */
@Service
public class DingTalkUserCheck {
    @Autowired
    private RedisService redisService;
    public OapiV2UserGetResponse.UserGetResponse getUserInfo(String code){
        // 1. 获取access_token
        String dingTalkAccessToken = null;
        dingTalkAccessToken = (String) redisService.getValue("dingTalk_accessToken");
        if (dingTalkAccessToken == null){
            dingTalkAccessToken = DingTalkAccessTokenUtil.getDingTalkAccessToken(DingTalkConfig.APP_KEY, DingTalkConfig.APP_SECRET);
            redisService.setKey("dingTalk_accessToken",dingTalkAccessToken);
            redisService.expireKey("dingTalk_accessToken",2,HOURS);
        }
        // 2. 获取用户ID
        String userId = getUserId(code,dingTalkAccessToken);
        // 3. 获取用户信息
        OapiV2UserGetResponse.UserGetResponse userInfoByUserId = getUserInfoByUserId(userId, dingTalkAccessToken);
        return userInfoByUserId;
    }

    //根据code获取用户Id
    public String getUserId (String code,String dingTalkAccessToken){
        String userId = null;
        DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.GET_USER_INFO_URL);
        OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
        req.setCode(code);
        try {
            OapiV2UserGetuserinfoResponse rsp = client.execute(req,dingTalkAccessToken);
            if (rsp.isSuccess()){
                userId =  rsp.getResult().getUserid();
            }
        } catch (ApiException e) {
            Logger.getLogger("mylog").error(e.getErrCode() + e.getErrMsg());
        }
        return userId;
    }

    //根据userId获取用户信息
    public OapiV2UserGetResponse.UserGetResponse getUserInfoByUserId(String userId,String dingTalkAccessToken){
        OapiV2UserGetResponse.UserGetResponse qu = null;
        DingTalkClient client = new DefaultDingTalkClient(DingTalkUrl.USER_GET_URL);
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid(userId);
        req.setLanguage("zh_CN");

        try {
            OapiV2UserGetResponse rsp = client.execute(req,dingTalkAccessToken);
            if (rsp.isSuccess()){
                qu = rsp.getResult();
            }
        } catch (ApiException e) {
            LogManager.getLogger("mylog").error(e.getErrMsg());
        }
        return qu;
    }
}
