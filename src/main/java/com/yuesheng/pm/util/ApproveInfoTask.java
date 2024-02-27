package com.yuesheng.pm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.model.FLowMessageQuery;
import com.yuesheng.pm.model.Mail;
import com.yuesheng.pm.model.OutMaterData;
import com.yuesheng.pm.model.PaymentData;
import com.yuesheng.pm.restapi.ManagerLogin;
import com.yuesheng.pm.service.*;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 96339 on 2017/6/12.
 *
 * @author XiaoSong
 * @date 2017/06/12
 * <p>
 * update by XiaoSong on 2023-1-6
 * * 定时任务统一交由quartz-scheduler管理，此类仅作为组件使用，之前的定时任务全部注释，仅保留任务方法，供quartz-scheduler调用
 */
@Component
@Lazy(false)
public class ApproveInfoTask {
    @Autowired
    private FlowApproveService flowApproveService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    private PaymentDetailService paymentDetailService;
    @Autowired
    private OutMaterService outMaterService;
    @Autowired
    private ProPutSignService proPutSignService;
    @Autowired
    private PutStorageService putStorageService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ProcurementMaterService materService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SystemConfigService configService;
    @Autowired
    private FlowCourseService flowCourseService;

    /**
     * 流程异常通知人员
     */
    private List<Staff> errorNotifyStaff = new ArrayList<>();

    /**
     * 付款报表通知人员集合
     */
    private List<Staff> staffs = new ArrayList<>();
    /**
     * 项目领料通知
     */
    private List<Staff> projectManager = new ArrayList<>();
    /**
     * 查询sql参数map
     */
    private final static Map<String, String> params = new HashMap(16);

    public ApproveInfoTask() {
        params.put("hour", WebParam.NOTIFY_HOUR);
    }

    @PostConstruct
    public void init() {
        SystemConfig sc = configService.queryByCoding(Constant.ERROR_NOTIFY_KEY);
        if (!Objects.isNull(sc)) {
            if (StringUtils.isNotBlank(sc.getValue())) {
                String[] val = sc.getValue().split(";");
                if (val.length > 0) {
                    for (int i = 0; i < val.length; i++) {
                        Staff s = staffService.getStaffById(val[i]);
                        if (!Objects.isNull(s)) {
                            errorNotifyStaff.add(s);
                        }
                    }
                }
            }
        }
    }

    /**
     * 印章管理通知，1分钟执行一次
     */
    // @Scheduled(cron = "0 0/1 * * * *")
    public void notifyYinZhang() {
        String start = DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        String end = start;
        start += " 00:00:00";
        end += " 23:59:59";
        List<String> params = new ArrayList<>();
        params.add("5");
        params.add("6");
        List<String> status = new ArrayList<>();
        status.add("1");
        status.add("2");
        status.add("3");
//          8bde2161-e1d3-4f1f-a7fe-2af8da0619cc
//        3E2BAFBA-0BA7-4AFA-8DF3-A8D747B010B5
        List<FlowApprove> notifys = flowApproveService.getMessages("8bde2161-e1d3-4f1f-a7fe-2af8da0619cc", start, end, params, status, 1, null, 1,100, null);
//        notifys.addAll(flowApproveService.getMessages("3E2BAFBA-0BA7-4AFA-8DF3-A8D747B010B5", start, end, params, status, 1, null, new PageBounds(1, 10000), null));
        for (int i = 0; i < notifys.size(); i++) {
            FlowApprove approve = notifys.get(i);
            String state = flowApproveService.getEmailHistory(approve.getId());
            if (StringUtils.isBlank(state)) {
                genPreviewHtml(approve);
            }
        }
    }

    /**
     * 审批通知任务
     */
    public void info() {
        params.put("datetime", DateFormat.getDateTime());
        List<FlowApprove> approveList = flowApproveService.getMessageByTimeout(params);
        for (FlowApprove fa : approveList) {
            try {
                String outHtml = genPreviewHtml(fa);
                Staff staff = fa.getAcceptUser();
                //邮箱通知
                if (outHtml != null && staff.getEmail() != null && staff.getEmail().matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
                    notify(staff, fa.getMessage().getTitle(), outHtml);
                }
                //websocket通知
                if(!Objects.isNull(ManagerLogin.getStaffList()) && !Objects.isNull(staff) && ManagerLogin.getStaffList().containsKey(staff.getId())) {
                    JSONObject jo = new JSONObject();
                    jo.put("type", "flowApprove");
                    jo.put("data", fa);
                    MyWebSocketHandle.sendMsg(staff.getId(), jo.toJSONString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.notifyYinZhang();
    }

    /**
     * 入库签名过期任务，50分钟执行一次
     */
    // @Scheduled(cron = "0 0/50 * * * *")
    public void pastPutSign() {
        ProPutSign pps = new ProPutSign();
        String pastDate = DateUtil.format(new Date(), DateUtil.PATTERN_CLASSICAL_SIMPLE);
//        当天过期日期
        pps.setPastDate(pastDate);
//        未签字状态
        pps.setType(0);
        List<ProPutSign> ppsList = proPutSignService.queryListByParam(pps);
        Map<String, Object> result = new HashMap<>();
        for (ProPutSign sign : ppsList) {
            //自动入库
            try {
                PutStorage ps = JSON.parseObject(sign.getPutobj(), PutStorage.class);
                ps.setId(UUID.randomUUID().toString());
                result.put("state", putStorageService.addPutStorageSelect(ps, staffService.getStaffById(sign.getStaffId())));
                if (Integer.parseInt(result.get("state").toString()) > 0) {
                    sign.setType(2);
                    sign.setSignDate(pastDate);
                    sign.setPutId(ps.getId());
                    proPutSignService.update(sign);
                    LogManager.getLogger("mylog").info("入库签字单id=" + sign.getId() + ",自动入库成功,入库单id=" + sign.getPutId());
                } else {
                    sign.setType(2);
                    sign.setSignDate(pastDate);
                    sign.setPutId("作废");
                    proPutSignService.update(sign);
                    LogManager.getLogger("mylog").info("入库签字单id=" + sign.getId() + ",自动入库失败,失败原因=" + result.get("state"));
                }
            } catch (Exception e) {
                LogManager.getLogger("error").info("入库签字单id=" + sign.getId() + ",自动入库失败,错误信息：" + e.getMessage());
            }
        }
    }

    /**
     * 采购订单材料未到货通知（周一到周五，每天执行一次）
     */
    // @Scheduled(cron = "0 00 09 ? * MON-FRI")
    public void noDhNotify() {
        materService.noDhNotify();
    }


    /**
     * 申请单采购通知（周一到周五，每天执行一次）
     */
    // @Scheduled(cron = "0 00 09 ? * MON-FRI")
    public void applyNotifyStaff() {
        applyService.notifyStaff();
    }

    /**
     * 每周五  17点执行一次(付款报表)
     */
    // @Scheduled(cron = "0 00 17 ? * SAT")
    public void notifyMonthPayment() {
        Map<String, Object> param = new HashMap<>(16);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 6);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        param.put("start", format.format(today));
        param.put("end", DateFormat.getDate());


        List<PaymentData> pdatas = paymentDetailService.getPaymentMoney(param);
        if (pdatas.isEmpty()) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        String start = param.get("start").toString();
        String end = param.get("end").toString();


        param.clear();

        Double moneys = 0.0;
        int x = 0;
        for (PaymentData pd : pdatas) {
            moneys += pd.getMoney();
            sb.append("<div class=\\\"gray\\\">");
            sb.append(pd.getCname());
            sb.append("：");
            sb.append(pd.getMoney());
            sb.append("</div>");
            if (++x == 5) {
                break;
            }
        }
        param.put("title", "本周付款汇总：" + moneys);
        param.put("mTitle", "");
        param.put("content", sb.toString());
        param.put("url", "/managent/getPage?pageName=managerIndex&param=mpindex=wx/paymentData=pindex=" + start + "," + end);

        flowNotifyService.msgNotify(staffs, param);
    }

    /**
     * 每周五  17点执行一次(项目报表)
     */
    // @Scheduled(cron = "0 29 11 ? * SAT")
    public void notifyMonthProject() {
        Map<String, Object> param = new HashMap<>(16);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 6);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        param.put("start", format.format(today));
        param.put("end", DateUtil.format(DateUtil.rollDay(new Date(),1),"yyyy-MM-dd"));


        List<OutMaterData> pdatas = outMaterService.getOutMaterialMoney(param);
        if (pdatas.isEmpty()) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        String start = param.get("start").toString();
        String end = param.get("end").toString();

        param.clear();

        Double moneys = 0.0;
        int x = 0;
        for (OutMaterData pd : pdatas) {
            moneys += pd.getMoney();
            sb.append("<div class=\\\"gray\\\">");
            sb.append(pd.getPname());
            sb.append("：");
            sb.append(String.format("%.2f", pd.getMoney()));
            sb.append("</div>");
            if (++x == 5) {
                break;
            }
        }
        param.put("title", "本周项目耗材,合计：" + String.format("%.2f", moneys));
        param.put("mTitle", "");
        param.put("content", sb.toString());
        param.put("url", "/managent/getPage?pageName=managerIndex&param=mpindex=wx/outMaterData=pindex=" + start + "," + end);
        //通知管理人员
        flowNotifyService.msgNotify(projectManager, param);
    }

    /**
     * 检查异常类流程，自动处理
     */
    // @Scheduled(cron = "0 0/30 * * * *")
    public void checkErrorFLow() {
        String startDate = DateUtil.format(DateUtil.rollDay(DateUtil.getNowDate(), -7), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        String endDate = DateUtil.format(DateUtil.rollDay(DateUtil.getNowDate(), 1), DateUtil.PATTERN_CLASSICAL_SIMPLE);
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 8) {
//            Logger.getLogger("mylog").error("异常流程检查未到执行时间");
            return;
        }
        FLowMessageQuery message = new FLowMessageQuery();
        message.setStartDate(startDate);
        message.setEndDate(endDate);
        message.setState(1);
        List<FlowMessage> messages = flowMessageService.getMsgListByApproveDate(message);

        HashMap<String, Object> param = new HashMap<>(4);
        String baseUrl = WebParam.VUETIFY_BASE + "/approve/by-approve-id/";
        param.put("title", "审批流程异常");
//        param.put("url", );
        messages.forEach(msg -> {
            if (msg != null) {
                msg.setStaff(staffService.getStaffById(msg.getStaffId()));
                msg.setApproveList(flowApproveService.getFlowApproveByMessageId(msg.getId()));
                flowMessageService.isError(msg);
                if (msg.isApproveError()) {
                    //处理该异常，先撤回，再次自动同意
                    if (!Objects.isNull(redisService.getValue("ERROR_FLOW_APPROVE_ID_" + msg.getErrorApproveId()))) {
                        String errorMsg = "审批数据异常停止，自动修复失败,流程标题：" + msg.getTitle() +
                                ",流程名称：" + msg.getFlow().getName();
                        param.put("mTitle", msg.getDate());
                        param.put("content", errorMsg);
                        param.put("url", baseUrl + msg.getErrorApproveId());
                        Logger.getLogger("mylog").error(errorMsg);
                    } else {
                        FlowApprove fa = flowApproveService.getFlowApproveById(msg.getErrorApproveId());
                        if (!Objects.isNull(fa)) {
                            String errorMsg = "审批数据异常停止,流程标题：" + msg.getTitle() +
                                    ",流程名称：" + msg.getFlow().getName() + ",过程名称" + fa.getCourseName();
                            Logger.getLogger("mylog").error(errorMsg);
                            param.put("mTitle", msg.getDate());
                            param.put("content", errorMsg);
                            param.put("url", baseUrl + fa.getId());
                            flowNotifyService.msgNotify(errorNotifyStaff, param);
                            //自动撤回
                            flowApproveService.updateState(1, fa.getId());
//                            flowApproveService.recall(fa, false);
                            //    自动审批同意
                            String ad = fa.getApproveDate();
                            flowApproveService.consentApprove(fa);
                            //记录处理记录
                            redisService.setKey("ERROR_FLOW_APPROVE_ID_" + fa.getId(), fa);
                            //回滚审批时间
                            flowApproveService.updateApproveDate(ad, fa.getId());
                        }
                    }
                }
            }
        });
    }

    /**
     * 到期未处理流程
     */
    // @Scheduled(cron = "0 0/5 * * * *")
    public void pastApprove() {
        PageHelper.startPage(1, 50);
        List<FlowApprove> approves = flowApproveService.getPastApprove();
        approves.forEach(item -> {
            FlowCourse fc = flowCourseService.getThanFlowCourseBByCourseId(item.getCourseId(), item.getMessage().getHistroryId());
            if (!Objects.isNull(fc)) {
                int pastType = fc.getPo02006();
                int pastTime = fc.getPo02013();
                int mi = DateUtil.getOffsetMinutes(DateUtil.parse(item.getAccrptDate()), new Date());
                if (mi >= pastTime) {
                    //到达过期时间
                    if (pastType == 3) {
                        item.setApproveState(3);
                        item.setApproveDate(DateUtil.getDatetime());
                        item.setContent("自动审批同意");
                        //自动同意
                        flowApproveService.consentApprove(item);
                    } else if (pastType == 7) {
                        //自动中断
                        flowApproveService.breakFlow(item);
                    } else if (pastTime == 5) {
                        //    重复通知
                        Staff staff = item.getAcceptUser();
                        List<Staff> staff1 = new ArrayList<>();
                        staff1.add(staff);
                        flowNotifyService.flowChange(1, staff1, item);
                    }
                }

            }
        });
    }

    /**
     * 生成审批预览
     *
     * @param fa
     */
    public String genPreviewHtml(FlowApprove fa) {

        if (fa == null) {
//            审批对象为null，不推送
            return null;
        }
        fa.setMessage(flowMessageService.getMessageById(fa.getFlowMessageId()));
        if (fa.getMessage() == null) {
            return null;
        }
        String outHtml = null;
        Staff staff = fa.getAcceptUser();
        if (staff != null) {
            try {
                outHtml = faDispose(fa);
                if (flowNotifyService != null) {
                    List<Staff> temp = new ArrayList<>();
                    temp.add(staff);
                    if (fa.getApproveState() == 5) {
                        flowNotifyService.notifyMsg(fa.getSendUser(), temp, fa);
                    } else {
                        flowNotifyService.flowChange(1, temp, fa);
                    }
                }
                flowApproveService.insertEmailHistory(fa.getId(), "0");
            } catch (IOException e) {
                LogManager.getLogger("mylog").error("流程通知消息错误：" + e.getLocalizedMessage());
                return null;
            }
        } else {
            // System.out.println("通知消息未执行...");
        }
        return outHtml;
    }

    /**
     * 审批消息处理
     *
     * @param fa
     */
    public static String faDispose(FlowApprove fa) throws IOException {
        //审批主单据名称&路径
        String fileName = fa.getId() + ".html";
        String url = WebParam.NOTIFY_IP + "/api/" + WebParam.EMAIL_FOLDER + "/" + fileName;
        //邮箱信息处理
        String outHtml = emailDispose(url, fa);
        //审批主单据出处理
        approveMainDispose(fa, fileName);
        return outHtml;
    }

    /**
     * 审批主单据处理,写入文件
     *
     * @param fa
     */
    public static void approveMainDispose(FlowApprove fa, String fileName) throws IOException {
//        消息模板
        InputStream childInput = null;
        childInput = new FileInputStream(WebParam.assetsPath + "js" + File.separator + "templete" + File.separator + "emailNotifyTemp.html");
        File file = new File(WebParam.webRootPath + WebParam.EMAIL_FOLDER + File.separator + fileName);
        if (file.exists()) {
            //已存在，忽略
            IOUtils.closeQuietly(childInput);
            return;
        }
//        消息临时文件
        OutputStream os = new FileOutputStream(file);
        Document d = Jsoup.parse(childInput, "utf-8", "http://" + WebParam.NOTIFY_IP);
        Element e = d.getElementById("approveObj");
        e.text(JSON.toJSONString(fa));
//        写入审批主单据文件
        IOUtils.write(d.outerHtml().getBytes("utf-8"), os);
        IOUtils.closeQuietly(childInput);
        IOUtils.closeQuietly(os);

        //通知分布式服务器
        if (!WebParam.SYSTEM_CLOSE_APPROVE_CLOSE) {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("fa", JSON.toJSONString(fa));
            params.put("fileName", fileName);
            try {
                NetRequestUtil.sendPostRequest("http://192.168.3.253:8089/flowApprove", params);
            } catch (Exception e1) {
                System.out.println("通知分布服务器失败：" + e1.getMessage());
            }
        }

    }

    /**
     * 邮箱处理
     *
     * @param url 审批主单据地址
     * @param fa  flowApprove对象
     * @return
     */
    private static String emailDispose(String url, FlowApprove fa) throws IOException {
//        邮箱模板
        InputStream parentInput = null;
        parentInput = new FileInputStream(WebParam.assetsPath + "js" + File.separator + "templete" + File.separator + "emailNotifyFrame.html");
        Document parentD = Jsoup.parse(parentInput, "utf-8", "http://" + WebParam.NOTIFY_IP);
        parentD.getElementById("path").attr("href", url);
        parentD.getElementById("path").text(fa.getMessage().getTitle());
        parentD.getElementById("source").text("请认准地址哦，以免被不法分子利用，我们的ip地址" );
        parentD.getElementById("source").attr("href", url);
        parentInput.close();
        return parentD.outerHtml();

    }

    private void notify(Staff staff, String title, String content) {
        Mail mail = new Mail();
        mail.setSubject(title);
        mail.setMessage(content);
        mail.setReceiver(staff.getEmail());
        MailUtil.send(mail);
    }
}
