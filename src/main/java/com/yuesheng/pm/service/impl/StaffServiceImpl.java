package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.mapper.StaffMapper;
import com.yuesheng.pm.model.WxUser;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import jakarta.annotation.PostConstruct;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2016-08-06.
 * 职员服务对象实现类
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private ProStaffHwService proStaffHwService;
    @Autowired
    private ProWorkCheckShowService proWorkCheckShowService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StDeviceService stDeviceService;
    @Autowired
    private ProPlateService plateService;
    @Autowired
    private ProStaffAccountService balanceService;
    @Autowired
    private StaffAdditionInfoService staffAdditionInfoService;
    @Autowired
    private SystemConfigService configService;
    @Autowired
    private DingTalkStaffInfoService dingTalkStaffInfoService;
    @Autowired
    private DingTalkApiService dingTalkApiService;
    @Autowired
    private ProNotifyTypeService notifyTypeService;

    @PostConstruct
    public void checkPasswd(){
        List<Staff> staff = seek(null);
        staff.forEach(item->{
            Staff loginInfo = getUserLogin(item.getCoding());
            if(!Objects.isNull(loginInfo)){
                String ep = loginInfo.getPasswd();
                if(StringUtils.isNotBlank(ep)){
                    ep = StringUtils.replaceChars(ep,"\n","");
                    ep = StringUtils.replaceChars(ep,"\t","");
                    ep = StringUtils.replaceChars(ep,"\r","");
                }
                loginInfo.setPasswd(ep);
                staffMapper.updateUserPasswd(loginInfo);
            }
        });
    }

    @Override
    public Staff getStaffById(String Id) {
        return staffMapper.getStaffById(Id);
    }

    @Override
    public Staff getStaffByCoding(String coding) {
        return staffMapper.getStaffByCoding(coding);
    }

    @Override
    public List<Staff> getProStaff() {
        return staffMapper.getProStaff();
    }

    @Override
    public Staff login(String name, String passwd) {
        return staffMapper.login(name, passwd);
    }

    @Override
    public List<Staff> getStaffs() {
        PageHelper.startPage(1,1000);
        return staffMapper.getStaffs();
    }

    @Override
    public List<Staff> getStaffByCount(List<Count> counts) {
        return staffMapper.getStaffByCount(counts);
    }

    @Override
    public List<Staff> seek(String str) {
        return staffMapper.seek(str);
    }

    @Override
    public Staff login(String name) {
        PageHelper.startPage(1,1);
        return staffMapper.fastLogin(name);
    }

    @Override
    public HeadMsg getHeadMsg(String id) {
        PageHelper.startPage(1,1);
        return staffMapper.getHeadMsg(id);
    }

    @Override
    public void update(Staff staff) {
        staffMapper.update(staff);
        //更新职务信息
        Duty[] duty = staff.getDuty();
        if (!Objects.isNull(duty)) {
            Map<String, String> param = new HashMap<>(2);
            param.put("staffId", staff.getId());
            dutyService.deletePersonAll(staff.getId());
            for (int i = 0; i < duty.length; i++) {
                Duty d = duty[i];
                param.put("dutyId", d.getId());
                try {
                    dutyService.insertPerson(param);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //更新角色信息
        Role[] roles = staff.getRole();
        if (!Objects.isNull(roles)) {
            roleService.deletePersonAll(staff.getId());
            for (int i = 0; i < roles.length; i++) {
                Role r = roles[i];
                roleService.insert(r.getCoding(), staff);
            }
        }
        //判断员工是否绑定考勤机
        ProStaffHw staffHw = proStaffHwService.queryByStaffId(staff.getId());
        if (Objects.isNull(staffHw)) {
            //绑定考勤机
            insertStaffToWokDevice(staff);
        }
        if (StringUtils.isNotBlank(staff.getHead())) {
            ProStaffHw hw = new ProStaffHw();
            hw.setHead(staff.getHead());
            hw.setStaffId(staff.getId());
            proStaffHwService.update(hw);
        }
        if (!StringUtils.isBlank(staff.getPasswd())) {
            try {
                staff.setPasswd(new RSAEncrypt().decrypt(staff.getPasswd()));
                updateUserPasswd(staff);
            } catch (Exception ignore) {

            }
        }

        if (staff.getIsLogin() == 1) {
            //离职员工，删除所有角色和职务
            dutyService.deletePersonAll(staff.getId());
            roleService.deletePersonAll(staff.getId());
        }
    }

    @PostConstruct
    public void checkUnLogin() {
        try {
            List<Staff> staff = queryUnLogin("");
            staff.forEach(item -> {
                dutyService.deletePersonAll(item.getId());
                roleService.deletePersonAll(item.getId());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public Map<String, Object> insert(Staff staff) {
        Map<String, Object> result = new HashMap<>(16);
        if (verifyStaff(staff)) {
            Staff s = getStaffByUserName(staff.getName());
            if (s == null) {  //登陆用户名不存在，可以添加
                staff.setDate(DateFormat.getDate());
                staff.setLastDate("");
                staffMapper.insert(staff);     //添加职员信息
                staff.setTime(DateFormat.getTime());        //设置用户最后登陆时间
                if (StringUtils.isBlank(staff.getUserName())) {
                    staff.setUserName(staff.getName());
                }
                try {
                    if (StringUtils.isBlank(staff.getPasswd())) {
                        staff.setPasswd(AESEncrypt.encryptBASE64("zm123".getBytes("UTF-8")));
                    } else {
                        staff.setPasswd(AESEncrypt.encryptBASE64(new RSAEncrypt().decrypt(staff.getPasswd()).getBytes("UTF-8")));
                    }
                } catch (Exception ignore) {

                }
                staffMapper.insertLoginInfo(staff);
                //添加职务集合
                Duty[] duties = staff.getDuty();
                if (duties != null) {
                    Map<String, String> param = new HashMap<>(16);
                    param.put("staffId", staff.getId());
                    for (Duty d : duties) {
                        if (StringUtils.isBlank(d.getId())) {
                            dutyService.insert(d);
                        }
                        param.put("dutyId", d.getId());
                        try {
                            dutyService.insertPerson(param);
                        } catch (SQLException e) {

                        }
                    }
                }
                //添加角色信息
                insertRole(staff);
                //添加职员信息到考勤机
                Map<String, Object> stResult = insertStaffToWokDevice(staff);
                //初始化职员钱包
                balanceService.insertByStaff(staff);
                //邀请至钉钉
                dingTalkApiService.createUser(staff);
                ProNotifyType type = new ProNotifyType();
                type.setStaffId(staff.getId());
                type.setWx(true);
                type.setDing(true);
                type.setType(true);
                notifyTypeService.insert(type);
                result.put("stDeviceResult", stResult);
                result.put("state", "1");
            } else {      //用户名已存在
                result.put("state", "0");
                result.put("msg", "登录用户名已存在,请重新指定登录用户名");
            }
        } else {
            staff.setId(null);
            result.put("state", "-1");
            result.put("msg", "-1");
        }
        result.put("module", staff);
        return result;
    }

    private void insertRole(Staff staff) {
        boolean isDefaultRole = true;
        String defaultRoleCode = null;
        Role[] roles = staff.getRole();
        SystemConfig sc = configService.queryByCoding(Constant.DEFAULT_ROLE_CODE);
        if (!Objects.isNull(sc)) {
            defaultRoleCode = sc.getValue();
        }
        if (!Objects.isNull(roles) && roles.length > 0) {
            for (int i = 0; i < roles.length; i++) {
                Role r = roles[i];
                if (!Objects.isNull(r)) {
                    if (StringUtils.equals(r.getCoding(), defaultRoleCode)) {
                        isDefaultRole = false;
                    }
                    roleService.insert(r.getCoding(), staff);
                }
            }
        }
        if (isDefaultRole && StringUtils.isNotBlank(defaultRoleCode)) {
            //添加默认角色
            roleService.insert(defaultRoleCode, staff);
        }
    }

    private Map<String, Object> insertStaffToWokDevice(Staff staff) {
        Map<String, Object> stResult = new HashMap<>(16);
        try {
            Attach attach = new Attach();
            attach.setFileName("staff-head.jpeg");
            attach.setFileBytes(Base64.getDecoder().decode(staff.getHead().replace("\r\n", "").toString().getBytes()));
            stResult = stDeviceService.addStaffTODevice(staff, attach);
            if (stResult != null) {
                stResult = JSON.parseObject(stResult.get("response").toString(), Map.class);
                if ("200".equals(stResult.get("code").toString())) {
                    JSONObject jsonObject = (JSONObject) stResult.get("data");
                    String id = jsonObject.get("id").toString();
                    staff.setHwDeviceId("1" + id + "9");
                    staff.setName(staff.getName());
                    insertRelation(staff);
                }
            }
        } catch (Exception ingore) {

        }
        return stResult;
    }

    @Override
    public ProStaffHw insertRelation(Staff s) {
        ProStaffHw staffHw = proStaffHwService.queryById(Integer.parseInt(s.getHwDeviceId()));
        if (staffHw == null) {
            ProStaffHw hw = new ProStaffHw();
            hw.setHead(s.getHead());
            hw.setId(s.getHwDeviceId());
            Staff isExists = login(s.getName());
            if (isExists != null) {
                hw.setStaffId(isExists.getId());
                try {
                    if (hw.getHead() == null) {
                        hw.setHead("");
                    }
//                    添加用户绑定
                    proStaffHwService.insert(hw);
//                    添加显示数据
                    ProWorkCheckShow show = new ProWorkCheckShow();
                    show.setStaffId(hw.getStaffId());
                    show.setIsShow(1);
                    proWorkCheckShowService.insert(show);
                    return hw;
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            } else {
                return null;
            }
        } else {
            return staffHw;
        }
        return null;
    }

    @Override
    public void insertLoginInfo(Staff staff) {
        staffMapper.insertLoginInfo(staff);
    }

    @Override
    public Staff getStaffByUserName(String userName) {
        PageHelper.startPage(1,1);
        return staffMapper.getStaffByUserName(userName);
    }

    @Override
    public Map<String, Object> updateUserPasswd(Staff staff) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("module", staff);
        if (verifyStaff(staff)) {
            try {
                String passwd = staff.getPasswd().replace("\r", "");
                passwd = passwd.replace("\n", "");
                //对用户密码进行加密处理
                staff.setPasswd(AESEncrypt.encryptBASE64(passwd.getBytes()));
                staffMapper.updateUserPasswd(staff);
                result.put("state", "1");
                result.put("msg", "更新成功");
            } catch (Exception e) {
                result.put("msg", e.getMessage());
                result.put("state", "500");
            }
        } else {
            result.put("state", "-1");
        }
        return result;
    }

    @Override
    public Staff loginIn(String userName, String s) {
        PageHelper.startPage(1,1);
        return staffMapper.loginIn(userName, s);
    }

    @Override
    public void updateIsLogin(Staff staff) {
        staffMapper.updateIsLogin(staff);
    }

    @Override
    public List<Staff> queryUnLogin(String str) {
        return staffMapper.queryUnLogin(str);
    }

    @Override
    public int updateLoginTime(Staff loginStaff) {
        return staffMapper.updateLoginTime(loginStaff);
    }

    @Override
    public int updateStaffPlate(String plate, String id) {
        ProPlate proPlate = new ProPlate();
        proPlate.setPlate(plate);
        List<ProPlate> proPlates = plateService.queryAll(proPlate);
        if (proPlates.isEmpty()) {
            proPlate.setStaffId(id);
            proPlate.setId(UUID.randomUUID().toString());
            plateService.insert(proPlate);
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int syncUserInfo(String wxUserId, String staffId) {
        WxUser wxUser = CompanyWxUtil.getUserData(wxUserId);
        if (!Objects.isNull(wxUser) && StringUtils.isNotBlank(wxUser.getMobile())) {
            Staff staff = getStaffById(staffId);
            if (!Objects.isNull(staff)) {
                if (StringUtils.isNotBlank(wxUser.getEmail())) {
                    staff.setEmail(wxUser.getEmail());
                }
                staff.setPasswd(null);
                staff.setDuty(null);
                staff.setRole(null);
                staff.setTel(wxUser.getMobile());
                update(staff);
            }
        }
        return 1;
    }

    @Override
    public List<Staff> getNoBindWxList() {
        return staffMapper.getNoBindWxList();
    }

    @Override
    public boolean syncUserInfo() {
        //查找已绑定的微信用户
        List<StaffAdditionInfo> additionInfos = staffAdditionInfoService.getAllWxUser();
        additionInfos.forEach(info -> {
            syncUserInfo(info.getWxUserId(), info.getStaffId());
        });
        //查找未绑定的微信用户
        List<Staff> noBindList = getNoBindWxList();
        StringBuffer sb = new StringBuffer();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        noBindList.forEach(item -> {
            String name = item.getName();
            if (StringUtils.isNotBlank(name)) {
                for (int i = 0; i < name.length(); i++) {
                    try {
                        String[] strs = PinyinHelper.toHanyuPinyinStringArray(name.charAt(i), format);
                        if (!Objects.isNull(strs) && strs.length > 0) {
                            sb.append(strs[0]);
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {

                    }
                }
                WxUser wxUser = CompanyWxUtil.getUserData(sb.toString());
                if (!Objects.isNull(wxUser) && StringUtils.isNotBlank(wxUser.getUserid())) {
                    StaffAdditionInfo sai = staffAdditionInfoService.getStaffAdditionInfoByWxUserId(wxUser.getUserid());
                    if (Objects.isNull(sai)) {
                        //未发现重名用户，可以自动关联
                        sai = new StaffAdditionInfo();
                        sai.setWxUserId(wxUser.getUserid());
                        sai.setStaffId(item.getId());
                        sai.setSystemId(CompanyWxUtil.APPID);
                        sai.setSystemName(CompanyWxUtil.AGENTID);
                        staffAdditionInfoService.insert(sai);
                        //同步微信用户信息
                        syncUserInfo(wxUser.getUserid(), item.getId());
                    }
                }
                sb.delete(0, sb.length());
            }
        });
        return false;
    }

    public void syncDingUser(String dingTalkUserId, String staffId) {
        OapiV2UserGetResponse.UserGetResponse userByUserId = dingTalkApiService.getUserByUserId(dingTalkUserId);
        if (!Objects.isNull(userByUserId) && StringUtils.isNotBlank(userByUserId.getMobile())) {
            Staff staff = getStaffById(staffId);
            if (!Objects.isNull(staff)) {
                if (StringUtils.isNotBlank(userByUserId.getEmail())) {
                    staff.setEmail(userByUserId.getEmail());
                }
                staff.setPasswd(null);
                staff.setDuty(null);
                staff.setRole(null);
                staff.setTel(userByUserId.getMobile());
                update(staff);
            }
        }
    }

    @Override
    public void syncDingTalkUser() {
        //已绑钉钉用户
        List<DingTalkStaffInfo> dingTalkStaffInfos = dingTalkStaffInfoService.selectAll();
        dingTalkStaffInfos.forEach(item -> {
            syncDingUser(item.getDingTalkUserId(), item.getStaffId());
        });
        //未绑钉钉用户
        List<Staff> noBindDingList = staffMapper.getNoBindDingList();
        noBindDingList.forEach(item -> {
            String userIdByMobile = dingTalkApiService.getUserIdByMobile(item.getTel());
            if (userIdByMobile != null) {
                DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByDing(userIdByMobile);
                if (Objects.isNull(dingTalkStaffInfo)) {
                    dingTalkStaffInfo = new DingTalkStaffInfo();
                    dingTalkStaffInfo.setStaffId(item.getId());
                    dingTalkStaffInfo.setDingTalkUserId(userIdByMobile);
                    dingTalkStaffInfoService.insertDing(dingTalkStaffInfo);

                    syncDingUser(userIdByMobile, item.getId());
                }
            }

        });

    }

    @Override
    public void syncCarPlan() {
        List<Staff> staffList = queryUnLogin("");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("us", "admin");
        linkedHashMap.put("ps", "zm123");
        staffList.forEach(item -> {
            linkedHashMap.put("name", item.getName().replaceAll(" ", ""));
            String json = null;
            try {
                json = NetRequestUtil.sendGetRequest("http://192.168.3.112:8081/open/ts/getFixedCarByName?us=admin&ps=zm123", linkedHashMap);
                JSONObject jsonObject = JSONObject.parseObject(json);
                JSONArray array = jsonObject.getJSONArray("data");
                if (!Objects.isNull(array)) {
                    array.forEach(plate -> {
                        JSONObject jo = (JSONObject) plate;
                        String temp = jo.getString("plate");
                        updateStaffPlate(temp, item.getId());
                        // NetRequestUtil.sendGetRequest("http://192.168.3.112:8081/open/ts/removeFixedCar?plate=" + temp + "&us=admin&ps=zm123");
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //离职，清空车牌信息
        });
    }

    @Override
    public int updateMobileInfo(Staff staff) {
        Staff update = new Staff();
        update.setId(staff.getId());
        update.setMobileModel(staff.getMobileModel());
        update.setMobileBrand(staff.getMobileBrand());
        return staffMapper.update(update);
    }

    @Override
    public void headToFile() {
        List<Staff> staff = seek(null);
        staff.forEach(item -> {
            String base64Head = proStaffHwService.queryHeadByStaffId(item.getId());
            if (!Objects.isNull(base64Head)) {
                byte[] head = Base64.getMimeDecoder().decode(base64Head.getBytes());
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(WebParam.assetsPath + "img" + File.separator + item.getId() + ".jpg");
                    IOUtils.write(head, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.closeQuietly(fos);
                }
            }
        });
    }

    @Override
    public Staff getUserLogin(String coding) {
        return staffMapper.getUserLogin(coding);
    }

    private boolean verifyStaff(Staff staff) {
        if (staff == null) {
            return false;
        }
        if (staff.getId() == null || "".equals(staff.getId())) {
            staff.setId(UUID.randomUUID().toString());
        }
        if (staff.getSection() == null) {
            Section s = new Section();
            s.setId("");
            staff.setSection(s);
        }
        if (staff.getTel() == null) {
            staff.setTel("");
        }
        if (staff.getEmail() == null) {
            staff.setEmail("");
        }
        if (staff.getCoding() == null || "".equals(staff.getCoding())) {
            String coding = getCoding();
            staff.setCoding(coding);
        }
        return true;
    }

    private String getCoding() {
        String coding = AESEncrypt.getFixLenthString(7);
        Staff n = staffMapper.getStaffByCoding(coding);
        if (n != null) {
            return getCoding();
        } else {
            return coding;
        }
    }
}
