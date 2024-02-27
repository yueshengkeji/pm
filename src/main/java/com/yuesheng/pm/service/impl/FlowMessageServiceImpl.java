package com.yuesheng.pm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.listener.WebParam;
import com.yuesheng.pm.mapper.FlowMessageMapper;
import com.yuesheng.pm.model.FLowConsentModel;
import com.yuesheng.pm.model.FLowMessageQuery;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author XiaoSong
 * @date 2016-08-16
 * 审批流程消息服务实现类
 */
@Service("flowMessageService")
public class FlowMessageServiceImpl implements FlowMessageService {
    @Autowired
    private FlowMessageMapper flowMessageMapper;
    @Autowired
    private FlowHistoryService flowHistoryService;
    @Autowired
    private FlowCourseService flowCourseService;
    @Autowired
    private FlowCourseRelationService flowCourseRelationService;
    @Autowired
    private CoursePersonAttachedService coursePersonAttachedService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MyApproveService myApproveService;
    @Autowired
    private FlowApproveService flowApproveService;
    @Autowired
    private MyApproveMainService myApproveMainService;
    @Autowired
    public SectionService sectionService;
    @Autowired
    public StaffService staffService;
    @Autowired
    public CourseJudeService courseJudeService;
    @Autowired
    public FlowNotifyService flowNotifyService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private DynamicMethodExecutorImpl dynamicMethodExecutor;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public Integer addFlowMessage(FlowMessage flowMessage) {
        if (flowMessage.getSql() == null) {
            String s = getLocalSql(flowMessage.getFrameCoding());
            if (StringUtils.isNotBlank(s)) {
                flowMessage.setSql(s);
            } else {
                flowMessage.setSql(getSql(flowMessage.getFrameCoding()));
                if (flowMessage.getSql() == null) {
                    flowMessage.setSql("");
                }
            }
        }
        flowMessage.setDate("");
        return flowMessageMapper.addFlowMessage(flowMessage);
    }

    private String getMenuSql(String frameCoding) {
        Menu menu = menuService.getProMenuByCoding(frameCoding);
        if (Objects.isNull(menu) || StringUtils.isBlank(menu.getFlowSql())) {
            return "";
        } else {
            return menu.getFlowSql();
        }
    }

    private String getLocalSql(String frameCoding) {
        String sql = getMenuSql(frameCoding);
        if (StringUtils.isBlank(sql)) {
            if (StringUtils.equals(frameCoding, "1320280")) {
                return "select * from(\n" +
                        "select id,\n" +
                        "isnull(sdpj003.pj00302,'''') as Department,\n" +
                        "isnull(sdpj004.pj00402,'''') as Personnel, \n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=id),0)\n" +
                        "\n" +
                        "from pro_apply_dine\n" +
                        "left join sdpj003 on section_id=pj00301 \n" +
                        "left join sdpj004 on staff_id=pj00401\n" +
                        ")#Temp Where 1=1";
            } else if (StringUtils.equals(frameCoding, "137214")) {
                return "SELECT * FROM(\n" +
                        "select sdpo057.*,\n" +
                        "po05703Name=ISNULL((select pj00402 from sdpj004 where pj00401=po05703),''''),\n" +
                        "po05704Name=ISNULL((select pj00302 from sdpj003 where pj00301=po05704),''''),\n" +
                        "po05710Name=ISNULL((select pj00402 from sdpj004 where pj00401=po05710),''''),\n" +
                        "po05713Name=ISNULL((select pj00402 from sdpj004 where pj00401=po05713),''''),\n" +
                        "pa00102=ISNULL((select pa00102 from sdpa001 where pa00101=po05716),''''),\n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=po05701),0)\n" +
                        "from sdpo057\n" +
                        ")#TEMP Where 1=1\n";
            } else if (StringUtils.equals(frameCoding, "1320276")) {
                return "select * from(\n" +
                        "select id,staff,date,overtime,begin_time,end_time\n" +
                        ",remark,hour,name,\n" +
                        "isnull(sdpj003.pj00302,'''') as Department,\n" +
                        "isnull(sdpj004.pj00402,'''') as Personnel,\n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=id),0)\n" +
                        "\n" +
                        "from pro_overtime\n" +
                        "left join sdpj004 on staff=pj00401\n" +
                        "left join sdpj003 on pj00417=pj00301 \n" +
                        ")#Temp Where 1=1\n";
            } else if (StringUtils.equals(frameCoding, "1320281")) {
                return "select * from(\n" +
                        "select id,staff,pay_money,\n" +
                        "isnull(sdpj003.pj00302,'''') as Department,\n" +
                        "isnull(sdpj004.pj00402,'''') as Personnel,\n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=id),0)\n" +
                        "\n" +
                        "from pro_subcontract_pay\n" +
                        "left join sdpj004 on staff=pj00401\n" +
                        "left join sdpj003 on pj00417=pj00301 \n" +
                        ")#Temp Where 1=1";
            } else if (StringUtils.equals(frameCoding, "132029")) {
                return "select * from(\n" +
                        "select id,staff_id,pay_money,\n" +
                        "isnull(sdpj003.pj00302,'''') as Department,\n" +
                        "isnull(sdpj004.pj00402,'''') as Personnel,\n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=id),0)\n" +
                        "\n" +
                        "from pro_other_pay\n" +
                        "left join sdpj004 on staff_id=pj00401\n" +
                        "left join sdpj003 on pj00417=pj00301 \n" +
                        ")#Temp Where 1=1";
            } else if (StringUtils.equals(frameCoding, "1320274")) {
                return "select * from(\n" +
                        "select uuid,traveller,department,total_time,\n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=uuid),0)\n" +
                        "\n" +
                        "from travel_application\n" +
                        ")#Temp Where 1=1";
            } else if (StringUtils.equals(frameCoding, "1320285")) {
                return "select * from(\n" +
                        "select id,staff_id,staff_name,section_id,section_name as Department\n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=id),0)\n" +
                        "\n" +
                        "from out_car_history\n" +
                        ")#Temp Where 1=1";
            } else if (StringUtils.equals(frameCoding, "1320285")) {
                return "select * from(\n" +
                        "select id,staff_id,staff_name,section_id,section_name as Department\n" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=id),0)\n" +
                        "\n" +
                        "from out_car_history\n" +
                        ")#Temp Where 1=1";
            } else if (StringUtils.equals(frameCoding, "1320286")) {
                return "select * from(\n" +
                        "select id,staff,pj00302 as Department,entertain_dining_standard,entertain_types,entertain_car" +
                        "OAState=isnull((select po00308 from sdpo003 where po00307=id),0)\n" +
                        "\n" +
                        "from out_car_history left join sdpj003 on section=pj00301\n" +
                        ")#Temp Where 1=1";
            } else {
                return "";
            }
        } else {
            return sql;
        }
    }

    @Override
    public void addFlowMessageHistory(FlowMessage flowMessage) {
        flowMessageMapper.addFlowMessageHistory(flowMessage);
    }

    @Override
    public FlowMessage getMessageById(String id) {
        return flowMessageMapper.getMessageById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FLowConsentModel startFlow(Flow flow, FlowMessage flowMessage, Staff staff, FlowHistory history, FlowCourse pubPerson) {
//        发起人和第一位审批人是否同一人
        boolean isMy = false;
        FlowApprove fa = null;
//        添加记录到数据库中
        flowHistoryService.addHistory(history);
        flowMessage.setFlow(history);
        flowMessage.setHistroryId(history.getId());
//        添加消息对象到数据库      1
        this.addFlowMessage(flowMessage);
        MyApproveMain approveMain = getMyApproveMain(flowMessage);
        MyApprove approve = getMyApprove(flowMessage, staff);
//        【我的发起附表】      4
        FlowApprove flowApprove = new FlowApprove();
//        获取该流程中所有的步骤
        List<FlowCourse> course = flowCourseService.getFlowCourseByFlow(flow.getId());
//        添加第一个审批人员到数据库
        FlowCourse flowCourse = null;
//        实例化【我的审批附表】对象
        MyApproveAttached attached = new MyApproveAttached();
        List<List<FlowCourseBRelation>> courseRelations = new ArrayList();
//        遍历所有步骤，添加到sdpo002b表中，并添加第一步骤到sdpo003表中
        for (int i = 0; i < course.size(); i++) {
//            获取步骤对象
            flowCourse = course.get(i);
            flowCourse.setParentId(flowCourseService.getParentId(flowCourse.getId()));
            flowCourse.setFlowId(history.getId());
            flowCourse.setFrameId(flowMessage.getFrameId());
            //根据当前步骤id，获取步骤关系对象
            List<FlowCourseBRelation> relations = flowCourseRelationService.getRelationByCourseId(flowCourse.getId());
            courseRelations.add(relations);
            List<CoursePerson> coursePerson;
            if (pubPerson != null && flowCourse.getId().equals(pubPerson.getId())) {
//                是自由选人,并且是当前步骤时才动作
                coursePerson = pubPerson.getPersonList();
            } else {
//                获取步骤中所有人员集合
                coursePerson = flowCourse.getPersonList();
            }
            //遍历步骤中所有审批和知会人员
            for (int x = 0; x < coursePerson.size(); x++) {
                CoursePerson person = saveCoursePerson(flowMessage, flowCourse, coursePerson.get(x));
                /*
                 * 【我的审批】对象
                 */
                if (flowCourse.getParentId() == null && person.getType() == 0) {
//                    父节点为null时 并且类型等于0（审批权） =第一位审批人员，第一位添加到sdpo003表中，及为审批人员才添加
                    List<Staff> staffList;
                    if (pubPerson != null) {
                        try {
//                          自由选人不为null
                            staffList = pubPerson.getPersonList().get(x).getStaff();
                        } catch (IndexOutOfBoundsException e) {
                            continue;
                        }
                    } else {
//                        非自由选人
                        staffList = getStaffByType(person, staff);
                    }
//                    遍历审批角色中人员集合，并添加到数据库中
                    for (int n = 0; n < staffList.size(); n++) {
                        Staff temp = staffList.get(n);
                        if (temp == null) {
                            continue;
                        }
//                        生成审批消息id
                        approve.setId(UUID.randomUUID().toString());
//                        生成【我的审批附表】主键id
                        attached.setId(UUID.randomUUID().toString());
//                        设置发送人员
                        attached.setSendPersong(staff.getId());
//                        设置接收时间
                        attached.setAcceptDate("");
//                        设置【我的发起附表】主表id
                        attached.setApproveId(approve.getId());
//                        设置审批人员
                        attached.setApprovePerson(temp.getId());
//                        生成消息主键id
                        flowApprove.setId(UUID.randomUUID().toString());
//                        设置发送人员id
                        flowApprove.setStaffId(staff.getId());
//                        设置过程id
                        flowApprove.setCourseId(person.getCourseId());
//                        设置流程消息主表id
                        flowApprove.setFlowMessageId(flowMessage.getId());
//                        设置审批人id
                        flowApprove.setAcceptStaffId(temp.getId());
//                        设置发送时间
                        flowApprove.setAcceptDate(DateFormat.getDateTime());
//                        设置审批过程序号
                        flowApprove.setPo00415(i);
//                        设置审批人序号
                        flowApprove.setPo00414((byte) person.getSerial());
//                        设置时间列
                        flowApprove.setDate(flowMessage.getDate());
//                        设置发送时间
                        flowApprove.setAccrptDate(approve.getAcceptDate());
//                        添加【我的审批附表 sdpo006】和【我的审批附表 sdpo007】消息到数据库
                        myApproveService.addMyApprove(approve, attached);
//                        设置【我的审批附表】id
                        flowApprove.setPo00418Id(attached.getId());
//                        添加【我的发起sdpo004】和发起消息附表【sdpo004_AllRecord】审批消息到数据库
                        flowApproveService.addApprove(flowApprove);
                        /*
                          【我的审核消息】
                          生成id主键
                         */
                        approveMain.setId(UUID.randomUUID().toString());
//                        设置接收时间
                        approveMain.setSendDate(flowApprove.getDate());
//                        设置sdpo007表id
                        approveMain.setApproveAttachedId(attached.getId());
//                        设置【我的发起】消息对象主键id
                        approveMain.setFlowMessageId(flowMessage.getId());
//                        设置发起人姓名
                        approveMain.setStaffName(staff.getName());
//                        添加【我的审批】超级对象到数据库
                        myApproveMainService.addApproveMain(approveMain);

                        //复制当前对象
                        if (staff.getId().equals(temp.getId())) {
                            isMy = true;
                            fa = flowApprove.clone();
                        }
                    }
                    try {
//                        添加当前流程过程到sdpo020b_Instance表中
                        flowCourseService.addFlowCourseBInstance(flowCourse);
                    } catch (Exception ignored) {
                        //忽略重复添加异常
                        LogManager.getLogger(FlowMessageServiceImpl.class).error("流程发起，添加当前过程实例异常：" + ignored.getMessage());
                    }
                }
            }
//            添加过程到sdpo020b记录表中
            flowCourseService.addFlowCourseB(flowCourse);
            /*
             * 判断该过程是否有判断集合，有则添加
             */
            addJudge(flowCourse, flowMessage.getFlow().getId());
        }
        /*
         * 遍历关系对象集合，并添加到当前流转sdpo020b_Relation关系表中
         */
        traversalCourseRelation(history, courseRelations);
//        再次生成新的uuid
        approve.setId(UUID.randomUUID().toString());
//        添加到数据库
        myApproveService.addMyApproveFirst(approve);
        FLowConsentModel result = new FLowConsentModel();
        if (isMy && fa != null) {
//            发起人自己审批时处理
            fa.setContent("同意");
            result = flowApproveService.consentApprove(fa);
            if (result.getState() == 2) {
                result.setState(5);
                result.setMsg("流程发起成功,需到《我的审批》中再次审批");
                result.setFlowMessage(flowMessage);
            }
        }
        return result;
    }

    @Override
    public CoursePerson saveCoursePerson(FlowMessage flowMessage,
                                         FlowCourse flowCourse,
                                         CoursePerson person) {
        CoursePersonAttached personAttached = new CoursePersonAttached();
        personAttached.setFrameId(flowMessage.getFrameId());
        personAttached.setCourseId(flowCourse.getId());
        personAttached.setCoursePersonId(person.getId());
        personAttached.setType(person.getType());
        personAttached.setSerial(person.getSerial());
        personAttached.setStaffId(person.getStaffId());
        personAttached.setFlowHistoryId(flowMessage.getHistroryId());
        personAttached.setStaffType(person.getStaffType());
        coursePersonAttachedService.addAttached(personAttached);
        return person;
    }

    private MyApprove getMyApprove(FlowMessage flowMessage, Staff staff) {
        //        实例化【我的审批】对象           3
        MyApprove approve = new MyApprove();
//        设置接收时间
        approve.setAcceptDate(DateFormat.getDateTime());
//        设置审批标题
        approve.setTitle(flowMessage.getTitle());
//        设置办文名称
        approve.setName(staff.getName() + ":" + flowMessage.getTitle() + "审批");
//        设置窗口代码
        approve.setFrameCoding(flowMessage.getFrameCoding());
//        设置采购订单id
        approve.setFrameId(flowMessage.getFrameId());
//        设置发起人id
        approve.setSendPerson(staff.getId());
        return approve;
    }

    private MyApproveMain getMyApproveMain(FlowMessage flowMessage) {
        //        创建【我的审批】超级对象 sdpo100
        MyApproveMain approveMain = new MyApproveMain();
//        创建审批标题
        approveMain.setTitle("审批：" + flowMessage.getTitle());
//        办文名称
        approveMain.setName(flowMessage.getTitle());
//        设置窗口编号
        approveMain.setFrameCoding(flowMessage.getFrameCoding());
//        设置窗口id号
        approveMain.setFrameId(flowMessage.getFrameId());
        return approveMain;
    }

    /**
     * 判断过程是否有判断集合，有则添加
     */
    private void addJudge(FlowCourse course, String historyId) {
        List<CourseJudge> judge = courseJudeService.getByCourse(course.getId());
        if (judge != null) {
            for (CourseJudge judge1 : judge) {
                if (judge1 != null) {
                    judge1.setHistoryId(historyId);
                    courseJudeService.addCourseJudgeForMsg(judge1);
                }
            }
        }
    }


    @Override
    public void updateMsgStatus(String flowMessageId, int status) {
        flowMessageMapper.updateMessage(flowMessageId, status, DateUtil.getDatetime());
    }

    @Override
    public Integer updateMsgDate(String data, String id) {
        return flowMessageMapper.updateMsgDate(data, id);
    }

    @Override
    public List<FlowMessage> getMessageByStaff(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("pageNumber").toString()),Integer.parseInt(params.get("pageSize").toString()));
        return flowMessageMapper.getMessageByStaff(params);
    }

    @Override
    public int getMessageByCount(Map<String, Object> params) {
        return flowMessageMapper.getMessageByCount(params);
    }

    @Override
    public void deleteMessage(String id) {
        deleteMessage(id, false);
    }

    /**
     * 遍历步骤管理和步骤判断关系，并添加到数据库
     *
     * @param history
     * @param courseRelations
     */
    public void traversalCourseRelation(FlowHistory history, List<List<FlowCourseBRelation>> courseRelations) {
        for (int i = 0; i < courseRelations.size(); i++) {
            List<FlowCourseBRelation> temp = courseRelations.get(i);
            insertCourseRelation(history, temp);
        }
    }

    @Override
    public void insertCourseRelation(FlowHistory history, List<FlowCourseBRelation> temp) {
        for (int x = 0; x < temp.size(); x++) {
            FlowCourseBRelation relation = temp.get(x);
//                设置流程记录id
            relation.setFlowHistoryId(history.getId());
            flowCourseRelationService.addRelationB(relation);
            //flowCourseRelationService.addCondition();
        }
    }

    /**
     * 获取职员集合
     *
     * @param person 审批人员，
     * @return 职员集合
     */
    @Override
    public List<Staff> getStaffByType(CoursePerson person, Staff staff) {
        List<Staff> staffList = new ArrayList<Staff>();
        switch (person.getStaffType()) {
            case Constant.APPROVE_SECTION:
//                部门
                staffList = sectionService.getStaffList(person.getStaffId());
                break;
            case Constant.APPROVE_ZHIWU:
//                根据职务编码获取职员集合
                staffList = dutyService.getStaffByDuty(person.getStaffId());
                break;
            case Constant.APPROVE_PERSON:
                /*
                 * 审批人
                 * 设置职员id，并添加到集合中
                 */
                Staff temp = staffService.getStaffById(person.getStaffId());
                staffList.add(temp);
                break;
            case Constant.APPROVE_JS:
//                角色
                //根据角色编码获取职员集合
                staffList = roleService.getStaffByRoleCoding(person.getStaffId());
                break;
            case Constant.APPROVE_LEADER:
//                上级领导
                Duty[] d = staff.getDuty();
                if (d == null || d.length <= 0) {
                    d = dutyService.getDutyByStaffId(staff.getId());
                }
                if (d != null) {
                    for (Duty te : d) {
                        if (te != null) {
                            te = dutyService.getById(te.getParentId());
                            if (te != null) {
//                                获取职务所有人员
                                if (staffList.size() > 0) {
                                    staffList.addAll(dutyService.getStaffByDuty(te.getId()));
                                } else {
                                    staffList = dutyService.getStaffByDuty(te.getId());
                                }
                            }
                        }
                    }
                }
                if (staffList == null || staffList.size() == 0) {
//                    没有上级领导，自己为领导
                    staffList.add(staff);
                }
                break;
            case Constant.APPROVE_SECTION_LEADER:
//                部门主管
                staffList = sectionService.getStaffListByLeader(staff.getSection().getId());
                break;
            case Constant.APPROVE_FAQIREN:
//                发起人
                staffList.add(staff);
                break;
            default:
//                        数据获取失败
                break;
        }
        return staffList;
    }

    /**
     * 获取sql语句，为了兼容pm2软件，利用窗口编号进行反查sql语句，抛弃om2后该sql可以放弃
     *
     * @param frameCoding 窗口编号
     * @return
     */
    @Override
    public String getSql(String frameCoding) {
        PageHelper.startPage(1, 1);
        return flowMessageMapper.getSql(frameCoding);
    }

    @Override
    public FlowMessage getMessageByFrameId(String id) {
        PageHelper.startPage(1, 1);
        return flowMessageMapper.getMsgByFrameId(id);
    }

    @Override
    public void deleteMessageById(String id) {
        FlowMessage fm = flowMessageMapper.getMessageById(id);
        if (fm != null) {
            deleteMessage(fm.getFrameId(), true);
        }
    }

    @Override
    public void updateFlag(String id, String fqFlag) {
        flowMessageMapper.updateFlag(id, fqFlag);
    }

    @Override
    public Map<String, Object> startFlow(FlowMessage message, FlowCourse pubPerson, Flow flow, Staff staff) {
        Map<String, Object> params = new HashMap(16);
        FlowCourse flowCourse = flowCourseService.getFlowCourseFirst(flow.getId());
        if (flowCourse != null && flowCourse.getPubPerson() == 1) {       //是自由选人
            if (pubPerson == null || pubPerson.getPersonList() == null) {        //未选择审批人员
                ArrayList<CoursePerson> cps = new ArrayList<>();
                for (CoursePerson course : flowCourse.getPersonList()) {
                    if (course != null && course.getType() == 0) {      //审批人员才获取
                        course.setStaff(getStaffByType(course, staff));
                        cps.add(course);
                    }
                }
                //返回自由选人步骤对象，
                flowCourse.setPersonList(cps);
                params.put("state", 2);
                params.put("flowCourse", flowCourse);
                return params;
            }
        }
        //删除之前的流程记录
        deleteMessage(message.getFrameId());
        //重新发起
        FlowHistory history = message.getFlow();
        history.setSourceFlowId(history.getId());
        history.setId(UUID.randomUUID().toString());
        history.setFolderCoding(flow.getFolder());
        message.setFlow(history);
        message.setId(UUID.randomUUID().toString());
        message.setStartDate(DateFormat.getDateTime());
        message.setState(1);
        message.setStaff(staff);
        message.setStaffId(message.getStaff().getId());
        FLowConsentModel result = startFlow(flow, message, staff, history, pubPerson);
        if (result.getState() == 2) {
            //下一步为自由选人
            flowCourse = result.getFlowCourse();
            for (CoursePerson course : flowCourse.getPersonList()) {
                //审批人员才获取
                if (course != null && course.getType() == 0) {
                    course.setStaff(getStaffByType(course, staff));
                }
            }
            params.put("state", 2);
            //返回自由选人步骤对象，
            params.put("flowCourse", flowCourse);
            return params;
        } else if (result.getState() == 5) {
            message.setApproveList(flowApproveService.getFlowApproveByMessageId(message.getId()));
            params.put("state", 5);
            params.put("flowMessage", message);
            params.put("flowCourse", flowCourse);
            return params;
        }

        params.put("state", 1);
        params.put("flowMessage", message);

        return params;
    }

    @Override
    public void flowNotify(String id, FlowMessage message) {

        List<FlowApprove> approveList = new LinkedList<>();
        Map<String, Object> resultMap = new HashMap(16);
        List<FlowApprove> fa = flowApproveService.getFlowApproveByMessageId(id);
        for (FlowApprove approve : fa) {
            approveList.add(approve);
            resultMap.put("type", 2);
            try {
                MyWebSocketHandle.sendMeg(approve.getAcceptUser().getId(),
                        JSON.toJSONString(resultMap, SerializerFeature.DisableCircularReferenceDetect));
            } catch (IllegalStateException e) {
                //忽律session过期异常
            }
            resultMap.clear();
            approveList.remove(0);
            List<Staff> user = new ArrayList<>();
            user.add(approve.getAcceptUser());
            approve.setMessage(message);
            flowNotifyService.flowChange(1, user, approve);
            flowApproveService.insertEmailHistory(approve.getId(), "0");
        }
    }

    @Override
    public List<FlowMessage> getMsgList(FlowMessage message) {
        return flowMessageMapper.getMsgList(message);
    }

    @Override
    public String getFlowMsgStateStr(String frameId) {
        StringBuffer sb = new StringBuffer();
        FlowMessage fm = getMessageByFrameId(frameId);
        if (!Objects.isNull(fm)) {
            List<FlowApprove> approves = flowApproveService.getFlowApproveByMessageId(fm.getId());
            approves.forEach(a -> {
                if (a.getApproveState() <= 1) {
                    sb.append(a.getCourseName() + "-" + a.getAcceptUser().getName());
                }
            });
        }
        return sb.toString();
    }

    @Override
    public FlowMessage isError(FlowMessage msg) {
        boolean isAllOk = true;
        if (!Objects.isNull(msg.getApproveList())) {
            for (int i = 0; i < msg.getApproveList().size(); i++) {
                FlowApprove item = msg.getApproveList().get(i);
                if (item.getApproveState() <= 1) {
                    isAllOk = false;
                }
            }
            if (isAllOk && msg.getState() == 1) {
                msg.setApproveError(true);
                //设置异常节点id
                for (int i = msg.getApproveList().size() - 1; i >= 0; i--) {
                    FlowApprove fa = msg.getApproveList().get(i);
                    if (fa.getApproveState() == 3) {
                        int minutes = DateUtil.getOffsetMinutes(DateUtil.parse(fa.getApproveDate()), new Date());
                        if (minutes > 5) {
                            //大于５分钟，流程仍未流转，记录为异常
                            msg.setErrorApproveId(fa.getId());
                            break;
                        }
                    }
                }

            }
        }
        return msg;
    }


    @Override
    public List<FlowMessage> getMsgListByApproveDate(FLowMessageQuery message) {
        PageHelper.startPage(1,100);
        return flowMessageMapper.getMsgListByApproveDate(message);
    }

    @Override
    public int checkStatus(FlowMessage flowMsg, FlowApprove approve) {
        try {
            if (Objects.isNull(approve.getAcceptUser())) {
                approve.setAcceptUser(staffService.getStaffById(approve.getAcceptStaffId()));
            }
            flowMsg.setLastApproveUser(approve.getAcceptUser());

            if (Constant.FLOW_SUCCESS_HANDLER.containsKey(flowMsg.getFrameCoding())) {
                String service = Constant.FLOW_SUCCESS_HANDLER.get(flowMsg.getFrameCoding());
                dynamicMethodExecutor.executeDynamicMethod(service, flowMsg);
            } else {
                Menu menu = menuService.getMenuByCoding(flowMsg.getFrameCoding());
                if (!Objects.isNull(menu) && StringUtils.isNotBlank(menu.getBeanName())) {
                    FrameStateCheckService o = WebParam.webApplicationContext.getBean(menu.getBeanName() + "Service", FrameStateCheckService.class);
                    o.oaSuccessChange(flowMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            flowMessageMapper.checkFrameState(flowMsg.getFrameId(), flowMsg.getFrameCoding(), approve.getAcceptStaffId(), DateUtil.getDate());
            updateMsgStatus(flowMsg.getId(), 2);
        }
        return 1;
    }

    @Override
    public void checkErrorFlow() {
        HashMap<String, Boolean> nc = new HashMap<>();
        String startDatetime = DateUtil.getDate() + " 00:00:00";
        String endDatetime = DateUtil.getDatetime();
        List<FlowApprove> fa = flowApproveService.queryErrorFlow(startDatetime, endDatetime);
        List<Staff> staff = new ArrayList<>();
        Staff staff1 = new Staff();
        staff1.setId("3E2BAFBA-0BA7-4AFA-8DF3-A8D747B010B5");
        staff.add(staff1);
        fa.forEach(item -> {
            FlowMessage fm = getMessageById(item.getFlowMessageId());
            if (!Objects.isNull(fm)
                    && !nc.containsKey(item.getFlowMessageId())
                    && fm.getState() == 2 && item.getApproveState() == 0) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("title", "异常流程通知");
                result.put("mTitle", "");
                result.put("content", "点击处理详情");
                result.put("url", WebParam.VUETIFY_BASE + "/system/errorFlowCheck/" + fm.getId() + "/" + item.getId());
                flowNotifyService.msgNotify(staff, result);
                nc.put(item.getFlowMessageId(), true);
            }
        });
    }

    @Override
    public String getSqlById(String id) {
        return flowMessageMapper.getSqlById(id);
    }

    @Override
    public List<FlowMessage> getOverMessage(String startDatetime, String endDatetime) {
        return flowMessageMapper.getOverMessage(startDatetime, endDatetime);
    }

    @Override
    public void deleteMessage(String frameId, boolean delHandler) {
        FlowMessage fm = getMessageByFrameId(frameId);
        if (!Objects.isNull(fm)) {
            String msgId = fm.getId();
            String historyId = fm.getHistroryId();
            flowMessageMapper.deleteMessage(msgId);

            executorService.submit(() -> {

                if (delHandler) {
                    //触发表单删除事件
                    try {
                        String delService = Constant.FLOW_DEL_HANDLER.get(fm.getFrameCoding());
                        if (StringUtils.isNotBlank(delService)) {
                            String[] dynamic = delService.split("\\.");
                            String sn = dynamic[0];
                            String method = dynamic[1];
                            dynamicMethodExecutor.executeDynamicMethod(sn, method, fm.getFrameId());
                        }
                    } catch (Exception e) {
                        //ignore this exception
                    }
                }

                flowMessageMapper.deleteHistory(msgId);
                flowApproveService.deleteByMsgId(msgId);
                flowCourseService.deleteInstanceByFlowId(historyId);
                coursePersonAttachedService.deleteHistoryByFlowId(historyId);
                flowCourseRelationService.deleteRelationBHistory(historyId);
                flowCourseService.deleteCourseBInstanceHistory(historyId);
                coursePersonAttachedService.deleteByFlowHistoryId(historyId, false);
                flowCourseRelationService.deleteByHistoryId(historyId, false);
            });
        }
    }

    @Override
    public void syncDate() {
        List<FlowMessage> messages = flowMessageMapper.getNoDate();
        messages.forEach(item -> {
            FlowApprove fa = flowApproveService.getLastApprove(item.getId());
            if (!Objects.isNull(fa)) {
                item.setDate(fa.getApproveDate());
                flowMessageMapper.updateFinishDate(item);
            }
        });
    }

    @Override
    public FlowMessage getByFlowHistory(String historyId) {
        PageHelper.startPage(1, 1);
        return flowMessageMapper.getByFlowHistory(historyId);
    }
}
