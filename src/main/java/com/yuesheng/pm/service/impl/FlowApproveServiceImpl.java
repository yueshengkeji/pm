package com.yuesheng.pm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.mapper.FlowApproveMapper;
import com.yuesheng.pm.model.FLowConsentModel;
import com.yuesheng.pm.model.SelectApprove;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016-08-16.
 *
 * @author xiaoSong
 * @date 2020/3/19
 * 流程发起步骤实现类
 */
@Service("flowApproveService")
public class FlowApproveServiceImpl implements FlowApproveService {
    private static Logger logger = LogManager.getLogger("mylog");
    @Autowired
    private FlowApproveMapper flowApproveMapper;
    @Autowired
    private FlowCourseService flowCourseService;
    @Autowired
    @Lazy
    private FlowMessageService flowMessageService;
    @Autowired
    private MyApproveAttachedService myApproveAttachedService;
    @Autowired
    private FlowNotifyService flowNotifyService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private FlowCourseRelationService flowCourseRelationService;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private DynamicMethodExecutor dynamicMethodExecutor;


    @Override
    public void addApprove(FlowApprove approve) {
        try {
            flowApproveMapper.addApprove(approve);
        } catch (Exception e) {
            String title = "添加审批消息出错:" + e.getMessage();
            if (!Objects.isNull(approve.getMessage())) {
                title += approve.getCourseName();
            }
            System.out.println(title);
        }
    }

    @Override
    public int updateState(int state, String id) {
        FlowApprove fa = new FlowApprove();
        fa.setApproveState(state);
        fa.setId(id);
        fa.setReadDate(DateUtil.getDatetime());
        executorService.execute(() -> flowApproveMapper.updateReadStateRecord(fa));
        return flowApproveMapper.updateReadState(fa);
    }

    @Override
    public List<FlowApprove> getFlowApproveByMessageId(String messageId) {
        List<FlowApprove> flowApproves = flowApproveMapper.getFlowApproveByMessageId(messageId);
        setCourse(flowApproves);
        return flowApproves;
    }

    private List<FlowApprove> setCourse(List<FlowApprove> fa) {
        if (!Objects.isNull(fa)) {
            HashMap<String, FlowCourse> fcCatch = new HashMap<>();
            fa.forEach(item -> {
                FlowCourse fc;
                String courseId = item.getCourseId();
                if (fcCatch.containsKey(courseId)) {
                    fc = fcCatch.get(courseId);
                } else {
                    //此处先从过程记录查找，如果过程被删除，从流程发起实例过程中查找，如果也被删除，则从记录表中查找
                    fc = flowCourseService.getFlowCourseById(courseId);
                    if (Objects.isNull(fc)) {
                        fc = flowCourseService.getFlowCourseBById(courseId);
                        if (Objects.isNull(fc)) {
                            fc = flowCourseService.getFlowCourseBByInstance(courseId);
                        }
                    }
                    fcCatch.put(courseId, fc);
                }
                if (!Objects.isNull(fc)) {
                    item.setCourseName(fc.getName());
                    item.setPo00415(fc.getSerial());
                } else {
                    item.setCourseName("-");
                }
            });

            fa.sort(Comparator.comparingInt(FlowApprove::getPo00415).thenComparingInt(FlowApprove::getPo00414));

        }
        return fa;
    }

    @Override
    public void updateStates(FlowApprove[] flowApproves) {
        flowApproveMapper.updateStates(flowApproves);
    }

    @Override
    public FlowApprove getFlowApproveByAttached(String attachId) {
        return flowApproveMapper.getFlowApproveByAttached(attachId);
    }

    @Override
    public void updateFlowApproveStatus(FlowApprove flowApprove) {
        updateApproveState(flowApprove);
    }

    @Override
    public FlowApprove getFlowApproveById(String id) {
        return flowApproveMapper.getFlowApproveById(id);
    }

    @Override
    public void updateOtherStatus(FlowApprove flowApprove) {
        flowApproveMapper.updateOtherStatus(flowApprove);
        executorService.execute(() -> flowApproveMapper.updateOtherStatusRecord(flowApprove));
    }

    @Override
    public List<FlowApprove> getApproveByMsgAndHistory(String flowMessageId, String courseId, String status) {
        return flowApproveMapper.getApproveByMsgAndHistory(flowMessageId, courseId, status);
    }

    @Override
    public FlowApprove isApprove(FlowApprove flowApprove) {
        return flowApproveMapper.isApprove(flowApprove);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FLowConsentModel consentApprove(FlowApprove approve) {
        FLowConsentModel result = new FLowConsentModel();
        /*
        判断该过程是否已经同意过
        */
        FlowApprove isApprove = this.isApprove(approve);
        if (isApprove != null) {
            result.setMsg("该步骤已经审批完成");
            result.setState(4);
            return result;
        }
        FlowMessage flowMsg = flowMessageService.getMessageById(approve.getFlowMessageId());
        //根据当前步骤+流程记录id，获取下一个步骤对象
        FlowCourse nextCourse = flowCourseService.getNextFlowCourseBByCourseId(approve.getCourseId(), flowMsg.getHistroryId(), flowMsg);
        Staff s = staffService.getStaffById(flowMsg.getStaffId());
        //是否会签
        boolean isNextCourse = isNextCourse(approve, flowMsg);
        //判断是否到最后的步骤，！=null为还有步骤没走完，否则交与流程结束的处理
        if (nextCourse != null) {
            List<CoursePerson> personAttached = nextCourse.getPersonList();
            if (nextCourse.getPubPerson() == 1 && isNextCourse) {
//                下一步，并且自由选人
                for (CoursePerson person : personAttached) {
                    person.setStaff(flowMessageService.getStaffByType(person, s));
                }
                result.setState(2);
                result.setMsg("需要指定下一步审批人");
                result.setFlowCourse(nextCourse);
                return result;
            }
//            if ("10563".equals(flowMsg.getFrameCoding())) {
//                //合同付款判断步骤位置
//                if (nextCourse.getName().startsWith("现金会计")) {
//                    //到最后一步，通知申请人
//                    paymentService.notifyStaff(flowMsg.getFrameId());
//                }
//            }
        } else if (isNextCourse) {
            //流程结束，审批完成
            executorService.submit(() -> {
                try {
                    flowMessageService.checkStatus(flowMsg, approve);
                } catch (Exception e) {
                    MDC.put("params", "approveId=" + approve.getId());
                    logger.error("OA表单状态更新异常" + e.getMessage());
                }
            });
            result.setFlowSuccess(true);
            result.setFlowMessage(flowMsg);
        }
        try {
            if (isNextCourse) {
                if (!Objects.isNull(nextCourse)) {
                    //添加下一个步骤
                    List<CoursePerson> personList = nextCourse.getPersonList();
                    //获取发起人信息
                    Staff startStaff = staffService.getStaffById(flowMsg.getStaffId());
                    if (personList.isEmpty()) {
                        //没有处理人，跳过该步骤，直接到下一步骤
                        setEmptyStaff(flowMsg, nextCourse, personList);
                    } else {
                        int x = 0;
                        for (int i = 0; i < personList.size(); i++) {
                            CoursePerson cp = personList.get(i);
                            if (cp.getType() == 0) {
                                if (Objects.isNull(cp.getStaff()) || cp.getStaff().isEmpty()) {
                                    cp.setStaff(flowMessageService.getStaffByType(cp, startStaff));
                                }
                                x += cp.getStaff().size();
                            }
                        }
                        if (x == 0) {
                            setEmptyStaff(flowMsg, nextCourse, personList);
                        }
                    }
                    personList.forEach(item -> {
                        //处理类型为审批人时，添加到当前步骤实例
                        if (item.getType() == 0) {
                            if (Objects.isNull(item.getStaff()) || item.getStaff().isEmpty()) {
                                item.setStaff(flowMessageService.getStaffByType(item, startStaff));
                            }
                            ArrayList<FlowApprove> newFaList = (ArrayList) this.approve(approve, item, nextCourse, flowMsg, isNextCourse).getOrDefault("newFaList", new ArrayList<>());
                            if (item.getStaff() == null || item.getStaff().size() <= 0) {
//                                MDC.put("url", "/managent/consentApprove");
//                                MDC.put("ip", "");
//                                MDC.put("userName", approve.getAcceptUser().getName());
//                                MDC.put("userId", approve.getAcceptUser().getId());
//                                MDC.put("params", "approveId=" + approve.getId());
//                                logger.error("审批<" + flowMsg.getTitle() + ">,同意操作出现异常，下一过程没有查询到审批人信息：" + item.getName());
                            } else if (!newFaList.isEmpty()) {
                                //判断该步骤审批人是否与上一个审批人相同，如果是，则自动同意
                                newFaList.forEach(item2 -> {
                                    if (item2.getApproveState() == 0 &&
                                            (StringUtils.equals(item2.getAcceptStaffId(), approve.getAcceptStaffId())
                                                    || StringUtils.equals(item.getStaffId(), "-1"))) {
                                        //自动审批同意.延迟1秒执行
                                        executorService.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                consentApprove(item2);
                                            }
                                        }, 1000, TimeUnit.MILLISECONDS);

                                    }
                                });
                            }
                        }
                    });

                    this.approveAfter(approve, isNextCourse, flowMsg);
                } else {
                    //审批结束
                    this.approve(approve, null, null, flowMsg, true);
                    this.approveAfter(approve, isNextCourse, flowMsg);
                }
            } else {
                //会签，有其他人未同意，更新状态，不执行下一步过程
                this.approve(approve, null, null, flowMsg, false);
                this.approveAfter(approve, false, flowMsg);
            }
            notifyInitPerson(approve, flowMsg);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
//        审批通过
        result.setFlowSuccess(true);
        result.setFlowMessage(flowMsg);
        result.setState(1);
        result.setMsg("操作成功");
        return result;
    }

    private void approveAfter(FlowApprove approve, Boolean isNext, FlowMessage flowMsg) {
        /*
            更新sdpo004和sdpo004_AllRecord状态
            1.审批时间
            2.审批状态为同意
            3.最后更新时间
             */
        approve.setApproveDate(DateFormat.getDateTime());
        approve.setApproveState(3);
        approve.setDate(approve.getApproveDate());
        this.updateFlowApproveStatus(approve);
        /*
         * 触发当前步骤的动态代理方法
         */
        if (BooleanUtils.isTrue(isNext)) {

            /*
             *  该步骤其他人员状态改为知会未读
             *  1.状态
             *  2.审批内容
             */
            List<FlowApprove> faList = getByCourseId(approve.getCourseId(), approve.getFlowMessageId());
            setOtherState(approve, faList);

            if (Objects.isNull(approve.getCourse())) {
                targetInvoke(flowCourseService.getFlowCourseById(approve.getCourseId()), flowMsg);
            } else {
                targetInvoke(approve.getCourse(), flowMsg);
            }
        }
    }

    private static void setEmptyStaff(FlowMessage flowMsg, FlowCourse nextCourse, List<CoursePerson> personList) {
        CoursePersonAttached personAttached = new CoursePersonAttached();
        personAttached.setFrameId(flowMsg.getFrameId());
        personAttached.setCourseId(nextCourse.getId());
        personAttached.setCoursePersonId("-1");
        personAttached.setType(0);
        personAttached.setSerial(0);
        personAttached.setStaffId("-1");
        personAttached.setFlowHistoryId(flowMsg.getHistroryId());
        personAttached.setStaffType(-1);
        List<Staff> staffs = new ArrayList<>();
        Staff staff = new Staff();
        staff.setId("-1");
        staff.setName("自动审批");
        staffs.add(staff);
        personAttached.setStaff(staffs);
        personList.add(personAttached);
    }

    private boolean isNextCourse(FlowApprove approve, FlowMessage flowMsg) {
        FlowCourse mfc = flowCourseService.getThanFlowCourseBByCourseId(approve.getCourseId(), flowMsg.getHistroryId());
        approve.setCourse(mfc);
        boolean isNextCourse = true;
        List<FlowApprove> faList = getByCourseId(approve.getCourseId(), approve.getFlowMessageId());
        if (!Objects.isNull(mfc)) {
            if (mfc.getPo02005() == 1) {
                //会签，判断该步骤所有审批人是否同意
                isNextCourse = isNext(faList, approve);
            } else {
                //查询是否有加签未审批的数据，如果有则返回false
                for (FlowApprove item : faList) {
                    if (StringUtils.equals(item.getPo00421(), "2") && item.getApproveState() <= 1) {
                        //有加签未审批，返回false
                        return false;
                    }
                }
            }
        }
        return isNextCourse;
    }

    private boolean isNext(List<FlowApprove> faList, FlowApprove approve) {
        boolean next = true;
        for (int i = 0; i < faList.size(); i++) {
            FlowApprove fa = faList.get(i);
            if (fa.getApproveState() <= 1 && !StringUtils.equals(approve.getId(), fa.getId())) {
                next = false;
                break;
            }
        }
        return next;
    }

    @Override
    public List<FlowApprove> getByCourseId(String courseId, String messageId) {
        return flowApproveMapper.getByCourseId(courseId, messageId);
    }

    private void notifyInitPerson(FlowApprove approve, FlowMessage flowMsg) {
        //获取当前步骤知会集合
        FlowCourse course = flowCourseService.getThanFlowCourseBByCourseId(approve.getCourseId(), flowMsg.getHistroryId());
        if (!Objects.isNull(course)) {
            List<CoursePerson> psList = course.getPersonList();
            Staff startStaff = flowMsg.getStaff();
            if (Objects.isNull(startStaff)) {
                startStaff = staffService.getStaffById(flowMsg.getStaffId());
            }
//            MyApproveAttached attached = myApproveAttachedService.getAttchedById(approve.getPo00418Id());
//            MyApprove myApprove = myApproveService.getMessageById(attached.getApproveId());
//            MyApproveMain approveMain = myApproveMainService.getMainByMsgId(flowMsg.getId());
            boolean resend = sendNotify(approve, flowMsg, psList, startStaff);
            if (resend) {
                //如果是自由选人的过程，需要手动查询知会人列表，并发送知会信息
                FlowCourse nfc = flowCourseService.getFlowCourseById(approve.getCourseId());
                if (!Objects.isNull(nfc)) {
                    psList = nfc.getPersonList();
                    sendNotify(approve, flowMsg, psList, startStaff);
                }
            }
        }
        /*FlowCourse temp = flowCourseService.getNotifyCourseBByMsgId(approve.getCourseId(), approve.getFlowMessageId());

        if (temp != null) {
            List<Staff> staffs = new ArrayList<>();
            //设置知会人员集合
            temp.setPersonList(coursePersonService.getPersonsByType(approve.getCourseId(), flowMsg.getHistroryId(), 1));
            *//*
         * 遍历人员，全部知会
         *//*
            List<CoursePerson> coursePersons = temp.getPersonList();
            ArrayList<Staff> staff = new ArrayList<>();
            Staff s = staffService.getStaffById(flowMsg.getStaffId());
            staff.add(s);
            for (int i = 0; i < coursePersons.size(); i++) {
                CoursePerson person = coursePersons.get(i);
                if (person != null && person.getStaffType() == APPROVE_FAQIREN) {
                    //微信通知发起人
                    Map<String, Object> param = new HashMap<>();
                    param.put("title", "审批通过");
                    param.put("mTitle", flowMsg.getTitle());
                    param.put("content", "请登录公司办公系统查看详情");
                    param.put("url", "/managent/getPage?pageName=managerIndex&param=mpindex=approve/approve=pindex=dsp");
                    flowNotifyService.msgNotify(staff, param);
                    break;
                }
            }
        }*/
    }

    private boolean sendNotify(FlowApprove approve, FlowMessage flowMsg, List<CoursePerson> psList, Staff startStaff) {
        boolean isPublicNotify = true;
        for (int i = 0; i < psList.size(); i++) {
            CoursePerson item = psList.get(i);
            if (item.getType() == 1) {
                isPublicNotify = false;
                List<Staff> staff = flowMessageService.getStaffByType(item, startStaff);
                myApproveAttachedService.staffDispose(approve.getCourse(), approve, flowMsg, item, null, null, approve.getAcceptStaffId(), staff);
                /*
                 * 微信通知
                 */
                Map<String, Object> param = new HashMap<>();
                param.put("title", "知会消息");
                param.put("mTitle", flowMsg.getTitle());
                param.put("content", "");
                param.put("url", WebParam.VUETIFY_BASE + "/approve/by-frame-id/" + flowMsg.getFrameId());
                flowNotifyService.msgNotify(staff, param);
            }
        }
        return isPublicNotify;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> consentByPerson(FlowApprove approve, CoursePerson coursePerson, FlowCourse flowCourse) {
        FlowMessage flowMsg = flowMessageService.getMessageById(approve.getFlowMessageId());
        Map<String, Object> res = approve(approve, coursePerson, flowCourse, flowMsg, true);
        this.approveAfter(approve, true, flowMsg);
        return res;
    }

    private Map<String, Object> approve(FlowApprove approve,
                                        CoursePerson coursePerson,
                                        FlowCourse flowCourse,
                                        FlowMessage flowMsg,
                                        Boolean isNext) {
        /*
        遍历过程中处理角色类型集合，并添加到数据库
        如果等于0：则该步骤为知会消息步骤，应该获取下一个步骤，继续流程流转
        如果等于-1：则该步骤中没有审批人员，应该获取下一个步骤，继续流程流转
         */
        Map<String, Object> result = new HashMap<>(16);
        List<Staff> staffList = null;
        if (!Objects.isNull(coursePerson)) {
            /*
             * 如果添加的人员大小<=0,则没有审批人员，利用递归，继续获取下一个审批步骤
             */
            staffList = coursePerson.getStaff();
            if (staffList == null || staffList.size() <= 0) {
                //该过程中未选择审批人员
                result.put("no-person", "no-person");
                return result;
            } else {
                //审批人员处理
                result.put("newFaList", myApproveAttachedService.staffDispose(flowCourse, approve, flowMsg, coursePerson, null, null, approve.getAcceptStaffId(), staffList));
            }
        }
        if (!Objects.isNull(flowCourse)) {
            try {
                //添加当前步骤到sdpo020b_Instance表中
                flowCourse.setFrameId(flowMsg.getFrameId());
                flowCourseService.addFlowCourseBInstance(flowCourse);
            } catch (Exception e) {
                //ignore this error
            }
        }

        if (!Objects.isNull(flowCourse)) {
            /*
             * 下一步骤的知会处理
             * 注释的为选择知会时的处理，暂不实现
             */
            myApproveAttachedService.notifyMsg(flowCourse, approve, flowMsg, null, null);
        }

        result.put("consent", "ok");
        return result;
    }

    private void targetInvoke(FlowCourse course, FlowMessage message) {
        try {
            if (!Objects.isNull(course) && StringUtils.isNotBlank(course.getInvokeName())) {
                String[] in = course.getInvokeName().split("\\.");
                dynamicMethodExecutor.executeDynamicMethod(in[0], in[1], message.getFrameId());
            }
        } catch (Exception e) {
            //ignore this e
        }
    }

    private void setOtherState(FlowApprove approve, List<FlowApprove> faList) {
        StringBuffer ids = new StringBuffer();
        faList.forEach(item -> {
            if (!StringUtils.equals(item.getId(), approve.getId()) && (item.getApproveState() != 3 && item.getApproveState() != 6)) {
                ids.append("'");
                ids.append(item.getId());
                ids.append("',");
            }
        });
        if (ids.length() > 0) {
            FlowApprove nfa = new FlowApprove();
            nfa.setId(approve.getId());
            nfa.setApproveState(5);
            nfa.setApproveDate(approve.getApproveDate());
            nfa.setDate(nfa.getApproveDate());
            try {
                nfa.setContent(approve.getAcceptUser().getName() + ":" + approve.getContent());
            } catch (Exception e) {
                nfa.setContent("" + approve.getContent());
            }
            nfa.setId(ids.substring(0, ids.length() - 1));
            FlowApproveServiceImpl.this.updateOtherStatus(nfa);
        }
    }


    @Override
    public int breakFLow(FlowApprove fa, FlowCourse link) {
        FlowMessage msg = flowMessageService.getMessageById(fa.getFlowMessageId());
        Staff staff = staffService.getStaffById(msg.getStaffId());
        if (msg.getState() == 1) {
            /*
             * 处理中的流程才可以中断
             * 更新sdpo003主对象状态为中断状态 3:中断，1审批中，4：取消，2：审批完成
             */
            //更新sdpo004对象状态为4：不同意
            fa.setApproveState(4);
            fa.setApproveDate(DateFormat.getDateTime());
            fa.setDate(fa.getApproveDate());
            this.updateFlowApproveStatus(fa);

            this.breakNotify(fa, msg);

            if (Constant.FLOW_BREAK_HANDLER.containsKey(msg.getFrameCoding())) {
                String service = Constant.FLOW_BREAK_HANDLER.get(msg.getFrameCoding());
                dynamicMethodExecutor.executeDynamicMethod(service, msg);
            }

            //查询指定节点，插入等待审批节点
            link.setParentId(flowCourseService.getParentId(link.getId()));
            link.setFlowId(msg.getHistroryId());
            link.setFrameId(msg.getFrameId());
            List<FlowCourseBRelation> relations = flowCourseRelationService.getRelationByCourseId(link.getId());
            flowMessageService.insertCourseRelation(msg.getFlow(), relations);

            //查询驳回过程中审批人列表
            List<FlowApprove> approves = getByCourseId(link.getId(), msg.getId());

            //循环审批人，重新添加到数据库
            List<CoursePerson> cpList = link.getPersonList();
            for (int x = 0; x < cpList.size(); x++) {
                CoursePerson cp = cpList.get(x);
                flowMessageService.saveCoursePerson(msg, link, cp);
                if (link.getPubPerson() == 1) {

                    //自由选人，直接从之前选择的审批人中遍历添加
                    approves.forEach(f -> {
                        if (f.getApproveState() == 3) {
                            f.setId(UUID.randomUUID().toString());
                            f.setContent("");
                            f.setApproveState(0);
                            f.setApproveDate("");
                            f.setAcceptDate(DateUtil.getDatetime());
                            f.setReadDate("");
                            f.setDate(f.getAcceptDate());
                            f.setAccrptDate(f.getAcceptDate());
                            addApprove(f);
                        }
                    });
                } else {

                    //非自由选人，获取审批人列表
                    List<Staff> staffList = flowMessageService.getStaffByType(cp, staff);
                    for (int i = 0; i < staffList.size(); i++) {
                        Staff s = staffList.get(i);
                        FlowApprove f = new FlowApprove();
                        //                        生成消息主键id
                        f.setId(UUID.randomUUID().toString());
//                        设置发送人员id
                        f.setStaffId(staff.getId());
//                        设置过程id
                        f.setCourseId(link.getId());
//                        设置流程消息主表id
                        f.setFlowMessageId(msg.getId());
//                        设置审批人id
                        f.setAcceptStaffId(s.getId());
//                        设置发送时间
                        f.setAcceptDate(DateUtil.getDatetime());
//                        设置审批过程序号
                        f.setPo00415(i);
//                        设置审批人序号
                        f.setPo00414((byte) cp.getSerial());
//                        设置时间列
                        f.setDate(f.getAcceptDate());
//                        设置发送时间
                        f.setAccrptDate(f.getAcceptDate());
                        addApprove(f);
                    }
                }
            }

            return 1;
        } else {
            return -1;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int breakFlow(FlowApprove approve) {
        if (approve.getId() == null || approve.getFlowMessageId() == null) {
            return -1;
        }
        FlowMessage msg = flowMessageService.getMessageById(approve.getFlowMessageId());
        if (msg.getState() == 1) {

            /*
             * 处理中的流程才可以中断
             * 更新sdpo003主对象状态为中断状态 3:中断，1审批中，4：取消，2：审批完成
             */
            flowMessageService.updateMsgStatus(approve.getFlowMessageId(), 3);
            //更新sdpo004对象状态为4：不同意
            approve.setApproveState(4);
            approve.setApproveDate(DateFormat.getDateTime());
            approve.setDate(approve.getApproveDate());
            this.updateFlowApproveStatus(approve);
            //异步通知
            executorService.submit(() -> {
                breakNotify(approve, msg);
                if (Constant.FLOW_BREAK_HANDLER.containsKey(msg.getFrameCoding())) {
                    String service = Constant.FLOW_BREAK_HANDLER.get(msg.getFrameCoding());
                    dynamicMethodExecutor.executeDynamicMethod(service, msg);
                }
            });

            return 1;
        }
        return -1;
    }

    private void breakNotify(FlowApprove approve, FlowMessage msg) {
        if (flowNotifyService != null) {
            List<Staff> temp = new ArrayList<>();
            temp.add(staffService.getStaffById(msg.getStaffId()));
            try {
                approve.setMessage(msg);
                ApproveInfoTask.faDispose(approve);
                flowNotifyService.offFlow(approve.getAcceptUser(), temp, approve);
            } catch (IOException e) {

            }
        }
    }

    @Override
    public List<FlowApprove> isRecall(FlowApprove approve, FlowMessage msg) {
        FlowCourse fc = flowCourseService.getNextFlowCourseBByCourseId(approve.getCourseId(), msg.getHistroryId(), msg);
        if (Objects.isNull(fc)) {
            //最后一步，不支持撤回
            return null;
        }

        List<FlowApprove> fas = flowApproveMapper.getApproveList(msg.getHistroryId(), fc.getId(), msg.getId());
        for (int i = 0; i < fas.size(); i++) {
            FlowApprove fa = fas.get(i);
            if (!Objects.isNull(fa) && fa.getApproveState() > 2) {
                //已经有人审批，不支持撤回
                return null;
            }
        }
        return fas;
    }

    @Override
    public Integer recall(FlowApprove approve, FlowMessage msg) {
        List<FlowApprove> fas = isRecall(approve, msg);
        if (!Objects.isNull(fas)) {
            //可以撤回，删除当前审批集合
            fas.forEach(item -> {
                deleteById(item.getId());
            });
            //更改撤回步骤的状态为已读
            updateState(1, approve.getId());
        }
        return 1;
    }

    private int deleteById(String id) {
        flowApproveMapper.deleteRecordById(id);
        return flowApproveMapper.deleteById(id);
    }

    @Override
    public Integer updateApproveDate(String date, String id) {
        executorService.schedule(() -> {
            flowApproveMapper.updateApproveDateRecord(date, id);
        }, 100, TimeUnit.MILLISECONDS);
        return flowApproveMapper.updateApproveDate(date, id);
    }

    @Override
    public Integer updateApproveState(FlowApprove flowApprove) {
        //更新审批消息状态
        executorService.submit(() -> {
            flowApproveMapper.updateApproveStateRecord(flowApprove);
        });
        return flowApproveMapper.updateApproveState(flowApprove);
    }

    @Override
    public List<FlowApprove> getMessages(String userId,
                                         String start,
                                         String end,
                                         List<String> states,
                                         List<String> msgStates,
                                         Integer type,
                                         String searchText,
                                         Integer pageNum,
                                         Integer pageSize,
                                         String fqFlag) {
        Map<String, Object> params = new HashMap(16);
        params.put("userId", userId);
        params.put("startDate", start);
        params.put("endDate", end);
        params.put("states", states);
        params.put("msgState", msgStates);
        params.put("type", type);
        params.put("str", searchText);
        params.put("fqFlag", fqFlag);
        String order = "a.po00408 DESC";
        if (type != null && type <= 2) {
            order = "a.po00406 DESC";
        }
        Page p = PageHelper.startPage(pageNum, pageSize, order);
        p.setCount(false);
        return flowApproveMapper.getMessagesV2(params);
    }


    @Override
    public List<FlowApprove> getMessages(String userId,
                                         String start,
                                         String end,
                                         List<String> states,
                                         List<String> msgStates,
                                         Integer type,
                                         String searchText,
                                         Integer page,
                                         Integer itemsPerPage,
                                         String fqFlag, String order) {
        Map<String, Object> params = new HashMap(16);
        params.put("userId", userId);
        params.put("startDate", start);
        params.put("endDate", end);
        params.put("states", states);
        params.put("msgState", msgStates);
        params.put("type", type);
        params.put("str", searchText);
        params.put("fqFlag", fqFlag);
//        String order = "a.po00408 DESC";
//        if (type != null && type <= 2) {
//            order = "a.po00406 DESC";
//        }
        PageHelper.startPage(page, itemsPerPage, order);
        return flowApproveMapper.getMessagesV2(params);
    }

    @Override
    public List<FlowApprove> getMessageByTimeout(Map<String, String> params) {
        return flowApproveMapper.getMessageByTimeout(params);
    }

    @Override
    public void insertEmailHistory(String id, String state) {
        flowApproveMapper.insertEmailHistory(id, state);
    }

    @Override
    public String getApproveState(String id) {
        return flowApproveMapper.getApproveState(id);
    }

    @Override
    public List<FlowApprove> getMessagesAll(String userId, String start, String end, List<String> params, List<String> msgStates, Integer type, String searchText, String flowFilter, Integer pageNum,Integer pageSize) {
        HashMap<String, Object> pmap = new HashMap(16);
        pmap.put("userId", userId);
        pmap.put("startDate", start);
        pmap.put("endDate", end);
        pmap.put("states", params);
        pmap.put("msgState", msgStates);
        pmap.put("type", type);
        pmap.put("str", searchText);
        pmap.put("flowFilter", flowFilter);
        String order = "a.po00408 DESC";
        if (type != null && type <= 2) {
            order = "a.po00406 DESC";
        }
        PageHelper.startPage(pageNum,pageSize, order);
        return flowApproveMapper.getMessagesV2(pmap);
    }

    @Override
    public void updateFlag(String id, String fqFlag) {
        executorService.schedule(() -> {
            flowApproveMapper.updateFlagRecord(id, fqFlag);
        }, 100, TimeUnit.MILLISECONDS);
        flowApproveMapper.updateFlag(id, fqFlag);
    }

    @Override
    public boolean isFlag(String messageId, String flag) {
        List<FlowApprove> flowApproves = flowApproveMapper.getApproveByFlag(messageId, flag);
        return !flowApproves.isEmpty();
    }

    @Override
    public FlowApprove getLastApprove(String flowMessageId) {
        PageHelper.startPage(1, 1);
        return flowApproveMapper.getLastApprove(flowMessageId);
    }

    @Override
    public String getEmailHistory(String id) {
        return flowApproveMapper.getEmailHistory(id);
    }

    @Override
    public List<FlowApprove> getMessagesAll2(String userId,
                                             String start,
                                             String end,
                                             Integer[] params,
                                             Integer[] msgStates,
                                             Integer type,
                                             String searchText,
                                             String flowFilter,
                                             Integer pageNum,
                                             Integer pageSize) {
        Map<String, Object> pmap = new HashMap(16);
        pmap.put("userId", userId);
        pmap.put("startDate", start);
        pmap.put("endDate", end);
        pmap.put("states", params);
        pmap.put("msgState", msgStates);
        pmap.put("type", type);
        pmap.put("str", searchText);
        pmap.put("flowFilter", flowFilter);
        String order = "a.po00406,a.po00408 DESC";
        if (type != null && type <= 2) {
            order = "a.po00406 DESC";
        }
        PageHelper.startPage(pageNum,pageSize, order);
        return flowApproveMapper.getMessagesAll2(pmap);
    }

    @Override
    public int recall(FlowApprove approve, Boolean recallContent) {
        FlowApprove temp = isApprove(approve);
        //判断该过程是否同意过，如果为null则没有同意过
        if (temp == null) {
            return -1;
        }
        FlowMessage msg = flowMessageService.getMessageById(approve.getFlowMessageId());

        int row = recall(approve, msg);
        temp.setApproveState(1);
        if (recallContent) {
            temp.setApproveDate("");
            temp.setContent("");
        }
        updateFlowApproveStatus(temp);
        updateFlag(approve.getId(), "");
        //判断之前是否还有分批付款标签，如果没有则更新主单据,标签取消
        if (!isFlag(msg.getId(), "1")) {
            flowMessageService.updateFlag(msg.getId(), "");
        }
        return row;

    }

    @Override
    public List<FlowApprove> getPastApprove() {
        return this.flowApproveMapper.getPastApprove();
    }

    @Override
    public List<FlowApprove> queryErrorFlow(String startDatetime, String endDatetime) {
        return this.flowApproveMapper.queryErrorFlow(startDatetime, endDatetime);
    }

    @Override
    public List<FlowApprove> getMessagesHistory(HashMap<String, String> params) {
        Page p = PageHelper.startPage(Integer.parseInt(params.get("pageNumber")),
                Integer.parseInt(params.get("pageSize")),
                false);
        p.setOrderBy(params.get("sort"));
        return this.flowApproveMapper.getMessageHistory(params);
    }

    @Override
    public Map<String, Object> appendApprove(FlowApprove approve) {
        Map<String, Object> result = new HashMap<>(16);
        approve.setId(UUID.randomUUID().toString());
        String date = DateUtil.getDatetime();
        approve.setAccrptDate(date);
        approve.setReadDate(date);
        approve.setApproveDate(date);
        approve.setApproveState(3);
        addApprove(approve);
        executorService.submit(()->{
            flowApproveMapper.addApproveRecord(approve);
        });
        FlowMessage fm = flowMessageService.getMessageById(approve.getFlowMessageId());
        FlowCourse fc = flowCourseService.getById(approve.getCourseId(), fm.getHistroryId());
//        自动知会
        if (fc != null) {
            Staff s = staffService.getStaffById(fm.getStaffId());
            List<CoursePerson> cps = fc.getPersonList();
            for (CoursePerson cp : cps) {
                if (cp.getType() == 1) {
                    List<Staff> staffs = flowMessageService.getStaffByType(cp, s);
                    String[] notifyStaff = new String[staffs.size()];
                    int x = 0;
                    for (Staff staff : staffs) {
                        notifyStaff[x] = staff.getId();
                        x++;
                    }
                    inFromUser(notifyStaff, approve.getId());
                }
            }
        }
        return result;
    }

    @Override
    public void inFromUser(String[] staffList, String flowApproveId) {

        FlowApprove fa = getFlowApproveById(flowApproveId);
        if (Objects.isNull(fa)) {
            return;
        }

        Map<String, Object> params = new HashMap(16);
        params.put("date", DateFormat.getDateTime());
        params.put("flowApproveId", flowApproveId);
        List<Staff> staff = new ArrayList<>();

        FlowApprove inform = new FlowApprove();
        inform.setCourse(fa.getCourse());
        inform.setCourseId(fa.getCourseId());
        inform.setAcceptDate(DateUtil.getDatetime());
        inform.setAccrptDate(inform.getAcceptDate());
        inform.setSendUser(fa.getAcceptUser());
        inform.setApproveState(5);
        inform.setPo00415(fa.getPo00415());
        inform.setDate(inform.getAcceptDate());
        inform.setFlowMessageId(fa.getFlowMessageId());
        inform.setStaffId(fa.getAcceptUser().getId());
        inform.setPo00418Id("");

        int x = 0;
        for (String staffId : staffList) {
            if (staffId != null) {
                try {
                    inform.setPo00414((byte) x);
                    x++;
                    inform.setAcceptStaffId(staffId);
                    inform.setId(UUID.randomUUID().toString());
                    addApprove(inform);
//                    inFormUser(params);
                    //通知该用户，获取最新消息，先判断是否在线
                    MyWebSocketHandle.sendMeg(staffId, "{\"type\":\"2\"}");
                    staff.add(staffService.getStaffById(staffId));
                } catch (Exception e) {
                    Logger.getLogger(FlowApproveServiceImpl.class).error("/inFormUser-----------知会消息异常：" + e.getMessage());
                    continue;
                }
            }
        }
    }

    @Override
    public FLowConsentModel publicApprove(SelectApprove selectApprove, Staff staff) {
        FLowConsentModel model = new FLowConsentModel();
        if (selectApprove.getApprove() == null || selectApprove.getFlowCourse() == null) {
            model.setState(3);
            model.setMsg("请指定审批人");
            return model;
        }
        List<CoursePerson> cps = selectApprove.getFlowCourse().getPersonList();
        if (cps == null) {      //没有选择审批人员
            model.setState(3);
            model.setMsg("请指定审批人");
            return model;
        } else {          //有审批人员，遍历人员是否有效
            Iterator<CoursePerson> icp = cps.iterator();
            Iterator<Staff> is = null;
            CoursePerson cp = null;
            Staff f = null;
            while (icp.hasNext()) {
                cp = icp.next();
                if (cp == null || cp.getStaff() == null) {     //选中审批角色或人员集合为空，删除
                    icp.remove();
                } else {
                    is = cp.getStaff().iterator();
                    while (is.hasNext()) {
                        f = is.next();
                        if (f == null) {
                            is.remove();
                        }
                    }
                    if (cp.getStaff().size() <= 0)      //该步骤中没有审批人员，删除
                    {
                        icp.remove();
                    }
                }
            }
            if (cps.size() <= 0) {        //没有指定审批角色或人员集合
                model.setState(3);
                model.setMsg("请指定审批人");
                return model;
            }
        }


        FlowApprove approve = selectApprove.getApprove();
//        设置当前用户为该步骤审批的接收人
        approve.setAcceptUser(staff);
        FlowCourse course = selectApprove.getFlowCourse();
        for (CoursePerson person : course.getPersonList()) {
            if (person != null) {
                Map<String, Object> result = consentByPerson(approve, person, course);
                if (result.get("no-person") != null) {
                    model.setState(3);
                    model.setMsg("请指定审批人");
                } else {
                    model.setFlowSuccess(true);
                    model.setState(1);
                    model.setMsg("成功");
                }
            }
        }
        return model;
    }

    @Override
    public void clearNotify(String endDate) {
        String startDate = "2010-01-01 00:00:00";
        Page<FlowApprove> flowApproves = getClearData(endDate);
        int pages = flowApproves.getPages();
        StringBuffer sb = new StringBuffer();
        for (int x = 1; x <= pages; x++) {
            for (int i = 0; i < flowApproves.size(); i++) {
                sb.append("'");
                sb.append(flowApproves.get(i).getId());
                sb.append("',");
            }
            if (sb.length() > 0) {
                flowApproveMapper.deleteNotifyHistorys(sb.substring(0, sb.length() - 1));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            PageHelper.startPage(1, 100, "po00406 desc");
            flowApproves = (Page<FlowApprove>) flowApproveMapper.queryExpireData(startDate, endDate);
        }
    }

    private Page<FlowApprove> getClearData(String endDate) {
        String startDate = "2010-01-01 00:00:00";
        PageHelper.startPage(1, 100, "po00406 desc");
        Page<FlowApprove> flowApprovePage = (Page<FlowApprove>) flowApproveMapper.queryExpireData(startDate, endDate);
        return flowApprovePage;
    }

    @Override
    public void clearExpireData(String endDate) {
//        this.checkNoRecord(endDate);
        String startDate = "2010-01-01 00:00:00";
        Page<FlowApprove> flowApprovePage = getClearData(endDate);
        int pages = flowApprovePage.getPages();
        StringBuffer sb = new StringBuffer();
        for (int x = 1; x <= pages; x++) {
            for (int i = 0; i < flowApprovePage.size(); i++) {

                FlowApprove fa = flowApprovePage.get(i);
                FlowApprove record = flowApproveMapper.queryRecordById(fa.getId());
                if (!Objects.isNull(record)) {
                    //记录表存在，方可清除
                    sb.append("'");
                    sb.append(fa.getId());
                    sb.append("',");
                    //更新记录表信息
                    if (fa.getApproveState() != record.getApproveState() || !StringUtils.equals(fa.getContent(), record.getContent())) {
                        record.setApproveState(fa.getApproveState());
                        record.setApproveDate(fa.getApproveDate());
                        record.setContent(fa.getContent());
                        record.setReadDate(fa.getReadDate());
                        record.setPo00421(fa.getPo00421());
                        flowApproveMapper.updateApproveStateRecord(record);
                    }
                } else {
                    fa.setPo00418Id(fa.getId());
                    flowApproveMapper.addApproveRecord(fa);
                }
                myApproveAttachedService.deleteById(fa.getPo00418Id());
            }
            if (sb.length() > 0) {
                flowApproveMapper.deleteInId(sb.substring(0, sb.length() - 1));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            PageHelper.startPage(1, 100, "po00406 desc");
            flowApprovePage = (Page<FlowApprove>) flowApproveMapper.queryExpireData(startDate, endDate);
        }
    }

    @Override
    public void checkNoRecord(String startTime, String endTime) {
        List<FlowApprove> flowApproves = flowApproveMapper.queryNoRecord(startTime, endTime);
        flowApproves.forEach(item -> {
            item.setPo00418Id(item.getId());
            flowApproveMapper.addApproveRecord(item);
        });
        //检查分期支付标记
        List<FlowApprove> fa = flowApproveMapper.queryFqFlagList(startTime, endTime);
        fa.forEach(item -> {
            flowApproveMapper.updateFlagRecord(item.getId(), item.getPo00421());
        });
    }

    @Override
    public void checkNoRecord(String endTime) {
        //检查未记录数据
        this.checkNoRecord("2011-01-01 00:00:00", endTime);
    }

    @Override
    public void checkPrevDayNoRecord() {
        String date = DateUtil.formatDate(DateUtil.rollDay(DateUtil.getNowDate(), -1));
        String startDate = date + " 00:00:00";
        String endDate = date + " 23:59:59";
        this.checkNoRecord(startDate, endDate);
    }

    @Override
    public List<FlowApprove> getFlowApproveRecordByMessageId(String flowMessageId) {
        List<FlowApprove> fa = flowApproveMapper.getFlowApproveRecordByMessageId(flowMessageId);
        return setCourse(fa);
    }

    @Override
    public Integer notifyMyCount(HashMap<String, Object> param) {
        return flowApproveMapper.notifyMyCount(param);
    }

    @Override
    public int updateStateAll(String staffId) {
        executorService.schedule(() -> {
            flowApproveMapper.updateStateAllRecord(staffId);
        }, 100, TimeUnit.MILLISECONDS);
        return flowApproveMapper.updateStateAll(staffId);
    }

    @Override
    public List<FlowApprove> getFlowApproveByCourse(String courseId, String flowMessageId) {
        return flowApproveMapper.getFlowApproveByCourse(courseId, flowMessageId);
    }

    @Override
    public void deleteNotifyHistory(String id) {
        flowApproveMapper.deleteNotifyHistory(id);
    }

    @Override
    public FlowApprove setLastCourse(FlowApprove fa) {
        FlowMessage fm;
        FlowCourse fc;
        fm = fa.getMessage();
        if (fm != null) {
            try {
                fc = flowCourseService.getNextFlowCourseBByCourseId(fa.getCourseId(), fm.getHistroryId(), fm);
                if (fc == null) {        //没有步骤了，设置为最后一个步骤
                    fa.setLastCourse(1);
                    flowApproveMapper.updateLastCourse(fa.getId(), 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogManager.getLogger("mylog").error("setLastCourse:" + e.getMessage() + "," + fa.getMessage().getTitle());
            }
        }
        return fa;
    }

    @Override
    public List<FlowApprove> getMessagesHistoryNow(HashMap<String, String> params) {
        PageHelper.startPage(Integer.parseInt(params.get("pageNumber")),
                Integer.parseInt(params.get("pageSize")),
                params.get("sort"));
        return this.flowApproveMapper.getMessageHistoryNow(params);
    }

    @Override
    public void queryErrorRecord(String startDatetime, String endDatetime) {
        List<FlowApprove> fa = this.flowApproveMapper.queryErrorRecord(startDatetime, endDatetime);
        fa.forEach(item -> {
            flowApproveMapper.addApprove(item);
            System.out.println("补充记录数据" + item.getId());
        });
    }

    @Override
    public int deleteByMsgId(String msgId) {
        int row = 0;
        row += this.flowApproveMapper.deleteByMsgId(msgId);
        row += this.flowApproveMapper.deleteRecordByMsgId(msgId);
        return row;
    }

    @Override
    public FlowApprove getFlowApproveByStaff(String flowMessageId, String courseId, String staffId) {
        PageHelper.startPage(1, 1);
        return this.flowApproveMapper.getFlowApproveByStaff(flowMessageId, courseId, staffId);
    }

    @Override
    public FlowApprove queryLastApprove(FlowMessage fm) {
        List<FlowApprove> approves = getFlowApproveByMessageId(fm.getId());
        for (int i = (approves.size() - 1); i >= 0; i--) {
            FlowApprove fa = approves.get(i);
            if (fa.getApproveState() == 3) {
                fm.setDate(fa.getApproveDate());
                return fa;
            }
        }
        return null;
    }


}
