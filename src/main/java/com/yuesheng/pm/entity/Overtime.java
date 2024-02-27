package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/5/10 加班单.
 * @author XiaoSong
 * @date 2017/05/10
 */
public class Overtime extends BaseEntity {
    /**
     * 1.单据日期
     */
    private String date;
    /**
     * 加班日期
     */
    private String overtime;
    /**
     * 开始时间
     */
    private String begin;
    /**
     * 截止时间
     */
    private String end;
    /**
     * 加班事由
     */
    private String remark;
    /**
     * 单据名称
     */
    private String name;
    /**
     * 加班时长
     */
    private double hour;
    /**
     * 加班人员
     */
    private Staff staff;
    /**
     * 流程主体
     */
    private FlowMessage msg;
    /**
     * 是否审核：0=未审核，1=已审核，根据sdpo003:po00308查询
     */
    private int approve;
    /**
     * 审批时间
     */
    private String approveDate;

    private String overtimeEnd;

    public String getOvertimeEnd() {
        return overtimeEnd;
    }

    public void setOvertimeEnd(String overtimeEnd) {
        this.overtimeEnd = overtimeEnd;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public FlowMessage getMsg() {
        return msg;
    }

    public void setMsg(FlowMessage msg) {
        this.msg = msg;
    }
}
