package com.yuesheng.pm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工作日志(ProWorkLog)实体类
 *
 * @author xiaoSong
 * @since 2021-06-25 14:52:55
 */
@JsonIgnoreProperties(value = {"handler"})
public class ProWorkLog implements Serializable {
    private static final long serialVersionUID = -61531209253507961L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 填报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datetime;
    /**
     * 工作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;
    /**
     * 工作内容
     */
    private String content;
    /**
     * 是否完成：0=未完成,1=已完成
     */
    private Byte state;
    /**
     * 填报部门
     */
    private Section section;
    /**
     * 填报人
     */
    private Staff staff;
    /**
     * 日志截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 附件列表
     */
    private List<Attach> files;
    @Schema(name="完成情况")
    private String note;
    @Schema(name="权重")
    private Double weight;
    @Schema(name="评分")
    private Double score = 0.0;
    @Schema(name="考核表id")
    private String perId;
    @Schema(name="标记信息")
    private String tag;
    @Schema(name="是否可编辑")//0可编辑
    private Boolean edit;
    @Schema(name="打分审批id")
    private String scoreId;
    @Schema(name="自评分")
    private Double iScore;

    public Double getiScore() {
        return iScore;
    }

    public void setiScore(Double iScore) {
        this.iScore = iScore;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPerId() {
        return perId;
    }

    public void setPerId(String perId) {
        this.perId = perId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Attach> getFiles() {
        return files;
    }

    public void setFiles(List<Attach> files) {
        this.files = files;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

}
