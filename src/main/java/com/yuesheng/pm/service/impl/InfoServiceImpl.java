package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Info;
import com.yuesheng.pm.entity.InfoState;
import com.yuesheng.pm.entity.Section;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.InfoMapper;
import com.yuesheng.pm.restapi.ManagerLogin;
import com.yuesheng.pm.service.FlowNotifyService;
import com.yuesheng.pm.service.InfoService;
import com.yuesheng.pm.service.SectionService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.MyWebSocketHandle;
import com.yuesheng.pm.listener.WebParam;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 96339 on 2017/11/24.
 */
@Service("InfoService")
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private StaffService staffService;

    @Autowired
    private FlowNotifyService flowNotifyService;

    @Autowired
    private SectionService sectionService;

    @Override
    public List<Info> queryList(Map<String, Object> params) {
        return infoMapper.queryList(params);
    }

    @Override
    public Info queryById(String id) {
        return infoMapper.queryById(id);
    }

    @Override
    public void deleteById(String id) {
        infoMapper.deleteById(id);
    }

    @Override
    public void insert(Info info) {
        //数据库插入公告消息
        info.setCount(0);
        info.setSendDate(DateFormat.getDateTime());
        info.setId(UUID.randomUUID().toString());
        if (StringUtils.isNotBlank(info.getContent())) {
            info.setContent(info.getContent().replaceAll(String.valueOf('\u200B'), ""));
        }
        infoMapper.insert(info);

        List<Section> sections = info.getSectionList();
        List<Staff> staffList = null;
        if (Objects.isNull(sections) || sections.isEmpty()) {
            //推送所有人
            staffList = staffService.getStaffs();
            //添加该消息的阅读状态到每个职员记录中
            Map<String, String> infoId = setReadState(info, staffList);
            //企业微信通知
            WeiCharNotice(info, staffList, infoId);
        } else {
            //推送指定部门的人
            sections.forEach(s -> {
                List<Staff> thanStaff = sectionService.getStaffList(s.getId());
                //添加该消息的阅读状态到每个职员记录中
                Map<String, String> infoId = setReadState(info, thanStaff);
                //企业微信通知
                WeiCharNotice(info, thanStaff, infoId);
            });
        }
    }

    private Map<String, String> setReadState(Info info, List<Staff> staffList) {
        InfoState infoState = new InfoState();
        infoState.setMainId(info.getId());
        infoState.setState(0);
        Map<String, String> infoId = new HashMap<>(16);
        for (Staff s : staffList) {
            infoState.setId(UUID.randomUUID().toString());
            infoState.setStaffId(s.getId());
            insertState(infoState);
            infoId.put(s.getId(), infoState.getId());
        }
        return infoId;
    }

    private void WeiCharNotice(Info info, List<Staff> staffList, Map<String, String> infoId) {
        Map<String, Object> param = new HashMap<>();
        param.put("title", "公司公告");
        param.put("mTitle", "发送人：" + info.getStaff().getName() + ",请登录公司办公系统查看详情");
        param.put("content", info.getTitle());
        param.put("url", WebParam.VUETIFY_BASE+"/infoDetail/" + info.getId());
        flowNotifyService.msgNotify(staffList, param);

        //通知在线用户
        Map<String, HttpSession> sl = ManagerLogin.getStaffList();
        Iterator<String> kets = sl.keySet().iterator();
        HttpSession session = null;
        String nextId;
        Map<String, Object> isSendMap = new HashMap<>(16);
        Map<String, Object> msg = new HashMap<>(16);
        msg.put("type", "3");
        msg.put("data", info);
        while (kets.hasNext()) {
            nextId = kets.next();
            //判断是否已经发送过
            if (isSendMap.get(nextId) != null) {
                continue;
            }
            session = sl.get(nextId);
            if (session != null) {
                try {
                    msg.put("stateId", infoId.get(nextId));
                    MyWebSocketHandle.sendMeg(nextId, JSON.toJSONString(msg, SerializerFeature.DisableCircularReferenceDetect));
                    isSendMap.put(nextId, "1");
                } catch (IllegalStateException e) {
                    //忽略session过期异常
                }
            }
        }
    }

    @Override
    public void insertState(InfoState infoState) {
        infoMapper.insertState(infoState);
    }

    @Override
    public int queryCount(String infoId) {
        return infoMapper.queryCount(infoId);
    }

    @Override
    public int updateState(InfoState infoState) {
        return infoMapper.updateState(infoState);
    }

    @Override
    public List<Staff> queryReadStaff(String infoId) {
        return infoMapper.queryReadStaff(infoId);
    }

    @Override
    public List<InfoState> queryInfoStates(String staffId) {
        return infoMapper.queryInfoStates(staffId);
    }

    @Override
    public int queryListCount(Map<String, Object> result) {
        return infoMapper.queryListCount(result);
    }

    @Override
    public List<Info> queryList(Info info) {
        return infoMapper.queryListByInfo(info);
    }

    @Override
    public int update(Info info) {
        return infoMapper.update(info);
    }

    @Override
    public int updateStateByInfo(InfoState info) {
        PageHelper.startPage(1,1);
        InfoState is = infoMapper.queryInfoState(info.getMainId(), info.getStaffId());
        if (Objects.isNull(is)) {
            info.setId(UUID.randomUUID().toString());
            insertState(info);
            return 1;
        } else {
            return infoMapper.updateStateByInfo(info);
        }
    }
}
