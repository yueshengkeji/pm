package com.yuesheng.pm.util;

import com.alibaba.fastjson.JSON;
import com.yuesheng.pm.entity.ProWorkCheck;
import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.restapi.CheckWorkController;
import com.yuesheng.pm.restapi.ProDetailController;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.ProWorkCheckService;
import com.yuesheng.pm.service.SectionService;
import com.yuesheng.pm.service.StaffService;
import org.apache.log4j.LogManager;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 考勤数据定时器
 *
 * @author XiaoSong
 * @date 2020-05-12
 */
@Component
@Lazy(false)
public class WorkCheckTask {
    @Autowired
    private CheckWorkController checkWorkController;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    private ProDetailController proDetailController;
    @Autowired
    private ProWorkCheckService workCheckService;

    @Autowired
    private StaffService staffService;
    public static String[] managerIds = new String[]{"设计部", "工程管理中心", "财务部", "行政人事部", "质量安全售后服务部", "市场部", "采购部", "产品及系统集成部", "智能停车事业部"};

    /**
     * 考勤机数据同步任务，每晚23:00执行
     */
    // @Scheduled(cron = "0 00 23 * * *")
    public void sync() {

        String startDate = DateUtil.format(DateUtil.rollDay(new Date(), -1), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 00:00:00";
        String endDate = DateUtil.format(DateUtil.rollDay(new Date(), 1), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";

        try {
            syncWeiChartWorkCheck(startDate, endDate);
        } catch (Exception e) {
            //ignore this error
        }
        try {
            syncHangWangWorkCheck(startDate, endDate);
        } catch (Exception e) {
            //ignore this error
        }
        try {
            syncDianMaoWorkCheck(startDate, endDate);
        } catch (Exception e) {
            //ignore this error
        }
        try {
            syncShangTangWorkCheck(startDate, endDate);
        } catch (Exception e) {
            //ignore this error
        }

        //同步请假、加班数据到考勤表
        workCheckService.syncLeaveAndOvertime(startDate, endDate);
    }

    /**
     * 食堂消费终端考勤人数通知
     */
    // @Scheduled(cron = "0 0 9 ? * MON-FRI")
    public void diningNotify() {
        String date = DateUtil.getDate();
        String startDate = DateUtil.format(DateUtil.rollDay(new Date(), -1), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 00:00:00";
        String endDate = DateUtil.format(DateUtil.rollDay(new Date(), 1), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";
        syncWeiChartWorkCheck(startDate, endDate);

        Map<String, Object> sendData = new HashMap<>();
        Integer count = workCheckService.getCountByDate(date);
        sendData.put("data", count);
        sendData.put("type", "workCheckTotal");
        MyWebSocketHandle.sendMsg("3a789f55-6b13-4b1e-90c3-4e1732c3a4f5", JSON.toJSONString(sendData));
        MDC.put("params", "diningNotify 定时任务完成");
        LogManager.getLogger("mylog").info("diningNotify 定时任务完成");
    }


    // @Scheduled(cron = "0 45 8 * * ?")
    public void diningNotifyV2() {
        String date = DateUtil.getDate();
        String startDate = DateUtil.format(DateUtil.rollDay(new Date(), -1), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 00:00:00";
        String endDate = DateUtil.format(DateUtil.rollDay(new Date(), 1), DateUtil.PATTERN_CLASSICAL_SIMPLE) + " 23:59:59";
        syncWeiChartWorkCheck(startDate, endDate);

        Map<String, Object> sendData = new HashMap<>();
        Integer count = workCheckService.getCountByDate(date);
        sendData.put("data", count);
        sendData.put("type", "workCheckTotal");
        MyWebSocketHandle.sendMsg("3a789f55-6b13-4b1e-90c3-4e1732c3a4f5", JSON.toJSONString(sendData));
        MDC.put("params", "diningNotifyV2 定时任务完成");
        LogManager.getLogger("mylog").info("diningNotifyV2 定时任务完成");
    }

    /**
     * 同步员工车牌信息
     */
    // @Scheduled(cron = "0 00 11 * * ?")
    public void syncStaffPlate() {
        staffService.syncCarPlan();
    }

    /**
     * 采购、仓库、财务对账单数据同步，每年的1月自动运行
     */
    // @Scheduled(cron = "0 00 23 * * ?")
    public void syncProDetail() {
        int month = Integer.parseInt(DateUtil.format(new Date(), "M"));
        if (month == 1) {
            int year = Integer.parseInt(DateUtil.format(new Date(), "yyyy")) - 1;
            Staff staff = new Staff();
            staff.setName("1001");
            staff.setId("7C2CE1CB-A593-4A7A-9860-EEBBE2AEE8C2");
            proDetailController.asyncProDetails(year + "", staff);
        }
    }

    /**
     * 考勤校准推送通知，定时任务，每天11点执行(暂停使用)
     */
    // @Scheduled(cron = "0 00 11 * * 1-7")
    public void notifyCheck() {
        String startDate = DateUtil.format(DateUtil.getMonthStartTime(), DateUtil.PATTERN_CLASSICAL);
        String endDate = DateUtil.format(DateUtil.getMonthEndTime(), DateUtil.PATTERN_CLASSICAL);
        List<Staff> staff = new ArrayList<>();
        for (String sectionName : managerIds) {
            Section section = sectionService.getSectionList(sectionName).get(0);
            Map<String, Object> result = checkWorkController.getDataBySection(startDate, endDate, 1, section.getManagerid(), "1,2");
            List<ProWorkCheck> checks1 = (List<ProWorkCheck>) result.get("rows");
            if (checks1.size() > 0) {
                //通知
                Map<String, Object> param = new HashMap<>();
                param.put("title", "考勤校准");
                param.put("mTitle", "外出人员：" + checks1.get(0).getStaffName() + "等.");
                param.put("content", "请点击进行考勤校准！");
                param.put("url", "/managent/getPage?pageName=managerIndex&param=mpindex=personnel/WorkCheckApprove=pindex=1");
                Staff staff1 = new Staff();
                staff1.setId(section.getManagerid());
                staff.add(staff1);
                flowNotifyService.msgNotify(staff, param);
            }
        }
    }

    /**
     * 同步点卯考勤机数据
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     */
    public void syncDianMaoWorkCheck(String startDate, String endDate) {
        checkWorkController.syncDianMaoWorkCheck(startDate, endDate);
    }

    /**
     * 同步汉王考勤数据
     */
    public void syncHangWangWorkCheck(String startDate, String endDate) {
        Map<String, Object> result = checkWorkController.asyncHanWangWorkCheck(startDate, endDate);
        if (Integer.parseInt(result.get("state").toString()) == -1) {
            try {
//                等待1分钟后重试
                Thread.sleep(1000 * 60);
                checkWorkController.asyncHanWangWorkCheck(startDate, endDate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步微信考勤数据
     */
    public void syncWeiChartWorkCheck(String startDate, String endDate) {
        checkWorkController.syncWxCheckWork(startDate, endDate);
    }

    /**
     * 同步商汤考勤机数据
     *
     * @param startDate
     * @param endDate
     */
    public void syncShangTangWorkCheck(String startDate, String endDate) {
        checkWorkController.syncShangTangUser(startDate, endDate);
    }
}
