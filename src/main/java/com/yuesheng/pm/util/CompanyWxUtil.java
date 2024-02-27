package com.yuesheng.pm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.ProWorkCheck;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.WxUser;
import com.yuesheng.pm.model.WxWorkCheckModel;
import com.yuesheng.pm.service.RedisService;
import org.apache.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaoSong
 * @date 2016/08/28
 */
public class CompanyWxUtil extends BaseEntity {
    /**
     * 授权access_token
     */
    private static String GET_WEBACCESSTOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    /**
     * 发送消息
     */
    private static String SEND_MSG_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    //    private static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";
    private static String TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=";

    private static String GET_WX_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 企业应用的id
     */
    public static String AGENTID = "1000002";
    /**
     * 企业ID
     */
    public static String APPID = "ww371cb85d0ae2ad3e";
    /**
     * 应用秘钥
     */
    public static String SECRET = "3S7YNOOsHIAT94WqTDdqDHw_Nxm2l9yimp4Jr4H7aqY";
    /**
     * 打卡应用secret
     */
    public static String WORK_SECRET = "NDZSpWUorNC1O1_dFQZuwmKpvmMMktyv4ubsZSrxeiM";

    /**
     * 授权方式-用户确认（可以获得相关用户信息）
     */
    public final static String SCOPE_CONFIRM = "snsapi_base";
    /**
     * 返回状态-ok
     */
    public final static String RESPONSE_STATE_OK = "OK";

    /**
     * 企业微信消息文本卡片消息推送
     *
     * @param userId    要推送的用户id
     * @param title     标题
     * @param content   文本内容
     * @param status    状态内容
     * @param linkedUrl 详情跳转地址
     */
    public static void sendMsg(String userId, String title, String content, String status, String linkedUrl) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(userId)) {
            //获取accesstoken
            String access_token = getToken();
            String time = DateFormat.getDateTime();
            String url = SEND_MSG_URL + access_token;
            String xml;
            if (StringUtils.isBlank(linkedUrl)) {
                xml = "{" +
                        "   \"touser\" :  \"" + userId + "\",\n" +
                        "   \"msgtype\" : \"textcard\",\n" +
                        "   \"agentid\" : \"" + AGENTID + "\",\n" +
                        "   \"textcard\" : {\n" +
                        "            \"title\" : \"" + title + "\",\n" +
                        "            \"description\" : \"" + content + "<div class=\\\"highlight\\\">" + status + "</div><div class=\\\"normal\\\">" + time + "</div><div class=\\\"highlight\\\">查看详情</div>\",\n" +
                        "            \"url\" : \"" + WebParam.NOTIFY_IP + "/login\",\n" +
                        "            \"btntxt\":\"查看详情\"\n" +
                        "   }\n" +
                        "}";
            } else {
                xml = "{" +
                        "   \"touser\" :  \"" + userId + "\",\n" +
                        "   \"msgtype\" : \"textcard\",\n" +
                        "   \"agentid\" : \"" + AGENTID + "\",\n" +
                        "   \"textcard\" : {\n" +
                        "            \"title\" : \"" + title + "\",\n" +
                        "            \"description\" : \"<div class=\\\"gray\\\">" + content + "</div><div class=\\\"highlight\\\">" + status + "</div><div class=\\\"normal\\\">" + time + "</div>请点击查看\",\n" +
                        "            \"url\" : \"" + linkedUrl + "\",\n" +
                        "            \"btntxt\":\"更多\"\n" +
                        "   }\n" +
                        "}";
            }
            NetRequestUtil.sendXmlPost(url, xml);
        }
    }

    public static String getToken() {
        return getToken(APPID, SECRET);
    }

    public static String getTicket(String token) {
        RedisService redisService = (RedisService) WebParam.webApplicationContext.getBean("redisService");
        Object ticket = redisService.getValue(token);
        if (Objects.isNull(ticket)) {
            LinkedHashMap param = new LinkedHashMap();
            param.put("access_token", token);
            String url = TICKET_URL + token;
            String responseStr = NetRequestUtil.sendGetRequest(url, param);
            JSONObject responseObject = JSONObject.parseObject(responseStr);
            ticket = responseObject.get("ticket");
            redisService.setKey(token, ticket);
            redisService.expireKey(token, 7000, TimeUnit.SECONDS);
        }
        return ticket.toString();
    }

    public static String getToken(String appid, String secret) {
        RedisService redisService = (RedisService) WebParam.webApplicationContext.getBean("redisService");
        String key = appid + secret;
        Object tokenCache = redisService.getValue(key);
        if (Objects.isNull(tokenCache)) {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("corpid", appid);
            params.put("corpsecret", secret);
            String responseStr = NetRequestUtil.sendGetRequest(GET_WEBACCESSTOKEN_URL, params);
            JSONObject responseObject = JSONObject.parseObject(responseStr);
            String token = (String) responseObject.get("access_token");
            redisService.setKey(key, token);
            redisService.expireKey(key, 7000, TimeUnit.SECONDS);
            return token;
        } else {
            return tokenCache.toString();
        }
    }

    public static ArrayList<ProWorkCheck> getWorkCheckData(String token, String start, String end, String userId) {

        String xml = "{" +
                "   \"opencheckindatatype\" : 3,\n" +
                "   \"starttime\" : " + DateFormat.parseData(start).getTime() / 1000 + ",\n" +
                "   \"endtime\" : " + DateFormat.parseData(end).getTime() / 1000 + ",\n" +
                "   \"useridlist\" : " + userId + "\n" +
                "}";


        String rs = NetRequestUtil.sendXmlPost("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=" + token, xml);
        HashMap map = JSONObject.parseObject(rs, HashMap.class);
        if ("0".equals(map.get("errcode").toString())) {
            ArrayList<ProWorkCheck> proWorkChecks = new ArrayList<>();
            List<WxWorkCheckModel> wxWorkCheckModels = JSONObject.parseArray(map.get("checkindata").toString(), WxWorkCheckModel.class);
            ProWorkCheck workCheck;
            for (WxWorkCheckModel model : wxWorkCheckModels) {
                if ("未打卡".equals(model.getException_type())) {
                    continue;
                }
                workCheck = new ProWorkCheck();
                workCheck.setStaffId(model.getUserid());
                workCheck.setType("外出打卡".equals(model.getCheckin_type()) ? (byte) 2 : (byte) 1);
                workCheck.setAttache(JSONObject.toJSONString(model.getMediaids()));
                workCheck.setNote(model.getNotes());
                workCheck.setAddress(model.getLocation_detail());
                Date date = new Date(model.getCheckin_time() * 1000);
                workCheck.setDate(DateUtil.format(date, DateUtil.PATTERN_CLASSICAL));
                workCheck.setTime(workCheck.getDate().substring(11, 19));
                workCheck.setDate(workCheck.getDate().substring(0, 10));
                workCheck.setColumn10(model.getLocation_title());
                workCheck.setLat(model.getLat());
                workCheck.setLng(model.getLng());
                proWorkChecks.add(workCheck);
            }
            return proWorkChecks;
        }
        return null;
    }

    public static WxUser getUserData(String wxUserId) {
        String token = getToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + token + "&userid=" + wxUserId;
        LinkedHashMap params = new LinkedHashMap<>();
        params.put("access_token", token);
        params.put("userid", wxUserId);
        String rs = NetRequestUtil.sendGetRequest(url, params);
        return JSONObject.parseObject(rs, WxUser.class);
    }

    public static String getMedia(String mediaId) {
        return new String(Base64.getEncoder().encode(getMediaByte(mediaId)));
    }

    public static byte[] getMediaByte(String mediaId) {
        return NetRequestUtil.sendGetRequest("https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=" + getToken() + "&media_id=" + mediaId);
    }

    public static byte[] getWxMediaByte(String mediaId) {
        return NetRequestUtil.sendGetRequest("https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + getToken() + "&media_id=" + mediaId);
    }

    public static void deleteUser(String wxUserId) {
        NetRequestUtil.sendGetRequest("https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=" + getToken() + "&userid=" + wxUserId);
    }

    /**
     * 获取模版token
     *
     * @param suite_id
     * @param suite_secret
     * @param suite_ticket
     * @return
     */
    public static String getSuiteTicketToken(String suite_id, String suite_secret, String suite_ticket) {

        RedisService redisService = (RedisService) WebParam.webApplicationContext.getBean("redisService");
        String key = suite_id + suite_ticket;
        Object tokenCache = redisService.getValue(key);

        if (Objects.isNull(tokenCache)) {
//            LinkedHashMap<String,String> param = new LinkedHashMap();
//            param.put("suite_id",suite_id);
//            param.put("suite_secret",suite_secret);
//            param.put("suite_ticket",suite_ticket);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("suite_id", suite_id);
            jsonObject.put("suite_secret", suite_secret);
            jsonObject.put("suite_ticket", suite_ticket);
            String res = NetRequestUtil.sendPostJsonRequest("https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token", jsonObject, null);
            LogManager.getLogger("mylog").info("verifyServer POST getSuiteTicketToken:" + res);
            JSONObject result = JSON.parseObject(res);
            String token = result.getString("suite_access_token");
            redisService.setKey(key, token);
            redisService.expireKey(key, 7000, TimeUnit.SECONDS);

            return token;
        } else {
            return tokenCache.toString();
        }

    }

    /**
     * 获取授权企业基础信息
     *
     * @param suiteTicketToken
     * @param auth_code
     * @return
     */
    public static String getAuthCorpInfo(String suiteTicketToken, String auth_code) {
//        LinkedHashMap param = new LinkedHashMap();
//        param.put("auth_code",auth_code);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_code", auth_code);
        return NetRequestUtil.sendPostJsonRequest("https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code?suite_access_token=" + suiteTicketToken, jsonObject, null);
    }

    /**
     * 获取企业授权应用信息
     *
     * @param auth_corpid
     * @param permanent_code
     * @param suite_access_token
     * @return
     */
    public static String getAuthInfo(String auth_corpid, String permanent_code, String suite_access_token) {
        JSONObject param = new JSONObject();
        param.put("auth_corpid", auth_corpid);
        param.put("permanent_code", permanent_code);
        return NetRequestUtil.sendPostJsonRequest("https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info?suite_access_token=" + suite_access_token, param, null);
    }

    /**
     * 企业微信帐号继承
     *
     * @param corpid
     * @param handoverUserId
     * @param takeoverUserId
     * @return
     */
    public static String transfer(String corpid, String handoverUserId, String takeoverUserId) {
        JSONObject param = new JSONObject();
        JSONObject ja = new JSONObject();
        ja.put("handover_userid", handoverUserId);
        ja.put("takeover_userid", takeoverUserId);
        param.put("corpid", corpid);
        param.put("transfer_list", ja);
        return NetRequestUtil.sendPostJsonRequest("https://qyapi.weixin.qq.com/cgi-bin/license/batch_transfer_license?provider_access_token=" + getToken(), param, null);
    }
}
