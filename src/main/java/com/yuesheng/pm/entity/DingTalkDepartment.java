package com.yuesheng.pm.entity;

/**
 * @ClassName dingTalkDepartment
 * @Description
 * @Author ssk
 * @Date 2022/10/22 0022 10:55
 */
public class DingTalkDepartment{
    /**
     * 系统部门id
     */
    private String sectionId;

    /**
     * 钉钉部门id
     */
    private Long dept_id;

    /**
     * 部门名称
     */
    private String name;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public Long getDept_id() {
        return dept_id;
    }

    public void setDept_id(Long dept_id) {
        this.dept_id = dept_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
