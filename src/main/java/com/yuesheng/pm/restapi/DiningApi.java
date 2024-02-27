package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.model.ResponseModel;
import com.yuesheng.pm.model.ResponsePage;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import com.yuesheng.pm.util.ExcelParse;
import com.yuesheng.pm.util.MyWebSocketHandle;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/dining")
public class DiningApi extends BaseApi {

    @Autowired
    private DiningExpenseService diningExpenseService;

    @Autowired
    private ProStaffBalanceHistoryService historyService;

    @Autowired
    private ProWorkCheckService workCheckService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private FlowNotifyService notifyService;


    /**
     * 消费
     *
     * @param history 消费信息
     * @return
     */
    @PostMapping
    public ResponseModel dining(@RequestBody ProStaffBalanceHistory history,@SessionAttribute(Constant.SESSION_KEY)Staff deviceStaff) throws Exception {
        ProStaffAccount account = null;
        try {
            account = diningExpenseService.dining(history.getStaff().getId());
            if (account != null) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("type", "diningSuccess");
                result.put("msg", "消费成功,余额:" + account.getBalance());
                if (account.getBalance() <= 5) {
                    List<Staff> staff = new ArrayList<>();
                    staff.add(staffService.getStaffById(history.getStaff().getId()));
                    result.put("title", "余额提醒");
                    result.put("mTitle", "余额提醒");
                    result.put("content", "剩余余额：" + account.getBalance() + ",请及时充值");
                    result.put("url", "");
                    notifyService.msgNotify(staff, result);
                }
                MyWebSocketHandle.sendMsg(history.getStaff().getId(), JSON.toJSONString(result));
            }
        } catch (Exception e) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("type", "diningSuccess");
            result.put("msg", e.getMessage());
            MyWebSocketHandle.sendMsg(history.getStaff().getId(), JSON.toJSONString(result));
            throw e;
        }
        return new ResponseModel(account);
    }

    @Operation(description = "补充指定日期消费")
    @PostMapping("diningByDate")
    public ResponseModel diningByDate(@RequestBody ProStaffBalanceHistory history) throws Exception {
        diningExpenseService.diningByDate(history.getStaff().getId(), history.getDatetime(), history.getMoney());
        return ResponseModel.ok(history);
    }


    /**
     * 查询餐厅当日消费记录
     *
     * @return
     */
    @GetMapping("toDayList")
    public ResponseModel getToDayList() {
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) 1);
        String startDate = DateUtil.getDate() + " 00:00:00";
        String endDate = DateUtil.getDate() + " 23:59:59";
        query.setRemark(Constant.DINING_STR);
        return new ResponseModel(historyService.queryAll(query, startDate, endDate));
    }

    /**
     * 查询餐厅所有消费记录
     *
     * @return
     */
    @GetMapping("list")
    public ResponsePage list(String startDate,
                             String endDate,
                             Integer page,
                             Integer itemsPerPage,
                             String sortBy,
                             String staffId,
                             String searchText,
                             Boolean sortDesc,
                             int type) {
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) type);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        return new ResponsePage((Page) historyService.queryAll(query, startDate, endDate));
    }


    @Operation(description = "导出充值/退款记录为excel")
    @GetMapping("exportExcel")
    public ResponseModel exportExcel(String staffId,
                                     Integer page,
                                     Integer itemsPerPage,
                                     String sortBy,
                                     Boolean sortDesc,
                                     String searchText,
                                     String startDate,
                                     String endDate,
                                     int type) {
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) type);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, 5000, sortBy, sortDesc);
        List<ProStaffBalanceHistory> histories = historyService.queryAll(query, startDate, endDate);
        for (int i = 0;i < histories.size();i++){
            histories.get(i).setBalance(histories.get(i).getAfterBalance() - histories.get(i).getMoney());
            histories.get(i).setSection(staffService.getStaffById(histories.get(i).getStaff().getId()).getSection());
        }

        String fileName = null;
        if (type == 1){
            fileName = DateUtil.format(DateUtil.parse(startDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(endDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-消费记录.xlsx";
            fileName = ExcelParse.writeExcel(histories,
                    fileName,
                    new String[]{"Staff.Name", "Money", "Type", "Datetime", "Remark"},
                    ProStaffBalanceHistory.class);
        }else if (type == 2){
            fileName = DateUtil.format(DateUtil.parse(startDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(endDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-退款记录.xlsx";
            fileName = ExcelParse.writeExcel(histories,
                    fileName,
                    new String[]{"Staff.Name", "Section.Name","Type", "Money", "Balance","Datetime", "Remark"},
                    ProStaffBalanceHistory.class);
        }
        return new ResponseModel(fileName);
    }

    @Operation(description = "获取今日公司附近考勤总人数")
    @GetMapping("toDayStaffCount")
    public ResponseModel toDayStaffCount() {
        HashMap<String, Integer> result = new HashMap(16);
        String date = DateUtil.getDate();
        Integer count = workCheckService.getCountByDate(date);
        result.put(date, count);
        return new ResponseModel(result);
    }

    @Operation(description = "查询指定员工当日消费信息")
    @GetMapping("getByStaff/{staffId}")
    public ResponseModel getByStaff(@PathVariable String staffId) {
        return new ResponseModel(historyService.queryByStaff(staffId,DateUtil.getDate(),1));
    }

    @Operation(description = "查询每日消费汇总")
    @GetMapping("getDiningDayStatistics")
    public ResponsePage getDiningDayStatistics(String startDate,
                                      String endDate,
                                      Integer page,
                                      Integer itemsPerPage,
                                      String sortBy,
                                      String staffId,
                                      String searchText,
                                      Boolean sortDesc,
                                      int type){
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) type);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);

        return new ResponsePage((Page) historyService.selectDiningDayStatistics(query, startDate, endDate));
    }

    @Operation(description = "导出每日消费汇总为excel")
    @GetMapping("exportExcelDiningDayStatistics")
    public ResponseModel exportExcelDiningDayStatistics(String staffId,
                                     Integer page,
                                     Integer itemsPerPage,
                                     String sortBy,
                                     Boolean sortDesc,
                                     String searchText,
                                     String startDate,
                                     String endDate,
                                     int type) {
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) type);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, 5000, sortBy, sortDesc);
        List<DiningDayStatistics> histories = historyService.selectDiningDayStatistics(query, startDate, endDate);
        Integer allTotalCountDay = 0;
        Double allTotalMoney = 0.00;
        if (histories != null){
            for (int i = 0;i < histories.size();i++){
                allTotalCountDay += histories.get(i).getCountDay();
                allTotalMoney += histories.get(i).getTotalMoney();
            }
        }

        DiningDayStatistics d = new DiningDayStatistics();
        d.setYmd("汇总");
        d.setCountDay(allTotalCountDay);
        d.setTotalMoney(allTotalMoney);

        histories.add(d);
        String fileName = DateUtil.format(DateUtil.parse(startDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(endDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-每日消费汇总.xlsx";
        fileName = ExcelParse.writeExcel(histories,
                fileName,
                new String[]{"Ymd", "CountDay", "TotalMoney"},
                DiningDayStatistics.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "查询个人消费汇总")
    @GetMapping("getPersonalDiningStatistics")
    public ResponsePage getPersonalDiningStatistics(String startDate,
                                                String endDate,
                                                Integer page,
                                                Integer itemsPerPage,
                                                String sortBy,
                                                String staffId,
                                                String searchText,
                                                Boolean sortDesc,
                                                int type){
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) type);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, itemsPerPage, sortBy, sortDesc);
        return new ResponsePage((Page) historyService.selectPersonalStatistics(query, startDate, endDate));
    }

    @Operation(description = "导出个人餐别消费汇总为excel")
    @GetMapping("exportExcelPersonalDiningStatistics")
    public ResponseModel exportExcelPersonalDiningStatistics(String staffId,
                                                        Integer page,
                                                        Integer itemsPerPage,
                                                        String sortBy,
                                                        Boolean sortDesc,
                                                        String searchText,
                                                        String startDate,
                                                        String endDate,
                                                        int type) {
        ProStaffBalanceHistory query = new ProStaffBalanceHistory();
        query.setType((byte) type);
        if (StringUtils.isNotBlank(staffId)) {
            Staff staff = new Staff();
            staff.setId(staffId);
            query.setStaff(staff);
        }
        query.setRemark(searchText);
        startPage(page, 5000, sortBy, sortDesc);
        List<PersonalDiningStatistics> histories = historyService.selectPersonalStatistics(query, startDate, endDate);
        Integer allTotalPersonalCounts = 0;
        Double allTotalMoney = 0.00;
        if (histories != null){
            for(int i = 0;i < histories.size();i++){
                allTotalPersonalCounts += histories.get(i).getPersonalCounts();
                allTotalMoney += histories.get(i).getTotalMoney();
            }
        }

        PersonalDiningStatistics p = new PersonalDiningStatistics();
        p.setStaffName("汇总");
        p.setPersonalCounts(allTotalPersonalCounts);
        p.setTotalMoney(allTotalMoney);

        histories.add(p);
        String fileName = DateUtil.format(DateUtil.parse(startDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(endDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-个人餐别消费汇总.xlsx";
        fileName = ExcelParse.writeExcel(histories,
                fileName,
                new String[]{"StaffName", "Staff.Section.Name", "PersonalCounts", "TotalMoney"},
                PersonalDiningStatistics.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "导出指定范围内最新余额")
    @GetMapping("exportExcelLastHistory")
    public ResponseModel exportExcelLastHistory(String startDate,
                                                String endDate){
        List<ProStaffBalanceHistory> histories = historyService.selectStaffLastHistory(startDate, endDate);
        histories.forEach(history -> {
            if (history.getType() == 0){
                history.setBalance(history.getAfterBalance() + history.getMoney());
            }else if (history.getType() == 1 || history.getType() == 2){
                history.setBalance(history.getAfterBalance() - history.getMoney());
            }
            history.setSection(staffService.getStaffById(history.getStaff().getId()).getSection());
        });

        Double totalBalance = 0.00;
        for (int i = 0;i < histories.size();i++){
            totalBalance += histories.get(i).getBalance();
        }

        ProStaffBalanceHistory p = new ProStaffBalanceHistory();
        Staff s = new Staff();
        s.setName("汇总");
        p.setStaff(s);
        p.setBalance(totalBalance);

        histories.add(p);

        String fileName = DateUtil.format(DateUtil.parse(startDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-" + DateUtil.format(DateUtil.parse(endDate), DateUtil.PATTERN_CLASSICAL_SIMPLE) + "-余额统计表.xlsx";
        fileName = ExcelParse.writeExcel(histories,
                fileName,
                new String[]{"Staff.Name", "Section.Name", "Balance"},
                ProStaffBalanceHistory.class);
        return new ResponseModel(fileName);
    }

    @Operation(description = "获取服务器时间")
    @GetMapping("getTime")
    public ResponseModel getTime(){
        return new ResponseModel(new Date());
    }
}
