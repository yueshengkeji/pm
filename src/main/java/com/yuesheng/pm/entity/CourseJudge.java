package com.yuesheng.pm.entity;

/**
 * Created by 96339 on 2017/3/16 过程判断对象 sdpo020_Condition.
 * @author XiaoSong
 * @date 2017/03/16
 */
public class CourseJudge extends BaseEntity {
    /**
     * 步骤id  02
     */
    private String courseId;
    /**
     * 判断的字段名称       03
     */
    private String fieldName;
    /**
     * 判断名称          04
     */
    private String name;
    /**
     * 前缀          05
     */
    private String fixing;
    /**
     * 判断类型 小于/大于 等等     06
     */
    private String type;
    /**
     * 判断的界限值        07
     */
    private String value;
    /**
     * 判断的界限值        07
     */
    private String value2;
    /**
     * 后缀        08
     */
    private String suffix;
    /**
     * 联合判断关系符号      09
     */
    private String relation;
    /**
     * 未知            11
     */
    private String no11 = "";
    /**
     * 未知            12    默认0
     */
    private String no12 = "0";
    /**
     * 未知            13    默认1
     */
    private String no13 = "1";
    /**
     * 未知            14
     */
    private String no14 = "";
    /**
     * 未知，默认0
     */
    private int no15 = 0;
    /**
     * 流程发起后记录表id        sdpo001_history主键
     */
    private String historyId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFixing() {
        return fixing;
    }

    public void setFixing(String fixing) {
        this.fixing = fixing;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getNo11() {
        return no11;
    }

    public void setNo11(String no11) {
        this.no11 = no11;
    }

    public String getNo12() {
        return no12;
    }

    public void setNo12(String no12) {
        this.no12 = no12;
    }

    public String getNo13() {
        return no13;
    }

    public void setNo13(String no13) {
        this.no13 = no13;
    }

    public String getNo14() {
        return no14;
    }

    public void setNo14(String no14) {
        this.no14 = no14;
    }

    public int getNo15() {
        return no15;
    }

    public void setNo15(int no15) {
        this.no15 = no15;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }
}
