package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.service.StDeviceService;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.NetRequestUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class StDeviceServiceImpl implements StDeviceService {
    private String APP_KEY = "b9b7f71bc10f8c59";
    private String APP_SECRET = "e87f4a69857c3624653416568572839d";
    private String REQUEST_SERVER = "https://link.bi.sensetime.com/api";

    @Override
    public List<StDeviceEntity> getDeviceList() {
        return new ArrayList<>();
    }

    @Override
    public List<ProWorkCheck> getStWorkCheckData(String startDate, String endDate) {
        Map<String, Object> result = getStWorkCheckList(startDate, endDate, "1");
        List<ProWorkCheck> proWorkChecks1 = new ArrayList<>();
        if(!Objects.isNull(result) && !Objects.isNull(result.get("data"))){
            JSONObject jsonObject = (JSONObject) result.get("data");
            Integer pageNum = jsonObject.getInteger("pageNum") + 1;
            Integer total = jsonObject.getInteger("totalPage");
            List<StWorkCheckEntity> proWorkChecks = parseProWorkCheck(result);
            for (; pageNum < total; pageNum++) {
                result = getStWorkCheckList(startDate, endDate, pageNum + "");
                proWorkChecks.addAll(parseProWorkCheck(result));
            }
            ProWorkCheck proWorkCheck;
            for (StWorkCheckEntity stWorkCheckEntity : proWorkChecks) {
                proWorkCheck = new ProWorkCheck();
                parseProWorkCheck(proWorkCheck, stWorkCheckEntity);
                proWorkChecks1.add(proWorkCheck);
//            debug 使用
//            if(WebParam.SYSTEM_IS_DEBUG){
//                ProStaffHw proStaffHw = proStaffHwService.queryById(Integer.parseInt(proWorkCheck.getStaffId()));
//                if(proStaffHw != null){
//                    //更新头像
//                    byte[] res = NetRequestUtil.sendGetRequest("https://link.bi.sensetime.com/v1/image/1/"+stWorkCheckEntity.getAvatar());
//                    proStaffHw.setHead(Base64.toBase64String(res));
//                    proStaffHwService.update(proStaffHw);
//                }
//            }
            }
        }

        return proWorkChecks1;
    }

    @Override
    public void parseProWorkCheck(final ProWorkCheck proWorkCheck, final StWorkCheckEntity stWorkCheckEntity) {
        proWorkCheck.setStaffName(stWorkCheckEntity.getName());
        proWorkCheck.setDate(stWorkCheckEntity.getSignDate());
        proWorkCheck.setDeviceName(stWorkCheckEntity.getDeviceName());
        proWorkCheck.setStaffId("1" + stWorkCheckEntity.getUserId() + "9");
        if (proWorkCheck.getStaffId().length() > 10) {
            proWorkCheck.setStaffId("1" + stWorkCheckEntity.getUserId());
        }
        proWorkCheck.setTime(DateUtil.millisecondToDate(stWorkCheckEntity.getSignTime() * 1000, DateUtil.PATTERN_ON_TIME));
        proWorkCheck.setType((byte) 5);
        proWorkCheck.setSignBgAvatar(stWorkCheckEntity.getSignBgAvatar());
    }

    @Override
    public List<Staff> getDeviceUsers(String userId) {
        ArrayList<Staff> staffList = new ArrayList<>();
        long timestamp = System.currentTimeMillis();
        String url = "/v1/user/list";
        LinkedHashMap<String, String> param = new LinkedHashMap();
        param.put("type", "1");
        if (StringUtils.isNotBlank(userId)) {
            param.put("id", userId);
        }

        // param.put("name", "张松进");
        param.put("app_key", APP_KEY);
        param.put("sign", getSign(timestamp));
        param.put("timestamp", timestamp + "");
        String result = NetRequestUtil.sendGetRequest(REQUEST_SERVER + url, param);
        try {
            Map<String, Object> mapResult = JSONObject.parseObject(result, Map.class);
            if (String.valueOf(mapResult.get("code")).equals("200")) {
                JSONArray jsonArray = (JSONArray) mapResult.get("data");
                Staff staff;
                for (int i = 0; i < jsonArray.size(); i++) {
                    staff = new Staff();
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    staff.setName(jsonObject.get("name").toString());
                    staff.setHwDeviceId(jsonObject.get("id").toString());
                    staff.setHead(jsonObject.get("avatar").toString());
                    staffList.add(staff);
                }
            }
        } catch (Exception ignore) {

        }
        return staffList;
    }

    @Override
    public Map<String, Object> addStaffTODevice(Staff staff, Attach attach) {
        String url = "/v1/user";
        long timestamp = System.currentTimeMillis();
        LinkedHashMap<String, String> param = new LinkedHashMap();
        param.put("groups", "22524");
        param.put("icNumber", "");
        param.put("jobNumber", "");
        param.put("mobile", "");
        param.put("name", staff.getName());
        param.put("remark", "");
        param.put("avatar", "");
        param.put("force", "1");
        attach.setName("avatarFile");
        HashMap header = new HashMap();
        StringBuffer urlParam = new StringBuffer();
        urlParam.append("app_key=");
        urlParam.append(APP_KEY);
        urlParam.append("&sign=");
        urlParam.append(getSign(timestamp));
        urlParam.append("&timestamp=");
        urlParam.append(timestamp);
        Map<String, Object> result = NetRequestUtil.sendPostRequest(REQUEST_SERVER + url + "?" + urlParam, param, header, attach);
        return result;
    }

    private List<Staff> parseUsers(Map<String, Object> result) {
        JsonArray jsonArray = ((JsonObject) result.get("data")).getAsJsonArray();
        List<Staff> staffList = new ArrayList<>();
        Staff staff;
        for (int x = 0; x < jsonArray.size(); x++) {
            staff = new Staff();
            JsonObject jsonObject = jsonArray.get(x).getAsJsonObject();
//            staff.setName(jsonObject.get(""));
        }
        return null;
    }

    private Map<String, Object> getUsers(String pageNum) throws Exception {
        String url = "/v1/group";
        long timestamp = System.currentTimeMillis();
        LinkedHashMap<String, String> param = new LinkedHashMap();
        param.put("app_key", APP_KEY);
        param.put("sign", getSign(timestamp));
        param.put("timestamp", timestamp + "");
        param.put("page", pageNum);
        param.put("size", "20");
        Map<String, Object> resultMap = JSONObject.parseObject(NetRequestUtil.sendGetRequest(REQUEST_SERVER + url, param), Map.class);
        return resultMap;
    }

    private List<StWorkCheckEntity> parseProWorkCheck(Map<String, Object> result) {
        if (result == null) {
            return new ArrayList<>();
        }
        JSONObject jsonObject = (JSONObject) result.get("data");
        List<StWorkCheckEntity> workCheckEntities = JSONObject.parseArray(jsonObject.getString("data"), StWorkCheckEntity.class);
        return workCheckEntities;
    }

    private Map<String, Object> getStWorkCheckList(String startDate, String endDate, String pageNum) {
        String url = "/v1/record";
        long timestamp = System.currentTimeMillis();
        LinkedHashMap<String, String> param = new LinkedHashMap();
        param.put("app_key", APP_KEY);
        param.put("sign", getSign(timestamp));
        param.put("timestamp", timestamp + "");
        param.put("startTime", startDate);
        param.put("endTime", endDate);
        param.put("page", pageNum);
        param.put("size", "20");
        param.put("type", "1");
        String result = NetRequestUtil.sendGetRequest(REQUEST_SERVER + url, param);
        Map<String, Object> resultMap = null;
        try {
            resultMap = JSONObject.parseObject(result, Map.class);
            if (resultMap.get("code").toString().equals("200")) {
                return resultMap;
            }
        } catch (Exception ignore) {
            System.out.println("商汤考勤机数据同步失败");
        }
        return null;
    }

    private String getSign(long timestamp) {
        return DigestUtils.md5Hex(timestamp + "#" + APP_SECRET);
    }


    public static void main(String[] args) throws IOException {
        Date startDate = DateUtil.parse("2022-08-01 00:00:00", DateUtil.PATTERN_CLASSICAL);
        Date endDate = DateUtil.parse("2022-08-02 00:00:00", DateUtil.PATTERN_CLASSICAL);
        List<ProWorkCheck> checks = new StDeviceServiceImpl().getStWorkCheckData(startDate.getTime() / 1000 + "", endDate.getTime() / 1000 + "");
        System.out.println(checks);
//         System.out.println(new StDeviceServiceImpl().addStaffTODevice(staff, attach));
//         StDeviceService stDeviceService = new StDeviceServiceImpl();

//         List<Staff> staff = stDeviceService.getDeviceUsers(null);
//         System.out.println(staff);

    }
}
