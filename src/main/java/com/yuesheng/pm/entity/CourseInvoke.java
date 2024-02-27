package com.yuesheng.pm.entity;

public class CourseInvoke extends BaseEntity {
    private String courseId;
    private String invokeName;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInvokeName() {
        return invokeName;
    }

    public void setInvokeName(String invokeName) {
        this.invokeName = invokeName;
    }
}
