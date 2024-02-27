package com.yuesheng.pm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.ProWorkCheck;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.model.DianMaoWorkCheckModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;

import java.util.*;

/**
 * 点卯考勤数据同步工具
 *
 * @author XiaoSong
 * @date 2020-5-15
 */
public class DianMaoWorkCheck {
    /**
     * 登录地址
     */
    private static String LOGIN_URL = "https://dm.woqu365.com/login/doLogin.htm";
    /**
     * 选择公司地址
     */
    private static String CID_LOGIN = "https://dm.woqu365.com/login/checkApps.htm";
    /**
     * 用户数据地址
     */
    private static String USER_URL = "https://dmhmc.woqu365.com/forward_webfront/api/hr/employee/advancedsearchemployeev2.htm";

    /**
     * 获取考勤数据URL地址
     */
    private static String DATA_URL = "https://dm.woqu365.com/forward_webfront/api/at/admin/report/reportDetailData.htm";

    /**
     * 获取点卯考勤数据，json字符串类型
     *
     * @param start
     * @param end
     * @return
     */
    public static String getData(String start, String end) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("dids", "1");
        params.put("startDate", start);
        params.put("endDate", end);
        params.put("workStatus", "ON");
        params.put("pageNum", "0");
        params.put("pageSize", "50");
        params.put("confirmStatus", null);
        Map<String, String> header = getCommonHeader();
        header.put(":path", "/forward_webfront/api/at/admin/report/reportDetailData.htm");
        String l = login();
        header.put("cookie", l + "UM_distinctid=17225437f282e6-054e06d7b68b66-336a7f49-232800-17225437f2977c; CNZZDATA1276152854=1259659341-1589762362-https%253A%252F%252Fdm.woqu365.com%252F%7C1589790131");
        header.put("invoke-type", "ajax");
        Map<String, Object> result = NetRequestUtil.sendPostRequest(DATA_URL, params, header);
        if(!Objects.isNull(result.get("response"))) {
            return result.get("response").toString();
        }else{
            return "";
        }
    }

    /**
     * 获取用户数据集合json字符串
     *
     * @return
     */
    public static String getUser() {
        LinkedHashMap<String, String> param = new LinkedHashMap();
        param.put("p", "-1");
        param.put("pageSize", "50");
        param.put("sortType", "");
        param.put("conditionStr", "[{\"fn\":\"depIds\",\"value\":\"[]\",\"tableEnum\":\"employee\"}]");
        param.put("containSubDeps", "t");
        Map<String, String> header = getCommonHeader();
        header.put(":authority", "dmhmc.woqu365.com");
        header.put(":path", "/forward_webfront/api/hr/employee/advancedsearchemployeev2.htm");
        header.put("cookie", login() + "UM_distinctid=17225437f282e6-054e06d7b68b66-336a7f49-232800-17225437f2977c; CNZZDATA1276152854=1259659341-1589762362-https%253A%252F%252Fdm.woqu365.com%252F%7C1589790131");
        Map<String, Object> result = NetRequestUtil.sendPostRequest(USER_URL, param, header);
        return result.get("response").toString();
    }

    /**
     * 登录操作
     *
     * @return
     */
    public static String login() {
        LinkedHashMap<String, String> params = new LinkedHashMap();
        params.put("phone", "18015916581");
        params.put("password", "yf7581328");
        Map<String, String> header = getCommonHeader();
        header.put(":path", "/login/doLogin.htm");
        Map<String, Object> result = NetRequestUtil.sendPostRequest(LOGIN_URL, params, header);
        StringBuffer sb = new StringBuffer();
        StringBuffer dataCookieBuffer = new StringBuffer();
        Header[] headers = (Header[]) result.get("header");
        String temp = null;
        for (Header header1 : headers) {
            String value = header1.getValue();
            temp = value.split(";")[0];
            if (temp.startsWith("_device_"))
                continue;
            if (temp.equals(""))
                continue;
            if (temp.startsWith("_ubid_")) {
                continue;
            } else if (temp.startsWith("_wcf_")) {
                sb.append(temp);
                sb.append("; ");
            } else if (temp.startsWith("_ct_")) {
                sb.append(temp);
                sb.append("; ");
                dataCookieBuffer.append(temp);
                dataCookieBuffer.append("; ");
            } else if (temp.startsWith("_wcfl_")) {
                String[] arrTemp = temp.split("=");
                sb.append(arrTemp[0]);
                sb.append("=");
                sb.append(arrTemp[1]);
                sb.append("; ");

                dataCookieBuffer.append(arrTemp[0]);
                dataCookieBuffer.append("=");
                dataCookieBuffer.append(arrTemp[1]);
                dataCookieBuffer.append("; ");
            } else {
                sb.append(temp);
                sb.append("; ");
            }
        }
        sb.append("UM_distinctid=17225437f282e6-054e06d7b68b66-336a7f49-232800-17225437f2977c; ");
        sb.append("CNZZDATA1276152854=1259659341-1589762362-https%253A%252F%252Fdm.woqu365.com%252F%7C1589762362");
        return checkApp(sb.toString()) + dataCookieBuffer.toString();
    }

    /**
     * 选择公司账套
     *
     * @param loginCookie cookie 字符串
     * @return 返回cookie字符串中添加 _wcf_ 数据 之后的结果
     */
    public static String checkApp(String loginCookie) {
        LinkedHashMap param = new LinkedHashMap();
        param.put("cid", "40000017248");
        Map<String, String> header = getCommonHeader();
        header.put("cookie", loginCookie);
        Map<String, Object> result = NetRequestUtil.sendPostRequest(CID_LOGIN, param, header);
        Header[] headers = (Header[]) result.get("header");
        StringBuffer newCookie = new StringBuffer();
        for (Header cookie : headers) {
            String value = cookie.getValue().split(";")[0];
            newCookie.append(value);
            newCookie.append("; ");
        }
        return newCookie.toString();
    }

    /**
     * 获取点卯考勤机数据，ProWorkCheck类型
     *
     * @param start 开始日期
     * @param end   截止日期
     * @return 考勤数据集合
     */
    public static List<ProWorkCheck> getDianMaoWorkChecks(String start, String end) {
        return getWorkChecks(getData(start, end));
    }

    /**
     * 获取点卯考勤用户数据集合
     *
     * @return
     */
    public static List<Staff> getDianMaoUser() {
        List<Staff> staffList = new ArrayList<>();
        JSONObject userMap = JSON.parseObject(getUser());
        JSONArray jsonArray = userMap.getJSONObject("data").getJSONArray("list");
        Staff staff = null;
        for (int x = 0; x < jsonArray.size(); x++) {
            staff = new Staff();
            JSONObject jo = jsonArray.getJSONObject(x);
            jo = jo.getJSONObject("employee");
            staff.setHwDeviceId("1002" + jo.getString("eid"));
            staff.setName(jo.getString("name"));
            staffList.add(staff);
        }
        return staffList;
    }

    private static Map<String, String> getCommonHeader() {
        Map<String, String> header = new HashMap();
        header.put(":authority", "dm.woqu365.com");
        header.put(":method", "POST");
        header.put(":scheme", "https");
        header.put("referer", "https://dm.woqu365.com/forward_webfront/at/attDetail/attDetail.html");
        header.put("sec-fetch-dest", "empty");
        header.put("sec-fetch-site", "cors");
        header.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; ) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        header.put("x-requested-with", "XMLHttpRequest");
        header.put("origin", "https://dm.woqu365.com");
        return header;
    }

    public static void main(String[] args) {
//        List<ProWorkCheck> checks = getDianMaoWorkChecks("2020-05-01", "2020-05-31");
//        System.out.println(checks);
        List<Staff> staffList = getDianMaoUser();
        System.out.println(staffList);
    }

    private static List<ProWorkCheck> getWorkChecks(String dianmaoResult) {
        if(StringUtils.isNotBlank(dianmaoResult)){
            List<ProWorkCheck> checks = new ArrayList<>();
            HashMap map = JSON.parseObject(dianmaoResult, HashMap.class);
            Map<String, Object> data = (Map<String, Object>) map.get("data");
            List<DianMaoWorkCheckModel> datas = JSON.parseArray(data.get("emps").toString(), DianMaoWorkCheckModel.class);
            for (DianMaoWorkCheckModel model : datas) {
                for (Map<String, String> day : model.getDays()) {
                    //考勤数据
                    List<ProWorkCheck> proWorkCheck = getWorkCheck(day, model);
                    checks.addAll(proWorkCheck);
                }
            }
            return checks;
        }else{
            return new ArrayList<>();
        }
    }

    private static List<ProWorkCheck> getWorkCheck(Map<String, String> day, DianMaoWorkCheckModel model) {
        List<ProWorkCheck> checks = new ArrayList<>();
        ProWorkCheck proWorkCheck;
        //遍历10次startTime{x} 和 endTime[x]
        byte x = 1;
        for (; x < 10; x++) {
            Object startTime = day.get("workStart" + x);
            Object endTime = day.get("workEnd" + x);
            if (startTime != null) {
                //创建一条上班打卡记录
                proWorkCheck = getProWorkCheck(model, startTime);
                checks.add(proWorkCheck);
            }
            if (endTime != null) {
                //创建一条下班打卡记录
                proWorkCheck = getProWorkCheck(model, endTime);
                checks.add(proWorkCheck);
            }
            if (startTime == null && endTime == null) {
                break;
            }
        }
        return checks;
    }

    private static ProWorkCheck getProWorkCheck(DianMaoWorkCheckModel model, Object startTime) {
        ProWorkCheck proWorkCheck;
        proWorkCheck = new ProWorkCheck();
        proWorkCheck.setStaffName(model.getName());
        proWorkCheck.setStaffId("1002" + model.getEid());
        proWorkCheck.setType((byte) 6);
        proWorkCheck.setDate(DateUtil.format(new Date(Long.parseLong(startTime.toString())), DateUtil.PATTERN_CLASSICAL_SIMPLE));
        proWorkCheck.setTime(DateUtil.format(new Date(Long.parseLong(startTime.toString())), DateUtil.PATTERN_ON_TIME2));
        return proWorkCheck;
    }
}
