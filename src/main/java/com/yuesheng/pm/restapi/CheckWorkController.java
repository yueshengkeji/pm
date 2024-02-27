package com.yuesheng.pm.restapi;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import static com.yuesheng.pm.util.CompanyWxUtil.APPID;

/**
 * @author XiaoSong
 * @date 2020/04/28
 * 考勤管理
 */
@Controller
@RequestMapping("/managent/checkWork")
public class CheckWorkController {
    @Autowired
    private StaffAdditionInfoService staffAdditionInfoService;
    @Autowired
    private ProStaffHwService proStaffHwService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ProWorkCheckService proWorkCheckService;
    @Autowired
    protected ProWorkCheckShowService proWorkCheckShowService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private StDeviceService stDeviceService;
    private static int syncNum = 10;

    /**
     * 同步企业微信考勤数据
     *
     * @return
     */
    @RequestMapping("/syncWeiChar")
    @ResponseBody
    public Map<String, Object> syncWxCheckWork(String start, String end) {

        HashMap result = new HashMap();
        try {
            String token = CompanyWxUtil.getToken(APPID, CompanyWxUtil.WORK_SECRET);
            List<StaffAdditionInfo> sinfo = staffAdditionInfoService.getAllWxUser();
            int x = 0;
            String[] users = new String[100];
            List<ProWorkCheck> proWorkChecks;
            for (StaffAdditionInfo wxInfo : sinfo) {
                result.put(wxInfo.getWxUserId(), wxInfo.getStaffId());
                users[x] = wxInfo.getWxUserId();
                if (x >= (users.length - 1)) {
                    proWorkChecks = CompanyWxUtil.getWorkCheckData(token, start, end, JSONObject.toJSONString(users));
                    syncWorkCheckForWx(proWorkChecks, result);
                    x = 0;
                    users = new String[100];
                    continue;
                }
                x++;
            }
            if (x > 0) {
                proWorkChecks = CompanyWxUtil.getWorkCheckData(token, start, end, JSONObject.toJSONString(users));
                syncWorkCheckForWx(proWorkChecks, result);
            }
            result.clear();
            result.put("state", "1");
            result.put("msg", "同步成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.clear();
            result.put("error", e.getMessage());
            result.put("msg", e.getMessage());
            result.put("state", "-1");
            return result;
        }
    }

    /**
     * 同步汉王打卡机考勤数据
     *
     * @return
     */
    @RequestMapping("/asyncHanWangWorkCheck")
    @ResponseBody
    public Map<String, Object> asyncHanWangWorkCheck(String start, String end) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ProWorkCheck> proWorkChecks = HanWangDeviceUtil.getWorkCheckData("192.168.2.2", "2018", 9922, start, end);
            syncWorkCheckForHanWang(proWorkChecks);
            Thread.sleep(1000);
            List<ProWorkCheck> proWorkChecks2 = HanWangDeviceUtil.getWorkCheckData("192.168.2.3", "2018", 9922, start, end);
            syncWorkCheckForHanWang(proWorkChecks2);
            /*
            考勤机设备数据获取失败时重新同步，连续10次未获取到则同步失败
             */
            if ((proWorkChecks.size() <= 0 || proWorkChecks2.size() <= 0) && syncNum < 10) {
                syncNum++;
                asyncHanWangWorkCheck(start, end);
            } else if (syncNum >= 10 && (proWorkChecks.size() <= 0 || proWorkChecks2.size() <= 0)) {
                syncNum = 0;
                result.put("state", -1);
                result.put("msg", "考勤机连接失败，请稍后再试!");
                return result;
            }
            syncNum = 0;
            result.put("state", 1);
            result.put("msg", "同步成功");
        } catch (InterruptedException e) {
            result.put("state", -1);
            result.put("msg", e.getMessage());
            result.put("error", e.getMessage());
        }
        return result;
    }

    /**
     * 同步点卯考勤机数据
     *
     * @param start 开始日期
     * @param end   截止日期
     * @return
     */
    @RequestMapping("/syncDianMaoWorkCheck")
    @ResponseBody
    public Map<String, Object> syncDianMaoWorkCheck(String start, String end) {
        Map<String, Object> result = new HashMap<>();
        List<ProWorkCheck> checks = DianMaoWorkCheck.getDianMaoWorkChecks(start, end);
        syncDianMao(checks);
        return result;
    }

    /**
     * 同步汉王设备用户信息集合
     *
     * @return
     */
    @RequestMapping("/syncHanWangUser")
    @ResponseBody
    public Map<String, Object> syncHanWangUser() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<String> userIds = HanWangDeviceUtil.getUserIds("192.168.2.2", "2018", 9922);
            for (String hwId : userIds) {
                if (proStaffHwService.queryById(hwId) == null) {
                    Staff staff = HanWangDeviceUtil.getUserInfo("192.168.2.2", "2018", 9922, hwId);
                    insertRelation(staff);
                }
            }
            result.put("state", "1");
            result.put("msg", "同步成功");
        } catch (InterruptedException e) {
            result.put("state", "-1");
            result.put("msg", e.getMessage());
            result.put("error", e.getMessage());
        }
        return result;
    }

    /**
     * 同步点卯设备用户信息集合
     *
     * @return
     */
    @RequestMapping("/syncDianMaoUser")
    @ResponseBody
    public Map<String, Object> syncDianMaoUser() {
        HashMap<String, Object> result = new HashMap<>(16);
        result.put("state", "1");
        result.put("msg", "同步成功");
        List<Staff> staffList = DianMaoWorkCheck.getDianMaoUser();
        syncUser(staffList);
        return result;
    }

    /**
     * 同步商汤考勤机数据
     *
     * @return
     */
    @RequestMapping("/syncShangTangWorkCheck")
    @ResponseBody
    public Map<String, Object> syncShangTangUser(String start, String end) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("state", "1");
        result.put("msg", "同步成功");
        List<ProWorkCheck> checks = stDeviceService.getStWorkCheckData(DateUtil.parse(start, DateUtil.PATTERN_CLASSICAL).getTime() / 1000 + "", DateUtil.parse(end, DateUtil.PATTERN_CLASSICAL).getTime() / 1000 + "");
        for (ProWorkCheck check : checks) {
            ProStaffHw proStaffHw = proStaffHwService.queryById(check.getStaffId());
            if (proStaffHw == null) {
                //添加
                Staff staff = new Staff();
                staff.setHwDeviceId(check.getStaffId());
                staff.setName(check.getStaffName());
                insertRelation(staff);
            }
        }
        syncWorkCheckForHanWang(checks);
        return result;
    }

    @RequestMapping("/getHead/{signBgAvatar}")
    @ResponseBody
    public String getHead(@PathVariable String signBgAvatar) {
        FileOutputStream outputStream = null;
        try {
            String fileName = WebParam.assetsPath + signBgAvatar + ".png";
            File file = new File(fileName);
            if (file.exists()) {
                return "/assets/" + signBgAvatar + ".png";
            }
            outputStream = new FileOutputStream(fileName);
            byte[] bytes = NetRequestUtil.sendGetRequest("https://link.bi.sensetime.com/v1/image/2/" + signBgAvatar);
            IOUtils.write(bytes, outputStream);
            return "/assets/" + signBgAvatar + ".png";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return "";
    }

    /**
     * 获取员工头像数据
     *
     * @param staffId 员工id
     * @return
     */
    @RequestMapping("/getStaffHead")
    @ResponseBody
    public Map<String, String> getStaffHead(String staffId) {
        Map<String, String> result = new HashMap<>(2);
        result.put("state", "1");
        result.put("data", proStaffHwService.queryHeadByStaffId(staffId));
        return result;
    }

    /**
     * 获取数据
     *
     * @param pageSize
     * @param pageNumber
     * @param searchText
     * @param sortOrder
     * @param sortName
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/getData")
    @ResponseBody
    public Map<String, Object> getData(String pageSize, String pageNumber, String searchText, String sortOrder, String sortName, String startDate, String endDate, Integer isShow, Integer type) {
        Map<String, Object> result = new HashMap<>();
        result.put("rows", proWorkCheckService.queryAllByDate(startDate, endDate, isShow, type));
        return result;
    }

    /**
     * 获取指定职员指定日期的考勤数据
     *
     * @param staffId 职员id
     * @param date    日期
     * @return
     */
    @RequestMapping("/getDataByStaff")
    @ResponseBody
    public List<ProWorkCheck> getDataByStaff(String staffId, String date) {
        Map<String, Object> result = new HashMap<>();
        result.put("staffId", staffId);
        result.put("date", date);
        return proWorkCheckService.queryByStaff(result);
    }

    /**
     * 通过指定条件过去考勤数据集合
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @param type      考勤类型
     * @param staffId   职员id
     * @param staffName 职员姓名
     * @return
     */
    @RequestMapping("/getDataByParam")
    @ResponseBody
    public List<ProWorkCheck> getDataByParam(String startDate, String endDate, Integer type, String staffId, String staffName) {
        Map<String, Object> result = new HashMap<>();
        result.put("staffId", staffId);
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("type", type);
        result.put("staffName", staffName);
        return proWorkCheckService.queryByStaff(result);
    }

    /**
     * 获取部门下考勤数据集合
     *
     * @param startDate 开始日期
     * @param endDate   截止日期
     * @param isShow    是否显示
     * @param staffId   职员id
     * @param dataType  考勤类型
     * @return
     */
    @RequestMapping("/getDataBySection")
    @ResponseBody
    public Map<String, Object> getDataBySection(String startDate, String endDate, Integer isShow, String staffId, String dataType) {
        Map<String, Object> result = new HashMap<>();
        List<Section> sections = sectionService.getSectionByManagerId(staffId);
        List<ProWorkCheck> checks = new ArrayList<>();
        for (Section s : sections) {
            checks.addAll(proWorkCheckService.queryAllBySection(startDate, endDate, isShow, s.getName(), dataType));
        }
        result.put("rows", checks);
        return result;
    }

    /**
     * 校准考勤数据
     *
     * @param work
     * @return
     */
    @RequestMapping("/checkTime")
    @ResponseBody
    public Map<String, Object> checkTime(ProWorkCheck work) {
        Map<String, Object> result = new HashMap<>();
        if (work == null || StringUtils.isBlank(work.getId())) {
            result.put("state", "-1");
            result.put("msg", "数据不存在");
        } else {
            proWorkCheckService.update(work);
            result.put("state", "1");
            result.put("msg", "操作成功");
        }
        return result;
    }

    /**
     * 通过分组获取考勤数据
     *
     * @param groupName   分组名称
     * @param filterValue 过来条件
     * @return
     */
    @RequestMapping("/getWorkCheckDataByGroup")
    @ResponseBody
    public List<ProWorkCheck> getWorkCheckDataByGroup(String groupName, String filterValue) {

        return null;
    }

    @RequestMapping("/getById")
    @ResponseBody
    public ProWorkCheck getById(String id) {
        return proWorkCheckService.queryById(id);
    }

    /**
     * 汉王考勤机数据添加
     *
     * @param proWorkChecks
     */
    public void syncWorkCheckForHanWang(List<ProWorkCheck> proWorkChecks) {
        proWorkCheckService.syncWorkCheckForHanWang(proWorkChecks);
    }

    /**
     * 微信考勤数据添加
     *
     * @param proWorkChecks
     * @param staffIdMap    微信用户id与职员id键值对
     */
    private void syncWorkCheckForWx(List<ProWorkCheck> proWorkChecks, Map<String, String> staffIdMap) {
        for (ProWorkCheck workCheck : proWorkChecks) {
            workCheck.setStaffId(staffIdMap.get(workCheck.getStaffId()));
            if (exists(workCheck, workCheck.getStaffId())) {
                continue;
            } else {
                try {
                    workCheck.setId(UUID.randomUUID().toString());
                    Staff staff = staffService.getStaffById(workCheck.getStaffId());
                    workCheck.setStaffName(staff.getName() + "," + staff.getSection().getName());
                    if (StringUtils.isBlank(workCheck.getAttache())) {
                        workCheck.setAttache("");
                    }
                    proWorkCheckService.insert(workCheck);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 同步点卯考勤数据
     *
     * @param checks
     */
    private void syncDianMao(List<ProWorkCheck> checks) {
        syncWorkCheckForHanWang(checks);
    }

    private boolean exists(ProWorkCheck workCheck, String staffId) {
        Map<String, Object> param = new HashMap<>();
        ProWorkCheck tempWc;
        param.put("date", workCheck.getDate());
        param.put("time", workCheck.getTime());
        param.put("staffId", staffId);
        tempWc = proWorkCheckService.queryByParam(param);
        if (tempWc != null) {
            workCheck.setId(tempWc.getId());
            return true;
        } else {
            return false;
        }
    }

    private void syncUser(List<Staff> staffList) {
        for (Staff s : staffList) {
            insertRelation(s);
        }
    }

    public void insertRelation(Staff s) {
        proStaffHwService.insertRelation(s);
    }
}
