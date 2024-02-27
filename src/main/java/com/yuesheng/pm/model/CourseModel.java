package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.Course;

/**
 * Created by 96339 on 2017/12/8.
 * @author XiaoSong
 * @date 2017/12/08
 */
public class CourseModel extends BaseEntity {
    /**
     * 科目
     */
    private Course course;
    /**
     * 金额
     */
    private Double money;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
