package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.WxNotifyModel;
import com.yuesheng.pm.model.WxUser;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.CompanyWxUtil;
import com.yuesheng.pm.util.NetRequestUtil;
import com.yuesheng.pm.util.Xml;
import com.yuesheng.pm.util.wx.AesException;
import com.yuesheng.pm.util.wx.WXBizMsgCrypt;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * 移动端第三方api
 */
@RestController
@RequestMapping("api/wx")
public class WxApi {

    @Autowired
    private SystemConfigService configService;

    @Operation(description = "获取微信openId")
    @GetMapping("getOpenId/{code}")
    @NoToken
    public ResponseModel getOpenId(@PathVariable String code) throws IOException {
        return ResponseModel.ok(WxController.getUserInfo(code));
    }

    @Operation(description = "获取微信ticket")
    @GetMapping("getTicket")
    public ResponseModel getTicket() {
        return ResponseModel.ok(CompanyWxUtil.getTicket(CompanyWxUtil.getToken()));
    }

    @Operation(description = "获取sha1签名")
    @GetMapping("getSha1")
    public ResponseModel getSha1(String str) throws NoSuchAlgorithmException {
        return ResponseModel.ok(AESEncrypt.getSha1(str));
    }

    @Operation(description = "根据经纬度查询位置信息（百度）")
    @GetMapping("getAddressName")
    public ResponseModel getAddressName(String latitude, String longitude) {
        String res = "";
        String url = "";
        try {
//            获取腾讯地址解析
            SystemConfig sc = configService.queryByCoding("TX_KEY");
            url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + latitude + "," + longitude + "&key=" + sc.getValue() + "&get_poi=1";
            res = NetRequestUtil.sendGetRequest(url, null);
            JSONObject jo = ((JSONObject) JSON.parse(res)).getJSONObject("result").getJSONObject("address_component");
            HashMap<String, Object> txResult = new HashMap<String, Object>();
            HashMap<String, Object> r = new HashMap<>();
            r.put("addressComponent", jo);
            txResult.put("result", r);
            return ResponseModel.ok(txResult);
        } catch (Exception e) {
            try {
                return ResponseModel.ok(JSON.parse(getBaiduMap(latitude, longitude)));
            } catch (Exception ex) {
                return ResponseModel.ok(getNullData());
            }
        }
    }

    private String getBaiduMap(String latitude, String longitude) throws Exception {
        SystemConfig sc = configService.queryByCoding("BAIDU_AK");
        if (Objects.isNull(sc)) {
            throw new Exception("请配置百度《BAIDU_AK》参数");
        }
        String url = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=" + sc.getValue() + "" +
                "&output=json&coordtype=wgs84ll&location=" + latitude + "," + longitude;
        return NetRequestUtil.sendGetRequest(url, null);
    }

    private JSONObject getGaoDeMap(String latitude, String longitude) throws Exception {
        SystemConfig sc = configService.queryByCoding("GAODE_AK");
        if(Objects.isNull(sc)){
            throw new Exception("请配置高得《GAODE_AK》参数");
        }
        String url = "https://restapi.amap.com/v3/geocode/regeo?key="+sc.getValue()+"&location="+longitude+","+latitude;
        String res = NetRequestUtil.sendGetRequest(url,null);
        JSONObject jo = JSON.parseObject(res);
        return jo;
    }

    private HashMap<String, Object> getNullData() {
        JSONObject o = new JSONObject();
        o.put("city", "-");
        o.put("street", "-");
        o.put("street_number", "-");
        HashMap<String, Object> nullResult = new HashMap<String, Object>();
        HashMap<String, Object> r = new HashMap<>();
        r.put("addressComponent", o);
        nullResult.put("result", r);
        return nullResult;
    }

    @Operation(description = "获取百度路线信息")
    @GetMapping("getRoutes")
    public ResponseModel getRoutes(String startLatitude, String startLongitude, String endLatitude, String endLongitude) {
        SystemConfig sc = configService.queryByCoding("BAIDU_AK");
        if (Objects.isNull(sc)) {
            return ResponseModel.error("请配置百度《BAIDU_AK》参数");
        }
        String url = "https://api.map.baidu.com/directionlite/v1/driving?" +
                "origin=" + startLatitude + "," + startLongitude +
                "&destination=" + endLatitude + "," + endLongitude + "&ak=" + sc.getValue();
        return ResponseModel.ok(JSON.parse(NetRequestUtil.sendGetRequest(url, null)));
    }


    /**
     * 验证企业微信代开发配置时回调url（创建代开发应用时验证地址）
     *
     * @param msg_signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param response
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     * @throws AesException
     * @throws IOException
     */
    @GetMapping("verifyServer")
    @NoToken
    public void verify(String msg_signature,
                       Integer timestamp,
                       String nonce,
                       String echostr,
                       String corpId,
                       HttpServletResponse response) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException, AesException, IOException {
        LogManager.getLogger("mylog").error("GetMapping verifyServerr");
        SystemConfig encodingAesKey = configService.queryByCoding("encodingAesKey");
        SystemConfig token = configService.queryByCoding("wx_server_token");
        if (StringUtils.isBlank(corpId)) {
            SystemConfig corpid = configService.queryByCoding("QY_WX_CORPID");
            corpId = corpid.getValue();
        }

        if (!Objects.isNull(encodingAesKey)) {
            try {
                WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token.getValue(), encodingAesKey.getValue(), corpId);
                String sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp.toString(),
                        nonce, echostr);
                response.getWriter().write(sEchoStr);
            } catch (Exception e) {
                LogManager.getLogger("mylog").error("verify error:" + e.getMessage());
            }
            /*String[] arrays = new String[]{token.getValue(), timestamp.toString(), nonce, echostr};
            Arrays.sort(arrays, Comparator.naturalOrder());
            String sortStr = StringUtils.join(arrays, "");
            String signature = AESEncrypt.getSha1(sortStr);
            if (StringUtils.equals(msg_signature, signature)) {
                String aesKey = new String(Base64.decode(encodingAesKey.getValue() + "="));
                String content = AESEncrypt.AESDecode(echostr, aesKey);

                try {
                    String newContent = StringUtils.substring(content, 16);
                    String msgLength = StringUtils.substring(newContent, 0, 4);
                    String msg = StringUtils.substring(newContent, 4, Integer.valueOf(msgLength));
                    String receiveid = StringUtils.substring(newContent, Integer.valueOf(msgLength) + 4);
                    return content;
                } catch (Exception e) {
                    LogManager.getLogger("info").error("签名验证通过,处理数据异常:content=" + content);
                }
                return content;
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append("签名验证未通过");
                sb.append("msg_signature=" + msg_signature);
                sb.append("nonce=" + nonce);
                sb.append("timestamp=" + timestamp);
                sb.append("echostr=" + echostr);
                sb.append("token=" + token.getValue());

                sb.append("sortStr=" + sortStr);
                sb.append("signature=" + signature);
                sb.append("msg_signature=" + msg_signature);
                LogManager.getLogger("mylog").error(sb.toString());
            }*/
        }
    }

    /**
     * 代开发应用回调URL
     *
     * @param msg_signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param response
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     * @throws AesException
     * @throws IOException
     */
    @GetMapping("verifyTemplate")
    @NoToken
    public void verifyTemplate(String msg_signature, Integer timestamp, String nonce, String echostr, HttpServletResponse response) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException, AesException, IOException {
        LogManager.getLogger("mylog").error("GetMapping verifyTemplate");
        SystemConfig encodingAesKey = configService.queryByCoding("encodingAesKey");
        SystemConfig token = configService.queryByCoding("wx_server_token");
        SystemConfig corpid = configService.queryByCoding("QY_WX_TEMPID");
        if (!Objects.isNull(encodingAesKey)) {
            try {
                WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token.getValue(), encodingAesKey.getValue(), corpid.getValue());
                String sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp.toString(),
                        nonce, echostr);
                response.getWriter().write(sEchoStr);
            } catch (Exception e) {
                LogManager.getLogger("mylog").error("verify error:" + e.getMessage());
            }
            /*String[] arrays = new String[]{token.getValue(), timestamp.toString(), nonce, echostr};
            Arrays.sort(arrays, Comparator.naturalOrder());
            String sortStr = StringUtils.join(arrays, "");
            String signature = AESEncrypt.getSha1(sortStr);
            if (StringUtils.equals(msg_signature, signature)) {
                String aesKey = new String(Base64.decode(encodingAesKey.getValue() + "="));
                String content = AESEncrypt.AESDecode(echostr, aesKey);

                try {
                    String newContent = StringUtils.substring(content, 16);
                    String msgLength = StringUtils.substring(newContent, 0, 4);
                    String msg = StringUtils.substring(newContent, 4, Integer.valueOf(msgLength));
                    String receiveid = StringUtils.substring(newContent, Integer.valueOf(msgLength) + 4);
                    return content;
                } catch (Exception e) {
                    LogManager.getLogger("info").error("签名验证通过,处理数据异常:content=" + content);
                }
                return content;
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append("签名验证未通过");
                sb.append("msg_signature=" + msg_signature);
                sb.append("nonce=" + nonce);
                sb.append("timestamp=" + timestamp);
                sb.append("echostr=" + echostr);
                sb.append("token=" + token.getValue());

                sb.append("sortStr=" + sortStr);
                sb.append("signature=" + signature);
                sb.append("msg_signature=" + msg_signature);
                LogManager.getLogger("mylog").error(sb.toString());
            }*/
        }
    }

    @PostMapping("verifyServer")
    @NoToken
    public void wxMsgNotify(WxNotifyModel model, HttpServletRequest request, HttpServletResponse response) throws IOException, AesException {

        SystemConfig encodingAesKey = configService.queryByCoding("encodingAesKey");
        SystemConfig token = configService.queryByCoding("wx_server_token");
        SystemConfig corpid = configService.queryByCoding("QY_WX_TEMPID");

        InputStream is = request.getInputStream();
        byte[] data = IOUtils.toByteArray(is);
        WXBizMsgCrypt wmc = new WXBizMsgCrypt(token.getValue(), encodingAesKey.getValue(), corpid.getValue());
        String content = wmc.DecryptMsg(model.getMsg_signature(), model.getTimestamp(), model.getNonce(), new String(data, "UTF-8"));

        Document document = null;
        String infoType = null;
        try {
            document = Xml.load(content);
            infoType = Xml.readProp(document, "InfoType");
            LogManager.getLogger("mylog").info("verifyServer POST" + infoType);
        } catch (Exception e) {
            LogManager.getLogger("mylog").info("verifyServer POST ERROR" + e.getMessage());
        }

        if (StringUtils.contains(infoType, "suite_ticket")) {
            //更新模版suite_ticket
            String SuiteTicket = Xml.readProp(document, "SuiteTicket");
            SystemConfig SuiteTicketConfig = new SystemConfig();
            SuiteTicketConfig.setName("SuiteTicket");
            SuiteTicketConfig.setCoding("SuiteTicket");
            SuiteTicketConfig.setValue(SuiteTicket);
            SuiteTicketConfig.setParent(1);
            configService.insert(SuiteTicketConfig);

            //获取授权客户corpId
            String SuiteId = Xml.readProp(document, "SuiteId");
            SystemConfig suiteSecret = configService.queryByCoding("SuiteSecret");
            String suiteTicketToken = CompanyWxUtil.getSuiteTicketToken(SuiteId, suiteSecret.getValue(), SuiteTicket);

            SystemConfig authCode = configService.queryByCoding("AuthCode");
            if (!Objects.isNull(authCode)) {

                //获取并保存授权应用信息
                String result = CompanyWxUtil.getAuthCorpInfo(suiteTicketToken, authCode.getValue());
                JSONObject jo = JSON.parseObject(result);
                JSONObject authInfo = jo.getJSONObject("auth_info");
                String agentid = null;
                if (!Objects.isNull(authInfo)) {
                    agentid = authInfo.getJSONArray("agent").getJSONObject(0).getString("agentid");
                } else {
                    LogManager.getLogger("mylog").info("verifyServer POST getAuthCorpInfo result=" + result);
                    response.getWriter().write("success");
                    return;
                }
                String secret = jo.getString("permanent_code");
                LogManager.getLogger("mylog").info("verifyServer POST permanent_code=" + secret);
                if (!Objects.isNull(jo.getJSONObject("dealer_corp_info"))) {
                    LogManager.getLogger("mylog").info("verifyServer POST dealer_corp_info=" + jo.getJSONObject("dealer_corp_info").toString());
                }
                JSONObject authCorpInfo = jo.getJSONObject("auth_corp_info");
                LogManager.getLogger("mylog").info("verifyServer POST auth_corp_info=" + authCorpInfo.getString("corpid"));
                LogManager.getLogger("mylog").info("verifyServer POST auth_info.agent.agentid=" + agentid);

                //保存应用agentId = agentid & secret = secretSc & 企业appid = auth_corp_info.corpid  & 打卡secret
                SystemConfig asc = configService.queryByCoding("AGENTID");
                asc.setValue(agentid);
                configService.update(asc);
                SystemConfig secretSc = configService.queryByCoding("SECRET");
                secretSc.setValue(secret);
                configService.update(secretSc);
                SystemConfig appidSc = configService.queryByCoding("APPID");
                appidSc.setValue(authCorpInfo.getString("corpid"));
                configService.update(appidSc);
                SystemConfig workSc = configService.queryByCoding("WORK_SECRET");
                workSc.setValue(secretSc.getValue());
                configService.update(workSc);

            } else {
                LogManager.getLogger("mylog").info("verifyServer POST suite_ticket error ,未查询到AuthCode");
            }

        } else if (StringUtils.contains(infoType, "create_auth")) {
            //保存用户授权成功信息
            String SuiteId = Xml.readProp(document, "SuiteId");
            SystemConfig suiteIdConfig = new SystemConfig();
            suiteIdConfig.setName("SuiteId");
            suiteIdConfig.setCoding("SuiteId");
            suiteIdConfig.setValue(SuiteId);
            suiteIdConfig.setParent(1);
            configService.insert(suiteIdConfig);

            SystemConfig authCode = new SystemConfig();
            authCode.setName("AuthCode");
            authCode.setCoding("AuthCode");
            authCode.setValue(Xml.readProp(document, "AuthCode"));
            authCode.setParent(1);
            configService.insert(authCode);
        } else if (StringUtils.contains(infoType, "change_auth")) {
            //授权变更
            SystemConfig suiteTicketConfig = configService.queryByCoding("SuiteTicket");

            //获取授权客户corpId
            String SuiteId = Xml.readProp(document, "SuiteId");
            SystemConfig suiteSecret = configService.queryByCoding("SuiteSecret");
            String suiteTicketToken = CompanyWxUtil.getSuiteTicketToken(SuiteId, suiteSecret.getValue(), suiteTicketConfig.getValue());
            String authCorpId = Xml.readProp(document, "AuthCorpId");
            SystemConfig secretSc = configService.queryByCoding("SECRET");
            String result = CompanyWxUtil.getAuthInfo(authCorpId, secretSc.getValue(), suiteTicketToken);
            JSONArray ja = JSON.parseObject(result).getJSONObject("auth_info").getJSONArray("agent");
            Logger log = LogManager.getLogger("mylog");
            for (int i = 0; i < ja.size(); i++) {
                JSONObject app = ja.getJSONObject(i);
                String name = app.getString("name");
                log.info("auth change " + name);
                if (StringUtils.contains(name, "打卡")) {
                    //将企业secret赋值给打卡secret，代开发模式时，两个secret为同一个
                    SystemConfig workSc = configService.queryByCoding("WORK_SECRET");
                    workSc.setValue(secretSc.getValue());
                    configService.update(workSc);
                }
            }
        }
        LogManager.getLogger("mylog").info("verifyServer POST success ,model=" + JSON.toJSONString(model));
        LogManager.getLogger("mylog").info("verifyServer POST success ,content=" + content);

        response.getWriter().write("success");
    }

    @Operation(description = "获取微信用户信息")
    @GetMapping("getWxUserInfo")
    public ResponseModel getWxUserInfo(String[] wxUserIds) {
        ArrayList<WxUser> users = new ArrayList<>();
        for (String userId : wxUserIds) {
            WxUser wxUser = CompanyWxUtil.getUserData(userId);
            users.add(wxUser);
        }
        return ResponseModel.ok(users);
    }


}
