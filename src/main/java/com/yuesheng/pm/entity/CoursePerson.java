package com.yuesheng.pm.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.util.List;

/**
 * Created by Administrator on 2016-08-16 审批过程对象 sdpo002.
 * @author XiaoSong
 * @date 2016/08/16
 */
@Schema(name="审批过程人员对象")
public class CoursePerson extends BaseEntity {
    @Schema(name="流程id")
    private String flowId;
    @Schema(name="序号")
    private int serial;
    @Schema(name="处理人员类型")
    private int staffType;
    @Schema(name="过程id")
    private String courseId;
    @Schema(name="审批人列表")
    private List<Staff> staff;
    @Schema(name="审批人Id 05,(若staffType为角色或别的类型，则他保存的是该角色主键)")
    private String staffId;
    @Schema(name="处理类型  09  {0：审批，1：知会};")
    private int type;
    @Schema(name="当前处理角色类型名称")
    private String name;
    @Schema(name="处理人员集合字符串")
    private String persons;
    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public int getStaffType() {
        return staffType;
    }

    public void setStaffType(int staffType) {
        this.staffType = staffType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}
