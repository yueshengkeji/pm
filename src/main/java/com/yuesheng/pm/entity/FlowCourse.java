package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-16 流程过程对象 sdpo020.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
@Schema(name="流程过程对象")
public class FlowCourse extends BaseEntity {
    /**
     *
     */
    @Schema(name="流程id")
    private String flowId;
    /**
     * 过程序号     03
     */
    @Schema(name="过程序号")
    private int serial;
    /**
     * 过程名称  04
     */
    @Schema(name="过程名称")
    private String name;
    /**
     * 会签  05
     */
    @Schema(name="会签：0=否，1=是")
    private byte po02005 = 0;
    /**
     * 时间限制  06
     */
    @Schema(name="是否有时间限制,0=没有，3=超过时间后自动同意，7=自动驳回，5=到期后重复发送通知")
    private int po02006 = 0;
    /**
     * 安全验证  07
     */
    @Schema(name="安全验证标记")
    private byte po02007 = 0;
    /**
     * 自由选人  08：{0：否，1：是}
     */
    @Schema(name="是否自由选人，0=否，1=是")
    private Integer pubPerson;
    /**
     * 短信审批    09
     */
    @Schema(name="短信审批，0=否，1=是")
    private String po02009 = "0";
    /**
     * 立即发送      10
     */
    @Schema(name="立即发送，0=否，1=是")
    private byte po02010 = 0;
    /**
     * 到期发送  11
     */
    @Schema(name="到期发送，0=否，1=是")
    private int po02011 = 0;
    /**
     * 允许跳过   12
     */
    @Schema(name="允许跳过，0=否，1=是")
    private byte po02012 = 0;
    /**
     * 13 自动处理到期时间
     */
    private int po02013 = 0;
    /**
     * 14
     */
    private int po02014 = 0;
    /**
     * 15
     */
    private int po02015 = 0;
    /**
     * 16
     */
    private int po02016 = 0;
    /**
     * 17
     */
    private String po02017 = "0";
    /**
     * 18
     */
    private int po02018 = 0;

    @Schema(name="判断条件字符串")
    private String po02019 = "";

    @Schema(name="手机审批，0=否，1=是")
    private byte po02021 = 1;

    private String po02026 = "";

    @Schema(name="窗口主键id")
    private String frameId;

    @Schema(name="审批人员集合")
    private List<CoursePerson> personList;

    @Schema(name="下一步骤集合")
    private List<FlowCourse> thisNextCourses;

    @Schema(name="父过程id")
    private String parentId;

    @Schema(name="当前过程关系对象")
    private FlowCourseRelation fcr;

    @Schema(name="过程判断条件")
    private CourseJudge judge;

    @Schema(name="过程判断条件集合")
    private List<CourseJudge> judgeList;

    @Schema(name="流程审批完成执行的方法")
    private String invokeName;

    public String getInvokeName() {
        return invokeName;
    }

    public void setInvokeName(String invokeName) {
        this.invokeName = invokeName;
    }

    /**
     * 审批消息实例集合
     */
    private List<FlowApprove> flowApproves;

    public List<FlowApprove> getFlowApproves() {
        return flowApproves;
    }

    public void setFlowApproves(List<FlowApprove> flowApproves) {
        this.flowApproves = flowApproves;
    }

    public CourseJudge getJudge() {
        return judge;
    }

    public void setJudge(CourseJudge judge) {
        this.judge = judge;
    }

    public FlowCourseRelation getFcr() {
        return fcr;
    }

    public void setFcr(FlowCourseRelation fcr) {
        this.fcr = fcr;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<FlowCourse> getThisNextCourses() {
        return thisNextCourses;
    }

    public void setThisNextCourses(List<FlowCourse> thisNextCourses) {
        this.thisNextCourses = thisNextCourses;
    }

    public byte getPo02021() {
        return po02021;
    }

    public void setPo02021(byte po02021) {
        this.po02021 = po02021;
    }

    public String getPo02026() {
        return po02026;
    }

    public void setPo02026(String po02026) {
        this.po02026 = po02026;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public byte getPo02005() {
        return po02005;
    }

    public void setPo02005(byte po02005) {
        this.po02005 = po02005;
    }

    public int getPo02006() {
        return po02006;
    }

    public void setPo02006(int po02006) {
        this.po02006 = po02006;
    }

    public byte getPo02007() {
        return po02007;
    }

    public void setPo02007(byte po02007) {
        this.po02007 = po02007;
    }

    public String getPo02009() {
        return po02009;
    }

    public void setPo02009(String po02009) {
        this.po02009 = po02009;
    }

    public byte getPo02010() {
        return po02010;
    }

    public void setPo02010(byte po02010) {
        this.po02010 = po02010;
    }

    public int getPo02011() {
        return po02011;
    }

    public void setPo02011(int po02011) {
        this.po02011 = po02011;
    }

    public byte getPo02012() {
        return po02012;
    }

    public void setPo02012(byte po02012) {
        this.po02012 = po02012;
    }

    public int getPo02013() {
        return po02013;
    }

    public void setPo02013(int po02013) {
        this.po02013 = po02013;
    }

    public int getPo02014() {
        return po02014;
    }

    public void setPo02014(int po02014) {
        this.po02014 = po02014;
    }

    public int getPo02015() {
        return po02015;
    }

    public void setPo02015(int po02015) {
        this.po02015 = po02015;
    }

    public int getPo02016() {
        return po02016;
    }

    public void setPo02016(int po02016) {
        this.po02016 = po02016;
    }


    public String getPo02017() {
        return po02017;
    }

    public void setPo02017(String po02017) {
        this.po02017 = po02017;
    }

    public int getPo02018() {
        return po02018;
    }

    public void setPo02018(int po02018) {
        this.po02018 = po02018;
    }

    public String getPo02019() {
        return po02019;
    }

    public void setPo02019(String po02019) {
        this.po02019 = po02019;
    }


    public List<CoursePerson> getPersonList() {
        return personList;
    }

    public void setPersonList(List<CoursePerson> personList) {
        this.personList = personList;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPubPerson() {
        return pubPerson;
    }

    public void setPubPerson(Integer pubPerson) {
        this.pubPerson = pubPerson;
    }

    public void setJudgeList(List<CourseJudge> judgeList) {
        this.judgeList = judgeList;
    }

    public List<CourseJudge> getJudgeList() {
        return judgeList;
    }
}
