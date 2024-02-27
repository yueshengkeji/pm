package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskResponseBody;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.CompanyWxUtil;
import com.yuesheng.pm.util.DataUtil;
import com.yuesheng.pm.listener.WebParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: kailismart.com
 * @description: 流程变化通知接口实现类
 * @author: zcj
 * @create: 2019-06-10 14:55
 **/
@Service("flowNotifyService")
public class FlowNotifyServiceImpl implements FlowNotifyService {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffAdditionInfoService staffAdditionInfoService;

    @Autowired
    private ProNotifyTypeService notifyTypeService;

    @Autowired
    private DingTalkStaffInfoService dingTalkStaffInfoService;

    @Autowired
    private DingTalkApiService dingTalkApiService;

    @Autowired
    private DingTalkTaskFlowRecordService dingTalkTaskFlowRecordService;

    @Override
    public void flowChange(int state, List<Staff> notifyStaff, FlowApprove flowApprove) {

        //判断条件 1：流程状态是否正确  2：审批流程对象和ID是否为空 3.通知对象是否为空
        if ((state != 1 && state != 2) || flowApprove == null || StringUtils.isBlank(flowApprove.getId()) || notifyStaff == null || notifyStaff.isEmpty()) {
            System.out.println("流程状态state=" + state +
                    ",通知消息ID：" + (StringUtils.isBlank(flowApprove.getId()) ? "NULL" : flowApprove.getId()) +
                    ",通知对象：" + ((notifyStaff == null || notifyStaff.isEmpty()) ? "NULL" : "不为空"));
        } else {

            //通知标题=文章标题 + (当前审批人 + 流程状态)
            String title = flowApprove.getMessage().getTitle();

            //通知状态
            String stateStr = "发送人：" + getStaffName(flowApprove);

            //通知详情URL
            String url = getNotifyWxUrl(flowApprove);

            //简要内容
            String content = flowApprove.getContent();

            //遍历wx通知
            for (Staff temp : notifyStaff) {
                CompanyWxUtil.sendMsg(getWxUserIdOrWxOpenId(temp), title, content, stateStr, url);
            }

            List<Staff> staffListForDing = new ArrayList<>();//用来存绑定钉钉账号并且开启钉钉消息推送
            //遍历dd通知
            for (Staff temp : notifyStaff) {
                DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(temp.getId());
                ProNotifyType proNotifyType = notifyTypeService.queryById(temp.getId());
                //判断已进入钉钉组织并开启钉钉推送
                if (!Objects.isNull(dingTalkStaffInfo) && !Objects.isNull(proNotifyType.getDing()) && proNotifyType.getDing()) {
                    temp.setDingTalkStaffInfo(dingTalkStaffInfo);
                    staffListForDing.add(temp);
                }
            }

            //通知详情钉钉url
            String notifyDDUrl = getNotifyDDUrl(flowApprove);

            DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(flowApprove.getStaffId());
            //创建钉钉待办任务推送
            sendDingToDo(staffListForDing, dingTalkStaffInfo, title, content, notifyDDUrl, flowApprove);



            //钉钉链接消息
//            sendDingLink(staffListForDing,title,content,notifyDDUrl);

        }
    }

    @Override
    public void reject(Staff staff, List<Staff> notifyStaff, FlowApprove flowApprove) {


        //判断条件 1.驳回人员为空 2.通知人员集合为空 3.通知消息为空或ID为空
        if (staff == null || notifyStaff == null || notifyStaff.isEmpty() || flowApprove == null || StringUtils.isBlank(flowApprove.getId())) {
            System.out.println("驳回人员：" + (staff == null ? "NULL" : "不为空") +
                    ",通知消息ID：" + (StringUtils.isBlank(flowApprove.getId()) ? "NULL" : flowApprove.getId()) +
                    ",通知对象：" + ((notifyStaff == null || notifyStaff.isEmpty()) ? "NULL" : "不为空"));
        } else {
            //文章标题
            String title = flowApprove.getMessage().getTitle();

            //通知状态
            String stateStr = "驳回人:" + staff.getName();

            //获取通知内容
            String content = flowApprove.getContent();

            //获取通知详情URL
            String url = getNotifyWxUrl(flowApprove);

            //遍历通知
            for (Staff temp : notifyStaff) {
                CompanyWxUtil.sendMsg(getWxUserIdOrWxOpenId(temp), title, content, stateStr, url);
            }

            List<Staff> staffListForDing = new ArrayList<>();
            //遍历dd通知
            for (Staff temp : notifyStaff) {
                DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(temp.getId());
                ProNotifyType proNotifyType = notifyTypeService.queryById(temp.getId());
                //判断已进入钉钉组织并开启钉钉推送
                if (!Objects.isNull(dingTalkStaffInfo) && !Objects.isNull(proNotifyType.getDing()) && proNotifyType.getDing()) {
                    temp.setDingTalkStaffInfo(dingTalkStaffInfo);
                    staffListForDing.add(temp);
                }
            }
            //通知详情钉钉url
            String notifyDDUrl = getNotifyDDUrl(flowApprove);

            DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(flowApprove.getStaffId());
            //创建钉钉待办任务推送
            sendDingToDo(staffListForDing, dingTalkStaffInfo, title, content, notifyDDUrl, flowApprove);


            //钉钉链接消息
//            sendDingLink(staffListForDing,title,content,notifyDDUrl);

        }
    }

    @Override
    public void offFlow(Staff staff, List<Staff> notifyStaff, FlowApprove flowApprove) {

        //判断条件 1.中断人员为空 2.通知人员集合为空 3.通知消息为空或ID为空
        if (staff == null || notifyStaff == null || notifyStaff.isEmpty() || flowApprove == null || StringUtils.isBlank(flowApprove.getId())) {
            System.out.println("中断人员：" + (staff == null ? "NULL" : "不为空") +
                    ",通知消息ID：" + (StringUtils.isBlank(flowApprove.getId()) ? "NULL" : flowApprove.getId()) +
                    ",通知对象：" + ((notifyStaff == null || notifyStaff.isEmpty()) ? "NULL" : "不为空"));
        } else {
            //文章标题
            String title = flowApprove.getMessage().getTitle();

            //通知状态
            String stateStr = "中断人:" + staff.getName();

            //获取通知内容
            String content = flowApprove.getContent();

            //获取通知详情URL
            String url = getNotifyWxUrl(flowApprove);

            //遍历通知
            for (Staff temp : notifyStaff) {
                CompanyWxUtil.sendMsg(getWxUserIdOrWxOpenId(temp), title, content, stateStr, url);
            }

            List<Staff> staffListForDing = new ArrayList<>();
            //遍历dd通知
            for (Staff temp : notifyStaff) {
                DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(temp.getId());
                ProNotifyType proNotifyType = notifyTypeService.queryById(temp.getId());
                //判断已进入钉钉组织并开启钉钉推送
                if (!Objects.isNull(dingTalkStaffInfo) && !Objects.isNull(proNotifyType.getDing()) && proNotifyType.getDing()) {
                    temp.setDingTalkStaffInfo(dingTalkStaffInfo);
                    staffListForDing.add(temp);
                }
            }
            //通知详情钉钉url
            String notifyDDUrl = getNotifyDDUrl(flowApprove);

            DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(flowApprove.getStaffId());
            //创建钉钉待办任务推送
            sendDingToDo(staffListForDing, dingTalkStaffInfo, title, content, notifyDDUrl, flowApprove);


            //钉钉链接消息
//            sendDingLink(staffListForDing,title,content,notifyDDUrl);
        }
    }

    @Override
    public void notifyMsg(Staff staff, List<Staff> notifyStaff, FlowApprove flowApprove) {

        //判断条件 1.知会人员为空 2.通知人员集合为空 3.通知消息为空或ID为空
        if (staff == null || notifyStaff == null || notifyStaff.isEmpty() || flowApprove == null || StringUtils.isBlank(flowApprove.getId())) {
            System.out.println("中断人员：" + (staff == null ? "NULL" : "不为空") +
                    ",通知消息ID：" + (StringUtils.isBlank(flowApprove.getId()) ? "NULL" : flowApprove.getId()) +
                    ",通知对象：" + ((notifyStaff == null || notifyStaff.isEmpty()) ? "NULL" : "不为空"));
        } else {
            //文章标题
            String title = flowApprove.getMessage().getTitle();

            //通知状态
            String stateStr = "发送人:" + staff.getName();

            //获取通知内容
            String content = flowApprove.getContent();

            //获取通知详情URL
            String url = getNotifyWxUrl(flowApprove);

            //遍历通知
            for (Staff temp : notifyStaff) {
                CompanyWxUtil.sendMsg(getWxUserIdOrWxOpenId(temp), title, content, stateStr, url);
            }

            List<Staff> staffListForDing = new ArrayList<>();
            //遍历dd通知
            for (Staff temp : notifyStaff) {
                DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(temp.getId());
                ProNotifyType proNotifyType = notifyTypeService.queryById(temp.getId());
                //判断已进入钉钉组织并开启钉钉推送
                if (!Objects.isNull(dingTalkStaffInfo) && !Objects.isNull(proNotifyType.getDing()) && proNotifyType.getDing()) {
                    temp.setDingTalkStaffInfo(dingTalkStaffInfo);
                    staffListForDing.add(temp);
                }
            }
            //通知详情钉钉url
            String notifyDDUrl = getNotifyDDUrl(flowApprove);

            DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(flowApprove.getStaffId());
            //创建钉钉待办任务推送
            sendDingToDo(staffListForDing, dingTalkStaffInfo, title, content, notifyDDUrl, flowApprove);


            //钉钉链接消息
//            sendDingLink(staffListForDing,title,content,notifyDDUrl);

        }
    }

    @Override
    public void msgNotify(List<Staff> notifyStaff, Map<String, Object> msg) {
        //遍历通知
        for (Staff temp : notifyStaff) {
            msgNotify(temp, msg);
        }

        List<Staff> staffListForDing = new ArrayList<>();
        //遍历dd通知
        try {
            for (Staff temp : notifyStaff) {
                DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(temp.getId());
                ProNotifyType proNotifyType = notifyTypeService.queryById(temp.getId());
                //判断已进入钉钉组织并开启钉钉推送
                if (!Objects.isNull(dingTalkStaffInfo) && !Objects.isNull(proNotifyType.getDing()) && proNotifyType.getDing()) {
                    temp.setDingTalkStaffInfo(dingTalkStaffInfo);
                    staffListForDing.add(temp);
                }
            }
            //通知详情钉钉url
            String notifyDDUrl = DataUtil.getNotifyWxUrl() + msg.get("url").toString();

//        DingTalkStaffInfo dingTalkStaffInfo = dingTalkStaffInfoService.selectByStaffId(flowApprove.getStaffId());
//        //创建钉钉待办任务推送
//        sendDingToDo(staffListForDing,dingTalkStaffInfo,msg.get("title").toString(),msg.get("content").toString(),notifyDDUrl,flowApprove);
            //钉钉链接消息
            sendDingLink(staffListForDing, msg.get("title").toString(), msg.get("content").toString(), notifyDDUrl);
        } catch (Exception e) {
            //ignore error
        }


    }

    /**
     * 获取发起人姓名
     *
     * @param flowApprove 通知消息
     * @return 发起人姓名
     * 注：默认为空字符串
     */
    private String getStaffName(FlowApprove flowApprove) {
        String staffName = "";

        //判断消息是否为空
        if (flowApprove != null) {

            //判断staff对象是否为空，若不行，根据staffID查找
            if (flowApprove.getSendUser() != null && StringUtils.isNotBlank(flowApprove.getSendUser().getName())) {
                staffName = flowApprove.getSendUser().getName();
            } else if (StringUtils.isNotBlank(flowApprove.getStaffId())) {

                //判断对象是否为空
                Staff staff = staffService.getStaffById(flowApprove.getStaffId());
                if (staff != null && StringUtils.isNotBlank(staff.getName())) {
                    staffName = staff.getName();
                }
            }
        }

        return staffName;
    }

    /**
     * 获取通知详情URL
     *
     * @param flowApprove 通知消息
     * @return 通知详情URL
     * 注：默认为空字符串
     */
    private String getNotifyWxUrl(FlowApprove flowApprove) {
        String url = "";

        //判断ID是否为空
        if (flowApprove != null && StringUtils.isNotBlank(flowApprove.getId())) {
            Staff accept = flowApprove.getAcceptUser();
            if (!Objects.isNull(accept)) {
                ProNotifyType nt = notifyTypeService.queryById(accept.getId());
                if (!Objects.isNull(nt)) {
                    LogManager.getLogger("mylog").info("notifyType" + JSON.toJSONString(nt));
                    Boolean type = nt.getType();
                    if (!Objects.isNull(type) && type) {
                        url = DataUtil.getNotifyWxUrl() + WebParam.VUETIFY_BASE + "/approve/by-approve-id/" + flowApprove.getId();
                        return url;
                    }
                }
            }
            url = DataUtil.getNotifyWxUrl() + "/api/emailApp/" + flowApprove.getId() + ".html";
        }
        return url;
    }

    /**
     * 获取通知详情URL 钉钉
     *
     * @param flowApprove
     * @return
     */
    private String getNotifyDDUrl(FlowApprove flowApprove) {
        String url = "";

        if (flowApprove != null && StringUtils.isNotBlank(flowApprove.getId())) {
            Staff accept = flowApprove.getAcceptUser();
            if (!Objects.isNull(accept)) {
                ProNotifyType nt = notifyTypeService.queryById(accept.getId());
                if (!Objects.isNull(nt)) {
                    LogManager.getLogger("mylog").info("notifyType" + JSON.toJSONString(nt));
                    Boolean type = nt.getType();
                    if (!Objects.isNull(type) && type) {
                        url = DataUtil.getNotifyWxUrl() + WebParam.VUETIFY_BASE + "/approve/by-approve-id/" + flowApprove.getId();
                        return url;
                    }
                }
            }
        }
        url = DataUtil.getNotifyWxUrl() + "/emailApp/" + flowApprove.getId() + ".html";
        return url;
    }

    /**
     * 钉钉待办任务
     *
     * @param staffListForDing  钉钉绑定用户&&开启钉钉推送的用户
     * @param dingTalkStaffInfo 审批流程发起人的钉钉绑定信息
     * @param title
     * @param content
     * @param notifyDDUrl
     * @param flowApprove
     */
    private void sendDingToDo(List<Staff> staffListForDing, DingTalkStaffInfo dingTalkStaffInfo, String title, String content, String notifyDDUrl, FlowApprove flowApprove) {
        if (staffListForDing.size() > 0) {
            DingTalkTaskToDo dingTalkTaskToDo = new DingTalkTaskToDo();

            dingTalkTaskToDo.setSubject(title);
            dingTalkTaskToDo.setDescription(content);
            dingTalkTaskToDo.setDingNotify("1");
            dingTalkTaskToDo.setAppUrl(notifyDDUrl);
            dingTalkTaskToDo.setPcUrl(notifyDDUrl);

            staffListForDing.forEach(item -> {
                if (!Objects.isNull(dingTalkStaffInfo)) {
                    dingTalkTaskToDo.setCreatorId(dingTalkStaffInfo.getUnionId());
                } else {
                    dingTalkTaskToDo.setCreatorId(item.getDingTalkStaffInfo().getUnionId());
                }
                ArrayList<String> list = new ArrayList();
                list.add(item.getDingTalkStaffInfo().getUnionId());
                dingTalkTaskToDo.setExecutorIds(list);
                dingTalkTaskToDo.setParticipantIds(list);

                CreateTodoTaskResponseBody task = dingTalkApiService.createTask(dingTalkTaskToDo);
                if (!Objects.isNull(task)) {
                    DingTalkTaskFlowRecord dingTalkTaskFlowRecord = new DingTalkTaskFlowRecord();
                    dingTalkTaskFlowRecord.setCreatorId(task.getCreatorId());
                    dingTalkTaskFlowRecord.setTaskId(task.getId());
                    dingTalkTaskFlowRecord.setIsDone(task.getDone() ? 1 : 0);
                    dingTalkTaskFlowRecord.setStaffId(item.getId());
                    dingTalkTaskFlowRecord.setFlowApproveId(flowApprove.getId());

                    dingTalkTaskFlowRecordService.insert(dingTalkTaskFlowRecord);
                }
            });

        }
    }

    /**
     * 钉钉链接消息
     *
     * @param staffListForDing 钉钉绑定用户&&开启钉钉推送的用户
     * @param title
     * @param content
     * @param notifyDDUrl
     */
    private void sendDingLink(List<Staff> staffListForDing, String title, String content, String notifyDDUrl) {
        if (staffListForDing.size() > 0) {
            String userList = null;
            for (Staff staffItem : staffListForDing) {
                userList += staffItem.getDingTalkStaffInfo().getDingTalkUserId() + ",";
            }
            DingTalkNoticeMSG dingTalkNoticeMSG = new DingTalkNoticeMSG();
            dingTalkNoticeMSG.setLinkUrl(notifyDDUrl);
            dingTalkNoticeMSG.setTitle(title);
            dingTalkNoticeMSG.setContent(content);
            dingTalkNoticeMSG.setUser_list(userList);
            dingTalkNoticeMSG.setTo_all_user(false);

            dingTalkApiService.workNotice(dingTalkNoticeMSG);
        }
    }

    /**
     * 根据职员对象的ID获取微信userID或微信OpenId
     *
     * @param staff 职员对象
     * @return 微信userID或微信OpenId
     * 注：默认为空字符串
     */
    @Override
    public String getWxUserIdOrWxOpenId(Staff staff) {
        String id = "";

        //判断该用户是否存在
        if (staff != null && StringUtils.isNotBlank(staff.getId())) {
            StaffAdditionInfo staffAdditionInfo = staffAdditionInfoService.getStaffAdditionInfoByStaffIdAndSystemId(staff.getId(), null);

            //判断是否为空
            if (staffAdditionInfo != null) {
                if (StringUtils.isNotBlank(staffAdditionInfo.getWxUserId())) {
                    id = staffAdditionInfo.getWxUserId();
                } else if (StringUtils.isNotBlank(staffAdditionInfo.getWxOpenId())) {
                    id = staffAdditionInfo.getWxOpenId();
                }

            }

        }

        return id;
    }

    @Override
    public void msgNotify(Staff manager, Map<String, Object> msg) {
        String url = msg.get("url").toString();
        if (!StringUtils.startsWith(url, "http")) {
            url = DataUtil.getNotifyWxUrl() + url;
        }
        CompanyWxUtil.sendMsg(getWxUserIdOrWxOpenId(manager), msg.get("title").toString(), msg.get("content").toString(), msg.get("mTitle").toString(), url);
    }
}
