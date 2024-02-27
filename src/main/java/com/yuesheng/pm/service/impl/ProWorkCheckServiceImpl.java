package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProWorkCheckMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 员工考勤表(ProWorkCheck)表服务实现类
 *
 * @author makejava
 * @since 2020-05-06 10:49:17
 */
@Service("proWorkCheckService")
public class ProWorkCheckServiceImpl implements ProWorkCheckService {
    @Autowired
    private ProWorkCheckMapper proWorkCheckDao;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ProStaffHwService proStaffHwService;
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private OvertimeService overtimeService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProWorkCheck queryById(String id) {
        return this.proWorkCheckDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProWorkCheck> queryAllByLimit(int offset, int limit) {
        return this.proWorkCheckDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proWorkCheck 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkCheck insert(ProWorkCheck proWorkCheck) {
        //查询新增考勤数据，是否有未设置时间的请假/加班数据，如果有，则更新该条数据的考勤时间即可
        List<ProWorkCheck> checks = this.queryAllByStaffIds(proWorkCheck.getDate(), proWorkCheck.getDate(), "'" + proWorkCheck.getStaffId() + "'");

        for (int i = 0; i < checks.size(); i++) {
            ProWorkCheck check = checks.get(i);
            if (StringUtils.isBlank(check.getTime())) {
                check.setTime(proWorkCheck.getTime());
                this.proWorkCheckDao.update(check);
                return check;
            }
        }

        this.proWorkCheckDao.insert(proWorkCheck);
        return proWorkCheck;
    }

    /**
     * 修改数据
     *
     * @param proWorkCheck 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkCheck update(ProWorkCheck proWorkCheck) {
        this.proWorkCheckDao.update(proWorkCheck);
        return this.queryById(proWorkCheck.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proWorkCheckDao.deleteById(id) > 0;
    }

    @Override
    public ProWorkCheck queryByParam(Map<String, Object> param) {
        return this.proWorkCheckDao.queryByParam(param);
    }

    @Override
    public List<ProWorkCheck> queryAllByDate(String startDate, String endDate, Integer isShow, Integer type) {
        return this.proWorkCheckDao.queryAllByDate(startDate, endDate, isShow, type);
    }

    @Override
    public List<ProWorkCheck> queryByStaff(Map<String, Object> params) {
        return this.proWorkCheckDao.queryByStaff(params);
    }

    @Override
    public List<ProWorkCheck> queryAllBySection(String startDate, String endDate, Integer isShow, String sectionName, String dataType) {
        return this.proWorkCheckDao.queryAllBySection(startDate, endDate, isShow, sectionName, dataType);
    }

    @Override
    public void syncWorkCheckForHanWang(List<ProWorkCheck> proWorkChecks) {
        for (ProWorkCheck workCheck : proWorkChecks) {
            ProStaffHw proStaffHw = proStaffHwService.queryById(workCheck.getStaffId());
            if (proStaffHw != null) {
                Staff staff = staffService.getStaffById(proStaffHw.getStaffId());
                if (staff == null) {
                    Logger.getLogger("mylog").info("staff is null : " + proStaffHw.getStaffId() + workCheck.getStaffId());
                    System.out.println("staff is null : " + proStaffHw.getStaffId() + workCheck.getStaffId());
                    continue;
                } else if (staff.getSection() == null) {
                    Logger.getLogger("mylog").info("staff section is null : " + staff.getName() + proStaffHw.getStaffId() + workCheck.getStaffId());
                    System.out.println("staff section is null : " + staff.getName() + proStaffHw.getStaffId() + workCheck.getStaffId());
                    return;
                } else if (workCheck.getStaffName() == null) {
                    Logger.getLogger("mylog").info("workCheck StaffName is null : " + staff.getName() + proStaffHw.getStaffId() + workCheck.getStaffId());
                    System.out.println("workCheck StaffName is null : " + staff.getName() + proStaffHw.getStaffId() + workCheck.getStaffId());
                    return;
                }
                try {
                    workCheck.setStaffName(workCheck.getStaffName() + "," + staff.getSection().getName());
                    workCheck.setStaffId(proStaffHw.getStaffId());
                    if (exists(workCheck, proStaffHw.getStaffId())) {
                        update(workCheck);
                        continue;
                    }
                    workCheck.setId(UUID.randomUUID().toString());
                    if (workCheck.getAttache() == null) {
                        workCheck.setAttache("");
                    }
                    insert(workCheck);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else {
//                该用户不存在
                System.out.println("该用户未在系统中:" + workCheck.getStaffId() + "," + workCheck.getStaffName());
            }
        }
    }

    @Override
    public Integer queryCount(String date, Integer type) {
        return proWorkCheckDao.queryCount(date, type);
    }

    @Override
    public Integer getCountByDate(String date) {
        Integer count = queryCount(date, 5);
        if (Objects.isNull(count)) {
            count = 0;
        }
        Integer wCount = queryCount(date, 2);
        if (Objects.isNull(wCount)) {
            wCount = 0;
        }
        return count + wCount;
    }

    @Override
    public List<ProWorkCheck> queryAllByStaffIds(String startDate, String endDate, String staffIds) {
        return proWorkCheckDao.queryAllByStaffIds(startDate, endDate, null, null, null, staffIds);
    }

    @Override
    public int updateLeave(ProWorkCheck update) {
        return proWorkCheckDao.updateLeave(update);
    }

    @Override
    public int updateOvertime(ProWorkCheck update) {
        return proWorkCheckDao.updateOvertime(update);
    }

    @Override
    public int syncLeaveAndOvertime(String startDate, String endDate) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("start", startDate);
        param.put("end", endDate);
        param.put("state", "1");
        PageHelper.startPage(1, 5000, "po05714 desc");
        List<Leave> leaves = leaveService.getApproveLeaveByParam(param);
        leaves.forEach(item -> {
            String staffId = item.getStaff().getId();
            String leaveStartDate = item.getStartDate();
            int days = DateUtil.getOffsetDays(DateUtil.parse(leaveStartDate, DateUtil.PATTERN_CLASSICAL_NORMAL), DateUtil.parse(leaveStartDate, DateUtil.PATTERN_CLASSICAL_NORMAL));
            if (days <= 0) {
                //同一天
                syncLeave(item, staffId, leaveStartDate);
            } else {
                //非同一天，遍历添加每天的请假数据
                for (int i = 0; i < days; i++) {
                    item.setLeaveHouse(0.0);
                    syncLeave(item, staffId, leaveStartDate);
                }
            }
        });
        param.put("approveStartDate", startDate);
        param.put("approveEndDate", endDate);
        param.put("approve", "2");
        PageHelper.startPage(1, 5000, "overtime desc");
        List<Overtime> overtimes = overtimeService.getByParam(param);
        overtimes.forEach(item -> {
            String staffId = item.getStaff().getId();
            String overtime = item.getOvertime();
            List<ProWorkCheck> workChecks = queryAllByStaffIds(overtime, overtime, "'" + staffId + "'");
            if (workChecks.isEmpty()) {
                ProWorkCheck proWorkCheck = new ProWorkCheck();
                proWorkCheck.setOvertime(item);
                List<ProWorkCheck> checks = proWorkCheckDao.queryAll(proWorkCheck);
                if (checks.isEmpty()) {
                    //没有考勤数据，添加新的
                    Staff staff = staffService.getStaffById(staffId);
                    //没有考勤数据，添加
                    proWorkCheck.setStaffId(staffId);
                    proWorkCheck.setDate(overtime);
                    proWorkCheck.setTime("");
                    proWorkCheck.setType((byte) 9);
                    proWorkCheck.setStaffName(staff.getName() + "," + staff.getSection().getName());
                    proWorkCheck.setId(UUID.randomUUID().toString());
                    proWorkCheck.setOvertime(item);
                    proWorkCheck.setAttache("");
                    insert(proWorkCheck);
                } else {
                    proWorkCheck = checks.get(0);
                    proWorkCheck.setOvertime(item);
                    updateOvertime(proWorkCheck);
                }
            } else {
                //有考勤数据，判断加班时间是上午还是下午
                Date d = DateUtil.parse(overtime + " " + item.getBegin(), DateUtil.PATTERN_CLASSICAL_NORMAL);
                int hour = DateUtil.getHour(d);
                ProWorkCheck update = null;
                if (hour <= 12) {
                    //上午加班
                    update = workChecks.get(0);
                    update.setOvertime(item);
                } else {
                    //下午加班
                    update = workChecks.get(workChecks.size() - 1);
                    update.setOvertime(item);
                }
                updateOvertime(update);
            }
        });
        return 1;
    }

    @Override
    public ProWorkCheck queryByDatetime(HashMap param) {
        return proWorkCheckDao.queryByDatetime(param);
    }

    @Override
    public boolean exists(ProWorkCheck workCheck, String staffId) {
        Map<String, Object> param = new HashMap<>();
        ProWorkCheck tempWc;
        param.put("date", workCheck.getDate());
        param.put("time", workCheck.getTime());
        param.put("staffId", staffId);
        tempWc = queryByParam(param);
        if (tempWc != null) {
            workCheck.setId(tempWc.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int updateType(ProWorkCheck check) {
        return proWorkCheckDao.updateType(check);
    }

    private void syncLeave(Leave item, String staffId, String leaveStartDate) {
        String day = leaveStartDate.substring(0, 10);
        List<ProWorkCheck> workChecks = queryAllByStaffIds(day, day, "'" + staffId + "'");
        if (Objects.isNull(item.getLeaveHouse())) {
            if (Objects.isNull(item.getLeaveNumber())) {
                item.setLeaveNumber(0.0);
            }
            item.setLeaveHouse(item.getLeaveNumber() * 8);
        }
        if (item.getLeaveHouse() == 0) {
            //重新计算
            int minutes = DateUtil.getOffsetMinutes(DateUtil.parse(item.getStartDate(), DateUtil.PATTERN_CLASSICAL_NORMAL),
                    DateUtil.parse(item.getEndDate(), DateUtil.PATTERN_CLASSICAL_NORMAL));
            double d = minutes / 60;
            double d2 = minutes % 60;
            item.setLeaveHouse(d + d2);
        }
        if (workChecks.isEmpty()) {
            ProWorkCheck proWorkCheck = new ProWorkCheck();
            proWorkCheck.setLeave(item);
            List<ProWorkCheck> checks = proWorkCheckDao.queryAll(proWorkCheck);
            Date d = DateUtil.parse(leaveStartDate, DateUtil.PATTERN_CLASSICAL_NORMAL);
            int hour = DateUtil.getHour(d);
            if (checks.isEmpty()) {
                Staff staff = staffService.getStaffById(staffId);
                //没有考勤数据，添加
                proWorkCheck.setStaffId(staffId);
                proWorkCheck.setDate(leaveStartDate.substring(0, leaveStartDate.indexOf(" ")));
                if (hour <= 12) {
                    //上午
                    proWorkCheck.setTime("");
                } else {
                    //下午
                    proWorkCheck.setTime(leaveStartDate.substring(leaveStartDate.indexOf(" ") + 1));
                }
                proWorkCheck.setType((byte) 8);
                proWorkCheck.setStaffName(staff.getName() + "," + staff.getSection().getName());
                proWorkCheck.setId(UUID.randomUUID().toString());
                proWorkCheck.setLeave(item);
                proWorkCheck.setAttache("");
                insert(proWorkCheck);
            } else {
                proWorkCheck = checks.get(0);
                proWorkCheck.setLeave(item);
                updateLeave(proWorkCheck);
            }
        } else {
            //判断上午还是下午
            Date d = DateUtil.parse(leaveStartDate, DateUtil.PATTERN_CLASSICAL_NORMAL);
            int hour = DateUtil.getHour(d);
            ProWorkCheck update = null;
            if (hour <= 12) {
                //上午
                update = workChecks.get(0);
                update.setLeave(item);
            } else {
                //    下午
                update = workChecks.get(workChecks.size() - 1);
                update.setLeave(item);
            }
            //更新考勤请假数据
            updateLeave(update);
        }
    }
}