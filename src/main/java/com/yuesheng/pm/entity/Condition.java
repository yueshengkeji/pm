package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2019-08-24 流程判断 sdpo020_Condition.
 * @author XiaoSong
 * @date 2019/08/21
 */
public class Condition extends BaseEntity {
    /**
     * 主键    01
     */
    private String id;
    /**
     * 流程过程主键    02
     */
    private String courseId;
    /**
     * 判断字段名称    03
     */
    private String column;
    /**
     * 判断名称  04
     */
    private String name;
    /**
     * 前置括号    05
     */
    private String beforeBracket;
    /**
     * 运算符 06
     */
    private String operation;
    /**
     * 运算目标值 07
     */
    private String value;
    /**
     * 与运算目标值相同  08
     */
    private String codition08;
    /**
     * 后置括号 09
     */
    private String afterBracket;
    /**
     * 多个条件连接符  (值为：or 或者 and  )  10
     */
    private String condition10;
    /**
     * 未知列   11  （值为：6 或者 1） 11      字符串比较时为1，数字比较时为6
     */
    private String condition11;
    /**
     * 未知列   12  （值为：0） 12
     */
    private String condition12;
    /**
     * 未知列   13  （值为：1） 13
     */
    private String condition13;
    /**
     * 未知列   14  （值为：空字符串） 14
     */
    private String condition14;
    /**
     * 排序列，多个判定条件根据该序号从小到大排序组合判定   15
     */
    private String condition15;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeforeBracket() {
        return beforeBracket;
    }

    public void setBeforeBracket(String beforeBracket) {
        this.beforeBracket = beforeBracket;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCodition08() {
        return codition08;
    }

    public void setCodition08(String codition08) {
        this.codition08 = codition08;
    }

    public String getAfterBracket() {
        return afterBracket;
    }

    public void setAfterBracket(String afterBracket) {
        this.afterBracket = afterBracket;
    }

    public String getCondition10() {
        return condition10;
    }

    public void setCondition10(String condition10) {
        this.condition10 = condition10;
    }

    public String getCondition11() {
        return condition11;
    }

    public void setCondition11(String condition11) {
        this.condition11 = condition11;
    }

    public String getCondition12() {
        return condition12;
    }

    public void setCondition12(String condition12) {
        this.condition12 = condition12;
    }

    public String getCondition13() {
        return condition13;
    }

    public void setCondition13(String condition13) {
        this.condition13 = condition13;
    }

    public String getCondition14() {
        return condition14;
    }

    public void setCondition14(String condition14) {
        this.condition14 = condition14;
    }

    public String getCondition15() {
        return condition15;
    }

    public void setCondition15(String condition15) {
        this.condition15 = condition15;
    }
}
