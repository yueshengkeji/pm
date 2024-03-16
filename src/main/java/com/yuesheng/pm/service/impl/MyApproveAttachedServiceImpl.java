package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.MyApproveAttachedMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author XiaoSong
 * @date 2016-08-17
 */
@Service("myApproveAttachedService")
public class MyApproveAttachedServiceImpl implements MyApproveAttachedService {
    /**
     * 审批流程附表，人员表
     */
    @Autowired
    @Lazy
    private FlowApproveService flowApproveService;
    /**
     * 【我的审批】附表服务
     */
    @Autowired
    private MyApproveAttachedMapper myApproveAttachedMapper;
    /**
     * 【我的消息】服务
     */
    @Autowired
    @Lazy
    private FlowMessageService flowMessageService;
    /**
     * 审批消息服务
     */
    @Autowired
    private MyApproveService myApproveService;
    @Autowired
    private FlowCourseService flowCourseService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private CoursePersonAttachedService personAttachedService;

    @Override
    public void addApproveAttached(MyApproveAttached myApproveAttached) {
        myApproveAttachedMapper.addApproveAttached(myApproveAttached);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int consentAttached(FlowApprove flowApprove) {
        return -1;
    }

    @Override
    public List<Staff> notifyMsg(FlowCourse course,
                                 FlowApprove approve,
                                 FlowMessage flowMsg,
                                 MyApprove myApprove,
                                 MyApproveMain approveMain) {
        String sendUser = approve.getAcceptStaffId();
        //获取当前步骤知会集合
        FlowCourse temp = flowCourseService.getNotifyCourseBByMsgId(approve.getCourseId(), approve.getFlowMessageId());

        if (temp == null) {
            //没有知会人员
            return null;
        }
        //设置知会人员集合
        temp.setPersonList(personAttachedService.getPersonsByType(approve.getCourseId(), flowMsg.getHistroryId(), 1));
        /*
         * 遍历人员，全部知会
         */
        Staff staff = staffService.getStaffById(flowMsg.getStaffId());
        List<CoursePerson> coursePersons = temp.getPersonList();
        for (int i = 0; i < coursePersons.size(); i++) {
            CoursePerson person = coursePersons.get(i);
            if (person != null) {
                staffDispose(course, approve, flowMsg, person, myApprove, approveMain, sendUser,
                        flowMessageService.getStaffByType(person, staff));
            }
        }
        return null;
    }

    @Override
    public List<FlowApprove> staffDispose(FlowCourse fc,
                                          FlowApprove flowApprove,
                                          FlowMessage flowMsg,
                                          CoursePerson person,
                                          MyApprove approve,
                                          MyApproveMain approveMain,
                                          String sendUser,
                                          List<Staff> staffList) {
        ArrayList<FlowApprove> faList = new ArrayList();
        Integer lastCourse = 0;
        if ("10563".equals(flowMsg.getFrameCoding()) && !Objects.isNull(fc)) {
            //合同付款判断步骤位置
            if (fc.getName().startsWith("现金会计")) {
                lastCourse = 1;
            }
        }

        for (int n = 0; n < staffList.size(); n++) {
            Staff temp = staffList.get(n);
            if (temp == null) {
                continue;
            }

            //添加数据到sdpo004 & sdpo004_AllRecord
            FlowApprove newApprove = new FlowApprove();
            //sdpo003主键
            newApprove.setFlowMessageId(flowApprove.getFlowMessageId());
            //生成消息主键id
            newApprove.setId(UUID.randomUUID().toString());
            //设置发送人员id
            newApprove.setStaffId(sendUser);
            //设置过程id
            newApprove.setCourseId(person.getCourseId());
            //设置审批过程序号
            if (Objects.isNull(fc)) {
                newApprove.setPo00415(n);
            } else {
                newApprove.setPo00415(fc.getSerial());
            }
            //设置审批人id
            newApprove.setAcceptStaffId(temp.getId());
            //设置发送时间
            newApprove.setAcceptDate(DateFormat.getDateTime());
            //设置审批人序号
            newApprove.setPo00414((byte) person.getSerial());
            //设置不知名的时间列
            newApprove.setDate(newApprove.getAcceptDate());
            //设置接收时间
            newApprove.setAccrptDate(DateUtil.getDatetime());
            newApprove.setPo00418Id("");
            if (person.getType() == 0) {
                //审批
                newApprove.setApproveState(0);
            } else {
                //知会
                newApprove.setApproveState(5);
            }
            newApprove.setLastCourse(lastCourse);
            flowApproveService.addApprove(newApprove);
            faList.add(newApprove);

        }
        return faList;
    }


    @Override
    public MyApproveAttached getAttchedById(String id) {
        return myApproveAttachedMapper.getAttachedById(id);
    }

    /**
     * 撤回我的审批
     *
     * @param approve 【我的审批对象】
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recall(FlowMessage flowMsg, String nextCourseId, FlowApprove approve) {
    }

    @Override
    public void updateAttached(MyApproveAttached attached) {
        myApproveAttachedMapper.updateAttached(attached);
    }

    @Override
    public int updateState(MyApproveAttached ma) {
        return myApproveAttachedMapper.updateState(ma);
    }

    @Override
    public int deleteById(String id) {
        int row = 0;
        MyApproveAttached maa = myApproveAttachedMapper.getAttachedById(id);
        if (!Objects.isNull(maa)) {
            MyApprove ma = myApproveService.getMessageById(maa.getApproveId());
            if (!Objects.isNull(ma)) {
                row += myApproveService.deleteById(ma.getId());
                row += myApproveAttachedMapper.deleteByApproveId(ma.getId());
            }
        }
        return row;
    }
}
