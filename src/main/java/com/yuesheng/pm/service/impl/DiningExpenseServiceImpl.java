package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.config.DiningTimeConfig;
import com.yuesheng.pm.entity.ProStaffAccount;
import com.yuesheng.pm.entity.ProStaffBalanceHistory;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.service.DiningExpenseService;
import com.yuesheng.pm.service.ProStaffAccountService;
import com.yuesheng.pm.service.ProStaffBalanceHistoryService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class DiningExpenseServiceImpl implements DiningExpenseService {
    private static Double money = 5.0;

    @Autowired
    private ProStaffAccountService balanceService;
    @Autowired
    private ProStaffBalanceHistoryService historyService;
    @Autowired
    private StaffService staffService;

    @Override
    public synchronized ProStaffAccount dining(String staffId) throws Exception {
        if (compareTime() == true) {
            throw new Exception("未到就餐时间");
        }
        Staff staff = staffService.getStaffById(staffId);
        if (Objects.isNull(staff)) {
            throw new Exception("员工不存在");
        }
        //查询员工当日是否已经有过进餐
        ProStaffBalanceHistory history = historyService.queryByStaff(staffId, DateUtil.getDate(), 1);
        if (Objects.isNull(history)) {
            history = new ProStaffBalanceHistory();
            history.setMoney(money);
            history.setRemark(Constant.DINING_STR);
            history.setStaff(staff);
            history.setType((byte) 1);
            Staff operate = new Staff();
            operate.setName("食堂终端");
            operate.setId("1001");
            return balanceService.subtract(history, operate);
        } else if (DateUtil.getOffsetSeconds(DateUtil.parse(history.getDatetime()), new Date()) < 5) {
            //3秒内重复请求扣费
            throw new Exception("重复请求");
        } else {
            throw new Exception("该员工今日已消费,禁止二次消费");
        }
    }

    public synchronized ProStaffAccount diningByDate(String staffId, String date, Double money) throws Exception {
        Staff staff = staffService.getStaffById(staffId);
        if (Objects.isNull(staff)) {
            throw new Exception("员工不存在");
        }
        //查询员工当日是否已经有过进餐
        ProStaffBalanceHistory history = historyService.queryByStaff(staffId, date.substring(0, 10), 1);
        if (Objects.isNull(history)) {
            history = new ProStaffBalanceHistory();
            history.setMoney(money);
            history.setRemark(Constant.DINING_STR);
            history.setStaff(staff);
            history.setType((byte) 1);
            history.setDatetime(date);
            Staff operate = new Staff();
            operate.setName("食堂终端");
            operate.setId("1001");
            return balanceService.subtract(history, operate);
        } else if (DateUtil.getOffsetSeconds(DateUtil.parse(history.getDatetime()), new Date()) < 5) {
            //3秒内重复请求扣费
            throw new Exception("重复请求");
        } else {
            throw new Exception("该员工今日已消费,禁止二次消费;" + staffId + ";" + date);
        }
    }

    public boolean compareTime() {
        Date date = new Date();
        Integer nowTime = Integer.valueOf(date.getHours() * 3600 + date.getMinutes() * 60);
        Integer compareTime = Integer.valueOf(DiningTimeConfig.HOUR) * 3600 + Integer.valueOf(DiningTimeConfig.MINUTE) * 60;
        if (nowTime < compareTime) {
            return true;
        } else {
            return false;
        }
    }
}
