package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.Reptile;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.entity.SystemConfig;
import com.yuesheng.pm.mapper.ReptileMapper;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.ReptileService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.service.SystemConfigService;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.NetRequestUtil;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.LogManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("reptileService")
public class ReptileServiceImpl implements ReptileService {
    public static String[] keys = new String[]{"智能", "立体停车", "机械", "车库", "监控", "软件", "平台", "系统","安防"};

    @Autowired
    private ReptileMapper reptileMapper;

    @Autowired
    private FlowNotifyService notifyService;

    @Autowired
    private SystemConfigService configService;

    @Autowired
    private StaffService staffService;

    @Override
    public void startTask() {
        this.jsCg();
        this.zfcg();
        this.ntzw();
        this.ntbgy();
        this.ntbzfjs();
        this.shfdc();
        this.sxtcyy();
        this.ntyhjt();
        this.ntgdzy();
        this.ntcxq();
        this.zhdc();
        this.haian();
        this.haianCaigou();
//        this.nanTongEdu();
        this.hhzb();
        this.ntzfCaigou();
        this.ntjiaotong();
    }

    @Override
    public Reptile insert(Reptile reptile) {
        reptile.setId(UUID.randomUUID().toString());
        Reptile q = new Reptile();
        q.setKey("'"+reptile.getKey()+"','"+reptile.getKey()+"-'");
        q.setTitle(reptile.getTitle());
        q.setDatetime(reptile.getDatetime());
        List<Reptile> reptiles = selectByParam(q);
        if(reptiles.isEmpty()){
            reptileMapper.insert(reptile);
        }
        return reptile;
    }

    @Override
    public Reptile selectById(String id) {
        return reptileMapper.queryById(id);
    }

    @Override
    public List<Reptile> selectByParam(Reptile reptile) {
        return reptileMapper.queryAllByLimit(reptile);
    }

    @Override
    public void notifyStaff() {

        List<Staff> staff = new ArrayList<>();
        SystemConfig sc = configService.queryByCoding("ZB_STAFFS");
        if (!Objects.isNull(sc)) {
            String[] staffs = StringUtils.split(sc.getValue(), ";");
            if (Objects.isNull(staffs) || staffs.length == 0) {
                return;
            }
            for (int i = 0; i < staffs.length; i++) {
                staff.add(staffService.getStaffById(staffs[i]));
            }
            if (staff.isEmpty()) {
                return;
            }
            String date = DateUtil.getDate();
            Reptile reptile = new Reptile();
            reptile.setReptileDatetime(date);
            reptile.setKey("'智能','立体停车','机械','车库','监控','安防'");
            List<Reptile> reptiles = selectByParam(reptile);
            HashMap<String, Object> p = new HashMap();
            p.put("title", "招标信息提示");
            reptiles.forEach(item -> {
                p.put("mTitle", "数据来源：" + item.getSourceName() + "。该数据用于学习交流使用，请勿传播,点击查看信息详情");
                p.put("content", item.getTitle());
                p.put("url", item.getContent());
                notifyService.msgNotify(staff, p);

                item.setKey(item.getKey() + "-");
                reptileMapper.update(item);
            });
        }


    }

    @Override
    public void notifyStaffV2() {

        List<Staff> staff = new ArrayList<>();
        SystemConfig sc = configService.queryByCoding("ZB_STAFFS");
        if (!Objects.isNull(sc)) {
            String[] staffs = StringUtils.split(sc.getValue(), ";");
            if (Objects.isNull(staffs) || staffs.length == 0) {
                return;
            }
            for (int i = 0; i < staffs.length; i++) {
                staff.add(staffService.getStaffById(staffs[i]));
            }
            if (staff.isEmpty()) {
                return;
            }
            String date = DateUtil.getDate();
            Reptile reptile = new Reptile();
            reptile.setReptileDatetime(date);
            reptile.setKey("'智能','立体停车','机械','车库','监控'");
            List<Reptile> reptiles = selectByParam(reptile);
            notifyStaff(staff, reptiles);
        }


    }

    @Override
    public void notifyStaffByApp() {

        List<Staff> staff = new ArrayList<>();
        SystemConfig sc = configService.queryByCoding("ZB_STAFFS_APP");
        if (!Objects.isNull(sc)) {
            String[] staffs = StringUtils.split(sc.getValue(), ";");
            if (Objects.isNull(staffs) || staffs.length == 0) {
                return;
            }
            for (int i = 0; i < staffs.length; i++) {
                staff.add(staffService.getStaffById(staffs[i]));
            }
            if (staff.isEmpty()) {
                return;
            }
            String date = DateUtil.getDate();
            Reptile reptile = new Reptile();
            reptile.setReptileDatetime(date);
            reptile.setKey("'软件','平台','系统'");
            List<Reptile> reptiles = reptileMapper.queryAllByLimit(reptile);
            notifyStaff(staff, reptiles);
        }
    }

    private void notifyStaff(List<Staff> staff, List<Reptile> reptiles) {
        if (reptiles.size() > 0) {
            HashMap<String, Object> p = new HashMap();
            p.put("title", "招标信息提示");
            p.put("mTitle", "该数据用于学习交流使用，请勿传播,点击查看信息详情");
            p.put("content", "今日有" + reptiles.size() + "条招标信息更新");
            p.put("url", WebParam.VUETIFY_BASE + "/reptile/list");
            notifyService.msgNotify(staff, p);
            reptiles.forEach(item -> {
                item.setKey(item.getKey() + "-");
                reptileMapper.update(item);
            });
        }
    }

    /**
     * 江苏公共资源交易
     */
    public void jsCg() {
        String param = "{\n" +
                "  \"token\": \"\",\n" +
                "  \"pn\": 0,\n" +
                "  \"rn\": 20,\n" +
                "  \"sdt\": \"\",\n" +
                "  \"edt\": \"\",\n" +
                "  \"wd\": \"\",\n" +
                "  \"inc_wd\": \"\",\n" +
                "  \"exc_wd\": \"\",\n" +
                "  \"fields\": \"title\",\n" +
                "  \"cnum\": \"001\",\n" +
                "  \"sort\": \"{\\\"infodatepx\\\":\\\"0\\\"}\",\n" +
                "  \"ssort\": \"title\",\n" +
                "  \"cl\": 200,\n" +
                "  \"terminal\": \"\",\n" +
                "  \"condition\": [\n" +
                "    {\n" +
                "      \"fieldName\": \"titlenew\",\n" +
                "      \"isLike\": true,\n" +
                "      \"likeType\": 0,\n" +
                "      \"equal\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"time\": [\n" +
                "    {\n" +
                "      \"fieldName\": \"infodatepx\",\n" +
                "      \"startTime\": \"-\",\n" +
                "      \"endTime\": \"-\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"highlights\": \"title\",\n" +
                "  \"statistics\": null,\n" +
                "  \"unionCondition\": null,\n" +
                "  \"accuracy\": \"\",\n" +
                "  \"noParticiple\": \"1\",\n" +
                "  \"searchRange\": null,\n" +
                "  \"isBusiness\": \"1\"\n" +
                "}";
        JSONObject j = JSON.parseObject(param);
        JSONArray condition = (JSONArray) j.get("condition");
        JSONObject cj = (JSONObject) condition.get(0);

        JSONArray time = (JSONArray) j.get("time");
        JSONObject cj2 = (JSONObject) time.get(0);
        cj2.put("startTime", DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00");
        cj2.put("endTime", DateUtil.getDate() + " 23:59:59");

        for (int i = 0; i < keys.length; i++) {
            try {
                cj.put("equal", keys[i]);
                String result = NetRequestUtil.sendPostJsonRequest("http://jsggzy.jszwfw.gov.cn/inteligentsearch/rest/esinteligentsearch/getFullTextDataNew",
                        j, null);
                JSONObject r = JSON.parseObject(result).getJSONObject("result");
                JSONArray ja = (JSONArray) r.get("records");
                if (ja.size() > 0) {
                    for (int k = 0; k < ja.size(); k++) {
                        JSONObject title = (JSONObject) ja.get(k);
                        String newsTitle = title.get("titlenew").toString();
                        if (StringUtils.contains(newsTitle, "中标公告")
                                || StringUtils.contains(newsTitle, "中标结果公告")
                                || StringUtils.contains(newsTitle, "成交公告")) {
                            continue;
                        }

                        Reptile reptile = new Reptile();
                        reptile.setKey(keys[i]);
                        reptile.setReptileDatetime(DateUtil.getDatetime());
                        reptile.setTitle(newsTitle);
                        reptile.setDatetime(title.get("infodatepx").toString());
                        reptile.setContent("http://jsggzy.jszwfw.gov.cn" + title.get("linkurl").toString());
                        reptile.setSourceName("江苏公共资源交易平台");
                        insert(reptile);
                    }
                }
            } catch (Exception e) {
                LogManager.getLogger("mylog").error("获取江苏公共资源交易平台-获取数据失败：" + e.getMessage());
            }
        }

    }

    /**
     * 崇川政府采购和工程招标
     */
    public void zfcg() {
        try{
            String url = "https://www.chongchuan.gov.cn/truecms/messageController/getMessageByCondition.do?lmxx=xccqrmzf,jygg";
            LinkedHashMap<String, String> param = new LinkedHashMap<>();
            param.put("pagenum", "1");
            param.put("pagesize", "6");
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Header[] headers = new Header[10];
            headers[0] = new BasicHeader("Host", "www.chongchuan.gov.cn");
            headers[1] = new BasicHeader("Sec-Fetch-Site", "same-origin");
            headers[2] = new BasicHeader("Sec-Fetch-Mode", "cors");
            headers[3] = new BasicHeader("Sec-Fetch-Dest", "empty");
            headers[4] = new BasicHeader("sec-ch-ua-platform", "macOS");
            headers[5] = new BasicHeader("sec-ch-ua-mobile", "?0");
            headers[6] = new BasicHeader("Origin", "https://www.chongchuan.gov.cn");
            headers[7] = new BasicHeader("Referer", "https://www.chongchuan.gov.cn/xccqrmzf/cx/cx.html?lmjc=jygg&xmh=%E6%99%BA%E8%83%BD");
            headers[8] = new BasicHeader("sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"");
            headers[9] = new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
//        Header header = new BasicHeader("Cookie", "JSESSIONID=57066B74F306DF87A2B78D7B382BD786.tomcat9_note60; __jsluid_s=469b89032c500e98ce4b6a3e9f5260b7; __jsluid_h=9583826c455be5c10f1ab48a39218eab; SF_cookie_4=36488769");
            for (int i = 0; i < keys.length; i++) {
                param.put("name", keys[i]);
                String result = NetRequestUtil.sendPostForm(url, param, headers);
                JSONObject r = JSON.parseObject(result);
                JSONArray ja = r.getJSONArray("msgList");
                for (int j = 0; j < ja.size(); j++) {
                    JSONObject title = ja.getJSONObject(j);
                    Long time = (Long) title.getJSONObject("fbrq").get("time");
                    if (time > dayTime) {
                        //当天发布，保存到数据库
                        Reptile reptile = new Reptile();
                        reptile.setReptileDatetime(DateUtil.getDatetime());
                        reptile.setKey(keys[i]);
                        reptile.setTitle(title.getString("msgtitle"));
                        reptile.setContent("");
                        reptile.setDatetime(DateUtil.format(new Date(time), DateUtil.PATTERN_CLASSICAL));
                        reptile.setSourceName("政府采购和工程招标");
                        insert(reptile);
                    } else {
                        break;
                    }
                }
            }
        }catch (Exception e){
            LogManager.getLogger("mylog").error("获取政府采购和工程招标数据异常：" + e.getMessage());
        }

    }

    /**
     * 崇川法定主动公开内容
     */
    public void ntzw() {
//        String[] apis = new String[]{"ntccqkfqn"};
        HashMap<String, String> apiName = new HashMap<>();
        apiName.put("ccqfgw1", "发改委");
        apiName.put("ntsccqjtj", "教体局");
        apiName.put("ntsccqkjj", "科技局");
        apiName.put("ntsccqgxj", "工信局");
        apiName.put("ntsccqmzj", "民政局");
        apiName.put("ntsccqsfj", "司法局");
        apiName.put("ntsccqsczj", "财政局");
        apiName.put("ntsccqrsj", "人社局");
        apiName.put("ccqfgwzjj", "住建局");
        apiName.put("ccqfgwcgj", "城管局");
        apiName.put("ccqszj", "市政局");
        apiName.put("ntsccqnsj", "农水局");
        apiName.put("ntsccqswj", "商务局");
        apiName.put("ntsccqwlj", "文旅局");
        apiName.put("ntsccqwjw", "卫健委");
        apiName.put("ntsccqtyjrswj", "退役军人事务局");
        apiName.put("ntsccqyjglj", "应急管理局");
        apiName.put("ntsccqsjj", "审计局");
        apiName.put("ntsccqgzb", "国资办");
        apiName.put("ntsccqxzspj", "行政审批局");
        apiName.put("ntsccqscjgj", "市场监管局");
        apiName.put("ntsccqtjj", "统计局");
        apiName.put("ccqxf", "信访局");
        apiName.put("ntsccqjrjgj", "地产金融监管局");
        apiName.put("ntsccqtcj", "投促局");
        apiName.put("ntccqkfqn", "崇川开发区-街道");
        apiName.put("ntsccqkfqb", "港闸开发区-街道");
        apiName.put("ntsccqsbgxq", "市北高薪区-街道");
        apiName.put("ntsccqcdjdfz", "城东街道");
        apiName.put("ntsccqcqjd", "陈桥街道");
        apiName.put("ntsccqhpqjd", "和平桥街道");
        apiName.put("ntsccqhqjd", "虹桥街道");
        apiName.put("ntsccqlszjd", "狼山镇街道");
        apiName.put("ntsccqqzjd", "秦灶街道");
        apiName.put("ntsccqrgjd", "任港街道");
        apiName.put("ntsccqtsgzjd", "天生港镇街道");
        apiName.put("ntsccqtzzjd", "唐闸镇街道");
        apiName.put("ntsccqwfjd", "文峰街道");
        apiName.put("ntsccqjtjfz", "新城桥街道");
        apiName.put("ntsccqxfjd", "幸福街道");
        apiName.put("ntsccqxtjd", "学田街道");
        apiName.put("ntsccqyxjd", "永兴街道");
        apiName.put("ntsccqzxjd", "钟秀街道");
        apiName.put("xccqrmzf", "公示公告");
        apiName.keySet().forEach(keys -> {
            ntzw(keys, apiName.get(keys));
        });

    }

    /**
     * 南通碧桂园控股
     */
    public void ntbgy() {
        try {
            String url = "http://www.ntjrgroup.com/publicity/bidding";
            Document document = Jsoup.connect(url).get();
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Element element = document.getElementsByClass("fusion-blog-rollover").get(0);
            Elements article = element.children();
            for (int i = 0; i < keys.length; i++) {
                for (int x = 0; x < article.size(); x++) {
                    Element a = article.get(x);
                    Element title = a.child(2).child(0).child(0);
                    Element dateE = a.child(2).child(1).child(1);
                    Date date = DateUtil.parse(dateE.text().replace("+08:00", "").replace("T", " "), "yyyy-MM-dd HH:mm:ss");
                    if (date.getTime() > dayTime) {
                        if (StringUtils.contains(title.text(), keys[i])) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(keys[i]);
                            reptile.setTitle(title.text());
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(date.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("南通碧桂园招标网");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            LogManager.getLogger("mylog").error("获取南通碧桂园数据异常：" + e.getMessage());
        }
    }

    /**
     * 南通市保障房建设
     */
    public void ntbzfjs() {
        try {
            String url = "http://ntftjt.com/notice/?type=list&category=7";
            Document document = Jsoup.connect(url).get();
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Element element = document.getElementsByClass("ul-listl7").get(0);
            Elements article = element.children();
            for (int i = 0; i < keys.length; i++) {
                for (int x = 0; x < article.size(); x++) {
                    Element a = article.get(x);
                    Element title = a.getElementsByClass("tit").get(0);
                    String dateValue = a.getElementsByClass("info").get(0).child(0).text();
                    Date date = DateUtil.parse(dateValue.replace("发布日期：", "") + " 23:59:59", DateUtil.PATTERN_CLASSICAL);
                    if (date.getTime() > dayTime) {
                        if (StringUtils.contains(title.text(), keys[i])) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(keys[i]);
                            reptile.setTitle(title.text());
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(date, DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("南通市保障房建设");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            LogManager.getLogger("mylog").error("获取南通市保障房建设数据异常：" + e.getMessage());
        }
    }

    /**
     * 盛和房地产
     */
    public void shfdc() {
        try {
            String url = "http://www.jsshfc.com.cn/info/list?channelcode=notice1";
            Document document = Jsoup.connect(url).get();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Element element = document.getElementsByClass("page-news").get(0);
            Elements article = element.children();
            for (int i = 0; i < keys.length; i++) {
                for (int x = 0; x < article.size(); x++) {
                    Element a = article.get(x);
                    Element title = a.child(1);
                    String dateValue = a.child(0).text();
                    String m = a.child(0).child(0).text();
                    dateValue = StringUtils.replaceChars(dateValue, "\t", "")
                            .replaceAll(" ", "")
                            .replaceAll("\n", "")
                            .replaceAll(m, "");
                    dateValue += "-" + m.replace("/", "-");

                    Date date = DateUtil.parse(dateValue + " 23:59:59", DateUtil.PATTERN_CLASSICAL);
                    if (date.getTime() > dayTime) {
                        if (StringUtils.contains(title.text(), keys[i])) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(keys[i]);
                            reptile.setTitle(title.text());
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(date, DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("盛和房地产");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            LogManager.getLogger("mylog").error("盛和房地产数据异常：" + e.getMessage());
        }
    }

    /**
     * 苏锡通产业园
     */
    public void sxtcyy() {
        String url = "http://www.stpac.gov.cn/stkjcyy/zfjzcg/zfjzcg.html";
        ntzf("苏锡通产业园", url);
    }

    /**
     * 南通沿海集团
     */
    public void ntyhjt() {
        String url = "http://www.ncd-group.com/tongzhi/103.html";
        try {
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByClass("un_box").get(0).child(0).children();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                for (int j = 0; j < element.size(); j++) {
                    Element li = element.get(j);
                    String day = li.child(0).child(0).child(0).text();
                    String date = li.child(0).child(0).child(1).text().replace(".", "-");
                    date += "-" + day;
                    String title = li.child(0).child(1).child(0).text();
                    Date cd = DateUtil.parse(date + " 23:59:59");
                    if (cd.getTime() > dayTime) {
                        if (StringUtils.contains(title, key)) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(key);
                            reptile.setTitle(title);
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("南通沿海集团");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取南通沿海集团数据异常：" + e.getMessage() + ",url:" + url);
        }
    }

    /**
     * 南通轨道置业
     */
    public void ntgdzy() {
        String url = "http://www.gcid.cn/lists/12.html";
        try {
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementById("n_news").child(0).child(0).children();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                for (int j = 0; j < element.size(); j++) {
                    Element li = element.get(j);
                    String day = li.child(0).child(0).child(0).text();
                    String date = li.child(0).child(0).child(1).text().replace(".", "-");
                    date += "-" + day;
                    String title = li.child(0).child(1).child(0).text();
                    Date cd = DateUtil.parse(date + " 23:59:59");
                    if (cd.getTime() > dayTime) {
                        if (StringUtils.contains(title, key)) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(key);
                            reptile.setTitle(title);
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("南通轨道置业");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取南通轨道置业数据异常：" + e.getMessage() + ",url:" + url);
        }
    }

    /**
     * 南通创新区
     */
    public void ntcxq() {
        String url = "http://www.ntscid.com/Category_36/Index.aspx";
        try {
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByClass("newslist").get(0).child(0).children();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                for (int j = 0; j < element.size(); j++) {
                    Element li = element.get(j);
                    String date = li.child(2).text();
                    String title = li.child(0).child(0).text();
                    Date cd = DateUtil.parse(date + ":00", "yyyy/MM/dd HH:mm:ss");
                    if (cd.getTime() > dayTime) {
                        if (StringUtils.contains(title, key)) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(key);
                            reptile.setTitle(title);
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("南通创新区");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取南通创新区数据异常：" + e.getMessage() + ",url:" + url);
        }
    }

    /**
     * 致豪地产
     */
    public void zhdc() {
        String url = "http://vwv.geho.com.cn/geho/zbtb.html";
        try {
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByClass("new_list").get(0).child(0).children();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                for (int j = 0; j < element.size(); j++) {
                    Element li = element.get(j);
                    String date = li.text();
                    String title = li.child(0).text();
                    date = date.replaceAll(title, "").replace(" (", "").replace(")", " ");
                    Date cd = DateUtil.parse(date + "23:59:59");
                    if (cd.getTime() > dayTime) {
                        if (StringUtils.contains(title, key)) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(key);
                            reptile.setTitle(title);
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("致豪地产");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取致豪地产数据异常：" + e.getMessage() + ",url:" + url);
        }
    }

    /**
     * 海安-工程项目公告
     */
    public void haian() {
        String url = "http://58.221.196.23/006/006001/subpage.html";
        try {
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByClass("sub-content").get(0).child(0).children();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                for (int j = 0; j < element.size(); j++) {
                    Element li = element.get(j);
                    String date = li.child(1).text();
                    String title = li.child(0).text();
                    Date cd = DateUtil.parse(date + " 23:59:59");
                    if (cd.getTime() > dayTime) {
                        if (StringUtils.contains(title, key)) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(key);
                            reptile.setTitle(title);
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("海安-工程项目公告");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取海安-工程项目公告数据异常：" + e.getMessage() + ",url:" + url);
        }
    }

    /**
     * 海安-采购项目公告
     */
    public void haianCaigou() {
        String url = "http://58.221.196.23/007/007001/subpage.html";
        try {
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();?
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByClass("sub-content").get(0).child(0).children();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                for (int j = 0; j < element.size(); j++) {
                    Element li = element.get(j);
                    String date = li.child(1).text();
                    String title = li.child(0).text();
                    Date cd = DateUtil.parse(date + " 23:59:59");
                    if (cd.getTime() > dayTime) {
                        if (StringUtils.contains(title, key)) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(key);
                            reptile.setTitle(title);
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("海安-采购项目公告");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取海安-采购项目公告数据异常：" + e.getMessage() + ",url:" + url);
        }
    }

    /**
     * 南通大学-招投标管理办公室
     */
    public void nanTongEdu() {
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("https://ztb.ntu.edu.cn/cgxxhw/index.chtml", "货物类");
        keyMap.put("https://ztb.ntu.edu.cn/cgxxgc/index.chtml", "工程类");
        keyMap.put("https://ztb.ntu.edu.cn/cgxxfw/index.chtml", "服务类");

        keyMap.keySet().forEach(url -> {
            Document document = null;
            try {
                Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
                Connection connection = Jsoup.connect(url);
                connection.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                connection.header("Accept-Encoding", "gzip, deflate, br");
                connection.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                connection.header("Cache-Control", "no-cache");
                connection.header("Connection", "keep-alive");
                connection.header("Host","ztb.ntu.edu.cn");
                connection.header("Pragma","no-cache");
                connection.header("Sec-Ch-Ua","\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
                connection.header("Sec-Ch-Ua-Mobile","?0");
                connection.header("Sec-Ch-Ua-Platform", "macOS");
                connection.header("Sec-Fetch-Dest","document");
                connection.header("Sec-Fetch-Mode","navigate");
                connection.header("Sec-Fetch-Site","none");
                connection.header("Sec-Fetch-User","?1");
                connection.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
                connection.header("Upgrade-Insecure-Requests","1");
                document = connection.get();
                Elements element = document.getElementsByClass("llist").get(0).children();
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    for (int j = 1; j < element.size(); j++) {
                        Element li = element.get(j);
                        if (StringUtils.equals(li.tagName(), "form")) {
                            continue;
                        }
                        String date = StringUtils.replaceChars(li.child(0).text(), " ", "");
                        String title = li.child(1).text();
                        Date cd = DateUtil.parse(date + " 23:59:59");
                        if (cd.getTime() > dayTime) {
                            if (StringUtils.contains(title, key)) {
                                Reptile reptile = new Reptile();
                                reptile.setReptileDatetime(DateUtil.getDatetime());
                                reptile.setKey(key);
                                reptile.setTitle(title);
                                reptile.setContent("");
                                reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                                reptile.setSourceName("南通大学-招投标管理办公室-" + keyMap.get(url));
                                reptile.setContent(url);
//                                insert(reptile);
                            }
                        } else {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(document.outerHtml());
                LogManager.getLogger("mylog").error("获取南通大学-招投标管理办公室数据异常：" + e.getMessage() + ",url:" + url + document.outerHtml());
//                if (this.reConnectCount < 50) {
//                    this.reConnectCount++;
//                    executorService.schedule(() -> {
//                        this.nanTongEdu();
//                    }, 5000, TimeUnit.MILLISECONDS);
//                }
            }
        });

    }

    /**
     * 海衡招标阳光集采平台
     */
    public void hhzb() {
        try {
            String url = "https://www.hhzb.com.cn/api/portal-web/bulletin/list?tradeClassification=A&" +
                    "current=1&" +
                    "size=20&" +
                    "type=tender_bulletin&" +
                    "descs=&" +
                    "tenderProjectClassifyCodeFirst=&" +
                    "tenderProjectClassifyCodeSecond=&" +
                    "ascs=&" +
                    "name=";
            byte[] bytes = NetRequestUtil.sendGetRequest(url);
            String data = new String(bytes);
            JSONObject jo = (JSONObject) JSON.parse(data);
            if (StringUtils.equalsIgnoreCase(jo.getString("code"), "200")) {
                JSONArray ja = jo.getJSONObject("data").getJSONArray("records");
                ja.forEach(item -> {
                    JSONObject temp = (JSONObject) item;
                    for (int i = 0; i < keys.length; i++) {
                            String key = keys[i];
                            String title = temp.getString("name");
                            if(StringUtils.contains(title,key)){
                                Reptile reptile = new Reptile();
                                reptile.setReptileDatetime(DateUtil.getDatetime());
                                reptile.setKey(key);
                                reptile.setTitle(title);
                                reptile.setDatetime(temp.getString("publishTime"));
                                reptile.setSourceName("海衡招标阳光集采平台");
                                reptile.setContent("https://www.hhzb.com.cn/BiddingInfo?id="+temp.getString("id"));
                                insert(reptile);
                            }
                    }
                });

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取海衡招标阳光集采平台数据异常：" + e.getMessage());
        }
        /*Document document = null;
        try {
            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            document = Jsoup.connect(url).get();
            Elements element = document.getElementsByTag("tbody").get(0).children();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                for (int j = 1; j < element.size(); j++) {
                    Element li = element.get(j);
                    String date = StringUtils.replaceChars(li.child(4).child(0).text(), " ", "");
                    String title = li.child(1).child(0).text();
                    Date cd = DateUtil.parse(date);
                    if (cd.getTime() > dayTime) {
                        if (StringUtils.contains(title, key)) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(key);
                            reptile.setTitle(title);
                            reptile.setContent("");
                            reptile.setDatetime(date);
                            reptile.setSourceName("海衡招标阳光集采平台");
                            reptile.setContent(url);
//                            insert(reptile);
                        }
                    }else{
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("mylog").error("获取海衡招标阳光集采平台数据异常：" + e.getMessage() + ",url:" + url+document.outerHtml());
        }*/
    }


    /**
     * 获取南通政府各街道公告
     *
     * @param apiKey
     * @param value
     */
    public void ntzw(String apiKey, String value) {
        String baseUrl = "https://www.chongchuan.gov.cn/APIS/gggs/gggs.html";
        String url = baseUrl.replace("APIS", apiKey);
        ntzf(value, url);
    }

    /**
     * 南通政府公告类网站
     *
     * @param value 网站名称
     * @param url   网站地址
     */
    private void ntzf(String value, String url) {
        try {
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                Connection connection = Jsoup.connect(url);
                connection.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                connection.header("Accept-Encoding", "gzip, deflate, br");
                connection.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                connection.header("Cache-Control", "no-cache");
                connection.header("Connection", "keep-alive");
                connection.header("Host","www.chongchuan.gov.cn");
                connection.header("Pragma","no-cache");
                connection.header("Sec-Ch-Ua","\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
                connection.header("Sec-Ch-Ua-Mobile","?0");
                connection.header("Sec-Ch-Ua-Platform", "macOS");
                connection.header("Sec-Fetch-Dest","document");
                connection.header("Sec-Fetch-Mode","navigate");
                connection.header("Sec-Fetch-Site","none");
                connection.header("Sec-Fetch-User","?1");
                connection.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
                connection.header("Upgrade-Insecure-Requests","1");
                Document document = connection.get();
//                Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
                Element element = document.getElementById("initData");
                Elements elements = element.children();
                for (int j = 0; j < elements.size(); j++) {
                    Element e = elements.get(j);
                    if (!Objects.isNull(e) && e instanceof Element) {
                        Element li = e.firstElementChild();
                        Element a = li.firstElementChild();
                        Element dateElement = a.firstElementChild();
                        if (Objects.isNull(dateElement)) {
                            dateElement = li.child(1);
                        }
                        String date = dateElement.text();
                        if (StringUtils.isNotBlank(date)) {
                            Date cd = DateUtil.parse(date + " 23:59:59");
                            if (cd.getTime() > dayTime) {
                                if (StringUtils.indexOf(a.text(), key) != -1) {
                                    Reptile reptile = new Reptile();
                                    reptile.setReptileDatetime(DateUtil.getDatetime());
                                    reptile.setKey(key);
                                    reptile.setTitle(a.text());
                                    reptile.setContent("");
                                    reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                                    reptile.setSourceName("崇川法定主动公开内容-" + value);
                                    reptile.setContent(url);
                                    insert(reptile);
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取[" + value + "]数据异常：" + e.getMessage() + ",url:" + url);
//            System.out.println("获取[" + value + "]数据异常：" + e.getMessage() + ",url:" + url);
        }
    }

    /**
     * 南通政府采购招标
     */
    private void ntzfCaigou(){
        try {
            String url = "https://www.chongchuan.gov.cn/xccqrmzf/jygg/jygg.html";
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                Document document = Jsoup.connect(url).get();
//                Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();?
                Element element = document.getElementById("initData");
                Elements elements = element.children();
                for (int j = 0; j < elements.size(); j++) {
                    Element e = elements.get(j);
                    if (!Objects.isNull(e) && e instanceof Element) {
                        Element li = e.firstElementChild();
                        Element a = li.getElementsByClass("content_title").get(0);
                        Element dateElement = a.firstElementChild();
                        String date = li.getElementsByClass("times").get(0).text();
                        if (StringUtils.isNotBlank(date)) {
                            Date cd = DateUtil.parse(date + " 23:59:59");
                            if (cd.getTime() > dayTime) {
                                if (StringUtils.indexOf(dateElement.text(), key) != -1) {
                                    Reptile reptile = new Reptile();
                                    reptile.setReptileDatetime(DateUtil.getDatetime());
                                    reptile.setKey(key);
                                    reptile.setTitle(a.text());
                                    reptile.setContent("");
                                    reptile.setDatetime(DateUtil.format(new Date(cd.getTime()), DateUtil.PATTERN_CLASSICAL));
                                    reptile.setSourceName("南通政府采购和工程招标");
                                    reptile.setContent(url);
                                    insert(reptile);
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            LogManager.getLogger("mylog").error("获取[南通政府采购和工程招标]数据异常：" + e.getMessage());
        }
    }

    public void ntjiaotong() {
        try {
            String url = "https://www.ntjtjt.com.cn/notice/16108277-0-10.html";
            Document document = Jsoup.connect(url).get();
//            Long dayTime = DateUtil.parse(DateUtil.getDate() + " 00:00:00").getTime();
            Long dayTime = DateUtil.parse(DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(),-1)) + " 00:00:00").getTime();
            Element element = document.getElementsByClass("p_list").get(1);
            Elements article = element.children();
            for (int i = 0; i < keys.length; i++) {
                for (int x = 0; x < article.size(); x++) {
                    Element a = article.get(x).child(0).child(0).child(0).child(0);
                    Element title = a.child(0).child(0);
                    Element dateE = a.child(2).child(1).child(0);
                    Date date = DateUtil.parse(dateE.text(), "yyyy-MM-dd");
                    if (date.getTime() > dayTime) {
                        if (StringUtils.contains(title.text(), keys[i])) {
                            Reptile reptile = new Reptile();
                            reptile.setReptileDatetime(DateUtil.getDatetime());
                            reptile.setKey(keys[i]);
                            reptile.setTitle(title.text());
                            reptile.setContent("");
                            reptile.setDatetime(DateUtil.format(new Date(date.getTime()), DateUtil.PATTERN_CLASSICAL));
                            reptile.setSourceName("南通交通建设投资集团");
                            reptile.setContent(url);
                            insert(reptile);
                        }
                    } else {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            LogManager.getLogger("mylog").error("获取南通碧桂园数据异常：" + e.getMessage());
        }
    }

}
