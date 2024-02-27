package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.yuesheng.pm.config.DingTalkConfig;
import com.yuesheng.pm.config.NoToken;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.DingTalkStaffInfo;
import com.yuesheng.pm.entity.ProWorkCheck;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.DingLoginModel;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.service.impl.DingTalkUserCheck;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.DingCallBackCrypto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @ClassName DingTalkAPI
 * @Description
 * @Author ssk
 * @Date 2022/7/19 0019 15:59
 */
@Tag(name = "钉钉")
@RestController
@RequestMapping("api/dingTalk")
public class DingTalkAPI {

    @Autowired
    private DingTalkUserCheck dingTalkUserCheck;
    @Autowired
    private DingTalkStaffInfoService dingTalkStaffInfoService;
    @Autowired
    private UserApi userApi;
    @Autowired
    private RedisService redisService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ProWorkCheckService proWorkCheckService;
    @Autowired
    private DingTalkApiService dingTalkApiService;
    @Autowired
    private DingTalkLinkNoticeImageService dingTalkLinkNoticeImageService;

    @NoToken
    @Operation(description = "获取corpId")
    @GetMapping("getCorpId")
    public ResponseModel getCorpId(){
        System.out.println(DingTalkConfig.CORP_ID);
        return new ResponseModel(DingTalkConfig.CORP_ID);
    }

    @NoToken
    @Operation(description = "登录")
    @GetMapping("dingLogin")
    public ResponseModel dingLogin(String code,@Parameter(hidden = true) HttpSession httpSession){
        OapiV2UserGetResponse.UserGetResponse userInfo = dingTalkUserCheck.getUserInfo(code);
        DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByDing(userInfo.getUserid());
        if (Objects.isNull(dingTalkStaffInfo)){
            String url = "/dingBind?userId=" + userInfo.getUserid() + "&unionId=" + userInfo.getUnionid();
            return new ResponseModel(new DingLoginModel(url));
        }else {
            Staff staffById = staffService.getStaffById(dingTalkStaffInfo.getStaffId());
            String token = (String)userApi.login(staffById, httpSession).getData();
            Staff staff = (Staff)redisService.getValue(token);
            staff.setRole(roleService.getRolesByStaff(staff.getId()));
            return new ResponseModel(new DingLoginModel(staff,token));
        }
    }

    @NoToken
    @Operation(description = "绑定")
    @GetMapping("dingBind")
    public ResponseModel dingBind(String staffName,String password,String userId,String unionId,@Parameter(hidden = true) HttpSession httpSession){
        Staff staff = new Staff();
        staff.setName(staffName);
        staff.setPasswd(password);
        ResponseModel login = userApi.login(staff, httpSession);
        if (login.getData() != null){
            Staff staffByToken = (Staff)redisService.getValue(login.getData().toString());
            if (Objects.isNull(dingTalkStaffInfoService.selectByStaffId(staffByToken.getId()))){
                DingTalkStaffInfo dingTalkStaffInfo = new DingTalkStaffInfo();
                dingTalkStaffInfo.setDingTalkUserId(userId);
                dingTalkStaffInfo.setStaffId(staffByToken.getId());
                dingTalkStaffInfo.setUnionId(unionId);
                dingTalkStaffInfoService.insertDing(dingTalkStaffInfo);
                login.setMsg("绑定成功");
                return login;
            }else {
                dingTalkStaffInfoService.update(staff,userId);
                login.setMsg("已更新绑定信息");
                return login;
            }
        }else {
            return new ResponseModel(500,login.getMsg());
        }
    }

    @NoToken
    @ResponseBody
    @Operation(description = "事件订阅")
//    @PostMapping("getEventsBack")
    public Map<String,String> getEventsBack(@RequestParam(value = "msg_signature", required = false) String msg_signature,
                                                @RequestParam(value = "timestamp", required = false) String timeStamp,
                                                @RequestParam(value = "nonce", required = false) String nonce,
                                                @RequestBody(required = false) JSONObject json){

        try {
            // 1. 从http请求中获取加解密参数

            // 2. 使用加解密类型
            // Constant.OWNER_KEY 说明：
            // 1、开发者后台配置的订阅事件为应用级事件推送，此时OWNER_KEY为应用的APP_KEY。
            // 2、调用订阅事件接口订阅的事件为企业级事件推送，
            //      此时OWNER_KEY为：企业的appkey（企业内部应用）或SUITE_KEY（三方应用）
            DingCallBackCrypto callbackCrypto = new DingCallBackCrypto(DingTalkConfig.TOKEN, DingTalkConfig.AES_KEY, DingTalkConfig.APP_KEY);
            String encryptMsg = json.getString("encrypt");
            String decryptMsg = callbackCrypto.getDecryptMsg(msg_signature, timeStamp, nonce, encryptMsg);

            // 3. 反序列化回调事件json数据
            JSONObject eventJson = JSON.parseObject(decryptMsg);
            String eventType = eventJson.getString("EventType");

            // 4. 根据EventType分类处理
            if ("check_url".equals(eventType)) {
                // 测试回调url的正确性
                System.out.println("测试回调url的正确性");
            } else if ("attendance_check_record".equals(eventType)) {
                // 打卡
                JSONArray jsonDataList = eventJson.getJSONArray("DataList");

                for (int i = 0;i < jsonDataList.size();i++){
                    JSONObject recordMSG = jsonDataList.getJSONObject(i);
                    ProWorkCheck proWorkCheck = new ProWorkCheck();
                    proWorkCheck.setId(UUID.randomUUID().toString());
                    DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByDing(recordMSG.getString("userId"));
                    if (!Objects.isNull(dingTalkStaffInfo)){
                        proWorkCheck.setStaffId(dingTalkStaffInfo.getStaffId());
                        Staff staff = staffService.getStaffById(dingTalkStaffInfo.getStaffId());
                        proWorkCheck.setStaffName(staff.getName() + "," + staff.getSection().getName());
                        Date date = new Date(recordMSG.getLong("checkTime"));
                        String dateString = DateUtil.format(date,DateUtil.PATTERN_CLASSICAL);
                        proWorkCheck.setDate(dateString.substring(0,10));
                        proWorkCheck.setTime(dateString.substring(11,19));
                        proWorkCheck.setAddress(recordMSG.getString("address"));
                        proWorkCheck.setType("Outside".equals(recordMSG.getString("locationResult")) ? (byte) 9 : (byte) 8);
                        proWorkCheck.setLat(recordMSG.getLong("latitude"));
                        proWorkCheck.setLng(recordMSG.getLong("longitude"));
                        proWorkCheck.setNote("");
                        proWorkCheck.setAttache("");
                        proWorkCheck.setSignBgAvatar("");
                        proWorkCheck.setMac("");
                        proWorkCheck.setColumn10(recordMSG.getString("address"));

                        proWorkCheckService.insert(proWorkCheck);
                    }
                }
            } else {
                // 添加其他已注册的
            }

            // 5. 返回success的加密数据
            Map<String, String> successMap = callbackCrypto.getEncryptedMap("success");
            return successMap;

        } catch (DingCallBackCrypto.DingTalkEncryptException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Operation(description = "媒文体文件上传")
    @PostMapping("mediaUpload")
    public ResponseModel mediaUpload(@RequestBody Attach attach){
        return new ResponseModel(dingTalkApiService.mediaUploadFileBytes(attach));
    }

    @Operation(description = "修改图片使用状态")
    @PostMapping("setStatus")
    public ResponseModel setStatus(String id){
        return new ResponseModel(dingTalkLinkNoticeImageService.update(id));
    }

    @Operation(description = "获取图片信息集合")
    @GetMapping("getImageMsg")
    public ResponseModel getImageMsg(){
        return new ResponseModel(dingTalkLinkNoticeImageService.selectAll());
    }

    @Operation(description = "删除图片")
    @PostMapping("deleteImage")
    public ResponseModel deleteImage(String id){
        return new ResponseModel(dingTalkLinkNoticeImageService.delete(id));
    }

    @Operation(description = "初始化创建钉钉部门")
    @PostMapping("createDingDepartment")
    public ResponseModel createDingDepartment(){
        dingTalkApiService.createDepartments();
        return new ResponseModel("ok");
    }

    @Operation(description = "初始化创建钉钉用户")
    @PostMapping("createDingUser")
    public ResponseModel createDingUser(){
        dingTalkApiService.createUsers();
        return new ResponseModel("ok");
    }
}
