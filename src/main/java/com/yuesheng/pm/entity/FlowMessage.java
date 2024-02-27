package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-16 流程发起后，消息主表【我的发起】 sdpo003.
 *
 * @author XiaoSong
 * @date 2016/08/16
 */
@Schema(name="流程实例消息")
public class FlowMessage extends BaseEntity {
    /**
     * 发起时间  02
     */
    @Schema(name="发起时间")
    private String startDate;
    /**
     * 发起人id        03
     */
    @Schema(name="发起人id")
    private String staffId;
    /**
     * 发起标题        04
     */
    @Schema(name="消息标题")
    private String title;
    /**
     * 发起内容        05
     */
    @Schema(name="消息内容")
    private String content;
    /**
     * 窗口编号        06
     */
    @Schema(name="窗体编号")
    private String frameCoding;
    /**
     * 窗口对象主键id  07 如：采购订单主id
     */
    @Schema(name="表单内容对象主键")
    private String frameId;
    /**
     * 当前流程状态    08 {
     * 1:审批中（处理中），
     * 2:流程已完成,
     * 3:已中断,
     * 4:已取消}
     */
    @Schema(name="流程状态")
    private int state = 0;
    /**
     * 默认0，作用未知
     */
    private int po00309 = 0;
    /**
     * 默认0，作用未知
     */
    private int po00310 = 0;
    /**
     * 流程记录id    sdpo001_History表主键
     */
    private String histroryId;
    /**
     * 默认0，作用未知
     */
    private int po00312 = 0;
    /**
     * 处理完成时间    13
     */
    private String date;
    /**
     * 默认空字符串，作用未知   14
     */
    private String po00314 = "";
    /**
     * sql语句，目前位置    15
     */
    private String sql;
    /**
     * 对象主键id列名称     16
     */
    private String frameColumn;
    /**
     * 默认空字符串，作用未知   17
     */
    private String po00317 = "";
    /**
     * 发起人对象
     */
    @Schema(name="发起人")
    private Staff staff;
    /**
     * 窗体名称   sdeb002中eb00202字段
     */
    @Schema(name="窗体名称")
    private String frameName;
    /**
     * 当前流程对象
     */
    private FlowHistory flow;
    /**
     * 当前流程审批步骤列表
     */
    private List<FlowApprove> approveList;
    /**
     * 审批步骤是否出现异常
     */
    private boolean approveError;
    /**
     * 异常步骤id
     */
    private String errorApproveId;
    /**
     * 最后审批人
     */
    private Staff lastApproveUser;

    public Staff getLastApproveUser() {
        return lastApproveUser;
    }

    public void setLastApproveUser(Staff lastApproveUser) {
        this.lastApproveUser = lastApproveUser;
    }

    public String getErrorApproveId() {
        return errorApproveId;
    }

    public void setErrorApproveId(String errorApproveId) {
        this.errorApproveId = errorApproveId;
    }

    public boolean isApproveError() {
        return approveError;
    }

    public void setApproveError(boolean approveError) {
        this.approveError = approveError;
    }

    public List<FlowApprove> getApproveList() {
        return approveList;
    }

    public void setApproveList(List<FlowApprove> approveList) {
        this.approveList = approveList;
    }

    public FlowHistory getFlow() {
        return flow;
    }

    public void setFlow(FlowHistory flow) {
        this.flow = flow;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrameCoding() {
        return frameCoding;
    }

    public void setFrameCoding(String frameCoding) {
        this.frameCoding = frameCoding;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPo00309() {
        return po00309;
    }

    public void setPo00309(int po00309) {
        this.po00309 = po00309;
    }

    public int getPo00310() {
        return po00310;
    }

    public void setPo00310(int po00310) {
        this.po00310 = po00310;
    }

    public String getHistroryId() {
        return histroryId;
    }

    public void setHistroryId(String histroryId) {
        this.histroryId = histroryId;
    }

    public int getPo00312() {
        return po00312;
    }

    public void setPo00312(int po00312) {
        this.po00312 = po00312;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPo00314() {
        return po00314;
    }

    public void setPo00314(String po00314) {
        this.po00314 = po00314;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getFrameColumn() {
        return frameColumn;
    }

    public void setFrameColumn(String frameColumn) {
        this.frameColumn = frameColumn;
    }

    public String getPo00317() {
        return po00317;
    }

    public void setPo00317(String po00317) {
        this.po00317 = po00317;
    }
}
