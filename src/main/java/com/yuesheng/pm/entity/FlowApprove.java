package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by Administrator on 2016-08-16 审批流程消息表 sdpo004.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
@Schema(name="审批消息")
public class FlowApprove extends BaseEntity {
    /**
     * 流程消息主表id  02
     */
    @Schema(name="消息实例id")
    private String flowMessageId;
    /**
     * 过程id  03
     */
    @Schema(name="过程id")
    private String courseId;
    /**
     * 过程名称
     */
    @Schema(name="过程名称")
    private String courseName;
    /**
     * 发送人id 04
     */
    @Schema(name="发送人id")
    private String staffId;
    /**
     * 接收人id 05
     */
    @Schema(name="接收人id")
    private String acceptStaffId;
    /**
     * 发送时间  06      添加时不用
     */
    @Schema(name="发送时间")
    private String accrptDate = "";
    /**
     * 已读时间  07      添加时不用
     */
    @Schema(name="已读时间")
    private String readDate = "";
    /**
     * 审批时间  08      添加时不用
     */
    @Schema(name="审批时间")
    private String approveDate = "";
    /**
     * 默认0，作用未知  09
     */
    private int po00409 = 0;
    /**
     * 审批内容  10      添加时不用
     */
    @Schema(name="审批内容（审批或者驳回时使用）")
    private String content = "";
    /**
     * 操作状态 11  {
     * 0：未读，
     * 1：已读，
     * 2: 催办,
     * 3：同意，
     * 4：不同意,
     * 5: 知会未读，
     * 6：知会已读，
     * 7：驳回,
     * 8: 取消（忽略）,
     * 9: 退回}
     * 添加时默认为0:：未读
     */
    @Schema(name="当前状态：{0：未读，1：已读，3：同意，5:知会未读，6：知会已读，7：驳回,8:忽略}")
    private int approveState = 0;
    /**
     * 未知标识，12 默认：0
     */
    private int po00412 = 0;
    /**
     * 某个时间列 13,最后时间列？
     */
    private String date;
    /**
     * 审批人员序号
     */
    @Schema(name="当前审批步骤中人员序号")
    private byte po00414;
    /**
     * 审批步骤序号
     */
    @Schema(name="当前审批步骤序号")
    private Integer po00415;
    /**
     * 作用未知  默认0；    16
     */
    private byte po00416 = 0;
    /**
     * 发送时间 17
     */
    @Schema(name="发送时间")
    private String acceptDate;
    /**
     * 我的审批表附表id     sdpo007:po00701
     */
    private String po00418Id;
    /**
     * 默认0   添加时无需   {0：自动，1：手动知会}
     */
    private byte po00419;
    /**
     * 默认0   添加时无需
     */
    private byte po00420;
    /**
     * 默认空字符串，1=分期支付标记,2=加签标记
     */
    private String po00421 = "";
    /**
     * 发送用户
     */
    @Schema(name="发送人对象")
    private Staff sendUser;
    /**
     * 接收用户
     */
    @Schema(name="接收人对象")
    private Staff acceptUser;
    /**
     * 审批消息主对象
     */
    @Schema(name="流程消息实例对象")
    private FlowMessage message;
    /**
     * 是否最后一个审批步骤
     */
    @Schema(name="是否为最后一步审批")
    private Integer lastCourse = 0;

    /**
     * 【我的审批】对象
     */
    private MyApproveAttached attached;
    /**
     * 当前步骤对象
     */
    private FlowCourse course;

    public FlowCourse getCourse() {
        return course;
    }

    public void setCourse(FlowCourse course) {
        this.course = course;
    }

    public Integer getLastCourse() {
        return lastCourse;
    }

    public void setLastCourse(Integer lastCourse) {
        this.lastCourse = lastCourse;
    }


    public MyApproveAttached getAttached() {
        return attached;
    }

    public void setAttached(MyApproveAttached attached) {
        this.attached = attached;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Staff getSendUser() {
        return sendUser;
    }

    public void setSendUser(Staff sendUser) {
        this.sendUser = sendUser;
    }

    public Staff getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(Staff acceptUser) {
        this.acceptUser = acceptUser;
    }

    public String getFlowMessageId() {
        return flowMessageId;
    }

    public void setFlowMessageId(String flowMessageId) {
        this.flowMessageId = flowMessageId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getAcceptStaffId() {
        return acceptStaffId;
    }

    public void setAcceptStaffId(String acceptStaffId) {
        this.acceptStaffId = acceptStaffId;
    }

    public String getAccrptDate() {
        return accrptDate;
    }

    public void setAccrptDate(String accrptDate) {
        this.accrptDate = accrptDate;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public int getPo00409() {
        return po00409;
    }

    public void setPo00409(int po00409) {
        this.po00409 = po00409;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getApproveState() {
        return approveState;
    }

    public void setApproveState(int approveState) {
        this.approveState = approveState;
    }

    public int getPo00412() {
        return po00412;
    }

    public void setPo00412(int po00412) {
        this.po00412 = po00412;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte getPo00414() {
        return po00414;
    }

    public void setPo00414(byte po00414) {
        this.po00414 = po00414;
    }

    public Integer getPo00415() {
        return po00415;
    }

    public void setPo00415(Integer po00415) {
        this.po00415 = po00415;
    }

    public byte getPo00416() {
        return po00416;
    }

    public void setPo00416(byte po00416) {
        this.po00416 = po00416;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getPo00418Id() {
        return po00418Id;
    }

    public void setPo00418Id(String po00418Id) {
        this.po00418Id = po00418Id;
    }

    public byte getPo00419() {
        return po00419;
    }

    public void setPo00419(byte po00419) {
        this.po00419 = po00419;
    }

    public byte getPo00420() {
        return po00420;
    }

    public void setPo00420(byte po00420) {
        this.po00420 = po00420;
    }

    public String getPo00421() {
        return po00421;
    }

    public void setPo00421(String po00421) {
        this.po00421 = po00421;
    }

    public FlowMessage getMessage() {
        return message;
    }

    public void setMessage(FlowMessage message) {
        this.message = message;
    }


    @Override
    public FlowApprove clone() {
        FlowApprove faClone = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
//            将该对象序列化成流, 因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
//            将流序列化成对象
            ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            faClone = (FlowApprove) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(oos);
        }
        return faClone;
    }
}
